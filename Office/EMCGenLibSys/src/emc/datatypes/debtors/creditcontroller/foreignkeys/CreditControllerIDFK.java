/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditcontroller.foreignkeys;

import emc.datatypes.debtors.creditcontroller.CreditControllerID;
import emc.entity.debtors.DebtorsCreditController;

/**
 * @description : Foreign key data type for creditInsurerId on DebtorsCreditInsurer.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditControllerIDFK extends CreditControllerID {

    /** Creates a new instance of CreditInsurerIDFK */
    public CreditControllerIDFK() {
        this.setRelatedField("creditControllerId");
        this.setRelatedTable(DebtorsCreditController.class.getName());
        this.setMandatory(false);
    }
}
