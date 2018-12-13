/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.posting;

import emc.datatypes.pop.purchasepostsetup.PostSetupId;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPPurchasePostSetupTable", uniqueConstraints = {@UniqueConstraint(columnNames = {"postSetupId", "companyId"}), @UniqueConstraint(columnNames = {"autoNumber"})})
public class POPPurchasePostSetupTable extends EMCEntityClass {

    private String postSetupId;
    private boolean post;
    private boolean print;
    //Printer setup?
    private String quantitySelection;
    private String documentType;
    //This field is included simply for convenience when selecting the last setup for a user
    private long autoNumber;
            
    /** Creates a new instance of POPPurchasePostSetupTable */
    public POPPurchasePostSetupTable() {
        
    }

    public boolean getPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public boolean getPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public String getQuantitySelection() {
        return quantitySelection;
    }

    public void setQuantitySelection(String quantitySelection) {
        this.quantitySelection = quantitySelection;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("postSetupId", new PostSetupId());
        return toBuild;
    }

    public String getPostSetupId() {
        return postSetupId;
    }

    public void setPostSetupId(String postSetupId) {
        this.postSetupId = postSetupId;
    }

    public long getAutoNumber() {
        return autoNumber;
    }

    public void setAutoNumber(long autoNumber) {
        this.autoNumber = autoNumber;
    }
}