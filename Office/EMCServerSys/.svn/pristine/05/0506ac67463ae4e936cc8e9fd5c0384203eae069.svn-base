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
public enum ServerPMMMessageEnum implements EMCMessageEnum {

    //Subject component report.
    ZERO_NORM_FOUND("One or more measurements with a zero norm and zero upper limit were found while populating the {0} chart.  These measurements were ignored.", "One or more measurements with a zero norm and zero upper limit were found while populating the {0} chart.  These measurements were ignored."),
    //Assessment generation
    ZERO_NORM_AND_LIMIT("No measurement or upper limit found for {0}, {1}, {2}, {3}.  Cannot generate assessment.", "No measurement or upper limit found for {component}, {measurement}, {level}, {position}.  Cannot generate assessment."),
    //No Disciplines found
    NO_DISCIPLINES("No category found.", "No category found."),
    //No Components found
    NO_COMPONENTS_FOUND("No components found for the category", "No components found for the category"),
    NEW_NORMS("No new norms needed to be generated.", "No new norms needed to be generated."),
    NO_NEW_NORM_WITH_POSITION("Failed to add a norm with a position value.", "Failed to add a norm with a position value."),
    SUBJECT_NORM_NOT_UPDATED("No subject norms were found to be updated for the norm.", "No subject norms were found to be updated for the norm."),
    SUBJECT_NORMS_UPDATED("Norms updated for subject.", "Norms updated for subject."),
    FAT_PERC_FAIL("Could not calculate fat percentage.", "Could not calculate fat percentage."),
    MAX_HR_MIN_HR_CONSTRAINT("The maximum heart rate may not be less than the resting heart rate.", "The maximum heart rate may not be less than the resting heart rate."),
    VO2_REST_HR("Could not calculate VO2, resting heart rate was not captured.", "Could not calculate VO2, resting heart rate was not captured."),
    VO2_MAX_HR("Could not calculate VO2, maximum heart rate was not captured.", "Could not calculate VO2, maximum heart rate was not captured."),
    HEIGHT_LIMITATION("Client's height must be greater than 160cm to be able to use the US navy method to calculate fat percentage.", "Client's height must be greater than 160cm to be able to use the US navy method to calculate fat percentage."),
    NO_FAT_METHOD("Please choose a fat percentage calculation method.", "Please choose a fat percentage calculation method."),
    NO_GENDER_CHART("Could not generate Fat Percentage chart as the subject does not have a gender specified.", "Could not generate Fat Percentage chart as the subject does not have a gender specified."),
    NO_DATE_OF_BIRTH_CHART("Could not generate Fat Percentage chart as there is not a date of birth captured for the subject.", "Could not generate Fat Percentage chart as there is not a date of birth captured for the subject."),
    NO_BMI("BMI could not be calculated as both weight and height is required fields and have not been specified.","BMI could not be calculated as both weight and height is required fields and have not been specified."),
    NO_CONVERSION("No conversion set up for weight and height.","No conversion set up for weight and height."),
    NO_PARAMETERS("Parameters were setup, please setup parameters.","Parameters were setup, please setup parameters."),
    COULD_NOT_GENERATE("Could not generate questions, please retry.", "Could not generate questions, please retry.");
    //Enum variables
    private String defaultMessage;
    private String defaultDescription;

    /** Creates a new instance of ServerPMMMessageEnum. */
    ServerPMMMessageEnum(String defaultMessage, String defaultDescription) {
        this.defaultMessage = defaultMessage;
        this.defaultDescription = defaultDescription;
    }

    @Override
    public enumEMCModules getEMCModule() {
        return enumEMCModules.PMM;
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
