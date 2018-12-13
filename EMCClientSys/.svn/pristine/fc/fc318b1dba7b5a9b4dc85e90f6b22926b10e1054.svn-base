/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.trectype;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECTrecTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class TRECTrecTypeForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TRECTrecTypeForm(EMCUserData userData) {
        super("Trec Type", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECTrecTypes(), userData), userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("trecTypeId");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Trec Type", tablePane());
        this.setContentPane(tabbedPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("trecTypeId");
        keys.add("description");
        keys.add("type");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
