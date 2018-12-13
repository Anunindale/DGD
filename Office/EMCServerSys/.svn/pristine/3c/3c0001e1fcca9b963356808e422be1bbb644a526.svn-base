/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.dimensions.DimensionsEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;


/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimensionGroupBean extends EMCEntityBean implements InventoryItemDimensionGroupLocal {

    /** Creates a new instance of InventoryItemDimensionGroupBean */
    public InventoryItemDimensionGroupBean() {

    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        InventoryItemDimensionGroup dim = (InventoryItemDimensionGroup) theRecord;
                
        if (ret) {
            //Dimension "Active" fields may not be changed when the dimension group is in use.
            if (fieldNameToValidate.contains("Active")) {
                if (checkDimensionGroupUsed(dim.getItemDimensionGroupId(), userData)) {
                    Logger.getLogger("emc").log(Level.WARNING, "The dimension group is already in use, you cannot change it.", userData);
                    return false;
                }
//                try {
//                    DefaultMutableTreeNode tree = super.testRelations(enumPersistOptions.DELETE, (InventoryItemDimensionGroup) theRecord, userData);
//                    Enumeration e = tree.children();
//                    while (e.hasMoreElements()) {
//                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
//                        if (node == tree.getChildAt(1)) {
//                            if (node.getChildCount() > 1) {
//                                Logger.getLogger("emc").log(Level.WARNING, "The dimension group is already in use, you cannot change it.", userData);
//                                ret = false;
//                            }
//                        } else if (node.getDepth() != 0) {
//                            Logger.getLogger("emc").log(Level.WARNING, "The dimension group is already in use, you cannot change it.", userData);
//                            ret = false;
//                        }
//                    }
//                } catch (Exception e) {
//                //New records throws NullPointerExceptions
//                }
            }
            /*if (ret && (fieldNameToValidate.equals("dim1Active") || fieldNameToValidate.equals("dim2Active") || fieldNameToValidate.equals("dim3Active"))) {
               if (!(dim.getDim1Active() || dim.getDim2Active() || dim.getDim3Active())) {
                    dim.setDimensionCost(false);
                    return dim;
                }
            }*/
            if (ret && fieldNameToValidate.equals("dimensionCost")) {
                if (!(dim.getDim1Active() || dim.getDim2Active() || dim.getDim3Active())) {
                    Logger.getLogger("emc").log(Level.WARNING, "At least one of the dimensions has to be active before you can set this field.", userData);
                    ret = false;
                }
            }
        }
        return ret;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.delete(dobject, userData);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Checks if the given dimensiuon is active on the item
     * @param itemId
     * @param dimension
     * @param userData
     * @return
     */
    public boolean isDimensionActiveOnItem(String itemId, DimensionsEnum dimension, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionGroup.class.getName());
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemDimensionGroupId", InventoryItemDimensionGroup.class.getName(), "dimensionGroup");
        query.addAnd("itemId", itemId, InventoryItemMaster.class.getName());
        InventoryItemDimensionGroup dimGroup = (InventoryItemDimensionGroup) util.executeSingleResultQuery(query, userData);
        switch (dimension) {
            case BATCH:
                return dimGroup.getBatchNumberActive();
            case DIMENSION3:
                return dimGroup.getDim3Active();
            case DIMENSION1:
                return dimGroup.getDim1Active();
            case LOCATION:
                return dimGroup.getLocationActive();
            case PALLET:
                return dimGroup.getPalletIdActive();
            case SERIAL:
                return dimGroup.getSerialNumberActive();
            case DIMENSION2:
                return dimGroup.getDim2Active();
            case WAREHOUSE:
                return dimGroup.getWarehouseActive();
        }
        return false;
    }
    
    /** Checks whether any items with the given dimension group are in use. */
    private boolean checkDimensionGroupUsed(String dimensionGroupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
        
        EMCQuery itemQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
        itemQuery.addField("itemId");
        itemQuery.addAnd("dimensionGroup", dimensionGroupId);
        
        query.addAnd("itemId", itemQuery, EMCQueryConditions.IN);
        
        return util.exists(query, userData);
    }
}

