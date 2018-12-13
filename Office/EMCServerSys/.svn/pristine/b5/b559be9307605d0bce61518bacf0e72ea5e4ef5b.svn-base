package emc.bus.base.journals.datasources;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.base.journals.datasource.BaseJournalApprovalGroupEmployeesDS;
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
public class BaseJournalApprovalGroupEmployeesDSBean extends EMCDataSourceBean implements BaseJournalApprovalGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    /** Creates a new instance of InventoryJournalApprovalGroupEmployeesBean */
    public BaseJournalApprovalGroupEmployeesDSBean() {
        this.setDataSourceClassName(BaseJournalApprovalGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseJournalApprovalGroupEmployeesDS ds = (BaseJournalApprovalGroupEmployeesDS) dataSourceInstance;

        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));


        return ds;
    }
}
