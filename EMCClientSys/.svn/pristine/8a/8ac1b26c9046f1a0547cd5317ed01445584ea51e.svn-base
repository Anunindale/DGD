/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCDoubleDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.forms.pop.display.purchaseorders.PurchaseOrderLinesDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.pop.ServerPOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class SplitDialog extends emcJDialog {

    private PurchaseOrderLinesDRM linesDRM;
    private emcJTextField txtSplitQty;
    private EMCDatePicker dpkRevisedDate;

    /** Creates a new instance of SplitDialog. */
    public SplitDialog(PurchaseOrderLinesDRM linesDRM) {
        super(null, "Split Line", true);

        this.linesDRM = linesDRM;

        this.initComponents();
        this.initDialog();
    }

    /** Initializes components. */
    private void initComponents() {
        this.txtSplitQty = new emcJTextField(new EMCDoubleDocument());
        this.txtSplitQty.setText(String.valueOf((Double)linesDRM.getLastFieldValueAt("quantity") - (Double)linesDRM.getLastFieldValueAt("itemsReceived")));
        this.dpkRevisedDate = new EMCDatePicker();
    }

    /** Initializes the dialog. */
    private void initDialog() {
        this.setLayout(new BorderLayout());

        this.add(createSplitPanel(), BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);

        this.pack();
    }

    /** Creates the split panel. */
    private emcJPanel createSplitPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel("Split Quantity"), txtSplitQty},
            {new emcJLabel("Revised Date"), dpkRevisedDate}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                EMCUserData userData = linesDRM.getUserData();

                String splitQty = txtSplitQty.getText();

                if (Functions.checkBlank(splitQty)) {
                    utilFunctions.logMessage(Level.SEVERE, "Split Quantity may not be blank.", userData);
                    return;
                }

                Date revisedDate = dpkRevisedDate.getDate();

                if (Functions.checkBlank(revisedDate)) {
                    utilFunctions.logMessage(Level.SEVERE, "Revised Date may not be blank.", userData);
                    return;
                }
                
                EMCCommandClass cmd = new EMCCommandClass(ServerPOPMethods.SPLIT_PURCHASE_ORDER_LINE);

                List toSend = new ArrayList();
                toSend.add(linesDRM.getRowCache(linesDRM.getLastRowAccessed()));
                toSend.add(Double.valueOf(splitQty));
                toSend.add(revisedDate);

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    utilFunctions.logMessage(Level.SEVERE, "Successfully split line.", userData);
                    linesDRM.refreshData();
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Failed to split line.", userData);
                }
            }
        });

        buttons.add(new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(SplitDialog.this, "Cancel?", "Are you sure that you wish to cancel the split?") == JOptionPane.YES_OPTION) {
                    SplitDialog.this.dispose();
                }
            }
        });

        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }
}
