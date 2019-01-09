/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.trec.display.phrasecomb;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECPhraseCombinations;
import emc.enums.modules.enumEMCModules;
import emc.forms.trec.display.chemicals.resources.TRECEncDRM;
import emc.framework.EMCUserData;
import emc.methods.trec.ServerTRECMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class TrecPhraseCombForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TrecPhraseCombForm(EMCUserData userData) {
        super("Phrase Combinations", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new TRECEncDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECPhraseCombinations(), userData),ServerTRECMethods.UPDATE_ENC_PHRASECOMBINATIONS, userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("phraseRefNum");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Forms", tablePane());
        this.setContentPane(tabbedPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("phraseRefNum");
        keys.add("description");
        keys.add("appearance");
        keys.add("classId");
        keys.add("trecType");
        keys.add("h");
        keys.add("p");
        keys.add("q");
        keys.add("d");
        keys.add("s");
        keys.add("f");
        keys.add("a");
        keys.add("e");
        keys.add("approved");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}

