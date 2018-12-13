package emc.forms.base.display.index;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseIndex;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
*
* @author claudette
*/
public class BaseIndexForm extends BaseInternalFrame{

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of BaseIndexForm. */
    public BaseIndexForm(EMCUserData userData) {
        super("Index", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseIndex(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("className");
        drm.setFormTextId2("indexOne");
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
        keys.add("className");
        keys.add("indexOne");
        keys.add("indexTwo");
        keys.add("indexThree");
        keys.add("indexFour");
        keys.add("indexFive");
        keys.add("indexSix");
        keys.add("indexSeven");
        keys.add("indexEight");
        keys.add("indexNine");
        keys.add("indexTen");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

}