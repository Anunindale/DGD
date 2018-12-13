/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.posting;

import emc.datatypes.inventory.transactions.DocumentNo;
import emc.datatypes.pop.purchaseordermaster.foreignkeys.PurchaseOrderIdFKNM;
import emc.datatypes.pop.purchasepostmaster.DocumentNumFKNM;
import emc.datatypes.pop.purchasepostmaster.PostMasterId;
import emc.datatypes.pop.supplier.foreignkeys.SupplierFK;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPPurchasePostMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"postMasterId", "purchaseOrderId", "companyId"})})
public class POPPurchasePostMaster extends EMCEntityClass {

    private String postMasterId;
    private String purchaseOrderId;
    private String supplierId;
    private String documentNumber;
    @Temporal(TemporalType.DATE)
    private Date postDate;
    private String postSetupId;
    private String approvedBy;
            
    /** Creates a new instance of POPPurchasePostMaster */
    public POPPurchasePostMaster() {
        
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        
        toBuild.put("postMasterId", new PostMasterId());
        toBuild.put("purchaseOrderId", new PurchaseOrderIdFKNM());
        toBuild.put("supplierId", new SupplierFK());
        toBuild.put("documentNumber", new DocumentNumFKNM());
          
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery theQuery = super.buildQuery();
        
        theQuery.addAnd("postMasterId", "");
        
        return theQuery;
    }

    public String getPostMasterId() {
        return postMasterId;
    }

    public void setPostMasterId(String postMasterId) {
        this.postMasterId = postMasterId;
    }

    public String getPostSetupId() {
        return postSetupId;
    }

    public void setPostSetupId(String postSetupId) {
        this.postSetupId = postSetupId;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

}
