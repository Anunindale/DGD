/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.stocksettlement;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class SettlementId extends EMCString{
    public SettlementId(){
        this.setMandatory(true);
        this.setColumnWidth(20);
        this.setEmcLabel("ID");
    }

}
