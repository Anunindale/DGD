/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.sendmsg;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.framework.EMCUserData;

/**
 *
 * @author rico
 */
public class SendMessageDRM extends emcDRMViewOnly {
    public SendMessageDRM(){
        super();
    }

    @Override
    public void setUserData(EMCUserData userData) {
        try{
            this.getTheForm().repaintFrameTitle(userData.getUserData().get(0).toString(), userData.getUserData().get(1).toString());
        }catch(Exception e){}
    }
    

}
