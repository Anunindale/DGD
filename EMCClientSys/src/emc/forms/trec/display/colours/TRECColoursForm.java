/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.colours;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECColours;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class TRECColoursForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TRECColoursForm(EMCUserData userData) {
        super("Colours", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Trec/TRECColours.html"));
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECColours(), userData), userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("colourId");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Colours", tablePane());
        this.setContentPane(tabbedPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("colourId");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
