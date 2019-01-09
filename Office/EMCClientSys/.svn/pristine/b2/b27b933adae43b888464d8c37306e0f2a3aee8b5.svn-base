/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.diciplaneryaction;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.components.hr.HRStatusDropDown;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.hr.HRActionResults;
import emc.entity.hr.HRDiciplaneryActions;
import emc.entity.hr.HRDisciplinaryLevel;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.hr.menuitems.display.HRActionResultsMenu;
import emc.menus.hr.menuitems.display.HRDisiplinaryLevelMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRDiciplaneryActionForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HRDiciplaneryActionForm(EMCUserData userData) {
        super("Disciplinary Action", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRDiciplaneryActions(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("offenceDate");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HRDiciplaneryActions.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Disciplinary Action", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("offenceDate");
        keys.add("broughtBy");
        keys.add("offence");
        keys.add("diciplaneryResult");
        keys.add("resultLevel");
        keys.add("status");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupPopup emp2Pop = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        EMCLookupJTableComponent lkp2Emp = new EMCLookupJTableComponent(new employees());
        lkp2Emp.setPopup(emp2Pop);
        table.setLookupToColumn("broughtBy", lkp2Emp);

        EMCLookupJTableComponent lkpResult = new EMCLookupJTableComponent(new HRActionResultsMenu());
        lkpResult.setPopup(new EMCLookupPopup(new HRActionResults(), "resultId", userData));
        table.setLookupToColumn("diciplaneryResult", lkpResult);

        EMCLookupJTableComponent lkpLevel = new EMCLookupJTableComponent(new HRDisiplinaryLevelMenu());
        lkpLevel.setPopup(new EMCLookupPopup(new HRDisciplinaryLevel(), "levelId", userData));
        table.setLookupToColumn("resultLevel", lkpLevel);

        table.setComboBoxLookupToColumn("status", new HRStatusDropDown());
    }
}
