/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.marketinggroup;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsMarketingGroup;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : This form is used to capture/edit marketing groups.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class MarketingGroupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of MarketingGroupForm */
    public MarketingGroupForm(EMCUserData userData) {
        super("Marketing Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsMarketingGroup(), userData), userData);

        drm.setTheForm(this);
        this.setDataManager(drm);

        drm.setFormTextId1("marketingGroup");
        drm.setFormTextId2("description");
        
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("marketingGroup");
        keys.add("description");
        
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        panel.add(tableScroll);
        
        return panel;
    }
}
