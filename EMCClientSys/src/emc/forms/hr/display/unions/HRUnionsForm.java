/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.unions;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRUnions;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRUnionsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public HRUnionsForm(EMCUserData userData) {
        super("Unions", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRUnions(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("unionId");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Unions", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("unionId");
        keys.add("description");
        keys.add("contactNum");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
