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
public class HierarchyTree extends EMCString {

    /** Creates a new instance of HierarchyTree */
    public HierarchyTree() {
        this.setEmcLabel("Hierarchy Tree");
        this.setStringSize(10000);
    }
}
