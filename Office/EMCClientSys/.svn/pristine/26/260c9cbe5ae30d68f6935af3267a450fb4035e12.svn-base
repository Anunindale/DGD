/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.priceaudittrail;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.datasource.SOPPriceAuditTrailDS;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.forms.sop.display.priceaudittrail.resources.PopulateHistorySelectionDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailForm extends BaseInternalFrame {

    private emcDRMViewOnly dataManager;
    private EMCUserData userData;

    public SOPPriceAuditTrailForm(EMCUserData userData) {
        super("Price Audit Trail - Price Change", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 350);
        this.userData = userData.copyUserDataAndDataList();
        //Set Query
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceAuditTrail.class);
        query.addAnd("recordType", SOPPriceAuditTrailType.DISCOUNT_APPROVAL.toString(), EMCQueryConditions.NOT);
        query.addAnd("recordType", SOPPriceAuditTrailType.PRICE_APPROVAL.toString(), EMCQueryConditions.NOT);
        this.userData.setUserData(0, query);

        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new SOPPriceAuditTrailDS(), this.userData), this.userData) {

            @Override
            public String getColumnName(String columnIndex) {
                if (columnIndex.equals("price")) {
                    return "Original List";
                } else if (columnIndex.equals("updatedPrice")) {
                    return "Current List";
                } else {
                    return super.getColumnName(columnIndex);
                }
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("userName");
        dataManager.setFormTextId2("logDate");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Audit Trail", talbePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
//        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate talbePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("recordType");
        keys.add("logDate");
        keys.add("userName");
        keys.add("priceGroup");
        keys.add("customerId");
        keys.add("itemReference");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("quantity");
        keys.add("price");
        keys.add("updatedPrice");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setColumnCellEditor("userName", new EMCGoToMainTableEditor(new EMCStringDocument(), new users()));
        table.setColumnCellEditor("priceGroup", new EMCGoToMainTableEditor(new EMCStringDocument(), new PriceGroups()));
        table.setColumnCellEditor("customerId", new EMCGoToMainTableEditor(new EMCStringDocument(), new SOPCustomersMenu()));
        table.setColumnCellEditor("itemReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        table.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        table.setColumnCellEditor("dimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        table.setColumnCellEditor("dimension2", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnPopulateHistory = new emcJButton("Populate History") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                Date fromDate = new PopulateHistorySelectionDialog(userData).getDate();

                if (fromDate != null) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.POPULATE_CHANGE_HISTORY);
                    List toSend = new ArrayList();
                    toSend.add(fromDate);
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPopulateHistory);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
