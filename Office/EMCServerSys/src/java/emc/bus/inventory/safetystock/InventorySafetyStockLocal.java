/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.safetystock;

import emc.entity.inventory.InventorySafetyStockGenerationRules;
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
public interface InventorySafetyStockLocal extends EMCEntityBeanLocalInterface {

    public boolean generateSafetyStock(InventorySafetyStockGenerationRules rules, EMCUserData userData) throws EMCEntityBeanException;

    public BigDecimal getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, EMCUserData userData);

    public List<BigDecimal> getSafetyStock(Date requiredDate, String itemId, String dimension1, String dimension2, String dimension3, String serialNo, Connection conn, BigDecimal item, BigDecimal combination, EMCUserData userData);

    public boolean deleteSafetyStock(emc.entity.inventory.InventorySafetyStockGenerationRules rules, java.util.Date deleteHistoryOlderThan, java.lang.String safetyStockType, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
