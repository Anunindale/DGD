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
public enum ServerGLMessageEnum implements EMCMessageEnum {

    //Customer not found
    CUST_NOT_FOUND("Customer {0} not found.", "Customer {0} not found."),
    //Financial periods
    FIN_PERIOD_DATE("The date selected falls within an existing financial period.", "The date selected falls in an existing financial period."),
    FIN_PERIOD_TOO_SMALL("The period must be at least 7 days long.", "The period must be at least 7 days long"),
    FIN_PERIOD_MUST_FOLLOW("Financial periods have to follow each other.", "Financial periods have to follow each other."),
    FIN_FAILED_DM_PARM("Failed to get start day of the week.", "Failed to get start day of the week."),
    FIN_PERIOD_USED("The selected period is already in use, no updates are allowed.", "May not update Financial Period if it is already in use."),
    FIN_PERIOD_DATES_REQ("The selected period requires valid start and end dates before allowing a status change.", "The selected period requires valid start and end dates before allowing a status change."),
    FIN_PERIOD_CLOSE("Open and closed periods may not be interleaved.", "Open and closed periods may not be interleaved."),
    GENERATED_MORE_THAN_12_MONTHS("You have generated financial periods for more than 12 months.", "You have generated financial periods for more than 12 months."),
    NOT_OPENING_PERIOD("Please specify a valid opening period.", "Please specify a valid opening period."),
    NOT_CLOSING_PERIOD("Please specify a valid closing period.", "Please specify a valid closing period."),
    //Journals
    JOURNAL_ALREADY_POSTED("The journal has already been posted.", "The journal has already been posted."),
    JOURNAL_NOT_APPROVED("The journal has not been approved yet.", "The journal has not been approved yet."),
    INVALID_CONTRA_ACCOUNT("{0} and {1} may not match.  Line: {2}", "{account field name} and {contra account field name} may not match.  Line: {line description}"),
    LINE_DOES_NOT_BALANCE("Debit and Credit does not balance.  Line: {0}.", "Debit and Credit does not balance.  Line: {line description}."),
    PERIOD_NOT_BALANCE("Journal does not balance for period: {0}.", "Journal does not balance for period: {period name}."),
    JOURNAL_NOT_BALANCE("Journal does not balance.", "Journal does not balance."),
    VALIDATION_SUCCESS("Journal validated successfully.", "Journal validated successfully."),
    VALIDATION_FAIL("Failed to validate journal.", "Failed to validate journal."),
    VAT_INCLUDED("VAT is included on this journal.  VAT code and VAT input/output is required.", "VAT is included on this journal.  VAT code and VAT input/output is required."),
    VAT_NOT_INCLUDED("VAT is not included on this journal.  VAT code and VAT input/output must be left blank.", "VAT is not included on this journal.  VAT code and VAT input/output must be left blank"),
    CONTRA_SPECIFIED("{0} has been specified.  Please enter both debit and credit amounts.", "{contra account field name} has been specified.  Please enter both debit and credit amounts."),
    NO_CONTRA_SPECIFIED("{0} has not been specified.  You may not specify both debit and credit amounts.", "{contra account field name} has not been specified.  You may not specify both debit and credit amounts."),
    INVALID_ANALYSIS_CODES("The specified analysis codes are not valid for account: {0}.  Analysis code {1} is required.", "The specified analysis codes are not valid for account: {account number}.  Analysis code {code number} is required."),
    DEBIT_AND_CREDIT_SPECIFIED("You may not specify both a debit and a credit amount on a journal line.", "You may not specify both a debit and a credit amount on a journal line."),
    CONTRA_DEBIT_AND_CREDIT_SPECIFIED("You may not specify both a contra debit and a contra credit amount on a journal line.", "You may not specify both a contra debit and a contra credit amount on a journal line."),
    //Invalid or no period
    NO_PERIOD("No valid period found for this account number.", "No valid period found for this account number."),
    PERIOD_NOT_IN_RANGE("Period not in the specified range.", "Period not in the specified range."),
    INVALID_ACCOUNT("Invalid account number typed in.", "Invalid account number typed in."),
    PERIOD_INVALID("The from period has to be before the to period", "The from period has to be before the to period"),
    NO_PERIOD_FOR_DATE("No financial period found for date: {0}", "No financial period found for date: {date}"),
    //VAT
    NO_VAT_INPUT_ACCOUNT("No VAT input account found for {0}.", "No VAT input account found for {account number}."),
    NO_VAT_OUTPUT_ACCOUNT("No VAT output account found for {0}.", "No VAT output account found for {account number}."),
    NO_VAT_INPUT_OUTPUT("No VAT input/output specified.", "No VAT input/output specified."),
    //Chart of Accounts
    NO_GROUP_RULES_FOR_ACCOUNT("No group rules have been specified for account: {0}.", "No group rules have been specified for account: {account}."),
    ACC_CLOSED("The specified account ({0}) is closed.  No transactions may be posted to it.", "The specified account ({account number}) is closed.  No transactions may be posted to it."),
    ACC_LOCKED("The specified account ({0}) is locked.  You may not post transactions to it manually.", "The specified account ({account number}) is closed.  You may not post transactions to it manually."),
    //Transaction posting
    NO_TRANSACTIONS_FOUND("No transactions were found.  Consolidation cannot take place.", "No transactions were found.  Consolidation cannot take place.");

    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerBaseMessageEnum. */
    ServerGLMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.GENERAL_LEDGER;
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
