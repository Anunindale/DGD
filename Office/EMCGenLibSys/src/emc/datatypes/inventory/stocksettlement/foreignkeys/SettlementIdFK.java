/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.stocksettlement.foreignkeys;

import emc.datatypes.inventory.stocksettlement.SettlementId;

/**
 *
 * @author rico
 */
public class SettlementIdFK extends SettlementId {
    public SettlementIdFK(){
        this.setRelatedField("settlementId");
        this.setRelatedTable(emc.entity.inventory.stocksettlement.InventoryStockSettlement.class.getName());
    }

}
