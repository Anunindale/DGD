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
public enum ServerSOPMessageEnum implements EMCMessageEnum {

    //Sales Orders
    INV_CUSTOMER_STATE("The selected customer is in the {0} state and may not be used on Sales Orders.", "The selected customer is in the {status} state and may not be used on Sales Orders."),
    CANCEL_NOT_ALLOWED("Sales Orders in the {0} state may not be cancelled.", "Sales Orders in the {order status} state may not be cancelled."),
    ORDER_INVOICED("Sales Orders may not be changed after being invoiced.", "Sales Orders may not be changed after being invoiced."),
    TYPE_CHANGED("{0} changed from {1} to {2}.  Transactions will be updated when you save the record.", "{type field name} changed from {previous type} to {current type}.  Transactions will be updated when you save the record."),
    DISALLOW_TYPE_CHANGE("{0} may not be changed in the current status.", "{type field name} may not be changed in the current status."),
    STATUS_REVERTED("Order reverted to {0} status.", "Order reverted to {captured} status."),
    DEL_CHARGE_DIFFERS("Delivery Charge flag on Sales Order differs from Delivery Charge flag on customer.", "Delivery Charge flag on Sales Order differs from Delivery Charge flag on customer."),
    DEL_CHARGE_ORDER_NOT_FOUND("Sales Order not found.  Allowing entry of delivery charge.", "Sales Order not found.  Allowing entry of delivery charge."),
    DEL_CHARGE_NOT_ALLOWED("You may not enter a delivery charge for Sales Order: {0}.", "You may not enter a delivery charge for Sales Order: {order number}."),
    OPEN_ASO_EXISTS("An open Assembly Order exists for the given order.  Please cancel the Assembly Order first.", "An open Assembly Order exists for the given order.  Please cancel the Assembly Order first."),
    OPEN_PL_EXISTS("An open Picking List exists for the given order.  Please cancel the Picking List first.", "An open Picking List exists for the given order.  Please cancel the Picking List first."),
    //Sales Order released from Blanket Order
    CREATED_SO("Created Sales Order: {0}", "Created Sales Order: {sales order number}"),
    //Reallocation
    NO_QUANTITIES("No reallocation/unreserve quantities entered.", "No reallocation/unreserve quantities entered."),
    DISALLOW_UNRESERVE("{0} may not be ticked when reallocating stock to another Sales Order.", "{unreserved field name} may not be ticked when reallocating stock to another Sales Order."),
    //Credit Note Registration
    REGISTER_QUANTITY_EXCEEDED("Registered quantity may not be more than original quantity delivered against specified dimensions.", "Registered quantity may not be more than original quantity delivered against specified dimensions."),
    //Parameters
    PARAMS_NOT_FOUND("SOP Parameters not found.", "SOP Parameters not found."),
    LINES_UPDATED("Sales Order Lines updated.", "Sales Order Lines updated.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerSOPMessageEnum. */
    ServerSOPMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.DEBTORS;
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
