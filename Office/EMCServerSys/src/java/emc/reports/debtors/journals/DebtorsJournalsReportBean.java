/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.debtors.journals;

import emc.bus.debtors.journals.DebtorsJournalLinesLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsJournalsReportBean extends EMCReportBean implements DebtorsJournalsReportLocal {

    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private DebtorsJournalLinesLocal journalLinesBean;
    
    /** Creates a new instance of DebtorsJournalsReportBean. */
    public DebtorsJournalsReportBean() {
        
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        for (Object result : queryResult) {
            DebtorsJournalMaster journalMaster = (DebtorsJournalMaster)result;

            List<DebtorsJournalLines> lines = journalLinesBean.getJournalLines(journalMaster.getJournalNumber(), userData);

            for (DebtorsJournalLines line : lines) {
                DebtorsJournalsReportDS ds = new DebtorsJournalsReportDS();
                ds.setJournalNumber(journalMaster.getJournalNumber());
                ds.setApprovedBy(journalMaster.getJournalApprovedBy());
                ds.setEnteredBy(journalMaster.getCreatedBy());
                ds.setEnteredDate(Functions.date2String(journalMaster.getCreatedDate()));
                ds.setJournalDesc(journalMaster.getJournalDescription());
                ds.setStatus(journalMaster.getJournalStatus());

                ds.setCustomerId(line.getCustomerId());
                
                SOPCustomers customer = customerBean.findCustomer(line.getCustomerId(), userData);
                if (customer != null) {
                    ds.setCustomerName(customer.getCustomerName());
                }

                ds.setLineRef(line.getLineRef());
                ds.setLineDate(Functions.date2String(line.getLineDate()));
                ds.setAmount(line.getLineAmount());
                ds.setContraType(line.getContraType());
                ds.setContraAccount(line.getContraAccount());
                ds.setTotal(line.getLineTotal());
                ds.setVatCode(line.getVatCode());
                ds.setVatAmount(line.getVatAmount());
                ds.setLineDescription(line.getLineDescription());
                
                ret.add(ds);
            }
        }

        return ret;
    }
}
