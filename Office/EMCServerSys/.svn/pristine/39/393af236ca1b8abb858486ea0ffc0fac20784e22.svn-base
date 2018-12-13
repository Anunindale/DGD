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
public enum ServerBaseMessageEnum implements EMCMessageEnum {

    //Insert validation failed
    IVALFAIL("Insert validation failed", "Insert Validation Failed"),
    //Update validation failed,
    UVALFAIL("Update validation failed", "Update Validation Failed"),
    //Delete validation failed
    DVALFAIL("Delete validation failed", "Delete Validation Failed"),
    //Number sequence number error
    NUMSEQCONF("The value for the {0} field conflicts with the number sequence used on the field.", "Invalid value entered into a number sequence field."),
    //Clone succeeded
    CLONESUC("Clone succeeded, created new record: {0}", "Clone succeeded"),
    //Could not validate field during import
    IMPNOTACCFIELD("Could not access field for validation: {0}", "Could not access field for validation"),
    //Failed to copy calendar
    CALCOPYFAIL("Failed To copy calendar. Could not insert new calendar.", "Failed To copy calendar. Could not insert new calendar."),
    //Failed to copy calendar exception
    EXCOPYFAIL("Failed To copy calendar. Could not insert new exception.", "Failed To copy calendar. Could not insert new exception."),
    //Failed copy calendar shift
    SHIFTCOPYFAIL("Failed To copy calendar. Could not insert new shift.", "Failed To copy calendar. Could not insert new shift."),
    //Calendar exception copied
    EXCOPY("Exception copied", "Exception copied"),
    //Shift day error
    SHIFTDAYERR("The day can't be specified if a date is specified.", "The day can't be specified if a date is specified."),
    //Shift date error
    SHIFTDATEERR("The date can't be specified if a day is specified.", "The date can't be specified if a day is specified."),
    //Shift date blank
    NOSHIFTDATE("You need to select a day or a date at least.", "You need to select a day or a date at least."),
    //All shifts captured
    ALLSHIFTCAP("All the shifts for the selected day have already been captured", "All the shifts for the selected day have already been captured"),
    //No calendar found
    NOCALFOUND("No calendar found.", "No calendar found."),
    //Shift total too large
    OVERTOT("There are only {0} to work on that day and your shifts amount to {1}.", "There are only getTimeToDisplay(total) to work on that day and your shifts amount to getTimeToDisplay(duration)."),
    //No number sequence set up for number sequence field
    NONUMSEQ("The {0} field supports a number sequence, but no number sequence is set up for that field.  Please inform the administrator.", "No number sequence found for number sequence field."),
    //Internal Employment history record created from BaseEmployees
    EMPHISTCREATED("Internal Employment history record created.", "Internal Employment history record created from BaseEmployees"),
    //Employee termination record created from BaseEmployees
    TERMINLOGCREATED("Termination log record created.", "Employee termination record created from BaseEmployees"),
    //No Access to journal definitions
    NOACCESS("You do not have access to this journal definition.", "You do not have access to this journal definition."),
    //Journal with lines
    HAS_LINES("The selected journal already has lines.", ".The selected journal already has lines"),
    //No menu item mappings for Base User Permissions report.
    NO_MENU_MAPPING("No menu item mappings found in user data.", "No menu item mappings found in user data."),
    //Journal definition approval groups.
    WRONG_GRP_MODULE("{0} is not in the {1} module.", "{approval group} is not in the {definition module} module."),
    APPROVAL_REQ("Approval is required.  Approved by may not be blank.", "Approval is required.  Approved by may not be blank."),
    //Journals
    APPROVED_MAY_NOT_CHANGE("Approved journals may not be changed, unapprove first.", "Approved journals may not be changed, unapprove first."),
    POSTED_MAY_NOT_CHANGE("Posted journals may not be changed.", "Posted journals may not be changed."),
    CONTRA_FIXED( "Field may not be changed.  Contra fixed on journal definition.",  "Field may not be changed.  Contra fixed on journal definition."),
    //Timed Operations
    TIMED_OPERATION_NOT_FOUND("Failed to find an implementation for enum while loading timed operations: {0}", "Failed to find an implementation for enum while loading timed operations: {operation}"),
    DRCT_ISRT_RECID_FAIL("Failed to Update BaseGeneratorDirectInsert.","Failed to Update BaseGeneratorDirectInsert."),
    REPORT_CONFLICT("You cannot have a header that is language specific.", "You cannot have a header that is language specific.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerBaseMessageEnum. */
    ServerBaseMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.BASE;
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
