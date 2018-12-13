/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.salesrepcommission.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCBigDecimalDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.enumQueryTypes;
import emc.forms.sop.display.salesrepcommission.SOPSalesRepCommissionForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.sop.menuitems.display.SOPSalesRepGroupSetupMenu;
import emc.methods.sop.ServerSOPMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class MassUpdateDialog extends emcJDialog {

    private SOPSalesRepCommissionForm form;
    private EMCLookup lkpRep;
    private emcJTextField txtCommission;

    //Maps lookups to fields.  Index 0 = field 1, index 1 = field 2, etc.
    private List<EMCLookup> lookups = new ArrayList<EMCLookup>();
    //Maps labes to fields.  Index 0 = field 1, index 1 = field 2, etc.
    private List<emcJLabel> labels = new ArrayList<emcJLabel>();

    /** Creates a new instance of MassUpdateDialog. */
    public MassUpdateDialog(SOPSalesRepCommissionForm form) {
        super(null, "Mass Update");
        this.form = form;

        this.initDialog(form.getUserData().copyUserData());
    }

    /** Initializes the dialog. */
    private void initDialog(EMCUserData userData) {
        this.add(createUpdatePanel(userData), BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);

        this.pack();
    }

    /** Creates the update panel. */
    private emcJPanel createUpdatePanel(EMCUserData userData) {
        lkpRep = new EMCLookupJTableComponent(new SOPSalesRepGroupSetupMenu());
        EMCLookupPopup repPopup = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        lkpRep.setPopup(repPopup);

        emcJLabel lblRep = new emcJLabel(this.form.getDataRelationManager().getColumnName("repId"));

        //Only select employees that have been set up as
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addTableAnd(SOPSalesRepGroups.class.getName(), "employeeNumber", BaseEmployeeTable.class.getName(), "groupManager");

        lkpRep.setTheQuery(query);

        emcJLabel lblCommission = new emcJLabel(this.form.getDataRelationManager().getColumnName("commissionPerc"));
        txtCommission = new emcJTextField(new EMCBigDecimalDocument());
        txtCommission.setText("0.0");

        for (String[] column : this.form.getTableColumns()) {
            this.lookups.add(this.form.createLookup(null,column, false));
            this.labels.add(new emcJLabel(this.form.getColumnName(column[0], column[1], userData)));
        }

        //Show all lookups and rep/commission fields.  (+2)
        Component[][] components = new Component[this.lookups.size() + 2][2];

        components[0][0] = lblRep;
        components[0][1] = lkpRep;

        for (int i = 1; i <= this.labels.size(); i++) {
            components[i][0] = this.labels.get(i - 1);
            components[i][1] = this.lookups.get(i - 1);
        }

        components[this.labels.size() + 1][0] = lblCommission;
        components[this.labels.size() + 1][1] = txtCommission;

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCUserData userData = form.getUserData();
                EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.MASS_UPDATE_COMMISSION);

                List toSend = new ArrayList();
                toSend.add(lkpRep.getValue());

                int blanks = 6 - MassUpdateDialog.this.labels.size();
                
                for (EMCLookup lookup : lookups) {
                    toSend.add(lookup.getValue() == null ? "" : lookup.getValue());
                }

                for (int i = 0; i < blanks; i++) {
                    toSend.add("");
                }

                String decString = MassUpdateDialog.this.txtCommission.getText();

                if (Functions.checkBlank(decString)) {
                    decString = "0";
                }
                
                toSend.add(new BigDecimal(decString));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    utilFunctions.logMessage(Level.INFO, "Mass update succeeded.", userData);
                    MassUpdateDialog.this.form.getDataRelationManager().refreshData();
                } else {
                    utilFunctions.logMessage(Level.SEVERE, "Mass update failed.", userData);
                }

                MassUpdateDialog.this.dispose();
            }
        });

        buttons.add(new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(form, "Cancel?", "Are you sure that you wish to cancel the mass update?") == JOptionPane.YES_OPTION) {
                    MassUpdateDialog.this.dispose();
                }
            }
        });

        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }
}
