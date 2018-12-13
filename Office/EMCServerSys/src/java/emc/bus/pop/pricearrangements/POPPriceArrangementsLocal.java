package emc.bus.pop.pricearrangements;

import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPSuppliers;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import javax.ejb.Local;

@Local
/** 
 *
 * @author riaan
 */
public interface POPPriceArrangementsLocal extends EMCEntityBeanLocalInterface {

    public BigDecimal findItemPrice(Connection conn, InventoryItemMaster item, InventoryItemDimensionCombinations combination, InventoryCostingGroup costingGroup,
            POPSuppliers supplier, POPParameters param, String priceGroup, String customerId, String itemId, String dimension1, String dimension2, String dimension3,
            Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException;

    public BigDecimal findItemPrice(String itemId, String dimension1, String dimension2, String dimension3, Date lineDate,
            BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException;

    public BigDecimal findItemPrice(String supplierId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate,
            BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException;
}
