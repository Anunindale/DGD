/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemmaster;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.entity.sop.SOPDiscountSetup;
import emc.entity.sop.SOPPriceArangements;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.discountsetup.ItemSelectionType;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;

import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class ItemMasterDRM extends StockDRM {

    private EMCLookupRelationManager lookupRelationManager;
    private EMCCommandClass cmd = new EMCCommandClass(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId(), emc.enums.modules.enumEMCModules.INVENTORY.getId(), emc.methods.inventory.ServerInventoryMethods.POPULATE_NEW_ITEM.toString());

    /** Creates a new instance of ItemMasterDRM */
    public ItemMasterDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    public void setLookupRelationManager(EMCLookupRelationManager lookupRelationManager) {
        this.lookupRelationManager = lookupRelationManager;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex, addRowAfter);
        List ret = EMCWSManager.executeGenericWS(cmd, new ArrayList(), this.getUserData());
        if (ret.size() > 1) {
            InventoryItemMaster mast = (InventoryItemMaster) ret.get(1);
            if (!Functions.checkBlank(mast.getBaseUOM())) {
                this.setFieldValueAt(this.getLastRowAccessed(), "baseUOM", mast.getBaseUOM());
            }
            if (!Functions.checkBlank(mast.getDefaultWarehouse())) {
                this.setFieldValueAt(this.getLastRowAccessed(), "defaultWarehouse", mast.getDefaultWarehouse());
            }
            if (!Functions.checkBlank(mast.getPurchaseVatCode())) {
                this.setFieldValueAt(this.getLastRowAccessed(), "purchaseVatCode", mast.getPurchaseVatCode());
            }
            if (!Functions.checkBlank(mast.getSellingVatCode())) {
                this.setFieldValueAt(this.getLastRowAccessed(), "sellingVatCode", mast.getSellingVatCode());
            }
            if (!Functions.checkBlank(mast.getDimensionGroup())) {
                this.setFieldValueAt(this.getLastRowAccessed(), "dimensionGroup", mast.getDimensionGroup());
            }
            this.setEditFlag(false);
        }

    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        lookupRelationManager.doRowChanged(this);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object itemId;
        Object description;
        Object itemReference;
        Object bomId;
        List x;
        EMCQuery query;
        switch (Index) {
            case 0:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension1Setup.class.getName());
                    query.addAnd("itemId", itemId);
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            case 1:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension2Setup.class.getName());
                    query.addAnd("itemId", itemId);
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            case 2:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimension3Setup.class.getName());
                    query.addAnd("itemId", itemId);
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;

            case 3:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    x = new ArrayList();
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                    query.addAnd("companyId", formUserData.getCompanyId());
                    query.addAnd("itemId", itemId.toString());
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            case 4:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
                    query.addAnd("itemId", itemId);
                    query.addOrderBy("reference");
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");

                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            case 5:
                //Moved generation code to Item Master form
                break;
            case 6:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
                    query.addAnd("itemId", itemId);
                    query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    query.addOrderBy("customerType");
                    query.addOrderBy("customerId");
                    query.addOrderBy("priceGroup");
                    query.addOrderBy("itemId");
                    query.addOrderBy("dimension1SortCode");
                    query.addOrderBy("dimension3SortCode");
                    query.addOrderBy("dimension2SortCode");
                    query.addOrderBy("fromDate");
                    query.addOrderBy("toDate");
                    query.addOrderBy("quantity");
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            case 7:
                //Item discounts
                EMCQuery discountQuery = new EMCQuery(enumQueryTypes.SELECT, SOPDiscountSetup.class);
                discountQuery.addAnd("itemId", this.getLastFieldValueAt("itemId"));
                formUserData.setUserData(0, discountQuery);
                break;
            case 8:
                //All applicable discounts
                discountQuery = new EMCQuery(enumQueryTypes.SELECT, SOPDiscountSetup.class);
                discountQuery.openConditionBracket(EMCQueryBracketConditions.NONE);
                discountQuery.addAnd("itemId", this.getLastFieldValueAt("itemId"));
                //Discount group.  Only include this if it has a value.
                if (!Functions.checkBlank(this.getLastFieldValueAt("sellingDiscountGroup"))) {
                    discountQuery.addOr("itemDiscGroup", this.getLastFieldValueAt("sellingDiscountGroup"));
                }
                //All
                discountQuery.addOr("itemSelectType", ItemSelectionType.ALL.toString());
                discountQuery.closeConditionBracket();

                formUserData.setUserData(0, discountQuery);
                break;

            case 13:
            //Fall through
            case 14:
                bomId = super.getFieldValueAt(this.getLastRowAccessed(), "productionBOMId");
                if (bomId != null) {
                    x = new ArrayList();
                    x.add(null);
                    x.add(null);
                    x.add(bomId);
                    formUserData.setUserData(x);
                }
                break;


            case 17:
                itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemReference");
                if (itemId != null && description != null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, POPPriceArrangements.class);
                    query.addAnd("itemId", itemId);
                    query.addAnd("toDate", Functions.nowDate(), EMCQueryConditions.GREATER_THAN_EQ);
                    query.addOrderBy("supplierType");
                    query.addOrderBy("supplierId");
                    query.addOrderBy("priceGroup");
                    query.addOrderBy("itemId");
                    query.addOrderBy("dimension1SortCode");
                    query.addOrderBy("dimension3SortCode");
                    query.addOrderBy("dimension2SortCode");
                    query.addOrderBy("fromDate");
                    query.addOrderBy("toDate");
                    query.addOrderBy("quantity");
                    x = new ArrayList();
                    x.add(0, query);
                    x.add(1, "");
                    x.add(2, description.toString());
                    x.add(3, (Functions.checkBlank(itemReference) ? itemId.toString() : itemReference.toString()));
                    x.add(4, itemId);
                    formUserData.setUserData(x);
                }
                break;
            default:
                break;
        }
        return formUserData;
    }

    @Override
    public void updatePersist(int rowIndex) {
        super.updatePersist(rowIndex);
        if (!this.isRowUpdated()) {
            this.refreshRecord(rowIndex);
        }
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        //Confirm before changing item reference
        if ("itemReference".equals(columnIndex) && (Long) getLastFieldValueAt("recordID") != 0 && !Functions.checkObjectsEqual(aValue, this.getFieldValueAt(rowIndex, columnIndex)) && EMCDialogFactory.createQuestionDialog(this.getTheForm(), "Change Reference?", "Are you sure that you want to change the item reference?") != JOptionPane.YES_OPTION) {
            return;
        }

        super.setFieldValueAt(rowIndex, columnIndex, aValue);
    }
}
