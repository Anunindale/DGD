/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.journals;

import emc.datatypes.pop.purchaseordermaster.foreignkeys.PurchaseOrderIdFKNM;
import emc.datatypes.pop.supplier.foreignkeys.SupplierFK;
import emc.datatypes.pop.supplierreceivedjournalmaster.DocumentNumber;
import emc.datatypes.pop.supplierreceivedjournalmaster.ReceivedDate;
import emc.datatypes.pop.supplierreceivedjournalmaster.ReceivedId;
import emc.datatypes.pop.supplierreceivedjournalmaster.Type;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@Table(name = "POPSupplierReceivedJournalMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"receivedId", "companyId"})})
public class POPSupplierReceivedJournalMaster extends EMCEntityClass {

    private String supplierId;
    private String receivedId;
    private String type;
    private String purchaseOrderId;
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    private String supplierName;
    private String supplierOrder; //Delivery note #
    private String documentNumber;

    /** Creates a new instance of POPSupplierReceivedJournalMaster */
    public POPSupplierReceivedJournalMaster() {
    }

    public String getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(String receivedId) {
        this.receivedId = receivedId;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierOrder() {
        return supplierOrder;
    }

    public void setSupplierOrder(String supplierOrder) {
        this.supplierOrder = supplierOrder;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("receivedId", new ReceivedId());
        toBuild.put("supplierId", new SupplierFK());
        toBuild.put("purchaseOrderId", new PurchaseOrderIdFKNM());
        toBuild.put("type", new Type());
        toBuild.put("receivedDate", new ReceivedDate());
        toBuild.put("documentNumber", new DocumentNumber());

        return toBuild;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String documentType) {
        this.type = documentType;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("receivedId");
        toBuild.add("supplierId");
        toBuild.add("receivedDate");
        return toBuild;
    }
}
