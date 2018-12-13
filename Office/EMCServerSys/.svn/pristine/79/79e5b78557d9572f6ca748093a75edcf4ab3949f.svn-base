/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.repgroups.datasource;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.sop.repgroups.SOPSalesRepGroupsLocal;
import emc.entity.sop.datasource.SOPSalesRepGroupsDS;
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
public class SOPSalesRepGroupsDSBean extends EMCDataSourceBean implements SOPSalesRepGroupsDSLocal {

    @EJB
    private SOPSalesRepGroupsLocal masterBean;
    @EJB
    private BaseEmployeeLocal employeesBean;

    public SOPSalesRepGroupsDSBean() {
        this.setDataSourceClassName(SOPSalesRepGroupsDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPSalesRepGroupsDS ds = (SOPSalesRepGroupsDS) dataSourceInstance;
        ds.setGroupManagerName(employeesBean.findEmployeeNameAndSurname(ds.getGroupManager(), userData));
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
