/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.deliverycharge.DeliveryChargeCode;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to store Debtors Delivery Charges.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsDeliveryCharge", uniqueConstraints = {@UniqueConstraint(columnNames = {"deliveryChargeCode", "companyId"})})
public class DebtorsDeliveryCharge extends EMCEntityClass {

    private String deliveryChargeCode;
    private String description;

    /** Creates a new instance of DebtorsDeliveryCharge */
    public DebtorsDeliveryCharge() {
    }

    public String getDeliveryChargeCode() {
        return deliveryChargeCode;
    }

    public void setDeliveryChargeCode(String deliveryChargeCode) {
        this.deliveryChargeCode = deliveryChargeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("deliveryChargeCode", new DeliveryChargeCode());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("deliveryChargeCode");
        toBuild.add("description");
        return toBuild;
    }
}
