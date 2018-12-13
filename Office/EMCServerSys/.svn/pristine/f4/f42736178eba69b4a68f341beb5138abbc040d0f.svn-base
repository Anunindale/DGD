/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.gl.chartofaccounts;

import emc.entity.gl.GLChartOfAccounts;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class GLChartOfAccountsReportBean extends EMCReportBean implements GLChartOfAccountsReportLocal {

    public GLChartOfAccountsReportBean() {
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List ret = new ArrayList();
        for (Object result : queryResult) {
            GLChartOfAccounts account = (GLChartOfAccounts) result;
            GLChartOfAccountsReportDS reportDS = new GLChartOfAccountsReportDS();
            reportDS.setAccountNumber(account.getAccountNumber());
            reportDS.setDescription(account.getDescription());
            reportDS.setRecordType(account.getRecordType());
            reportDS.setAccountType(account.getAccountType());
            reportDS.setLocked(account.isLocked() ? "Y" : "N");
            reportDS.setAccountClosed(account.isAccountClosed() ? "Y" : "N");
            reportDS.setSecurityRules(account.getSecurityRules());
            reportDS.setAllocationRules(account.getAllocationRules());
            reportDS.setAnalysisRules(account.getAnalysisRules());
            reportDS.setAnalysisCodes1(account.getAnalysisCode1());
            reportDS.setAnalysisCodes2(account.getAnalysisCode2());
            reportDS.setAnalysisCodes3(account.getAnalysisCode3());
            reportDS.setAnalysisCodes4(account.getAnalysisCode4());
            reportDS.setAnalysisCodes5(account.getAnalysisCode5());

            ret.add(reportDS);
        }
        return ret;
    }
}
