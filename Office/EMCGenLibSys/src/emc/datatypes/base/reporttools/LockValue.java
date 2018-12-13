/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.reporttools;

import emc.datatypes.EMCBoolean;

/**
 *
 * @author wikus
 */
public class LockValue extends EMCBoolean{

    public LockValue() {
        this.setEmcLabel("Lock");
        this.setColumnWidth(10);
    }
    
}
