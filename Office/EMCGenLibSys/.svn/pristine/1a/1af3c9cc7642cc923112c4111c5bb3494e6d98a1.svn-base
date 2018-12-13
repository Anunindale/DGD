/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.versioningcontrol;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.versioning.RepositoryName;
import emc.datatypes.developertools.versioning.RepositoryURL;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.datatypes.systemwide.Description;
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
@Table(name = "DevVersioningControl", uniqueConstraints = {@UniqueConstraint(columnNames = {"customerId", "repositoryName", "companyId"})})
public class DevVersioningControl extends EMCEntityClass {

    private String repositoryName;
    private String repositoryDescription;
    private String repositoryURL;
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRepositoryDescription() {
        return repositoryDescription;
    }

    public void setRepositoryDescription(String repositoryDescription) {
        this.repositoryDescription = repositoryDescription;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryURL() {
        return repositoryURL;
    }

    public void setRepositoryURL(String repositoryURL) {
        this.repositoryURL = repositoryURL;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repositoryName", new RepositoryName());
        toBuild.put("repositoryDescription", new Description());
        toBuild.put("customerId", new CustomerIdFK());
        toBuild.put("repositoryURL", new RepositoryURL());
        return toBuild;
    }
}
