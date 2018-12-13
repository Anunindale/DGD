package emc.forms.gl.display.transactionperiodsummary;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.columnmanager.EMCColumnManagerButton;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;
import emc.forms.gl.display.transactionperiodsummary.resources.GLTransactionPeriodSummaryDRM;
import emc.forms.gl.display.transactionperiodsummary.resources.GLTransactionViewButton;
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
public class GLTransactionPeriodSummaryForm extends BaseInternalFrame {

    private GLTransactionPeriodSummaryDRM drm;
    
    /** Creates a new instance of GLTransactionPeriodSummaryForm. */
    public GLTransactionPeriodSummaryForm(EMCUserData userData) {
        super("Transaction Period Summary", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 290);
        drm = new GLTransactionPeriodSummaryDRM(new emcGenericDataSourceUpdate(new GLTransactionPeriodSummary(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("accountNumber");
        drm.setFormTextId2("financialPeriod");
        this.initFrame();
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }


    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("accountNumber");
        keys.add("financialPeriod");
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

    /**
     * Creates the buttons panel.
     * @return The buttons panel.
     */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new GLTransactionViewButton(this));
        
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}