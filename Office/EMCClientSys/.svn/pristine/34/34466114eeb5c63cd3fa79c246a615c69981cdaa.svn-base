/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.allocationimportfaillog.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class ImportButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate failLogDRM;

    /** Creates a new instance of ImportButton. */
    public ImportButton(emcDataRelationManagerUpdate failLogDRM) {
        super("Import", failLogDRM.getTheForm());
        this.failLogDRM = failLogDRM;

        this.addMenuItem("Selected", null, 0, false);
        this.addMenuItem("All", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        EMCUserData userData = failLogDRM.getUserData();

        if ("Selected".equals(theCmd)) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.IMPORT_LINE_FROM_FAIL_LOG);

            List toSend = new ArrayList();
            toSend.add(failLogDRM.getRowCache(failLogDRM.getLastRowAccessed()));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
                utilFunctions.logMessage(Level.SEVERE, "Succesfully imported line.", userData);
            } else {
                utilFunctions.logMessage(Level.SEVERE, "Failed to import line.", userData);
            }
        } else if ("All".equals(theCmd)) {
            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.IMPORT_ALL_FROM_FAIL_LOG);

            List toSend = new ArrayList();
            toSend.add(failLogDRM.getLastFieldValueAt("importCode"));

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
                utilFunctions.logMessage(Level.SEVERE, "Succesfully imported lines.", userData);
            } else {
                utilFunctions.logMessage(Level.SEVERE, "Failed to import lines.", userData);
            }
        } else {
            super.executeCmd(theCmd);
        }
    }

}
