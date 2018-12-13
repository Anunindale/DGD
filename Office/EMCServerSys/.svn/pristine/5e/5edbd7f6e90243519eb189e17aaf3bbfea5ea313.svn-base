/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.journals.accessgroups;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployeesDS;
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
public class BaseJournalAccessGroupEmployeesDSBean extends EMCDataSourceBean implements BaseJournalAccessGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public BaseJournalAccessGroupEmployeesDSBean() {
        this.setDataSourceClassName(BaseJournalAccessGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseJournalAccessGroupEmployeesDS ds = (BaseJournalAccessGroupEmployeesDS) dataSourceInstance;
        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));
        return ds;
    }
}
