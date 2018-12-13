/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.utility.support;

import emc.framework.EMCBean;
import emc.framework.EMCUserData;
import emc.messages.ServerSupportMessageEnum;
import java.util.logging.Level;
import javax.ejb.Stateless;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import emc.entity.base.BaseCountries;
import emc.entity.base.BasePostalCodes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.helpers.um.PhonenumberHelper;
import java.util.List;
import java.util.Locale;

/**
 * This bean offers various utility method used by thing such as validations throughout EMC.
 *
 * @author riaan
 */
@Stateless
public class SupportLogicBean extends EMCBean implements SupportLogicLocal {

    /** Creates a new instance of SupportLogicBean. */
    public SupportLogicBean() {
        
    }

    /**
     * Checks that the given parameter matches the format of a standard South African ID number.
     * @param idNumber Id number to check.
     * @param userData User data.
     * @return A boolean indicating whether the given parameter is a valid South African ID number.
     */
    public boolean validateSouthAfricanIDNumber(String idNumber, EMCUserData userData) {
        if (idNumber.matches("\\d{13}")) {
            return true;
        } else {
            logMessage(Level.SEVERE, ServerSupportMessageEnum.INVALID_ID_NO, userData);
            return false;
        }
    }
    
     /**
     * Validate the given number using Google libphonenumber and return a number
     * as valid or invalid, check isvalid to make sure which one
     * use national number for valid, use original number for invalid
     * postal code is also looked up for non South African
     *
     * @param number Id number to check.
     * @param userData User data.
     * @return An helper class with the number in national and international format
     * the country code and postal code
     */
    @Override
    public PhonenumberHelper validateTelephone(String number, EMCUserData userData) {
        PhonenumberHelper helper = null;
        Locale loc = Locale.getDefault();
        String region = "ZA";
        
        if (loc != null) {
            region = loc.getCountry();
        }
        
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber numberProto = phoneUtil.parse(number, region);
            if (numberProto != null) {
                helper = new PhonenumberHelper();
                helper.setNationalNumber(removeWhiteSpaces(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)));
                helper.setInternationalNumber(removeWhiteSpaces(phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)));
                helper.setHasCountryCode(numberProto.hasCountryCode());
                helper.setValid(phoneUtil.isValidNumber(numberProto));
                helper.setCountryCode(numberProto.getCountryCode());
                helper.setOriginalNumber(number);
                if (helper.hasCountryCode() && helper.getCountryCode() != 27) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCountries.class);
                    query.addTableAnd(BasePostalCodes.class.getName(), "Code", BaseCountries.class.getName(), "country");
                    query.addAnd("numberCode", helper.getCountryCode(), BaseCountries.class.getName());
                    query.addField("code", BasePostalCodes.class.getName());
                    List<String> postalCodes = (List<String>) util.executeGeneralSelectQuery(query, userData);
                    if (postalCodes != null && !postalCodes.isEmpty()) {
                        helper.setPostalCode(postalCodes.get(0));
                        helper.setHasPostalCode(true);
                    }
                }
            }
        } catch (NumberParseException e) {
            helper = new PhonenumberHelper();
            helper.setOriginalNumber(number);
            System.err.println("Failed to resolve phone number: " + e.toString());
            System.err.println("Original number: "+number);
        }

        return helper;
    }

    public String removeWhiteSpaces(String value) {
        if (!isBlank(value)) {
            value = value.trim();
            value = value.replaceAll("\\s+", "");
        }
        return value;
    }
}

