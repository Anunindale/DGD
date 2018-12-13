/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.webportal;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.webportalusers.UserId;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @name        : BaseWebPortalUsers
 *
 * @description : Entity class to maintain web portal users.
 *
 * @date        : 18 Nov 2009
 *
 * @author      : riaan
 */
@Entity
@Table(name = "BaseWebPortalUsers", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"})})
public class BaseWebPortalUsers extends EMCEntityClass {

    private String userId;
    private String password = "emc123";
    private String linkToSourceType = WebPortalUsersReferenceType.CAPTURED.toString();
    private long linkToSourceRecId;
    private boolean active;

    /** Creates a new instance of BaseWebPortalUsers. */
    public BaseWebPortalUsers() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLinkToSourceType() {
        return linkToSourceType;
    }

    public void setLinkToSourceType(String linkToSourceType) {
        this.linkToSourceType = linkToSourceType;
    }

    public long getLinkToSourceRecId() {
        return linkToSourceRecId;
    }

    public void setLinkToSourceRecId(long linkToSourceRecId) {
        this.linkToSourceRecId = linkToSourceRecId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String,EMCDataType> toBuild =  super.buildFieldList();
        toBuild.put("userId", new UserId());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("userId");
        return toBuild;
    }

}
