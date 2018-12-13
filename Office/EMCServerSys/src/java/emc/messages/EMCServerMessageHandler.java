/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.messages;

import emc.entity.base.messages.BaseMessages;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBean;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 * @Name         : EMCServerMessageHandler
 *
 * @Date         : 13 Oct 2009
 *
 * @Description  : This bean acts as a message handler on the EMCServer.
 *
 * @author       : riaan
 */
@Stateless
public class EMCServerMessageHandler extends EMCBean implements EMCServerMessageHandlerLocal {

    /** Creates a new instance of EMCServerMessageHandler. */
    public EMCServerMessageHandler() {

    }

    /**
     * Fetches a message matching the specified message id from the EMC database.
     * @param messageId Id of message to find.
     * @param userData User data.
     * @param parameters Message parameters.
     * @return A message matching the specified message id from the EMC database.
     */
    @Override
    public String getMessage(EMCMessageEnum messageId, EMCUserData userData, Object... parameters) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseMessages.class.getName());
        query.addField("message");
        query.addAnd("messageId", messageId.toString());
        query.addAnd("messageModule", messageId.getEMCModule().toString());

        String message = (String)util.executeSingleResultQuery(query, userData);

        if (message == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Message not found in SQL.  Using default message.", userData);
            }
            message = messageId.getDefaultMessage();
        }

        return MessageFormat.format(message, parameters);
    }
}
