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
public enum ServerHRMessageEnum implements EMCMessageEnum {

    //Training record created from prop training
    TRAININGCREATED("Employee training record created.", "Training record created from prop training.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerBaseMessageEnum. */
    ServerHRMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.HR;
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
