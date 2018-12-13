package emc.entity.inventory.datasource;

import emc.datatypes.datasources.base.employeeNameDS;
import emc.entity.inventory.InventoryItemAccessGroupEmployees;
import emc.entity.inventory.journals.datasource.*;
import java.util.HashMap;

/**
 * 
 * @author wikus
 */
public class InventoryItemAccessGroupEmployeesDS extends InventoryItemAccessGroupEmployees {

    private String employeeName;

    public InventoryItemAccessGroupEmployeesDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("employeeName", new employeeNameDS());
        return toBuild;
    }
}
