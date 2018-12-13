/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.stocktakeunreserved.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class UnreservedStockTakeDeleteDialog extends emcJDialog {

    private emcDataRelationManagerUpdate dataManager;
    private EMCDatePicker dpDeletionDate;
    private emcYesNoComponent ynDeleteAllCompleted;

    public UnreservedStockTakeDeleteDialog(EMCDesktop owner, emcDataRelationManagerUpdate dataManager) {
        super(owner, "Delete Stocktake Unreserved", true);
        this.dataManager = dataManager;
        
        initDialog();

        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Delete", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        dpDeletionDate = new EMCDatePicker();

        ynDeleteAllCompleted = new emcYesNoComponent();

        Component[][] comp = {{new emcJLabel("Delete everything created before"), dpDeletionDate},
                              {new emcJLabel("Delete all resolved"), ynDeleteAllCompleted}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private Component buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                Date deletionDate = dpDeletionDate.getDate();

                boolean deleteCompleted = ynDeleteAllCompleted.getSelectedItem().equals("Yes");

                if (!deleteCompleted && deletionDate == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Nothing will be deleted with your current selection.", dataManager.getUserData().copyUserData());
                    return;
                }

                EMCCommandClass cmd = new EMCCommandClass(ServerInventoryMethods.CLEAR_STOCKTAKEUNRESERVED);

                List toSend = new ArrayList();
                toSend.add(deleteCompleted);
                toSend.add(deletionDate);

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData().copyUserData());

                if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    Logger.getLogger("emc").log(Level.INFO, "Stocktake unreserved deleted.", dataManager.getUserData().copyUserData());

                    dataManager.refreshDataIgnoreFocus();

                    UnreservedStockTakeDeleteDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to delete the stocktake unreserved", dataManager.getUserData().copyUserData());
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                UnreservedStockTakeDeleteDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
