/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.pop.deliverymodes.DeliveryModeId;
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
@Table(name = "POPDeliveryModes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"deliveryModeId", "companyId"})})
public class POPDeliveryModes extends EMCEntityClass {

    private String deliveryModeId;
    private String description;

    /**
     * Creates a new instance of POPDeliveryModes
     */
    public POPDeliveryModes() {
    }

    public String getDeliveryModeId() {
        return deliveryModeId;
    }

    public void setDeliveryModeId(String deliveryModeId) {
        this.deliveryModeId = deliveryModeId;
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

        toBuild.put("deliveryModeId", new DeliveryModeId());
        toBuild.put("description", new Description());

        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("deliveryModeId");
        toBuild.add("description");
        return toBuild;
    }
}
