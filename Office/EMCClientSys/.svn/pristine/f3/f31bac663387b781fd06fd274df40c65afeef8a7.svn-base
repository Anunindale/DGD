/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.erg;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECColours;
import emc.entity.trec.TRECErgMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stu
 */
public class TRECErgForm extends BaseInternalFrame {
    
    private emcDataRelationManagerUpdate dataManager;
    
    public TRECErgForm(EMCUserData userData){
        super("Colours", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        //this.setHelpFile(new emcHelpFile("Trec/TRECColours.html"));
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECErgMaster(), userData), userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("erg");
        dataManager.setFormTextId2("description");
        initFrame();
    }
    
    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("ERG Numbers", tablePane());
        this.setContentPane(tabbedPane);
    }
    
    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("erg");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
    
}
