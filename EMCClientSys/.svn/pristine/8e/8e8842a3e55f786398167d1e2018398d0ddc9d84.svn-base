/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.postdatedpayment;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.DebtorsPostDatedPayment;
import emc.entity.sop.SOPCustomers;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.display.postdatedpayment.resources.ProcessButton;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description This form is used to set up the Cloud Project activity palette.
 *
 * @version     1.0 6 April 2010
 *
 * @author      Riaan Nel
 */
public class PostDatedPaymentForm extends BaseInternalFrame {

    private EMCLookupJTableComponent lkpCustomer;

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of PostDatedPaymentForm. */
    public PostDatedPaymentForm(EMCUserData userData) {
        super("Post Dated Payments", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 300);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsPostDatedPayment(), userData), userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("internalRef");
        drm.setFormTextId2("customer");

        setupLookups(userData);
        setupFrame();
    }

    /** Sets up lookups. */
    private void setupLookups(EMCUserData userData) {
        this.lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        this.lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));
    }
    
    /** Sets up the frame. */
    private void setupFrame() {
        emcJTabbedPane tabs = createTabs();
        tabs.setRelationManager(drm);
        emcJPanel pnlMain = new emcJPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(tabs, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the tabbed pane containing the components on the form. */
    private emcJTabbedPane createTabs() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Post Dated Payments", createSummaryTab());
        tabs.add("Text", createTextTab());

        return tabs;
    }

    /** Creates the summary tab. */
    private emcJPanel createSummaryTab() {
        emcJPanel pnlOverview = new emcJPanel();

        List<String> keys = new ArrayList<String>();
        keys.add("internalRef");
        keys.add("customer");
        //keys.add("paymentType");
        keys.add("paymentNumber");
        keys.add("paymentDate");
        keys.add("paymentAmount");
        keys.add("presentedDate");
        keys.add("createdDate");
        keys.add("createdBy");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate tblMaster = new emcJTableUpdate(m);
        
        tblMaster.setColumnEditable("createdDate", false);
        tblMaster.setColumnEditable("createdBy", false);

        tblMaster.setLookupToColumn("customer", lkpCustomer);
        
        drm.setMainTableComponent(tblMaster);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(tblMaster);
        pnlOverview.setLayout(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        drm.setTablePanel(topscroll);

        return pnlOverview;
    }

    /** Creates text tab. */
    private emcJPanel createTextTab() {
        emcJLabel lblText = new emcJLabel(drm.getColumnName("text"));
        emcJTextArea txaText = new emcJTextArea(new EMCStringFormDocument(drm, "text"));

        Component[][] components = new Component[][] {
            {txaText, lblText}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new ProcessButton(drm));
        
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
