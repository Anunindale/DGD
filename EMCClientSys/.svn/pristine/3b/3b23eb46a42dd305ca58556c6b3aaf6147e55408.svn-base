package emc.forms.gl.display.parameters;

import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.gl.GLParameters;
import emc.framework.EMCUserData;
import emc.menus.gl.menuitems.display.GLChartOfAccountsMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;

/** 
 *
 * @author claudette
 */
public class GLParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of GLParametersForm. */
    public GLParametersForm(EMCUserData userData) {
        super("Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new GLParameters(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("budgetFreezePeriods");
        drm.setFormTextId2("companyId");
        setupFrame(userData);
    }

    /** Sets up the frame. */
    private void setupFrame(EMCUserData userData) {
        emcJTabbedPane tabs = createTabs(userData);
        tabs.setRelationManager(drm);
        emcJPanel pnlMain = new emcJPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(tabs, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        this.add(pnlMain, BorderLayout.CENTER);
    }

    /** Creates the tabbed pane containing the components on the form. */
    private emcJTabbedPane createTabs(EMCUserData userData) {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Parameters", createParametersTab(userData));
        return tabs;
    }

    /** Creates the parameters tab. */
    private emcJPanel createParametersTab(EMCUserData userData) {
        emcJTextField txtBudgetFreeze = new emcJTextField(new EMCIntegerFormDocument(drm, "budgetFreezePeriods"));

        EMCLookupFormComponent lkpVATInputAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), drm, "vatInputAccount");
        lkpVATInputAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", userData));

        EMCLookupFormComponent lkpVATOutputAccount = new EMCLookupFormComponent(new GLChartOfAccountsMenu(), drm, "vatOutputAccount");
        lkpVATOutputAccount.setPopup(new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", userData));

        Component[][] components = new Component[][]{
            {new emcJLabel(drm.getColumnName("budgetFreezePeriods")), txtBudgetFreeze},
            {new emcJLabel(drm.getColumnName("vatInputAccount")), lkpVATInputAccount},
            {new emcJLabel(drm.getColumnName("vatOutputAccount")), lkpVATOutputAccount},
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }
}