/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.personal.display.personal.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.forms.personal.display.personal.MyActivitiesForm;

/**
 *
 * @author riaan
 */
public class PersonalDRM extends emcDRMViewOnly {

    public PersonalDRM() {
        
    }
    
    @Override
    public void refreshData() {
        ((MyActivitiesForm)getTheForm()).refresh();
    }
    
    @Override
    public boolean persistOnClosing() {
        return true;
    }
}
