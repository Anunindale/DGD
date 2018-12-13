/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesrepcommissionenquiry;

import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.datasource.SOPSalesRepCommissionEnquiry;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesRepCommissionEnquiryBean extends EMCEntityBean implements SOPSalesRepCommissionEnquiryLocal {

    @EJB
    private SOPParametersLocal paramBean;

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return super.getNumRows(theTable, userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        SOPSalesRepCommissionEnquiry enquiry;
        Object[] dataArray;
        List returnData = new ArrayList();
        Collection theData = super.getDataInRange(theTable, userData, start, end);
        if (!theData.isEmpty()) {
            SOPParameters param = paramBean.getParameterRecord(userData);
                        EMCQuery query;
                        String description;
            for (Object o : theData) {
                dataArray = (Object[]) o;
                enquiry = new SOPSalesRepCommissionEnquiry();
                enquiry.setRepId((String) dataArray[0]);
                enquiry.setRepName((String) dataArray[1] + " " + (String) dataArray[2]);
                enquiry.setRepGroup((String) dataArray[3]);
                enquiry.setRepGroupDescription((String) dataArray[4]);
                enquiry.setRepGroupManager((String) dataArray[5]);
                enquiry.setRepGroupManagerName((String) dataArray[6] + " " + (String) dataArray[7]);
                enquiry.setCustomerItemField1((String) dataArray[8]);
                enquiry.setCustomerItemField2((String) dataArray[9]);
                enquiry.setCustomerItemField3((String) dataArray[10]);
                enquiry.setCustomerItemField4((String) dataArray[11]);
                enquiry.setCustomerItemField5((String) dataArray[12]);
                enquiry.setCustomerItemField6((String) dataArray[13]);
                enquiry.setDefaultCommission((BigDecimal) dataArray[14]);
                enquiry.setRepCommission((BigDecimal) dataArray[15]);
                enquiry.setManagerCommission((BigDecimal) dataArray[16]);

                if (param != null) {
                    if (!isBlank(param.getCustomerItemFieldDesc1())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable1());
                        query.addAnd(param.getCustomerItemField1(), enquiry.getCustomerItemField1());
                        query.addField(param.getCustomerItemFieldDesc1());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc1(description);
                    }
                    if (!isBlank(param.getCustomerItemFieldDesc2())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable2());
                        query.addAnd(param.getCustomerItemField2(), enquiry.getCustomerItemField2());
                        query.addField(param.getCustomerItemFieldDesc2());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc2(description);
                    }
                    if (!isBlank(param.getCustomerItemFieldDesc3())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable3());
                        query.addAnd(param.getCustomerItemField3(), enquiry.getCustomerItemField3());
                        query.addField(param.getCustomerItemFieldDesc3());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc3(description);
                    }
                    if (!isBlank(param.getCustomerItemFieldDesc4())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable4());
                        query.addAnd(param.getCustomerItemField4(), enquiry.getCustomerItemField4());
                        query.addField(param.getCustomerItemFieldDesc4());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc4(description);
                    }
                    if (!isBlank(param.getCustomerItemFieldDesc5())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable5());
                        query.addAnd(param.getCustomerItemField5(), enquiry.getCustomerItemField5());
                        query.addField(param.getCustomerItemFieldDesc5());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc5(description);
                    }
                    if (!isBlank(param.getCustomerItemFieldDesc6())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, param.getCustomerItemTable6());
                        query.addAnd(param.getCustomerItemField6(), enquiry.getCustomerItemField6());
                        query.addField(param.getCustomerItemFieldDesc6());
                        description = (String) util.executeSingleResultQuery(query, userData);
                        enquiry.setCustomerItemFieldDesc6(description);
                    }
                }

                returnData.add(enquiry);
            }
        }
        return returnData;
    }
}
