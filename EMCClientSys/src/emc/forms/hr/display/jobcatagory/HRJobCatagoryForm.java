/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.jobcatagory;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRJobCatagory;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRJobCatagoryForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public HRJobCatagoryForm(EMCUserData userData) {
        super("Job Category", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRJobCatagory(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("catagory");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Category", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("catagory");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
