package emc.forms.gl.display.transactiondaysummary;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.transactions.GLTransactionDaySummary;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author claudette
 */
public class GLTransactionDaySummaryForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of GLTransactionDaySummaryForm. */
    public GLTransactionDaySummaryForm(EMCUserData userData) {
        super("Transaction Day Summary", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 290);
        drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new GLTransactionDaySummary(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("accountNumber");
        drm.setFormTextId2("financialDate");
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
        keys.add("accountNumber");
        keys.add("financialDate");
        keys.add("analysisCode1");
        keys.add("analysisCode2");
        keys.add("analysisCode3");
        keys.add("analysisCode4");
        keys.add("analysisCode5");
        keys.add("analysisCode6");
        keys.add("debit");
        keys.add("credit");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setColumnCellEditor("accountNumber", new EMCGoToMainTableEditor(new EMCStringDocument(), new GLChartOfAccountsMenu()));
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
}