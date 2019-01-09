package emc.forms.sop.display.salesrepcommissionwizard.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJList;
import emc.app.components.emcJScrollPane;
import emc.app.components.emctable.EMCJTableSuper;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPSalesRepCommissionWizzard;
import emc.forms.sop.display.salesrepcommissionwizard.SOPSalesRepCommissionWizardForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author riaan
 */
public class SOPRepLoadDialog extends emcJDialog {

    private emcJList lstOptions;
    private EMCUserData userData;
    private SOPSalesRepCommissionWizardForm theForm;

    public SOPRepLoadDialog(SOPSalesRepCommissionWizardForm theForm, EMCUserData userData) {
        super();
        this.setModal(true);
        this.setTitle("Load Setup");

        this.userData = userData;
        this.theForm = theForm;

        init();
    }

    private void init() {
        this.setSize(200, 200);
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createTitledBorder("Select a query to load"));
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.FIND_COMMISSION_WIZZARD);
        List toSend = new ArrayList();
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (toSend.size() > 1) {
            toSend = (List) toSend.get(1);
        } else {
            toSend = new ArrayList();
        }
        DefaultListModel model = new DefaultListModel();
        for (Object o : toSend) {
            model.addElement(o);
        }
        this.lstOptions = new emcJList(model);
        this.lstOptions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.lstOptions.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse Listener");
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Popup Trigger");
                    new DeletePopup(SOPRepLoadDialog.this, userData).show(lstOptions, e.getX(), e.getY());
                }
            }
        });
        System.out.println(this.lstOptions.getListeners(MouseListener.class));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(new emcJScrollPane(this.lstOptions), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        this.add(new emcJButton("Load") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(lstOptions.getSelectedValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the query to load.", userData);
                } else {
                    theForm.getFieldTables();
                    Map<String, EMCJTableSuper> fieldTables = theForm.getFieldTables();
                    Iterator<Entry<String, EMCJTableSuper>> fieldTableSet = fieldTables.entrySet().iterator();
                    Entry<String, EMCJTableSuper> fieldTableEntry;
                    EMCJTableSuper theTable;
                    DefaultTableModel tableModel;
                    SOPSalesRepCommissionWizzard wizzard;
                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.LOAD_COMMISSION_WIZZARD);
                    List toSend;
                    Object[] tableData;
                    while (fieldTableSet.hasNext()) {
                        fieldTableEntry = fieldTableSet.next();

                        toSend = new ArrayList();
                        toSend.add(lstOptions.getSelectedValue());
                        toSend.add(fieldTableEntry.getKey());
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                        toSend.remove(0);

                        theTable = fieldTableEntry.getValue();
                        tableModel = (DefaultTableModel) theTable.getModel();
                        tableModel.setRowCount(0);

                        for (int row = 0; row < toSend.size(); row++) {
                            wizzard = (SOPSalesRepCommissionWizzard) toSend.get(row);
                            tableData = new Object[theTable.getColumnCount()];
                            for (int col = 0; col < theTable.getColumnCount(); col++) {
                                switch (col) {
                                    case 0:
                                        tableData[0] = wizzard.getMainValue();
                                        break;
                                    case 1:
                                        tableData[1] = wizzard.getDescription1();
                                        break;
                                    case 2:
                                        tableData[2] = wizzard.getDescription2();
                                        break;
                                    case 3:
                                        tableData[3] = wizzard.getDescription3();
                                        break;
                                    case 4:
                                        tableData[4] = wizzard.getDescription4();
                                        break;
                                    case 5:
                                        tableData[5] = wizzard.getDescription5();
                                        break;
                                }
                            }
                            tableModel.addRow(tableData);
                        }
                    }
                    SOPRepLoadDialog.this.dispose();
                }
            }
        }, gbc);

        this.setVisible(true);
    }

    class DeletePopup extends JPopupMenu implements ActionListener {

        private SOPRepLoadDialog loadDialog;
        private EMCUserData userData;
        private JMenuItem itemDelete;

        public DeletePopup(SOPRepLoadDialog loadDialog, EMCUserData userData) {
            super();
            System.out.println("Dialog");
            this.loadDialog = loadDialog;
            this.userData = userData;
            this.itemDelete = new JMenuItem("Delete Query");
            itemDelete.addActionListener(this);
            this.add(itemDelete);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals(itemDelete.getText()) && !Functions.checkBlank(lstOptions.getSelectedValue())) {
                int result = EMCDialogFactory.createQuestionDialog(loadDialog, "Delete query", "Are you sure you want to delete " + lstOptions.getSelectedValue() + "?");
                if (result == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.DELETE_COMMISSION_WIZZARD);
                    List toSend = new ArrayList();
                    toSend.add(lstOptions.getSelectedValue());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    DefaultListModel model = (DefaultListModel) lstOptions.getModel();
                    model.removeElement(lstOptions.getSelectedValue());
                }
            }
        }
    }
}

