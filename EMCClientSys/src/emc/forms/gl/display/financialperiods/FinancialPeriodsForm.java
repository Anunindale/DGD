/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.financialperiods;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.gl.FinancialPeriodStatus;
import emc.enums.gl.FinancialPeriodTypes;
import emc.enums.gl.financialperiods.GLFinancialPeriodGenerationTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.gl.display.financialperiods.resources.FinancialPeriodGenerationDialog;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class FinancialPeriodsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of FinancialPeriodsForm */
    public FinancialPeriodsForm(EMCUserData userData) {
        super("Financial Periods", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 300);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLFinancialPeriods(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("periodId");
        drm.setFormTextId2("periodType");

        initForm();
    }

    /** Creates the components on the form. */
    private void initForm() {
        this.setLayout(new BorderLayout());

        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.addTab("Overview", createOverviewTab());

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the overview tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List keys = new ArrayList();
        keys.add("periodId");
        keys.add("periodType");
        keys.add("startDate");
        keys.add("endDate");
        keys.add("numberOfWeeks");
        keys.add("periodName");
        keys.add("periodQuarter");
        keys.add("accountStatus");

        emcTableModelUpdate m = new emcTableModelUpdate(this.drm, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 7 && FinancialPeriodTypes.OPENING.equals(FinancialPeriodTypes.fromString((String) drm.getFieldValueAt(row, "periodType")))) {
                    //Do not allow change of status on opening periods
                    return false;
                } else {
                    return super.isCellEditable(row, column);
                }
            }
        };
        setupLookup(toptable);
        drm.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        drm.setTablePanel(topscroll);

        return pnlOverview;
    }

    private void setupLookup(emcJTableUpdate toptable) {
        emcJComboBox cbStatus = new emcJComboBox(FinancialPeriodStatus.values());
        emcJComboBox cbType = new emcJComboBox(FinancialPeriodTypes.values());
        toptable.setComboBoxLookupToColumn("accountStatus", cbStatus);
        toptable.setComboBoxLookupToColumn("periodType", cbType);
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        emcMenuButtonList btnGenerate = new emcMenuButtonList("Generate", this) {

            @Override
            public void executeCmd(String theCmd) {
                GLFinancialPeriodGenerationTypes generationType = null;
                if ("Monthly Periods".equals(theCmd)) {
                    generationType = GLFinancialPeriodGenerationTypes.MONTHLY;
                }

                if (generationType != null) {
                    new FinancialPeriodGenerationDialog(drm, generationType);
                }
            }
        };
        btnGenerate.addMenuItem("Monthly Periods", null, -1, false);

        buttons.add(btnGenerate);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
