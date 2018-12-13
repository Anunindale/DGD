/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.financialperiods.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.gl.financialperiods.GLFinancialPeriodGenerationTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
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
 * @author riaan
 */
public class FinancialPeriodGenerationDialog extends emcJDialog {

    private emcDataRelationManagerUpdate financialPeriodsDRM;
    private EMCDatePicker dpkFrom = new EMCDatePicker();
    private EMCDatePicker dpkTo = new EMCDatePicker();
    private emcJTextField txtGenerationType = new emcJTextField();
    private GLFinancialPeriodGenerationTypes generationType;

    /** Creates a new instance of FinancialPeriodGenerationDialog. */
    public FinancialPeriodGenerationDialog(emcDataRelationManagerUpdate financialPeriodsDRM, GLFinancialPeriodGenerationTypes generationType) {
        super(null, "Generate Financial Periods");
        this.financialPeriodsDRM = financialPeriodsDRM;
        this.generationType = generationType;

        initDialog();
        this.setVisible(true);
    }

    /** Initializes the dialog. */
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(420, 130);

        this.txtGenerationType.setText(generationType.toString());
        this.txtGenerationType.setEditable(false);

        Component[][] components = new Component[][]{
            {new emcJLabel("Generation Type: "), txtGenerationType},
            {new emcJLabel("From"), dpkFrom},
            {new emcJLabel("To"), dpkTo}
        };

        this.add(emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true), BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(FinancialPeriodGenerationDialog.this, "Generate Financial Periods?", "Generate Financial Periods?") == JOptionPane.YES_OPTION && validateGeneration()) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.GENERATE_FINANCIAL_PERIODS);
                    EMCUserData userData = financialPeriodsDRM.getUserData();

                    List toSend = new ArrayList();
                    toSend.add(generationType.toString());
                    toSend.add(dpkFrom.getDate());
                    toSend.add(dpkTo.getDate());

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                        utilFunctions.logMessage(Level.INFO, "Successfully generated financial periods.", userData);
                        financialPeriodsDRM.refreshDataIgnoreFocus();
                        dispose();
                    } else {
                        utilFunctions.logMessage(Level.INFO, "Failed to generate financial periods.", userData);
                    }
                }
            }
        });

        buttons.add(new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                dispose();
            }
        });
        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }

    /**
     * Validates the values specified on this dialog.
     * @return A boolean indicating whether the specified values are valid.
     */
    private boolean validateGeneration() {
        return true;
    }
}
