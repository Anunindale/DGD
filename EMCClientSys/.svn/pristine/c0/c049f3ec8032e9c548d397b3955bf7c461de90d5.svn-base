package emc.forms.gl.display.grouprules;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.rules.GLGroupRules;
import emc.enums.gl.GroupGranularityTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author claudette
 */
public class GLGroupRulesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of GLGroupRulesForm. */
    public GLGroupRulesForm(EMCUserData userData) {
        super("Group Rules", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLGroupRules(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("groupRules");
        drm.setFormTextId2("description");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        this.add(tabs, BorderLayout.CENTER);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("groupRules");
        keys.add("description");
        keys.add("analysisCode1");
        keys.add("analysisCode2");
        keys.add("analysisCode3");
        keys.add("analysisCode4");
        keys.add("analysisCode5");
        keys.add("analysisCode6");
        keys.add("granularity");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        table.setComboBoxLookupToColumn("granularity", new emcJComboBox(GroupGranularityTypes.values()));

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
}