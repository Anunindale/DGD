/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.employeeaccessgroupemployees.datasourse;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.base.employeeaccessgroupemployees.datasourse.BaseEmployeeAccessGroupEmployeesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseEmployeeAccessGroupEmployeesDSBean extends EMCDataSourceBean implements BaseEmployeeAccessGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public BaseEmployeeAccessGroupEmployeesDSBean() {
        this.setDataSourceClassName(BaseEmployeeAccessGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseEmployeeAccessGroupEmployeesDS ds = (BaseEmployeeAccessGroupEmployeesDS) dataSourceInstance;
        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));
        return ds;

    }
}
