/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.um;

/**
 *
 * @author kapeshi
 */
public class PhonenumberHelper {

    private String nationalNumber;
    private String internationalNumber;
    private String originalNumber;
    private String postalCode;
    private String country;
    private int countryCode;
    private boolean valid = false;
    private boolean hasCountryCode;
    private boolean hasPostalCode;

    public PhonenumberHelper() {
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getInternationalNumber() {
        return internationalNumber;
    }

    public void setInternationalNumber(String internationalNumber) {
        this.internationalNumber = internationalNumber;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getOriginalNumber() {
        return originalNumber;
    }

    public void setOriginalNumber(String originalNumber) {
        this.originalNumber = originalNumber;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean hasCountryCode() {
        return hasCountryCode;
    }

    public void setHasCountryCode(boolean hasCountryCode) {
        this.hasCountryCode = hasCountryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean hasPostalCode() {
        return hasPostalCode;
    }

    public void setHasPostalCode(boolean hasPostalCode) {
        this.hasPostalCode = hasPostalCode;
    }
}
