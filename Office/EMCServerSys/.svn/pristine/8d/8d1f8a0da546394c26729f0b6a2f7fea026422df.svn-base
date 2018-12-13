/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.messages;

import emc.enums.modules.enumEMCModules;

/**
 *
 * @author riaan
 */
public enum ServerUMMessageEnum implements EMCMessageEnum {

    //Model not found
    SUR_RES_EXISTS("The selected question already has responses stored in the database.  It may not be changed.", "The selected question already has responses stored in the database.  It may not be changed.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerUMMessageEnum. */
    ServerUMMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.UM;
    }

    /** Returns a "default" message. */
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    /** Returns a "default" description. */
    public String getDefaultDescription() {
        return this.defaultDescription;
    }
}
