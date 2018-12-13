/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.priceaudittrail.datasource;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.datasource.SOPPriceAuditTrailDS;
import emc.enums.enumQueryTypes;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPPriceAuditTrailDSBean extends EMCDataSourceBean implements SOPPriceAuditTrailDSLocal {

    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private SOPCustomersLocal customerBean;

    public SOPPriceAuditTrailDSBean() {
        this.setDataSourceClassName(SOPPriceAuditTrailDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPPriceAuditTrailDS ds = (SOPPriceAuditTrailDS) dataSourceInstance;
        ds.setUserName(employeeBean.findEmployeeNameAndSurname(employeeBean.findEmployee(ds.getUserId(), userData), userData));
        if (isBlank(ds.getUserName())) {
            ds.setUserName(ds.getUserId());
        }
        if (!isBlank(ds.getItemId())) {
            itemReferenceBean.populateDSFields(ds, userData);
        }
        if (!isBlank(ds.getCustomerId())) {
            SOPCustomers cust = customerBean.findCustomer(ds.getCustomerId(), userData);
            if (cust != null) {
                ds.setCutomerName(cust.getCustomerName());
            }
        }
        if (!isBlank(ds.getSourceRecordId())) {
            EMCQuery query;
            switch (SOPPriceAuditTrailType.fromString(ds.getRecordType())) {
                case PRICE_APPROVAL:
                case DISCOUNT_APPROVAL:
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
                    query.addAnd("recordID", ds.getSourceRecordId());
                    query.addField("salesOrderNo");
                    ds.setSourceReference((String) util.executeSingleResultQuery(query, userData));
                    break;
            }
        }
        return ds;
    }
}
