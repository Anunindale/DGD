/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.pop.deliveryterms.DeliveryTermsId;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryCharge;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "POPDeliveryTerms", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"deliveryTermsId", "companyId"})})
public class POPDeliveryTerms extends EMCEntityClass {

    private String deliveryTermsId;
    private String description;
    private boolean deliveryCharge;

    /**
     * Creates a new instance of POPDeliveryTerms
     */
    public POPDeliveryTerms() {
    }

    public String getDeliveryTermsId() {
        return deliveryTermsId;
    }

    public void setDeliveryTermsId(String deliveryTermsId) {
        this.deliveryTermsId = deliveryTermsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("deliveryTermsId", new DeliveryTermsId());
        toBuild.put("description", new Description());
        toBuild.put("deliveryCharge", new DeliveryCharge());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("deliveryTermsId");
        toBuild.add("description");
        return toBuild;
    }

    public boolean isDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(boolean deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
}
