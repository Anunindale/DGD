/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.batchprocessing.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseBatchProcessPriorityDialog extends emcJDialog {

    private emcJComboBox priority;
    private emcDataRelationManagerUpdate dataManager;

    public BaseBatchProcessPriorityDialog(emcDataRelationManagerUpdate dataManager) {
        super(null, "Priority", true);
        this.dataManager = dataManager;
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Priority", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());

        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        priority = new emcJComboBox(new Object[]{1, 2, 3, 4, 5});
        priority.setSelectedItem(1);
        Component[][] comp = {{new emcJLabel("Priority"), priority}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "priority", priority.getSelectedIndex() + 1);
                dataManager.updatePersist(dataManager.getLastRowAccessed());
                dataManager.refreshRecord(dataManager.getLastRowAccessed());

                BaseBatchProcessPriorityDialog.this.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                BaseBatchProcessPriorityDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
