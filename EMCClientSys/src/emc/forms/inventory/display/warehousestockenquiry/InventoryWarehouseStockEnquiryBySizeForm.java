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
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiryBySize;
import emc.enums.enumQueryTypes;
import emc.forms.inventory.display.warehousestockenquiry.resources.BySizePopulationDialog;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wikus
 */
public class InventoryWarehouseStockEnquiryBySizeForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDRMViewOnly dataManager;
    private List<String> columnButtonKeys;
    private emcTableModelUpdate tableModel;
    private Map<String, String> sizeColumnNameMap;

    public InventoryWarehouseStockEnquiryBySizeForm(EMCUserData userData) {
        super("Warehouse Enquiry By Size", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 400);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryWarehouseStockEnquiryBySize(), userData), userData) {

            @Override
            public String getColumnName(String columnIndex) {
                if (sizeColumnNameMap != null && sizeColumnNameMap.containsKey(columnIndex)) {
                    return sizeColumnNameMap.get(columnIndex);
                }
                return super.getColumnName(columnIndex);
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("itemReference");
        dataManager.setFormTextId2("itemDescription");
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
        List keys = new ArrayList<String>();
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("brandGroup");
        keys.add("productGroup");
        keys.add("planningGroup");
        keys.add("classification1");
        keys.add("classification5");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("location");
        keys.add("quantityTotal");

        columnButtonKeys = new ArrayList<String>(keys.subList(0, 10));

        tableModel = new emcTableModelUpdate(dataManager, keys);
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
        final EMCColumnManagerButton columnButton = new EMCColumnManagerButton(dataManager, columnButtonKeys, columnButtonKeys, InventoryWarehouseStockEnquiryBySize.class);
        emcJButton btnPopulate = new emcJButton("Populate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                BySizePopulationDialog dialog = new BySizePopulationDialog(userData);
                if (dialog.isPopulated()) {
                    //Get Size Column
                    List<String> sizeColumns = dialog.getSizeColumnsHolderList().get(0);
                    List<String> sizeColumnNames = dialog.getSizeColumnsHolderList().get(1);
                    //Update table columns
                    List keys = new ArrayList<String>();
                    keys.add("itemReference");
                    keys.add("itemDescription");
                    keys.add("brandGroup");
                    keys.add("productGroup");
                    keys.add("planningGroup");
                    keys.add("classification1");
                    keys.add("classification5");
                    keys.add("dimension1");
                    keys.add("dimension3");
                    keys.add("location");
                    keys.addAll(sizeColumns);
                    keys.add("quantityTotal");
                    tableModel.setKeys(keys);
                    tableModel.fireTableStructureChanged();
                    //Setup Size Column Names
                    sizeColumnNameMap = new HashMap<String, String>();
                    for (int i = 0; i < sizeColumns.size(); i++) {
                        sizeColumnNameMap.put(sizeColumns.get(i), sizeColumnNames.get(i));
                    }
                    //Refress Data
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouseStockEnquiryBySize.class);
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
