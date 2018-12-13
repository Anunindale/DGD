/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.basket.BasketId;
import emc.datatypes.debtors.basket.SessionId;
import emc.datatypes.debtors.basket.Status;
import emc.datatypes.debtors.basket.foreignkeys.InvoiceIdFK;
import emc.datatypes.debtors.customerinvoice.PostedBy;
import emc.datatypes.debtors.customerinvoice.ReleasedBy;
import emc.datatypes.debtors.journals.foreignkeys.JournalNumberFK;
import emc.datatypes.debtors.journals.foreignkeys.JournalNumberFKNM;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.enums.debtors.basket.BasketStatus;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Stuart
 */
@Entity
@Table(name = "DebtorsBasketMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"basketId", "companyId"})})
public class DebtorsBasketMaster extends EMCEntityClass {
    private String basketId;
    private String sessionId;
    private String customerId;
    private String status = BasketStatus.CREATED.toString();
    private String invoiceId;
    private String journalId;
    private String invoiceToCustomerDeliveryInstructions;
    private String postedBy;
    private String releasedBy;
     private String physicalAddresLine1;
    private String physicalAddresLine2;
    private String physicalAddresLine3;
    private String physicalAddresLine4;
    private String physicalAddresLine5;
    private String physicalPostalCode;  
    
    
    public DebtorsBasketMaster(){
        
    }
    
    public void setBasketId(String basketId){
        this.basketId = basketId;
    }
    
    public String getBasketId(){
        return this.basketId;
    }
    
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    
    public String getCustomerId(){
        return this.customerId;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceToCustomerDeliveryInstructions() {
        return invoiceToCustomerDeliveryInstructions;
    }

    public void setInvoiceToCustomerDeliveryInstructions(String invoiceToCustomerDeliveryInstructions) {
        this.invoiceToCustomerDeliveryInstructions = invoiceToCustomerDeliveryInstructions;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getPhysicalAddresLine1() {
        return physicalAddresLine1;
    }

    public void setPhysicalAddresLine1(String physicalAddresLine1) {
        this.physicalAddresLine1 = physicalAddresLine1;
    }

    public String getPhysicalAddresLine2() {
        return physicalAddresLine2;
    }

    public void setPhysicalAddresLine2(String physicalAddresLine2) {
        this.physicalAddresLine2 = physicalAddresLine2;
    }

    public String getPhysicalAddresLine3() {
        return physicalAddresLine3;
    }

    public void setPhysicalAddresLine3(String physicalAddresLine3) {
        this.physicalAddresLine3 = physicalAddresLine3;
    }

    public String getPhysicalAddresLine4() {
        return physicalAddresLine4;
    }

    public void setPhysicalAddresLine4(String physicalAddresLine4) {
        this.physicalAddresLine4 = physicalAddresLine4;
    }

    public String getPhysicalAddresLine5() {
        return physicalAddresLine5;
    }

    public void setPhysicalAddresLine5(String physicalAddresLine5) {
        this.physicalAddresLine5 = physicalAddresLine5;
    }

    public String getPhysicalPostalCode() {
        return physicalPostalCode;
    }

    public void setPhysicalPostalCode(String physicalPostalCode) {
        this.physicalPostalCode = physicalPostalCode;
    }
    
    
    
    
    
    @Override
    public EMCQuery buildQuery(){
        EMCQuery query = super.buildQuery();
        
        query.addOrderBy("invoiceId");
        
        return query;
    }
    
    @Override
    public List<String> getDefaultLookupFields(){
        List<String> fields = new ArrayList<String>();
        fields.add("customerId");
        fields.add("basketId");
        return fields;
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList(){
        HashMap ret = super.buildFieldList();
        ret.put("basketId", new BasketId());
        ret.put("sessionId", new SessionId());
        ret.put("customerId", new CustomerIdFK());
        ret.put("status", new Status());
        ret.put("invoiceId", new InvoiceIdFK());
        ret.put("postedBy", new PostedBy());
        ret.put("releasedBy", new ReleasedBy());
        ret.put("journalId", new JournalNumberFKNM());
        return ret;
    }

   

    
}
