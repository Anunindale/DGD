/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.sop.display.customerlabels;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.sop.SOPCustomerLabelDocDropDown;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.sop.SOPCustomerLabels;
import emc.entity.sop.SOPCustomers;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class CustomerLabelsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public CustomerLabelsForm(EMCUserData userData) {
        super("Customer Box Labels", true, true, true, true, userData);
        this.setBounds(20, 20, 570, 290);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPCustomerLabels(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("customerId");
            dataManager.setFormTextId2("custLabelDocument");
        } catch (Exception ex) {
        }
        initFrame(userData.copyUserData());
    }

    private void initFrame(EMCUserData userData) {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Overview", tablePane(userData));
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane(EMCUserData userData) {
        List keys = new ArrayList();
        keys.add("customerId");
        keys.add("custLabelDocument");
        keys.add("labelTitle");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setComboBoxLookupToColumn("custLabelDocument", new SOPCustomerLabelDocDropDown());
        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
        table.setLookupToColumn("customerId", lkpCustomer);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
