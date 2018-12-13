/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemaccessgroupusers;

import emc.app.components.emcJPanel;
import emc.app.components.emcJLabel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.InventoryItemAccessGroupEmployees;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author wikus
 */
public class ItemAccessGroupEmployeesForm extends BaseInternalFrame {

    private emcJPanel pnlAccessGroupEmp = new emcJPanel();
    private emcJPanel pnlAccessGroup = new emcJPanel();
    private emcJLabel lblAccessGroup = new emcJLabel("Item Access Group");
    private EMCControlLookupComponent lkpAccessGroup;
    private emcJLabel lblAccessGroupDesc = new emcJLabel("Description");
    private emcJTextField txtAccessGroupDesc = new emcJTextField();
    private EMCLookupJTableComponent lkpEmployee;
    private EMCLookupTableCellEditor empEditor;
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;

    public ItemAccessGroupEmployeesForm(EMCUserData userData) {
        super("Approval Group Employees", true, true, true, true, userData);
        EMCUserData copyUD = userData.copyUserData();
        this.setBounds(20, 20, 600, 290);
        try {

            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.datasource.InventoryItemAccessGroupEmployeesDS(), userData), userData);
            
            lkpAccessGroup = new EMCControlLookupComponent(new emc.menus.inventory.menuitems.display.ItemAccessGroup(), dataRelation, "itemAccessGroupId", txtAccessGroupDesc, "description", InventoryItemAccessGroupEmployees.class.getName());
            EMCLookupPopup accessGroupPopup = new EMCLookupPopup(new emc.entity.inventory.InventoryItemAccessGroup(), "itemAccessGroupId", userData);
            lkpAccessGroup.setPopup(accessGroupPopup);
            dataRelation.setLookup(lkpAccessGroup);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemAccessGroupEmployees.class.getName());
            lkpAccessGroup.setFormQuery(query);

            List groupKeys = new ArrayList();
            groupKeys.add("itemAccessGroupId");
            groupKeys.add("description");

            EMCLookupPopup itemAccessGroupsPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.InventoryItemAccessGroup(),
                    "itemAccessGroupId", groupKeys, copyUD);

            lkpAccessGroup.setPopup(itemAccessGroupsPopup);

            List empKeys = new ArrayList<String>();
            empKeys.add("employeeNumber");
            empKeys.add("forenames");
            empKeys.add("surname");

            lkpEmployee = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.employees());
            EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                    "employeeNumber", empKeys, copyUD);
            empEditor = new EMCLookupTableCellEditor(lkpEmployee);
            lkpEmployee.setPopup(empPopup);
            
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("itemAccessGroupId");
            dataRelation.setFormTextId2("employeeId");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Item Access Group Employees Form", userData);
            }
        }

        setupComponents();
        initFrame();
    }

    private void setupComponents() {
        txtAccessGroupDesc.setEditable(false);

    }

    private void createItemPanel() {
        pnlAccessGroup.setBorder(javax.swing.BorderFactory.createTitledBorder("Item Access Group"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlAccessGroup.add(this.lblAccessGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        pnlAccessGroup.add(this.lkpAccessGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlAccessGroup.add(this.lblAccessGroupDesc, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlAccessGroup.add(this.txtAccessGroupDesc, localg);
    }

    private void tabApprovalGroupEmp() {
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("employeeId");
        keys.add("employeeName");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setLookupCellEditor(0, empEditor);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlAccessGroupEmp.setLayout(new GridBagLayout());
        createItemPanel();
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        pnlAccessGroupEmp.add(pnlAccessGroup, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        pnlAccessGroupEmp.add(topscroll, localg);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabApprovalGroupEmp();
        tabbedPanetop.add("Item Access Employees", this.pnlAccessGroupEmp);
        this.add(tabbedPanetop);
    }
}
