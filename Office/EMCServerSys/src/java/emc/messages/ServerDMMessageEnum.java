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
public enum ServerDMMessageEnum implements EMCMessageEnum {

    MODEL_NOT_FOUND("No forecast model found.", "No forecast model found."),
    CUST_NOT_ALLOWED("The model dimensions do not allow for a customer to be captured.", "The model dimensions do not allow for a customer to be captured."),
    VAL_NOT_ALLOWED("You may not specify a value for both {0} and {1}.", "You may not specify a value for both {0} and {1}."),
    GRP_NOT_MATCH("The {0} specified on the curve does not match the {0} on the item.", "The {0} specified on the curve does not match the {0} on the item."),
    DIM_REQ("Either a {0} or a {1} is required.", "Either a {dimension} or a {dimension curve} is required."),
    NO_DIM_REQ("Neither a {0} nor a {1} may be specified.", "Neither a {dimension} nor a {dimension curve} may be specified."),
    PERC_INVALID("The percentages specified on curve lines must sum up to 100% before a curve may be activated.", "The percentages specified on curve lines must sum up to 100% before a curve may be activated."),
    CURVE_STATUS("Curve status changed.", "Curve status changed."),
    CURVE_NOT_ACTIVE("The {0} has not been activated yet.", "The {curve} has not been activated yet."),
    DONE_PROCESSED("The {0} model has already been processed.", "The {model id} model has already been processed."),
    PROCESSED("Model processed succesfully.", "Model processed succesfully."),
    CURVE_WARNING("{0} of the {1} dimensions on the selected curve is not active on the selected item.", "Dimension Curve does not match item dimensions."),
    DEMAND_FENCHING("The demand fence does not allow you to update the selected lines.", "Demand fencing invalid."),
    DIM_CURVE_NOT_FOUND("Failed to find the dimension curve master record.", "Failed to find master record"),
    CUST_ON_GROUP("Customers belonging to a forecast group may not be captured individually.", "Customers belonging to a forecast group may not be captured individually."),
    //Forecast Performance report
    FORECAST_PERFORMANCE_INVALID_PERIOD("Invalid current period specified.", "Invalid current period specified.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerDMMessageEnum. */
    ServerDMMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.DEMANDMANAGEMENT;
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
