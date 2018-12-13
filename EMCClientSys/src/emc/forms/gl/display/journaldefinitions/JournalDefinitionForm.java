/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.gl.display.journaldefinitions;

import emc.enums.base.journals.Modules;
import emc.enums.gl.journals.GLJournalDirection;
import emc.enums.gl.journals.GLJournalType;
import emc.forms.app.journals.journaldefinitions.JournalDefinitionsForm;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class JournalDefinitionForm  extends JournalDefinitionsForm {

    /** Creates a new instance of JournalDefinitionForm. */
    public JournalDefinitionForm(EMCUserData userData){
        super(userData, Modules.GL);
    }

    @Override
    protected String[] getJournalDirections() {
        return new String[] {GLJournalDirection.GL.toString()};
    }

    @Override
    protected String[] getJournalTypes() {
        return new String[] {GLJournalType.GL.toString()};
    }
}

