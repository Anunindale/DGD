/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.action;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.enumQueryTypes;
import emc.enums.gl.FinancialPeriodTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class GLYearEndForm extends BaseInternalFrame {

    private EMCUserData userData;
    private EMCLookup lkpOpeningPeriod;

    public GLYearEndForm(EMCUserData userData) {
        super("GL Year-End", true, true, true, true, userData);
        this.setBounds(20, 20, 450, 200);
        this.userData = userData;
        initComponents();
        initFrame();
    }

    /**
     * Initializes components.
     */
    private void initComponents() {
        this.lkpOpeningPeriod = new EMCLookup(new emc.menus.gl.menuitems.display.GLFinancialPeriods());
        this.lkpOpeningPeriod.setPopup(new EMCLookupPopup(new GLFinancialPeriods(), "periodId", userData));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodType", FinancialPeriodTypes.OPENING);

        this.lkpOpeningPeriod.setTheQuery(query);
    }
    
    /**
     * Initializes the frame.
     */
    private void initFrame() {
        this.setLayout(new BorderLayout());

        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Year-End", createYearEndPanel());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /**
     * Creates the year-end panel.
     * @return Year-end panel.
     */
    private emcJPanel createYearEndPanel() {
        Component[][] comp = {
            {new emcJLabel("Next Opening Period"), lkpOpeningPeriod}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    /**
     * Creates the buttons panel.
     * @return Buttons panel.
     */
    private emcJPanel createButtonsPanel() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (validateYearEnd() && EMCDialogFactory.createQuestionDialog(null, "Process Year-End?", "Are you sure that you want to process the financial year-end?") == JOptionPane.YES_NO_OPTION) {
                    EMCUserData userData = getUserData();

                    EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.PROCESS_YEAR_END);
                    List toSend = new ArrayList();
                    toSend.add(lkpOpeningPeriod.getValue());

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                        utilFunctions.logMessage(Level.INFO, "Year-end processed successfully.", userData);
                    } else {
                        utilFunctions.logMessage(Level.SEVERE, "Failed to process year-end.", userData);
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                GLYearEndForm.this.dispose();
            }
        };
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(btnOK);
        buttons.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }

    /**
     * Checks that the required values have been specified.
     * @return A boolean indicating whether all required information has been
     * specified.
     */
    private boolean validateYearEnd() {
        if (Functions.checkBlank(lkpOpeningPeriod.getValue())) {
            utilFunctions.logMessage(Level.SEVERE, "Please specify the next opening period.", userData);
            return false;
        }

        return true;
    }
}
