/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.barcodes;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension1GroupSetup;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryDimension3GroupSetup;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class BarcodeLookupRM extends EMCLookupRelationManager {

    private EMCQuery query;
    private EMCQuery dim1;
    private EMCQuery dim2;
    private EMCQuery dim3;

    public BarcodeLookupRM() {
        //Query Dimension1
        dim1 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
        dim1.addTableAnd(InventoryDimension1GroupSetup.class.getName(), "dimensionId", InventoryDimension1.class.getName(), "dimensionId");
        dim1.addTableAnd(InventoryItemMaster.class.getName(), "dimensionGroupId", InventoryDimension1GroupSetup.class.getName(), "dimension1Group");
        dim1.addAnd("itemId", "", InventoryItemMaster.class.getName());
        dim1.addGroupBy("dimensionId");

        //Query Dimension2
        dim2 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        dim2.addTableAnd(InventoryDimension2GroupSetup.class.getName(), "dimensionId", InventoryDimension2.class.getName(), "dimensionId");
        dim2.addTableAnd(InventoryItemMaster.class.getName(), "dimensionGroupId", InventoryDimension2GroupSetup.class.getName(), "dimension2Group");
        dim2.addAnd("itemId", "", InventoryItemMaster.class.getName());
        dim2.addGroupBy("dimensionId");

        //Query Dimension3
        dim3 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        dim3.addTableAnd(InventoryDimension3GroupSetup.class.getName(), "dimensionId", InventoryDimension3.class.getName(), "dimensionId");
        dim3.addTableAnd(InventoryItemMaster.class.getName(), "dimensionGroupId", InventoryDimension3GroupSetup.class.getName(), "dimension3Group");
        dim3.addAnd("itemId", "", InventoryItemMaster.class.getName());
        dim3.addGroupBy("dimensionId");
    }

    @Override
    public void doRelation(EMCLookup lookup) {
        if (getLookupName(lookup).equals("item")) {
            setCompQuerys(lookup.getValue().toString());
        }
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
        setCompQuerys((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "itemId"));
    }

    private void setCompQuerys(String value) {
        if (!Functions.checkBlank(value)) {
            //Dimension1
            query = dim1;
            query.removeAnd("itemId", InventoryItemMaster.class.getName());
            query.addAnd("itemId", value, InventoryItemMaster.class.getName());
            getLookupFromName("dim1").get(0).setTheQuery(query);
            //Dimension2
            query = dim2;
            query.removeAnd("itemId", InventoryItemMaster.class.getName());
            query.addAnd("itemId", value, InventoryItemMaster.class.getName());
            getLookupFromName("dim2").get(0).setTheQuery(query);
            //Dimension3
            query = dim3;
            query.removeAnd("itemId", InventoryItemMaster.class.getName());
            query.addAnd("itemId", value, InventoryItemMaster.class.getName());
            getLookupFromName("dim3").get(0).setTheQuery(query);
        } else {
            getLookupFromName("dim1").get(0).setTheQuery(dim1);
            getLookupFromName("dim2").get(0).setTheQuery(dim2);
            getLookupFromName("dim3").get(0).setTheQuery(dim3);
        }
    }
}
