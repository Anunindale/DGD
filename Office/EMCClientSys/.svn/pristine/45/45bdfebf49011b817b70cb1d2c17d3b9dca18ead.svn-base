/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.statusenquiry.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPCustomers;
import emc.forms.sop.display.statusenquiry.SOPStatusEnquiryForm;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.menus.debtors.menuitems.display.DebtorsMarketingGroup;
import emc.menus.gl.menuitems.display.GLFinancialPeriods;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class SOPStatusEnquirySelectionDialog extends emcJDialog {

    private EMCLookup period;
    private EMCLookup customerGroup;
    private EMCLookup customerId;
    private SOPStatusEnquiryForm theForm;

    public SOPStatusEnquirySelectionDialog(SOPStatusEnquiryForm theForm) {
        super(null, "Data Selection", true);
        this.theForm = theForm;
        initFrame();
        this.pack();
        this.setVisible(true);
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        period = new EMCLookup(new GLFinancialPeriods());
        period.setPopup(new EMCLookupPopup(new emc.entity.gl.GLFinancialPeriods(), "periodId", theForm.getUserData()));
        customerGroup = new EMCLookup(new DebtorsMarketingGroup());
        customerGroup.setPopup(new EMCLookupPopup(new emc.entity.debtors.DebtorsMarketingGroup(), "marketingGroup", theForm.getUserData()));
        customerId = new EMCLookup(new SOPCustomersMenu());
        customerId.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", theForm.getUserData()));

        Component[][] comp = {{new emcJLabel("Period"), period},
            {new emcJLabel("Marketing Group"), customerGroup},
            {new emcJLabel("Customer"), customerId}};
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Population", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));
        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(period.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the period.", theForm.getUserData());
                    return;
                }
                EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.POPULATE_SALES_STATUS_ENQUIRY);
                List toSend = new ArrayList();
                toSend.add(period.getValue());
                toSend.add(Functions.checkBlank(customerGroup.getValue()) ? " " : customerGroup.getValue());
                toSend.add(Functions.checkBlank(customerId.getValue()) ? " " : customerId.getValue());
                Object returned = EMCWSManager.executeGenericWS(cmd, toSend, theForm.getUserData()).get(1);
                if (returned instanceof Map) {
                    theForm.setTableMap((Map<String, List<String>>) returned);
                    SOPStatusEnquirySelectionDialog.this.dispose();
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                theForm.setTableMap(null);
                SOPStatusEnquirySelectionDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
