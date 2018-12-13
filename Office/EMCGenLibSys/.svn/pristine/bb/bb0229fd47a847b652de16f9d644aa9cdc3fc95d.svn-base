/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditnotereasons.foreignkeys;

import emc.datatypes.debtors.creditnotereasons.ReasonCode;
import emc.entity.debtors.DebtorsCreditNoteReasons;

/**
 *
 * @author riaan
 */
public class ReasonCodeFK extends ReasonCode {

    /** Creates a new instance of ReasonCodeFK. */
    public ReasonCodeFK() {
        this.setRelatedTable(DebtorsCreditNoteReasons.class.getName());
        this.setRelatedField("reasonCode");
        this.setMandatory(false);
    }
}
