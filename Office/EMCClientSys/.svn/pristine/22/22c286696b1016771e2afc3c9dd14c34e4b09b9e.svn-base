/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.customers;

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
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.commission.SOPCommissionTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SOPCustomerRepEnquiry extends BaseInternalFrame {

    private EMCUserData userData;
    private List<String[]> tableColumns = new ArrayList<String[]>();
    private emcDRMViewOnly dataRelation;

    public SOPCustomerRepEnquiry(EMCUserData userData) {
        super("Customer No Rep Allocated", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 340);
        this.userData = userData.copyUserData();
        if (!getParameters()) {
            this.dispose();
            return;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        query.addAnd("customerId", null);

        this.userData.setUserData(0, query);

        dataRelation = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new SOPCustomers(), this.userData), this.userData) {

            @Override
            public void createGoToMainTableForm(EMCMenuItem menuItem, String relatedField) {
                if (menuItem instanceof SOPCustomersMenu) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                    query.addAnd("customerId", getLastFieldValueAt("customerId"));
                    EMCUserData genUD = getUserData().copyUserData();
                    genUD.setUserData(0, query);
                    getTheForm().getDeskTop().createAndAdd(menuItem, -1, -1, genUD, null, 0);
                } else {
                    super.createGoToMainTableForm(menuItem, relatedField);
                }
            }
        };
        dataRelation.setTheForm(this);
        this.setDataManager(dataRelation);
        dataRelation.setFormTextId1("customerId");
        dataRelation.setFormTextId2("customerName");

        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());

        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList();
        keys.add("customerId");
        keys.add("customerName");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setColumnCellEditor("customerId", new EMCGoToMainTableEditor(new EMCStringDocument(), new SOPCustomersMenu()));
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnPopulate = new emcJButton("Populate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCQuery nested = null;
                if (!tableColumns.isEmpty()) {
                    nested = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                    nested.addTable(SOPCustomers.class.getName());
                    for (String[] sa : tableColumns) {
                        nested.addAnd(sa[2], SOPCustomers.class.getName(), EMCQueryConditions.EQUALS, "customerItemField" + sa[0], SOPSalesRepCommission.class.getName());
                    }
                    nested.addField("customerId", SOPCustomers.class.getName());
                    nested.setSelectDistinctAll(true);
                }
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                query.addAnd("repService", SOPCommissionTypes.MULTIPLE.toString());
                if (nested != null) {
                    query.addAnd("customerId", nested, EMCQueryConditions.NOT_IN);
                }

                SOPCustomerRepEnquiry.this.userData.setUserData(0, query);
                SOPCustomerRepEnquiry.this.dataRelation.setUserData(SOPCustomerRepEnquiry.this.userData);
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPopulate);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private boolean getParameters() {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_SOPPARAMETER);
        List paramHolder = new ArrayList();
        paramHolder = EMCWSManager.executeGenericWS(cmd, paramHolder, userData);
        if (paramHolder.size() > 1) {
            SOPParameters param = (SOPParameters) paramHolder.get(1);
            String custTable = SOPCustomers.class.getName();
            if (param.getCustomerItemTable1() != null && param.getCustomerItemTable1().equals(custTable)) {
                tableColumns.add(new String[]{"1", param.getCustomerItemTable1(), param.getCustomerItemField1()});
            }
            if (param.getCustomerItemTable2() != null && param.getCustomerItemTable2().equals(custTable)) {
                tableColumns.add(new String[]{"2", param.getCustomerItemTable2(), param.getCustomerItemField2()});
            }
            if (param.getCustomerItemTable3() != null && param.getCustomerItemTable3().equals(custTable)) {
                tableColumns.add(new String[]{"3", param.getCustomerItemTable3(), param.getCustomerItemField3()});
            }
            if (param.getCustomerItemTable4() != null && param.getCustomerItemTable4().equals(custTable)) {
                tableColumns.add(new String[]{"4", param.getCustomerItemTable4(), param.getCustomerItemField4()});
            }
            if (param.getCustomerItemTable5() != null && param.getCustomerItemTable5().equals(custTable)) {
                tableColumns.add(new String[]{"5", param.getCustomerItemTable5(), param.getCustomerItemField5()});
            }
            if (param.getCustomerItemTable6() != null && param.getCustomerItemTable6().equals(custTable)) {
                tableColumns.add(new String[]{"6", param.getCustomerItemTable6(), param.getCustomerItemField6()});
            }
            if (!tableColumns.isEmpty()) {
                return true;
            }
        }
        Logger.getLogger("emc").log(Level.SEVERE, "No SOP Parameters set up. Failed to open form.", userData);
        return false;
    }
}
