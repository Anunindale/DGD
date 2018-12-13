/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.stocksettlement;

import emc.datatypes.EMCDate;

/**
 *
 * @author rico
 */
public class EndDate extends EMCDate {
    public EndDate(){
        this.setEmcLabel("End Date");
        this.setMandatory(true);
    }

}
