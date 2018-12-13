/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.transactions.stockreservationrules;

import emc.entity.inventory.transactions.InventorySummary;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.math.EMCMath;
import java.util.Map;

/**
 * @name        : DefaultReservationRule
 *
 * @description : This class specifies the default stock reservation rule.
 *
 * @date        : 26 Oct 2009
 *
 * @author      : riaan
 */
public class DefaultReservationRule implements StockReservationRule {

    private EMCMath math = new EMCMath();

    /** Creates a new instance of DefaultReservationRule. */
    public DefaultReservationRule() {

    }

    @Override
    public double calcRuleQuantityToReserve(double totalAvailable, double requiredQuantity, Map<String, Object> parameters, EMCUserData userData) {
        if (math.compareDouble(requiredQuantity, totalAvailable) < 0) {
            return requiredQuantity;
        } else {
            return totalAvailable;
        }
    }
}
