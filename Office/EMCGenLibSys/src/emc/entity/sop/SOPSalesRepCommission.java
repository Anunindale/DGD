/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.repcommission.Commission;
import emc.datatypes.sop.repgroupsetup.RepId;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
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
@Table(name = "SOPSalesRepCommission", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"repId", "customerItemField1", "customerItemField2", "customerItemField3", "customerItemField4", "customerItemField5", "customerItemField6", "companyId"})})
public class SOPSalesRepCommission extends EMCEntityClass {

    private String repId;
    private String customerItemField1;
    private String customerItemField2;
    private String customerItemField3;
    private String customerItemField4;
    private String customerItemField5;
    private String customerItemField6;
    private BigDecimal commissionPerc = new BigDecimal(0);

    public BigDecimal getCommissionPerc() {
        return commissionPerc;
    }

    public void setCommissionPerc(BigDecimal commissionPerc) {
        this.commissionPerc = commissionPerc;
    }

    public String getCustomerItemField1() {
        return customerItemField1;
    }

    public void setCustomerItemField1(String customerItemField1) {
        this.customerItemField1 = customerItemField1;
    }

    public String getCustomerItemField2() {
        return customerItemField2;
    }

    public void setCustomerItemField2(String customerItemField2) {
        this.customerItemField2 = customerItemField2;
    }

    public String getCustomerItemField3() {
        return customerItemField3;
    }

    public void setCustomerItemField3(String customerItemField3) {
        this.customerItemField3 = customerItemField3;
    }

    public String getCustomerItemField4() {
        return customerItemField4;
    }

    public void setCustomerItemField4(String customerItemField4) {
        this.customerItemField4 = customerItemField4;
    }

    public String getCustomerItemField5() {
        return customerItemField5;
    }

    public void setCustomerItemField5(String customerItemField5) {
        this.customerItemField5 = customerItemField5;
    }

    public String getCustomerItemField6() {
        return customerItemField6;
    }

    public void setCustomerItemField6(String customerItemField6) {
        this.customerItemField6 = customerItemField6;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repId", new RepId());
        toBuild.put("commissionPerc", new Commission());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("repId");
        toBuild.add("customerItemField1");
        toBuild.add("customerItemField2");
        toBuild.add("customerItemField3");
        return toBuild;
    }
}
