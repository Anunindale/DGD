/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditstopreason;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsCreditStopReason;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DebtorsCreditStopReasonForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public DebtorsCreditStopReasonForm(EMCUserData userData) {
        super("Credit Stop Reason", true, true, true, true, userData);
        this.setBounds(20, 20, 570, 290);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsCreditStopReason(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("reason");
            dataManager.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Credit Stop Reason", tablePane());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("reason");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
