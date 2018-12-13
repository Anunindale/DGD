/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.gl.journals;

import emc.entity.gl.journals.GLJournalLines;
import emc.entity.gl.journals.GLJournalMaster;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class GLJournalReportBean extends EMCReportBean implements GLJournalReportLocal {

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        if (!queryResult.isEmpty()) {
            for (Object result : queryResult) {
                Object[] res = (Object[]) result;
                GLJournalReportDS ds = new GLJournalReportDS();

                ds.setJournalNumber((String) res[0]);
                ds.setJournalDescription((String) res[1]);
                ds.setJournalStatus((String) res[2]);
                if (!isBlank(res[2])) {
                    ds.setJournalDate(dateHandler.date2String((Date) res[3]));
                }
                ds.setApprovedBy((String) res[4]);
                if (!isBlank(res[5])) {
                    ds.setApprovedDate(dateHandler.date2String((Date) res[5]));
                }
                ds.setPostedBy((String) res[6]);
                if (!isBlank(res[7])) {
                    ds.setPostedDate(dateHandler.date2String((Date) res[7]));
                }
                ds.setExtRef((String) res[8]);
                ds.setType((String) res[9]);
                ds.setAccount((String) res[10]);
                ds.setDescription((String) res[11]);
                ds.setDebit((BigDecimal) res[12]);
                ds.setCredit((BigDecimal) res[13]);
                ds.setContraType((String) res[14]);
                ds.setContraAccount((String) res[15]);
                ds.setContraDebit((BigDecimal) res[16]);
                ds.setContraCredit((BigDecimal) res[17]);

                ret.add(ds);
            }
        }
        return ret;
    }

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("journalNumber", GLJournalMaster.class.getName());//0
        query.addField("journalDescription", GLJournalMaster.class.getName());//1
        query.addField("journalStatus", GLJournalMaster.class.getName());//2
        query.addField("journalDate", GLJournalMaster.class.getName());//3
        query.addField("journalApprovedBy", GLJournalMaster.class.getName());//4
        query.addField("journalApprovedDate", GLJournalMaster.class.getName());//5
        query.addField("journalPostedBy", GLJournalMaster.class.getName());//6
        query.addField("journalPostedDate", GLJournalMaster.class.getName());//7
        query.addField("extReference", GLJournalLines.class.getName());//8
        query.addField("lineType", GLJournalLines.class.getName());//9
        query.addField("account", GLJournalLines.class.getName());//10
        query.addField("description", GLJournalLines.class.getName());//11
        query.addField("debit", GLJournalLines.class.getName());//12
        query.addField("credit", GLJournalLines.class.getName());//13
        query.addField("contraType", GLJournalLines.class.getName());//14
        query.addField("contraAccount", GLJournalLines.class.getName());//15
        query.addField("contraDebit", GLJournalLines.class.getName());//16
        query.addField("contraCredit", GLJournalLines.class.getName());//17

        return super.manipulateQuery(query, userData);
    }
}
