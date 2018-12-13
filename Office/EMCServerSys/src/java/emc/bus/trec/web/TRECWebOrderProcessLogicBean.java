/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.web;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.debtors.basket.DebtorsBasketLinesLocal;
import emc.bus.debtors.basket.DebtorsBasketMasterLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceLinesLocal;
import emc.bus.debtors.customerinvoice.DebtorsCustomerInvoiceMasterLocal;
import emc.bus.debtors.journals.DebtorsJournalLinesLocal;
import emc.bus.debtors.journals.DebtorsJournalMasterLocal;
import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.bus.trec.treccards.TRECTrecCardsLinesLocal;
import emc.bus.trec.treccards.TRECTrecCardsMasterLocal;
import emc.datatypes.EMCDataType;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.inventory.InventoryParameters;
import emc.entity.sop.SOPCustomers;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.debtors.DebtorsInvoiceCreditNoteType;
import emc.enums.debtors.basket.BasketStatus;
import emc.enums.debtors.journals.DebtorsJournalType;
import emc.enums.debtors.transactions.DebtorsTransactionRefTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECPrintTypeEnum;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCEmail;
import emc.helpers.trec.TRECWebInvoiceHelper;
import emc.helpers.trec.TRECWebOrderProcessHelper;
import emc.messages.ServerDebtorsMessageEnum;
import emc.server.mailmanager.EMCMailManagerLocal;
import emc.tables.EMCTable;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author stuart
 */
@Stateless
public class TRECWebOrderProcessLogicBean extends EMCBusinessBean implements TRECWebOrderProcessLogicBeanLocal {

    @EJB
    private TRECTrecCardsMasterLocal trecCardMasterBean;
    @EJB
    private TRECTrecCardsLinesLocal trecCardLinesBean;
    @EJB
    private DebtorsBasketMasterLocal basketMasterBean;
    @EJB
    private DebtorsBasketLinesLocal basketLinesBean;
    @EJB
    private SOPPriceArangementsLocal priceArrangmentsBean;
    @EJB
    private DebtorsCustomerInvoiceMasterLocal invoiceMasterBean;
    @EJB
    private DebtorsCustomerInvoiceLinesLocal invoiceLinesBean;
    @EJB
    private DebtorsParametersLocal debtorsParametersBean;
    private SOPCustomers thisCustomer;
    @EJB
    private EMCMailManagerLocal mailManager;
    @EJB
    private DebtorsJournalMasterLocal journalMasterBean;
    @EJB
    private DebtorsTransactionLogicLocal transactionLogicBean;
    @EJB
    private DebtorsJournalLinesLocal journalLinesBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private InventoryParametersLocal inventoryParametersBean;

