/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.installations;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.installation.PropertyName;
import emc.datatypes.developertools.installation.PropertyValue;
import emc.datatypes.developertools.installation.foreignkeys.InstallationNameFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevInstallationLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"installationName", "propertyName", "companyId"})})
public class DevInstallationLines extends EMCEntityClass {

    private String installationName;
    private String propertyName;
    private String propertyValue;

    public String getInstallationName() {
        return installationName;
    }

    public void setInstallationName(String installationName) {
        this.installationName = installationName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("installationName", new InstallationNameFK());
        toBuild.put("propertyName", new PropertyName());
        toBuild.put("propertyValue", new PropertyValue());
        return toBuild;
    }
}
