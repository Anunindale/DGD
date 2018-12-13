/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.returngoodsreason.ReturnReasonId;
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "POPGoodsReturnReason", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"reasonCode", "companyId"})})
public class POPGoodsReturnReason extends EMCEntityClass {

    private String reasonCode;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("reasonCode", new ReturnReasonId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("reasonCode");
        toBuild.add("description");
        return toBuild;
    }
}
