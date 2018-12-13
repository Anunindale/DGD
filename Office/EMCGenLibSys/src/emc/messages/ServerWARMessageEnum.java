/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.messages;

import emc.enums.modules.enumEMCModules;

/**
 * This enum was moved to GenLibSys so that it can be accessed by the web
 * portal.
 *
 * @author claudette
 */
public enum ServerWARMessageEnum implements EMCMessageEnum {

    //Warranty check message
    INVALID_BARCODE("Invalid barcode.", "Invalid barcode."),
    WARRANTY_VALID("In warranty until {0}.", "In warranty until {date string}"),
    WARRANTY_EXPIRED("Warranty expired on {0}.", "Warranty expired on {date string}."),
    INCORRECT_RANGE_PRODUCTION_NUMBER("No data found for specified criteria.", "No data found for specified criteria."),
    NO_WARRANTY_GROUP("No warranty group found for item.", "No warranty group found for item."),
    NO_IP("No IP address was found for this device.", "No IP address was found for this device."),
    NO_USER("No user found with given password", "No user found with given password"),
    NO_BATCH("The specified batch was not found.", "The specified batch was not found."),
    NO_REPRINT("No serial numbers were selected for reprinting.", "No serial numbers were selected for reprinting."),
    BATCH_FOUND("Serial numbers cannot be added for existing batches.", "Serial numbers cannot be added for existing batches."),
    //Invoice scanning
    PROC_INV("Processed invoice: {0}", "Processed invoice: {invoice number}"),
    PROC_FAILED("Failed to process invoice.  Please try again.", "Failed to process invoice.  Please try again."),
    NO_SERIAL("No serial number specified.", "No serial number specified."),
    DUPLICATE_SERIAL("Duplicate serial number specified.", "Duplicate serial number specified."),
    ALL_SERIAL_CAPTURED("All of the required serial numbers have already been captured.", "All of the required serial numbers have already been captured."),
    NOT_ALL_ITEMS_SCANNED("The required number of items have not been scanned for {0}.", "The required number of items have not been scanned for {item}."),
    FAILED_TO_GET_INV_INFO("Failed to fetch info.  Please try again.", "Failed to fetch info.  Please try again."),
    TOO_MANY_SERIAL_NUMBERS("Serial numbers exceed item quantity.", "Serial numbers exceed item quantity."),
    ITEM_MISMATCH("The item does not match the serial number.", "The item does not match the serial number."),
    NO_PARM("No parameters found, please set up.", "No parameters found, please set up."),
    FAIL_INV_DATA("No or invalid response from Hansa.", "No or invalid response from Hansa."),
    NO_INV_LINES("No lines found.", "No lines found."),
    INV_NOT_FOUND("The invoice number was not found in Hansa.", "The invoice number was not found in Hansa."),
    MOV_NOT_FOUND("The movement number was not found in Hansa.", "The movement number was not found in Hansa."),
    INV_NOT_OK("The invoice NOT OK in Hansa.", "The invoice NOT OK in Hansa."),
    MOV_NOT_OK("The movement NOT OK in Hansa.", "The movement NOT OK in Hansa."),
    SERIAL_NOT_FOUND("Serial Number not found.", "Serial Number not found."),
    FAIL_VALIDATE("Failed to validate.", "Failed to validate."),
    INV_POSTED("Invoice posted.", "Invoice Posted."),
    TFR_POSTED("Transfer posted.", "Transfer Posted");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /**
     * Creates a new instance of ServerBaseMessageEnum.
     */
    ServerWARMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.WAR;
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
