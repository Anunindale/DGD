/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.journals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.journals.DebtorsJournalsForm;
import emc.forms.inventory.display.journals.JournalsDRM;
import emc.framework.EMCCommandClass;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class ApprovalButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate masterDRM;

    /** Creates a new instance of ApprovalButton */
    public ApprovalButton(DebtorsJournalsForm form) {
        super("Approval", form);
        this.masterDRM = (emcDataRelationManagerUpdate) form.getDataManager();
        this.addMenuItem("Approve", null, -1005, false);
        this.addMenuItem("Unapprove", null, -1006, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if (theCmd.equals("Approve")) {
            boolean goOn = false;
            String journalNumber = (String) this.masterDRM.getFieldValueAt(this.masterDRM.getLastRowAccessed(), "journalNumber");
            DebtorsJournalMaster jMast = (DebtorsJournalMaster) masterDRM.getRowCache(-1);
            EMCCommandClass cmd = new EMCCommandClass(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.VALIDATE_DEBTORS_JOURNAL.toString());
            List toSend = new ArrayList();
            toSend.add(jMast);
            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterDRM.getUserData());
            if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                goOn = true;
            }

            if (journalNumber != null && !journalNumber.equals("") && goOn) {
                EMCCommandClass cmdApp = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.APPROVE_DEBTORS_JOURNAL.toString());

                ArrayList toSendApp = new ArrayList();
                toSendApp.add(journalNumber);

                EMCWSManager.executeGenericWS(cmdApp, toSendApp, this.masterDRM.getUserData());

                masterDRM.refreshRecordIgnoreFocus(-1);
            } else {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot approve the Journal.", masterDRM.getUserData());
            }
        } else if (theCmd.equals("Unapprove")) {
            String journalNumber = (String) this.masterDRM.getFieldValueAt(this.masterDRM.getLastRowAccessed(), "journalNumber");

            if (journalNumber != null && !journalNumber.equals("")) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.UNAPPROVE_DEBTORS_JOURNAL.toString());

                ArrayList toSend = new ArrayList();
                toSend.add(journalNumber);

                EMCWSManager.executeGenericWS(cmd, toSend, this.masterDRM.getUserData());

                masterDRM.refreshRecordIgnoreFocus(-1);
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Cannot approve an empty journal.", masterDRM.getUserData());
            }
        }

    }
}
