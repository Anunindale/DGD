/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.movestock;

import emc.entity.inventory.movestock.InventoryMoveStockLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.movestock.InventoryMoveStockSummary;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless; 

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMoveStockSummaryBean extends EMCEntityBean implements InventoryMoveStockSummaryLocal {

    public void insertRecord(InventoryMoveStockMaster master, EMCUserData userData) throws EMCEntityBeanException {
        InventoryMoveStockSummary summary = new InventoryMoveStockSummary();
        summary.setItemId(master.getItemId());
        summary.setDimensionId(master.getDimensionId());
        summary.setTransId(master.getTransId());
        
        if (master.isSplit()) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockLines.class.getName());
            query.addAnd("masterId", master.getRecordID());
            List linesList = util.executeGeneralSelectQuery(query, userData);
            InventoryMoveStockLines line;
            for (Object o : linesList) {
                line = (InventoryMoveStockLines) o;

                InventoryMoveStockSummary newSummary = (InventoryMoveStockSummary)doClone(summary, userData);

                newSummary.setNewLocation(line.getToLocation());
                newSummary.setQty(line.getQuantity());

                super.insert(newSummary, userData);
            }
        } else {
            summary.setNewLocation(master.getToLocation());
            summary.setQty(master.getQuantity());

            super.insert(summary, userData);
        }
    }
}
