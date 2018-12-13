/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.messages;

import emc.enums.modules.enumEMCModules;

/**
 *
 * @author claudette
 */
public enum ServerFAMessageEnum implements EMCMessageEnum {

    //Purchase date after sell date
    PURCHASE_SELL_DATE("Sell date cannot be before purchase date.", "Sell date cannot be before purchase date."),
    PURCHASE_SCRAP_DATE("Scrap date cannot be before purchase date.", "Scrap date cannot be before purchase date."),
    SCRAP_SELL_DATE("There can not be a scrap and a sell date for one asset.", "There can not be a scrap and a sell date for one asset.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerFAMessageEnum. */
    ServerFAMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.FA;
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
