/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.debtors.customerstatement;

import emc.enums.debtors.customerstatement.IgnoreBalanceEnum;
import emc.enums.emcquery.EMCQueryConditions;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reports.debtors.customeragingdetailed.DebtorsCustomerAgingDetailedReportLocal;
import emc.reporttools.EMCReportConfig;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DebtorsCustomerStatementReportBean extends EMCReportBean implements DebtorsCustomerStatementReportLocal {

    //Re-use population logic.
    @EJB
    private DebtorsCustomerAgingDetailedReportLocal agingDetailedReportBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery)queryList.get(1);
        query.addAnd("customerId", EMCQueryConditions.EQUALS, "invoiceToCustomer");

        //Indicates that Customer Statement is being populated.
        userData.setUserData(5, true);
        //Indicates whether certain customers should be ignored.
        userData.setUserData(6, IgnoreBalanceEnum.fromString((String)reportConfig.getParameters().get("ignore")));

        return agingDetailedReportBean.getReportResult(queryList, reportConfig, userData);
    }
}
