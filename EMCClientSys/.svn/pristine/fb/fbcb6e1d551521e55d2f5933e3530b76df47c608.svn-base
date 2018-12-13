/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommissionenquiry;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.datasource.SOPSalesRepCommissionEnquiry;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SOPSalesRepCommissionEnquiryForm extends BaseInternalFrame {

    private emcDRMViewOnly dataManager;
    private EMCUserData userData;
    private List<String[]> tableColumns = new ArrayList<String[]>();

    public SOPSalesRepCommissionEnquiryForm(EMCUserData userData) {
        super("Sales Rep Commission Enquiry", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 350);
        this.userData = userData.copyUserDataAndDataList();
        if (!getParameters()) {
            this.dispose();
            return;
        }
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPSalesRepCommissionEnquiry(), userData), userData) {

            @Override
            public String getColumnName(String columnIndex) {
                if (columnIndex.equals("customerItemField1")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(0)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc1")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(0)[2]);
                } else if (columnIndex.equals("customerItemField2")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(1)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc2")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(1)[2]);
                } else if (columnIndex.equals("customerItemField3")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(2)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc3")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(2)[2]);
                } else if (columnIndex.equals("customerItemField4")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(3)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc4")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(3)[2]);
                } else if (columnIndex.equals("customerItemField5")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(4)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc5")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(4)[2]);
                } else if (columnIndex.equals("customerItemField6")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(5)[1]);
                } else if (columnIndex.equals("customerItemFieldDesc6")) {
                    return SOPSalesRepCommissionEnquiryForm.this.buildColumnName(tableColumns.get(5)[2]);
                } else {
                    return super.getColumnName(columnIndex);
                }
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("repId");
        dataManager.setFormTextId2("repName");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        tabbed.add("Manager", managerPane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("repGroup");
        keys.add("repGroupDescription");
        keys.add("repId");
        keys.add("repName");
        keys.add("defaultCommission");
        for (int c = 0; c < tableColumns.size(); c++) {
            keys.add("customerItemField" + (c + 1));
            if (!Functions.checkBlank(tableColumns.get(c)[2])) {
                keys.add("customerItemFieldDesc" + (c + 1));
            }
        }
        keys.add("repCommission");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel managerPane() {
        emcJTextField txtManager = new emcJTextField(new EMCStringFormDocument(dataManager, "repGroupManager"));
        txtManager.setEditable(false);
        emcJTextField txtManagerName = new emcJTextField(new EMCStringFormDocument(dataManager, "repGroupManagerName"));
        txtManagerName.setEditable(false);
        emcJTextField txtManagerCommission = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "managerCommission"));
        txtManagerCommission.setEditable(false);
        Component[][] comp = {{new emcJLabel("Manager"), txtManager, new emcJLabel("Name"), txtManagerName},
            {new emcJLabel("Manager Commission"), txtManagerCommission}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Manager");
    }

    private boolean getParameters() {
        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_SOPPARAMETER);
        List paramHolder = new ArrayList();
        paramHolder = EMCWSManager.executeGenericWS(cmd, paramHolder, userData);
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
