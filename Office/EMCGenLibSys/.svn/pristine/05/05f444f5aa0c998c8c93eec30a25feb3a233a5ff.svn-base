/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.basket.foreignkeys;

import emc.datatypes.debtors.basket.BasketId;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author asd_admin
 */
public class BasketIdFK extends BasketId {

    public BasketIdFK(){
        this.setRelatedTable(DebtorsBasketMaster.class.getName());
        this.setRelatedField("basketId");
        this.setNumberSeqAllowed(false);
        this.setDeleteAction(enumDeleteUpdateOptions.CLEARFIELD);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        
    }
}
