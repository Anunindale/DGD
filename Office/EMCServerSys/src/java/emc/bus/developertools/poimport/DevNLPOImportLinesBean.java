/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.poimport;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.constants.systemConstants;
import emc.entity.developertools.DevProgressItemConversionTable;
import emc.entity.developertools.poimport.DevNLPOImportLines;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : DevNLPOImportLinesBean
 *
 * @Date         : 13 Jul 2009
 * 
 * @Description  : Entity bean used to import old N & L PO Lines.
 *
 * @author       : riaan
 */
@Stateless
public class DevNLPOImportLinesBean extends EMCDataSourceBean implements DevNLPOImportLinesLocal {

    @EJB
    private POPPurchaseOrderLinesLocal poLinesBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;

    /** Creates a new instance of DevNLPOImportLinesBean. */
    public DevNLPOImportLinesBean() {
        this.setDataSourceClassName(DevNLPOImportLines.class.getName());
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DevNLPOImportLines importLine = (DevNLPOImportLines) iobject;

        importLine.setCompanyId(userData.getCompanyId());

        //Fetch correct item + dimensions
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProgressItemConversionTable.class.getName());
        query.addAnd("oldItem", importLine.getNinianItem());

        DevProgressItemConversionTable conversion = (DevProgressItemConversionTable) util.executeSingleResultQuery(query, userData);

        String itemRef = null;

        if (conversion != null) {
            itemRef = conversion.getEmcItem();
            importLine.setItemDimension1(conversion.getDimension1());
            importLine.setItemDimension2(conversion.getDimension2());
            importLine.setItemDimension3(conversion.getDimension3());
        } else {
            //Valid EMC Item
            itemRef = importLine.getNinianItem();
        }

        if (itemRef != null) {
            Object[] refInfo = refBean.checkItemReference(itemRef, userData);

            if (refInfo != null && !isBlank(refInfo[0])) {
                importLine.setItemId((String) refInfo[0]);

                InventoryItemMaster item = itemMasterBean.findItem((String) refInfo[0], userData);

                if (item != null) {
                    importLine.setUom(item.getBaseUOM());
                    
                    //Try to find combinations, otherwise update item master.
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                    query.addAnd("itemId", item.getItemId());
                    query.addAnd("dimension1Id", isBlank(importLine.getItemDimension1()) ? systemConstants.emcNA() : importLine.getItemDimension1());
                    query.addAnd("dimension2Id", isBlank(importLine.getItemDimension2()) ? systemConstants.emcNA() : importLine.getItemDimension2());
                    query.addAnd("dimension3Id", isBlank(importLine.getItemDimension3()) ? systemConstants.emcNA() : importLine.getItemDimension3());

                    InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations) util.executeSingleResultQuery(query, userData);

                    if (combination != null) {
                        importLine.setItemPrice(combination.getPurchasePrice());
                    } else {
                        importLine.setItemPrice(item.getPurchasePrice());
                    }
                }

                //query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
                //query.addAnd("warehouseId", item.getDefaultWarehouse());

                //InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);

                if (isBlank(importLine.getWarehouse())) {
                    importLine.setWarehouse(item.getDefaultWarehouse());
                }

            //Use master here if needed
//                query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
//                query.addAnd("purchaseOrderId", importLine.getPurchaseOrderId());
//                
//                POPPurchaseOrderMaster master = (POPPurchaseOrderMaster)util.executeGeneralSelectQuery(query, userData);      
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Item not found - " + importLine.getNinianItem(), userData);
                throw new EMCEntityBeanException(this);
            }


        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Item not found in EMC - " + importLine.getNinianItem(), userData);
            throw new EMCEntityBeanException(this);
        }

        importLine.setLineNo(getNextLineNo(importLine, userData));
        importLine.setNetAmount(importLine.getQuantity() * importLine.getItemPrice());
        return poLinesBean.insert(convertDataSourceToSuper(importLine, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return poLinesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return poLinesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    /** Gets next line number for the Purchase Order Master of the line being inserted. */
    private double getNextLineNo(DevNLPOImportLines importLine, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addFieldAggregateFunction("lineNo", "MAX");
        query.addAnd("purchaseOrderId", importLine.getPurchaseOrderId());

        Double ret = (Double) util.executeSingleResultQuery(query, userData);

        return ret == null ? 10 : ret + 10;
    }
}
