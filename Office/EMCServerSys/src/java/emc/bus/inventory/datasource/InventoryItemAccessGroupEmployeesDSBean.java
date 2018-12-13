package emc.bus.inventory.datasource;

import emc.bus.inventory.journals.datasource.*;
import emc.bus.base.BaseEmployeeLocal;
import emc.entity.inventory.datasource.InventoryItemAccessGroupEmployeesDS;
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
public class InventoryItemAccessGroupEmployeesDSBean extends EMCDataSourceBean implements InventoryItemAccessGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    /** Creates a new instance of InventoryItemAccessGroupEmployeesDSBean */
    public InventoryItemAccessGroupEmployeesDSBean() {
        this.setDataSourceClassName(InventoryItemAccessGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryItemAccessGroupEmployeesDS ds = (InventoryItemAccessGroupEmployeesDS) dataSourceInstance;

        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));


        return ds; 
    }
}
