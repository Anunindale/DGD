/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.lookup.factory;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryPallet;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.InventSerialBatchMenu;
import emc.menus.inventory.menuitems.display.LocationMenu;
import emc.menus.inventory.menuitems.display.PalletMenu;
import emc.menus.inventory.menuitems.display.Warehouse;

/**
 *
 * @author wikus
 */
public class EMCDimensionLookupFactory {

    public static EMCLookupJTableComponent createDimension1JTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new Dimension1());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createDimension1Lookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData);
        EMCLookup lkp = new EMCLookup(new Dimension1());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createDimension1FormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension1(), "dimensionId", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new Dimension1(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createDimension2JTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new Dimension2());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createDimension2FormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new Dimension2(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createDimension2Lookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension2(), "dimensionId", userData);
        EMCLookup lkp = new EMCLookup(new Dimension2());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createDimension3JTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new Dimension3());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createDimension3Lookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData);
        EMCLookup lkp = new EMCLookup(new Dimension3());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createDimension3FormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryDimension3(), "dimensionId", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new Dimension3(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createWarehouseJTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new Warehouse());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createWarehouseFormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupFormComponent lkpWarehouse = new EMCLookupFormComponent(new Warehouse(), dataRelation, fieldKey);
        EMCLookupPopup warehousePop = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        lkpWarehouse.setPopup(warehousePop);
        return lkpWarehouse;
    }

    public static EMCLookup createWarehouseLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryWarehouse(), "warehouseId", userData);
        EMCLookup lkp = new EMCLookup(new Warehouse());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createLocationJTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new LocationMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createLocationFormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupFormComponent lkpLocation = new EMCLookupFormComponent(new LocationMenu(), dataRelation, fieldKey);
        EMCLookupPopup locationPop = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        lkpLocation.setPopup(locationPop);
        return lkpLocation;
    }

    public static EMCLookup createLocationLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryLocation(), "locationId", userData);
        EMCLookup lkp = new EMCLookup(new LocationMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createBatchJTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "batch", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new InventSerialBatchMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createBatchFormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "batch", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new InventSerialBatchMenu(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createBatchLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "batch", userData);
        EMCLookup lkp = new EMCLookup(new InventSerialBatchMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createSerialJTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "serial", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new InventSerialBatchMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createSerialFormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "serial", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new InventSerialBatchMenu(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createSerialLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventorySerialBatch(), "serial", userData);
        EMCLookup lkp = new EMCLookup(new InventSerialBatchMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupJTableComponent createPalletJTableLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryPallet(), "palletId", userData);
        EMCLookupJTableComponent lkp = new EMCLookupJTableComponent(new PalletMenu());
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookupFormComponent createPalletFormLookup(emcDataRelationManagerUpdate dataRelation, String fieldKey, EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryPallet(), "palletId", userData);
        EMCLookupFormComponent lkp = new EMCLookupFormComponent(new PalletMenu(), dataRelation, fieldKey);
        lkp.setPopup(pop);
        return lkp;
    }

    public static EMCLookup createPalletLookup(EMCUserData userData) {
        EMCLookupPopup pop = new EMCLookupPopup(new InventoryPallet(), "palletId", userData);
        EMCLookup lkp = new EMCLookup(new PalletMenu());
        lkp.setPopup(pop);
        return lkp;
    }
}
