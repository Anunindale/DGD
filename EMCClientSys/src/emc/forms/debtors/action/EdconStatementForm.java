/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.action;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.menus.debtors.menuitems.display.DebtorsCustomers;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class EdconStatementForm extends BaseInternalFrame {

    private EMCDatePicker dpkFrom = new EMCDatePicker();
    private EMCDatePicker dpkTo = new EMCDatePicker();
    private EMCFilePicker fpkOutput = new EMCFilePicker();
    private emcJTextField txtEdconSupplierNo = new emcJTextField();
    private EMCLookup lkpCustomerAccount;
    
    /** Creates a new instance of EdconStatementForm. */
    public EdconStatementForm(EMCUserData userData) {
        super("Edcon Statement", true, true, true, true, userData);
        this.setBounds(20, 20, 420, 185);

        initForm(userData);
    }
    
    /** Initializes form. */
    private void initForm(EMCUserData userData) {
        this.lkpCustomerAccount = new EMCLookup(new DebtorsCustomers());
        this.lkpCustomerAccount.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        //N & L Edcon customer number hard-coded, but may be changed by the user if needed.
        this.lkpCustomerAccount.setValue("005632");
        //File name is in a standard format.  User may only select a directory.  Default to home directory.
        this.fpkOutput.setSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.fpkOutput.setValue(System.getProperty("user.home"));
        //Edcon supplier ref hard-coded, but may be changed if needed.
        this.txtEdconSupplierNo.setText("11711");

        Component[][] components = new Component[][] {
            {new emcJLabel("From Date"), dpkFrom},
            {new emcJLabel("To Date"), dpkTo},
            {new emcJLabel("Output Directory"), fpkOutput},
            {new emcJLabel("Customer"), lkpCustomerAccount},
            {new emcJLabel("Edcon Supplier Ref"), txtEdconSupplierNo}
        };

        this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.CENTER);
    
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new ExportButton());
        
        this.add(emcSetGridBagConstraints.createButtonPanel(buttons, true), BorderLayout.EAST);
    }

    private class ExportButton extends emcJButton {

        /** Creates a new instance of ExportButton. */
        public ExportButton() {
            super("Export");
        }

        @Override
        public void doActionPerformed(ActionEvent evt) {
            super.doActionPerformed(evt);

            Date from = EdconStatementForm.this.dpkFrom.getDate();
            Date to = EdconStatementForm.this.dpkTo.getDate();
            String customerId = (String)EdconStatementForm.this.lkpCustomerAccount.getValue();
            String filePath = EdconStatementForm.this.fpkOutput.getFilePath();
            String edconSupplierRef = EdconStatementForm.this.txtEdconSupplierNo.getText();
            
            EMCUserData userData = EdconStatementForm.this.getUserData();

            if (from == null || to == null) {
                utilFunctions.logMessage(Level.SEVERE, "Please specify both a from and to date.", userData);
                return;
            }

            if (Functions.checkBlank(customerId)) {
                utilFunctions.logMessage(Level.SEVERE, "Please specify a customer.", userData);
                return;
            }

            if (Functions.checkBlank(edconSupplierRef)) {
                utilFunctions.logMessage(Level.SEVERE, "Please specify an Edcon supplier reference.", userData);
                return;
            }

            if (EMCDialogFactory.createQuestionDialog(EdconStatementForm.this, "Export?", "Are you sure that you want to export an Edcon statement?") == JOptionPane.YES_OPTION) {
                EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.CREATE_EDCON_STATEMENT);

                List toSend = new ArrayList();
                toSend.add(customerId);
                toSend.add(from);
                toSend.add(to);
                toSend.add(edconSupplierRef);
                
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof String && !((String)toSend.get(1)).isEmpty()) {
                    //Fix new lines.  Server returns <LF> instead of line breaks in XML.
                    String edconStatementString = new EMCXMLHandler().reinstateNewLines((String)toSend.get(1));

                    BufferedWriter writer = null;

                    try {
                        writer = new BufferedWriter(new FileWriter(filePath + File.separator + "IMS" + edconSupplierRef + ".dat"));

                        writer.write(edconStatementString);

                        utilFunctions.logMessage(Level.INFO, "Successfully created Edcon statement.", userData);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } finally {
                        try {
                            if (writer != null) {
                                writer.flush();
                                writer.close();
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Failed to get data for Edcon statement.", userData);
                }
            }
        }
    }
}
