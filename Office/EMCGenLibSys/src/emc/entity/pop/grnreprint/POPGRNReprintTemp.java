/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.grnreprint;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemIdFKNotMandatory;
import emc.datatypes.pop.grnreprint.NumLabels;
import emc.datatypes.pop.purchasepostlines.StandardLocation;
import emc.datatypes.pop.supplierreceivedjournalmaster.ReceivedDate;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "POPGRNReprintTemp")
public class POPGRNReprintTemp extends EMCEntityClass {

    private String itemId;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String serial;
    private String batch;
    @Temporal(TemporalType.DATE)
    private Date dateRecieved;
    private String grnumber;
    private String supplierCode;
    private int numLables;
    private Double width;
    private String standardLocation;
    private String documentNumber;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getDateRecieved() {
        return dateRecieved;
    }

    public void setDateRecieved(Date dateRecieved) {
        this.dateRecieved = dateRecieved;
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

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getGrnumber() {
        return grnumber;
    }

    public void setGrnumber(String grnumber) {
        this.grnumber = grnumber;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public int getNumLables() {
        return numLables;
    }

    public void setNumLables(int numLables) {
        this.numLables = numLables;
    }

    public double getWidth() {
        return width == null ? 0d : width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemId", new ItemIdFKNotMandatory());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("numLables", new NumLabels());
        toBuild.put("standardLocation", new StandardLocation());
        toBuild.put("dateRecieved", new ReceivedDate());

        return toBuild;
    }

    public String getStandardLocation() {
        return standardLocation;
    }

    public void setStandardLocation(String standardLocation) {
        this.standardLocation = standardLocation;
    }
}
