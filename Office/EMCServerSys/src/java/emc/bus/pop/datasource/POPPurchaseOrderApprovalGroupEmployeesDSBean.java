package emc.bus.pop.datasource;

import emc.bus.inventory.journals.datasource.*;
import emc.bus.base.BaseEmployeeLocal;
import emc.entity.pop.datasource.POPPurchaseOrderApprovalGroupEmployeesDS;
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
public class POPPurchaseOrderApprovalGroupEmployeesDSBean extends EMCDataSourceBean 
        implements POPPurchaseOrderApprovalGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    /** Creates a new instance of POPPurchaseOrderApprovalGroupEmployeesDSBean */
    public POPPurchaseOrderApprovalGroupEmployeesDSBean() {
        this.setDataSourceClassName(POPPurchaseOrderApprovalGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
       POPPurchaseOrderApprovalGroupEmployeesDS ds = (POPPurchaseOrderApprovalGroupEmployeesDS) dataSourceInstance;

        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));


        return ds;
    }
}
