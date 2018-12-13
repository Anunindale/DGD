/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.journaldefinitions;

import emc.enums.base.journals.Modules;
import emc.enums.debtors.journals.DebtorsJournalDirection;
import emc.enums.debtors.journals.DebtorsJournalType;
import emc.forms.app.journals.journaldefinitions.JournalDefinitionsForm;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class JournalDefinitionForm  extends JournalDefinitionsForm {

    /** Creates a new instance of JournalDefinitionForm. */
    public JournalDefinitionForm(EMCUserData userData){
        super(userData, Modules.DEBTORS);
    }

    @Override
    protected String[] getJournalDirections() {
        return new String[] {DebtorsJournalDirection.CREDIT.toString(), DebtorsJournalDirection.DEBIT.toString()};
    }

    @Override
    protected String[] getJournalTypes() {
        return new String[] {DebtorsJournalType.INTEREST.toString(), DebtorsJournalType.JOURNAL_CREDIT.toString(), 
        DebtorsJournalType.JOURNAL_DEBIT.toString(), DebtorsJournalType.PAYMENT.toString(), DebtorsJournalType.RETURNED_PAYMENTS.toString(),
        DebtorsJournalType.SETTLEMENT_DISCOUNT.toString(), DebtorsJournalType.TRANSFER.toString()};
    }
}

