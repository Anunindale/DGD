/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools.sites;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.sites.AuthorizationKey;
import emc.datatypes.developertools.sites.MasterId;
import emc.datatypes.developertools.sites.ServerSystemId;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;
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
@Table(name = "DevSiteMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"masterId", "companyId"})})
public class DevSiteMaster extends EMCEntityClass {

    private String masterId;
    private String customerId;
    private String serverSystemId;
    private String authorizationKey;

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getServerSystemId() {
        return serverSystemId;
    }

    public void setServerSystemId(String serverSystemId) {
        this.serverSystemId = serverSystemId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("masterId", new MasterId());
        toBuild.put("customerId", new CustomerIdFKNotMandatory());
        toBuild.put("serverSystemId", new ServerSystemId());
        toBuild.put("authorizationKey", new AuthorizationKey());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("masterId");
        toBuild.add("customerId");
        return toBuild;
    }
}
