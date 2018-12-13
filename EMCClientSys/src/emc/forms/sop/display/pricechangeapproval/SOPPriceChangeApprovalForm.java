/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricechangeapproval;

import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.components.lookup.factory.EMCItemLookupFactory;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.sop.datasource.SOPPriceChangeApprovalDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class SOPPriceChangeApprovalForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;
    //Used to refresh Sales Order master after approval
    private emcDataRelationManagerUpdate salesOrderDRM;

    public SOPPriceChangeApprovalForm(EMCUserData userData) {
        super("Price Change Approval", true, true, true, true, userData);
        this.setBounds(20, 20, 850, 600);
        this.userData = userData.copyUserDataAndDataList();

        if (userData.getUserData(3) instanceof emcDataRelationManagerUpdate) {
            this.salesOrderDRM = (emcDataRelationManagerUpdate) userData.getUserData().remove(3);
        }

        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPPriceChangeApprovalDS(), userData), userData) {

            @Override
            public void updatePersist(int rowIndex) {
                super.updatePersist(rowIndex);
            }

            @Override
            public boolean persistOnClosing() {
                boolean ret = super.persistOnClosing();

                if (ret) {
                    salesOrderDRM.refreshRecordIgnoreFocus(salesOrderDRM.getLastRowAccessed());
                }

                return ret;
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You may not delete rows from this table.", this.getUserData());
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerId");
        dataManager.setFormTextId2("itemReference");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane topTabbed = new emcJTabbedPane();
        topTabbed.add("Overview", tablePane());
        emcJPanel topPane = new emcJPanel(new BorderLayout());
        topPane.add(topTabbed, BorderLayout.CENTER);

        emcJTabbedPane bottomTabbed = new emcJTabbedPane();
        bottomTabbed.add("Detail", detailPane());
        emcJPanel bottomPane = new emcJPanel(new BorderLayout());
        bottomPane.add(bottomTabbed, BorderLayout.CENTER);

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, bottomPane);
        topBottomSplit.setDividerLocation(250);

        this.setContentPane(topBottomSplit);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerId");
        keys.add("customerName");
        keys.add("itemReference");
        keys.add("itemDescription");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("approveLine");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnEditable("customerId", false);
        table.setColumnEditable("customerName", false);
        table.setColumnEditable("itemReference", false);
        table.setColumnEditable("itemDescription", false);
        table.setColumnEditable("dimension1", false);
        table.setColumnEditable("dimension2", false);
        table.setColumnEditable("dimension3", false);

        //Use lookups to enable go to main table
        setupLookups(table);

        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        EMCLookupJTableComponent lkpItemRef = EMCItemLookupFactory.createItemLookup(userData);
        EMCLookupJTableComponent lkpDimension1 = EMCDimensionLookupFactory.createDimension1JTableLookup(userData);
        EMCLookupJTableComponent lkpDimension2 = EMCDimensionLookupFactory.createDimension2JTableLookup(userData);
        EMCLookupJTableComponent lkpDimension3 = EMCDimensionLookupFactory.createDimension3JTableLookup(userData);

        table.setLookupToColumn("customerId", lkpCustomer);
        table.setLookupToColumn("itemReference", lkpItemRef);
        table.setLookupToColumn("dimension1", lkpDimension1);
        table.setLookupToColumn("dimension2", lkpDimension2);
        table.setLookupToColumn("dimension3", lkpDimension3);
    }

    private Component detailPane() {
        emcJTextField txtCurrentPrice = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "priceListPrice"));
        txtCurrentPrice.setEditable(false);
        emcJTextField txtOriginalPrice = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "originalPrice"));
        txtOriginalPrice.setEditable(false);
        emcJTextField txtSOPrice = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "price"));
        txtSOPrice.setEditable(false);
        emcJTextField txtPriceReason = new emcJTextField(new EMCStringFormDocument(dataManager, "priceChangeReason"));
        txtPriceReason.setEditable(false);
        emcJTextField txtPriceReasonDesc = new emcJTextField(new EMCStringFormDocument(dataManager, "priceChangeReasonDescription"));
        txtPriceReasonDesc.setEditable(false);

        emcJTextField txtCurrentDiscount = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "discountGroupDiscountPerc"));
        txtCurrentDiscount.setEditable(false);
        emcJTextField txtOriginalDiscount = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "originalDiscountPerc"));
        txtOriginalDiscount.setEditable(false);
        emcJTextField txtSODiscount = new emcJTextField(new EMCBigDecimalFormDocument(dataManager, "discountPerc"));
        txtSODiscount.setEditable(false);
        emcJTextField txtDiscountReason = new emcJTextField(new EMCStringFormDocument(dataManager, "discountChangeReason"));
        txtDiscountReason.setEditable(false);
        emcJTextField txtDiscountReasonDesc = new emcJTextField(new EMCStringFormDocument(dataManager, "discountChangeReasonDescription"));
        txtDiscountReasonDesc.setEditable(false);

        Component[][] comp = {{new emcJLabel("List Price - Current"), txtCurrentPrice, new emcJLabel("List Discount - Current"), txtCurrentDiscount},
            {new emcJLabel("List Price - SO Entry"), txtOriginalPrice, new emcJLabel("List Discount - SO Entry"), txtOriginalDiscount},
            {new emcJLabel("SO Line Price"), txtSOPrice, new emcJLabel("SO Line Discount"), txtSODiscount},
            {new emcJLabel()},
            {new emcJLabel("Price Change Reason"), txtPriceReason, new emcJLabel("Discount Change Reason"), txtDiscountReason},
            {new emcJLabel("Description"), txtPriceReasonDesc, new emcJLabel("Description"), txtDiscountReasonDesc}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Detail");
    }

    @Override
    public void setUserData(EMCUserData userData) {
        if (userData.getUserData(3) instanceof emcDataRelationManagerUpdate) {
            this.salesOrderDRM = (emcDataRelationManagerUpdate) userData.getUserData().remove(3);
        }

        super.setUserData(userData);
    }
}
