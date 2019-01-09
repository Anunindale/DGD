/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.safetystockgenerationrules;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventorySafetyStockGenerationRules;
import emc.entity.sop.SOPCustomers;
import emc.enums.inventory.safetystock.SafetyStockGenerationRules;
import emc.enums.inventory.safetystock.SafetyStockGranularity;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsMarketingGroup;
import emc.menus.inventory.menuitems.display.ItemGroups;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

/**
 *
 * @author wikus
 */
public class InventorySafetyStockGenerationRulesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public InventorySafetyStockGenerationRulesForm(EMCUserData userData) {
        super("Safety Stock Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 400);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new InventorySafetyStockGenerationRules(), userData), userData);
        dataManager.setDummyForm(this);
        this.setDataManager(dataManager);
        initFrame();
    }

    private void initFrame() {

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Generation Rules", generationRulesPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contentPane);
    }

    private Component generationRulesPane() {
        emcYesNoComponent ynFromForecast = new emcYesNoComponent(dataManager, "fromForecast");
        ynFromForecast.setSelectedItem("Yes");
        ynFromForecast.setEditable(false);

        emcYesNoComponent ynSafetyStockForecastOnly = new emcYesNoComponent(dataManager, "safetyStockForecastOnly");

        EMCLookupFormComponent lkpCustomerGroup = new EMCLookupFormComponent(new DebtorsMarketingGroup(), dataManager, "customerGroup");
        lkpCustomerGroup.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsMarketingGroup(), "marketingGroup", userData));

        EMCLookupFormComponent lkpCustomer = new EMCLookupFormComponent(new SOPCustomersMenu(), dataManager, "customer");
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        EMCLookupFormComponent lkpItemGroup = new EMCLookupFormComponent(new ItemGroups(), dataManager, "itemGroup");
        lkpItemGroup.setPopup(new EMCLookupPopup(new InventoryItemGroup(), "itemGroup", userData));

        EMCLookupFormComponent lkpItem = EMCItemLookupFactory.createItemFormLookup(userData, dataManager, "item");

        EMCFormComboBox cbGranularity = new EMCFormComboBox(SafetyStockGranularity.values(), dataManager, "granularity");

        emcJTextField txtSafetyStockHorizon = new emcJTextField(new EMCIntegerFormDocument(dataManager, "safetyStockHorizon"));

        EMCFormComboBox cbGenerationRule = new EMCFormComboBox(SafetyStockGenerationRules.values(), dataManager, "generationRule");

        emcJTextField txtNumGranularityForSumt = new emcJTextField(new EMCIntegerFormDocument(dataManager, "numGranularityForSum"));

        emcYesNoComponent ynIncludeCurrentWeek = new emcYesNoComponent(dataManager, "includeCurrentWeek");

        emcYesNoComponent ynRegenerateOnMPS = new emcYesNoComponent(dataManager, "regenerateOnMPS");

        Component[][] comp = {{new emcJLabel(dataManager.getFieldEmcLabel("fromForecast")), ynFromForecast},
            {new emcJLabel(dataManager.getFieldEmcLabel("safetyStockForecastOnly")), ynSafetyStockForecastOnly},
            {new emcJLabel(dataManager.getFieldEmcLabel("customerGroup")), lkpCustomerGroup},
            {new emcJLabel(dataManager.getFieldEmcLabel("customer")), lkpCustomer},
            {new emcJLabel(dataManager.getFieldEmcLabel("itemGroup")), lkpItemGroup},
            {new emcJLabel(dataManager.getFieldEmcLabel("item")), lkpItem},
            {new emcJLabel(dataManager.getFieldEmcLabel("granularity")), cbGranularity},
            {new emcJLabel(dataManager.getFieldEmcLabel("safetyStockHorizon")), txtSafetyStockHorizon},
            {new emcJLabel(dataManager.getFieldEmcLabel("generationRule")), cbGenerationRule},
            {new emcJLabel(dataManager.getFieldEmcLabel("numGranularityForSum")), txtNumGranularityForSumt},
            {new emcJLabel(dataManager.getFieldEmcLabel("includeCurrentWeek")), ynIncludeCurrentWeek},
            {new emcJLabel(dataManager.getFieldEmcLabel("regenerateOnMPS")), ynRegenerateOnMPS}
        };

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
}
