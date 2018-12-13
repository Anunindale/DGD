/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.transactions.stockreservationrules;

import emc.entity.inventory.transactions.InventorySummary;
import emc.framework.EMCUserData;
import java.util.Map;

/**
 *
 * @author riaan
 */
public interface StockReservationRule {

    /**
     *
     * @param totalAvailable Total items available for the stock that should be reserved.
     * @param requiredQuantity The quantity required by the order for  which stock should be reserved.
     * @param parameters HashMap containing parameters to be used by this reservation rule.
     * @return The actual quantity to be reserved, based on this rule.
     */
    public double calcRuleQuantityToReserve(double totalAvailable, double requiredQuantity, Map<String, Object> parameters, EMCUserData userData);
}
