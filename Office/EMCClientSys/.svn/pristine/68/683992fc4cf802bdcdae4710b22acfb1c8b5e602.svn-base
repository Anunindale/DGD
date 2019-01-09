/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJCheckBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author wikus
 */
public class SearchDBForValue extends BaseInternalFrame {

    public SearchDBForValue(final EMCUserData userData) {
        super("Search in DB", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 340);
        final emcJTextField txtValue = new emcJTextField(new EMCStringDocument());
        final emcJCheckBox chkUpdate = new emcJCheckBox("");
        final emcJTextField txtNewValue = new emcJTextField();
        txtNewValue.setEnabled(false);

        chkUpdate.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                txtNewValue.setEnabled(chkUpdate.isSelected());
            }
        });

        final DefaultListModel lsModel = new DefaultListModel();
        emcJList lsValues = new emcJList(lsModel);
        emcJButton btnSearch = new emcJButton("Search") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(txtValue.getText())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please enter a value to search for.", userData);
                    return;
                }

                if (chkUpdate.isSelected() && Functions.checkBlank(txtNewValue.getText())) {
                    utilFunctions.logMessage(Level.SEVERE, "Please enter a new value.", userData);
                    return;
                }

                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.SEARCH_DB_FOR_VALUE);
                List toSend = new ArrayList();
                toSend.add(txtValue.getText());
                toSend.add(chkUpdate.isSelected());
                toSend.add(txtNewValue.getText());
                toSend = (List) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1);
                lsModel.clear();
                for (Object o : toSend) {
                    lsModel.addElement(o);
                }
            }
        };
        Component[][] comp = {{new emcJLabel("Search"), txtValue},
        {new emcJLabel("Update Field Value"), chkUpdate},
        {new emcJLabel("New Value"), txtNewValue},
            {lsValues, new emcJLabel("Results")}};
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Search", emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true));
        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSearch);
        thePane.add(emcSetGridBagConstraints.createButtonPanel(buttonList), BorderLayout.EAST);
        this.setContentPane(thePane);
    }
}
