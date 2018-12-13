/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.gl.financialperiod;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class PeriodName extends EMCString{
    public PeriodName(){
        this.setEmcLabel("Period Name");
        this.setColumnWidth(176);
    }
}
