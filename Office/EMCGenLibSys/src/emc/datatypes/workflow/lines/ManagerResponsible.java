package emc.datatypes.workflow.lines;

import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;

/**
 *
 * @author Marius-Gert Coetzee
 */
public class ManagerResponsible extends EMCString {

    /** Creates a new instance of ManagerResponsible */
    public ManagerResponsible() {
        this.setEmcLabel("Manager Responsible");
        this.setEditable(true);
        this.setStringSize(50);
        this.setRelatedTable(BaseEmployeeTable.class.getName());
        this.setRelatedField("employeeNumber");
    }
}
