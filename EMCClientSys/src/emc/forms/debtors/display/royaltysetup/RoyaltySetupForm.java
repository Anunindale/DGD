/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.royaltysetup;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.datatypes.EMCString;
import emc.entity.debtors.DebtorsRoyaltyGroups;
import emc.entity.debtors.DebtorsRoyaltySetup;
import emc.entity.inventory.InventoryItemMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.debtors.menuitems.display.DebtorsRoyaltyGroupsMI;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : This form is used to set up Royalties.
 *
 * @date        : 09 September 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class RoyaltySetupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of RoyaltySetupForm */
    public RoyaltySetupForm(EMCUserData userData) {
        super("Royalty Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);

        Object[] fields = getFieldsToDisplay(userData);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(getRoyaltySetupInstance(fields, userData), userData), userData);

        drm.setTheForm(this);
        this.setDataManager(drm);

        drm.setFormTextId1("field1Value");
        drm.setFormTextId2("royaltyGroup");

        this.initFrame(fields, userData);
    }

    /** Initializes the frame. */
    private void initFrame(Object[] fields, EMCUserData userData) {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab(fields, userData));

        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab(Object[] fields, EMCUserData userData) {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();

        if (!Functions.checkBlank(fields[0])) {
            keys.add("field1Value");
        }

        if (!Functions.checkBlank(fields[1])) {
            keys.add("field2Value");
        }

        if (!Functions.checkBlank(fields[2])) {
            keys.add("field3Value");
        }

        keys.add("royaltyGroup");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        this.setupLookups(table, fields, userData);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);

        return panel;
    }

    /** Gets fields to display. */
    private Object[] getFieldsToDisplay(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.GET_ROYALTY_FIELDS);

        List toSend = new ArrayList();

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Object[]) {
            return (Object[]) toSend.get(1);
        } else {
            return new Object[3];
        }
    }

    /** Creates and sets lookups on the specified emcJTableUpdate instance. */
    private void setupLookups(emcJTableUpdate table, Object[] fields, EMCUserData userData) {
        if (!Functions.checkBlank(fields[0])) {
            EMCLookupJTableComponent lkpField1 = new EMCLookupJTableComponent(new ItemMaster());

            List<String> keys = new ArrayList<String>();
            keys.add((String) fields[0]);

            lkpField1.setPopup(new EMCLookupPopup(new InventoryItemMaster(), (String) fields[0], keys, userData));

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addGroupBy((String)fields[0]);
            query.addOrderBy((String) fields[0]);

            lkpField1.setTheQuery(query);

            table.setLookupToColumn("field1Value", lkpField1);
        }

        if (!Functions.checkBlank(fields[1])) {
            EMCLookupJTableComponent lkpField2 = new EMCLookupJTableComponent(new ItemMaster());

            List<String> keys = new ArrayList<String>();
            keys.add((String) fields[1]);

            lkpField2.setPopup(new EMCLookupPopup(new InventoryItemMaster(), (String) fields[1], keys, userData));

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addGroupBy((String)fields[1]);
            query.addOrderBy((String) fields[1]);

            lkpField2.setTheQuery(query);

            table.setLookupToColumn("field2Value", lkpField2);
        }

        if (!Functions.checkBlank(fields[2])) {
            EMCLookupJTableComponent lkpField3 = new EMCLookupJTableComponent(new ItemMaster());

            List<String> keys = new ArrayList<String>();
            keys.add((String) fields[2]);

            lkpField3.setPopup(new EMCLookupPopup(new InventoryItemMaster(), (String) fields[2], keys, userData));

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addGroupBy((String)fields[2]);
            query.addOrderBy((String) fields[2]);

            lkpField3.setTheQuery(query);

            table.setLookupToColumn("field3Value", lkpField3);
        }

        EMCLookupJTableComponent tblLkpRoyaltyGroup = new EMCLookupJTableComponent(new DebtorsRoyaltyGroupsMI());
        tblLkpRoyaltyGroup.setPopup(new EMCLookupPopup(new DebtorsRoyaltyGroups(), "royaltyGroupId", userData));

        table.setLookupToColumn("royaltyGroup", tblLkpRoyaltyGroup);
    }

    /** This method returns a DebtorsRoyaltySetup instance with emc labels representing the corresponding fields on the InventoryItemMaster entity. */
    private DebtorsRoyaltySetup getRoyaltySetupInstance(Object[] fields, EMCUserData userData) {
        DebtorsRoyaltySetup royaltySetup = new DebtorsRoyaltySetup();

        InventoryItemMaster item = new InventoryItemMaster();

        if (!Functions.checkBlank(fields[0])) {
            EMCDataType dt = new EMCString();
            dt.setEmcLabel(item.getDisplayLabelForField((String) fields[0], userData));

            royaltySetup.getFieldDataTypeMapper().put("field1Value", dt);
        }

        if (!Functions.checkBlank(fields[1])) {
            EMCDataType dt = new EMCString();
            dt.setEmcLabel(item.getDisplayLabelForField((String) fields[1], userData));

            royaltySetup.getFieldDataTypeMapper().put("field2Value", dt);
        }

        if (!Functions.checkBlank(fields[2])) {
            EMCDataType dt = new EMCString();
            dt.setEmcLabel(item.getDisplayLabelForField((String) fields[2], userData));

            royaltySetup.getFieldDataTypeMapper().put("field3Value", dt);
        }

        return royaltySetup;
    }
}
