/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.transactionsettlement;

import emc.bus.debtors.allocationimport.DebtorsAllocationImportFailLogLocal;
import emc.bus.debtors.allocationimport.DebtorsAllocationImportLocal;
import emc.bus.debtors.allocationimport.DebtorsAllocationImportSetupMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.entity.debtors.DebtorsOpenTransactions;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportSetupLines;
import emc.entity.debtors.transactionsettlement.DebtorsTransactionSettlement;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportSetupConditions;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : This bean is used by the Debtors transaction settlement procedure.
 *
 * @date        : 10 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsTransactionSettlementBean extends EMCEntityBean implements DebtorsTransactionSettlementLocal {

    @EJB
    private DebtorsTransactionLogicLocal transLogicBean;
    @EJB
    private DebtorsAllocationImportSetupMasterLocal importSetupMasterBean;
    @EJB
    private DebtorsAllocationImportLocal allocationImportBean;
    @EJB
    private DebtorsAllocationImportFailLogLocal allocationImportFailLogBean;
    @EJB
    private DebtorsParametersLocal parametersBean;
    @EJB
    private EMCDateHandlerLocal dateHandler;

    /** Creates a new instance of DebtorsTransactionSettlementBean */
    public DebtorsTransactionSettlementBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            DebtorsTransactionSettlement settlement = (DebtorsTransactionSettlement) theRecord;

            if (!"tick".equals(fieldNameToValidate) && !settlement.isTick()) {
                //TODO: DB Message
                logMessage(Level.SEVERE, "You may not change unticked records.", userData);
                return false;
            }

            if (fieldNameToValidate.equals("tick") && settlement.isTick()) {
                DebtorsParameters params = parametersBean.getDebtorsParameters(userData);
                if (params == null) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
                }

                if (checkTickAllowed(settlement, userData)) {
                    if (settlement.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                        settlement.setDiscAvail(calculateDiscAvailable(settlement, null, params.getDiscountToleranceDays(), userData));
                    }
                    return settlement;
                } else {
                    return false;
                }
            } else if ("creditAmountSettled".equals(fieldNameToValidate)) {
                if (getTickedDebits(settlement.getSessionId(), userData).size() > 1) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.AMT_CHANGE, userData, "Credit", "debit");
                    return false;
                } else {
                    settlement.setUserChangedAmount(true);

                    //Ensure that client honours amount changed flag when sending record
                    //back to server for field validation or update.
                    return settlement;
                }
            } else if ("debitAmountSettled".equals(fieldNameToValidate)) {
                if (getTickedCredits(settlement.getSessionId(), userData).size() > 1) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.AMT_CHANGE, userData, "Debit", "credit");
                    return false;
                } else {
                    settlement.setUserChangedAmount(true);

                    //Ensure that client honours amount changed flag when sending record
                    //back to server for field validation or update.
                    return settlement;
                }
            } else if ("discTaken".equals(fieldNameToValidate)) {
                if (settlement.getDiscTaken().compareTo(settlement.getDebitBalance()) > 0) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.DISCOUNT_TOTAL_EXCEED, userData);
                    return false;
                } else if (settlement.getDiscTaken().compareTo(settlement.getDiscAvail()) > 0) {
                    logMessage(Level.WARNING, ServerDebtorsMessageEnum.DISCOUNT_AVAILABLE_EXCEED, userData);
                }
            }
        }

        return ret;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsTransactionSettlement settlement = (DebtorsTransactionSettlement) uobject;

        DebtorsTransactionSettlement persisted = (DebtorsTransactionSettlement) util.findDetachedPersisted(settlement, userData);

        //This is used when a settlement is ticked and has a rebate/discount set before being persisted.
        boolean ticked = false;

        boolean discountAllocated = false;

        if (settlement.isTick() && !persisted.isTick()) {
            ticked = true;
            if (settlement.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                debitTicked(settlement, userData);

                if (settlement.getDiscTaken().compareTo(BigDecimal.ZERO) != 0) {
                    settlement.setUserChangedAmount(true);
                } else {
                    //Allocate discount here in order to get a hold of the result
                    discountAllocated = allocateDiscount(settlement, true, userData);
                }
            } else if (settlement.getCredit().compareTo(BigDecimal.ZERO) > 0) {
                creditTicked(settlement, userData);
            }
        } else if (settlement.getDebitAmountSettled().compareTo(persisted.getDebitAmountSettled()) != 0) {
            debitAmountChanged(settlement, settlement.getDebitAmountSettled().subtract(persisted.getDebitAmountSettled()), userData);
        } else if (settlement.getCreditAmountSettled().compareTo(persisted.getCreditAmountSettled()) != 0) {
            creditAmountChanged(settlement, persisted, userData);
        } else if (!settlement.isTick() && persisted.isTick()) {
            if (settlement.getDebit().compareTo(BigDecimal.ZERO) > 0) {
                debitUnticked(settlement, userData);
            } else if (settlement.getCredit().compareTo(BigDecimal.ZERO) > 0) {
                creditUnticked(settlement, userData);
            }
        }

        if ((settlement.getDiscTaken().compareTo(persisted.getDiscTaken()) != 0 || settlement.getRebate().compareTo(persisted.getRebate()) != 0) && settlement.isTick()) {
            //Do not maintain relation between amount and discount taken if user changed amount explicitly.
            if (!settlement.isUserChangedAmount() && !discountAllocated) {
                BigDecimal discDiff = settlement.getDiscTaken().subtract(persisted.getDiscTaken());
                BigDecimal rebateDiff = settlement.getRebate().subtract(persisted.getRebate());

                settlement.setDebitAmountSettled(settlement.getDebitAmountSettled().subtract(discDiff).subtract(rebateDiff));

                if (settlement.getDebitAmountSettled().compareTo(BigDecimal.ZERO) < 0) {
                    throw new EMCEntityBeanException("Amount allocated - discount - rebate may not be less than 0.");
                }

                //If record has just been ticked, do not use persisted, otherwise wrong amount will be allocated on credit.
                //If the record has just been ticked, the rebate and discount should be subtracted (* -1).
                debitAmountChanged(settlement, (ticked ? (discDiff.add(rebateDiff)).multiply(new BigDecimal(-1)) : settlement.getDebitAmountSettled().subtract(persisted.getDebitAmountSettled())), userData);
            }
            updateDebitBalance(settlement);
        }

        return super.update(uobject, userData);
    }

    /** Called when a credit settlement record is ticked and saved. */
    private void creditTicked(DebtorsTransactionSettlement creditSettlement, EMCUserData userData) throws EMCEntityBeanException {
        //This method is called from update() for the debitSettlement that is passed in.
        //Update logic shouldn't be called again, so we use super.update() to update
        //credit settlement records in this method.

        long sessionId = creditSettlement.getSessionId();

        //Get records ticked on either side
        List<DebtorsTransactionSettlement> debitSettlements = getTickedDebits(sessionId, userData);
        List<DebtorsTransactionSettlement> creditSettlements = getTickedCredits(sessionId, userData);

        if (creditSettlements.size() > 0 && creditSettlements.get(0).getRecordID() == creditSettlement.getRecordID()) {
            //Hack to make auto allocation work
            creditSettlements.remove(0);
        }

        if (debitSettlements.size() == 0) {
            if (!creditSettlement.isUserChangedAmount() && creditSettlement.getCreditAmountSettled().compareTo(BigDecimal.ZERO) == 0) {
                //No settlements ticked on other side.  Allocate in full
                creditSettlement.setCreditAmountSettled(creditSettlement.getCredit());
                updateCreditBalance(creditSettlement);
            }
        } else if (debitSettlements.size() == 1) {
            //Get the ticked debit settlement
            DebtorsTransactionSettlement debitSettlement = debitSettlements.get(0);

            if (creditSettlements.size() == 0) {
                //Only active transaction is ticked on this side
                //Compare debit & credit settlement amounts.  Debit gets preference
                //if only one record is ticked on either side.  Allocate credit to debit.
                allocateDebitToCredit(debitSettlement, creditSettlement, creditSettlements.size() > 0, userData);

                //Call super.update() to prevent this logic from being called recursively.
                super.update(debitSettlement, userData);
            } else {
                //Multiple credits are ticked
                allocateDebitToCredit(debitSettlement, creditSettlement, userData);
            }
        } else if (debitSettlements.size() > 1) {
            //Multiple debits are ticked
            if (creditSettlements.size() > 0) {
                //Including active settlement, multiple settlements are ticked
                throw new EMCEntityBeanException("You may not select multiple debits and multiple credits at the same time.");
            } else {
                allocateCreditToDebits(creditSettlement, debitSettlements, userData);
            }
        }

        //Calculate discounts
        Date newestCreditDate = getNewestCreditDate(sessionId, null, userData);
        if (newestCreditDate == null) {
            newestCreditDate = creditSettlement.getTransactionDate();
        }
        DebtorsParameters params = parametersBean.getDebtorsParameters(userData);
        if (params == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
        }

        for (DebtorsTransactionSettlement debitSettlement : debitSettlements) {
            debitSettlement.setDiscAvail(calculateDiscAvailable(debitSettlement, newestCreditDate, params.getDiscountToleranceDays(), userData));
            allocateDiscount(debitSettlement, false, userData);
            super.update(debitSettlement, userData);
        }
    }

    /** Called when a debit Settlement record is ticked and saved. */
    private void debitTicked(DebtorsTransactionSettlement debitSettlement, EMCUserData userData) throws EMCEntityBeanException {
        //This method is called from update() for the debitSettlement that is passed in.
        //Update logic shouldn't be called again, so we use super.update() to update
        //credit settlement records in this method.

        long sessionId = debitSettlement.getSessionId();

        //Get records ticked on either side
        List<DebtorsTransactionSettlement> debitSettlements = getTickedDebits(sessionId, userData);
        List<DebtorsTransactionSettlement> creditSettlements = getTickedCredits(sessionId, userData);

        if (creditSettlements.size() == 0) {
            if (!debitSettlement.isUserChangedAmount() && debitSettlement.getDebitAmountSettled().compareTo(BigDecimal.ZERO) == 0) {
                //No settlements ticked on other side.  Allocate in full, minus discount taken.
                debitSettlement.setDebitAmountSettled(debitSettlement.getDebit().subtract(debitSettlement.getDiscTaken()));
                updateDebitBalance(debitSettlement);
            }
        } else if (creditSettlements.size() == 1) {
            //Get the ticked credit settlement
            DebtorsTransactionSettlement creditSettlement = creditSettlements.get(0);

            //Compare debit & credit settlement amounts.  Debit gets preference
            //if only one record is ticked on either side.  Allocate credit to debit.
            allocateCreditToDebit(debitSettlement, creditSettlement, debitSettlements.size() > 0, userData);

            //Call super.update() to prevent this logic from being called recursively.
            super.update(creditSettlement, userData);
        } else if (creditSettlements.size() > 1) {
            //Multiple credits are ticked
            if (debitSettlements.size() > 0) {
                //Including active settlement, multiple settlements are ticked
                throw new EMCEntityBeanException("You may not select multiple debits and multiple credits at the same time.");
            } else {
                allocateDebitToCredits(debitSettlement, creditSettlements, userData);
            }
        }

        //Calculate discounts
        Date newestCreditDate = getNewestCreditDate(sessionId, null, userData);

        //This code caused a 'Newer version in database' message.
        //for (DebtorsTransactionSettlement oldDebitSettlement : debitSettlements) {
        //    if (oldDebitSettlement.getRecordID() != debitSettlement.getRecordID()) {
        //        debitSettlement.setDiscAvail(calculateDiscAvailable(oldDebitSettlement, newestCreditDate, userData));
        //        super.update(oldDebitSettlement, userData);
        //    }
        //}

        DebtorsParameters params = parametersBean.getDebtorsParameters(userData);
        if (params == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
        }

        //Also do ticked debit
        debitSettlement.setDiscAvail(calculateDiscAvailable(debitSettlement, newestCreditDate, params.getDiscountToleranceDays(), userData));
    //Discount not calculated here.  Result needed by the calling method.  See line 122.
    }

    /**
     * This method is used to allocate one credit against one debit.
     * Debit is given preference.  This method allocates against the outstanding amount
     * on the debit side, therefore it can cater to one or more credits.
     */
    private void allocateCreditToDebit(DebtorsTransactionSettlement debitSettlement, DebtorsTransactionSettlement creditSettlement, boolean multipleDebits, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal credit = null;

        if (!multipleDebits) {
            //This is the only ticked debit.  Unsettle credit
            creditSettlement.setCreditAmountSettled(BigDecimal.ZERO);
            updateCreditBalance(creditSettlement);

            //If debit amount is allocated, use that.  Also take discount into account
            if (debitSettlement.getDebitAmountSettled().compareTo(BigDecimal.ZERO) > 0) {
                credit = debitSettlement.getDebitAmountSettled();
            } else {
                //Use full credit.
                credit = creditSettlement.getCredit();
            }
        } else {
            if (debitSettlement.isUserChangedAmount()) {
                credit = debitSettlement.getDebitAmountSettled();
            } else {
                //Use unallocated
                credit = creditSettlement.getCredit().subtract(creditSettlement.getCreditAmountSettled());
            }
        }

        if (credit.compareTo(debitSettlement.getDebit()) <= 0) {
            //Debit is greater than credit.  Allocate full credit amount outstanding
            debitSettlement.setDebitAmountSettled(credit);
            updateDebitBalance(debitSettlement);
            creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(credit));
            updateCreditBalance(creditSettlement);
        } else {
            //Credit amount is greater than debit.  Allocate full debit amount
            debitSettlement.setDebitAmountSettled(debitSettlement.getDebit());
            updateDebitBalance(debitSettlement);
            creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(debitSettlement.getDebit()));
            updateCreditBalance(creditSettlement);
        }
    }

    /**
     * This method is used to allocate one credit against one debit.
     * Credit is given preference.  This method allocates against the outstanding amount
     * on the credit side, therefore it can cater to one or more debits.
     */
    private void allocateDebitToCredit(DebtorsTransactionSettlement debitSettlement, DebtorsTransactionSettlement creditSettlement, boolean multipleCredits, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal debit = null;

        if (!multipleCredits) {
            //If credit amount is allocated, use that.
            if (creditSettlement.getCreditAmountSettled().compareTo(BigDecimal.ZERO) > 0) {
                debit = creditSettlement.getCreditAmountSettled();
            } else {
                if (debitSettlement.getDebitAmountSettled().compareTo(BigDecimal.ZERO) > 0) {
                    //Use amount that user wishes to allocate
                    debit = debitSettlement.getDebitAmountSettled();
                } else {
                    //Use full debit.
                    debit = debitSettlement.getDebit();
                }
            }
        } else {
            //Use unallocated
            debit = debitSettlement.getDebit().subtract(debitSettlement.getDebitAmountSettled());
        }

        if (debit.compareTo(creditSettlement.getCredit()) <= 0) {
            //Credit is greater than debit.  Allocate full debit amount outstanding
            creditSettlement.setCreditAmountSettled(debit);
            updateCreditBalance(creditSettlement);

            if (multipleCredits) {
                debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(debit));

            } else {
                debitSettlement.setDebitAmountSettled(debit);
            }

            updateDebitBalance(debitSettlement);
        } else {
            //Debit amount is greater than credit.  Allocate full credit amount
            creditSettlement.setCreditAmountSettled(creditSettlement.getCredit());
            updateCreditBalance(creditSettlement);
            debitSettlement.setDebitAmountSettled(creditSettlement.getCredit());
            updateDebitBalance(debitSettlement);
        }
    }

    /**
     * This method is used to allocate one debit against one credit.
     * Debit is given preference.  This method allocates against the outstanding amount
     * on the debit side, therefore it can cater to one or more debits.
     */
    private void allocateDebitToCredit(DebtorsTransactionSettlement debitSettlement, DebtorsTransactionSettlement creditSettlement, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal debit = debitSettlement.getDebitBalance();

        if (creditSettlement.isUserChangedAmount()) {
            //Allocate credit amount set by user
            debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(creditSettlement.getCreditAmountSettled()));
            updateDebitBalance(debitSettlement);
        } else if (debit.compareTo(creditSettlement.getCredit()) < 0) {
            //Credit is greater than debit.  Allocate full debit amount outstanding
            creditSettlement.setCreditAmountSettled(debit);
            updateCreditBalance(creditSettlement);
            debitSettlement.setDebitAmountSettled(debitSettlement.getDebit());
            updateDebitBalance(debitSettlement);
        } else {
            //Debit amount is greater than credit.  Allocate full credit amount
            creditSettlement.setCreditAmountSettled(creditSettlement.getCredit());
            updateCreditBalance(creditSettlement);
            debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(creditSettlement.getCredit()));
            updateDebitBalance(debitSettlement);
        }
    }

    /** Allocates one debit against many credits. */
    private void allocateDebitToCredits(DebtorsTransactionSettlement debitSettlement, List<DebtorsTransactionSettlement> creditSettlements, EMCUserData userData) throws EMCEntityBeanException {
        //This code was updated to honour what has been allocated on the debit side.
        BigDecimal creditTotal = getCreditAllocated(debitSettlement.getSessionId(), userData);

        if (creditTotal.compareTo(debitSettlement.getDebit()) > 0) {
            throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.AMT_EXCEED, userData, "Credit"));
        } else {
            debitSettlement.setDebitAmountSettled(creditTotal);
            updateDebitBalance(debitSettlement);
        }

