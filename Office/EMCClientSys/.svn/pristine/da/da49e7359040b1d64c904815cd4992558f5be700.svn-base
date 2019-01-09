/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactions.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.functions.math.EMCMath;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DebtorsBalanceButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate drm;

    public DebtorsBalanceButton(BaseInternalFrame form) {
        super("Balance", form);
        this.addMenuItem("Reference", null, 0, false);
        this.addMenuItem("Total", null, 0, false);
        this.drm = (emcDataRelationManagerUpdate) form.getDataManager();
    }

    @Override
    public void executeCmd(String theCmd) {
        String theMethod = null;
        List toSend = new ArrayList();
        String message = null;
        if (theCmd.equals("Reference")) {
            theMethod = ServerDebtorsMethods.CALCULATE_REFERENCE_BALANCE.toString();
            toSend.add(drm.getLastFieldValueAt("referenceNumber"));
            message = "The balance for " + toSend.get(0) + " is: R ";
        } else if (theCmd.equals("Total")) {
            theMethod = ServerDebtorsMethods.CALCULATE_TOTAL_BALANCE.toString();
            message = "The total balance is: R ";
        }
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), theMethod);
        double balance = (Double) EMCWSManager.executeGenericWS(cmd, toSend, drm.getUserData()).get(1);
        EMCDialogFactory.createMessageDialog(drm.getTheForm(), "Balance", message + new EMCMath().round(balance, 2));
    }
}
