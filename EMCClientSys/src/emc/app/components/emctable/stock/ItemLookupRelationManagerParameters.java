/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.emctable.stock;

import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;

/**
 * This is a helper class containing parameters for the ItemLookupRelationManager.
 * Null values may be used, but they won't be added to the lookup relation manager.
 * @author riaan
 */
public class ItemLookupRelationManagerParameters {

    private String itemField;
    private String dim1Field;
    private String dim2Field;
    private String dim3Field;
    private EMCLookupJTableComponent tableItemLookup;
    private EMCLookupFormComponent formItemLookup;
    private EMCLookupJTableComponent tableDim1Lookup;
    private EMCLookupFormComponent formDim1Lookup;
    private EMCLookupJTableComponent tableDim2Lookup;
    private EMCLookupFormComponent formDim2Lookup;
    private EMCLookupJTableComponent tableDim3Lookup;
    private EMCLookupFormComponent formDim3Lookup;
    private EMCQuery dimension1Query;
    private EMCQuery dimension2Query;
    private EMCQuery dimension3Query;

    /** Creates a new instance of ItemLookupRelationManagerParameters. */
    public ItemLookupRelationManagerParameters() {
        initQueries();
    }

    public String getDim1Field() {
        return dim1Field;
    }

    public void setDim1Field(String dim1Field) {
        this.dim1Field = dim1Field;
    }

    public String getDim2Field() {
        return dim2Field;
    }

    public void setDim2Field(String dim2Field) {
        this.dim2Field = dim2Field;
    }

    public String getDim3Field() {
        return dim3Field;
    }

    public void setDim3Field(String dim3Field) {
        this.dim3Field = dim3Field;
    }

    public String getItemField() {
        return itemField;
    }

    public void setItemField(String itemField) {
        this.itemField = itemField;
    }

    public EMCLookupFormComponent getFormDim1Lookup() {
        return formDim1Lookup;
    }

    public void setFormDim1Lookup(EMCLookupFormComponent formDim1Lookup) {
        formDim1Lookup.setTheQuery(dimension1Query);
        this.formDim1Lookup = formDim1Lookup;
    }

    public EMCLookupFormComponent getFormDim2Lookup() {
        return formDim2Lookup;
    }

    public void setFormDim2Lookup(EMCLookupFormComponent formDim2Lookup) {
        formDim2Lookup.setTheQuery(dimension2Query);
        this.formDim2Lookup = formDim2Lookup;
    }

    public EMCLookupFormComponent getFormDim3Lookup() {
        return formDim3Lookup;
    }

    public void setFormDim3Lookup(EMCLookupFormComponent formDim3Lookup) {
        formDim3Lookup.setTheQuery(dimension3Query);
        this.formDim3Lookup = formDim3Lookup;
    }

    public EMCLookupFormComponent getFormItemLookup() {
        return formItemLookup;
    }

    public void setFormItemLookup(EMCLookupFormComponent formItemLookup) {
        this.formItemLookup = formItemLookup;
    }

    public EMCLookupJTableComponent getTableDim1Lookup() {
        return tableDim1Lookup;
    }

    public void setTableDim1Lookup(EMCLookupJTableComponent tableDim1Lookup) {
        tableDim1Lookup.setTheQuery(dimension1Query);
        this.tableDim1Lookup = tableDim1Lookup;
    }

    public EMCLookupJTableComponent getTableDim2Lookup() {
        return tableDim2Lookup;
    }

    public void setTableDim2Lookup(EMCLookupJTableComponent tableDim2Lookup) {
        tableDim2Lookup.setTheQuery(dimension2Query);
        this.tableDim2Lookup = tableDim2Lookup;
    }

    public EMCLookupJTableComponent getTableDim3Lookup() {
        return tableDim3Lookup;
    }

    public void setTableDim3Lookup(EMCLookupJTableComponent tableDim3Lookup) {
        tableDim3Lookup.setTheQuery(dimension3Query);
        this.tableDim3Lookup = tableDim3Lookup;
    }

    public EMCLookupJTableComponent getTableItemLookup() {
        return tableItemLookup;
    }

    public void setTableItemLookup(EMCLookupJTableComponent tableItemLookup) {
        this.tableItemLookup = tableItemLookup;
    }

    /** This method creates the queries used on by the lookups. */
    private void initQueries() {
        dimension1Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class.getName());
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension1Setup.class.getName(), "itemId");
        dimension1Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimension1Id");
        dimension1Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension1Query.addGroupBy("dimensionId");

        dimension2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class.getName());
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension2Setup.class.getName(), "itemId");
        dimension2Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimension2Id");
        dimension2Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension2Query.addGroupBy("dimensionId");

        dimension3Query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class.getName());
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "itemId", InventoryItemDimension3Setup.class.getName(), "itemId");
        dimension3Query.addTableAnd(InventoryItemDimensionCombinations.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimension3Id");
        dimension3Query.addTableAnd(InventoryDimension3.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimensionId");
        dimension3Query.addAnd("active", true, InventoryItemDimensionCombinations.class.getName());
        dimension3Query.addGroupBy("dimensionId");
        dimension3Query.addOrderBy("sortCode", InventoryDimension3.class.getName());
    }
}
