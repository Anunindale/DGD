/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.numbersequence;

import emc.enums.modules.enumEMCModules;
import emc.forms.app.numbersequence.NumberSequenceForm;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class TRECNumberSequenceForm extends NumberSequenceForm {

    public TRECNumberSequenceForm(EMCUserData userData) {
        super(enumEMCModules.TREC, userData);
    }
}
