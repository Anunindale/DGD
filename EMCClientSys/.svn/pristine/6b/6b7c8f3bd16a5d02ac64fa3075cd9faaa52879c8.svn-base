package emc.forms.gl.display.budgetperiod;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.entity.gl.GLBudgetPeriod;
import emc.entity.gl.GLFinancialPeriods;
import emc.forms.gl.display.budgetperiod.resources.GLSplitAmountsDialog;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @author claudette
 */
public class GLBudgetPeriodForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;
    emcJTextField txtAccount = new emcJTextField();
    emcJTextField txtDescription = new emcJTextField();
    private long lineRecordId;

    /** Creates a new instance of GLBudgetPeriodForm. */
    public GLBudgetPeriodForm(EMCUserData userData) {
        super("Budget Period", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new GLBudgetPeriod(), userData), userData);
        drm.setTheForm(this);
        this.setLayout(new BorderLayout());
        this.setDataManager(drm);
        drm.setFormTextId1("budgetPeriod");
        drm.setFormTextId2("amount");
        lineRecordId = (Long) userData.getUserData(4);
        initTextFields(userData);
        this.initFrame();
    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);
        lineRecordId = (Long) userData.getUserData(4);
        initTextFields(userData);
    }

    /** Initializes the frame. */
    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        emcJPanel panel = new emcJPanel(new BorderLayout());
        panel.add(tabs, BorderLayout.CENTER);
        panel.add(createTextFields(), BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the Overview Tab. */
    private emcJPanel createOverviewTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("budgetPeriod");
        keys.add("amount");
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpFromPeriod = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
        lkpFromPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", drm.getUserData()));
        table.setLookupToColumn("budgetPeriod", lkpFromPeriod);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }

    private emcJPanel createTextFields() {
        txtAccount.setEditable(false);
        txtDescription.setEditable(false);

        Component[][] comp = {{new emcJLabel("Account"), txtAccount, new emcJLabel("Description"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Account");
    }

    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        emcJButton btnGenerate = new emcJButton("Generate") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new GLSplitAmountsDialog(lineRecordId, utilFunctions.findEMCDesktop(this), drm);
                drm.refreshData();
            }
        };
        buttons.add(btnGenerate);

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    private void initTextFields(EMCUserData userData) {
        txtAccount.setText((String) userData.getUserData(2));
        txtDescription.setText((String) userData.getUserData(3));
    }
}