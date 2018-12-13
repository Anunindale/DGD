/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.datasource.chartofaccounts;

import emc.bus.gl.chartofaccounts.GLChartOfAccountsLocal;
import emc.entity.gl.datasource.GLChartOfAccountsDS;
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
public class GLChartOfAccountsDSBean extends EMCDataSourceBean implements GLChartOfAccountsDSLocal {

    @EJB
    private GLChartOfAccountsLocal chartOfAccountsBean;
    
    /**
     * Creates a new instance of GLChartOfAccountsDSBean.
     */
    public GLChartOfAccountsDSBean() {
        this.setDataSourceClassName(GLChartOfAccountsDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return chartOfAccountsBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return chartOfAccountsBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return chartOfAccountsBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return chartOfAccountsBean.validateField(fieldNameToValidate, theRecord, userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        GLChartOfAccountsDS ds = (GLChartOfAccountsDS)dataSourceInstance;
        
        return ds;
    }
}
