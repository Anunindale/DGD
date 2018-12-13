/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.language;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.language.BaseLanguage;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseLanguageForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public BaseLanguageForm(EMCUserData userData) {
        super("Language", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseLanguage(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("languageId");
            dataManager.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Language", tablePane());
        this.setContentPane(tabbedPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("languageId");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
