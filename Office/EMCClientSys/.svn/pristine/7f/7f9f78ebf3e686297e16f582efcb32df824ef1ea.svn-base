/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommission.resources;

import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.config.emcicons;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.enumPersistOptions;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 *
 * @author wikus
 */
public class SOPSalesRepCommissionUpdateDialog extends emcJDialog {

    private enumPersistOptions formType;
    private EMCUserData userData;
    private DefaultListModel availableModel;
    private emcJList availableList;
    private DefaultListModel selectedModel;
    private emcJList selectedList;
    private List<String> availableData;
    private emcJTextField txtCommission;
    private List<String[]> toGenerate;
    private emcYesNoComponent overrideExisting;

    public SOPSalesRepCommissionUpdateDialog(enumPersistOptions formType, List<String[]> toGenerate, EMCUserData userData) {
        super(null, "Commission Updates", true);
        this.formType = formType;
        this.userData = userData.copyUserDataAndDataList();
        this.toGenerate = toGenerate;
        availableData = populateAvailableData();
        initFrame();
        this.pack();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Commission", selectionPane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        txtCommission = new emcJTextField(new EMCBigDecimalDocument());
        overrideExisting = new emcYesNoComponent();
        overrideExisting.setSelectedItem("No");
        if (!formType.equals(enumPersistOptions.INSERT)) {
            overrideExisting.setEnabled(false);
            if (!formType.equals(enumPersistOptions.UPDATE)) {
                txtCommission.setEditable(false);
            }
        }
        Component[][] commissionPane = {{new emcJLabel("Commission"), txtCommission, new emcJLabel("Override Existing"), overrideExisting}};
        Component[][] paneComp = {{emcSetGridBagConstraints.createSimplePanel(commissionPane, GridBagConstraints.NONE, true)},
            {listPane()}};
        return emcSetGridBagConstraints.createSimplePanel(paneComp, GridBagConstraints.NONE, false);
    }

    private emcJPanel listPane() {
        availableModel = new DefaultListModel();
        for (String s : availableData) {
            availableModel.addElement(s);
        }
        availableList = new emcJList(availableModel);
        selectedModel = new DefaultListModel();
        selectedList = new emcJList(selectedModel);
        emcJButton btnAddSingle = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SOPSalesRepCommissionUpdateDialog.this.addSingle();
            }
        };
        btnAddSingle.setIcon(new ImageIcon(getClass().getResource(emcicons.getNextRecord())));
        btnAddSingle.setToolTipText("Add");
        emcJButton btnRemoveSingle = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SOPSalesRepCommissionUpdateDialog.this.removeSingle();
            }
        };
        btnRemoveSingle.setIcon(new ImageIcon(getClass().getResource(emcicons.getPrevRecord())));
        btnRemoveSingle.setToolTipText("Remove");
        emcJButton btnAddAll = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SOPSalesRepCommissionUpdateDialog.this.addAll();
            }
        };
        btnAddAll.setIcon(new ImageIcon(getClass().getResource(emcicons.getLastRecord())));
        btnAddAll.setToolTipText("Add All");
        emcJButton btnRemoveAll = new emcJButton() {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SOPSalesRepCommissionUpdateDialog.this.removeAlll();
            }
        };
        btnRemoveAll.setIcon(new ImageIcon(getClass().getResource(emcicons.getFirstRecord())));
        btnRemoveAll.setToolTipText("Remove All");

        emcJLabel lblAvailable = new emcJLabel("Available");
        lblAvailable.setPreferredSize(new Dimension(200, 200));
        lblAvailable.setUsePreferedDimensions(true);
        emcJLabel lblSelected = new emcJLabel("Selected");
        lblSelected.setPreferredSize(new Dimension(200, 200));
        lblSelected.setUsePreferedDimensions(true);

        Component[][] availableComp = {{availableList, lblAvailable}};
        Component[][] selectedComp = {{selectedList, lblSelected}};
        Component[][] buttonComp = {{btnAddSingle}, {btnRemoveSingle}, {new emcJLabel()}, {btnAddAll}, {btnRemoveAll}};
        Component[][] paneComp = {{emcSetGridBagConstraints.createSimplePanel(availableComp, GridBagConstraints.NONE, true),
                emcSetGridBagConstraints.createSimplePanel(buttonComp, GridBagConstraints.NONE, true),
                emcSetGridBagConstraints.createSimplePanel(selectedComp, GridBagConstraints.NONE, true)}};
        return emcSetGridBagConstraints.createSimplePanel(paneComp, GridBagConstraints.NONE, false);
    }

    private void addSingle() {
        int selected = availableList.getSelectedIndex();
        if (selected != -1) {
            Object toAdd = availableModel.remove(selected);
            selectedModel.addElement(toAdd);
            availableList.setSelectedIndex(selected > availableModel.getSize() - 1 ? availableModel.getSize() - 1 : selected);
        }
    }

    private void removeSingle() {
        int selected = selectedList.getSelectedIndex();
        if (selected != -1) {
            Object toAdd = selectedModel.remove(selected);
            availableModel.addElement(toAdd);
            selectedList.setSelectedIndex(selected > selectedModel.getSize() - 1 ? selectedModel.getSize() - 1 : selected);
        }
    }

    private void addAll() {
        while (!availableModel.isEmpty()) {
            selectedModel.addElement(availableModel.remove(0));
        }
    }

    /**
     * Do not update splelling as removeAll will clear the frame.
     */
    private void removeAlll() {
        while (!selectedModel.isEmpty()) {
            availableModel.addElement(selectedModel.remove(0));
        }
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                List<String> reps = new ArrayList<String>();
                for (int i = 0; i < selectedModel.size(); i++) {
                    reps.add(selectedModel.get(i).toString());
                }
                if (reps.isEmpty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No Reps selected.", userData);
                    return;
                }
                BigDecimal commissionPerc = Functions.checkBlank(txtCommission.getText()) ? BigDecimal.ZERO : new BigDecimal(txtCommission.getText());
                EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.POPULATE_SALES_REP_COMMISSION);
                List toSend = new ArrayList();
                toSend.add(formType.toString());
                toSend.add(toGenerate);
                toSend.add(reps);
                toSend.add(commissionPerc.toString());
                toSend.add(overrideExisting.getSelectedItem().toString().equals("Yes"));
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                if (toSend.size() > 1) {
                    Logger.getLogger("emc").log(Level.INFO, "Sales Rec Commission Populated.", userData);
                    SOPSalesRepCommissionUpdateDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate Sales Rec Commission.", userData);
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SOPSalesRepCommissionUpdateDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private List<String> populateAvailableData() {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_AVAILABLE_REPS);
        List toSend = new ArrayList();
        toSend.add(formType.toString());
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1) {
            List<String> retList = (List<String>) toSend.get(1);
            if (retList.isEmpty()) {
                Logger.getLogger("emc").log(Level.WARNING, "No Sales Reps Found.", userData);
            }
            return retList;
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate available data.", userData);
            return new ArrayList<String>();
        }
    }
}

