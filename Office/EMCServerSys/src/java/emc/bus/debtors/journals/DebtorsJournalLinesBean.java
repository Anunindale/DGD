/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.journals;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.debtors.journals.DebtorsJournalDirection;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsJournalLines.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsJournalLinesBean extends EMCEntityBean implements DebtorsJournalLinesLocal {

    @EJB
    private BaseJournalDefinitionLocal definitionBean;
    @EJB
    private DebtorsJournalMasterLocal masterBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private SOPCustomersLocal customerBean;

    /** Creates a new instance of DebtorsJournalLinesBean */
    public DebtorsJournalLinesBean() {
    }

    /**
     * Returns a list containing the journal lines for the specified journal.
     * @param journalNumber Journal number.
     * @param userData User data.
     * @return A list containing the journal lines for the specified journal.
     */
    public List<DebtorsJournalLines> getJournalLines(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalLines.class);
        query.addAnd("journalNumber", journalNumber);
        query.addOrderBy("lineNo");

        return (List<DebtorsJournalLines>) util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            DebtorsJournalLines line = (DebtorsJournalLines) theRecord;

            DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(line.getJournalNumber(), userData);

            if (JournalStatus.POSTED == JournalStatus.fromString(master.getJournalStatus())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.JR_ALREADY_POSTED, userData);
                return false;
            }

            if ("customerId".equals(fieldNameToValidate)) {
                this.setLineVATCode(line, userData);
                this.calculateLineTotal(line, definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData), userData);
                return line;
            } else if (fieldNameToValidate.equals("lineAmount")) {
                //Get type
                BaseJournalDefinitionTable definition = definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData);

                //Negate value, if needed.
                if (definition != null) {
                    if (DebtorsJournalDirection.CREDIT.equals(DebtorsJournalDirection.fromString(definition.getMovementDirection()))) {
                        if (line.getRecordID() == 0) {
                            doMovementJournalLineQuantity(line, definition, userData);
                        }
                        if (line.getLineAmount().compareTo(BigDecimal.ZERO) > 0) {
                            Logger.getLogger("emc").log(Level.WARNING, "Line total is not negative and this is a credit journal.", userData);
                        }
                    }
                }

                calculateLineTotal(line, definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData), userData);
                return line;
            } else if (fieldNameToValidate.equals("vatAmount")) {
                calculateLineTotal(line, definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData), userData);
                return line;
            }
        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsJournalLines journalLine = (DebtorsJournalLines) iobject;

        DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(journalLine.getJournalNumber(), userData);

        if (isBlank(journalLine.getVatCode())) {
            this.setLineVATCode(journalLine, userData);
        }

        if (isBlank(journalLine.getContraAccount())) {
            journalLine.setContraAccount(master.getJournalContraAccount());
        }

        if (isBlank(journalLine.getContraType())) {
            journalLine.setContraType(master.getJournalContraType());
        }

        if (isBlank(journalLine.getLineDate())) {
            journalLine.setLineDate(master.getJournalDate());
        }

        if (isBlank(journalLine.getLineDescription())) {
            journalLine.setLineDescription(master.getJournalDescription());
        }

        calculateLineTotal(journalLine, definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData), userData);

        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DebtorsJournalLines journalLine = (DebtorsJournalLines) uobject;
        DebtorsJournalLines persistedLine = (DebtorsJournalLines) util.findDetachedPersisted(journalLine, userData);

        DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(journalLine.getJournalNumber(), userData);

        if (!util.checkObjectsEqual(journalLine.getVatCode(), persistedLine.getVatCode()) || !util.checkObjectsEqual(journalLine.getLineAmount(), persistedLine.getLineAmount())) {
            calculateLineTotal(journalLine, definitionBean.getJournalDefinition(master, Modules.DEBTORS, userData), userData);
        }

        if (master != null && JournalStatus.fromString(master.getJournalStatus()) == JournalStatus.APPROVED) {
            master.setJournalStatus(JournalStatus.CAPTURED.toString());

            masterBean.update(master, userData);
        }

        return super.update(uobject, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            DebtorsJournalLines line = (DebtorsJournalLines) vobject;

            DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(line.getJournalNumber(), userData);

            if (JournalStatus.POSTED == JournalStatus.fromString(master.getJournalStatus())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.JR_ALREADY_POSTED, userData);
                return false;
            }
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            DebtorsJournalLines line = (DebtorsJournalLines) vobject;

            DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(line.getJournalNumber(), userData);

            if (JournalStatus.POSTED == JournalStatus.fromString(master.getJournalStatus())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.JR_ALREADY_POSTED, userData);
                return false;
            }

            if (line.getLineDate().compareTo(master.getJournalDate()) > 0) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INVALID_DATE, userData);
                return false;
            }
        }

        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);

        if (ret) {
            DebtorsJournalLines line = (DebtorsJournalLines) vobject;

            DebtorsJournalMaster master = masterBean.getDebtorsJournalMaster(line.getJournalNumber(), userData);

            if (JournalStatus.POSTED == JournalStatus.fromString(master.getJournalStatus())) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.JR_ALREADY_POSTED, userData);
                return false;
            }
        }

        return ret;
    }

    /**
     * Calculates and sets VAT and total on the line.
     * @param line Line to update.
     * @param userData User data.
     */
    private void calculateLineTotal(DebtorsJournalLines line, BaseJournalDefinitionTable definition, EMCUserData userData) {
        //Ignore VAT on credit lines
        if (line.getLineAmount().compareTo(BigDecimal.ZERO) < 0) {
            line.setLineTotal(line.getLineAmount());
        } else {
            if (definition.isVatApplicable()) {
                double vatPerc = vatCodeBean.getVatPercentage(line.getVatCode(), userData);

                line.setVatAmount(line.getLineAmount().multiply(new BigDecimal(vatPerc / 100)));

            }
            
            line.setLineTotal(line.getLineAmount().add(line.getVatAmount()));
        }
    }

    /** Updates quantity for a credit lines.  */
    private void doMovementJournalLineQuantity(DebtorsJournalLines line, BaseJournalDefinitionTable definition, EMCUserData userData) {
        if (line.getLineAmount().compareTo(BigDecimal.ZERO) > 0) {
            line.setLineAmount(line.getLineAmount().multiply(new BigDecimal(-1)));
        }
    }

    /**
     * Gets the VAT code for the customer on the specified line, and sets it on the line.
     */
    private void setLineVATCode(DebtorsJournalLines journalLine, EMCUserData userData) {
        //Ignore VAT on credit lines
        if (journalLine.getLineAmount().compareTo(BigDecimal.ZERO) < 0) {
            return;
        }

        SOPCustomers customer = customerBean.findCustomer(journalLine.getCustomerId(), userData);

        if (customer != null) {
            journalLine.setVatCode(customer.getVatCode());
        }
    }
}
