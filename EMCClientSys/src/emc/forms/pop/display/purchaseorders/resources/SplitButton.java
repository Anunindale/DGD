/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.forms.pop.display.purchaseorders.PurchaseOrderLinesDRM;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class SplitButton extends emcJButton {

    private PurchaseOrderLinesDRM linesDRM;
    /** Creates a new instance of SplitButton. */
    public SplitButton(PurchaseOrderLinesDRM linesDRM) {
        super("Split");

        this.linesDRM = linesDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        
        if (EMCDialogFactory.createQuestionDialog(this.linesDRM.getTheForm(), "Split line?", "Do you want to split the selected line?") == JOptionPane.YES_OPTION) {
            new SplitDialog(linesDRM).setVisible(true);
        }
    }
}
