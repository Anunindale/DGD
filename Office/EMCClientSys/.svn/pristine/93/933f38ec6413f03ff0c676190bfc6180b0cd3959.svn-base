/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJProgressBar;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

/**
 *
 * @author wikus
 */
public class BaseFixEmpId extends BaseInternalFrame {

    private EMCUserData userData;
    private emcJProgressBar progressBar;
    private emcJButton theButton;

    public BaseFixEmpId(EMCUserData userData) {
        super("Fix Emp Id", true, true, true, true, userData);
        this.userData = userData;
        this.setBounds(20, 20, 200, 170);
        initFrame(userData);
    }

    private void initFrame(final EMCUserData userData) {
        progressBar = new emcJProgressBar();
        theButton = new emcJButton("FIX") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                SwingWorker thread = new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        doThread();
                        return null;
                    }
                };
                thread.execute();
            }
        };
        emcJPanel thePanel = new emcJPanel(new GridBagLayout());
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.CENTER);
        thePanel.add(new emcJLabel("FIX", false), gbc);
        gbc.gridy = 1;
        thePanel.add(new emcJLabel(), gbc);
        gbc.gridy = 2;
        thePanel.add(progressBar, gbc);
        gbc.gridy = 3;
        thePanel.add(new emcJLabel(), gbc);
        gbc.gridy = 4;
        thePanel.add(theButton, gbc);
        thePanel.setBorder(new TitledBorder("FIX"));
        this.add(thePanel);
    }

    private void doThread() {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.FIX_EMP_ID.toString());
        List toSend = new ArrayList();
        progressBar.setIndeterminate(true);
        theButton.setEnabled(false);
        EMCWSManager.executeGenericWS(cmd, toSend, userData);
        theButton.setEnabled(true);
        progressBar.setIndeterminate(false);
    }
}
