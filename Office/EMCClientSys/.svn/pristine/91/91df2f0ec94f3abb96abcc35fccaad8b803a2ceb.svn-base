/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.inventoryreference;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class SupplierReferenceLookupRM extends EMCLookupRelationManager {

    private EMCQuery query;
    private EMCQuery dim1;
    private EMCQuery dim2;
    private EMCQuery dim3;
    private emcDataRelationManagerUpdate dataRelation;

    public SupplierReferenceLookupRM(emcDataRelationManagerUpdate dataRelation) {
        this.dataRelation = dataRelation;
        //Query Dimension1
        dim1 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
        dim1.addTableAnd(InventoryItemDimension1Setup.class.getName(), "dimensionId", InventoryDimension1.class.getName(), "dimensionId");
        dim1.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        dim1.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        dim1.addAnd("reference", "", InventoryReference.class.getName());

        //Query Dimension2
        dim2 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        dim2.addTableAnd(InventoryItemDimension2Setup.class.getName(), "dimensionId", InventoryDimension2.class.getName(), "dimensionId");
        dim2.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        dim2.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        dim2.addAnd("reference", "", InventoryReference.class.getName());

        //Query Dimension3
        dim3 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        dim3.addTableAnd(InventoryItemDimension3Setup.class.getName(), "dimensionId", InventoryDimension3.class.getName(), "dimensionId");
        dim3.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        dim3.addTableAnd(InventoryReference.class.getName(), "itemId", InventoryItemMaster.class.getName(), "itemId");
        dim3.addAnd("reference", "", InventoryReference.class.getName());
    }

    @Override
    public void doRelation(EMCLookup lookup) {
        if (getLookupName(lookup).equals("item")) {
            setCompQuerys(lookup.getValue().toString());
        } else {
            setCompQuerys(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(), "itemId"));
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
        setCompQuerys((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "itemId"));
    }

    private void setCompQuerys(Object value) {
        if (!Functions.checkBlank(value)) {
            //Dimension1
            query = dim1;
            query.removeAnd("reference", InventoryReference.class.getName());
            query.addAnd("reference", value, InventoryReference.class.getName());
            getLookupFromName("dim1").get(0).setTheQuery(query);
            //Dimension2
            query = dim2;
            query.removeAnd("reference", InventoryReference.class.getName());
            query.addAnd("reference", value, InventoryReference.class.getName());
            getLookupFromName("dim2").get(0).setTheQuery(query);
            //Dimension3
            query = dim3;
            query.removeAnd("reference", InventoryReference.class.getName());
            query.addAnd("reference", value, InventoryReference.class.getName());
            getLookupFromName("dim3").get(0).setTheQuery(query);
        } else {
            getLookupFromName("dim1").get(0).setTheQuery(dim1);
            getLookupFromName("dim2").get(0).setTheQuery(dim2);
            getLookupFromName("dim3").get(0).setTheQuery(dim3);
        }
    }
}
