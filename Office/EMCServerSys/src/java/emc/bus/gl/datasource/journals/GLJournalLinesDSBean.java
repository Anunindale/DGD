/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.datasource.journals;

import emc.bus.gl.chartofaccounts.GLChartOfAccountsLocal;
import emc.bus.gl.journallines.GLJournalLinesLocal;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.datasource.GLJournalLinesDS;
import emc.entity.gl.journals.GLJournalLines;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * 
 * @author riaan
 */
@Stateless
public class GLJournalLinesDSBean extends EMCDataSourceBean implements GLJournalLinesDSLocal {

    @EJB
    private GLJournalLinesLocal journalLinesBean;
    @EJB
    private GLChartOfAccountsLocal chartOfAccountsBean;

    /**
     * Creates a new instance of GLJournalLinesDSBean.
     */
    public GLJournalLinesDSBean() {
        this.setDataSourceClassName(GLJournalLinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalLinesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalLinesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalLinesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = journalLinesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret.getClass().equals(GLJournalLines.class)) {
            //Convert superclass back to data source
            ret = convertSuperToDataSource(ret, userData);
        } else if (ret == Boolean.TRUE) {
            ret = theRecord;
        }

        if (ret instanceof GLJournalLinesDS) {
            GLJournalLinesDS ds = (GLJournalLinesDS)ret;
            if (fieldNameToValidate.equals("account") || fieldNameToValidate.equals("contraAccount")) {
                populateDataSourceFields(ds, userData);
            }
        }
        return ret;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        GLJournalLinesDS ds = (GLJournalLinesDS) dataSourceInstance;

        if (!isBlank(ds.getAccount())) {
            GLChartOfAccounts account = chartOfAccountsBean.getAccount(ds.getAccount(), userData);
            if (account != null) {
                ds.setAccountDescription(account.getDescription());
            }
        }

        if (!isBlank(ds.getContraAccount())) {
            GLChartOfAccounts account = chartOfAccountsBean.getAccount(ds.getContraAccount(), userData);
            if (account != null) {
                ds.setContraAccountDescription(account.getDescription());
            }
        }

        return ds;
    }
}
