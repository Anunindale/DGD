/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.base.journals.journaldefinition.foreignkeys.JournalDefinitionIdFK;

/**
 * @description : Data type for postDatedPaymentJournalDef on DebtorsParameters.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PostDatedPaymentJournalDef extends JournalDefinitionIdFK {

    /** Creates a new instance of PostDatedPaymentJournalDef */
    public PostDatedPaymentJournalDef() {
        this.setEmcLabel("Post Dated Payment Journal Definition");
    }
}
