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
public enum ServerDebtorsMessageEnum implements EMCMessageEnum {

    //Customer not found
    CUST_NOT_FOUND("Customer {0} not found.", "Customer {0} not found."),
    //Item not found
    ITEM_NOT_FOUND("Item {0} not found.", "Item {item} not found."),
    //Journals
    VAL_SUCC("Journal validated successfully.", "Journal validated successfully."),
    VAL_FAIL("Journal validation failed.", "Journal validation failed."),
    INVALID_DEF("The {0} Journal definition is not valid for the {1} module.", "The {0} Journal definition is not valid for the {1} module."),
    JR_ALREADY_POSTED("The selected journal has already been posted.  Changes not allowed.", "The selected journal has already been posted.  Changes not allowed."),
    INVALID_DATE("Journal line date may not be greater than journal date.", "Journal line date may not be greater than journal date."),
    INVALID_CUSTOMER("All financial transactions must be posted to the Invoice To Customer {0}.", "All financial transactions must be posted to the Invoice To Customer {invoice customer}."),
    //Post dated payments
    NO_DATE("Please specify both a from and a to date.", "Please specify both a from and a to date."),
    //Parameters
    WRONG_TYPE_DEF("{0} must be a a {1} journal definition.", "{field name} must be a a {journal definition type} journal definition."),
    PARAM_NOT_FOUND("Debtors parameters were not found.  Please save Debtors Parameters.", "Debtors parameters were not found.  Please save Debtors Parameters."),
    HAND_ITEM_TYPE("Handling charge item must be of the {0} type.", "Handling charge item must be of the {itemType} type."),
    DEL_ITEM_TYPE("Delivery charge item must be of the {0} type.", "Delivery charge item must be of the {itemType} type."),
    NO_DEF_RET_WHSE("No default return warehouse specified on Debtors Parameters.", "No default return warehouse specified on Debtors Parameters."),
    NO_DEF_RET_LOC("No default return location specified on Debtors Parameters.", "No default return location specified on Debtors Parameters."),
    DEF_RET_LOC_NOT_IN_WHSE("The default return location on Debtors Parameters is not in the default return warehouse.", "The default return location on Debtors Parameters is not in the default return warehouse."),
    //Invoice
    LINES_EXIST("{0} may not be changed once an Invoice has lines.", "{stock field name} may not be changed once an Invoice has lines."),
    INV_FROM_SO("{0} may not be changed if an Invoice was generated from a Sales Order.", "{field name} may not be changed if an Invoice was generated from a Sales Order."),
    INV_POSTED("Invoices may not be changed after being posted.", "Invoices may not be changed after being posted."),
    INV_CANCELED("Invoices may not be changed after being canceled.", "Invoices may not be changed after being canceled."),
    INV_NOT_FOUND("Invoice {0} not found.", "Invoice {invoice number} not found."),
    INSUF_PAY_PERIOD("Payment due date exceeds maximum financial period.  Please check your financial periods.", "Payment due date exceeds maximum financial period.  Please check your financial periods."),
    INSUF_DISC_PERIOD("Settlement discount date exceeds maximum financial period.  Please check your financial periods.", "Settlement discount date exceeds maximum financial period.  Please check your financial periods."),
    INVALID_INV_TO_CUST("Invoice to customer must be an invoice customer.", "Invoice to customer must be an invoice customer."),
    MAY_NOT_CHANGE_INV_CUST("Invoice to customer may not be changed while customer {0} has a balance.", "Invoice to customer may not be changed while customer {customer id} has a balance."),
    INV_TO_CUST_NOT_FOUND("Invoice to customer does not exist.", "Invoice to customer does not exist."),
    NO_DEL_CHARGE_ITEM("No delivery charge item specified on Debtors Parameters.", "No delivery charge item specified on Debtors Parameters."),
    NO_DEL_CHARGE("Delivery charge may not be zero.", "Delivery charge may not be zero."),
    DELIVERY_CHARGE_ITEM_NOT_FOUND("Delivery charge item not found.", "Delivery charge item not found."),
    NO_VAT_CODE_ON_DEL_CHARGE("No VAT code on delivery charge item.", "No VAT code on delivery charge item."),
    ZERO_TOTAL_COST("A total price of zero is not allowed.", "A total price of zero is not allowed."),
    DEL_CHARGE_EXISTS("A delivery charge line already exists on invoice {0}", "A delivery charge line already exists on invoice {invoice number}"),
    DEL_CHARGE_NOT_ALLOWED("You may not enter a delivery charge for the selected Invoice.", "You may not enter a delivery charge for the selected Invoice."),
    NO_REP_FOR_COMMISSION("Commission applies to the selected {0}.  Sales Rep may not be blank.", "Commission applies to the selected {invoice/credit note}.  Sales Rep may not be blank."),
    REP_NO_COMMISSION("Commission does not apply to the selected {0}.  No Sales Rep may be specified.", "Commission does not apply to the selected {invoice/credit note}.  No Sales Rep may be specified."),
    //Credit Notes
    ALREADY_APPROVED("The selected Credit Note has already been approved.", "The selected Credit Note has already been approved."),
    ALREADY_POSTED("The selected Credit Note has already been posted.", "The selected Credit Note has already been posted."),
    ALREADY_CANCELED("The selected Credit Note has already been canceled.", "The selected Credit Note has already been canceled."),
    NO_REASON("Credit Notes may not be approved without a reason code.", "Credit Notes may not be approved without a reason code."),
    NO_GROUP("No approval group specified on Credit Note.", "No approval group specified on Credit Note."),
    NO_EMPLOYEE("No employee found for your username.", "No employee found for your username."),
    APPROVAL_DISALLOWED("You are not authorized to approve the selected Credit Note.", "You are not authorized to approve the selected Credit Note."),
    APPROVAL_DISALLOWED_EMP("The Approval Group on a Credit Note may only be changed by an employee in the current approval group.", "The Approval Group on a Credit Note may only be changed by an employee in the current approval group."),
    UNAPPROVED_CR("Credit Note unapproved.", "Credit Note unapproved."),
    ONLY_QTY_CHANGE("Field may not be changed on stock Credit Notes generated from Invoices.", "Field may not be changed on stock Credit Notes generated from Invoices."),
    NO_ITEM_CHANGE("Items and dimensions may not be changed on Credit Notes generated from Invoices.", "Items and dimensions may not be changed on Credit Notes generated from Invoices."),
    CN_LINES_EXIST("{0} may not be changed once a Credit Note has lines.", "{stock field name} may not be changed once a Credit Note has lines."),
    APP_GRP_NO_CHANGE("Approval Group may not be changed when a Credit Note Reason has not been specified.", "Approval Group may not be changed when a Credit Note Reason has not been specified."),
    APP_HIGHER_LEVEL("Approval passed to higher level Approval Group.", "Approval passed to higher level Approval Group."),
    FAILED_TO_POST_CN("Failed to post Credit Note: {0} - {1}", "Failed to post Credit Note: {credit note number} - {exception message}"),
    NOT_ALL_CREDIT_NOTES_POSTED("Not all credit notes were posted successfully.", "Not all credit notes were posted successfully."),
    STARTING_CN_POSTING("Started Credit Note posting.", "Started Credit Note posting."),
    COMPLETED_CN_POSTING("Completed Credit Note posting.", "Completed Credit Note posting."),
    CRN_NOT_FOUND("Credit Note {0} not found.", "Credit Note {credit note number} not found."),
    RET_OPTION_NO_STOCK("{0} may not have a value on non-stock Credit Notes.", "{return option field name} may not have a value for non-stock Credit Notes."),
    NO_STOCK_RET_OPTION("{0} must have a value on stock Credit Notes.", "{return option field name} must have a value on stock Credit Notes."),
    REASON_NOT_FOUND("Credit Note reason not found.", "Credit Note reason not found."),
    APPROVAL_GROUP_NOT_FOUND("Approval Group not found for reason: {0}", "Approval Group not found for reason: {reason code}"),
    POSTED_CR_LINE_NO_DELETE("Posted Credit Note lines may not be deleted.", "Posted Credit Note lines may not be deleted."),
    CANCELED_CR_LINE_NO_DELETE("Canceled Credit Note lines may not be deleted.", "Canceled Credit Note lines may not be deleted."),
    POSTED_CR_NO_DELETE("Posted Credit Notes may not be deleted.", "Posted Credit Notes may not be deleted."),
    CANCELED_CR_NO_DELETE("Canceled Credit Notes may not be deleted.", "Canceled Credit Notes may not be deleted."),
    //Transactions
    CLOSED_PERIOD("Transactions may not be posted into a closed period. ({0})", "Transactions may not be posted into a closed period. ({journal date})"),
    //Transaction settlement
    AMT_CHANGE("{0} amount may not be changed if multiple transactions are ticked on the {1} side.", "{debit/credit} amount may not be changed if multiple transactions are ticked on the {other} side."),
    AMT_EXCEED("{0} total exceeds ticked amount.", "{opposite side} total exceeds ticked amount."),
    DISCOUNT_TOTAL_EXCEED("The discount taken may not be greater than the debit balance.", "The discount taken may not be greater than the debit balance."),
    DISCOUNT_AVAILABLE_EXCEED("The discount taken is greater than the discount available.", "The discount taken is greater than the discount available."),
    //Aging summary report.
    NO_AT_DATE("No at date specified - using today.", "No at date specified - using today."),
    NO_MODE("No aging mode specified for unallocated credits.  Using default mode from Debtors Parameters.", "No aging mode specified for unallocated credits.  Using default mode from Debtors Parameters."),
    //Debtors Balance Report
    NO_DATE_RANGE("No date range specified.  Cannot print report.", "No date range specified.  Cannot print report."),
    //Credit Stop Reasons
    SYSTEM_REASON("{0} is a system reason and may not be changed manually.", "{reason code} is a system reason and may not be changed manually."),
    //Credit Checking
    CUST_STOPPED("The specified customer ({0}) has been stopped and may not receive credit.", "The specified customer ({customer}) has been stopped and may not receive credit."),
    CUST_CLOSED("The specified customer has been closed and may not receive credit.", "The specified customer has been closed and may not receive credit."),
    CUST_TERMS_NOT_FOUND("No terms of payment found for the specified customer.", "No terms of payment found for the specified customer."),
    TERMS_EXCEEDED("The specified customer has an open debit transaction which exceeds Terms of Payment.", "The specified customer has an open debit transaction which exceeds Terms of Payment."),
    LIMIT_EXCEEDED("Credit limit will be exceeded.", "Credit limit will be exceeded."),
    CREDIT_CHECK_FAILED("Credit check failed.  You may not proceed.", "Credit check failed.  You may not proceed."),
    NO_TERMS("No aging period specified on parameters.  Unable to check terms.", "No aging period specified on parameters.  Unable to check terms."),
    NO_CURRENT_PERIOD("No current financial period found.  Cannot check terms.", "No current financial period found.  Cannot check terms."),
    FAILED_SET_HELD("Failed to set held flag on customer {0}.", "Failed to set held flag on customer {customer number}."),
    DELETING_CREDIT_HELD_MASTER("Deleting current credit held record.  Redoing credit check.", "Deleting current credit held record.  Redoing credit check."),
    //Edcon Allocation Import
    ALLOCATION_IMPORT_NOT_FOUND("Allocation import record not found for code: {0}", "Allocation import record not found for code: {import code}"),
    MAY_NOT_VALIDATE("Imports may not be validated after being allocated.", "Imports may not be validated after being allocated."),
    IMPORT_ALREADY_PROCESSED("The selected import has already been allocated.", "The selected import has already been allocated."),
    TRANS_ALLOC_FAILED("Failed to allocate transaction: {0}", "Failed to allocate transaction: {transaction number}"),
    STARTING_IMPORT("Starting allocation import.", "Starting allocation import."),
    INSUF_CREDIT("The selected customer does not have sufficient credit to pay all the debits in the import file.", "The selected customer does not have sufficient credit to pay all the debits in the import file."),
    IMPORT_COMPLETE("Allocation import complete.", "Allocation import complete."),
    //Age Analysis by Agent Report
    AGE_NO_DATE_RANGE("No date range specified for Sales Order selection.", "No date range specified for Sales Order selection."),
    //Credit Note Register
    NO_STOCK_CREDIT_NOTE("Register lines may not be captured against non-stock Credit Notes.", "Register lines may not be captured against non-stock Credit Notes."),
    OVER_REGISTRATION("Line quantity is less than registered quantity.  Over-registration against manual Credit Notes is not allowed.", "Line quantity is less than registered quantity.  Over-registration against manual Credit Notes is not allowed."),
    LINE_MAX_EXCEEDED("Total registered quantity will exceed the maximum quantity on the Credit Note line.", "Total registered quantity will exceed the maximum quantity on the Credit Note line."),
    //Royalty
    BLANK_ROYALTY_FIELD("{1} may not have a value if {0} is blank.", "{first royalty field} may not have a value if {second royalty field} is blank.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerDebtorsMessageEnum. */
    ServerDebtorsMessageEnum(String defaultMessage, String defaultDescription) {
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
