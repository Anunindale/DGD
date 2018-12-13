/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.journals;

import emc.datatypes.inventory.TransactionNumber;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.pop.purchasepostlines.StandardLocation;
import emc.datatypes.pop.supplierreceivedjournallines.DimId;
import emc.datatypes.pop.supplierreceivedjournallines.ItemId;
import emc.datatypes.pop.supplierreceivedjournallines.LineAmount;
import emc.datatypes.pop.supplierreceivedjournallines.LineNo;
import emc.datatypes.pop.supplierreceivedjournallines.Price;
import emc.datatypes.pop.supplierreceivedjournallines.Quantity;
import emc.datatypes.pop.supplierreceivedjournallines.UOM;
import emc.datatypes.pop.supplierreceivedjournalmaster.ReceivedDate;
import emc.datatypes.pop.supplierreceivedjournalmaster.foreignkeys.ReceivedIdFK;
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
@Table(name = "POPSupplierReceivedJournalLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"receivedId", "lineNo", "companyId"})})
public class POPSupplierReceivedJournalLines extends EMCEntityClass {

    private String receivedId;
    private double lineNo;
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    private double quantity;
    private double price;
    private double lineAmount;
    private long dimId;
    private String uom;
    private String itemId;
    private String transactionNumber;
    //Used for GRN labels
    private String standardLocation;

    /** Creates a new instance of POPSupplierReceivedJournalLines */
    public POPSupplierReceivedJournalLines() {
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

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(double lineAmount) {
        this.lineAmount = lineAmount;
    }

    public long getDimId() {
        return dimId;
    }

    public void setDimId(long dimId) {
        this.dimId = dimId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("transactionNumber", new TransactionNumber());
        toBuild.put("standardLocation", new StandardLocation());
        toBuild.put("receivedId", new ReceivedIdFK());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("dimId", new DimId());
        toBuild.put("itemId", new ItemId());
        toBuild.put("lineAmount", new LineAmount());
        toBuild.put("lineNo", new LineNo());
        toBuild.put("price", new Price());
        toBuild.put("quantity", new Quantity());
        toBuild.put("uom", new UOM());
        toBuild.put("receivedDate", new ReceivedDate());


        return toBuild;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getStandardLocation() {
        return standardLocation;
    }

    public void setStandardLocation(String standardLocation) {
        this.standardLocation = standardLocation;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("receivedId");
        toBuild.add("itemId");
        return toBuild;
    }
}
