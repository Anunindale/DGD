/* 
 * BaseLicenceTable.java
 *
 * Created on 24 October 2007, 09:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.systemwide.CompanyName;
import emc.datatypes.base.license.LicenseKey;
import emc.datatypes.base.license.Validate;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseLicence")
public class BaseLicenceTable extends EMCEntityClass implements Serializable {

    /** Creates a new instance of BaseLicenceTable */
    public BaseLicenceTable() {
    }
    @Column(name = "CompanyName", nullable = false)
    private String companyName;
    @Column(name = "LicenceKey", nullable = false, length = 1000)
    private String licenceKey;
    @Column(name = "ValidateOK", nullable = false)
    private boolean validateOK;
    @Temporal(TemporalType.DATE)
    private Date lastLogin;
    private boolean backDated;
    //Used to ensure that application may only run on one machine.
    private String authorizationKey;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public boolean isValidateOK() {
        return validateOK;
    }

    public void setValidateOK(boolean validateOK) {
        this.validateOK = validateOK;
    }

    //////////////////////////////////////////////
    //Added Marius 021216 to get number of users
    //and number of companies from the license key
    public int NumberOfUsers() {
        emc.license.EMCLicense emcLicense = new emc.license.EMCLicense();
        emcLicense.setCompanyName(companyName);
        if (emcLicense.emcDecrypt(licenceKey)) {
            return emcLicense.getNumUsers();
        }
        return 0;
    }

    public List<String> moduleList() {
        emc.license.EMCLicense emcLicense = new emc.license.EMCLicense();
        emcLicense.setCompanyName(companyName);
        if (emcLicense.emcDecrypt(licenceKey)) {
            List<String> moduleList = new ArrayList();
            List o = emcLicense.getModules();
            for (int j = 0; j < o.size(); j++) {
                if (EMCDebug.getDebug()) {
                    System.out.println(o.get(j));
                }
                moduleList.add(o.get(j).toString());
            }
            return moduleList;
        }
        return new ArrayList();
    }

    public int NumberOfCompanies() {
        emc.license.EMCLicense emcLicense = new emc.license.EMCLicense();
        emcLicense.setCompanyName(companyName);
        emcLicense.emcDecrypt(licenceKey);
        return emcLicense.getNumCompanies();
    }
    //////////////////////////////////////////////

    public Date licenseValidUntil() {
        emc.license.EMCLicense emcLicense = new emc.license.EMCLicense();
        emcLicense.setCompanyName(companyName);
        if (emcLicense.emcDecrypt(licenceKey)) {
            return emcLicense.getExpiryDate().getTime();
        }
        return new Date();
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("licenceKey", new LicenseKey());
        toBuild.put("companyName", new CompanyName());
        toBuild.put("validateOK", new Validate());

        return toBuild;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isBackDated() {
        return backDated;
    }

    public void setBackDated(boolean backDated) {
        this.backDated = backDated;
    }

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }
}
