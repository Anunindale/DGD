/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.installations;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.installation.InstallationName;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevInstallationMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"installationName", "companyId"})})
public class DevInstallationMaster extends EMCEntityClass {

    private String installationName;
    private String installationDescription;

    public String getInstallationDescription() {
        return installationDescription;
    }

    public void setInstallationDescription(String installationDescription) {
        this.installationDescription = installationDescription;
    }

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("installationName", new InstallationName());
        toBuild.put("installationDescription", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("installationName");
        toBuild.add("installationDescription");
        return toBuild;
    }
}
