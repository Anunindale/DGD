/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.dimension1;

import emc.datatypes.EMCInt;

/**
 * @description : Data type for sortCode on InventoryDimension1.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class SortCode extends EMCInt {

    /** Creates a new instance of SortCode */
    public SortCode() {
        this.setEmcLabel("Sort Code");
        this.setColumnWidth(65);
    }
}
