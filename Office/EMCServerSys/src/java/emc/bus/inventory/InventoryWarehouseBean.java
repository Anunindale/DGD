/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.inventory;

import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryLocationsEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerInventoryMessageEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryWarehouseBean extends EMCEntityBean implements InventoryWarehouseLocal {

    @EJB
    private InventoryLocationLocal locationBean;

    /** Creates a new instance of InventoryWarehouseBean */
    public InventoryWarehouseBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            if (fieldNameToValidate.equals("RECAvailable") || fieldNameToValidate.equals("QCAvailable")) {
                InventoryWarehouse record = (InventoryWarehouse) theRecord;
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                query.addAnd("warehouse", record.getWarehouseId());
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected warehouse is already in use. You may not change the QC or REC Available fields.", userData);
                    ret = false;
                }
            }
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryWarehouse warehouse = (InventoryWarehouse) iobject;
        InventoryLocation location = new InventoryLocation();
        location.setLocationId(InventoryLocationsEnum.RECEIVING_AREA.toString());
        location.setDescription("Receiving Area");
        location.setWarehouseId(warehouse.getWarehouseId());
        locationBean.insert(location, userData);
        location = new InventoryLocation();
        location.setLocationId(InventoryLocationsEnum.QUALITY_CHECK.toString());
        location.setDescription("Quality Check");
        location.setWarehouseId(warehouse.getWarehouseId());
        locationBean.insert(location, userData);
        location = new InventoryLocation();
        location.setLocationId(InventoryLocationsEnum.DESPATCH.toString());
        location.setDescription("Dispatch");
        location.setWarehouseId(warehouse.getWarehouseId());
        locationBean.insert(location, userData);
        return super.insert(iobject, userData);
    }

    /**
     * Returns the warehouse with the specified id.
     * @param warehouseId Warehouse id to search for.
     * @param userData User data.
     * @return The warehouse with the specified id.
     */
    public InventoryWarehouse getWarehouse(String warehouseId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
        query.addAnd("warehouseId", warehouseId);

        InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);

        if (warehouse == null) {
            logMessage(Level.SEVERE, ServerInventoryMessageEnum.WH_NOT_FOUND, userData, warehouseId);
        }

        return warehouse;
    }

    public void createSystemLocations(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);

        List<InventoryWarehouse> warehouseList = util.executeGeneralSelectQuery(query, userData);

        for (InventoryWarehouse warehouse : warehouseList) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class);
            query.addAnd("warehouseId", warehouse.getWarehouseId());
            query.openAndConditionBracket();
            query.addOr("locationId", InventoryLocationsEnum.DESPATCH.toString());
            query.addOr("locationId", InventoryLocationsEnum.QUALITY_CHECK.toString());
            query.addOr("locationId", InventoryLocationsEnum.RECEIVING_AREA.toString());
            query.closeConditionBracket();

            List<InventoryLocation> locationList = util.executeGeneralSelectQuery(query, userData);

            List<String> systemLocations = new ArrayList<String>();
            systemLocations.add(InventoryLocationsEnum.DESPATCH.toString());
            systemLocations.add(InventoryLocationsEnum.QUALITY_CHECK.toString());
            systemLocations.add(InventoryLocationsEnum.RECEIVING_AREA.toString());

            for (InventoryLocation location : locationList) {
                systemLocations.remove(location.getLocationId());
            }

            for (String location : systemLocations) {
                InventoryLocation locationRec = new InventoryLocation();
                locationRec.setLocationId(location);
                locationRec.setDescription("System Location");
                locationRec.setWarehouseId(warehouse.getWarehouseId());
                locationBean.insert(locationRec, userData);
            }
        }
        Logger.getLogger("emc").log(Level.INFO, "System Locations generated.", userData);
    }
}
