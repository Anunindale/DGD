/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.approvalgroupsemployees;

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
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class PurchaseOrderApprovalGroupEmployeesForm extends BaseInternalFrame {

    private emcJPanel pnlApprovalGroupEmp = new emcJPanel();
    private emcJPanel pnlApprovalGroup = new emcJPanel();
    private emcJLabel lblApprovalGroup = new emcJLabel("Approval Group");
    private EMCControlLookupComponent lkpApprovalGroup;
    private emcJLabel lblApprovalGroupDesc = new emcJLabel("Description");
    private emcJTextField txtApprovalGroupDesc = new emcJTextField();
    private EMCLookupJTableComponent lkpEmployee;
    private EMCLookupTableCellEditor empEditor;
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;

    public PurchaseOrderApprovalGroupEmployeesForm(EMCUserData userData) {
        super("Approval Group Employees", true, true, true, true, userData);
        EMCUserData copyUD = userData.copyUserData();
        this.setBounds(20, 20, 600, 290);
        try {
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new emc.entity.pop.datasource.POPPurchaseOrderApprovalGroupEmployeesDS(), userData), userData);

            lkpApprovalGroup = new EMCControlLookupComponent(new emc.menus.pop.menuitems.display.PurchaseOrderApprovalGroup(), dataRelation, "purchaseOrderApprovalGroupId", txtApprovalGroupDesc, "description", POPPurchaseOrderApprovalGroupEmployees.class.getName());
            
            
            List groupKeys = new ArrayList();
            groupKeys.add("purchaseOrderApprovalGroupId");
            groupKeys.add("description");

            EMCLookupPopup purchaseOrderApprovalGroupsPopup = new EMCLookupPopup(enumEMCModules.POP.getId(), new emc.entity.pop.POPPurchaseOrderApprovalGroups(),
                    "purchaseOrderApprovalGroupId", groupKeys, copyUD);

            lkpApprovalGroup.setPopup(purchaseOrderApprovalGroupsPopup);

            dataRelation.setLookup(lkpApprovalGroup);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroupEmployees.class.getName());
            lkpApprovalGroup.setFormQuery(query);

            List empKeys = new ArrayList<String>();
            empKeys.add("employeeNumber");
            empKeys.add("forenames");
            empKeys.add("surname");

            lkpEmployee = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.employees());
            EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                    "employeeNumber", empKeys, copyUD);
            lkpEmployee.setPopup(empPopup);
            empEditor = new EMCLookupTableCellEditor(lkpEmployee);

            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("purchaseOrderApprovalGroupId");
            dataRelation.setFormTextId2("employeeId");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Purchase Order Approval Group Employees Form", userData);
            }
        }

        setupComponents();
        initFrame();
    }

    private void setupComponents() {
        txtApprovalGroupDesc.setEditable(false);

    }

    private void createItemPanel() {
        pnlApprovalGroup.setBorder(javax.swing.BorderFactory.createTitledBorder("Purchase Order Approval Group"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlApprovalGroup.add(this.lblApprovalGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        pnlApprovalGroup.add(this.lkpApprovalGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlApprovalGroup.add(this.lblApprovalGroupDesc, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlApprovalGroup.add(this.txtApprovalGroupDesc, localg);
    }

    private void tabApprovalGroupEmp() {
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("employeeId");
        keys.add("employeeName");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys){

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if(columnIndex == 1){
                    return false;
                }
                return super.isCellEditable(rowIndex, columnIndex);
            }
            
        };
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setLookupCellEditor(0, empEditor);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlApprovalGroupEmp.setLayout(new GridBagLayout());
        createItemPanel();
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        pnlApprovalGroupEmp.add(pnlApprovalGroup, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        pnlApprovalGroupEmp.add(topscroll, localg);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabApprovalGroupEmp();
        tabbedPanetop.add("Purchase Order Group Employees", this.pnlApprovalGroupEmp);
        this.add(tabbedPanetop);
    }
}
