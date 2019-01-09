/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.declarationlines.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.frame.EMCDesktop;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.dangerousgoods.DGDVehicles;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.methods.dangerousgoods.ServerDGMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.ext.swing.GridBagConstants;

/**
 *
 * @author pj
 */
public class DGSelectRegDialog extends emcJDialog 
{
    private emcDataRelationManagerUpdate dataManager;
    private emcJTextField txtContactNum;
    private List<Object> registrationRecords;
    private String[] registrationNumbers;
    private emcJComboBox registrationCombo;
    
    public DGSelectRegDialog(EMCDesktop desktop, emcDataRelationManagerUpdate dataManager, String contactNum)
    {
        super(desktop, "Select Vehicle", true);
        
        this.dataManager = dataManager;

        txtContactNum = new emcJTextField();
        txtContactNum.setEditable(false);
        txtContactNum.setText(contactNum);
        
        EMCCommandClass cmd = new EMCCommandClass(ServerDGMethods.GET_REGISTRATION_NUMBERS);
        
        registrationRecords = new ArrayList();
        registrationRecords.add(contactNum);
        registrationRecords = EMCWSManager.executeGenericWS(cmd, registrationRecords, dataManager.getUserData());
        
        registrationNumbers = new String[registrationRecords.size() - 1];
        
        for(int i = 1; i < registrationRecords.size(); ++i)
        {
            DGDVehicles regRec = (DGDVehicles) registrationRecords.get(i);
            String toAdd = regRec.getRegistrationNumber();
            registrationNumbers[i - 1] = toAdd;
        }
        
        initDialog();
        pack();
        setVisible(true);
    }
    
    private void initDialog()
    {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Select Vehicle", selectionPane());
        
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        
        setContentPane(contentPane);
    }
    
    private Component selectionPane()
    {
        registrationCombo = new emcJComboBox(registrationNumbers);
        
        Component[][] comp = {{new emcJLabel("Operator"), txtContactNum}, {new emcJLabel("Vehicle Registration"), registrationCombo}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstants.NONE, true);
    }
    
    private Component buttonPane()
    {
        emcJButton btnOK = new emcJButton("OK")
        {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if(Functions.checkBlank(registrationCombo.getSelectedItem()))
                {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select a vehicle regstration number.", dataManager.getUserData());
                    return;
                }
                dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "registrationNumber", registrationCombo.getSelectedItem().toString());
                DGSelectRegDialog.this.dispose();
            }
        };
        
        emcJButton btnCancel = new emcJButton("Cancel")
        {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                DGSelectRegDialog.this.dispose();
            }
        };
        
        List<emcJButton> buttonList = new ArrayList<>();
            buttonList.add(btnOK);
            buttonList.add(btnCancel);
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
