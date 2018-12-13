/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.safetystockgenerationrules;

import emc.entity.inventory.InventorySafetyStockGenerationRules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventorySafetyStockGenerationRulesBean extends EMCEntityBean implements InventorySafetyStockGenerationRulesLocal {

    public InventorySafetyStockGenerationRules getRuleSet(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySafetyStockGenerationRules.class);
        return (InventorySafetyStockGenerationRules) util.executeSingleResultQuery(query, userData);
    }
}
