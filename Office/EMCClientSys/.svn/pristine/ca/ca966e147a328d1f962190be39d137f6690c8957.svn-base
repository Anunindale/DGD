/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommissionwizard;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.EMCJTableSuper;
import emc.app.components.emctable.editors.EMCTableCellEditor;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.enumQueryTypes;
import emc.forms.sop.display.salesrepcommissionwizard.resources.SOPRepLoadDialog;
import emc.forms.sop.display.salesrepcommissionwizard.resources.SOPRepSaveDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupSetupMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

/**
 *
 * @author riaan
 */
public class SOPSalesRepCommissionWizardForm extends BaseInternalFrame {

    private EMCUserData userData;
    private String[][] tableColumns;
    private EMCLookup lkpRep;
    private emcJTextField txtCommissionPerc;
    private emcYesNoComponent ysnOverwrite;
    private emcYesNoComponent ysnDelete;
    private Map<String, EMCJTableSuper> fieldTables;
    private Map<String, String> tableFieldMap = new HashMap<String, String>();
    private Map<String, List<Object>> addAllMap = new HashMap<String, List<Object>>();
    private List<String> availableFields = new ArrayList<String>();
    private emcJComboBox cmbFieldName;

    /** Creates a new instance of RepCommissionWizard. */
    public SOPSalesRepCommissionWizardForm(EMCUserData userData) {
        super("Rep Commission Wizard", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 500);
        this.userData = userData.copyUserDataAndDataList();

        //Find Commission Parameters
        if (!this.getParameters(userData)) {
            this.dispose();
        }

        emcJSplitPane split = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, setupPane(), selectionPane());
        split.setDividerLocation(150);
        this.setContentPane(split);
    }

    private boolean getParameters(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_SOPPARAMETER);
        List paramHolder = new ArrayList();
        paramHolder = EMCWSManager.executeGenericWS(cmd, paramHolder, userData);
        if (paramHolder.size() > 1) {
            boolean setup = false;
            tableColumns = new String[6][2];
            SOPParameters param = (SOPParameters) paramHolder.get(1);
            if (param.getCustomerItemTable1() != null) {
                tableColumns[0] = new String[]{param.getCustomerItemTable1(), param.getCustomerItemField1()};
                setup = true;
            }
            if (param.getCustomerItemTable2() != null) {
                tableColumns[1] = new String[]{param.getCustomerItemTable2(), param.getCustomerItemField2()};
                setup = true;
            }
            if (param.getCustomerItemTable3() != null) {
                tableColumns[2] = new String[]{param.getCustomerItemTable3(), param.getCustomerItemField3()};
                setup = true;
            }
            if (param.getCustomerItemTable4() != null) {
                tableColumns[3] = new String[]{param.getCustomerItemTable4(), param.getCustomerItemField4()};
                setup = true;
            }
            if (param.getCustomerItemTable5() != null) {
                tableColumns[4] = new String[]{param.getCustomerItemTable5(), param.getCustomerItemField5()};
                setup = true;
            }
            if (param.getCustomerItemTable6() != null) {
                tableColumns[5] = new String[]{param.getCustomerItemTable6(), param.getCustomerItemField6()};
                setup = true;
            }
            if (setup) {
                return true;
            }
        }
        Logger.getLogger("emc").log(Level.SEVERE, "No SOP Parameters set up of commission.", userData);
        return false;
    }

    private emcJPanel setupPane() {
        lkpRep = new EMCLookup(new SOPSalesRepGroupSetupMenu());
        EMCLookupPopup repPopup = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        lkpRep.setPopup(repPopup);
        //Only select employees that have been set up as
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addTableAnd(SOPSalesRepGroups.class.getName(), "employeeNumber", BaseEmployeeTable.class.getName(), "groupManager");
        lkpRep.setTheQuery(query);

        txtCommissionPerc = new emcJTextField(new EMCBigDecimalDocument());
        txtCommissionPerc.setText("0.0");

        ysnOverwrite = new emcYesNoComponent();
        ysnOverwrite.setBoolField(false);
        ysnOverwrite.setSelectedItem("No");

        ysnDelete = new emcYesNoComponent();
        ysnDelete.setBoolField(false);
        ysnDelete.setSelectedItem("No");

        SOPSalesRepCommission commissionInstance = new SOPSalesRepCommission();

        Component[][] comp = new Component[][]{
            {new emcJLabel(commissionInstance.getDisplayLabelForField("repId", userData)), lkpRep},
            {new emcJLabel(commissionInstance.getDisplayLabelForField("commissionPerc", userData)), txtCommissionPerc},
            {new emcJLabel("Overwrite"), ysnOverwrite},
            {new emcJLabel("Delete"), ysnDelete}
        };

        emcJPanel setupPane = new emcJPanel(new BorderLayout());
        setupPane.add(emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Commission Wizard"), BorderLayout.CENTER);
        setupPane.add(setupButtonPane(), BorderLayout.EAST);
        return setupPane;
    }

    private emcJPanel setupButtonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(lkpRep.getValue())) {
                    utilFunctions.logMessage(Level.SEVERE, "No rep specified!", getUserData());
                    return;
                } else {
                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.VALIDATE_SALES_REP);
                    List toSend = new ArrayList();
                    toSend.add(lkpRep.getValue());
                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    if (!(toSend.size() > 1 && (Boolean) toSend.get(1))) {
                        utilFunctions.logMessage(Level.SEVERE, "Invalid sales rep specified!", getUserData());
                        return;
                    }
                }
                if (Functions.checkBlank(txtCommissionPerc.getValue())) {
                    utilFunctions.logMessage(Level.SEVERE, "No commission percentage specified!", getUserData());
                    return;
                }

                if (EMCDialogFactory.createQuestionDialog(this, "Generate commission records?", "Are you sure that you wish to " + (ysnDelete.isBoolField() ? "deleted" : "generated") + " commission records for " + lkpRep.getValue() + " using the specified values?") == JOptionPane.YES_OPTION) {

                    List field1Values = new ArrayList();
                    if (tableColumns[0] != null && !Functions.checkBlank(tableColumns[0][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(0));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field1Values.add(fieldValue);
                            }
                        }
                    }

                    List field2Values = new ArrayList();
                    if (tableColumns[1] != null && !Functions.checkBlank(tableColumns[1][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(1));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field2Values.add(fieldValue);
                            }
                        }
                    }

                    List field3Values = new ArrayList();
                    if (tableColumns[2] != null && !Functions.checkBlank(tableColumns[2][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(2));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field3Values.add(fieldValue);
                            }
                        }
                    }

                    List field4Values = new ArrayList();
                    if (tableColumns[3] != null && !Functions.checkBlank(tableColumns[3][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(3));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field4Values.add(fieldValue);
                            }
                        }
                    }

                    List field5Values = new ArrayList();
                    if (tableColumns[4] != null && !Functions.checkBlank(tableColumns[4][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(4));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field5Values.add(fieldValue);
                            }
                        }
                    }

                    List field6Values = new ArrayList();
                    if (tableColumns[5] != null && !Functions.checkBlank(tableColumns[5][0])) {
                        EMCJTableSuper table = fieldTables.get(availableFields.get(5));

                        TableModel tableModel = table.getModel();

                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String fieldValue = (String) tableModel.getValueAt(i, 0);

                            if (!Functions.checkBlank(fieldValue)) {
                                field6Values.add(fieldValue);
                            }
                        }
                    }

                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GENERATE_COMMISSION_DATA);

                    List toSend = new ArrayList();
                    toSend.add(lkpRep.getValue());
                    toSend.add(field1Values);
                    toSend.add(field2Values);
                    toSend.add(field3Values);
                    toSend.add(field4Values);
                    toSend.add(field5Values);
                    toSend.add(field6Values);
                    toSend.add(ysnOverwrite.isBoolField());
                    toSend.add(ysnDelete.isBoolField());
                    toSend.add(new BigDecimal(txtCommissionPerc.getText()));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        utilFunctions.logMessage(Level.INFO, "Succesfully " + (ysnDelete.isBoolField() ? "deleted" : "generated") + " commission data.", getUserData());
                    } else {
                        utilFunctions.logMessage(Level.SEVERE, "Failed to " + (ysnDelete.isBoolField() ? "deleted" : "generated") + " commission data.", getUserData());
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel?") == JOptionPane.YES_OPTION) {
                    SOPSalesRepCommissionWizardForm.this.dispose();
                }
            }
        };
        emcJButton btnSave = new emcJButton("Save") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new SOPRepSaveDialog(SOPSalesRepCommissionWizardForm.this, userData);
            }
        };
        emcJButton btnLoad = new emcJButton("Load") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new SOPRepLoadDialog(SOPSalesRepCommissionWizardForm.this, userData);
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        buttonList.add(btnSave);
        buttonList.add(btnLoad);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel selectionPane() {
        final CardLayout cards = new CardLayout();
        final emcJPanel selectionTablePane = new emcJPanel(cards);


        SOPCustomers customerInstance = new SOPCustomers();
        InventoryItemMaster itemInstance = new InventoryItemMaster();

        fieldTables = new HashMap<String, EMCJTableSuper>();

        DefaultTableModel tableModel;
        EMCJTableSuper table;
        EMCLookupJTableComponent lookup;
        List<String> keys;
        List<String> tableKeys;
        EMCLookupPopup popup;
        EMCQuery query;
        EMCDataType dt;
        EMCEntityClass entityInstance = null;
        String fieldName;
        emcJTabbedPane tabbed;

        for (String[] tableColumn : tableColumns) {
            if (tableColumn == null || Functions.checkBlank(tableColumn[0])) {
                continue;
            } else if (itemInstance.getClass().getName().equals(tableColumn[0])) {
                entityInstance = itemInstance;
            } else if (customerInstance.getClass().getName().equals(tableColumn[0])) {
                entityInstance = customerInstance;
            }

            fieldName = entityInstance.getDisplayLabelForField(tableColumn[1], userData);
            availableFields.add(fieldName);
            tableFieldMap.put(fieldName, entityInstance.getClass().getName() + "." + tableColumn[1]);

            dt = entityInstance.getDataType(tableColumn[1], userData);

            if (dt != null && !Functions.checkBlank(dt.getRelatedTable())) {
                try {
                    entityInstance = (EMCEntityClass) Class.forName(dt.getRelatedTable()).newInstance();
                } catch (Exception ex) {
                    //Log Message
                }
                keys = entityInstance.getDefaultLookupFields();
                keys.remove(dt.getRelatedField());
                keys.add(0, dt.getRelatedField());
                query = entityInstance.getQuery();
            } else {
                keys = entityInstance.getDefaultLookupFields();
                keys.remove(tableColumn[1]);
                keys.add(0, tableColumn[1]);
                query = new EMCQuery(enumQueryTypes.SELECT, entityInstance.getClass());
                query.addGroupBy(tableColumn[1]);
                query.addOrderBy(tableColumn[1]);
            }

            tableKeys = new ArrayList<String>();
            for (String s : keys) {
                tableKeys.add(entityInstance.getDisplayLabelForField(s, userData));
            }
            tableModel = new DefaultTableModel(tableKeys.toArray(), 1) {

                @Override
                public void removeRow(int row) {
                    super.removeRow(row);
                    if (getRowCount() == 0) {
                        addRow(new Object[getColumnCount()]);
                    }
                }
            };
            table = new EMCJTableSuper(tableModel, true) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return super.isCellEditable(row, column);
                    } else {
                        return false;
                    }
                }

                @Override
                public void setValueAt(Object aValue, int row, int column) {
                    if (Functions.checkBlank(aValue)) {
                        for (int i = 0; i < getColumnCount(); i++) {
                            super.setValueAt(null, row, i);
                        }
                    } else if (!aValue.equals(getValueAt(row, column))) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.VALIDATE_SELECTED_FIELD);
                        String selectedField = (String) cmbFieldName.getSelectedItem();
                        List toSend = new ArrayList(addAllMap.get(selectedField));
                        toSend.add(0, aValue);
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                        if (toSend.size() > 1) {
                            Object o = toSend.get(1);
                            if (o instanceof Object[]) {
                                Object[] oa = (Object[]) o;
                                for (int i = 0; i < oa.length; i++) {
                                    super.setValueAt(oa[i], row, i);
                                }
                            } else {
                                super.setValueAt(o, row, column);
                            }
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Value entered does not exist", userData);
                            return;
                        }
                    }
                }

                @Override
                public TableCellEditor getCellEditor(int row, int column) {
                    return super.getCellEditor(row, column);
                }
            };

            lookup = new EMCLookupJTableComponent(null);
            popup = new CommissionLookupPopup(table, entityInstance, tableColumn[1], keys, userData);
            lookup.setPopup(popup);
            lookup.setTheQuery(query);
            List addall = new ArrayList();
            addall.add(query);
            addall.add(keys);
            addAllMap.put(fieldName, addall);



            table.getColumnModel().getColumn(0).setCellEditor(new CommissionWizardCellEditor(lookup));

            tabbed = new emcJTabbedPane();
            tabbed.add("Selection", new emcJScrollPane(table));
            selectionTablePane.add(tabbed, fieldName);

            fieldTables.put(fieldName, table);
        }

        cmbFieldName = new emcJComboBox(availableFields.toArray());
        cmbFieldName.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(selectionTablePane, (String) cmbFieldName.getSelectedItem());
            }
        });

        cards.show(selectionTablePane, (String) cmbFieldName.getSelectedItem());

        emcJPanel fieldPanel = new emcJPanel();
        fieldPanel.add(new emcJLabel("Field"));
        fieldPanel.add(cmbFieldName);

        Dimension buttonDimension = new Dimension(120, 25);
        emcJButton btnAddAll = new emcJButton("Add All") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(this, "Add all values?", "Are you sure that you wish to add all field values in the database?\n(Note: This will replace existing values)") == JOptionPane.YES_OPTION) {

                    EMCUserData userData = getUserData();

                    String selectedField = (String) cmbFieldName.getSelectedItem();

                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_ALL_FIELD_VALUES);

                    List toSend = addAllMap.get(selectedField);

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof List) {
                        List allValues = (List) toSend.get(1);

                        EMCJTableSuper activeTable = fieldTables.get(selectedField);
                        DefaultTableModel tableModel = (DefaultTableModel) activeTable.getModel();
                        tableModel.setRowCount(0);

                        Object selection;

                        for (int i = 0; i < allValues.size(); i++) {
                            selection = allValues.get(i);
                            tableModel.addRow(selection instanceof Object[] ? (Object[]) selection : new Object[]{selection});
                        }

                        if (tableModel.getRowCount() == 0) {
                            //Add one blank row
                            tableModel.addRow(new Object[]{});
                        }
                    } else {
                        utilFunctions.logMessage(Level.SEVERE, "Failed to get all values", userData);
                    }
                }
            }
        };
        btnAddAll.setPreferredSize(buttonDimension);
        fieldPanel.add(btnAddAll);

        emcJButton btnRemoveAll = new emcJButton("Remove All") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(this, "Remove all values?", "Are you sure that you wish to remove all field values?") == JOptionPane.YES_OPTION) {
                    String selectedField = (String) cmbFieldName.getSelectedItem();

                    EMCJTableSuper activeTable = fieldTables.get(selectedField);
                    DefaultTableModel tableModel = (DefaultTableModel) activeTable.getModel();
                    tableModel.setRowCount(0);
                    //Add one blank row
                    tableModel.addRow(new Object[]{});
                }
            }
        };
        btnRemoveAll.setPreferredSize(buttonDimension);
        fieldPanel.add(btnRemoveAll);

        emcJPanel selectionPane = new emcJPanel(new BorderLayout());
        selectionPane.add(fieldPanel, BorderLayout.NORTH);
        selectionPane.add(selectionTablePane, BorderLayout.CENTER);

        return selectionPane;
    }

    public Map<String, EMCJTableSuper> getFieldTables() {
        return fieldTables;
    }
}

