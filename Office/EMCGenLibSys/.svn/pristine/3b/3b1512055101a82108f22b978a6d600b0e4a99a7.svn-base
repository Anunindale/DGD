/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.deploymentupdatelog.SubversionHeader;
import emc.datatypes.developertools.deploymentupdatelog.UpdateDate;
import emc.datatypes.developertools.installation.foreignkeys.InstallationNameFK;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevDeploymentUpdateLog")
public class DevDeploymentUpdateLog extends EMCEntityClass {

    private String installationName;
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    private int subversionHeader;

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    public int getSubversionHeader() {
        return subversionHeader;
    }

    public void setSubversionHeader(int subversionHeader) {
        this.subversionHeader = subversionHeader;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("installationName", new InstallationNameFK());
        toBuild.put("dateUpdated", new UpdateDate());
        toBuild.put("subversionHeader", new SubversionHeader());
        return toBuild;
    }
}
