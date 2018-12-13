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
public enum ServerMRPMessageEnum implements EMCMessageEnum {

    PARAMETERS_NOT_FOUND("No MRP parameters have been set up.  Cannot run MRP.", "No MRP parameters have been set up.  Cannot run MRP."),
    MRP_FAILED("Failed to run MRP: {0}", "Failed to run MRP: {exception message}."),
    MRP_SUCCESSFUL("MRP run completed successfully.", "MRP run completed successfully."),
    CREATED_ACTIVITY("MRP activity created.  Please see activity {0} for log messages.", "MRP activity created.  Please see activity {activity number} for log messages."),
    //Planned Order Number Sequences
    INSUF_PPO_NUM_SEQ("Insufficient sequence numbers available for planned Purchase Orders.", "Insufficient sequence numbers available for planned Purchase Orders."),
    INSUF_PWO_NUM_SEQ("Insufficient sequence numbers available for planned Works Orders.", "Insufficient sequence numbers available for planned Works Orders."),
    NO_PPO_NUM_SEQ("No number sequence found for planned Purchase Orders.  Cannot complete MRP run.", "No number sequence found for planned Purchase Orders.  Cannot complete MRP run."),
    NO_PWO_NUM_SEQ("No number sequence found for planned Works Orders.  Cannot complete MRP run.", "No number sequence found for planned Works Orders.  Cannot complete MRP run."),
    //Order Policies
    NO_EOQ("{0} ({1}, {2},{3}) uses the EOQ order policy, but no economic order quantity is specified for the item.  Using Discrete order policy.", "{item reference} ({dimension 1}, {dimension 3}, {dimension 2}) uses the EOQ order policy, but no economic order quantity is specified for the item.  Using Discrete order policy."),
    INVALID_MIN_MAX_MULT("{0} ({1}, {2},{3}) uses the Min/Max/Mult order policy, but the values specified are incorrect.  Maximum and mult order quantities may not be zero and minimum order quantity should be less than maximum order quantity.  Using Discrete order policy.", "{0} ({1}, {2},{3}) uses the Min/Max/Mult order policy, but the values specified are incorrect.  Maximum and mult order quantities may not be zero and minimum order quantity should be less than maximum order quantity.  Using Discrete order policy."),
    //BOM
    MPS_ON_NON_MPS_BOM("The BOM for non-MPS-managed item, {0} ({1}, {3}, {2}), contains an MPS-managed item ({4}, {5}, {7}, {6}).", "The BOM for non-MPS-managed item, {item ref} ({dim 1}, {dim 3}, {dim 2}), contains an MPS-managed item ({BOM item ref}, {BOM dim 1}, {BOM dim 3}, {BOM dim 2})."),
    //Calendar
    NO_BASE_CAL("No standard calendar set up on Base parameters.  Cannot run MRP.", "No standard calendar set up on Base parameters.  Cannot run MRP.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerMRPMessageEnum. */
    ServerMRPMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.MATERIALPLAN;
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
