/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.postdatedpayment;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.debtors.journals.DebtorsJournalLinesLocal;
import emc.bus.debtors.journals.DebtorsJournalMasterLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.journals.Modules;
import emc.enums.debtors.journals.DebtorsJournalType;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsPostDatedPayment.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsPostDatedPaymentBean extends EMCEntityBean implements DebtorsPostDatedPaymentLocal {

    @EJB
    private DebtorsJournalMasterLocal journalMasterBean;
    @EJB
    private DebtorsJournalLinesLocal journalLinesBean;
    @EJB
    private BaseJournalDefinitionLocal journalDefBean;
    @EJB
    private SOPCustomersLocal customerBean;

    /** Creates a new instance of DebtorsPostDatedPaymentBean */
    public DebtorsPostDatedPaymentBean() {

    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);

        if (ret) {
            DebtorsPostDatedPayment postDatedPayment = (DebtorsPostDatedPayment)vobject;
            ret = ret && validateCustomer(postDatedPayment, userData);
        }
        
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);

        if (ret) {
            DebtorsPostDatedPayment postDatedPayment = (DebtorsPostDatedPayment)vobject;
            ret = ret && validateCustomer(postDatedPayment, userData);
        }

        return ret;
    }

    /** Checks that the customer on the specified DebtorsPostDatedPayment instance is valid. */
    private boolean validateCustomer(DebtorsPostDatedPayment postDatedPayment, EMCUserData userData) {
        SOPCustomers customer = customerBean.findCustomer(postDatedPayment.getCustomer(), userData);

        if (customer != null && !util.checkObjectsEqual(customer.getCustomerId(), customer.getInvoiceToCustomer())) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.INVALID_CUSTOMER, userData, customer.getInvoiceToCustomer());
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates a payment journal for all post dated payments in the specified date range.
     * @param from From date.
     * @param to To date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean createPaymentJournal(Date from, Date to, String journalDefinition, EMCUserData userData) throws EMCEntityBeanException {
        if (from == null || to == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.NO_DATE, userData);
            return false;
        }

        if (!isBlank(journalDefinition)) {
            BaseJournalDefinitionTable journalDef = journalDefBean.getJournalDefinition(journalDefinition, Modules.DEBTORS, userData);

                if (!DebtorsJournalType.PAYMENT.equals(DebtorsJournalType.fromString(journalDef.getJournalType()))) {
                    logMessage(Level.SEVERE, ServerDebtorsMessageEnum.WRONG_TYPE_DEF, userData, "Journal Definition", DebtorsJournalType.PAYMENT.toString());
                    return false;
                }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsPostDatedPayment.class);
        query.addAnd("paymentDate", Functions.date2String(from).replaceAll("/", "-"), EMCQueryConditions.GREATER_THAN_EQ);
        query.addAnd("paymentDate", Functions.date2String(to).replaceAll("/", "-"), EMCQueryConditions.LESS_THAN_EQ);
        query.addAnd("processed", false);

        List<DebtorsPostDatedPayment> postDatedPayments = (List<DebtorsPostDatedPayment>)util.executeGeneralSelectQuery(query, userData);

        DebtorsJournalMaster journalMaster = new DebtorsJournalMaster();
        journalMaster.setJournalDescription("Post Dated Payment");
        journalMaster.setJournalDefinitionId(journalDefinition);
        journalMaster.setJournalDate(to);

        journalMasterBean.insert(journalMaster, userData);

        int lineNo = 10;
        for (DebtorsPostDatedPayment payment : postDatedPayments) {
            DebtorsJournalLines journalLine = new DebtorsJournalLines();

            SOPCustomers customer = customerBean.findCustomer(payment.getCustomer(), userData);

            if (customer == null) {
                throw new EMCEntityBeanException("Customer: " + customer + " does not exist.");
            }

            journalLine.setLineRef(payment.getPaymentNumber());
            journalLine.setVatCode(customer.getVatCode());
            journalLine.setLineNo(lineNo);
            journalLine.setJournalNumber(journalMaster.getJournalNumber());
            journalLine.setCustomerId(payment.getCustomer());
            journalLine.setLineAmount(payment.getPaymentAmount().abs().multiply(new BigDecimal(-1)));
            journalLine.setLineDate(payment.getPaymentDate());
            
            journalLinesBean.insert(journalLine, userData);

            lineNo += 10;

            payment.setProcessed(true);

            this.update(payment, userData);
        }

        return true;
    }
}
