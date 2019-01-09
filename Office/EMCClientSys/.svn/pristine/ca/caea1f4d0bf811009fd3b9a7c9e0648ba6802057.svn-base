/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.education;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREducationTypesDropDown;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HREmployeeEducation;
import emc.entity.hr.HRInstitution;
import emc.entity.hr.HRQualification;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.hr.menuitems.display.HRInstitutionMenu;
import emc.menus.hr.menuitems.display.HRQualificationMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HREmployeeEducationForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HREmployeeEducationForm(EMCUserData userData) {
        super("Employee Education", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HREmployeeEducation(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("institution");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HREmployeeEducation.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Education", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("educationType");
        keys.add("institution");
        keys.add("dateCompleted");
        keys.add("qualification");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("educationType", new HREducationTypesDropDown());

        EMCLookupPopup institutionPop = new EMCLookupPopup(new HRInstitution(), "institution", userData);
        EMCLookupJTableComponent lkpInstitution = new EMCLookupJTableComponent(new HRInstitutionMenu());
        lkpInstitution.setPopup(institutionPop);
        table.setLookupToColumn("institution", lkpInstitution);

        EMCLookupJTableComponent lkpQualification = new EMCLookupJTableComponent(new HRQualificationMenu());
        lkpQualification.setPopup(new EMCLookupPopup(new HRQualification(), "qualification", userData));
        table.setLookupToColumn("qualification", lkpQualification);
    }
}
