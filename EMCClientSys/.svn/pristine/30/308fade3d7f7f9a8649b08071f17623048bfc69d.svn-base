/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.absenteeismtype;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRAbsenteeismType;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRAbsenteeismTypeForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public HRAbsenteeismTypeForm(EMCUserData userData) {
        super("Absenteeism Type", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new HRAbsenteeismType(), userData), userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("absenteeismType");
        dataRelation.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contenPane = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Absenteeism Type", tablePane());

        contenPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contenPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("absenteeismType");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }
}
