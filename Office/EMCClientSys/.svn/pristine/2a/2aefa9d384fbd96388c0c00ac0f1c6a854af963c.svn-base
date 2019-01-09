/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.Usertable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import emc.menus.base.menuItems.display.users;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class DevTaskCompletionDialog extends emcJDialog {

    private EMCUserData userData;
    private emcYesNoComponent ynBillable;
    private EMCLookup lkpTestedBy;
    private emcJTextArea txaTest;
    private emcJTextArea txaAffected;
    private emcJTextArea txaAffectedData;
    private emcJTextArea txaCoreUpdates;
    private emcJTextArea txaDocumentation;
    private emcJTextArea txaEntityLog;
    private DevTimeSheetTaskHelper helper;

    public DevTaskCompletionDialog(EMCDesktop desktop, EMCUserData userData) {
        super(desktop, "Tasks", true);

        this.userData = userData;

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

    private emcJPanel selectionPane() {
        ynBillable = new emcYesNoComponent();

        lkpTestedBy = new EMCLookup(new users());
        lkpTestedBy.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));


        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addAnd("companyId", userData.getCompanyId());

        lkpTestedBy.setTheQuery(query);

        txaTest = new emcJTextArea();
        emcJLabel testLabel = new emcJLabel("Test");
        testLabel.setUsePreferedDimensions(true);
        testLabel.setPreferredSize(new Dimension(350, 55));

        txaAffected = new emcJTextArea();
        emcJLabel affectedLabel = new emcJLabel("Possible Affected");
        affectedLabel.setUsePreferedDimensions(true);
        affectedLabel.setPreferredSize(new Dimension(350, 55));

        txaEntityLog = new emcJTextArea();
        emcJLabel entityLabel = new emcJLabel("Entity Log");
        entityLabel.setUsePreferedDimensions(true);
        entityLabel.setPreferredSize(new Dimension(350, 55));

        txaAffectedData = new emcJTextArea();
        emcJLabel affectedDataLabel = new emcJLabel("Possible Affected Data");
        entityLabel.setUsePreferedDimensions(true);
        entityLabel.setPreferredSize(new Dimension(350, 55));

        txaCoreUpdates = new emcJTextArea();
        emcJLabel coreUpdateLabel = new emcJLabel("Core Updates");
        entityLabel.setUsePreferedDimensions(true);
        entityLabel.setPreferredSize(new Dimension(350, 55));

        txaDocumentation = new emcJTextArea();
        emcJLabel documentationLabel = new emcJLabel("Documentation");
        entityLabel.setUsePreferedDimensions(true);
        entityLabel.setPreferredSize(new Dimension(350, 55));

        Component[][] comp = {{new emcJLabel("Billable"), ynBillable},
            {new emcJLabel("Tested By"), lkpTestedBy},
            {txaTest, testLabel},
            {txaCoreUpdates, coreUpdateLabel},
            {txaAffected, affectedLabel},
            {txaAffectedData, affectedDataLabel},
            {txaEntityLog, entityLabel},
            {txaDocumentation, documentationLabel}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (Functions.checkBlank(lkpTestedBy.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the test user.");
                    return;
                }

                if (Functions.checkBlank(txaTest.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please fill in the test.");
                    return;
                }

                helper = new DevTimeSheetTaskHelper();
                helper.setBillable(ynBillable.getSelectedItem().equals("Yes"));
                helper.setTestedBy((String) lkpTestedBy.getValue());
                helper.setTest(txaTest.getText());
                helper.setCoreUpdates(txaCoreUpdates.getText());
                helper.setAffected(txaAffected.getText());
                helper.setAffectedData(txaAffectedData.getText());
                helper.setEntityLog(txaEntityLog.getText());
                helper.setDocumentation(txaDocumentation.getText());

                DevTaskCompletionDialog.this.dispose();
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                DevTaskCompletionDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public DevTimeSheetTaskHelper getHelper() {
        return helper;
    }
}

