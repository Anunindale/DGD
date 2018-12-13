/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

/**
 * @description : This form is used to call the maintenance routine which cleans up the BaseDBLogTable.
 *
 * @date        : 28 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ClearDBLogForm extends BaseInternalFrame {

    public ClearDBLogForm(EMCUserData userData) {
        super("Clean Up DB Log", true, true, true, true, userData);
        this.setBounds(20, 20, 200, 150);
        initFrame(userData);
    }

    private void initFrame(final EMCUserData userData) {
        final EMCDatePickerFormComponent datePicker = new EMCDatePickerFormComponent();
        emcJButton theButton = new emcJButton("Clean Up") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (!Functions.checkBlank(datePicker.getValue())) {
                    Date toDate = datePicker.getDate();
                    if (EMCDialogFactory.createQuestionDialog(ClearDBLogForm.this, "Clear DB Log?", "Are you sure that you want to delete all DB log records up to " + Functions.date2String(toDate) + "?") == JOptionPane.YES_OPTION) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CLEAR_DB_LOG);

                        List toSend = new ArrayList();
                        toSend.add(toDate);

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "DB log cleared.");
                        } else {
                            Logger.getLogger("emc").log(Level.INFO, "Failed to clear DB log.");
                        }
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the date.");
                }
            }
        };
        emcJPanel thePanel = new emcJPanel(new GridBagLayout());
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.CENTER);

        thePanel.add(new emcJLabel("Date To"), gbc);
        gbc.gridy = 1;

        thePanel.add(datePicker, gbc);
        gbc.gridy = 2;

        thePanel.add(new emcJLabel(), gbc);
        gbc.gridy = 3;

        thePanel.add(theButton, gbc);

        thePanel.setBorder(new TitledBorder("Clean Up"));


        this.add(thePanel);
    }
}
