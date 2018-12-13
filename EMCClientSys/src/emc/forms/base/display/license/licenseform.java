/*
 * licenseform.java
 *
 * Created on 24 October 2007, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.forms.base.display.license;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class licenseform extends BaseInternalFrame {
    private emcDataRelationManagerUpdate dataRelation;
    private emcJPanel form = new emcJPanel();
    private emcJTextField compName = new emcJTextField();
    private emcJTextArea licenseKey = new emcJTextArea();
    private emcJLabel compNameLabel = new emcJLabel("Company Name");
    private emcJLabel licenceKeyLabel = new emcJLabel("Licence Key");
    private emcJLabel validateStatusLabel = new emcJLabel("Validate Status");
    private emcJLabel expiryDateLabel = new emcJLabel("Expiry Date");
    private emcJLabel companiesLabel = new emcJLabel("Companies");
    private emcJLabel usersLabel = new emcJLabel("Users");
    private emcJLabel modulesListLabel = new emcJLabel("Modules");
    private emcJTextField validateStatus = new emcJTextField();
    private emcJTextField expiryDate = new emcJTextField();
    private emcJTextField companies = new emcJTextField();
    private emcJTextField users = new emcJTextField();
    private emcJList modules = new emcJList();
    private emcJTabbedPane tabpane = new emcJTabbedPane();
    private emcJScrollPane scrollpane = new emcJScrollPane(modules);
    private licenseDocument compNameDoc;
    private licenseDocument seqKeyDoc;
    private emcJScrollPane spane;

    private emcJTextField txtSystemId;
    private emcJTextField txtAuthKey;
    private emcJLabel lblSystemId;
    private emcJLabel lblAuthKey;
    
    //DataSource
    //end dataSource
    
    /** Creates a new instance of licenseform */
    public licenseform(EMCUserData userData) {
       super("Licence", true, true,true, true,userData);
       this.setBounds(20, 20, 650, 450);
       this.setHelpFile(new emcHelpFile("Base/Licence.html"));
           
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                enumEMCModules.BASE.getId(),new emc.entity.base.BaseLicenceTable(),userData),userData);

       this.setDataManager(dataRelation);
       compNameDoc = new licenseDocument(dataRelation,"companyName",compName,licenseKey
               ,validateStatus,expiryDate,companies,users,modules);
       this.compName.setDocument(compNameDoc);
       seqKeyDoc = new licenseDocument(dataRelation,"licenceKey",compName,licenseKey
               ,validateStatus,expiryDate,companies,users,modules);
       this.licenseKey.setDocument(seqKeyDoc);
       spane = new emcJScrollPane(licenseKey);
       initLabels();
       initTextFields();
       initDisplay();
       dataRelation.refreshData();
      
    }

    private void initLabels() {
        this.lblSystemId = new emcJLabel("System ID");
        this.lblAuthKey = new emcJLabel(dataRelation.getColumnName("authorizationKey"));
    }

    private void initTextFields() {
        this.txtSystemId = new emcJTextField();
        this.txtSystemId.setEditable(false);

       EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.GET_SYSTEM_ID.toString());
       List toSend = new ArrayList();

       EMCUserData userData = getUserData();

       toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

       if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof String) {
           txtSystemId.setText((String)toSend.get(1));
       }

        this.txtAuthKey = new emcJTextField(new EMCStringFormDocument(dataRelation, "authorizationKey"));
    }

    private void initDisplay(){
       form.setLayout(new GridBagLayout());
       form.setBorder(javax.swing.BorderFactory.createTitledBorder("Licence"));
       
       int y =0;
       GridBagConstraints c = new GridBagConstraints();
       c.gridx = 0;
       c.gridy = 0;
       c.weightx = 0.1;
       c.anchor = GridBagConstraints.LINE_START;
       
       form.add(compNameLabel,c);  
       c.gridx = 1;
       c.anchor = GridBagConstraints.LINE_END;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.weightx = 0.9;
       c.gridwidth = GridBagConstraints.REMAINDER;
       compName.setPreferredSize(new java.awt.Dimension(400,25));
       form.add(compName,c);
       c = null;
       c = new GridBagConstraints();
       c.gridx = 0;
       c.gridy = 1;
       c.anchor = GridBagConstraints.LINE_START;
       c.fill = GridBagConstraints.NONE;
       c.weightx = 0.1;
       form.add(licenceKeyLabel,c);
       c.gridx = 1;
       c.anchor = GridBagConstraints.LINE_END;
       c.gridwidth = GridBagConstraints.REMAINDER;
       licenseKey.setPreferredSize(new java.awt.Dimension(400,75));
       c.fill = GridBagConstraints.HORIZONTAL;
       c.weightx = 0.9;
       form.add(spane,c);
       //add space
       y = 2;
       c = null;
       c = new GridBagConstraints();
       c.gridx = 0;
       c.gridy = y;
       c.anchor = GridBagConstraints.LINE_START;
       form.add(new emcJLabel());
       //VALIDATION RESULTS
       GridBagConstraints d = new GridBagConstraints();
       d.gridx = 0;
       d.gridy = 3;
       d.weightx = 0.1;
       d.anchor = GridBagConstraints.LINE_START;
       form.add(this.validateStatusLabel,d);
       d.gridx = 1;
       d.weightx = 0.1;
       this.validateStatus.setPreferredSize(new java.awt.Dimension(100,25));
       this.validateStatus.setEditable(false);
       form.add(this.validateStatus,d);
       d.gridx = 2;
       d.gridy = 3;
       d.weightx = 0.1;
       form.add(this.modulesListLabel,d);
       //add the listbox
       d.gridx = 3;
       d.gridy = 3;
       d.weightx = 0.7;
       d.fill = GridBagConstraints.BOTH;
       d.anchor = GridBagConstraints.LINE_END;
       d.gridheight = GridBagConstraints.REMAINDER;
       
       modules.setVisibleRowCount(-1);
       form.add(this.scrollpane,d);
       d = null;
       d = new GridBagConstraints();
       d.gridx = 0; d.gridy = 4;
       d.weightx = 0.1;
       d.anchor = GridBagConstraints.LINE_START;
       form.add(this.expiryDateLabel,d);
       d.gridx = 1;
        d.weightx = 0.1;
       this.expiryDate.setPreferredSize(new java.awt.Dimension(100,25));
       this.expiryDate.setEditable(false);
       form.add(this.expiryDate,d);
       d.gridx = 0; d.gridy = 5;
       form.add(this.companiesLabel,d);
       d.gridx = 1;
       this.companies.setPreferredSize(new java.awt.Dimension(100,25));
       this.companies.setEditable(false);
       form.add(this.companies,d);
       
       //users
       d.gridx = 0; d.gridy = 6;
       form.add(this.usersLabel,d);
       d.gridx = 1;
       this.users.setPreferredSize(new java.awt.Dimension(100,25));
       this.users.setEditable(false);
       form.add(this.users,d);
       
        y++;
        form.add(new emcJLabel(),emcSetGridBagConstraints.endPanel(y));
       //add the panel
       this.tabpane.addTab("Licence Data",form);
       this.tabpane.add("Authorization", createAuthTab());

       this.add(tabpane,BorderLayout.CENTER);
    }

    /** Creates authorization tab. */
    private emcJPanel createAuthTab() {
        emcJButton btnCopy = new emcJButton("Copy to Clipboard") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                StringSelection ss = new StringSelection(txtSystemId.getText());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            }
        };

        emcJButton btnValidate = new emcJButton("Validate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.VALIDATE_AUTH_KEY);
                List toSend = new ArrayList();
                toSend.add(txtAuthKey.getText());

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean) {
                    if ((Boolean)toSend.get(1)) {
                        //Valid
                        Logger.getLogger("emc").log(Level.INFO, "The authorization key is valid.", getUserData());
                    } else {
                        //Not valid
                        Logger.getLogger("emc").log(Level.SEVERE, "The authorization key is not valid.", getUserData());
                    }
                }
            }

        };

        Component[][] components = new Component[][] {
            {lblSystemId, txtSystemId, btnCopy},
            {lblAuthKey, txtAuthKey, btnValidate}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
}
