/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.consolidatedpickinglist.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.consolidatedpickinglistsetup.AddLine;
import emc.datatypes.inventory.consolidatedpickinglistsetup.ItemReference;
import emc.datatypes.inventory.consolidatedpickinglistsetup.OrderType;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.entity.inventory.picking.InventoryPickingListMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.pickinglist.PickingListStatusses;
import emc.framework.EMCQuery;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class InventoryPLLinesConsolidatedDS extends InventoryPickingListLines implements ItemReferenceInterface {

    private boolean addLine;
    private String itemReference;
    private String orderType;
    private String itemDescription;

    /** Creates a new instance of InventoryPLLinesConsolidatedDS. */
    public InventoryPLLinesConsolidatedDS() {
        this.setDataSource(true);
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("addLine", new AddLine());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("orderType", new OrderType());

        return toBuild;
    }

    public boolean isAddLine() {
        return addLine;
    }

    public void setAddLine(boolean addLine) {
        this.addLine = addLine;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
