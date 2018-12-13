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
public enum ServerSupportMessageEnum implements EMCMessageEnum {

    INVALID_ID_NO("Invalid ID number.  A valid South African ID number consists of 13 digits.", "Invalid ID number.  A valid South African ID number consists of 13 digits.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerSystemMessageEnum. */
    ServerSupportMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        //TODO:  Should we add a 'Support' module or include all support messages in the Base module?
        return enumEMCModules.BASE;
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
