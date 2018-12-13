/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.lookup.factory;

import emc.app.components.lookup.*;
import emc.app.components.formlookup.controllookup.itemlookup.EMCItemControlLookup;
import emc.app.components.emcJTextField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.stock.ItemLookupRelationManager;
import emc.app.components.emctable.stock.ItemLookupRelationManagerParameters;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.formlookup.controllookup.StockControlLookupDRM;
import emc.app.components.inventory.lookups.itemreference.ItemReferencePopup;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class EMCItemLookupFactory {

    private static EMCMenuItem itemMasterMenuItem = new emc.menus.inventory.menuitems.display.ItemMaster();

    public static EMCLookupTableCellEditor createItemLookupEditor(EMCUserData copyUD) {
        EMCLookupJTableComponent lkpItem = new EMCLookupJTableComponent(itemMasterMenuItem);
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        lkpItem.setPopup(itemPopup);
        return new EMCLookupTableCellEditor(lkpItem);
    }

    public static EMCLookupJTableComponent createItemLookup(EMCUserData copyUD) {
        EMCLookupJTableComponent lkpItem = new EMCLookupJTableComponent(itemMasterMenuItem);
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        lkpItem.setPopup(itemPopup);
        return lkpItem;
    }

    public static EMCLookupFormComponent createItemFormLookup(EMCUserData copyUD, emcDataRelationManagerUpdate dataSource, String fieldKey) {
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        EMCLookupFormComponent lkpItem = new EMCLookupFormComponent(itemMasterMenuItem, dataSource, fieldKey);
        lkpItem.setPopup(itemPopup);
        dataSource.setDocument(lkpItem.getDocument());
        return lkpItem;
    }

    public static EMCLookup createItemFormLookup(EMCUserData copyUD) {
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        EMCLookup lkpItem = new EMCLookup(itemMasterMenuItem);
        lkpItem.setPopup(itemPopup);
        return lkpItem;
    }

    public static EMCItemControlLookup createItemControllLookup(EMCControlLookupComponentDRM dataRelation, emcJTextField txtDesc, String entityClassName, EMCUserData copyUD) {
        EMCItemControlLookup lkpItem = new EMCItemControlLookup(itemMasterMenuItem, dataRelation, "itemId", txtDesc, "description", entityClassName, (String) copyUD.getUserData(4));
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        lkpItem.setPopup(itemPopup);
        dataRelation.setLookup(lkpItem);
        return lkpItem;
    }

    public static EMCItemControlLookup createItemControllLookup(StockControlLookupDRM dataRelation, emcJTextField txtDesc, String entityClassName, EMCUserData copyUD) {
        EMCItemControlLookup lkpItem = new EMCItemControlLookup(itemMasterMenuItem, dataRelation, "itemId", txtDesc, "description", entityClassName, (String) copyUD.getUserData(4));
        ItemReferencePopup itemPopup = new ItemReferencePopup("itemId", copyUD);
        lkpItem.setPopup(itemPopup);
        dataRelation.setLookup(lkpItem);
        return lkpItem;
    }

    /**
     * Creates a Map with the item and dimension lookup for a table and a lookup relation manager
     * 
     * @param drm A StockDARM
     * @param dimension1 field key for dimension1   
     * @param dimension2 field key for dimension1
     * @param dimension3 field key for dimension1
     * @param userData your normal userData
     * @return A Map<String, Object> with:  item - item lookup;
     *                                      dimension1 - dim1 lookup;
     *                                      dimension2 - dim2 lookup;
     *                                      dimension3 - dim3 lookup;
     *                                      lrm - LookupRelationManager;
     */
    public static HashMap<String, Object> createItemAndDimensionLookups(StockDRM drm, String dimension1, String dimension2, String dimension3, EMCUserData userData) {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        EMCLookupJTableComponent lkpItem = createItemLookup(userData);
        retMap.put("item", lkpItem);

        EMCLookupJTableComponent lkpDimension1 = new EMCLookupJTableComponent(new Dimension1());
        EMCLookupPopup dim1Pop = new EMCLookupPopup(new InventoryItemDimension1SetupDS(), "dimensionId", userData);
        dim1Pop.setPreferredWidth(500);
        lkpDimension1.setPopup(dim1Pop);
        retMap.put("dimension1", lkpDimension1);

        EMCLookupJTableComponent lkpDimension2 = new EMCLookupJTableComponent(new Dimension2());
        EMCLookupPopup dim2Pop = new EMCLookupPopup(new InventoryItemDimension2SetupDS(), "dimensionId", userData);
        lkpDimension2.setPopup(dim2Pop);
        retMap.put("dimension2", lkpDimension2);

        EMCLookupJTableComponent lkpDimension3 = new EMCLookupJTableComponent(new Dimension3());
        EMCLookupPopup dim3Pop = new EMCLookupPopup(new InventoryItemDimension3SetupDS(), "dimensionId", userData);
        lkpDimension3.setPopup(dim3Pop);
        retMap.put("dimension3", lkpDimension3);

        ItemLookupRelationManagerParameters params = new ItemLookupRelationManagerParameters();
        params.setItemField("itemId");
        params.setDim1Field(dimension1);
        params.setDim2Field(dimension2);
        params.setDim3Field(dimension3);
        params.setTableItemLookup(lkpItem);
        params.setTableDim1Lookup(lkpDimension1);
        params.setTableDim2Lookup(lkpDimension2);
        params.setTableDim3Lookup(lkpDimension3);
        retMap.put("lrm", new ItemLookupRelationManager(drm, params));

        return retMap;
    }
}
