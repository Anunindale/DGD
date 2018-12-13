/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.priceaudittrail.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.framework.EMCUserData;
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
public class PopulateHistorySelectionDialog extends emcJDialog {

    private EMCUserData userData;
    private EMCDatePicker dpFromDate;

    public PopulateHistorySelectionDialog(EMCUserData userData) {
        super(null, "Populate History", true);
        this.userData = userData;
        init();
        this.pack();
        this.setVisible(true);
    }

    private void init() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Populate", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        dpFromDate = new EMCDatePicker();

        Component[][] comp = {{new emcJLabel("From Date"), dpFromDate}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (dpFromDate.getDate() == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select a date.", userData);
                    return;
                }
                PopulateHistorySelectionDialog.this.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                dpFromDate.setDate(null);
                PopulateHistorySelectionDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public Date getDate() {
        return dpFromDate.getDate();
    }
}
