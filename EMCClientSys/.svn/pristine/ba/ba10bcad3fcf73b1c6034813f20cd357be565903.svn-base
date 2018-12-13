/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar.resources;

import emc.app.components.documents.EMCIntegerDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
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
public class BaseTestCalanderDialog extends emcJDialog {

    private emcDataRelationManagerUpdate drm;
    private EMCUserData userData;
    private EMCDatePicker dpStartDate;
    private emcJTextField txtShiftBy;

    public BaseTestCalanderDialog(EMCDesktop owner, emcDataRelationManagerUpdate drm, EMCUserData userData) {
        super(owner, "Test Calender", true);
        this.drm = drm;
        this.userData = userData.copyUserData();
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Test", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        dpStartDate = new EMCDatePicker();

        txtShiftBy = new emcJTextField(new EMCIntegerDocument());

        Component[][] comp = {{new emcJLabel("Original Date"), dpStartDate},
            {new emcJLabel("Shift"), txtShiftBy}
        };

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.TEST_CALENDAR);

                List toSend = new ArrayList();
                toSend.add(drm.getLastFieldValueAt("calendarId"));
                toSend.add(dpStartDate.getDate());
                toSend.add(Integer.valueOf(txtShiftBy.getText()));

                EMCWSManager.executeGenericWS(cmd, toSend, userData);
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                BaseTestCalanderDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
