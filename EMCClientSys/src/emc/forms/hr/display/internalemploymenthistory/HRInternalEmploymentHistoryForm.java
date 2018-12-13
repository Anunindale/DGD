/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.internalemploymenthistory;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseCompanyTable;
import emc.entity.hr.HRBranch;
import emc.entity.hr.HRGrade;
import emc.entity.hr.HRInternalEmploymentHistory;
import emc.entity.hr.HRSection;
import emc.entity.workflow.WorkFlowDepartment;
import emc.entity.workflow.WorkFlowJobTitles;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.companies;
import emc.menus.hr.menuitems.display.HRBranchMenu;
import emc.menus.hr.menuitems.display.HRGradeMenu;
import emc.menus.hr.menuitems.display.HRSectionMenu;
import emc.menus.workflow.menuitems.display.Departments;
import emc.menus.workflow.menuitems.display.JobTitles;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRInternalEmploymentHistoryForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HRInternalEmploymentHistoryForm(EMCUserData userData) {
        super("Internal Employment History", true, true, true, true, userData);
        this.setBounds(20, 20, 690, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRInternalEmploymentHistory(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("jobTitle");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HRInternalEmploymentHistory.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Internal Employment History", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("company");
        keys.add("jobTitle");
        keys.add("grade");
        keys.add("department");
        keys.add("branch");
        keys.add("sectionId");
        keys.add("fromDate");
        keys.add("toDate");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupPopup compPop = new EMCLookupPopup(new BaseCompanyTable(), "companyId", userData);
        EMCLookupJTableComponent lkpComp = new EMCLookupJTableComponent(new companies());
        lkpComp.setPopup(compPop);
        table.setLookupToColumn("company", lkpComp);

        EMCLookupPopup jobTitlePop = new EMCLookupPopup(new WorkFlowJobTitles(), "jobTitle", userData);
        EMCLookupJTableComponent lkpJobTile = new EMCLookupJTableComponent(new JobTitles());
        lkpJobTile.setPopup(jobTitlePop);
        table.setLookupToColumn("jobTitle", lkpJobTile);

        EMCLookupPopup gradePop = new EMCLookupPopup(new HRGrade(), "grade", userData);
        EMCLookupJTableComponent lkpGrade = new EMCLookupJTableComponent(new HRGradeMenu());
        lkpGrade.setPopup(gradePop);
        table.setLookupToColumn("grade", lkpGrade);

        EMCLookupPopup departmentPop = new EMCLookupPopup(new WorkFlowDepartment(), "department", userData);
        EMCLookupJTableComponent lkpDepartment = new EMCLookupJTableComponent(new Departments());
        lkpDepartment.setPopup(departmentPop);
        table.setLookupToColumn("department", lkpDepartment);

        EMCLookupPopup branchPOP = new EMCLookupPopup(new HRBranch(), "branch", userData);
        EMCLookupJTableComponent lkpBranch = new EMCLookupJTableComponent(new HRBranchMenu());
        lkpBranch.setPopup(branchPOP);
        table.setLookupToColumn("branch", lkpBranch);

        EMCLookupPopup sectionPop = new EMCLookupPopup(new HRSection(), "sectionId", userData);
        EMCLookupJTableComponent lkpSection = new EMCLookupJTableComponent(new HRSectionMenu());
        lkpSection.setPopup(sectionPop);
        table.setLookupToColumn("sectionId", lkpSection);
    }
}
