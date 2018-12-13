/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.inventory.itemclassification.hierarchies;

import emc.datatypes.EMCString;

/**
 *
 * @author riaan
 */
public class HierarchyId extends EMCString {

    /** Creates a new instance of HierarchyId */
    public HierarchyId() {
        this.setEmcLabel("Hierarchy");
        this.setColumnWidth(50);
        this.setMandatory(true);
    }
}
