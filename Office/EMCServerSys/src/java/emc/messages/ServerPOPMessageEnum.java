/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.messages;

import emc.enums.modules.enumEMCModules;

/**
 *
 * @author rico
 */
public enum ServerPOPMessageEnum implements EMCMessageEnum {

    //Insert validation failed
    POCAWOBOMFAIL("Failed to find AWO BOM line.", "Failed to find AWO BOM line."),
    OVER_REC_NOT_ALLOWED("PurchPase Parameters do not allow over receive.", "PurchPase Parameters do not allow over receive."),
    OVER_REC_EXCEEDED("Purchase Parameters limits over receive to {0}%", "Purchase Parameters limits over receive to {0}%"),
    //PO Lines
    INVALID_SPLIT_QTY("Split quantity exceeds outstanding quantity.", "Split quantity exceeds outstanding quantity."),
    MASTER_RECEIVED("Lines on a received Purchase Order may not be changed.", "Lines on a received Purchase Order may not be changed."),
    //Delivery terms
    DEL_TERMS_NOT_FOUND("Delivery terms not found.", "Delivery terms not found."),
    //No default supplier on Item Master
    NO_DEF_SUPPLIER("No default supplier on Item Master for item: {0}.", "No default supplier on Item Master for item: {item reference}."),
    //Parameters
    NO_POP_PARAMS("No POP Parameters set up.", "No POP Parameters set up."),
    //Planned Purchase Orders
    NO_PLANNED_ORDERS("No Planned Purchase Orders match criteria.", "No Planned Purchase Orders match criteria."),
    //Price Arrangements
    ARRANGEMENT_EXISTS("The specified period already has a similar Price Arrangement.", "The specified period already has a similar Price Arrangement."),
    //Higher level approval group available
    APP_HIGHER_LEVEL("Approval passed to higher level Approval Group.", "Approval passed to higher level Approval Group.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerBaseMessageEnum. */
    ServerPOPMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.POP;
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
