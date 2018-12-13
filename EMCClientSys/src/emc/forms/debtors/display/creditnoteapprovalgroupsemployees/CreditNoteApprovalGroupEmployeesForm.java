/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnoteapprovalgroupsemployees;

import emc.app.components.controllookup.EMCControlLookupPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroupEmployees;
import emc.entity.debtors.DebtorsCreditNoteApprovalGroups;
import emc.entity.debtors.datasource.DebtorsCreditNoteApprovalGroupEmployeesDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.debtors.menuitems.display.DebtorsCreditNoteApprovalGroupsMI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riaan
 */
public class CreditNoteApprovalGroupEmployeesForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    
    public CreditNoteApprovalGroupEmployeesForm(EMCUserData userData) {
        super("Credit Note Approval Group Employees", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        this.dataRelation = new EMCControlLookupComponentDRM(
                new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCreditNoteApprovalGroupEmployeesDS(), userData), userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("approvalGroupId");
        dataRelation.setFormTextId2("employeeId");

        initFrame(userData);
    }

    private void initFrame(EMCUserData userData) {
        List keys = new ArrayList();
        keys.add("employeeId");
        keys.add("employeeName");

        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        
        EMCLookupJTableComponent lkpEmployeeId = new EMCLookupJTableComponent(new employees());
        lkpEmployeeId.setPopup(new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData));

        table.setLookupToColumn("employeeId", lkpEmployeeId);

        emcJTextField txtDesc = new emcJTextField();
        txtDesc.setEditable(false);

        EMCControlLookupComponent lkpApprovalGroup = new EMCControlLookupComponent(new DebtorsCreditNoteApprovalGroupsMI(), dataRelation, "approvalGroupId", txtDesc, "description", DebtorsCreditNoteApprovalGroupEmployees.class.getName());
        dataRelation.setLookup(lkpApprovalGroup);
        lkpApprovalGroup.setPopup(new EMCLookupPopup(new DebtorsCreditNoteApprovalGroups(), "approvalGroupId", userData));

        this.add(new EMCControlLookupPanel("Credit Note Approval Group Employees", "Approval Group", lkpApprovalGroup, "Description", txtDesc, tableScroll, "Employees"));
    }
}
