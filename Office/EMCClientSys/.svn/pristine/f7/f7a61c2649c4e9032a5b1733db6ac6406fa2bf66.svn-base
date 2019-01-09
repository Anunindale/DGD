/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.correspondencelog;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMCorrespondenceLog;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CRMCorrespondenceLogForm extends BaseInternalFrame {

    private emcDRMViewOnly dataManager;

    public CRMCorrespondenceLogForm(EMCUserData userData) {
        super("Corresponence Log", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMCorrespondenceLog(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("eventDate");
        dataManager.setFormTextId2("eventDescription");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePanbe());
        this.setContentPane(tabbed);
    }

    private emcTablePanelUpdate tablePanbe() {
        List<String> keys = new ArrayList<String>();
        keys.add("referenceNumber");
        keys.add("eventDate");
        keys.add("eventType");
        keys.add("eventDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
