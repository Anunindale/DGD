/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.pricearangements;

import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPPriceArangementsLocal extends EMCEntityBeanLocalInterface {

    public BigDecimal findItemPrice(String customerId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException;

    public boolean importPriceArangements(List<Object> theData, EMCUserData userData) throws EMCEntityBeanException;

    public BigDecimal findItemPrice(Connection conn, InventoryItemMaster item, InventoryItemDimensionCombinations combination, InventoryCostingGroup costingGroup,
            SOPCustomers customer, SOPParameters param, java.lang.String priceGroup, String customerId, String itemId, String dimension1, String dimension2, String dimension3,
            Date lineDate, BigDecimal quantity, EMCUserData userData) throws EMCEntityBeanException;
}
