/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.postsetup.Post;
import emc.datatypes.sop.postsetup.PostSetupId;
import emc.datatypes.sop.postsetup.Print;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "SOPSalesOrderPostSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"postSetupId", "companyId"})})
public class SOPSalesOrderPostSetup extends EMCEntityClass {

    private String postSetupId;
    private boolean post;
    private boolean print;
    private boolean useBatch = true;

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public String getPostSetupId() {
        return postSetupId;
    }

    public void setPostSetupId(String postSetupId) {
        this.postSetupId = postSetupId;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("postSetupId", new PostSetupId());
        toBuild.put("post", new Post());
        toBuild.put("print", new Print());
        return toBuild;
    }

    /**
     * @return the useBatch
     */
    public boolean isUseBatch() {
        return useBatch;
    }

    /**
     * @param useBatch the useBatch to set
     */
    public void setUseBatch(boolean useBatch) {
        this.useBatch = useBatch;
    }
}
