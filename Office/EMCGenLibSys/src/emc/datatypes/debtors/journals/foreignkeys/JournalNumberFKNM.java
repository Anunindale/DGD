/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.journals.foreignkeys;

import emc.datatypes.base.journals.superclass.JournalNumber;
import emc.datatypes.debtors.journals.*;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 * @description : Foreign key data type for journalNumber on DebtorsJournalMaster.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class JournalNumberFKNM extends JournalNumber {

    /** Creates a new instance of JournalNumberFK */
    public JournalNumberFKNM() {
        this.setNumberSeqAllowed(false);
        this.setRelatedField("journalNumber");
        this.setRelatedTable(DebtorsJournalMaster.class.getName());
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setMandatory(false);
        this.setColumnWidth(30);
    }
}
