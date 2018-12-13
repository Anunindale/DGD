/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.developertools;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.constants.systemConstants;
import emc.datatypes.base.unitsofmeasureconversion.Conversion;
import emc.entity.developertools.DevNLPriceImportTable;
import emc.entity.developertools.DevProgressItemConversionTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : DevNLPriceImportTableBean
 *
 * @Date         : 24 Jul 2009
 * 
 * @Description  : Bean used for price imports.
 *
 * @author       : riaan
 */
@Stateless
public class DevNLPriceImportTableBean extends EMCEntityBean implements DevNLPriceImportTableLocal {

    @EJB
    private InventoryItemDimensionCombinationsLocal combinationsBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryReferenceLocal refBean;
    
    /** Creates a new instance of DevNLPriceImportTableBean. */
    public DevNLPriceImportTableBean() {

    }
    
    /** 
     * Updates prices in the system using the records in the DevNLPriceImportTable.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean updateItemPrices(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevNLPriceImportTable.class.getName());
        
        List<DevNLPriceImportTable> itemPrices = (List<DevNLPriceImportTable>)util.executeGeneralSelectQuery(query, userData);
        
        for (DevNLPriceImportTable itemPrice : itemPrices) {
            query = new EMCQuery(enumQueryTypes.SELECT, DevProgressItemConversionTable.class.getName());
            query.addAnd("oldItem", itemPrice.getOldItem());
            
            DevProgressItemConversionTable itemConversion = (DevProgressItemConversionTable)util.executeSingleResultQuery(query, userData);
            
            if (itemConversion != null) {
                Object[] refInfo = refBean.checkItemReference(itemConversion.getEmcItem(), userData);
                
                if (refInfo != null && refInfo[0] != null) {
                    InventoryItemMaster item = itemMasterBean.findItem((String)refInfo[0], userData);
                    
                    if (item != null) {
                        //Try to find combinations, otherwise update item master.
                        query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class.getName());
                        query.addAnd("itemId", item.getItemId());
                        query.addAnd("dimension1Id", isBlank(itemConversion.getDimension1()) ? systemConstants.emcNA() : itemConversion.getDimension1());
                        query.addAnd("dimension2Id", isBlank(itemConversion.getDimension2()) ? systemConstants.emcNA() : itemConversion.getDimension2());
                        query.addAnd("dimension3Id", isBlank(itemConversion.getDimension3()) ? systemConstants.emcNA() : itemConversion.getDimension3());
                    
                        InventoryItemDimensionCombinations combination = (InventoryItemDimensionCombinations)util.executeSingleResultQuery(query, userData);
                        
                        if (combination != null) {
                            combination.setCostPrice(itemPrice.getPrice());
                            combination.setPurchasePrice(itemPrice.getPrice());
                            
                            combinationsBean.update(combination, userData);
                        } else {
                            if (util.compareDouble(item.getPurchasePrice(), 0) == 0) {
                                item.setPurchasePrice(itemPrice.getPrice());
                                itemMasterBean.update(item, userData);
                            }
                        }
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "EMC Item not found - " + itemConversion.getEmcItem() , userData);
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "EMC Item not found - " + itemConversion.getEmcItem() , userData);
                }
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Old item not found - " + itemPrice.getOldItem(), userData);
            }
        }
       
        return true;
    }
}
