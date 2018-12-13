/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.scarcepriority;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRScarcePriority;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRScarcePriorityForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public HRScarcePriorityForm(EMCUserData userData) {
        super("Scarce Priority", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new HRScarcePriority(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("scarcePriority");
        dataManager.setFormTextId2("description");

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
        keys.add("scarcePriority");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}
