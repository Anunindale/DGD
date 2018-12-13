/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.courier.foreignkeys;

import emc.datatypes.debtors.courier.CourierId;
import emc.entity.debtors.DebtorsCourier;

/**
 * @description : Foreign key datatype for courierId on DebtorsCourier.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CourierIdFK extends CourierId {

    /** Creates a new instance of CourierIdFK */
    public CourierIdFK() {
        this.setRelatedTable(DebtorsCourier.class.getName());
        this.setRelatedField("courierId");
        this.setMandatory(false);
    }
}
