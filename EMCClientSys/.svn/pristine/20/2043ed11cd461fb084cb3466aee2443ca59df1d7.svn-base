/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommissionwizard.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJTextField;
import emc.app.components.emctable.EMCJTableSuper;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPSalesRepCommissionWizzard;
import emc.forms.sop.display.salesrepcommissionwizard.SOPSalesRepCommissionWizardForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author riaan
 */
public class SOPRepSaveDialog extends emcJDialog {

    private emcJTextField txtSetupName;
    private EMCUserData userData;
    private SOPSalesRepCommissionWizardForm theForm;

    public SOPRepSaveDialog(SOPSalesRepCommissionWizardForm theForm, EMCUserData userData) {
        super();
        this.setModal(true);
        this.setTitle("Save Setup");
        this.userData = userData;
        this.theForm = theForm;
        init();
    }

    private void init() {
        this.setSize(300, 150);
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createTitledBorder("Enter a name for your setup"));
        emcJButton btnSave = new emcJButton("Save") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String newSetupName = txtSetupName.getText();
                if (Functions.checkBlank(newSetupName)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please supply the setup name.", userData);
                    return;
                }
                Map<String, EMCJTableSuper> fieldTables = theForm.getFieldTables();
                Iterator<Entry<String, EMCJTableSuper>> fieldTableSet = fieldTables.entrySet().iterator();
                Entry<String, EMCJTableSuper> fieldTableEntry;
                EMCJTableSuper theTable;
                SOPSalesRepCommissionWizzard wizzard;
                List<Object> wizzardList;
                EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.SAVE_COMMISSION_WIZZARD);
                while (fieldTableSet.hasNext()) {
                    wizzardList = new ArrayList<Object>();
                    fieldTableEntry = fieldTableSet.next();
                    theTable = fieldTableEntry.getValue();
                    for (int row = 0; row < theTable.getRowCount(); row++) {
                        wizzard = new SOPSalesRepCommissionWizzard();
                        for (int col = 0; col < theTable.getColumnCount(); col++) {
                            switch (col) {
                                case 0:
                                    wizzard.setMainValue(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                                case 1:
                                    wizzard.setDescription1(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                                case 2:
                                    wizzard.setDescription2(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                                case 3:
                                    wizzard.setDescription3(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                                case 4:
                                    wizzard.setDescription4(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                                case 5:
                                    wizzard.setDescription5(String.valueOf(theTable.getValueAt(row, col)));
                                    break;
                            }
                        }
                        wizzard.setWizzardId(newSetupName);
                        wizzard.setFieldId(fieldTableEntry.getKey());
                        wizzardList.add(wizzard);
                    }
                    wizzardList.add(0, newSetupName);
                    wizzardList.add(1, fieldTableEntry.getKey());
                    EMCWSManager.executeGenericWS(cmd, wizzardList, userData);
                }
                SOPRepSaveDialog.this.dispose();
            }
        };
        this.txtSetupName = new emcJTextField();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.add(this.txtSetupName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        this.add(btnSave, gbc);

        this.setVisible(true);
    }
}
