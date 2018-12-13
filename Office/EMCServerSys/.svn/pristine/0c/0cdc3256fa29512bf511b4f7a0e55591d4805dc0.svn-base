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
public enum ServerInventoryMessageEnum implements EMCMessageEnum {

    //Journals
    JRQTYCHANGE("Journal line quantity changed.  Remember to update registration lines to match new quantity.", "Journal line quantity change.  Remember to update registration lines to match new quantity."),
    JRREGDELETED("Deleting registration lines.", "Journal line changed.  Registration lines deleted."),
    //Move stock
    MOVE_SUCCESS("Succesfully moved stock.", "Succesfully moved stock."),
    //Parameters
    PARAM_NOT_FOUND("Inventory Parameters not found.", "Inventory Parameters not found."),
    //settlement
    SETTNODELETEUPDATE("This settlement was completed and may not be changed, only rolled back.", "Settlement Record no change"),
    SETINVALIDENDDATE("The End date specified must be greater than previous end dates.", "Invalid End date."),
    TX_NOT_FOUND("Could not find {0} transaction to reverse settlement.  (Record ID: {1})", "Could not find {IN/OUT} transaction to reverse settlement.  (Record ID: {recordID})"),
    //Transactions
    TXDELCLOSEFAIL("The Transaction has been closed and my not be deleted", "Transaction may not be deleted"),
    TXPERSISTFAILSC("The physical date of the transaction falls within a closed period", "Failed to insert or update transaction."),
    TXSCPERIOD("Failed to get valid stock close end date.", "Failed to get valid stock close end date."),
    //Picking List
    UPDATE_ON_CANCEL_ORDER("Updated Picking List {0} for cancelled order {1}", "Updated Picking List {0}(picking list id) for cancelled order {1}(order id)"),
    DELETE_ON_CANCEL_ORDER("Deleted Picking List {0} for cancelled order {1}", "Deleted Picking List {0}(picking list id) for cancelled order {1} (order id)"),
    MAY_NOT_UPDATE("Picking list may not be changed after being posted.", "Picking list may not be changed after being posted."),
    NO_LINES_SELECTED("No lines selected to add to consolidated Picking List.  Cannot create consolidated Picking List.", "No lines selected to add to consolidated Picking List.  Cannot create consolidated Picking List."),
    CARTONS_AND_WEIGHT("Sales Order Picking Lists may not be posted without a number of cartons and total weight.", "Sales Order Picking Lists may not be posted without a number of cartons and total weight."),
    PICKING_LIST_NOT_FOUND("Picking List {0} not found.", "Picking List {picking list id} not found."),
    PL_TRANS_NOT_FOUND("No stock transaction found for line {0}.  Item: {1}, {2}, {3}, {4}", "No stock transaction found for line {line number}.  Item: {item ref}, {dimension1}, {dimension3}, {dimension2}"),
    PL_CANCELLED("Cancelled Picking List: {0}", "Cancelled Picking List: {picking list id}"),
    //Warehouse and location
    WH_NOT_FOUND("Warehouse {0} not found.", "Warehouse {0} not found."),
    STOCK_NOT_AVAILABLE("Stock in the {0} location of the {1} warehouse is not available.", "Stock in the {0} location of the {1} warehouse is not available."),
    LOCATION_NOT_IN_WAREHOUSE("The {0} location is not in the {1} warehouse.", "The {location} location is not in the {warehouse} warehouse."),
    STOCK_TAKE_LOCATION("Location {0} has already been marked for stocktake.", "Location {location} has already been marked for stocktake."),
    //Dimensions
    DIM1_DUP("{0} is a duplicate of {1}.", "{dimension} is a duplicate of {other dimension}."),
    DIM1_SEQ_DUP("The sequence of the colours are not unique.", "The sequence of the colours are not unique."),
    DIM1_NOT_ACTIVE("{0} is not active and may not be used.", "{dimension} is not active and may not be used."),
    DIM_USED_ON_TRANS("{0} has been used on {1}.  Transaction Type: {2}, Transaction Reference: {3}.", "{dimension} has been used on {item}.  Transaction Type: {transaction type}, Transaction Reference: {transaction reference}."),
    DIM_USED_ON_BO("{0} has been used on {3} on a {1} Blanket Order. ({2})", "{dimension} has been used on {item reference} on a {SOP/POP} Blanket Order. ({order id})"),
    DIM_USED_ON_FORECAST("{0} has been used on {1} on a Forecast Model.  Model: {2}, Date: {3}.", "{dimension} has been used on {item} on a Forecast Model.  Model: {model id}, Date: {forecast transaction date}."),
    NO_DIMENSIONS_ALLOWED("No dimensions not allowed on item ({0}).", "No dimensions not allowed on item ({item reference})."),
    INVALID_DIMENSIONS_ON_ITEM("Invalid dimensions entered on item.  ({0}, {1}, {3}, {2})", "Invalid dimensions entered on item.  ({item reference}, {dimension 1}, {dimension 3}, {dimension 2})"),
    //Consolidated Picking List
    CON_PL_NOT_ISSUE_PART("Quantity may only be changed to zero or full quantity on consolidated Picking Lists.  To issue partially, update detail quantities.", "Quantity may only be changed to zero or full quantity on consolidated Picking Lists.  To issue partially, update detail quantities."),
    ISSUED_PICKING_LISTS("Issued Picking Lists", "Issued Picking Lists"),
    ISSUED_PICKING_LIST("Issued Picking List {0}.", "Issued Picking List {Picking List ID}."),
    //Reference
    INVALID_REFERENCE("Invalid item reference entered.", "Invalid item reference entered."),
    NO_REFERENCE("Failed to find item reference.", "Failed to find item reference."),
    MULTI_SUPP_REFERENCE("Multiple supplier references found.", "Multiple supplier references found."),
    SIMILAR_REF_EXISTS("A similar reference already exists; Item: {0}, Reference: {1}, Type: {2}, Supplier: {3}, Customer: {4}.", "A similar reference already exists; Item: {item}, Reference: {reference}, Type: {type}, Supplier: {supplier}, Customer: {customer}."),
    REF_MAY_NOT_CHANGE("The type of the {0} reference may not be changed.", "The type of the {reference type} reference may not be changed."),
    //Register
    BATCH_NOT_USED("The specified batch has not been issued out previously.", "The specified batch has not been issued out previously."),
    BATCH_USED_WITH_DIFFERENT_DIMS("The specified batch has already been used with different dimensions. ({0}, {2}, {1}) You may not register it against new dimensions.", "The specified batch has already been used with different dimensions. (dimension 1, dimension 2, dimension3) You may not register it against new dimensions."),
    ZERO_REG_QTY("Quantity may not be zero.", "Quantity may not be zero."),
    //Safety Stock
    SAFETY_STOCK_RECORD_EXISTS("A similar safety stock record already exists.", "A similar safety stock record already exists."),
    SAFETY_STOCK_CUSTOMER_REQUIRED("A customer is required for the {0} type.", "A customer is required for the {type} type."),
    SAFETY_STOCK_CUSTOMER_NOT_REQUIRED("A customer is not required for the {0} type.", "A customer is not required for the {type} type."),
    SAFETY_STOCK_ITEM_REQUIRED("An item is required for the {0} type.", "An item is required for the {type} type."),
    SAFETY_STOCK_ITEM_NOT_REQUIRED("An item is not required for the {0} type.", "An item is not required for the {type} type."),
    SAFETY_STOCK_FAILED_TO_POPULATE_DATES("Failed to populate date list of safety stock calculation.", "Failed to populate date list of safety stock calculation."),
    SAFETY_STOCK_NOT_ENOUGH_FINANCIAL_PERIODS("There are not enough financial period set up to cater for the safety stock horizon.", "There are not enough financial period set up to cater for the safety stock horizon."),
    SAFETY_STOCK_MANUAL_LINE_FOUND("Found manual lines in the generation criteria, no safety stock was generated for these lines.", "Found manual lines in the generation criteria, no safety stock was generated for these lines."),
    //Order Policy Checks
    MAX_NOT_ZERO("Maximum order quantity may not be zero for order policy (Min/Max/Mult).", "Minimum order quantity may not be zero for order policy (Min/Max/Mult)."),
    MAX_GREATER_THAN_MIN("Maximum order quantity must be greater than minimum order quantity for order policy (Min/Max/Mult).", "Maximum order quantity must be greater than  minimum order quantity for order policy (Min/Max/Mult)."),
    MIN_PLUS_MULT_LT_MAX("Minimum order quantity + mult order quantity may not be greater than maximum order quantity for order policy (Min/Max/Mult).", "Minimum order quantity + mult order quantity may not be greater than maximum order quantity for order policy (Min/Max/Mult)."),
    MULT_MINIMUM1("Mult order quantity must be a minimum of 1 for order policy (Min/Max/Mult).", "Mult order quantity must be a minimum of 1 for order policy (Min/Max/Mult)."),
    ECONOMIC_QTY_NOT_ZERO("Economic order quantity may not be zero for order policy (EOQ).", "Economic order quantity may not be zero for order policy (EOQ)."),
    STOCK_ON_HAND("There is stock on hand for this dimension and it may not be deleted.", "There is stock on hand for this dimension and it may not be deleted"),
    COMBINATIONS_ACTIVE("There is stock on hand for this combination and it may not be deleted.", "There is stock on hand for this combination and it may not be deleted."),
    //Param
    NO_PARAMS("No Inventory Parameters are set up.", "No Inventory Parameters are set up."),
    //Crate Consolidation
    NO_CONSOLIDATE_CRATE_QUANTITY("The \'Consolidate crates with quantity less than\' may not be zero.", "The \'Consolidate crates with quantity less than\' may not be zero."),
    NO_MAX_CRATE_QUANTITY("The \'Max crate quantity\' may not be zero.", "The \'Max crate quantity\' may not be zero."),
    NO_MAX_NUMBER_OF_CRATES("The \'Max number of crates to visit\' may not be zero.", "The \'Max number of crates to visit\' may not be zero."),
    NO_TRANSFER_DEFINITION("The \'Transfer journal definition\' has not been set in iventory parameters.", "The \'Transfer journal definition\' has not been set in iventory parameters."),
    NO_MOVEMENT_DEFINITION("The \'Movement journal definition\' has not been set in iventory parameters.", "The \'Movement journal definition\' has not been set in iventory parameters."),
    NO_MAX_JOURNAL_LINES("The \'Max number of line per journal\' has not been set in iventory parameters.", "The \'Max number of line per journal\' has not been set in iventory parameters."),
    NO_JOURNAL_LINE_COST("The \'Journal line cost\' has not been set in iventory parameters.", "The \'Journal line cost\' has not been set in iventory parameters."),
    NO_DEFAULT_WAREHOUSE("The \'Default warehouse\' has not been set in iventory parameters.", "The \'Default warehouse\' has not been set in iventory parameters."),
    NO_CONSOLIDATION_USER("The \'Activity User\' has not been set in iventory parameters, no activity will be created.", "The \'Activity User\' has not been set in iventory parameters, no activity will be created.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /**
     * Creates a new instance of ServerBaseMessageEnum.
     */
    ServerInventoryMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.INVENTORY;
    }

    /**
     * Returns a "default" message.
     */
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    /**
     * Returns a "default" description.
     */
    public String getDefaultDescription() {
        return this.defaultDescription;
    }
}
