/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools;

/**
 *
 * @author riaan
 */
public class CompanyReportInformation {

    private String companyName;
    private String tradingAs;
    private String website;

    /**
     * Creates a new instance of CompanyReportInformation.
     */
    public CompanyReportInformation() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingAs() {
        return tradingAs;
    }

    public void setTradingAs(String tradingAs) {
        this.tradingAs = tradingAs;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
