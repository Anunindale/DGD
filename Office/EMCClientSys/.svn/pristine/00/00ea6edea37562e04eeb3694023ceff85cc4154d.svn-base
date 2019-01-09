package emc.forms.base.display.countries;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class countriesForm extends BaseInternalFrame {

    private emcJPanel pnlCountries = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public countriesForm(EMCUserData userData) {
        super("Countries", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupCountries.html"));
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseCountries(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("Code");
            dataRelation.setFormTextId2("Name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabCountries();
        tabbedPanetop.add("Countries", this.pnlCountries);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(tabbedPanetop, BorderLayout.CENTER);
    }

    private void tabCountries() {
        List keys = new ArrayList();
        keys.add("Code");
        keys.add("Name");
        keys.add("numberCode");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlCountries.setLayout(new GridLayout(1, 1));
        pnlCountries.add(topscroll);
        this.setTablePanel(topscroll);
    }
}
