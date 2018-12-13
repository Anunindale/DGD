/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.datatypes.sop.postmaster.DistributionOrder;
import emc.datatypes.sop.postmaster.PostDate;
import emc.datatypes.sop.postmaster.PostMasterId;
import emc.datatypes.sop.postsetup.foreignkeys.PostSetupIdFK;
import emc.datatypes.sop.salesordermaster.DeliveryMethod;
import emc.datatypes.sop.salesordermaster.foreignkeys.SalesOrderNoFK;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPSalesOrderPostMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"postMasterId", "salesOrderId", "companyId"})})
public class SOPSalesOrderPostMaster extends EMCEntityClass {

    private String postSetupId;
    private String postMasterId;
    private String salesOrderId;
    private String customerId;
    @Temporal(TemporalType.DATE)
    private Date postDate;
    private String deliveryTerms;
    private String deliveryMethod;
    @Temporal(TemporalType.DATE)
    private Date requiredDate;
    private boolean distributionOrder;

    public SOPSalesOrderPostMaster() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public boolean isDistributionOrder() {
        return distributionOrder;
    }

    public void setDistributionOrder(boolean distributionOrder) {
        this.distributionOrder = distributionOrder;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("postSetupId", new PostSetupIdFK());
        toBuild.put("postMasterId", new PostMasterId());
        toBuild.put("salesOrderId", new SalesOrderNoFK());
        toBuild.put("customerId", new CustomerIdFK());
        toBuild.put("postDate", new PostDate());
        toBuild.put("deliveryMethod", new DeliveryMethod());
        toBuild.put("deliveryTerms", new DeliveryTermsIdFK());
        toBuild.put("distributionOrder", new DistributionOrder());

        return toBuild;
    }
}
