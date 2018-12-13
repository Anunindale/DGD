/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.transactionperiodsummary.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class GenerateOpeningBalancesButton extends emcMenuButtonList {

    private GLTransactionPeriodSummaryDRM periodSummaryDRM;

    /** Creates a new instance of GenerateOpeningBalancesButton. */
    public GenerateOpeningBalancesButton(GLTransactionPeriodSummaryDRM periodSummaryDRM) {
        super("Generate", periodSummaryDRM.getTheForm());

        this.addMenuItem("Opening Balances", null, 0, false);

        this.periodSummaryDRM = periodSummaryDRM;
    }

    @Override
    public void executeCmd(String theCmd) {
        if (theCmd.equals("Opening Balances")) {
            new OpeningBalanceGenerationDialog().setVisible(true);
        }
    }

    private class OpeningBalanceGenerationDialog extends emcJDialog {

        private EMCLookup lkpFinancialPeriod;

        /** Creates a new instance of OpeningBalanceDialog. */
        public OpeningBalanceGenerationDialog() {
            super(null, "Generate Opening Balances");

            initDialog();
        }

        /** Initializes the dialog. */
        private void initDialog() {
            this.setLayout(new BorderLayout());

            final EMCUserData userData = periodSummaryDRM.getUserData();

            lkpFinancialPeriod = new EMCLookup(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
            lkpFinancialPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", userData));

            Component[][] components = new Component[][]{
                {new emcJLabel("Opening Period"), lkpFinancialPeriod}
            };

            this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.CENTER);

            emcJButton btnOK = new emcJButton("OK") {

                @Override
                public void doActionPerformed(ActionEvent evt) {
                    super.doActionPerformed(evt);

                    if (Functions.checkBlank(lkpFinancialPeriod.getValue())) {
                        utilFunctions.logMessage(Level.SEVERE, "Please specify an opening period.", userData);
                    } else {
                        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.GENERAL_LEDGER.getId(), ServerGLMethods.APPROVE_JOURNAL.toString());

                        List toSend = new ArrayList();
                        toSend.add(lkpFinancialPeriod.getValue());

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                            utilFunctions.logMessage(Level.INFO, "Opening period balances generated successfully.", userData);
                            periodSummaryDRM.refreshData();
                            OpeningBalanceGenerationDialog.this.dispose();
                        } else {
                            utilFunctions.logMessage(Level.SEVERE, "Failed to generate opening period balances.", userData);
                        }
                    }
                }
            };

            List<emcJButton> buttons = new ArrayList<emcJButton>();
            buttons.add(btnOK);

            this.add(emcSetGridBagConstraints.createButtonPanel(buttons, false), BorderLayout.EAST);
            this.pack();
        }
    }
}
