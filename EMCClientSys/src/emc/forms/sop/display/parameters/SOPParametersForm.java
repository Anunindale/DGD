/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.parameters;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.tables.EMCFormFieldsMapComboBox;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesOrderCancelReason;
import emc.enums.modules.enumEMCModules;
import emc.enums.sop.parameters.UseInvoiceTo;
import emc.forms.sop.display.parameters.resources.SOPParametersTableMapComboBox;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPSalesOrderCancelMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

/**
 *
 * @author wikus
 */
public class SOPParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public SOPParametersForm(EMCUserData userData) {
        super("Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 450);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPParameters(), userData), userData);
        dataManager.setDummyForm(this);
        this.setDataManager(dataManager);
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Parameters", paramPane());
        tabbed.add("Commission", commissionPane());
        tabbed.add("Contact", createContactPanel());
        this.setContentPane(tabbed);
    }

    private emcJPanel paramPane() {
        emcYesNoComponent allowStandardPrice = new emcYesNoComponent(dataManager, "allowStandardPrice");
        emcYesNoComponent allowPartialPost = new emcYesNoComponent(dataManager, "allowPartialPost");
        emcYesNoComponent allowPartialBOPost = new emcYesNoComponent(dataManager, "allowPartialBOPost");
        emcYesNoComponent ysnForcePriceEntry = new emcYesNoComponent(dataManager, "forcePriceEntry");
        emcYesNoComponent ysnUseLowestPrice = new emcYesNoComponent(dataManager, "useLowestPrice");
        EMCFormComboBox cmbUseInvoiceTo = new EMCFormComboBox(UseInvoiceTo.values(), dataManager, "useInvoiceTo");
        emcJTextField txtMinimumOrderValue = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "minimumOrderValue"));
        emcYesNoComponent ysnAllowDuplicateSOLines = new emcYesNoComponent(dataManager, "allowDuplicateSOLines");
        emcYesNoComponent ysnAllowPartialSTKAssembly = new emcYesNoComponent(dataManager, "allowPartialSTKAssembly");
        EMCLookupFormComponent lkpBackOrderCancelReason = new EMCLookupFormComponent(new SOPSalesOrderCancelMenu(), dataManager, "backOrderCancelReason");
        lkpBackOrderCancelReason.setPopup(new EMCLookupPopup(new SOPSalesOrderCancelReason(), "reasonId", userData));

        Component[][] comp = {{new emcJLabel("Allow Standard Price"), allowStandardPrice},
            {new emcJLabel("Allow Partial Post"), allowPartialPost},
            {new emcJLabel("Allow Partial BO Assembly"), allowPartialBOPost},
            {new emcJLabel(dataManager.getColumnName("allowPartialSTKAssembly")), ysnAllowPartialSTKAssembly},
            {new emcJLabel(dataManager.getColumnName("forcePriceEntry")), ysnForcePriceEntry},
            {new emcJLabel(dataManager.getColumnName("useInvoiceTo")), cmbUseInvoiceTo},
            {new emcJLabel("Minimum Order Value"), txtMinimumOrderValue},
            {new emcJLabel(dataManager.getColumnName("useLowestPrice")), ysnUseLowestPrice},
            {new emcJLabel(dataManager.getColumnName("allowDuplicateSOLines")), ysnAllowDuplicateSOLines},
            {new emcJLabel("Back Order Cancel Reason"), lkpBackOrderCancelReason}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Parameters");
    }

    private Component commissionPane() {
        EMCFormFieldsMapComboBox customerItemField1 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField1");
        EMCFormFieldsMapComboBox customerItemFieldDesc1 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc1", true);
        SOPParametersTableMapComboBox customerItemTable1 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable1", customerItemField1, customerItemFieldDesc1);
        EMCFormFieldsMapComboBox customerItemField2 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField2");
        EMCFormFieldsMapComboBox customerItemFieldDesc2 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc2", true);
        SOPParametersTableMapComboBox customerItemTable2 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable2", customerItemField2, customerItemFieldDesc2);
        EMCFormFieldsMapComboBox customerItemField3 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField3");
        EMCFormFieldsMapComboBox customerItemFieldDesc3 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc3", true);
        SOPParametersTableMapComboBox customerItemTable3 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable3", customerItemField3, customerItemFieldDesc3);
        EMCFormFieldsMapComboBox customerItemField4 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField4");
        EMCFormFieldsMapComboBox customerItemFieldDesc4 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc4", true);
        SOPParametersTableMapComboBox customerItemTable4 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable4", customerItemField4, customerItemFieldDesc4);
        EMCFormFieldsMapComboBox customerItemField5 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField5");
        EMCFormFieldsMapComboBox customerItemFieldDesc5 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc5", true);
        SOPParametersTableMapComboBox customerItemTable5 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable5", customerItemField5, customerItemFieldDesc5);
        EMCFormFieldsMapComboBox customerItemField6 = new EMCFormFieldsMapComboBox(dataManager, "customerItemField6");
        EMCFormFieldsMapComboBox customerItemFieldDesc6 = new EMCFormFieldsMapComboBox(dataManager, "customerItemFieldDesc6", true);
        SOPParametersTableMapComboBox customerItemTable6 = new SOPParametersTableMapComboBox(dataManager, "customerItemTable6", customerItemField6, customerItemFieldDesc6);

        EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.DISABLE_COMMISSION_FIELDS);
        if ((Boolean) EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData).get(1)) {
            customerItemTable1.setEnabled(false);
            customerItemField1.setEnabled(false);
            customerItemTable2.setEnabled(false);
            customerItemField2.setEnabled(false);
            customerItemTable3.setEnabled(false);
            customerItemField3.setEnabled(false);
            customerItemTable4.setEnabled(false);
            customerItemField4.setEnabled(false);
            customerItemTable5.setEnabled(false);
            customerItemField5.setEnabled(false);
            customerItemTable6.setEnabled(false);
            customerItemField6.setEnabled(false);
            //Logger.getLogger("emc").log(Level.WARNING, "Sales Rep Commission structure already exists. Commission fields disabled.");
        }

        Component[][] comp = {
            {new emcJLabel("Table"), customerItemTable1, new emcJLabel("Field"), customerItemField1},
            {new emcJLabel("Description"), customerItemFieldDesc1},
            {new emcJLabel("Table"), customerItemTable2, new emcJLabel("Field"), customerItemField2},
            {new emcJLabel("Description"), customerItemFieldDesc2},
            {new emcJLabel("Table"), customerItemTable3, new emcJLabel("Field"), customerItemField3},
            {new emcJLabel("Description"), customerItemFieldDesc3},
            {new emcJLabel("Table"), customerItemTable4, new emcJLabel("Field"), customerItemField4},
            {new emcJLabel("Description"), customerItemFieldDesc4},
            {new emcJLabel("Table"), customerItemTable5, new emcJLabel("Field"), customerItemField5},
            {new emcJLabel("Description"), customerItemFieldDesc5},
            {new emcJLabel("Table"), customerItemTable6, new emcJLabel("Field"), customerItemField6},
            {new emcJLabel("Description"), customerItemFieldDesc6}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Commission");
    }

    /**
     * Creates the Production tab.
     */
    /**
     * Creates contact panel.
     */
    private emcJPanel createContactPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel(dataManager.getColumnName("faxNumber")), new emcJTextField(new EMCStringFormDocument(dataManager, "faxNumber"))}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
}
