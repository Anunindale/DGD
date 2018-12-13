/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.messages;

import emc.datatypes.EMCString;

/**
 * @name        MessageId
 *
 * @date        20 Apr 2009
 *
 * @desciption  Datatype for messageId on BaseMessages entity.  Mandatory.
 *
 * @author      riaan
 */
public class MessageId extends EMCString {

    /** Creates a new instance of MessageId. */
    public MessageId() {
        this.setEmcLabel("Message Id");
        this.setMandatory(true);
    }
}
