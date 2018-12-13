/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.movestock;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.movestock.DimensionId;
import emc.datatypes.inventory.movestock.Group;
import emc.datatypes.inventory.movestock.MasterSessionId;
import emc.datatypes.inventory.movestock.Reserve;
import emc.datatypes.inventory.movestock.Split;
import emc.datatypes.inventory.movestock.TransId;
import emc.datatypes.inventory.summary.QCStatus;
import emc.datatypes.systemwide.Quantity;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryMoveStockMaster")
public class InventoryMoveStockMaster extends EMCEntityClass {

    private String itemId;
    private String toLocation;
    private double quantity;
    private double availableQty;
    private long dimensionId;
    private boolean split;
    private boolean groupLine;
    private boolean posted;
    private String qCStatus;
    private String masterSessionId; //this was added to identify a move "session"
    private String transId; //This was added to identify transactions created by moving stock.
    private boolean reserved;

    public InventoryMoveStockMaster() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("toLocation", new LocationFKNotManditory());
        toBuild.put("quantity", new Quantity());
        toBuild.put("dimensionId", new DimensionId());
        toBuild.put("qCStatus", new QCStatus());
        toBuild.put("split", new Split());
        toBuild.put("groupLine", new Group());
        toBuild.put("masterSessionId", new MasterSessionId());
        toBuild.put("transId", new TransId());
        toBuild.put("reserved", new Reserve());

        return toBuild;
    }

    public long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public boolean isGroupLine() {
        return groupLine;
    }

    public void setGroupLine(boolean group) {
        this.groupLine = group;
    }

    public boolean getPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public String getQCStatus() {
        return qCStatus;
    }

    public void setQCStatus(String qCStatus) {
        this.qCStatus = qCStatus;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class.getName());
        query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryMoveStockMaster.class.getName(), "recordID");
        query.addAnd("warehouseId", "", InventoryDimensionTable.class.getName());
        query.addAnd("locationId", "", InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        return query;
    }

    /**
     * @return the masterSessionId
     */
    public String getMasterSessionId() {
        return masterSessionId;
    }

    /**
     * @param masterSessionId the masterSessionId to set
     */
    public void setMasterSessionId(String masterSessionId) {
        this.masterSessionId = masterSessionId;
    }

    /**
     * @return the availableQty
     */
    public double getAvailableQty() {
        return availableQty;
    }

    /**
     * @param availableQty the availableQty to set
     */
    public void setAvailableQty(double availableQty) {
        this.availableQty = availableQty;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
