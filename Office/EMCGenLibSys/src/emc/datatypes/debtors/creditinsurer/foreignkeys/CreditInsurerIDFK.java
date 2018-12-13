/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditinsurer.foreignkeys;

import emc.datatypes.debtors.creditinsurer.CreditInsurerID;
import emc.entity.debtors.DebtorsCreditInsurer;

/**
 * @description : Foreign key data type for creditInsurerId on DebtorsCreditInsurer.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditInsurerIDFK extends CreditInsurerID {

    /** Creates a new instance of CreditInsurerIDFK */
    public CreditInsurerIDFK() {
        this.setRelatedField("creditInsurerId");
        this.setRelatedTable(DebtorsCreditInsurer.class.getName());
        this.setMandatory(false);
    }
}
