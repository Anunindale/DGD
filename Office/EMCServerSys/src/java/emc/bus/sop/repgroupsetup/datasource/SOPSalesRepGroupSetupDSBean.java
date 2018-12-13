/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.repgroupsetup.datasource;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.sop.repgroupsetup.SOPSalesRepGroupSetupLocal;
import emc.entity.sop.datasource.SOPSalesRepGroupSetupDS;
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
public class SOPSalesRepGroupSetupDSBean extends EMCDataSourceBean implements SOPSalesRepGroupSetupDSLocal {

    @EJB
    private SOPSalesRepGroupSetupLocal masterBean;
    @EJB
    private BaseEmployeeLocal employeesBean;

    public SOPSalesRepGroupSetupDSBean() {
        this.setDataSourceClassName(SOPSalesRepGroupSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPSalesRepGroupSetupDS ds = (SOPSalesRepGroupSetupDS) dataSourceInstance;
        ds.setRepName(employeesBean.findEmployeeNameAndSurname(ds.getRepId(), userData));
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
}
