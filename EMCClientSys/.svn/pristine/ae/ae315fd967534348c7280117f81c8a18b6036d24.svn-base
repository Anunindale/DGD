/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.postdatedpayment.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.DebtorsParameters;
import emc.enums.base.journals.Modules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.JournalDefinitions;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @description : This dialog is used to specify a date range when processing post dated payments.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ProcessPaymentDialog extends emcJDialog {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate drm;
    private EMCDatePicker dpkFrom = new EMCDatePicker();
    private EMCDatePicker dpkTo = new EMCDatePicker();
    private EMCLookup lkpJournalDefinition;

    /** Creates a new instance of ProcessPaymentDialog */
    public ProcessPaymentDialog(emcDataRelationManagerUpdate drm) {
        super(null, "Process Payments");

        this.drm = drm;
        this.userData = drm.getUserData();

        this.setLayout(new BorderLayout());
        setupJournalDefLookup();
        initDialog();
    }

    /** Initializes journal definition lookup and sets value to default specified on Debtors parameters. */
    private void setupJournalDefLookup() {
        this.lkpJournalDefinition = new EMCLookup(new JournalDefinitions());
        this.lkpJournalDefinition.setPopup(new EMCLookupPopup(new BaseJournalDefinitionTable(), "journalDefinitionId", userData));
        this.lkpJournalDefinition.getTheQuery().addAnd("journalModule", Modules.DEBTORS.toString());
        
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.GETDATA_DEBTORSPARAMETERS);

        EMCUserData copyUd = userData.copyUserData();

        List toSend = new ArrayList();
        //Only get 1 record
        toSend.add(0);
        toSend.add(1);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, copyUd);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof DebtorsParameters) {
            lkpJournalDefinition.setValue(((DebtorsParameters)toSend.get(1)).getPostDatedPaymentJournalDef());
        }
    }
    
    /** Adds components to dialog. */
    private void initDialog() {
        Component[][] components = new Component[][] {
            {new emcJLabel("From Date"), dpkFrom},
            {new emcJLabel("To Date"), dpkTo},
            {new emcJLabel("Journal Definition"), lkpJournalDefinition}
        };

        this.add(BorderLayout.CENTER, emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true));

        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new ProcessPaymentButton());
        buttons.add(new CancelButton());

        this.add(BorderLayout.EAST, emcSetGridBagConstraints.createButtonPanel(buttons, false));

        this.pack();
    }

    /** Checks that fields have valid values. */
    private boolean validatePayment() {
        Date from = dpkFrom.getDate();
        Date to = dpkTo.getDate();
        if (from == null || to == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please specify both from and to dates.", userData);
            return false;
        } else if (from.after(to)) {
            Logger.getLogger("emc").log(Level.SEVERE, "From date may not be after to date.");
            return false;
        } else if (Functions.checkBlank(lkpJournalDefinition.getValue())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please specify a journal definition.");
            return false;
        } else {
            return true;
        }
    }

    class ProcessPaymentButton extends emcJButton {

        /** Creates a new instance of ProcessButton. */
        public ProcessPaymentButton() {
            super("Process");
        }

        @Override
        public void doActionPerformed(ActionEvent evt) {
            super.doActionPerformed(evt);

            if (validatePayment()) {
                EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.PROCESS_POST_DATED_PAYMENTS);

                List toSend = new ArrayList();
                toSend.add(dpkFrom.getDate());
                toSend.add(dpkTo.getDate());
                toSend.add(lkpJournalDefinition.getValue());

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    Logger.getLogger("emc").log(Level.INFO, "Payments processed successfully.");
                    drm.refreshData();
                    ProcessPaymentDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to process payments.");
                }
            }
        }
    }

    class CancelButton extends emcJButton {

        /** Creates a new instance of CancelButton. */
        public CancelButton() {
            super("Cancel");
        }

        @Override
        public void doActionPerformed(ActionEvent evt) {
            super.doActionPerformed(evt);

            if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel?") == JOptionPane.YES_OPTION) {
                ProcessPaymentDialog.this.dispose();
            }
        }
    }
}
