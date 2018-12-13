/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.EMCString;

/**
 * @name        Module
 *
 * @date        20 Apr 2009
 *
 * @desciption  Data type for module fields on entities.
 *
 * @author      riaan
 */
public class EMCModule extends EMCString {

    /** Creates a new instance of EMCModule. */
    public EMCModule() {
        this.setEmcLabel("Module");
        this.setColumnWidth(70);
        this.setEditable(false);
    }
}
