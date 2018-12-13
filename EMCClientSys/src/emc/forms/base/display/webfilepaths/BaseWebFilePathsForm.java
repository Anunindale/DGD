/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.webfilepaths;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseWebFilePaths;
import emc.enums.base.webfilepaths.BaseWebFilePathProcesses;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseWebFilePathsForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public BaseWebFilePathsForm(EMCUserData userData) {
        super("Web File Paths", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseWebFilePaths(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("processId");
        dataManager.setFormTextId2("relativeFilePath");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("processId");
        keys.add("relativeFilePath");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        table.setComboBoxLookupToColumn("processId", new emcJComboBox(BaseWebFilePathProcesses.values()));
    }
}
