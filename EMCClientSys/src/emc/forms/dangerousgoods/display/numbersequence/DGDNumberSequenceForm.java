/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.numbersequence;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.numbersequence.NumberSequenceForm;
import emc.framework.EMCUserData;

/**
 *
 * @author pj
 */
public class DGDNumberSequenceForm extends NumberSequenceForm {

    public DGDNumberSequenceForm(EMCUserData userData) {
        super( enumEMCModules.DG, userData);
    }
}
