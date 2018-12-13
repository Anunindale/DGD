/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.approvalgroups;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author wikus
 */
public class PurchaseOrderApprovalGroupsForm extends BaseInternalFrame {

    private emcJPanel thePanel = new emcJPanel();
    private emcJPanel pnlApprovalGroups = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    private PurchaseOrderApprovalGroupsFormDRM dataRelation;
    private EMCUserData cpUserData;

    public PurchaseOrderApprovalGroupsForm(EMCUserData userData) {
        super("Purchase Order Approval Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            cpUserData = userData.copyUserData();
            dataRelation = new PurchaseOrderApprovalGroupsFormDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new emc.entity.pop.POPPurchaseOrderApprovalGroups(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("purchaseOrderApprovalGroupId");
            dataRelation.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabAppprovalGroups() {
        List keys = new ArrayList();
        keys.add("purchaseOrderApprovalGroupId");
        keys.add("description");
        keys.add("itemGroup");
        keys.add("maxValue");
        keys.add("higherLevelApprovalGroups");
        keys.add("allowCancel");
        keys.add("priceQtyChange");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        EMCLookupJTableComponent lkpItemGroup = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ItemGroups());
        EMCLookupPopup itemGrpPopup = new EMCLookupPopup(new emc.entity.inventory.InventoryItemGroup(), "itemGroup", cpUserData);
        lkpItemGroup.setPopup(itemGrpPopup);

        EMCLookupJTableComponent lkpHigerLevelApprovalGroups = new EMCLookupJTableComponent(new PurchaseOrderApprovalGroup());
        EMCLookupPopup higherLevelApprovalGroupsPopup = new EMCLookupPopup(new POPPurchaseOrderApprovalGroups(), "purchaseOrderApprovalGroupId", cpUserData);
        lkpHigerLevelApprovalGroups.setPopup(higherLevelApprovalGroupsPopup);

        toptable.setLookupToColumn("itemGroup", lkpItemGroup);
        toptable.setLookupToColumn("higherLevelApprovalGroups", lkpHigerLevelApprovalGroups);
        
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlApprovalGroups.setLayout(new GridLayout(1, 1));
        pnlApprovalGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void tabButtons() {
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new emcMenuButton("Approval Employees", new emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroupEmployees(), this, 0, false), gbc);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabAppprovalGroups();
        tabButtons();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(pnlApprovalGroups, BorderLayout.CENTER);
        thePanel.add(pnlButtons, BorderLayout.EAST);
        tabbedPanetop.add("Purchase Order Approval Groups", this.thePanel);
        this.add(tabbedPanetop);
    }
}
