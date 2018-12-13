/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField1DS;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField2DS;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField3DS;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField4DS;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField5DS;
import emc.datatypes.sop.repcommissionenquiry.CustomerItemField6DS;
import emc.datatypes.sop.repcommissionenquiry.RepGroupDS;
import emc.datatypes.sop.repcommissionenquiry.RepGroupManagerDS;
import emc.datatypes.sop.repcommissionenquiry.RepIdDS;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesRepCommissionEnquiry extends EMCEntityClass {

    private String repId;
    private String repName;
    private String repGroup;
    private String repGroupDescription;
    private String repGroupManager;
    private String repGroupManagerName;
    private String customerItemField1;
    private String customerItemField2;
    private String customerItemField3;
    private String customerItemField4;
    private String customerItemField5;
    private String customerItemField6;
    private String customerItemFieldDesc1;
    private String customerItemFieldDesc2;
    private String customerItemFieldDesc3;
    private String customerItemFieldDesc4;
    private String customerItemFieldDesc5;
    private String customerItemFieldDesc6;
    private BigDecimal defaultCommission = new BigDecimal(0);
    private BigDecimal repCommission = new BigDecimal(0);
    private BigDecimal managerCommission = new BigDecimal(0);

    public SOPSalesRepCommissionEnquiry() {
        this.setDataSource(true);
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

    public BigDecimal getDefaultCommission() {
        return defaultCommission;
    }

    public void setDefaultCommission(BigDecimal defaultCommission) {
        this.defaultCommission = defaultCommission;
    }

    public BigDecimal getManagerCommission() {
        return managerCommission;
    }

    public void setManagerCommission(BigDecimal managerCommission) {
        this.managerCommission = managerCommission;
    }

    public BigDecimal getRepCommission() {
        return repCommission;
    }

    public void setRepCommission(BigDecimal repCommission) {
        this.repCommission = repCommission;
    }

    public String getRepGroup() {
        return repGroup;
    }

    public void setRepGroup(String repGroup) {
        this.repGroup = repGroup;
    }

    public String getRepGroupDescription() {
        return repGroupDescription;
    }

    public void setRepGroupDescription(String repGroupDescription) {
        this.repGroupDescription = repGroupDescription;
    }

    public String getRepGroupManager() {
        return repGroupManager;
    }

    public void setRepGroupManager(String repGroupManager) {
        this.repGroupManager = repGroupManager;
    }

    public String getRepGroupManagerName() {
        return repGroupManagerName;
    }

    public void setRepGroupManagerName(String repGroupManagerName) {
        this.repGroupManagerName = repGroupManagerName;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getCustomerItemFieldDesc1() {
        return customerItemFieldDesc1;
    }

    public void setCustomerItemFieldDesc1(String customerItemFieldDesc1) {
        this.customerItemFieldDesc1 = customerItemFieldDesc1;
    }

    public String getCustomerItemFieldDesc2() {
        return customerItemFieldDesc2;
    }

    public void setCustomerItemFieldDesc2(String customerItemFieldDesc2) {
        this.customerItemFieldDesc2 = customerItemFieldDesc2;
    }

    public String getCustomerItemFieldDesc3() {
        return customerItemFieldDesc3;
    }

    public void setCustomerItemFieldDesc3(String customerItemFieldDesc3) {
        this.customerItemFieldDesc3 = customerItemFieldDesc3;
    }

    public String getCustomerItemFieldDesc4() {
        return customerItemFieldDesc4;
    }

    public void setCustomerItemFieldDesc4(String customerItemFieldDesc4) {
        this.customerItemFieldDesc4 = customerItemFieldDesc4;
    }

    public String getCustomerItemFieldDesc5() {
        return customerItemFieldDesc5;
    }

    public void setCustomerItemFieldDesc5(String customerItemFieldDesc5) {
        this.customerItemFieldDesc5 = customerItemFieldDesc5;
    }

    public String getCustomerItemFieldDesc6() {
        return customerItemFieldDesc6;
    }

    public void setCustomerItemFieldDesc6(String customerItemFieldDesc6) {
        this.customerItemFieldDesc6 = customerItemFieldDesc6;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repId", new RepIdDS());
        toBuild.put("repGroup", new RepGroupDS());
        toBuild.put("repGroupManager", new RepGroupManagerDS());
        toBuild.put("customerItemField1", new CustomerItemField1DS());
        toBuild.put("customerItemField2", new CustomerItemField2DS());
        toBuild.put("customerItemField3", new CustomerItemField3DS());
        toBuild.put("customerItemField4", new CustomerItemField4DS());
        toBuild.put("customerItemField5", new CustomerItemField5DS());
        toBuild.put("customerItemField6", new CustomerItemField6DS());
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepGroups.class);
        query.addTableAnd(SOPSalesRepCommission.class.getName(), "groupManager", SOPSalesRepGroups.class.getName(), "repId");
        query.addTableAnd(BaseEmployeeTable.class.getName(), "repId", SOPSalesRepCommission.class.getName(), "employeeNumber");
        query.clearFieldList();
        query.addField("repId", SOPSalesRepCommission.class.getName());
        query.addField("forenames", BaseEmployeeTable.class.getName());
        query.addField("surname", BaseEmployeeTable.class.getName());
        query.addField("repGroupId", SOPSalesRepGroups.class.getName());
        query.addField("description", SOPSalesRepGroups.class.getName());
        query.addField("groupManager", SOPSalesRepGroups.class.getName());
        query.addField("forenames", BaseEmployeeTable.class.getName());
        query.addField("surname", BaseEmployeeTable.class.getName());
        query.addField("customerItemField1", SOPSalesRepCommission.class.getName());
        query.addField("customerItemField2", SOPSalesRepCommission.class.getName());
        query.addField("customerItemField3", SOPSalesRepCommission.class.getName());
        query.addField("customerItemField4", SOPSalesRepCommission.class.getName());
        query.addField("customerItemField5", SOPSalesRepCommission.class.getName());
        query.addField("customerItemField6", SOPSalesRepCommission.class.getName());
        query.addField("groupManagerCommissionPerc", SOPSalesRepGroups.class.getName());
        query.addField("commissionPerc", SOPSalesRepCommission.class.getName());
        query.addField("groupManagerCommissionPerc", SOPSalesRepGroups.class.getName());
        return query;
    }
}
