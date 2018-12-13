/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.serialbatch;

import emc.datatypes.base.unitsofmeasure.foreignkeys.UnitOfMeasureFKNotMandatory;
import emc.datatypes.pop.serialbatch.POPBatch;
import emc.datatypes.pop.serialbatch.POPSerial;
import emc.datatypes.pop.serialbatch.POPquantity;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author wikus
 */
@Entity
public class SerialBatchSuper  extends EMCEntityClass {

    private String masterId;
    private String transId;
    private String batch;
    private String serial;
    private double quantity;
    private String type;
    
    //Ninian specific
    private String widthUOM;
    private double width;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public String getWidthUOM() {
        return widthUOM;
    }

    public void setWidthUOM(String widthUOM) {
        this.widthUOM = widthUOM;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("batch", new POPBatch());
        toBuild.put("serial", new POPSerial());
        toBuild.put("quantity", new POPquantity());
        toBuild.put("widthUOM", new UnitOfMeasureFKNotMandatory());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("batch");
        toBuild.add("serial");
        return toBuild;
    }   
    
}
