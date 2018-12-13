/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommission;

import emc.app.components.emcDialogue;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.datatypes.EMCString;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepGroups;
import emc.entity.sop.datasource.SOPSalesRepCommissionDS;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.sop.display.salesrepcommission.resources.MassUpdateButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupSetupMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wikus
 */
public class SOPSalesRepCommissionForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;
    private List<String[]> tableColumns = new ArrayList<String[]>();
    //Used to display get data type emc labels for display.
    private SOPCustomers customerInstance = new SOPCustomers();
    private InventoryItemMaster itemInstance = new InventoryItemMaster();

    public SOPSalesRepCommissionForm(final EMCUserData userData) {
        super("Sales Rep Commission", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 500);
        this.userData = userData.copyUserDataAndDataList();

        //Indicates whether this form was opened from the Customer master form.
        final boolean enquiry = userData.getUserData(2) == Boolean.TRUE;

        if (!getParameters()) {
            this.dispose();
            return;
        }
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesRepCommissionDS(), userData), userData) {

            List<String> custItemFields;

            {
                custItemFields = new ArrayList<String>();
                custItemFields.add("customerItemField1");
                custItemFields.add("customerItemField2");
                custItemFields.add("customerItemField3");
                custItemFields.add("customerItemField4");
                custItemFields.add("customerItemField5");
                custItemFields.add("customerItemField6");
                custItemFields.add("customerItemFieldDesc1");
                custItemFields.add("customerItemFieldDesc2");
                custItemFields.add("customerItemFieldDesc3");
                custItemFields.add("customerItemFieldDesc4");
                custItemFields.add("customerItemFieldDesc5");
                custItemFields.add("customerItemFieldDesc6");
            }

            @Override
            public String getColumnName(String columnIndex) {
                if (custItemFields.contains(columnIndex)) {
                    int field = Integer.valueOf(columnIndex.substring(columnIndex.length() - 1));
                    field--;
                    if (tableColumns.size() > field) {
                        String[] tableColumn = tableColumns.get(field);
                        String tableName = tableColumn[0];
                        String fieldName;
                        if (columnIndex.contains("Desc")) {
                            fieldName = tableColumn[2];
                        } else {
                            fieldName = tableColumn[1];
                        }
                        return SOPSalesRepCommissionForm.this.getColumnName(tableName, fieldName, userData);
                    }
                }
                return super.getColumnName(columnIndex);
            }

            @Override
            public EMCDataType getDataType(String fieldKey) {
                if (custItemFields.contains(fieldKey)) {
                    int field = Integer.valueOf(fieldKey.substring(fieldKey.length() - 1));
                    field--;
                    if (tableColumns.size() > field) {
                        String[] tableColumn = tableColumns.get(field);
                        String tableName = tableColumn[0];
                        String fieldName = tableColumn[1];
                        EMCString dt = new EMCString();
                        dt.setRelatedTable(tableName);
                        dt.setRelatedField(fieldName);
                        return dt;
                    }

                }
                return super.getDataType(fieldKey);
            }

            @Override
            public Object getFieldValueAt(int rowIndex, String columnIndex) {
                if (enquiry && "commissionPerc".equals(columnIndex)) {
                    return null;
                } else {
                    return super.getFieldValueAt(rowIndex, columnIndex);
                }
            }

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                if (enquiry) {
                    showMessage();
                } else {
                    super.insertRowCache(rowIndex, addRowAfter);
                }
            }

            @Override
            public void updatePersist(int rowIndex) {
                if (enquiry) {
                    showMessage();
                } else {
                    super.updatePersist(rowIndex);
                }
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                if (enquiry) {
                    showMessage();
                } else {
                    super.deleteRowCache(rowIndex);
                }
            }

            @Override
            public boolean persistOnClosing() {
                return true;
            }

            private void showMessage() {
                emcDialogue resetOK = new emcDialogue("Info", "This form is view only.",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION);
                if (resetOK.getDialogueResult() == JOptionPane.OK_OPTION) {
                }
            }
        };

        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("repId");
        if (enquiry) {
            dataManager.setFormTextId2("commissionPerc");
        } else {
            dataManager.setFormTextId2("customerItemField1");
        }
        initFrame(enquiry);
    }

    private void initFrame(boolean enquiry) {
        this.add(mainPane(enquiry), BorderLayout.CENTER);
        if (!enquiry) {
            this.add(createButtonsPanel(), BorderLayout.EAST);
        }
    }

    private emcJPanel mainPane(boolean enquiry) {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Commission", tablePane(enquiry));
        emcJPanel bottomPane = new emcJPanel(new BorderLayout());
        bottomPane.add(tabbed, BorderLayout.CENTER);
        return bottomPane;
    }

    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new MassUpdateButton(this));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private emcJPanel tablePane(final boolean enquiry) {
        List<String> keys = new ArrayList<String>();
        keys.add("repId");
        for (int c = 0; c < tableColumns.size(); c++) {
            keys.add("customerItemField" + (c + 1));
            if (!Functions.checkBlank(tableColumns.get(c)[2])) {
                keys.add("customerItemFieldDesc" + (c + 1));
            }
        }
        if (!enquiry) {
            keys.add("commissionPerc");
        }
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (enquiry) {
                    return false;
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };

        setLookups(table);

        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    /** Sets lookups on the specified emcJTableUpdate instance. */
    private void setLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpRep = new EMCLookupJTableComponent(new SOPSalesRepGroupSetupMenu());
        EMCLookupPopup repPopup = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        lkpRep.setPopup(repPopup);

        //Only select employees that have been set up as
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addTableAnd(SOPSalesRepGroups.class.getName(), "employeeNumber", BaseEmployeeTable.class.getName(), "groupManager");

        lkpRep.setTheQuery(query);

        table.setLookupToColumn("repId", lkpRep);

        for (int i = 0; i < tableColumns.size(); i++) {
            table.setLookupToColumn("customerItemField" + (i + 1), (EMCLookupJTableComponent) createLookup("customerItemField" + (i + 1), tableColumns.get(i), true));
            if (!Functions.checkBlank(tableColumns.get(i)[2])) {
                table.setColumnEditable("customerItemFieldDesc" + (i + 1), false);
            }
        }
    }

    /** Creates a lookup. */
    public EMCLookup createLookup(String tableField, String[] tableColumn, boolean tableLookup) {
        try {
            String tableName = tableColumn[0];
            String field = tableColumn[1];

            EMCEntityClass entityInstance = (EMCEntityClass) Class.forName(tableName).newInstance();

            EMCLookup lookup = null;

            if (tableLookup) {
                lookup = new EMCLookupJTableComponent((entityInstance.getClass().getName().equals(InventoryItemMaster.class.getName()) ? new ItemMaster() : new SOPCustomersMenu()));
            } else {
                lookup = new EMCLookup((entityInstance.getClass().getName().equals(InventoryItemMaster.class.getName()) ? new ItemMaster() : new SOPCustomersMenu()));
            }

            List<String> keys = new ArrayList<String>();
            keys.add(field);

            lookup.setPopup(new EMCLookupPopup(entityInstance, field, keys, userData));

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, entityInstance.getClass());
            query.addGroupBy(field);
            query.addOrderBy(field);

            lookup.setTheQuery(query);

            lookup.enableRickClickPopup(dataManager, tableField);

            return lookup;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean getParameters() {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_SOPPARAMETER);
        List paramHolder = new ArrayList();
        paramHolder = EMCWSManager.executeGenericWS(cmd, paramHolder, userData.copyUserData());
        if (paramHolder.size() > 1) {
            SOPParameters param = (SOPParameters) paramHolder.get(1);
            if (param.getCustomerItemTable1() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable1(), param.getCustomerItemField1(), param.getCustomerItemFieldDesc1()});
            }
            if (param.getCustomerItemTable2() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable2(), param.getCustomerItemField2(), param.getCustomerItemFieldDesc2()});
            }
            if (param.getCustomerItemTable3() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable3(), param.getCustomerItemField3(), param.getCustomerItemFieldDesc3()});
            }
            if (param.getCustomerItemTable4() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable4(), param.getCustomerItemField4(), param.getCustomerItemFieldDesc4()});
            }
            if (param.getCustomerItemTable5() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable5(), param.getCustomerItemField5(), param.getCustomerItemFieldDesc5()});
            }
            if (param.getCustomerItemTable6() != null) {
                tableColumns.add(new String[]{param.getCustomerItemTable6(), param.getCustomerItemField6(), param.getCustomerItemFieldDesc6()});
            }
            if (!tableColumns.isEmpty()) {
                return true;
            }
        }
        Logger.getLogger("emc").log(Level.SEVERE, "No SOP Parameters set up. Failed to open form.", userData);
        return false;
    }

    public String getColumnName(String table, String name, EMCUserData userData) {
        if (SOPCustomers.class.getName().equals(table)) {
            return customerInstance.getDisplayLabelForField(name, userData);
        } else if (InventoryItemMaster.class.getName().equals(table)) {
            return itemInstance.getDisplayLabelForField(name, userData);
        } else {
            return null;
        }
    }

    public emcDataRelationManagerUpdate getDataRelationManager() {
        return dataManager;
    }

    public List<String[]> getTableColumns() {
        return tableColumns;
    }
}
