/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.race;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRRace;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRRaceForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public HRRaceForm(EMCUserData userData) {
        super("Race", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRRace(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("race");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Race", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("race");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
