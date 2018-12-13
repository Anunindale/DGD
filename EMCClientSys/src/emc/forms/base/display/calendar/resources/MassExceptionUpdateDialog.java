/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class MassExceptionUpdateDialog extends emcJDialog {

    private EMCUserData userData;
    private long recordId;
    private emcJTableUpdate table;

    public MassExceptionUpdateDialog(long recordId, EMCUserData userData) {
        super(null, "Mass Update Exceptions");
        this.userData = userData;
        this.recordId = recordId;
        initFrame();
        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Calendars", tablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(buttonPane(), BorderLayout.EAST);
        this.add(thePanel);
    }

    private emcJPanel tablePane() {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.GET_MASS_UPDATE_DATA.toString());
        List theList = (List) EMCWSManager.executeGenericWS(cmd, new ArrayList(), userData).get(1);
        Object[][] data = new Object[theList.size()][2];
        int count = 0;
        for (Object s : theList) {
            data[count][0] = s;
            data[count][1] = false;
            count++;
        }
        Object[] columns = {"Calendar", "Add Exception"};
        table = new emcJTableUpdate(data, columns);
        table.getColumn("Add Exception").setCellEditor(table.getDefaultEditor(Boolean.class));
        table.getColumn("Add Exception").setCellRenderer(table.getDefaultRenderer(Boolean.class));
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        thePanel.setPreferredSize(new Dimension(300, 200));
        return thePanel;
    }

    private emcJPanel buttonPane() {
        emcJButton theButton = new emcJButton("Add Exceptions") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                List calendarList = new ArrayList();
                for (int i = 0; i < table.getRowCount(); i++) {
                    if ((Boolean) table.getValueAt(i, 1)) {
                        calendarList.add(table.getValueAt(i, 0));
                    }
                }
                if (!calendarList.isEmpty()) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.DO_MASS_UPDATE.toString());
                    List toSend = new ArrayList();
                    toSend.add(recordId);
                    toSend.add(calendarList);
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
                MassExceptionUpdateDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(theButton);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
