/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.jobtitles;

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
import emc.entity.hr.HREducationLevel;
import emc.entity.hr.HRJobCatagory;
import emc.entity.hr.HRJobLevel;
import emc.entity.hr.HROFOCodes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.hr.menuitems.display.HREducationLevelMI;
import emc.menus.hr.menuitems.display.HRJobCatagoryMenu;
import emc.menus.hr.menuitems.display.HRJobLevelMenu;
import emc.menus.hr.menuitems.display.HROFOCodeMenu;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class JobTitlesForm extends BaseInternalFrame {

    private emcJPanel pnlJobTitles = new emcJPanel();
    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;

    public JobTitlesForm(EMCUserData userData) {
        super("JobTitles", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowJobTitles.html"));
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowJobTitles(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("jobTitle");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabJobTitles() {
        List keys = new ArrayList();
        keys.add("jobTitle");
        keys.add("description");
        keys.add("eduLevel");
        keys.add("jobCatagory");
        keys.add("jobLevel");
        keys.add("ofoCode");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        setupLookups(toptable);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlJobTitles.setLayout(new GridLayout(1, 1));
        pnlJobTitles.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabJobTitles();
        tabbedPanetop.add("JobTitles", this.pnlJobTitles);
        this.add(tabbedPanetop);
    }

    private void setupLookups(emcJTableUpdate toptable) {
        EMCLookupJTableComponent lkpCatagory = new EMCLookupJTableComponent(new HRJobCatagoryMenu());
        lkpCatagory.setPopup(new EMCLookupPopup(new HRJobCatagory(), "catagory", userData));
        toptable.setLookupToColumn("jobCatagory", lkpCatagory);

        EMCLookupJTableComponent lkpLevel = new EMCLookupJTableComponent(new HRJobLevelMenu());
        lkpLevel.setPopup(new EMCLookupPopup(new HRJobLevel(), "jobLevel", userData));
        toptable.setLookupToColumn("jobLevel", lkpLevel);

        EMCLookupJTableComponent lkpOFOCode = new EMCLookupJTableComponent(new HROFOCodeMenu());
        lkpOFOCode.setPopup(new EMCLookupPopup(new HROFOCodes(), "ofoCode", userData));
        toptable.setLookupToColumn("ofoCode", lkpOFOCode);

        EMCLookupJTableComponent lkpEducLevel = new EMCLookupJTableComponent(new HREducationLevelMI());
        lkpEducLevel.setPopup(new EMCLookupPopup(new HREducationLevel(), "eduLevelId", userData));
        toptable.setLookupToColumn("eduLevel", lkpEducLevel);

    }
}
