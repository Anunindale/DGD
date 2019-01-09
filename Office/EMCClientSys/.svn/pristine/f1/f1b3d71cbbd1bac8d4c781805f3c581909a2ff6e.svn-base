/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.departments;

import emc.app.components.emcHelpFile;
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
import emc.entity.base.employeeaccessgroup.BaseEmployeeAccessGroup;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseEmployeeAccessGroupMenu;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DepartmentsForm extends BaseInternalFrame {

    private emcJPanel pnlDepartments = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public DepartmentsForm(EMCUserData userData) {
        super("Departments", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowDepartments.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowDepartment(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("department");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabDepartments() {
        List keys = new ArrayList();
        keys.add("department");
        keys.add("description");
        keys.add("accessGroup");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        setupLookups(toptable);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDepartments.setLayout(new GridLayout(1, 1));
        pnlDepartments.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabDepartments();
        tabbedPanetop.add("Departments", this.pnlDepartments);
        this.add(tabbedPanetop);
    }

    private void setupLookups(emcJTableUpdate toptable) {
        EMCLookupJTableComponent lkpAccessGroup = new EMCLookupJTableComponent(new BaseEmployeeAccessGroupMenu());
        lkpAccessGroup.setPopup(new EMCLookupPopup(new BaseEmployeeAccessGroup(), "accessGroup", dataRelation.getUserData()));
        toptable.setLookupToColumn("accessGroup", lkpAccessGroup);
    }
}
