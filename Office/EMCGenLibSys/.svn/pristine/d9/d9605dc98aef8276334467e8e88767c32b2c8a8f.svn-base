/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditclosereason.foreignkey;

import emc.datatypes.debtors.creditclosereason.CreditCloseReason;
import emc.entity.debtors.DebtorsClosedReason;

/**
 *
 * @author wikus
 */
public class CreditCloseReasonFKNM extends CreditCloseReason {

    public CreditCloseReasonFKNM() {
        this.setRelatedTable(DebtorsClosedReason.class.getName());
        this.setRelatedField("closedReasonId");
        this.setMandatory(false);
    }
}
