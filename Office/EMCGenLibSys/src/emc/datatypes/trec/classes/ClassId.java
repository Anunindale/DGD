/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.classes;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ClassId extends EMCString{

    public ClassId() {
        this.setEmcLabel("Class");
    	this.setColumnWidth(74);
        this.setMandatory(true);
    }

}
