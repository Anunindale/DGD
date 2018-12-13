/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.priceaudittrail;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;

import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPPriceAuditTrailReportBean extends EMCReportBean implements SOPPriceAuditTrailReportLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        List<Object> reportData = new ArrayList<Object>();

        SOPPriceAuditTrailReportDS ds;

        Map<String, String> userNameMap = new HashMap<String, String>();
        String userName;

        List<String> recordTypes = new ArrayList<String>();

        if ((Integer) userData.getUserData(0) == 1) {
            recordTypes.add(SOPPriceAuditTrailType.PRICE_LIST_DELETE.toString());
            recordTypes.add(SOPPriceAuditTrailType.PRICE_LIST_INSERT.toString());
            recordTypes.add(SOPPriceAuditTrailType.PRICE_LIST_UPDATE.toString());
            query.addAndInList("recordType", SOPPriceAuditTrail.class.getName(), recordTypes, true, false);
            query.addField("recordType", SOPPriceAuditTrail.class.getName());
            query.addField("logDate", SOPPriceAuditTrail.class.getName());
            query.addField("priceGroup", SOPPriceAuditTrail.class.getName());
            query.addField("customerId", SOPCustomers.class.getName());
            query.addField("customerName", SOPCustomers.class.getName());
            query.addField("itemReference", InventoryItemMaster.class.getName());
            query.addField("description", InventoryItemMaster.class.getName());
            query.addField("dimension1", SOPPriceAuditTrail.class.getName());
            query.addField("dimension2", SOPPriceAuditTrail.class.getName());
            query.addField("dimension3", SOPPriceAuditTrail.class.getName());
            query.addField("quantity", SOPPriceAuditTrail.class.getName());
            query.addField("price", SOPPriceAuditTrail.class.getName());
            query.addField("updatedPrice", SOPPriceAuditTrail.class.getName());
            query.addField("userId", SOPPriceAuditTrail.class.getName());
            query.addField("fromDate", SOPPriceAuditTrail.class.getName());
            query.addField("toDate", SOPPriceAuditTrail.class.getName());

            List<Object[]> selectedData = util.executeNativeQuery(query, userData);

            for (Object[] data : selectedData) {
                ds = new SOPPriceAuditTrailReportDS();
                ds.setRecordType((String) data[0]);
                ds.setRecordDate(Functions.date2String((Date) data[1]));
                if (isBlank((String) data[2])) {
                    ds.setCustomerId((String) data[3]);
                    ds.setCustomerName((String) data[4]);
                } else {
                    ds.setCustomerId((String) data[2]);
                    ds.setCustomerName((String) data[4]);
                }
                ds.setItemId((String) data[5]);
                ds.setItemDescription((String) data[6]);
                if (isBlank((String) data[7])) {
                    ds.setDimension1((String) data[9]);
                } else {
                    ds.setDimension1((String) data[7]);
                }
                ds.setDimension2((String) data[8]);
                ds.setQuantity((BigDecimal) data[10]);
                ds.setOriginal((BigDecimal) data[11]);
                ds.setCurrent((BigDecimal) data[12]);

                userName = userNameMap.get((String) data[13]);
                if (isBlank(userName)) {
                    userName = employeeBean.findEmployeeNameAndSurname(employeeBean.findEmployee((String) data[13], userData), userData);
                    if (isBlank(userName)) {
                        userName = (String) data[13];
                    }
                    userNameMap.put((String) data[13], userName);
                }
                ds.setUserName((String) data[13]);

                if (!isBlank(data[14])) {
                    ds.setEffectiveFrom(Functions.date2String((Date) data[14]));
                }
                if (!isBlank(data[15])) {
                    ds.setEffectiveTo(Functions.date2String((Date) data[15]));
                }

                reportData.add(ds);
            }
        } else if ((Integer) userData.getUserData(0) == 2) {
            recordTypes.add(SOPPriceAuditTrailType.PRICE_APPROVAL.toString());
            recordTypes.add(SOPPriceAuditTrailType.DISCOUNT_APPROVAL.toString());
            query.addAndInList("recordType", SOPPriceAuditTrail.class.getName(), recordTypes, true, false);
            query.addField("recordType", SOPPriceAuditTrail.class.getName());
            query.addField("logDate", SOPPriceAuditTrail.class.getName());
            query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());
            query.addField("customerId", SOPCustomers.class.getName());
            query.addField("customerName", SOPCustomers.class.getName());
            query.addField("itemReference", InventoryItemMaster.class.getName());
            query.addField("description", InventoryItemMaster.class.getName());
            query.addField("dimension1", SOPPriceAuditTrail.class.getName());
            query.addField("dimension2", SOPPriceAuditTrail.class.getName());
            query.addField("dimension3", SOPPriceAuditTrail.class.getName());
            query.addField("quantity", SOPPriceAuditTrail.class.getName());
            query.addField("originalPrice", SOPPriceAuditTrail.class.getName());
            query.addField("price", SOPPriceAuditTrail.class.getName());
            query.addField("updatedPrice", SOPPriceAuditTrail.class.getName());
            query.addField("userId", SOPPriceAuditTrail.class.getName());
            query.addField("fromDate", SOPPriceAuditTrail.class.getName());
            query.addField("toDate", SOPPriceAuditTrail.class.getName());

            List<Object[]> selectedData = util.executeNativeQuery(query, userData);

            for (Object[] data : selectedData) {
                ds = new SOPPriceAuditTrailReportDS();
                ds.setRecordType((String) data[0]);
                ds.setRecordDate(Functions.date2String((Date) data[1]));
                ds.setSource((String) data[2]);
                ds.setCustomerId((String) data[3]);
                ds.setCustomerName((String) data[4]);
                ds.setItemId((String) data[5]);
                ds.setItemDescription((String) data[6]);
                ds.setDimension1((String) data[7]);
                ds.setDimension2((String) data[8]);
                ds.setDimension3((String) data[9]);
                ds.setQuantity((BigDecimal) data[10]);
                ds.setOriginal((BigDecimal) data[11]);
                ds.setCurrent((BigDecimal) data[12]);
                ds.setSOPrice((BigDecimal) data[13]);

                userName = userNameMap.get((String) data[14]);
                if (isBlank(userName)) {
                    userName = employeeBean.findEmployeeNameAndSurname(employeeBean.findEmployee((String) data[14], userData), userData);
                    if (isBlank(userName)) {
                        userName = (String) data[14];
                    }
                    userNameMap.put((String) data[14], userName);
                }
                ds.setUserName((String) data[14]);
                if (!isBlank(data[15])) {
                    ds.setEffectiveFrom(Functions.date2String((Date) data[15]));
                }
                if (!isBlank(data[16])) {
                    ds.setEffectiveTo(Functions.date2String((Date) data[16]));
                }

                reportData.add(ds);
            }
        }
        return reportData;
    }
}
