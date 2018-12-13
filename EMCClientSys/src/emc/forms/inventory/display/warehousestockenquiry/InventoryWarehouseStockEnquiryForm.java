/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.warehousestockenquiry;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.columnmanager.EMCColumnManagerButton;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiry;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.warehousestockenquiry.resources.PopulationDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryWarehouseStockEnquiryForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDRMViewOnly dataManager;
    private List<String> columnButtonKeys;
    private List<String> binNames;
    private emcTableModelUpdate tableModel;

    public InventoryWarehouseStockEnquiryForm(EMCUserData userData) {
        super("Warehouse Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 400);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryWarehouseStockEnquiry(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("itemReference");
        dataManager.setFormTextId2("itemDescription");

        //Get bin names
        EMCCommandClass getBinsCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.GET_BIN_LABELS.toString());

        List list = new ArrayList();

        list = EMCWSManager.executeGenericWS(getBinsCmd, list, userData);

        if (list != null && !list.isEmpty()) {
            binNames = new ArrayList<String>();
            //Remove command
            list.remove(0);

            for (int i = 0; i < list.size(); i++) {
                binNames.add((String) list.get(i));
            }
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Enquiry", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        final List keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("productGroup");
        keys.add("planningGroup");
        keys.add("classification1");
        keys.add("classification5");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("batch");
        keys.add("serial");
        keys.add("location");
        keys.add("totalQuantity");
        keys.add("reservedQuantity");
        keys.add("availableQuantity");
        if (binNames.size() > 0) {
            keys.add("bin1Quantity");
        }
        if (binNames.size() > 1) {
            keys.add("bin2Quantity");
        }
        if (binNames.size() > 2) {
            keys.add("bin3Quantity");
        }
        if (binNames.size() > 3) {
            keys.add("bin4Quantity");
        }
        //Note that the server only caters for 4 bins - just like the Inventory Ageing report.

        columnButtonKeys = new ArrayList<String>(keys.subList(0, 12));

        tableModel = new emcTableModelUpdate(dataManager, keys) {

            @Override
            public String getColumnName(int columnIndex) {
                if (String.valueOf(keys.get(columnIndex)).contains("bin")) {
                    //This code is dependent on the order of the columns in both
                    //the key list and the bin names list.  Assume that bins are
                    //always visible.
                    int nonBinFields = keys.size() - 4; //There are four bins on the UI.
                    if (binNames.size() > columnIndex - nonBinFields) {
                        return binNames.get(columnIndex - nonBinFields);
                    }
                }
                return super.getColumnName(columnIndex);
            }
        };
        emcJTableUpdate table = new emcJTableUpdate(tableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public boolean getScrollableTracksViewportWidth() {
                final Component p = getParent();
                if (!(p instanceof javax.swing.JViewport)) {
                    return false;
                }
                return ((javax.swing.JViewport) p).getWidth() > getPreferredSize().width;
            }
        };

        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        final EMCColumnManagerButton columnButton = new EMCColumnManagerButton(dataManager, columnButtonKeys, columnButtonKeys, InventoryWarehouseStockEnquiry.class) {

            @Override
            public EMCQuery buildQuery() {
                EMCQuery query = dataManager.getOriginalQuery().copyQuery();
                query.clearGroupByFields();
                String tableName = tableClass.getName();
                Class tempClass = tableClass;
                do {
                    Field[] fields = tempClass.getDeclaredFields();
                    for (Field f : fields) {
                        query.removeOrderBy(tableName, f.getName());
                        if (availableCoumns.contains(f.getName()) && currentColumns.contains(f.getName())) {
                            query.addGroupBy(tableName, f.getName());
                            if (f.getName().equals("dimension2")) {
                                query.addOrderBy("dimension2SortCode", tableName);
                            } else {
                                query.addOrderBy(f.getName(), tableName);
                            }
                        }
                    }
                    tempClass = tempClass.getSuperclass();
                } while (!tempClass.getName().equals(EMCEntityClass.class.getName()));
                return query;
            }
        };
        emcJButton btnPopulate = new emcJButton("Populate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                PopulationDialog d = new PopulationDialog(userData);
                if (d.isPopulated()) {
                    if (d.doAgeing()) {
                        List keys = tableModel.getKeys();
                        if (binNames.size() > 0 && !keys.contains("bin1Quantity")) {
                            keys.add("bin1Quantity");
                        }
                        if (binNames.size() > 1 && !keys.contains("bin2Quantity")) {
                            keys.add("bin2Quantity");
                        }
                        if (binNames.size() > 2 && !keys.contains("bin3Quantity")) {
                            keys.add("bin3Quantity");
                        }
                        if (binNames.size() > 3 && !keys.contains("bin4Quantity")) {
                            keys.add("bin4Quantity");
                        }
                        tableModel.setKeys(keys);
                        tableModel.fireTableStructureChanged();
                    } else {
                        List keys = tableModel.getKeys();
                        keys.remove("bin1Quantity");
                        keys.remove("bin2Quantity");
                        keys.remove("bin3Quantity");
                        keys.remove("bin4Quantity");
                        tableModel.setKeys(keys);
                        tableModel.fireTableStructureChanged();
                    }

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouseStockEnquiry.class);
                    query.addAnd("recordOwner", userData.getUserName());
                    dataManager.setOriginalQuery(query);
                    columnButton.refressData();
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPopulate);
        buttonList.add(columnButton);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
