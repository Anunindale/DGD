package emc.forms.gl.display.analysiscodehierarchy;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.analysiscodes.GLAnalysisCodeHierarchy;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLAnalysisCodeHierarchySetupMI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/** 
 *
 * @author claudette
 */
public class GLAnalysisCodeHierarchyForm extends BaseInternalFrame {

    private GLAnalysisCodeHierarchyDRM drm;
    private emcJPanel pnlHierarchies = new emcJPanel();

    /** Creates a new instance of GLAnalysisCodeHierarchyForm. */
    public GLAnalysisCodeHierarchyForm(EMCUserData userData) {
        super("Analysis Code Hierarchy", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        this.setLayout(new BorderLayout());
        try {
            drm = new GLAnalysisCodeHierarchyDRM(new emcGenericDataSourceUpdate(enumEMCModules.GENERAL_LEDGER.getId(), new GLAnalysisCodeHierarchy(), userData), userData);
            drm.setTheForm(this);
            this.setDataManager(drm);
            drm.setFormTextId1("hierarchyId");
            drm.setFormTextId2("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.initFrame();
    }

    private void tabHierarchies() {
        List keys = new ArrayList();
        keys.add("hierarchyId");
        keys.add("description");

        emcTableModelUpdate m = new emcTableModelUpdate(this.drm, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        drm.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlHierarchies.setLayout(new GridLayout(1, 1));
        pnlHierarchies.add(topscroll);
        //TODO: CLAUDETTE if the scroll funtion is funny, start here
        drm.setTablePanel(topscroll);
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabHierarchies();
        tabs.add("Hierarchies", this.pnlHierarchies);
        this.add(tabs, BorderLayout.CENTER);
        this.add(initButtons(), BorderLayout.EAST);
    }

    private emcJPanel initButtons() {
        emcJPanel pnlButtons = new emcJPanel();
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;

        EMCMenuItem hierachiesItem = new GLAnalysisCodeHierarchySetupMI();
        hierachiesItem.setDoNotOpenForm(false);

        emcMenuButton button = new emcMenuButton("Setup", hierachiesItem, this, 0, false);
        pnlButtons.add(button, localg);
        y++;
        pnlButtons.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(110, 25));

        return pnlButtons;
    }
}