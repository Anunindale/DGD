/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactions.resources;

import emc.app.components.documents.EMCDoubleDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.BorderFactory;

/**
 *
 * @author riaan
 */
public class TotalCreditHeldForm extends BaseInternalFrame {

    private emcJPanel pnlTotalHeld;
    private emcJTextField txtTotalHeld = new emcJTextField(new EMCDoubleDocument());

    /** Creates a new instance of TotalCreditHeldForm. */
    public TotalCreditHeldForm(EMCUserData userData) {
        super("Total Credit Held", true, true, true, true, userData);
        this.setBounds(20, 20, 400, 100);
        this.setLayout(new BorderLayout());

        txtTotalHeld.setEditable(false);

        Component[][] components = new Component[][]{
            {txtTotalHeld}
        };

        pnlTotalHeld = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);

        this.add(pnlTotalHeld, BorderLayout.CENTER);

        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                TotalCreditHeldForm.this.dispose();
            }
        });

        this.add(emcSetGridBagConstraints.createButtonPanel(buttons, false), BorderLayout.EAST);

        this.refreshForm(userData);
    }

    /** Updates value displayed on this form. */
    private void refreshForm(EMCUserData userData) {
        String customerId = (String) userData.getUserData(3);
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.GET_TOTAL_HELD_FOR_CUSTOMER);

        List toSend = new ArrayList();
        toSend.add(customerId);

        BigDecimal totalHeld = BigDecimal.ZERO;

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof BigDecimal) {
            totalHeld = (BigDecimal) toSend.get(1);
        } else {
            utilFunctions.logMessage(Level.SEVERE, "Failed to get total credit held for customer.", userData);
            return;
        }

        pnlTotalHeld.setBorder(BorderFactory.createTitledBorder("Total Credit Held for " + customerId + ":"));
        txtTotalHeld.setText(new DecimalFormat("#.00").format(totalHeld.doubleValue()));
    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);

        this.refreshForm(userData);
    }
}
