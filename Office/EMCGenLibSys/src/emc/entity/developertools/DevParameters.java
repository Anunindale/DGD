/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.EmailAddress;
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
@Table(name = "DevParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class DevParameters extends EMCEntityClass {

    private String completedBugMailAddress1;
    private String completedBugMailAddress2;
    private String completedBugMailAddress3;
    private String completedBugMailAddress4;
    private String completedBugMailAddress5;
    private String completedBugFromMailAddress;
    private String generalUser;

    public String getCompletedBugMailAddress1() {
        return completedBugMailAddress1;
    }

    public void setCompletedBugMailAddress1(String completedBugMailAddress1) {
        this.completedBugMailAddress1 = completedBugMailAddress1;
    }

    public String getCompletedBugMailAddress2() {
        return completedBugMailAddress2;
    }

    public void setCompletedBugMailAddress2(String completedBugMailAddress2) {
        this.completedBugMailAddress2 = completedBugMailAddress2;
    }

    public String getCompletedBugMailAddress3() {
        return completedBugMailAddress3;
    }

    public void setCompletedBugMailAddress3(String completedBugMailAddress3) {
        this.completedBugMailAddress3 = completedBugMailAddress3;
    }

    public String getCompletedBugMailAddress4() {
        return completedBugMailAddress4;
    }

    public void setCompletedBugMailAddress4(String completedBugMailAddress4) {
        this.completedBugMailAddress4 = completedBugMailAddress4;
    }

    public String getCompletedBugMailAddress5() {
        return completedBugMailAddress5;
    }

    public void setCompletedBugMailAddress5(String completedBugMailAddress5) {
        this.completedBugMailAddress5 = completedBugMailAddress5;
    }

    public String getCompletedBugFromMailAddress() {
        return completedBugFromMailAddress;
    }

    public void setCompletedBugFromMailAddress(String completedBugFromMailAddress) {
        this.completedBugFromMailAddress = completedBugFromMailAddress;
    }

    public String getGeneralUser() {
        return generalUser;
    }

    public void setGeneralUser(String generalUser) {
        this.generalUser = generalUser;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("completedBugMailAddress1", new EmailAddress());
        toBuild.put("completedBugMailAddress2", new EmailAddress());
        toBuild.put("completedBugMailAddress3", new EmailAddress());
        toBuild.put("completedBugMailAddress4", new EmailAddress());
        toBuild.put("completedBugMailAddress5", new EmailAddress());
        toBuild.put("completedBugFromMailAddress", new EmailAddress());
        return toBuild;
    }
}
