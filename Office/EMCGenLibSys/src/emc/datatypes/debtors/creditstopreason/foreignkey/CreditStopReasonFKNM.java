/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.creditstopreason.foreignkey;

import emc.datatypes.debtors.creditstopreason.CreditStopReason;
import emc.entity.debtors.DebtorsCreditStopReason;

/**
 *
 * @author wikus
 */
public class CreditStopReasonFKNM extends CreditStopReason {

    public CreditStopReasonFKNM() {
        this.setRelatedTable(DebtorsCreditStopReason.class.getName());
        this.setRelatedField("reason");
        this.setMandatory(false);
    }
}
