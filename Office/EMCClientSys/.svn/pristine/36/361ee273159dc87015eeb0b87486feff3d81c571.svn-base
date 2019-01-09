/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.sop.display.salesrepcommission.resources;

import emc.app.components.emcJButton;
import emc.forms.sop.display.salesrepcommission.SOPSalesRepCommissionForm;
import java.awt.event.ActionEvent;

/**
 *
 * @author riaan
 */
public class MassUpdateButton extends emcJButton {

    private SOPSalesRepCommissionForm form;

    /** Creates a new instance of SalesRepCommissionMassUpdateButton. */
    public MassUpdateButton(SOPSalesRepCommissionForm form) {
        super("Mass Update");
        this.form = form;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        new MassUpdateDialog(form).setVisible(true);
    }
}
