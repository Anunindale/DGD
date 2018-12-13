/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.regeneratesummary;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.gl.ServerGLMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class GLRegenerateSummaryForm extends BaseInternalFrame {

    public GLRegenerateSummaryForm(EMCUserData userData) {
        super("Regenerate Summary Tables", true, true, true, true, userData);
        this.setBounds(20, 20, 300, 120);
        initFrame();
    }

    private void initFrame() {
        this.add(createMainPanel(), BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(this, "Regenerate", "Are you sure that you want to regenerate all of the GL summary tables?") == JOptionPane.YES_OPTION) {
                    EMCUserData userData = getUserData();

                    EMCCommandClass cmd = new EMCCommandClass(ServerGLMethods.REGENERATE_GL_SUMMARY_TABLES);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                    if (toSend != null && toSend.size() > 1 && toSend.get(1) == Boolean.TRUE) {
                        utilFunctions.logMessage(Level.INFO, "Successfully regenerated summary tables.", userData);
                        GLRegenerateSummaryForm.this.dispose();
                    } else {
                        utilFunctions.logMessage(Level.SEVERE, "Failed to regenerate summary tables.", userData);
                    }
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (EMCDialogFactory.createQuestionDialog(this, "Cancel", "Are you sure that you want to cancel?") == JOptionPane.YES_OPTION) {
                    GLRegenerateSummaryForm.this.dispose();
                }
            }
        };

        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(btnOK);
        buttons.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }

    /** Creates the main panel. */
    private emcJPanel createMainPanel() {
        emcJPanel panel = new emcJPanel();
        return panel;
    }
}
