/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.messages;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseMessagesLocal extends EMCEntityBeanLocalInterface {

    /**
     * Populates BaseMessage records in SQL with server messages from the various server message enums.  This method will not overwrite existing records.
     * @param moduleString Module for which messages should be populated.  May not be blank.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean populateServerMessages(java.lang.String moduleString, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

}
