/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.statusenquiry;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.columnmanager.EMCColumnManagerButton;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.mergetable.EMCMergeJTable;
import emc.app.components.emctable.mergetable.EMCMergeTableDRMViewOnly;
import emc.app.components.emctable.mergetable.EMCMergeTableModel;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.sop.SOPStatusEnquirySummary;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.sop.statusenquiry.SalesStatusEnquiryTypes;
import emc.forms.sop.display.statusenquiry.resources.SOPStatusEnquirySelectionDialog;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author wikus
 */
public class SOPStatusEnquiryForm extends BaseInternalFrame {

    private EMCMergeTableDRMViewOnly dataManager;
    private EMCMergeTableModel model;
    private Map<String, List<String>> tableMap;
    private List<String> blankFields;
    private List<String> nonMergeFields;
    private EMCColumnManagerButton columnButton;
    private Map<String, String> columnNames;
    private EMCUserData userData;

    public SOPStatusEnquiryForm(EMCUserData userData) {
        super("Sales Order Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 400);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new EMCMergeTableDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPStatusEnquirySummary(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerGroup");
        dataManager.setFormTextId2("customerId");
        columnNames = new HashMap<String, String>();
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Sales Status", tablePane());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        blankFields = new ArrayList<String>();
        blankFields.add("customerGroup");
        blankFields.add("customerId");
        setTableFields();
        EMCMergeJTable table = new EMCMergeJTable(model) {

            @Override
            public Object getValueAt(int row, int column) {
                if (model.getKeys().get(column).equals("recordType")) {
                    if ("Units".equals(dataManager.getFieldValueAt(row, "recordGroup"))) {
                        return SalesStatusEnquiryTypes.fromId((Integer) dataManager.getFieldValueAt(row, "recordType")).toString();
                    } else {
                        return null;
                    }
                } else if (model != null && ((Integer) dataManager.getFieldValueAt(row, "recordType") != 0 ||
                        !"Units".equals(dataManager.getFieldValueAt(row, "recordGroup"))) && blankFields.contains(model.getKeys().get(column).toString())) {
                    return null;
                }
                return super.getValueAt(row, column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                final java.awt.Component c = super.prepareRenderer(renderer, row, col);
                if ((Integer) dataManager.getFieldValueAt(row, "recordType") == 0 && "Units".equals(dataManager.getFieldValueAt(row, "recordGroup"))) {
                    c.setForeground(Color.BLUE);
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (model.getKeys().get(column).equals("recordType")) {
                    return getDefaultRenderer(String.class);
                }
                return super.getCellRenderer(row, column);
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setTableFields() {
        if (nonMergeFields == null) {
            nonMergeFields = new ArrayList<String>();
            nonMergeFields.add("customerGroup");
            nonMergeFields.add("customerId");
            nonMergeFields.add("recordType");
            nonMergeFields.add("recordGroup");
            nonMergeFields.add("quantityTotal");
        }

        List<String> keys = new ArrayList<String>();
        keys.addAll(nonMergeFields);

        if (tableMap != null) {
            keys.addAll(nonMergeFields.indexOf("quantityTotal"), tableMap.remove("Order"));
            model.updateTable(keys, tableMap, nonMergeFields);
        } else {
            model = new EMCMergeTableModel(dataManager, keys, new HashMap<String, List<String>>(), nonMergeFields) {

                @Override
                public String getColumnName(int columnIndex) {
                    if (columnNames.containsKey(getKeys().get(columnIndex).toString())) {
                        return SOPStatusEnquiryForm.this.buildColumnName(columnNames.get(getKeys().get(columnIndex).toString()));
                    }
                    return super.getColumnName(columnIndex);
                }

                @Override
                public Object getCalcValue(int row, String column, List<String> mappedFields) {
                    return dataManager.getFieldValueAt(row, mappedFields.get(mappedFields.size() - 1));
                }


            };
            model.addSpecilaCalcFieldValue("recordType", "0");
        }
    }

    private emcJPanel buttonPane() {
        emcJButton btnPopulate = new emcJButton("Populate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new SOPStatusEnquirySelectionDialog(SOPStatusEnquiryForm.this);
                if (tableMap != null) {
                    setTableFields();
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPStatusEnquirySummary.class);
                    query.addAnd("createdBy", userData.getUserName());
                    dataManager.setOriginalQuery(query);
                    columnButton.refressData();
                }
            }
        };
        columnButton = new EMCColumnManagerButton(dataManager, nonMergeFields.subList(0, nonMergeFields.size() - 1), nonMergeFields.subList(0, nonMergeFields.size() - 1), SOPStatusEnquirySummary.class) {

            @Override
            public EMCQuery buildQuery() {
                EMCQuery query = super.buildQuery();

                query.removeOrderBy(SOPStatusEnquirySummary.class.getName(), "recordType");
                query.removeOrderBy(SOPStatusEnquirySummary.class.getName(), "recordGroup");

                query.addGroupBy("recordType");
                query.addGroupBy("recordGroup");
                query.addOrderBy("recordType");
                query.addOrderBy("recordGroup");

                dataManager.setOriginalQuery(query);

                return query;
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPopulate);
        buttonList.add(columnButton);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public void setTableMap(Map<String, List<String>> tableMap) {
        this.tableMap = tableMap;
    }

    private String buildColumnName(String name) {
        StringBuilder theName = new StringBuilder();
        boolean firstLetter = true;
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c) && !firstLetter) {
                theName.append(" ");
            }
            if (firstLetter) {
                theName.append(Character.toUpperCase(c));
            } else {
                theName.append(c);
            }
            firstLetter = false;
        }
        return theName.toString();
    }
}
