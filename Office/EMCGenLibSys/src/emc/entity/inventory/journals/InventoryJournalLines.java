/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.journals;

import emc.datatypes.gl.glaccount.foreignkey.GLAccountNumFKMandatory;
import emc.datatypes.inventory.TransactionNumber;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFK;
import emc.datatypes.inventory.journals.journallines.ConfirmedQty;
import emc.datatypes.inventory.journals.journallines.Cost;
import emc.datatypes.inventory.journals.journallines.CountQty;
import emc.datatypes.inventory.journals.journallines.JRLineDate;
import emc.datatypes.inventory.journals.journallines.ToDimension1;
import emc.datatypes.inventory.journals.journallines.ToDimension2;
import emc.datatypes.inventory.journals.journallines.ToDimension3;
import emc.datatypes.inventory.journals.journallines.JRLineQuantity;
import emc.datatypes.inventory.journals.journallines.JRLineText;
import emc.datatypes.inventory.journals.journallines.JRTotalCost;
import emc.datatypes.inventory.journals.journallines.QtyOnHand;
import emc.datatypes.inventory.journals.journallines.ToItemId;
import emc.datatypes.inventory.journals.journalmaster.foreignkeys.JournalNumberFKUseBean;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.warehouses.foreignkeys.WarehouseFKNotMandatory;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
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
@Table(name = "InventoryJournalLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"journalNumber", "lineNo", "companyId"})})
public class InventoryJournalLines extends EMCEntityClass {

    private String journalNumber;
    private double lineNo;
    @Temporal(TemporalType.DATE)
    private Date lineDate;
    @Column(length = 1000)
    private String lineText;
    private String journalLineExtRef;
    private String dimension1;
    private String dimension2;
    private String dimension3;
        private String batch;
    private String serial;
    private String warehouse;
    private String pallet;
    private double quantity;
    private double cost;
    private double totalCost;
    private String transId;
    private String contraType;
    private String contraAccount;
    private String itemId;
    private double countQOH;
    private double countedQuantity;
    @Temporal(TemporalType.DATE)
    private Date countedDate;
    private String countedBy;
    private double confirmedQuantity;
    @Temporal(TemporalType.DATE)
    private Date confirmedDate;
    private String confirmedBy;
    private String location;
    //To item field
    private String toItemId;
    private String toDimension1;
    private String toDimension2;
    private String toDimension3;
    private String toWarehouse;
    private String toBatch;
    private String toSerial;
    private String toLocation;
    private String toPallet;
    private boolean generated = false;
    private String journalType;

    /** Creates a new instance of InventoryJournalLines */
    public InventoryJournalLines() {
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public double getLineNo() {
        return lineNo;
    }

    public void setLineNo(double lineNo) {
        this.lineNo = lineNo;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public String getJournalLineExtRef() {
        return journalLineExtRef;
    }

    public void setJournalLineExtRef(String journalLineExtRef) {
        this.journalLineExtRef = journalLineExtRef;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getCountQOH() {
        return countQOH;
    }

    public void setCountQOH(double countQOH) {
        this.countQOH = countQOH;
    }

    public double getConfirmedQuantity() {
        return confirmedQuantity;
    }

    public void setConfirmedQuantity(double confirmedQuantity) {
        this.confirmedQuantity = confirmedQuantity;
    }

    public String getContraType() {
        return contraType;
    }

    public void setContraType(String contraType) {
        this.contraType = contraType;
    }

    public String getContraAccount() {
        return contraAccount;
    }

    public void setContraAccount(String contraAccount) {
        this.contraAccount = contraAccount;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemId", new ItemIdFK());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());

        toBuild.put("toItemId", new ToItemId());
        toBuild.put("toDimension1", new ToDimension1());
        toBuild.put("toDimension2", new ToDimension2());
        toBuild.put("toDimension3", new ToDimension3());
        toBuild.put("toWarehouse", new WarehouseFKNotMandatory());
        toBuild.put("toLocation", new LocationFKNotManditory());

        toBuild.put("transId", new TransactionNumber());
        toBuild.put("warehouse", new WarehouseFKNotMandatory());

        toBuild.put("journalNumber", new JournalNumberFKUseBean());
        toBuild.put("quantity", new JRLineQuantity());
        toBuild.put("totalCost", new JRTotalCost());
        toBuild.put("cost", new Cost());
        toBuild.put("contraAccount", new GLAccountNumFKMandatory());
        toBuild.put("lineText", new JRLineText());
        toBuild.put("lineDate", new JRLineDate());
        toBuild.put("countQOH", new QtyOnHand());
        toBuild.put("countedQuantity", new CountQty());
        toBuild.put("confirmedQuantity", new ConfirmedQty());

        return toBuild;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getToItemId() {
        return toItemId;
    }

    public void setToItemId(String toItemId) {
        this.toItemId = toItemId;
    }

    public String getToDimension1() {
        return toDimension1;
    }

    public void setToDimension1(String toDimension1) {
        this.toDimension1 = toDimension1;
    }

    public String getToDimension2() {
        return toDimension2;
    }

    public void setToDimension2(String toDimension2) {
        this.toDimension2 = toDimension2;
    }

    public String getToDimension3() {
        return toDimension3;
    }

    public void setToDimension3(String toDimension3) {
        this.toDimension3 = toDimension3;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getToBatch() {
        return toBatch;
    }

    public void setToBatch(String toBatch) {
        this.toBatch = toBatch;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public Date getCountedDate() {
        return countedDate;
    }

    public void setCountedDate(Date countedDate) {
        this.countedDate = countedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountedBy() {
        return countedBy;
    }

    public void setCountedBy(String countedBy) {
        this.countedBy = countedBy;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addOrderBy("lineNo");

        return query;
    }

    public String getToPallet() {
        return toPallet;
    }

    public void setToPallet(String toPallet) {
        this.toPallet = toPallet;
    }

    /**
     * @return the countedQuantity
     */
    public double getCountedQuantity() {
        return countedQuantity;
    }

    /**
     * @param countedQuantity the countedQuantity to set
     */
    public void setCountedQuantity(double countedQuantity) {
        this.countedQuantity = countedQuantity;
    }

    public boolean getGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> ret = super.buildDefaultLookupFieldList();
        ret.add("journalNumber");
        return ret;
    }

    @Override
    public List<String> buildFieldListToClearOnCopy() {
        List<String> ret = super.buildFieldListToClearOnCopy();

        ret.add("lineNo");

        return ret;
    }
}
