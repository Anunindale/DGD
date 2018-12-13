/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server;

import emc.framework.EMCLogRecord;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marius-Gert Coetzee
 */
public class EMCSessionMessages {
    private EMCUserData userData;
    private List<EMCLogRecord> messages;
    
    public EMCSessionMessages()
    {
        userData = new EMCUserData();
        messages = new ArrayList<EMCLogRecord>();
    }
    
    public EMCSessionMessages(EMCUserData _userData)
    {
        userData = _userData;
        messages = new ArrayList<EMCLogRecord>();
    }
    
    public void addMessages(EMCLogRecord _message)
    {
        messages.add(0,_message);
    }
    
    public List<EMCLogRecord> getMessages()
    {
        List<EMCLogRecord> ret = messages;
        messages = new ArrayList<EMCLogRecord>();
        return ret;         
    }
    
    public EMCUserData getUserData()
    {
        return userData;
    }

}