class CommissionWizardCellEditor extends EMCTableCellEditor {

    private EMCLookup lookup;

    /** Creates a new instance of CommissionWizardCellEditor. */
    public CommissionWizardCellEditor(EMCLookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public Object getCellEditorValue() {
        return lookup.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean arg2, int arg3, int arg4) {
        lookup.setValue(value);
        return lookup;
    }

    @Override
    public boolean isCellEditable(final EventObject evt) {
        boolean ret = super.isCellEditable(evt);

        if (ret) {
            //Credit to db
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    lookup.requestFocus();

                    if (evt instanceof KeyEvent && !Character.isIdentifierIgnorable(((KeyEvent) evt).getKeyChar())) {

                        lookup.setValue(String.valueOf(((KeyEvent) evt).getKeyChar()));

                        SwingUtilities.invokeLater(new Runnable() {

                            //This should only happen after the lookup got the focus
                            public void run() {
                                lookup.selectAllText(false);
                                lookup.setCaretPosition(-1);
                            }
                        });


                    }

                }
            });
        }

        return ret;
    }
}

class CommissionLookupPopup extends EMCLookupPopup {

    private EMCJTableSuper table;
    private List<String> keys;

    CommissionLookupPopup(EMCJTableSuper table, EMCEntityClass entityInstance, String string, List<String> keys, EMCUserData userData) {
        super(entityInstance, string, keys, userData);
        this.table = table;
        this.keys = keys;
    }
//    @Override
//    public void selectionComplete() {
//        emcDataRelationManagerUpdate drm = getDataRelation();
//        int lookupRow = userTable.convertRowIndexToModel(userTable.getSelectedRow());
//        int row = table.getSelectedRow();
//        int col = 0;
//        for (String s : keys) {
//            table.setValueAt(drm.getFieldValueAt(lookupRow, s), row, col);
//            col++;
//        }
//        super.selectionComplete();
//        table.getCellEditor().stopCellEditing();
//    }
}