//        BigDecimal unallocatedDebit = debitSettlement.getDebit().subtract(debitSettlement.getDebitAmountSettled());
//        for (DebtorsTransactionSettlement creditSettlement : creditSettlements) {
//
//            if (unallocatedDebit.compareTo(BigDecimal.ZERO) == 0) {
//                //Unallocated debit consumed.  Ensure that transaction does not have anything allocated
//                creditSettlement.setCreditAmountSettled(BigDecimal.ZERO);
//                updateCreditBalance(creditSettlement);
//
//                super.update(creditSettlement, userData);
//            }
//
//            if (unallocatedDebit.compareTo(creditSettlement.getCredit()) > 0) {
//                //Unallocated debits can consume credit
//                creditSettlement.setCreditAmountSettled(creditSettlement.getCredit());
//                updateCreditBalance(creditSettlement);
//
//                super.update(creditSettlement, userData);
//
//                unallocatedDebit = unallocatedDebit.subtract(creditSettlement.getCredit());
//                debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(creditSettlement.getCredit()));
//            } else {
//                //Credit > unallocated debit.  Consume debit remaining.
//                creditSettlement.setCreditAmountSettled(unallocatedDebit);
//                updateCreditBalance(creditSettlement);
//
//                debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(unallocatedDebit));
//
//                super.update(creditSettlement, userData);
//
//                unallocatedDebit = BigDecimal.ZERO;
//            }
//        }
//
//        updateDebitBalance(debitSettlement);
    }

    /** Allocates one credit against many debits. */
    private void allocateCreditToDebits(DebtorsTransactionSettlement creditSettlement, List<DebtorsTransactionSettlement> debitSettlements, EMCUserData userData) throws EMCEntityBeanException {
        //This code was updated to honour what has been allocated on the debit side.
        BigDecimal debitTotal = getDebitAllocated(creditSettlement.getSessionId(), userData);

        if (debitTotal.compareTo(creditSettlement.getCredit()) > 0) {
            throw new EMCEntityBeanException(getMessage(ServerDebtorsMessageEnum.AMT_EXCEED, userData, "Debit"));
        } else {
            creditSettlement.setCreditAmountSettled(debitTotal);
            updateCreditBalance(creditSettlement);
        }
//        BigDecimal unallocatedCredit = creditSettlement.getCredit().subtract(creditSettlement.getCreditAmountSettled());
//        for (DebtorsTransactionSettlement debitSettlement : debitSettlements) {
//
//            if (unallocatedCredit.compareTo(BigDecimal.ZERO) == 0) {
//                //Unallocated credit consumed.  Ensure that transaction does not have anything allocated
//                debitSettlement.setDebitAmountSettled(BigDecimal.ZERO);
//                updateDebitBalance(debitSettlement);
//
//                super.update(debitSettlement, userData);
//            }
//
//            if (unallocatedCredit.compareTo(debitSettlement.getDebit()) > 0) {
//                //Unallocated credits can consume debit
//                debitSettlement.setDebitAmountSettled(debitSettlement.getDebit());
//                updateDebitBalance(debitSettlement);
//
//                super.update(debitSettlement, userData);
//
//                unallocatedCredit = unallocatedCredit.subtract(debitSettlement.getDebit());
//                creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(debitSettlement.getDebit()));
//            } else {
//                //Debit > unallocated credit.  Consume credit remaining.
//                debitSettlement.setDebitAmountSettled(unallocatedCredit);
//                updateDebitBalance(debitSettlement);
//
//                creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(unallocatedCredit));
//
//                super.update(debitSettlement, userData);
//
//                unallocatedCredit = BigDecimal.ZERO;
//            }
//        }
//
//        updateCreditBalance(creditSettlement);
    }

    /**Called when a debit is unticked. */
    private void debitUnticked(DebtorsTransactionSettlement settlement, EMCUserData userData) throws EMCEntityBeanException {
        //Check credits are ticked
        List<DebtorsTransactionSettlement> creditSettlements = getTickedCredits(settlement.getSessionId(), userData);

        if (creditSettlements.size() == 1) {
            //If only one credit is ticked, update its balance
            DebtorsTransactionSettlement creditSettlement = creditSettlements.get(0);

            if (creditSettlement.getCreditAmountSettled().compareTo(settlement.getDebitAmountSettled()) >= 0) {
                creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().subtract(settlement.getDebitAmountSettled()));
                updateCreditBalance(settlement);

                update(creditSettlement, userData);
            }
        }

        settlement.setDebitBalance(settlement.getDebit());
        settlement.setDebitAmountSettled(BigDecimal.ZERO);
        settlement.setDiscAvail(BigDecimal.ZERO);
        settlement.setDiscTaken(BigDecimal.ZERO);
        settlement.setRebate(BigDecimal.ZERO);
        settlement.setUserChangedAmount(false);
    }

    /**Called when a credit is unticked. */
    private void creditUnticked(DebtorsTransactionSettlement settlement, EMCUserData userData) throws EMCEntityBeanException {
        //Check credits are ticked
        List<DebtorsTransactionSettlement> debitSettlements = getTickedDebits(settlement.getSessionId(), userData);

        if (debitSettlements.size() == 1) {
            //If only one debit is ticked, update its balance
            DebtorsTransactionSettlement debitSettlement = debitSettlements.get(0);

            if (debitSettlement.getDebitAmountSettled().compareTo(settlement.getCredit()) >= 0) {
                debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().subtract(settlement.getCreditAmountSettled()));
                updateDebitBalance(debitSettlement);

                update(debitSettlement, userData);
            }
        }

        settlement.setCreditBalance(settlement.getCredit());
        settlement.setCreditAmountSettled(BigDecimal.ZERO);
        settlement.setUserChangedAmount(false);

        //Calculate discounts
        Date newestCreditDate = getNewestCreditDate(settlement.getSessionId(), settlement.getTransactionDate(), userData);
        DebtorsParameters params = parametersBean.getDebtorsParameters(userData);
        if (params == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
        }
        
        for (DebtorsTransactionSettlement debitSettlement : debitSettlements) {
            debitSettlement.setDiscAvail(calculateDiscAvailable(debitSettlement, newestCreditDate, params.getDiscountToleranceDays(), userData));
            allocateDiscount(settlement, false, userData);
            super.update(debitSettlement, userData);
        }
    }

    /** Updates debit balance. */
    private void updateDebitBalance(DebtorsTransactionSettlement settlement) throws EMCEntityBeanException {
        settlement.setDebitBalance(settlement.getDebit().subtract(settlement.getDebitAmountSettled()).subtract(settlement.getDiscTaken()).subtract(settlement.getRebate()));

        if (settlement.getDebitBalance().compareTo(BigDecimal.ZERO) < 0) {
            //Negative balance, throw exception.
            throw new EMCEntityBeanException("Over-allocation is not allowed.");
        }
    }

    /** Updates credit balance. */
    private void updateCreditBalance(DebtorsTransactionSettlement settlement) throws EMCEntityBeanException {
        settlement.setCreditBalance(settlement.getCredit().subtract(settlement.getCreditAmountSettled()));

        if (settlement.getCreditBalance().compareTo(BigDecimal.ZERO) < 0) {
            //Negative balance, throw exception.
            throw new EMCEntityBeanException("Over-allocation is not allowed.");
        }
    }

    /**
     * This method is used to populate the DebtorsTransactionSettlement table
     * with data for the active user.  Records can be identified using a
     * combination of the session id returned by this method, and a user id.
     *
     * @param customerId Customer id for which records should be created.
     * @param userData User data.
     * @return The session id for the records that were created.
     * @throws EMCEntityBeanException
     */
    public long populateSettlement(String customerId, EMCUserData userData) throws EMCEntityBeanException {
        long sessionId = System.currentTimeMillis();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
        query.addAnd("customerId", customerId);

        //Only select on open transactions
        EMCQuery openTransQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        openTransQuery.addField("debtorsTransRef");
        openTransQuery.addAnd("customerId", customerId);

        query.addAnd("recordID", openTransQuery, EMCQueryConditions.IN);

        List<DebtorsTransactions> openTrans = (List<DebtorsTransactions>) util.executeGeneralSelectQuery(query, userData);

        //Detach transactions
        util.detachEntities(userData);

        for (DebtorsTransactions transaction : openTrans) {
            DebtorsTransactionSettlement settlement = new DebtorsTransactionSettlement();
            settlement.setCredit(transaction.getCredit().subtract(transaction.getCreditAmountSettled()));
            settlement.setCreditBalance(settlement.getCredit());
            settlement.setDebit(transaction.getDebit().subtract(transaction.getDebitAmountSettled()));
            settlement.setDebitBalance(settlement.getDebit());
            settlement.setDebtorsTransRef(transaction.getRecordID());
            settlement.setReferenceNumber(transaction.getReferenceNumber());
            settlement.setReferenceType(transaction.getReferenceType());
            settlement.setSessionId(sessionId);
            settlement.setTransactionDate(transaction.getTransactionDate());
            settlement.setTransVersionNumber(transaction.getVersion());
            settlement.setCustomerOrderNumber(transaction.getCustomerOrderNumber());

            settlement.setSettlementDiscDate(transaction.getSettlementDiscDate());
            settlement.setSettlementDiscPercentage(transaction.getSettlementDiscPercentage());

            //settlement.setDiscAvail(calculateDiscAvailable(settlement, null, userData));
            //allocateDiscount(settlement, false, userData);
            settlement.setOriginalCredit(transaction.getCredit());

            settlement.setCreatedDate(Functions.nowDate());
            settlement.setCreatedTime(Functions.nowDate());
            if (isSystemTable(DebtorsTransactionSettlement.class.getName())) {
                settlement.setCompanyId(emc.constants.systemConstants.defaultCompanyId());
            } else {
                settlement.setCompanyId(userData.getCompanyId());
            }

            settlement.setCreatedBy(userData.getUserName());

            doInsert(settlement, userData);
        }

        return sessionId;
    }

    /** Calculates and returns the debitSettlement discount available for the specified transaction.
     *  The settlement discount is calculated against the newest credit allocated to debit(s) on
     *  this settlement.  If newestCreditDate is null, the max discount available will be returned.
     */
    private BigDecimal calculateDiscAvailable(
            DebtorsTransactionSettlement debitSettlement, Date newestCreditDate, int toleranceDays, EMCUserData userData) {
        if (DebtorsTransactionRefTypes.fromString(debitSettlement.getReferenceType()) == DebtorsTransactionRefTypes.INVOICE) {
            BigDecimal discountPercentage = debitSettlement.getSettlementDiscPercentage();
            Date discountDate = debitSettlement.getSettlementDiscDate();

            if (discountPercentage == null || discountDate == null) {
                return BigDecimal.ZERO;
            }

            if (newestCreditDate == null) {
                return debitSettlement.getDebit().multiply(discountPercentage.divide(new BigDecimal(100)));
            } else {
                newestCreditDate = new Date(dateHandler.addToDate(newestCreditDate, -1 * toleranceDays));
                if (newestCreditDate.compareTo(discountDate) <= 0) {
                    //If transaction date is before discount expiry date, allow discount
                    //Check whether full discount will cover balance.  If so, allow full
                    BigDecimal fullDiscount = debitSettlement.getDebit().multiply(discountPercentage.divide(new BigDecimal(100)));
                    if (fullDiscount.compareTo(debitSettlement.getDebitBalance()) >= 0) {
                        return debitSettlement.getDebitBalance();
                    } else {
                        //Allow partial discounts
                        return debitSettlement.getDebitAmountSettled().multiply(discountPercentage.divide(new BigDecimal(100)));
                    }
                } else {
                    return BigDecimal.ZERO;
                }
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    /** Checks whether a discount can be allocated to a settlement record.  If a discount can be allocated, this method sets the discount taken on the specified record.
     *  Returns a boolean indicating whether a discount was allocated.
     */
    private boolean allocateDiscount(DebtorsTransactionSettlement settlement, boolean ticked, EMCUserData userData) throws EMCEntityBeanException {
        if (settlement.isUserChangedAmount() || (ticked && settlement.getDiscTaken().compareTo(BigDecimal.ZERO) > 0)) {
            return false;
        } else if (settlement.getDebitAmountSettled().compareTo(settlement.getDebit()) == 0) {
            //Allocate full discount and subtract from allocated amount
            settlement.setDiscTaken(settlement.getDiscAvail());

            BigDecimal settled = settlement.getDebitAmountSettled();

            settlement.setDebitAmountSettled(settlement.getDebitAmountSettled().subtract(settlement.getDiscTaken()));

            BigDecimal diff = settlement.getDebitAmountSettled().subtract(settled);

            debitAmountChanged(settlement, diff, userData);

            return true;
        } else if (settlement.getDebitBalance().compareTo(settlement.getDiscAvail()) <= 0) {
            //Allocate discount.  Do not update allocated amount.
            settlement.setDiscTaken(settlement.getDebitBalance());
            //Balance should be zero, as entire balance is discounted
            settlement.setDebitBalance(BigDecimal.ZERO);

            return true;
        } else {
            //Clear discount
            settlement.setDiscTaken(BigDecimal.ZERO);
            return false;
        }
    }

    /**
     * Clears debitSettlement records for the specified session.
     * @param sessionId Session id.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean clearSettlement(long sessionId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd(
                "sessionId", sessionId);

        List<DebtorsTransactionSettlement> records = (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);

        for (DebtorsTransactionSettlement settlement : records) {
            delete(settlement, userData);
        }

        return true;
    }

    /**
     * Posts a debitSettlement.
     * @param sessionId The session id for which debitSettlement records should be allocated.
     * @param customerId Customer id.  Should be unique per session.  This is used when creating a discount transaction.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean allocateSettlement(long sessionId, String customerId, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = transLogicBean.allocateSettlement(sessionId, customerId, userData);

        if (ret) {
            clearSettlement(sessionId, userData);
        }

        return ret;
    }

    /**
     * Returns a List containing ticked debit transactions for the specified session.
     * @param sessionId Session id.
     * @param userData User data
     * @return A List containing ticked debit transactions for the specified session.
     */
    private List<DebtorsTransactionSettlement> getTickedDebits(long sessionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd(
                "sessionId", sessionId);
        query.addAnd(
                "tick", true);
        query.addAnd(
                "debit", 0, EMCQueryConditions.GREATER_THAN);

        return (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);
    }

    /**
     * Returns a List containing ticked credit transactions for the specified session.
     * @param sessionId Session id.
     * @param userData User data
     * @return A List containing ticked credit transactions for the specified session.
     */
    private List<DebtorsTransactionSettlement> getTickedCredits(long sessionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addAnd(
                "sessionId", sessionId);
        query.addAnd(
                "tick", true);
        query.addAnd(
                "credit", 0, EMCQueryConditions.GREATER_THAN);

        return (List<DebtorsTransactionSettlement>) util.executeGeneralSelectQuery(query, userData);
    }

    /** Called when the amount settled on a debit is changed. */
    private void debitAmountChanged(DebtorsTransactionSettlement debitSettlement, BigDecimal diff, EMCUserData userData) throws EMCEntityBeanException {
        //If the amount can be changed, there should be one or no ticked credit transactions.
        List<DebtorsTransactionSettlement> creditSettlements = getTickedCredits(debitSettlement.getSessionId(), userData);

        if (creditSettlements.size() > 0) {
            for (DebtorsTransactionSettlement creditSettlement : creditSettlements) {
                if (diff.compareTo(BigDecimal.ZERO) < 0) {
                    //Negative
                    if ((creditSettlement.getCreditAmountSettled().add(diff)).compareTo(BigDecimal.ZERO) > 0) {
                        creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(diff));
                        updateCreditBalance(creditSettlement);
                        break;
                    } else {
                        diff = diff.add(creditSettlement.getCreditAmountSettled());
                        creditSettlement.setCreditAmountSettled(BigDecimal.ZERO);
                    }
                } else {
                    //Positive
                    if ((creditSettlement.getCreditAmountSettled().add(diff)).compareTo(creditSettlement.getCredit()) <= 0) {
                        creditSettlement.setCreditAmountSettled(creditSettlement.getCreditAmountSettled().add(diff));
                        updateCreditBalance(creditSettlement);
                        break;
                    } else {
                        diff = diff.subtract(creditSettlement.getCredit());
                        creditSettlement.setCreditAmountSettled(creditSettlement.getCredit());
                    }
                }
            }
        }

        updateDebitBalance(debitSettlement);
    }

    /** Called when the amount settled on a credit is changed. */
    private void creditAmountChanged(DebtorsTransactionSettlement creditSettlement, DebtorsTransactionSettlement persisted, EMCUserData userData) throws EMCEntityBeanException {
        //If the amount can be changed, there should be one or no ticked debit transactions.
        List<DebtorsTransactionSettlement> debitSettlements = getTickedDebits(creditSettlement.getSessionId(), userData);

        if (debitSettlements.size() > 0) {
            //Difference between new and old settled amounts.
            BigDecimal diff = creditSettlement.getCreditAmountSettled().subtract(persisted.getCreditAmountSettled());

            DebtorsTransactionSettlement debitSettlement = debitSettlements.get(0);
            debitSettlement.setDebitAmountSettled(debitSettlement.getDebitAmountSettled().add(diff));
            updateDebitBalance(debitSettlement);
        }

        updateCreditBalance(creditSettlement);
    }

    /** Returns the DebtorsOpenTransactions with the specified reference. */
    private DebtorsOpenTransactions getTransaction(long debtorsTransRef, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsOpenTransactions.class);
        query.addAnd("debtorsTransRef", debtorsTransRef);

        return (DebtorsOpenTransactions) util.executeSingleResultQuery(query, userData);
    }

    /** Returns the date of the newest ticked credit.  This is used when calculating discounts. */
    private Date getNewestCreditDate(long sessionId, Date lessThanDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addFieldAggregateFunction("transactionDate", "MAX");
        query.addAnd("sessionId", sessionId);
        query.addAnd("tick", true);
        query.addAnd("credit", 0, EMCQueryConditions.GREATER_THAN);

        //This is used when a credit is unticked, to ensure that it is excluded from the selection.
        if (lessThanDate != null) {
            query.addAnd("transactionDate", lessThanDate, EMCQueryConditions.LESS_THAN);
        }

        return (Date) util.executeSingleResultQuery(query, userData);
    }

    /** Checks whether a user is allowed to tick a settlement record. */
    private boolean checkTickAllowed(DebtorsTransactionSettlement settlement, EMCUserData userData) {
        long sessionId = settlement.getSessionId();

        //Only check if multiple records are ticked on other side.
        if (settlement.getCredit().compareTo(BigDecimal.ZERO) > 0) {
            if (getTickedDebits(sessionId, userData).size() > 1) {
                if (getTickedCredits(sessionId, userData).size() > 0) {
                    logMessage(Level.SEVERE, "You may not tick multiple credits when multiple debits are ticked", userData);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            if (getTickedCredits(sessionId, userData).size() > 1) {
                if (getTickedDebits(sessionId, userData).size() > 0) {
                    logMessage(Level.SEVERE, "You may not tick multiple debits when multiple credits are ticked", userData);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    /**
     * Returns the total of ticked credit transactions for the specified session.
     * @param sessionId Session id.
     * @param userData User data
     * @return The total of ticked credit transactions for the specified session..
     */
    private BigDecimal getCreditAllocated(long sessionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addFieldAggregateFunction("creditAmountSettled", "SUM");
        query.addAnd("sessionId", sessionId);
        query.addAnd("tick", true);
        query.addAnd("credit", 0, EMCQueryConditions.GREATER_THAN);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        if (total == null) {
            total = BigDecimal.ZERO;
        }

        return total;
    }

    /**
     * Returns the total of ticked debit transactions for the specified session.
     * @param sessionId Session id.
     * @param userData User data
     * @return The total of ticked debit transactions for the specified session..
     */
    private BigDecimal getDebitAllocated(long sessionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        query.addFieldAggregateFunction("debitAmountSettled", "SUM");
        query.addAnd("sessionId", sessionId);
        query.addAnd("tick", true);
        query.addAnd("debit", 0, EMCQueryConditions.GREATER_THAN);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        if (total == null) {
            total = BigDecimal.ZERO;
        }

        return total;
    }

    /** Returns the specified line. */
    private DebtorsTransactionSettlement getSettlement(long sessionId, String source, String customerOrderNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactionSettlement.class);
        if (!isBlank(source)) {
            query.addAnd("transactionSource", source);
        }

        if (!isBlank(customerOrderNumber)) {
            query.addAnd("customerOrderNumber", customerOrderNumber);
        }

        query.addAnd("sessionId", sessionId);
        //Only select debits
        query.addAnd("debit", 0, EMCQueryConditions.GREATER_THAN);

        return (DebtorsTransactionSettlement) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Imports data in the specified list to the specified allocation session.
     * @param allocationImport Allocation import record.
     * @param sessionId Allocation session id.
     * @param importData Data to import.  The first line should contain column headings.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean importSettlement(DebtorsAllocationImport allocationImport, long sessionId, List<String> importData, EMCUserData userData) throws EMCEntityBeanException {
        if (importData.isEmpty()) {
            return false;
        }

        List<String> columnNames = Arrays.asList(importData.remove(0).split(","));

        Map<String, List<MappingHelper>> mappingHelpers = getMappingHelpers(allocationImport.getCustomerId(), columnNames, userData);

        for (String line : importData) {
            //Temporary settlement info map.  This stores data until we have enough info
            //to fetch the correct record.
            Map<String, Object> tempSettlement = new HashMap<String, Object>();

            String[] lineData = line.split(",");

            for (int i = 0; i < columnNames.size(); i++) {
                if (mappingHelpers.containsKey(columnNames.get(i))) {
                    for (MappingHelper helper : mappingHelpers.get(columnNames.get(i))) {
                        if (helper.checkCondition(columnNames, lineData, userData)) {
                            try {
                                String fieldName = helper.allocationField;
                                Object value = null;

                                Field field = DebtorsTransactionSettlement.class.getDeclaredField(fieldName);

                                if (field.getType() == BigDecimal.class) {
                                    //Always use positive values.
                                    value = new BigDecimal(lineData[i]).abs();
                                } else {
                                    value = lineData[i];
                                }

                                tempSettlement.put(helper.allocationField, value);

                                break;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            //Continue loop.
                            }
                        }
                    }
                }
            }

            DebtorsTransactionSettlement actualSettlement = getSettlement(sessionId, (String) tempSettlement.get("transactionSource"), (String) tempSettlement.get("customerOrderNumber"), userData);

            if (actualSettlement != null) {
                BigDecimal debitSettled = (BigDecimal) tempSettlement.get("debitAmountSettled");
                BigDecimal discTaken = (BigDecimal) tempSettlement.get("discTaken");
                BigDecimal rebate = (BigDecimal) tempSettlement.get("rebate");

                actualSettlement.setTick(true);
                actualSettlement.setUserChangedAmount(true);

                if (debitSettled != null) {
                    actualSettlement.setDebitAmountSettled(actualSettlement.getDebitAmountSettled().add(debitSettled));
                }

                if (discTaken != null) {
                    actualSettlement.setDiscTaken(actualSettlement.getDiscTaken().add(discTaken));
                }

                if (rebate != null) {
                    actualSettlement.setRebate(actualSettlement.getRebate().add(rebate));
                }

                try {
                    this.update(actualSettlement, userData);
                } catch (Exception ex) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.TRANS_ALLOC_FAILED, userData, actualSettlement.getReferenceNumber());
                    ex.printStackTrace();

                    logFailedImportLine(allocationImport.getImportCode(), line, tempSettlement, userData);

                    continue;
                }
            } else {
                logFailedImportLine(allocationImport.getImportCode(), line, tempSettlement, userData);
            }
        }

        allocationImport.setImportDate(Functions.nowDate());

        allocationImportBean.update(allocationImport, userData);

        return true;
    }

    /**
     * Writes a failed import line to the log table.
     * @param importCode Import code.
     * @param line Line to write to log.
     * @param tempSettlement Map containing values on the failed line.
     * @param userData User data.
     * @throws emc.framework.EMCEntityBeanException
     */
    private void logFailedImportLine(String importCode, String line, Map<String, Object> tempSettlement, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsAllocationImportFailLog failLog = new DebtorsAllocationImportFailLog();

        if (tempSettlement.get("debitAmountSettled") != null) {
            failLog.setBalance((BigDecimal) tempSettlement.get("debitAmountSettled"));
        }

        failLog.setImportCode(importCode);
        failLog.setTransReference((String) tempSettlement.get("referenceNumber"));
        failLog.setLine(line);

        allocationImportFailLogBean.insert(failLog, userData);
    }

    private Map<String, List<MappingHelper>> getMappingHelpers(String customerId, List<String> importFields, EMCUserData userData) {
        Map<String, List<MappingHelper>> mappingHelpers = new HashMap<String, List<MappingHelper>>();

        Map<String, List<DebtorsAllocationImportSetupLines>> mapping = importSetupMasterBean.getCustomerMapping(customerId, userData);

        for (String importField : importFields) {
            if (!isBlank(importField)) {
                mappingHelpers.put(importField, new ArrayList<MappingHelper>());
            }
        }

        for (String field : mapping.keySet()) {
            for (DebtorsAllocationImportSetupLines setupLine : mapping.get(field)) {
                if (mappingHelpers.containsKey(setupLine.getSpreadsheetMapping())) {
                    mappingHelpers.get(setupLine.getSpreadsheetMapping()).add(new MappingHelper(setupLine, field));
                }
            }
        }

        return mappingHelpers;
    }

    /**
     * This class assists with mapping field values.
     */
    private class MappingHelper {

        private DebtorsAllocationImportSetupLines setupLine;
        protected String allocationField;

        /** Creates a new instance of MappingHelper. */
        public MappingHelper(DebtorsAllocationImportSetupLines setupLine, String allocationField) {
            this.setupLine = setupLine;
            this.allocationField = allocationField;
        }

        /**
         * Returns a boolean indicating whether the specified field matches the
         * condition on the DebtorsAllocationImportSetupLines instance belonging
         * to this MappingHelper instance.
         *
         * @param value Value to check.
         * @return A boolean indicating whether the condition is met.
         */
        public boolean checkCondition(List<String> columnNames, String[] lineData, EMCUserData userData) {
            try {
                DebtorsAllocationImportSetupConditions condition = DebtorsAllocationImportSetupConditions.fromString(setupLine.getMapCondition());

                String conditionField = isBlank(this.setupLine.getMapConditionField()) ? this.setupLine.getSpreadsheetMapping() : this.setupLine.getMapConditionField();
                String conditionValue = this.setupLine.getMapConditionValue();
                int conditionFieldIndex = columnNames.indexOf(conditionField);

                //Do not do a check for the 'NONE' condition.
                if (condition != null && condition != DebtorsAllocationImportSetupConditions.NONE) {
                    switch (condition) {
                        case CONTAINS:
                            //Only applies to String values
                            return lineData[conditionFieldIndex].contains(conditionValue);
                        //These conditions only apply to decimal fields.
                        case GREATER_THAN_EQ:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) >= 0;
                        case LESS_THAN_EQ:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) <= 0;
                        case EQUAL:
                            return new BigDecimal(lineData[conditionFieldIndex]).compareTo(new BigDecimal(conditionValue)) == 0;
                    }
                }

                return true;
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to map field - " + this.setupLine.getSpreadsheetMapping() + "." + ex.getMessage(), userData);
                return false;
            }
        }
    }
}