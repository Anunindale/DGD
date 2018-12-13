/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.priceaudittrail;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.documents.EMCTimeFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.datasource.SOPPriceAuditTrailDS;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.forms.sop.display.priceaudittrail.resources.PopulateHistorySelectionDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPPriceAuditTrailApprovalForm extends BaseInternalFrame {

    private emcDRMViewOnly dataManager;
    private EMCUserData userData;

    public SOPPriceAuditTrailApprovalForm(EMCUserData userData) {
        super("Price Audit Trail - Approval", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 350);
        this.userData = userData.copyUserDataAndDataList();
        //Set Query
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceAuditTrail.class);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addOr("recordType", SOPPriceAuditTrailType.DISCOUNT_APPROVAL.toString());
        query.addOr("recordType", SOPPriceAuditTrailType.PRICE_APPROVAL.toString());
        query.closeConditionBracket();
        query.addOrderBy("logDate");
        this.userData.setUserData(0, query);

        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new SOPPriceAuditTrailDS(), this.userData), this.userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("userName");
        dataManager.setFormTextId2("logDate");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Audit Trail", talbePane());
        tabbed.add("Detail", detailedPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
//        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate talbePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("recordType");
        keys.add("logDate");
        keys.add("sourceReference");
        keys.add("userName");
        keys.add("customerId");
        keys.add("itemReference");
        keys.add("price");
        keys.add("originalPrice");
        keys.add("updatedPrice");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
       table.setColumnCellEditor("userName", new EMCGoToMainTableEditor(new EMCStringDocument(), new users()));
        table.setColumnCellEditor("customerId", new EMCGoToMainTableEditor(new EMCStringDocument(), new SOPCustomersMenu()));
        table.setColumnCellEditor("itemReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private Component detailedPane() {
        EMCDatePickerFormComponent dpLogDate = new EMCDatePickerFormComponent(dataManager, "logDate");
        dpLogDate.setEnabled(false);
        emcJTextField txtLogTime = new emcJTextField();
        txtLogTime.setDocument(new EMCTimeFormDocument(txtLogTime, dataManager, "logTime"));
        txtLogTime.setEditable(false);

        emcJTextField txtDimension1 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension1"));
        txtDimension1.setEditable(false);
        emcJTextField txtDimension2 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension2"));
        txtDimension2.setEditable(false);
        emcJTextField txtDimension3 = new emcJTextField(new EMCStringFormDocument(dataManager, "dimension3"));
        txtDimension3.setEditable(false);

        emcJTextField txtQuantity = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "quantity"));
        txtQuantity.setEditable(false);

        emcJTextField txtReason = new emcJTextField(new EMCStringFormDocument(dataManager, "changeReason"));
        txtReason.setEditable(false);

        Component[][] comp = {{new emcJLabel("Date"), dpLogDate, new emcJLabel("Time"), txtLogTime},
            {new emcJLabel()},
            {new emcJLabel("Config"), txtDimension1, new emcJLabel("Color"), txtDimension3},
            {new emcJLabel("Size"), txtDimension2},
            {new emcJLabel()},
            {new emcJLabel("Quantity"), txtQuantity},
            {new emcJLabel()},
            {new emcJLabel("Reason"), txtReason}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detail");
    }

    private emcJPanel buttonPane() {
        emcJButton btnPopulateHistory = new emcJButton("Populate History") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                Date fromDate = new PopulateHistorySelectionDialog(userData).getDate();

                if (fromDate != null) {

                    EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.POPULATE_APPROVAL_HISTORY);
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
