/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.hr.terminationlog.datasource;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.hr.terminationlog.HRTerminationLogLocal;
import emc.entity.hr.datasource.HRTerminationLogDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HRTerminationLogDSBean extends EMCDataSourceBean implements HRTerminationLogDSLocal {

    @EJB
    private BaseEmployeeLocal empBean;
    @EJB
    private HRTerminationLogLocal masterBean;

    public HRTerminationLogDSBean() {
        this.setDataSourceClassName(HRTerminationLogDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        HRTerminationLogDS ds = (HRTerminationLogDS) dataSourceInstance;
        ds.setEmployeeName(empBean.findEmployeeNameAndSurname(ds.getEmployeeNumber(), userData));
        return ds;
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
}
