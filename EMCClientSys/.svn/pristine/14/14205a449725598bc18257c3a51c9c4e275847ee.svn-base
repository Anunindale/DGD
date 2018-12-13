/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.frame.EMCDesktop;
import emc.entity.developertools.bugtracking.DevBugsTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class DevTaskSelectionDialog extends emcJDialog {

    private List<DevBugsTable> posibleTasks;
    private emcJTableUpdate selectionTable;
    private int selectedTask = -1;

    public DevTaskSelectionDialog(EMCDesktop desktop, List<DevBugsTable> posibleTasks) {
        super(desktop, "Task Selection", true);

        this.posibleTasks = posibleTasks;

        initDialog();

        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Tasks", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJScrollPane selectionPane() {
        String[] columnNames = new String[]{"Task", "Summary", "Detail"};
        Object[][] rowData = new Object[posibleTasks.size()][3];

        int row = 0;

        for (DevBugsTable task : posibleTasks) {
            rowData[row][0] = task.getBugNumber();
            rowData[row][1] = task.getSummary();
            rowData[row][2] = task.getBugId();
        }

        selectionTable = new emcJTableUpdate(rowData, columnNames);

        emcJScrollPane tableScroll = new emcJScrollPane(selectionTable);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (selectionTable.getSelectedRow() == -1) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the task you werre working on.");
                    return;
                }

                selectedTask = selectionTable.getSelectedRow();
                DevTaskSelectionDialog.this.dispose();
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                DevTaskSelectionDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public int getSelectedTask() {
        return selectedTask;
    }
}
