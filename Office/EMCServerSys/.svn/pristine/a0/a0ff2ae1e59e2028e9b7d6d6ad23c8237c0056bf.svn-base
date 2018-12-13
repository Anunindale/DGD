/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.creditheldmaster;

import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.datasource.DebtorsCreditHeldMasterDS;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsCreditHeldMasterDSBean extends EMCDataSourceBean implements DebtorsCreditHeldMasterDSLocal {

    @EJB
    private SOPCustomersLocal customerBean;

    /** Creates a new instance of CreditHeldMasterDSBean. */
    public DebtorsCreditHeldMasterDSBean() {
        this.setDataSourceClassName(DebtorsCreditHeldMasterDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsCreditHeldMasterDS ds = (DebtorsCreditHeldMasterDS) dataSourceInstance;

        SOPCustomers customer = customerBean.findCustomer(ds.getCustomerId(), userData);

        if (customer != null) {
            ds.setCustomerName(customer.getCustomerName());
        }

        ds.setTotalCreditHeld(getTotalHeldValue(ds, userData));
        ds.setRep(getRep(ds, userData));
        
        return ds;
    }

    private BigDecimal getTotalHeldValue(DebtorsCreditHeldMasterDS ds, EMCUserData userData) {
        DebtorsCreditHeldRefType refType = DebtorsCreditHeldRefType.fromString(ds.getReferenceType());

        if (refType == DebtorsCreditHeldRefType.INVOICE) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceLines.class);
            query.addFieldAggregateFunctionArithmetic("totalCost", DebtorsCustomerInvoiceLines.class, "vatAmount", DebtorsCustomerInvoiceLines.class, "SUM", '+');
            query.addAnd("invCNNumber", ds.getReference());
            //Show both held and approved credit
            query.addAnd("creditHeldStatus", null, EMCQueryConditions.NOT);

            BigDecimal ret = (BigDecimal) util.executeSingleResultQuery(query, userData);

            return ret == null ? BigDecimal.ZERO : ret;
        } else if (refType == DebtorsCreditHeldRefType.SALES_ORDER) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
            query.addFieldAggregateFunctionArithmetic("lineTotal", SOPSalesOrderLines.class, "vatAmount", SOPSalesOrderLines.class, "SUM", '+');
            query.addAnd("salesOrderNo", ds.getReference());
            //Show both held and approved credit
            query.addAnd("creditHeldStatus", null, EMCQueryConditions.NOT);

            BigDecimal ret = (BigDecimal) util.executeSingleResultQuery(query, userData);

            return ret == null ? BigDecimal.ZERO : ret;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Fetches the rep for the specified Credit Held record.  This will query either
     * invocies or sales orders.
     * @param ds DS for which a rep should be fetched.
     * @param userData User data.
     * @return The rep for the specified Credit Held record.
     */
    private String getRep(DebtorsCreditHeldMasterDS ds, EMCUserData userData) {
        DebtorsCreditHeldRefType refType = DebtorsCreditHeldRefType.fromString(ds.getReferenceType());

        EMCQuery query = null;

        if (refType == DebtorsCreditHeldRefType.INVOICE) {
            query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCustomerInvoiceMaster.class);
            query.addAnd("invCNNumber", ds.getReference());
            query.addField("salesRep");
        } else if (refType == DebtorsCreditHeldRefType.SALES_ORDER) {
            query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addAnd("salesOrderNo", ds.getReference());
            query.addField("salesRep");
        }

        if (query != null) {
            return (String)util.executeSingleResultQuery(query, userData);
        } else {
            return null;
        }
    }
}
