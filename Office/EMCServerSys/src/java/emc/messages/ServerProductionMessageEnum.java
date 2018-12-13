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
public enum ServerProductionMessageEnum implements EMCMessageEnum {

    AWOFROMPLANFAIL("Failed to release Aggregate Works Order from Planned Works Orders", "Failed to release Aggregate Works Order from Planned Works Orders"),
    NOWCPLANGROUP("Please select a Work Center Planning Group before attempting to release an Aggregate Works Order.", "Please select a Work Center Planning Group before attempting to release an Aggregate Works Order."),
    AWOSTATUSNORESERVE("You may not reserve stock in the current status of the Aggregate Works Order.", "You may not reserve stock in the current status of the Aggregate Works Order."),
    AWOBOMLINEISSUED("Stock has already been issued against the specified AWO BOM line.  You may not reserve stock again.", "Stock has already been issued against the specified AWO BOM line.  You may not reserve stock again."),
    BOM_JOURNAL_QTY_CHANGE("Production BOM Journal line quantity changed.  Ensure that your registration is still valid.", "Production BOM Journal line quantity changed.  Ensure that your registration is still valid."),
    OPEN_PL_EXISTS("An open Picking List exists for the selected order.  You may not create a new Production BOM Journal while an open Picking List exists.", "An open Picking List exists for the selected order.  You may not create a new Production BOM Journal while an open Picking List exists."),
    PO_DEF_NOT_ALLOWED("A Purchase Order Definition may only be specified for statusses that create Purchase Orders.", "A Purchase Order Definition may only be specified for statusses that create Purchase Orders."),
    PO_DEF_NOT_FOUND("The status is set to create a Purchase Order.  Please specify a Purchase Order Definition.", "The status is set to create a Purchase Order.  Please specify a Purchase Order Definition."),
    PO_DEF_DUPLICATED("An {0} Purchase Order is already set to be created on another status.", "An {PO definition} Purchase Order is already set to be created on another status."),
    NO_STOCK_REGISTERED("No stock is registered for BOM Journal line {0}.  System reserved stock.", "No stock is registered for BOM Journal line {0}.  System reserved stock."),
    NO_PARAMS("No Production Parameters are set up.", "No Production Parameters are set up."),
    PACKS_PER_BOX_ITEM_AND_GRP("{0} and {1} may not be populated on the same record.", "{item id field} and {product group} may not be populated on the same record."),
    PACKS_PER_BOX_ITEM_AND_GRP_REC("Either {0} or {1} is required.", "Either {item id field} or {product group field} is required."),
    NO_CLASS6_ON_ITEM("No Class Group 6 (pack size) specified on item {0}.", "No Class Group 6 (pack size) specified on item {item reference}."),
    MULTIPLE_ORDERED_TX_FOR_BOM("Multiple ordered transactions exists against BOM line: {0} ({1}, {3}, {2})", "Multiple ordered transactions exists against BOM line: {item} (dimension1, dimension3, dimension2)"),
    MAY_NOT_CHANGE_STATUS("Your Status Change Group does not allow you to change the status of the specified order to {0}.", "Your Status Change Group does not allow you to change the status of the specified order to {new status}."),
    //Planned Order release
    INVALID_ASS_TYPE("Assembly Orders may only be released for groups with the {0} or {1} Work Types.", "Assembly Orders may only be released for groups with the {DESP} or {STK} Work Types."),
    INVALID_AGG_TYPE("Aggregate Works Orders may only be released for groups with the {0}, {1} and {2} Work Types.", "Assembly Orders may only be released for groups with the {CMT}, {STANDARD}, and {OUTWORK} Work Types."),
    MAX_WEEKS_EXCEEDED("You may not release orders for dates more than {0} week(s) from today.", "You may not release orders for dates more than {number of weeks} weeks from today."),
    INVALID_RELEASE_PARAMETERS("Invalid release parameters.", "Invalid release parameters."),
    //AWO Picking List Creation
    NO_ITEMS_ON_BOM("{0}, {1}, {2}, {3} has a zero quantity on the AWO BOM.", "{item reference}, {dimension1}, {dimension3}, {dimension2} has a zero quantity on the AWO BOM."),
    //BOM Journal
    REG_QTY_INVALID("Registered quantity is less than required quantity for {0} ({1}, {3}, {2}).", "Registered quantity is less than required quantity for {item} ({dimension 1}, {dimension 2}, {dimension 3})."),
    BOM_NOTFOUND("No AWO BOM Line found for {0} ({1}, {3}, {2}).", "No AWO BOM Line found for {item} ({dimension 1}, {dimension 2}, {dimension 3})."),
    PL_NOT_VALID_ON_LINE("Only added lines or previously issued lines in a status in which picking is allowed may be included on a Picking List.", "Only added lines or previously issued lines in a status in which picking is allowed may be included on a Picking List."),
    //Parameters
    STOCK_EXISTS_WITH_DEFAULT_SERIAL("Stock exists with default serial number.  The serial number may not be changed.", "Stock exists with default serial number.  The serial number may not be changed.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerBaseMessageEnum. */
    ServerProductionMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.PRODUCTION;
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