    @Override
    public TRECWebOrderProcessHelper insertFetchTrecMasterLinesFromWeb(TRECWebOrderProcessHelper helper, Long customerRecordId, String UNNumber, Long trecLineRecId, EMCUserData userData) throws EMCEntityBeanException {
        TRECTrecCardsMaster theMaster = new TRECTrecCardsMaster();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        query.addAnd("recordID", customerRecordId);
        thisCustomer = (SOPCustomers) util.executeSingleResultQuery(query, userData);
        if (thisCustomer != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsMaster.class); //use customer id
            query.addAnd("customerId", thisCustomer.getCustomerId());
            theMaster = (TRECTrecCardsMaster) util.executeSingleResultQuery(query, userData);
            if (theMaster == null) {
                theMaster = new TRECTrecCardsMaster();
                theMaster.setTrecCompanyName(thisCustomer.getCustomerName());
                theMaster.setCustomerId(thisCustomer.getCustomerId());
                if (!isBlank(thisCustomer.getEmergencyNumber())) {
                    theMaster.setEmergencyNumber(thisCustomer.getEmergencyNumber());
                } else {
                    theMaster.setEmergencyNumber(thisCustomer.getTelephoneNumber());
                }
                try {
                    theMaster = (TRECTrecCardsMaster) trecCardMasterBean.insert(theMaster, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server try again later", ex);
                }
            }
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Customer registration incomplete - ");
        }

        query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
        query.addAnd("masterId", theMaster.getMasterId());
        query.addAnd("recordID", trecLineRecId);
        TRECTrecCardsLines line = (TRECTrecCardsLines) util.executeSingleResultQuery(query, userData);
//        TRECTrecCardsLines line = null;
        if (helper.getBasketMaster() == null) {
            TRECWebOrderProcessHelper newHelper;
            if (line != null) {
                newHelper = new TRECWebOrderProcessHelper(theMaster, line);
                newHelper.setProperShippingName(helper.getProperShippingName());
                newHelper.setPackingGroup(helper.getPackingGroup());
                newHelper.setSessionId(helper.getSessionId());
                newHelper.setTrecCardsQuantity(helper.getTrecCardsQuantity());
                return newHelper;
            } else {
                line = new TRECTrecCardsLines();
                line.setMasterId(theMaster.getMasterId());
                line.setUnNumber(UNNumber);
                line.setPreparedBy(theMaster.getTrecCompanyName());
                line.setProperShipping(helper.getProperShippingName());
                line.setPackingGroup(helper.getPackingGroup());
                line.setEmergencyNumber(thisCustomer.getTelephoneNumber());
                try {
                    line = (TRECTrecCardsLines) trecCardLinesBean.insert(line, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server try again later", ex);
                }
                newHelper = new TRECWebOrderProcessHelper(theMaster, line);
                newHelper.setProperShippingName(helper.getProperShippingName());
                newHelper.setPackingGroup(helper.getPackingGroup());
                newHelper.setSessionId(helper.getSessionId());
                newHelper.setTrecCardsQuantity(helper.getTrecCardsQuantity());
                return newHelper;
            }
        } else {
            if (line != null) {
                helper.setTheMaster(theMaster);
                helper.setCurrentLine(line);
                return helper;
            } else {
                helper.setTheMaster(theMaster);
                line = new TRECTrecCardsLines();
                line.setMasterId(theMaster.getMasterId());
                line.setUnNumber(UNNumber);
                line.setPreparedBy(theMaster.getTrecCompanyName());
                line.setProperShipping(helper.getProperShippingName());
                line.setPackingGroup(helper.getPackingGroup());
                line.setEmergencyNumber(thisCustomer.getTelephoneNumber());
                try {
                    line = (TRECTrecCardsLines) trecCardLinesBean.insert(line, userData);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server try again later", ex);
                }
                helper.setCurrentLine(line);
                return helper;
            }
        }
    }

    @Override
    public TRECTrecCardsLines fetchTrecCardLine(Long trecCardRecordId, EMCUserData userData) {

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
        query.addAnd("recordID", trecCardRecordId);
        TRECTrecCardsLines line = (TRECTrecCardsLines) util.executeSingleResultQuery(query, userData);
        if (line != null) {
            return line;
        } else {
            return null;
        }
    }

    @Override
    public Collection getDataInRange(Class type, EMCUserData emcud, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object insert(Object o, EMCUserData emcud) throws EMCEntityBeanException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object delete(Object o, EMCUserData emcud) throws EMCEntityBeanException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getNumRows(Class type, EMCUserData emcud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object update(Object o, EMCUserData emcud) throws EMCEntityBeanException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object validateField(String string, Object o, EMCUserData emcud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DefaultMutableTreeNode testRelations(enumPersistOptions po, EMCTable emct, EMCUserData emcud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean populateNumberSequenceField(Object o, String string, EMCDataType emcdt, EMCUserData emcud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TRECWebOrderProcessHelper updateCardMaster(TRECWebOrderProcessHelper helper, EMCUserData userData) {
        TRECTrecCardsMaster theMaster = helper.getTheMaster();
        try {
            theMaster = (TRECTrecCardsMaster) trecCardMasterBean.update(helper.getTheMaster(), userData);
            helper.setTheMaster(theMaster);
            return helper;
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Customer registration incomplete - ");
            return null;
        }

    }

    @Override
    public TRECTrecCardsLines updateCardLines(TRECTrecCardsLines lines, EMCUserData userData) {
        try {
            lines = (TRECTrecCardsLines) trecCardLinesBean.update(lines, userData);
            TRECTrecCardsLines newLine = fetchTrecCardLine(lines.getRecordID(), userData);
            return newLine;
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to update trec lines.");
            return lines;
        }

    }

    @Override
    public TRECWebOrderProcessHelper createUpdateBasketWeb(TRECWebOrderProcessHelper helper, EMCUserData userData) {
        //create or update basket master
        DebtorsBasketMaster basketMaster = new DebtorsBasketMaster();
        if (helper.getBasketMaster() != null) {
            if (helper.getBasketMaster().getBasketId() == null) {
                basketMaster.setSessionId(helper.getSessionId());
                if (helper.isTreccard()) {
                    basketMaster.setCustomerId(helper.getTheMaster().getCustomerId());
                } else {
                    basketMaster.setCustomerId(helper.getBasketMaster().getCustomerId());
                }
                basketMaster.setStatus(BasketStatus.CREATED.toString());
                try {
                    basketMaster = ((DebtorsBasketMaster) basketMasterBean.insert(basketMaster, userData));
                    helper.setBasketMaster(basketMaster);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server to create Basket try again later", ex);
                }
            } else {
                basketMaster = helper.getBasketMaster();
            }
        } else {
            basketMaster.setSessionId(helper.getSessionId());
            if (helper.isTreccard()) {
                basketMaster.setCustomerId(helper.getTheMaster().getCustomerId());
            } else {
                basketMaster.setCustomerId(helper.getCustomerId());
            }
            basketMaster.setStatus(BasketStatus.CREATED.toString());
            try {
                basketMaster = ((DebtorsBasketMaster) basketMasterBean.insert(basketMaster, userData));
                helper.setBasketMaster(basketMaster);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server to create Basket try again later", ex);
            }
        }



        //create update lines
        DebtorsBasketLines basketLines = new DebtorsBasketLines();
        if (basketMaster != null) {
            basketLines.setBasketId(helper.getBasketMaster().getBasketId());
            //get new line number 
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsBasketLines.class);
            query.addAnd("basketId", basketMaster.getBasketId());
            query.addFieldAggregateFunction("lineNumber", "MAX");
//            List<DebtorsBasketLines> linesList = util.executeGeneralSelectQuery(query, userData);
            Integer lineNumber = (Integer) util.executeSingleResultQuery(query, userData);
//            Integer lineNumber = linesList.size();
            if (lineNumber == null) {
                lineNumber = 0;
            }
            basketLines.setLineNumber(lineNumber + 1);

            if (helper.isTreccard()) {
                DebtorsParameters parm = debtorsParametersBean.getDebtorsParameters(userData);
                if (parm.getDefaultItem() == null) {
                    try {
                        throw new EMCEntityBeanException("Please setup default debtors item");
                    } catch (EMCEntityBeanException ex) {
                        Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
                    }
                }
//                if (helper.getBasketLines() == null) {
//                    basketLines.setItemId(parm.getDefaultItem());
//                } else {
//                    basketLines.setItemId(helper.getBasketLines().getItemId());
//                }
                basketLines.setItemId(parm.getDefaultItem());
                basketLines.setQuantity((BigDecimal) helper.getTrecCardsQuantity());
                basketLines.setTrecCardLink(helper.getCurrentLine().getRecordID());
                basketLines.setDescription(helper.getProperShippingName());
                basketLines.setPrintOption(null);
            } else {

                basketLines.setItemId(helper.getBasketLines().getItemId());
                basketLines.setQuantity((BigDecimal) helper.getBasketLines().getQuantity());
                basketLines.setTrecCardLink(null);
                basketLines.setDimension1(helper.getBasketLines().getDimension1());
                basketLines.setDimension2(helper.getBasketLines().getDimension2());
                basketLines.setDimension3(helper.getBasketLines().getDimension3());
                basketLines.setDescription(helper.getBasketLines().getDescription());
                basketLines.setPrintOption("Not Applicable");
                if (helper.getBasketLines().getPlacardUNNumber() != null) {
                    basketLines.setPlacardUNNumber(helper.getBasketLines().getPlacardUNNumber());
                }
                if (helper.getBasketLines().getOperatorTlNo() != null) {
                    basketLines.setOperatorTlNo(helper.getBasketLines().getOperatorTlNo());
                }
                if (helper.getBasketLines().getSpecialistTelNo() != null) {
                    basketLines.setSpecialistTelNo(helper.getBasketLines().getSpecialistTelNo());
                }

            }


            boolean credit = false;

            DebtorsParameters param = debtorsParametersBean.getDebtorsParameters(userData);

            EMCQuery quer = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            quer.addAnd("customerId", helper.getBasketMaster().getCustomerId());
            SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(quer, userData);

            if (customer != null && param != null && !isBlank(customer.getCreditRating()) && !isBlank(param.getCreditRating()) && customer.getCreditRating().equals(param.getCreditRating())) {
                credit = true;

            }
            if (!credit) {
                if (helper.isTreccard()) {
                    basketLines.setPrintQty(helper.getTrecCardsQuantity().intValue());
                } else {
                    basketLines.setPrintQty(helper.getBasketLines().getQuantity().intValue());
                }
            } else {
                basketLines.setPrintQty(0);

            }


            //get price
            try {
                if (helper.isTreccard()) {
                    BigDecimal price = priceArrangmentsBean.findItemPrice(basketMaster.getCustomerId(), basketLines.getItemId(), null, null, null, dateHandler.nowDate(), helper.getTrecCardsQuantity(), userData);
                    basketLines.setPrice(price);
                } else {
                    BigDecimal price = priceArrangmentsBean.findItemPrice(basketMaster.getCustomerId(), basketLines.getItemId(), helper.getBasketLines().getDimension1(), helper.getBasketLines().getDimension2(), helper.getBasketLines().getDimension3(), dateHandler.nowDate(), helper.getBasketLines().getQuantity(), userData);
                    basketLines.setPrice(price);
                }
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error: Could not find price", ex);
            }
            //get vat
            DebtorsCustomerInvoiceMaster invoiceMaster = new DebtorsCustomerInvoiceMaster();
            invoiceMaster.setCustomerNo(basketMaster.getCustomerId());
            invoiceMaster.setInvoiceDate(dateHandler.nowDate());
            invoiceMaster.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
            // invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.validateField("customerNo", invoiceMaster, userData);

            invoiceMaster.setDeliveryAddress1(helper.getBasketMaster().getPhysicalAddresLine1());
            invoiceMaster.setDeliveryAddress2(helper.getBasketMaster().getPhysicalAddresLine2());
            invoiceMaster.setDeliveryAddress3(helper.getBasketMaster().getPhysicalAddresLine3());
            invoiceMaster.setDeliveryAddress4(helper.getBasketMaster().getPhysicalAddresLine4());
            invoiceMaster.setDeliveryAddress5(helper.getBasketMaster().getPhysicalAddresLine5());
            invoiceMaster.setDeliveryAddressPostalCode(helper.getBasketMaster().getPhysicalPostalCode());
            invoiceMaster.setVatCode(customer.getVatCode());
            //insert invoiceMaster when basket is posted

            DebtorsCustomerInvoiceLines invoiceLines = new DebtorsCustomerInvoiceLines();
            //invoiceLines.setInvCNNumber(invoiceMaster.getInvCNNumber());
            invoiceLines.setItemId(basketLines.getItemId());
            invoiceLines.setUnitPrice(basketLines.getPrice());
            if (helper.isTreccard()) {
                invoiceLines.setDimension1(null);
                invoiceLines.setDimension2(null);
                invoiceLines.setDimension3(null);
            } else {
                invoiceLines.setDimension1(basketLines.getDimension1());
                invoiceLines.setDimension2(basketLines.getDimension2());
                invoiceLines.setDimension3(basketLines.getDimension3());
            }
            invoiceLines.setQuantity(basketLines.getQuantity());
            try {
                invoiceLinesBean.calculateLineTotal(invoiceLines, invoiceMaster, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error: Error Calculating Line Totals", ex);
            }
            basketLines.setVat(invoiceLines.getVatAmount());
            basketLines.setTotalPrice(invoiceLines.getTotalCost());

            try {
                basketLines = ((DebtorsBasketLines) basketLinesBean.insert(basketLines, userData));
                helper.setBasketLines(basketLines);
                helper.getBasketLinesList().add(basketLines);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
            }
            return (helper);

        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Server communication error please try again later");
            return null;
        }

    }

    @Override
    public TRECWebOrderProcessHelper updateBasketLine(TRECWebOrderProcessHelper helper, DebtorsBasketLines newLine, EMCUserData userData) {
        //create or update basket master
        Integer lineNo = newLine.getLineNumber();
        if (lineNo == null) {
            // lineNo =0; 
        } else {
            if (lineNo == 0) {
            }
        }

        DebtorsBasketMaster basketMaster = new DebtorsBasketMaster();
        if (helper.getBasketMaster() != null) {
            if (helper.getBasketMaster().getBasketId() == null) {
                basketMaster.setSessionId(helper.getSessionId());
                if (helper.isTreccard()) {
                    basketMaster.setCustomerId(helper.getTheMaster().getCustomerId());
                } else {
                    basketMaster.setCustomerId(helper.getBasketMaster().getCustomerId());
                }
                basketMaster.setStatus(BasketStatus.CREATED.toString());
                try {
                    basketMaster = ((DebtorsBasketMaster) basketMasterBean.insert(basketMaster, userData));
                    helper.setBasketMaster(basketMaster);
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server to create Basket try again later", ex);
                }
            } else {
                basketMaster = helper.getBasketMaster();
            }
        } else {
            basketMaster.setSessionId(helper.getSessionId());
            if (helper.isTreccard()) {
                basketMaster.setCustomerId(helper.getTheMaster().getCustomerId());
            } else {
                basketMaster.setCustomerId(helper.getCustomerId());
            }
            basketMaster.setStatus(BasketStatus.CREATED.toString());
            try {
                basketMaster = ((DebtorsBasketMaster) basketMasterBean.insert(basketMaster, userData));
                helper.setBasketMaster(basketMaster);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server to create Basket try again later", ex);
            }
        }



        //create update lines
        DebtorsBasketLines basketLines = new DebtorsBasketLines();
        if (basketMaster != null) {
            basketLines.setBasketId(helper.getBasketMaster().getBasketId());
            //get new line number 
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsBasketLines.class);
            query.addAnd("basketId", basketMaster.getBasketId());
            query.addFieldAggregateFunction("lineNumber", "MAX");

            Integer lineNumber = (Integer) util.executeSingleResultQuery(query, userData);
            if (lineNumber == null) {
                lineNumber = 0;
            }
            basketLines.setLineNumber(lineNumber);
            basketLines.setItemId(newLine.getItemId());
            basketLines.setPrintOption(newLine.getPrintOption());

            basketLines.setQuantity((BigDecimal) helper.getTrecCardsQuantity());

            if (lineNo != null) {
                basketLines.setDescription(newLine.getDescription());
                basketLines.setTrecCardLink(newLine.getTrecCardLink());
            } else {
                basketLines.setDescription(helper.getProperShippingName());
                basketLines.setTrecCardLink(helper.getCurrentLine().getRecordID());
            }



            boolean credit = false;

            DebtorsParameters param = debtorsParametersBean.getDebtorsParameters(userData);

            EMCQuery quer = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            quer.addAnd("customerId", helper.getBasketMaster().getCustomerId());
            SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(quer, userData);

            if (customer != null && param != null && !isBlank(customer.getCreditRating()) && !isBlank(param.getCreditRating()) && customer.getCreditRating().equals(param.getCreditRating())) {
                credit = true;

            }
            if (!credit) {
                if (helper.isTreccard()) {
                    basketLines.setPrintQty(helper.getTrecCardsQuantity().intValue());
                } else {
                    basketLines.setPrintQty(helper.getBasketLines().getQuantity().intValue());
                }
            } else {
                basketLines.setPrintQty(0);

            }


            //get price
            try {

                BigDecimal price = priceArrangmentsBean.findItemPrice(basketMaster.getCustomerId(), basketLines.getItemId(), null, null, null, dateHandler.nowDate(), helper.getTrecCardsQuantity(), userData);
                basketLines.setPrice(price);

            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error: Could not find price", ex);
            }
            //get vat
            DebtorsCustomerInvoiceMaster invoiceMaster = new DebtorsCustomerInvoiceMaster();
            invoiceMaster.setCustomerNo(basketMaster.getCustomerId());
            invoiceMaster.setInvoiceDate(dateHandler.nowDate());
            invoiceMaster.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
            // invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.validateField("customerNo", invoiceMaster, userData);

            invoiceMaster.setDeliveryAddress1(helper.getBasketMaster().getPhysicalAddresLine1());
            invoiceMaster.setDeliveryAddress2(helper.getBasketMaster().getPhysicalAddresLine2());
            invoiceMaster.setDeliveryAddress3(helper.getBasketMaster().getPhysicalAddresLine3());
            invoiceMaster.setDeliveryAddress4(helper.getBasketMaster().getPhysicalAddresLine4());
            invoiceMaster.setDeliveryAddress5(helper.getBasketMaster().getPhysicalAddresLine5());
            invoiceMaster.setDeliveryAddressPostalCode(helper.getBasketMaster().getPhysicalPostalCode());
            invoiceMaster.setVatCode(customer.getVatCode());
            //insert invoiceMaster when basket is posted

            DebtorsCustomerInvoiceLines invoiceLines = new DebtorsCustomerInvoiceLines();
            //invoiceLines.setInvCNNumber(invoiceMaster.getInvCNNumber());
            invoiceLines.setItemId(basketLines.getItemId());
            invoiceLines.setUnitPrice(basketLines.getPrice());
            if (helper.isTreccard()) {
                invoiceLines.setDimension1(null);
                invoiceLines.setDimension2(null);
                invoiceLines.setDimension3(null);
            } else {
                invoiceLines.setDimension1(basketLines.getDimension1());
                invoiceLines.setDimension2(basketLines.getDimension2());
                invoiceLines.setDimension3(basketLines.getDimension3());
            }
            invoiceLines.setQuantity(basketLines.getQuantity());
            try {
                invoiceLinesBean.calculateLineTotal(invoiceLines, invoiceMaster, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error: Error Calculating Line Totals", ex);
            }
            basketLines.setVat(invoiceLines.getVatAmount());
            basketLines.setTotalPrice(invoiceLines.getTotalCost());

            try {
                if (lineNo != null) {
                    DebtorsBasketLines lineToRemove = null;
                    int index = 0;
                    int lineToDeleteIndex = 0;
                    for (DebtorsBasketLines l : helper.getBasketLinesList()) {
                        if (lineNo == l.getLineNumber()) {
                            lineToRemove = l;
                            lineToDeleteIndex = index;
                        }
                        index = index + 1;
                    }


                    if (lineToRemove != null) {
                        basketLinesBean.delete(lineToRemove, userData);
                        helper.getBasketLinesList().remove(lineToRemove);
                    }

                    basketLines.setLineNumber(lineNo);
                    basketLines = ((DebtorsBasketLines) basketLinesBean.insert(basketLines, userData));
                    helper.setBasketLines(basketLines);
                    helper.getBasketLinesList().add(lineToDeleteIndex, basketLines);

                } else {
                    basketLines = ((DebtorsBasketLines) basketLinesBean.insert(basketLines, userData));
                    helper.setBasketLines(basketLines);
                    helper.getBasketLinesList().add(basketLines);
                }
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
            }
            return (helper);

        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Server communication error please try again later");
            return null;
        }

    }

    @Override
    public boolean updateBasketMaster(TRECWebOrderProcessHelper helper, EMCUserData userData) {
        if (helper.getBasketMaster() != null) {
            try {
                basketMasterBean.update(helper.getBasketMaster(), userData);
                return true;
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to connect to server to update Basket try again later", ex);
            }
        }
        return false;
    }

    @Override
    public TRECWebOrderProcessHelper createDebtorsInvoice(TRECWebOrderProcessHelper helper, EMCUserData userData) throws EMCEntityBeanException {
        boolean credit = false;
        DebtorsParameters param = debtorsParametersBean.getDebtorsParameters(userData);
        DebtorsParameters parm = debtorsParametersBean.getDebtorsParameters(userData);
        InventoryParameters invParameters = null;

        invParameters = inventoryParametersBean.getInventoryParameters(userData);

        if (parm.getDefaultItem() == null) {
            throw new EMCEntityBeanException("Please setup default debtors item");
        }

        if (invParameters.getDefaultReminderItem() == null) {
            throw new EMCEntityBeanException("Please setup default inventory reminder item parameters");
        }

        SOPCustomers customer = null;
        EMCQuery querySubCust = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        querySubCust.addAnd("customerId", helper.getBasketMaster().getCustomerId());
        SOPCustomers subCustomer = (SOPCustomers) util.executeSingleResultQuery(querySubCust, userData);

        if (!isBlank(subCustomer) && !isBlank(subCustomer.getInvoiceToCustomer())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("customerId", subCustomer.getInvoiceToCustomer());
            customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("customerId", helper.getBasketMaster().getCustomerId());
            customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);
        }

        if (customer != null && param != null && !isBlank(customer.getCreditRating()) && !isBlank(param.getCreditRating()) && customer.getCreditRating().equals(param.getCreditRating())) {
            credit = true;
        }

        if (credit) {
            helper.setCredit(credit);

//            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
//            query.addTableAnd(DebtorsJournalMaster.class.getName(), "transactionSource", DebtorsTransactions.class.getName(), "journalNumber");
//            query.addTableAnd(DebtorsJournalLines.class.getName(), "journalNumber", DebtorsJournalMaster.class.getName(), "journalNumber");
//            query.addAnd("customerId", helper.getBasketMaster().getCustomerId(), DebtorsTransactions.class.getName());
//            query.addAnd("journalDefinitionId", param.getCreditJournalDef(), DebtorsJournalMaster.class.getName());
//            query.addFieldAggregateFunction("credit", "SUM");
//            query.addFieldAggregateFunction("debit", "SUM");

            EMCQuery creditQuery = new EMCQuery(enumQueryTypes.SELECT, DebtorsTransactions.class);
            creditQuery.addTableAnd(DebtorsJournalMaster.class.getName(), "transactionSource", DebtorsTransactions.class.getName(), "journalNumber");
            creditQuery.addFieldAggregateFunction("debit", DebtorsTransactions.class.getName(), "SUM");
            creditQuery.addFieldAggregateFunction("credit", DebtorsTransactions.class.getName(), "SUM");
            creditQuery.addAnd("customerId", customer.getCustomerId());
            creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
            creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.DEBIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
            creditQuery.addOr("referenceType", DebtorsTransactionRefTypes.CREDIT_JOURNAL.toString(), EMCQueryConditions.EQUALS);
            creditQuery.closeConditionBracket();
            creditQuery.openConditionBracket(EMCQueryBracketConditions.AND);
            creditQuery.addOr("journalDefinitionId", param.getCreditJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
            creditQuery.addOr("journalDefinitionId", param.getDebitJournalDef(), DebtorsJournalMaster.class.getName(), EMCQueryConditions.EQUALS);
            creditQuery.closeConditionBracket();

            List<Object[]> credAmounts = util.executeGeneralSelectQuery(creditQuery, userData);
            BigDecimal balance = BigDecimal.ZERO;
            BigDecimal debit = BigDecimal.ZERO;
            BigDecimal credi = BigDecimal.ZERO;
            BigDecimal creditAmt = BigDecimal.ZERO;
            if (credAmounts == null) {
                balance = BigDecimal.ZERO;
            }
            for (Object[] cred : credAmounts) {
                if (cred.length > 0) {
                    credi = (BigDecimal) cred[1];
                    if (credi == null) {
                        credi = BigDecimal.ZERO;
                    }
                    debit = (BigDecimal) cred[0];
                    if (debit == null) {
                        debit = BigDecimal.ZERO;
                    }

                    creditAmt = credi.subtract(debit);
                    balance = creditAmt.round(new MathContext(4, RoundingMode.HALF_UP));

                    if (balance.compareTo(BigDecimal.ZERO) < 0) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Your account is in arrears, Please contact customer support", userData);
                    }
                }
            }

            //
            BigDecimal amount = BigDecimal.ZERO;
            BigDecimal totalSum;
            boolean createInvoice = false;
            boolean addReminder = false;
            for (DebtorsBasketLines line : helper.getBasketLinesList()) {
                if (line.getItemId().equalsIgnoreCase(param.getDefaultItem())) {
                    addReminder = true;
                }
            }

            for (DebtorsBasketLines line : helper.getBasketLinesList()) {
                // only calc credits for TREC Cards only

                if (addReminder) {
                    if (line.getItemId().equalsIgnoreCase(param.getDefaultItem()) || line.getItemId().equalsIgnoreCase(invParameters.getDefaultReminderItem())) {
                        totalSum = line.getTotalPrice().add(line.getVat());
                        amount = amount.add(totalSum);
                    } else {
                        createInvoice = true;
                    }
                } else {
                    if (line.getItemId().equalsIgnoreCase(param.getDefaultItem())) {
                        totalSum = line.getTotalPrice().add(line.getVat());
                        amount = amount.add(totalSum);
                    } else {
                        createInvoice = true;
                    }
                }
            }
            if (amount == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find the total price of the order", userData);
                return helper;
            }
            if (balance.compareTo(amount) >= 0) {
                DebtorsJournalMaster journalMaster = createPaymentJournal(helper, param.getDebitJournalDef(), userData);
                helper.setTheJournalMaster(journalMaster);

                if (createInvoice) {
                    //
                    SOPCustomers customer1 = null;
                    EMCQuery querySubCust1 = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                    querySubCust1.addAnd("customerId", helper.getBasketMaster().getCustomerId());
                    SOPCustomers subCustomer1 = (SOPCustomers) util.executeSingleResultQuery(querySubCust1, userData);

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                    if (!isBlank(subCustomer1) && !isBlank(subCustomer1.getInvoiceToCustomer())) {
                        query.addAnd("customerId", subCustomer1.getInvoiceToCustomer());
                    } else {
                        query.addAnd("customerId", helper.getBasketMaster().getCustomerId());
                    }
                    customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);

                    DebtorsCustomerInvoiceMaster invoiceMaster = new DebtorsCustomerInvoiceMaster();
                    invoiceMaster.setCustomerNo(customer.getCustomerId());
                    invoiceMaster.setInvoiceDate(dateHandler.nowDate());
                    invoiceMaster.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
                    invoiceMaster.setSalesRep("LIZET");//?
                    // invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.validateField("customerNo", invoiceMaster, userData);

                    invoiceMaster.setDeliveryAddress1(helper.getBasketMaster().getPhysicalAddresLine1());
                    invoiceMaster.setDeliveryAddress2(helper.getBasketMaster().getPhysicalAddresLine2());
                    invoiceMaster.setDeliveryAddress3(helper.getBasketMaster().getPhysicalAddresLine3());
                    invoiceMaster.setDeliveryAddress4(helper.getBasketMaster().getPhysicalAddresLine4());
                    invoiceMaster.setDeliveryAddress5(helper.getBasketMaster().getPhysicalAddresLine5());
                    invoiceMaster.setDeliveryAddressPostalCode(helper.getBasketMaster().getPhysicalPostalCode());

                    invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.insert(invoiceMaster, userData);


                    if (invoiceMaster != null) {

                        for (DebtorsBasketLines helperBasketLine : helper.getBasketLinesList()) {
                            boolean calc = false;
                            if (addReminder) {
                                if (helperBasketLine.getItemId().equalsIgnoreCase(invParameters.getDefaultReminderItem())) {
                                    // do not add
                                } else {
                                    calc = true;
                                }
                            } else {
                                calc = true;
                            }

                            if (calc) {
                                if (!helperBasketLine.getItemId().equalsIgnoreCase(parm.getDefaultItem())) {
                                    DebtorsCustomerInvoiceLines invoiceLines = new DebtorsCustomerInvoiceLines();

                                    invoiceLines.setItemId(helperBasketLine.getItemId());
                                    invoiceLines.setDimension1(helperBasketLine.getDimension1());
                                    invoiceLines.setDimension2(helperBasketLine.getDimension2());
                                    invoiceLines.setDimension3(helperBasketLine.getDimension3());
                                    //get price
                                    BigDecimal price = new BigDecimal(20.0); //sets it to default
                                    price = priceArrangmentsBean.findItemPrice(invoiceMaster.getCustomerNo(), invoiceLines.getItemId(), invoiceLines.getDimension1(), invoiceLines.getDimension2(), invoiceLines.getDimension3(), dateHandler.nowDate(), helperBasketLine.getQuantity(), userData);
                                    invoiceLines.setInvCNNumber(invoiceMaster.getInvCNNumber());
                                    invoiceLines.setUnitPrice(price);

                                    invoiceLines.setQuantity(helperBasketLine.getQuantity());
                                    invoiceLines.setUom("EA");//?
                                    //insert invoice lines
                                    invoiceLines = (DebtorsCustomerInvoiceLines) invoiceLinesBean.insert(invoiceLines, userData);

                                }
                            }
                        }
                        //post invoice
                        invoiceMasterBean.postInvoice(invoiceMaster.getInvCNNumber(), userData);
                        helper.setTheInvoiceMaster(invoiceMaster);

                    }
                }
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "You do not have enough credit in your account. Please contact support or change order. Available credit: R" + balance, userData);
                return helper;
            }

        }

        if (!credit) {
            helper.setCredit(credit);
            //
            SOPCustomers customer1 = null;
            EMCQuery querySubCust1 = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            querySubCust1.addAnd("customerId", helper.getBasketMaster().getCustomerId());
            SOPCustomers subCustomer1 = (SOPCustomers) util.executeSingleResultQuery(querySubCust1, userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            if (!isBlank(subCustomer1) && !isBlank(subCustomer1.getInvoiceToCustomer())) {
                query.addAnd("customerId", subCustomer1.getInvoiceToCustomer());
            } else {
                query.addAnd("customerId", helper.getBasketMaster().getCustomerId());
            }
            customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);

            DebtorsCustomerInvoiceMaster invoiceMaster = new DebtorsCustomerInvoiceMaster();
            invoiceMaster.setCustomerNo(customer.getCustomerId());
            invoiceMaster.setInvoiceDate(dateHandler.nowDate());
            invoiceMaster.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
            invoiceMaster.setSalesRep("LIZET");//?
            // invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.validateField("customerNo", invoiceMaster, userData);

            invoiceMaster.setDeliveryAddress1(helper.getBasketMaster().getPhysicalAddresLine1());
            invoiceMaster.setDeliveryAddress2(helper.getBasketMaster().getPhysicalAddresLine2());
            invoiceMaster.setDeliveryAddress3(helper.getBasketMaster().getPhysicalAddresLine3());
            invoiceMaster.setDeliveryAddress4(helper.getBasketMaster().getPhysicalAddresLine4());
            invoiceMaster.setDeliveryAddress5(helper.getBasketMaster().getPhysicalAddresLine5());
            invoiceMaster.setDeliveryAddressPostalCode(helper.getBasketMaster().getPhysicalPostalCode());

            
            invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.insert(invoiceMaster, userData);
           

            if (invoiceMaster != null) {
                
                    for (DebtorsBasketLines helperBasketLine : helper.getBasketLinesList()) {
                        DebtorsCustomerInvoiceLines invoiceLines = new DebtorsCustomerInvoiceLines();
//                        DebtorsParameters parm = debtorsParametersBean.getDebtorsParameters(userData);
                        //                if (parm.getDefaultItem() == null) {
                        //                    try {
                        //                        throw new EMCEntityBeanException("Please setup default debtors item");
                        //                    } catch (EMCEntityBeanException ex) {
                        //                        Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
                        //                    }
                        //                invoiceLines.setItemId(parm.getDefaultItem());
                        //                invoiceLines.setItemId(parm.getDefaultItem());

                        invoiceLines.setItemId(helperBasketLine.getItemId());
                        invoiceLines.setDimension1(helperBasketLine.getDimension1());
                        invoiceLines.setDimension2(helperBasketLine.getDimension2());
                        invoiceLines.setDimension3(helperBasketLine.getDimension3());
                        //get price
                        BigDecimal price = new BigDecimal(20.0); //sets it to default
                       
                        price = priceArrangmentsBean.findItemPrice(invoiceMaster.getCustomerNo(), invoiceLines.getItemId(), invoiceLines.getDimension1(), invoiceLines.getDimension2(), invoiceLines.getDimension3(), dateHandler.nowDate(), helperBasketLine.getQuantity(), userData);
                       
                        invoiceLines.setInvCNNumber(invoiceMaster.getInvCNNumber());
                        invoiceLines.setUnitPrice(price);

                        invoiceLines.setQuantity(helperBasketLine.getQuantity());
                        invoiceLines.setUom("EA");//?
                            //insert invoice lines
                        invoiceLines = (DebtorsCustomerInvoiceLines) invoiceLinesBean.insert(invoiceLines, userData);

                    }
                    //post invoice
                    invoiceMasterBean.postInvoice(invoiceMaster.getInvCNNumber(), userData);
                    helper.setTheInvoiceMaster(invoiceMaster);
               
            }
        }

        return helper;


    }

    @Override
    public TRECWebOrderProcessHelper createUpdateDebtorsLines(TRECWebOrderProcessHelper helper, EMCUserData userData) {
        TRECTrecCardsLines savedLine = new TRECTrecCardsLines();
        if (helper.getCurrentLine() != null) {
            //find if current line is persisting
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
            query.addAnd("recordID", helper.getCurrentLine().getRecordID());
            savedLine = (TRECTrecCardsLines) util.executeSingleResultQuery(query, userData);
            if (savedLine == null) {
                try {
                    savedLine = (TRECTrecCardsLines) trecCardLinesBean.insert(helper.getCurrentLine(), userData);
                    helper.setCurrentLine(savedLine);
                    return helper;
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
                    return null;
                }
            } else {
                try {
                    savedLine = (TRECTrecCardsLines) trecCardLinesBean.update(savedLine, userData);
                    helper.setCurrentLine(savedLine);
                    return helper;
                } catch (EMCEntityBeanException ex) {
                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
                    return null;
                }
            }

        } else {
            return null;
        }

    }

    @Override
    public boolean deleteBasketLine(TRECWebInvoiceHelper line, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsBasketLines.class);
        query.addAnd("basketId", line.getBasketId());
        query.addAnd("lineNumber", line.getLineNumber());
        DebtorsBasketLines lineToDelete = (DebtorsBasketLines) util.executeSingleResultQuery(query, userData);
        if (lineToDelete != null) {
            try {
                basketLinesBean.delete(lineToDelete, userData);
                return true;
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to Delete Basket Line Item", ex);
                return false;
            }
        } else {
            return false;
        }


    }

    @Override
    public boolean removeBasketLine(DebtorsBasketLines line, EMCUserData userData) {
        if (line != null) {
            try {
                basketLinesBean.delete(line, userData);
                return true;
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to Delete Basket Line Item", ex);
                return false;
            }
        } else {
            return false;
        }


    }

//    @Override
//    public DebtorsBasketLines updateBasketLine(DebtorsBasketMaster master, DebtorsBasketLines line, EMCUserData userData) {
//        if (line != null) {
//            try {
//
//                BigDecimal price = priceArrangmentsBean.findItemPrice(master.getCustomerId(), line.getItemId(), null, null, null, dateHandler.nowDate(), line.getQuantity(), userData);
//                line.setPrice(price);
//                DebtorsCustomerInvoiceMaster invoiceMaster = new DebtorsCustomerInvoiceMaster();
//                invoiceMaster.setCustomerNo(master.getCustomerId());
//                invoiceMaster.setInvoiceDate(dateHandler.nowDate());
//                invoiceMaster.setInvCNType(DebtorsInvoiceCreditNoteType.MANUAL_INVOICE.toString());
//                invoiceMaster = (DebtorsCustomerInvoiceMaster) invoiceMasterBean.validateField("customerNo", invoiceMaster, userData);
//
//                //insert invoiceMaster when basket is posted
//
//                DebtorsCustomerInvoiceLines invoiceLines = new DebtorsCustomerInvoiceLines();
//                //invoiceLines.setInvCNNumber(invoiceMaster.getInvCNNumber());
//                invoiceLines.setItemId(line.getItemId());
//                invoiceLines.setUnitPrice(line.getPrice());
//              
//                    invoiceLines.setDimension1(null);
//                    invoiceLines.setDimension2(null);
//                    invoiceLines.setDimension3(null);
//                
//                invoiceLines.setQuantity(line.getQuantity());
//                try {
//                    invoiceLinesBean.calculateLineTotal(invoiceLines, invoiceMaster, userData);
//                } catch (EMCEntityBeanException ex) {
//                    Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error: Error Calculating Line Totals", ex);
//                }
//                line.setVat(invoiceLines.getVatAmount());
//                line.setTotalPrice(invoiceLines.getTotalCost());
//                DebtorsBasketLines newline = (DebtorsBasketLines) basketLinesBean.update(line, userData);
//                return newline;
//            } catch (EMCEntityBeanException ex) {
//                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Failed to Update Basket Line Item", ex);
//                return line;
//            }
//        } else {
//            return line;
//        }
//
//
//    }
    @Override
    public boolean emailCustomerInvoice(TRECWebOrderProcessHelper helper, String emailAddress, String attachment, boolean printInstruction, EMCUserData userData) throws EMCEntityBeanException {

        String companyName;
        if (helper.getTheMaster() != null) {
            companyName = helper.getTheMaster().getTrecCompanyName();
        } else {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("customerId", helper.getCustomerId());
            query.addField("customerName");
            companyName = (String) util.executeSingleResultQuery(query, userData);
        }


        StringBuilder message = new StringBuilder();
        message.append("<head><body>");
        message.append("<p>Dear " + companyName + "</p>");
        message.append("<p>Thank you for using our Trec Card web service.</p>");
        message.append("<p>We are pleased to inform you we have recieved your order.</p>");
        message.append("<p>Please find your attached invoice.</p>");
        message.append("<p>Thank you.</p>");
        message.append("<p>Kind Regards,<br/>");
        message.append("Lizet van Rensburg</p>");
        message.append("<div style='width:100%;height:5px; border-bottom:1px solid;'></div>");
        message.append("<p style='font-weight:bold'>Print Instructions</p>");
        message.append("<p style='font-style:italic'>Trec cards can be printed after payment has been made. Please make payment to the following bank account - Account Holder: Trek Card, Bank: Nedbank - Booysens, Account Number: 1980380929, Branch code: 198005. Please email proof of payment to info@trec.co.za.</p>");
        message.append("<table><ol>");
        message.append("<li><p style='font-weight:bold'>If this is the first time you purchase trec cards from Trek Card (Pty) Ltd, you must install the trec printer program. The printing program can be installed from the website using the following link: <a href='http://www.trec.co.za/TrecPrinter/install.htm'>Install print software</a></p></li>");
        message.append("<li><p>Open the Trec Printer program.</p></li>");
        message.append("<li><p>Type in your full Invoice Id and key you received via mail specified on your Invoice in the text boxes indicated.</p></li>");
        message.append("<li><p>Click the icon.</p></li>");
        message.append("<li><p>A list of available UN numbers should appear on the left in a list box. Select the one you want by double click.</p></li>");
        message.append("<li><p>Inspect the preview, if you are certain of the print. Click on the print icon. Select the printer and number of copies and print.</p></li>");
        message.append("<li><p>Be sure to check if your printer is ready – the program will deduct the quantity of cards printed once you click print. Miss printed cards cannot be printed again.</p></li>");
        message.append("<li><p style='color:red'>By LAW all trec cards must have the red lines on the sides. If you do not have a colour printer you must have pre-printed stationary, contact info@trec.co.za to obtain preprinted stationary.</p></li>");
        message.append("</ol></table>");
        message.append("<div style='width:100%;height:5px; border-bottom:1px solid;'></div>");
        message.append("</body></head>");



        try {
            EMCEmail email = new EMCEmail();
            if (printInstruction) {
                email.addRecipient(helper.getTheMaster().getTrecCompanyName(), emailAddress);
                email.setSubject("Trec Card Order Print Instructions #" + helper.getTheJournalMaster().getJournalNumber());
            } else {
                email.addRecipient(companyName, emailAddress);
                email.setSubject("Trec Card Order Invoice #" + helper.getTheInvoiceMaster().getInvCNNumber());
            }
            email.setMessage(message.toString());
            email.addAttachment(enumEMCModules.DEBTORS.toString() + "/" + attachment.substring(attachment.lastIndexOf(File.separator) + 1));
            if (mailManager.sendEmail(email, userData)) {
                logMessage(Level.INFO, "A confirmation email has been sent to " + emailAddress, userData);
                if (printInstruction) {
                    logMessage(Level.INFO, "Invoice Number: " + helper.getTheJournalMaster().getJournalNumber(), userData);
                } else {
                    logMessage(Level.INFO, "Invoice Number: " + helper.getTheInvoiceMaster().getInvCNNumber(), userData);
                }
                return true;

            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex);
        }



    }

    public DebtorsJournalMaster createPaymentJournal(TRECWebOrderProcessHelper helper, String journalDefinition, EMCUserData userData) throws EMCEntityBeanException {
        Date journalDate = Functions.nowDate();
        //
        DebtorsJournalMaster journalMaster = null;

        int lineNo = 10;
        BigDecimal totalSum;
        String journalNumber = null;
        DebtorsParameters parm = debtorsParametersBean.getDebtorsParameters(userData);
        if (parm.getDefaultItem() == null) {
            try {
                throw new EMCEntityBeanException("Please setup default debtors item");
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
            }
        }
        InventoryParameters invParameters = inventoryParametersBean.getInventoryParameters(userData);
        if (invParameters.getDefaultReminderItem() == null) {
            try {
                throw new EMCEntityBeanException("Please setup default reminder item in parameters");
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger(TRECWebOrderProcessLogicBean.class.getName()).log(Level.SEVERE, "Server Error try again later", ex);
            }
        }

        boolean addReminder = false;
        for (DebtorsBasketLines line : helper.getBasketLinesList()) {
            if (line.getItemId().equalsIgnoreCase(parm.getDefaultItem())) {
                addReminder = true;
            }
        }

        for (DebtorsBasketLines line : helper.getBasketLinesList()) {

            if (addReminder) {
                if (line.getItemId().equalsIgnoreCase(parm.getDefaultItem()) || line.getItemId().equalsIgnoreCase(invParameters.getDefaultReminderItem())) {

                    if (journalMaster == null) {
                        journalMaster = new DebtorsJournalMaster();
                        journalMaster.setJournalDescription("Customer Order");
                        journalMaster.setJournalDefinitionId(journalDefinition);
                        journalMaster.setJournalDate(journalDate);
                        journalMaster = (DebtorsJournalMaster) journalMasterBean.insert(journalMaster, userData);

                    }


                    DebtorsJournalLines journalLine = new DebtorsJournalLines();
                    SOPCustomers subCustomer = customerBean.findCustomer(helper.getBasketMaster().getCustomerId(), userData);
                    SOPCustomers customer = null;
                    if (subCustomer.getInvoiceToCustomer() != null) {
                        customer = customerBean.findCustomer(subCustomer.getInvoiceToCustomer(), userData);
                    } else {
                        customer = subCustomer;
                    }


                    if (customer == null) {
                        throw new EMCEntityBeanException("Customer: " + customer + " does not exist.");
                    }

                    totalSum = line.getTotalPrice()/*.add(line.getVat())*/;

                    journalLine.setLineRef(line.getItemId() + "-" + line.getDescription());
                    journalLine.setVatCode(customer.getVatCode());
                    journalLine.setLineNo(lineNo);
                    journalLine.setJournalNumber(journalMaster.getJournalNumber());
                    journalLine.setCustomerId(customer.getCustomerId());
                    journalLine.setLineAmount(totalSum);
                    journalLine.setLineDate(journalDate);

                    journalLinesBean.insert(journalLine, userData);

                    lineNo += 10;
                }
            } else {
                if (line.getItemId().equalsIgnoreCase(parm.getDefaultItem())) {

                    if (journalMaster == null) {
                        journalMaster = new DebtorsJournalMaster();
                        journalMaster.setJournalDescription("Customer Order");
                        journalMaster.setJournalDefinitionId(journalDefinition);
                        journalMaster.setJournalDate(journalDate);
                        journalMaster = (DebtorsJournalMaster) journalMasterBean.insert(journalMaster, userData);

                    }


                    DebtorsJournalLines journalLine = new DebtorsJournalLines();
                    SOPCustomers subCustomer = customerBean.findCustomer(helper.getBasketMaster().getCustomerId(), userData);
                    SOPCustomers customer = null;
                    if (subCustomer.getInvoiceToCustomer() != null) {
                        customer = customerBean.findCustomer(subCustomer.getInvoiceToCustomer(), userData);
                    } else {
                        customer = subCustomer;
                    }


                    if (customer == null) {
                        throw new EMCEntityBeanException("Customer: " + customer + " does not exist.");
                    }

                    totalSum = line.getTotalPrice()/*.add(line.getVat())*/;

                    journalLine.setLineRef(line.getItemId() + "-" + line.getDescription());
                    journalLine.setVatCode(customer.getVatCode());
                    journalLine.setLineNo(lineNo);
                    journalLine.setJournalNumber(journalMaster.getJournalNumber());
                    journalLine.setCustomerId(customer.getCustomerId());
                    journalLine.setLineAmount(totalSum);
                    journalLine.setLineDate(journalDate);

                    journalLinesBean.insert(journalLine, userData);

                    lineNo += 10;
                }
            }
        }

        if (journalMaster != null) {
            journalMaster.setJournalPostedBy(userData.getUserName());
            journalMaster.setJournalPostedDate(Functions.nowDate());

            journalMaster.setJournalStatus(JournalStatus.POSTED.toString());

            transactionLogicBean.postDebtorsJournal((DebtorsJournalMaster) journalMaster, userData);

            return journalMaster;
        } else {
            return null;
        }
    }
}
