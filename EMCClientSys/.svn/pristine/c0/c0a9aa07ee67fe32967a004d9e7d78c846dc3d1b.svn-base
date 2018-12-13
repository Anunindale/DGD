/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.course;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRCourses;
import emc.entity.hr.HREducationPriority;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.hr.menuitems.display.HREducationPriorityMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRCourseForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;

    public HRCourseForm(EMCUserData userData) {
        super("Course", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRCourses(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("course");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Course", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("course");
        keys.add("description");
        keys.add("duration");
        keys.add("courseLevel");
        keys.add("nqfLevel");
        keys.add("priority");
        keys.add("saqaRegistered");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookup
        EMCLookupJTableComponent lkpPriority = new EMCLookupJTableComponent(new HREducationPriorityMenu());
        lkpPriority.setPopup(new EMCLookupPopup(new HREducationPriority(), "priority", userData));
        table.setLookupToColumn("priority", lkpPriority);
        dataRelation.setMainTableComponent(table);
        //Lookup
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }
}
