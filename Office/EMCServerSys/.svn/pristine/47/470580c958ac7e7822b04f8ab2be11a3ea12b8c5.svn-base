/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.postlines;

import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.sop.SOPSalesOrderPostLines;
import emc.entity.sop.SOPSalesOrderPostMaster;
import emc.enums.enumQueryTypes;
import emc.enums.sop.SalesDeliveryRules;
import emc.enums.sop.SalesOrderTypes;
import emc.enums.sop.assemblytype.EnumAssemblyTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesOrderPostLinesBean extends EMCEntityBean implements SOPSalesOrderPostLinesLocal {

    @EJB
    private SOPParametersLocal paramBean;
    @EJB
    private SOPCustomersLocal customerBean;

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            SOPSalesOrderPostLines record = (SOPSalesOrderPostLines) vobject;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addTableAnd(SOPSalesOrderPostMaster.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderId");
            query.addAnd("postMasterId", record.getPostMasterId(), SOPSalesOrderPostMaster.class.getName());
            query.addField("salesOrderType", SOPSalesOrderMaster.class.getName());
            String soType = (String) util.executeSingleResultQuery(query, userData);
            
            query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addTableAnd(SOPSalesOrderPostMaster.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderId");
            query.addAnd("postMasterId", record.getPostMasterId(), SOPSalesOrderPostMaster.class.getName());
            query.addField("assemblyType", SOPSalesOrderMaster.class.getName());
            String assyType = (String) util.executeSingleResultQuery(query, userData);

            if (record.getMaxQuantity().compareTo(record.getPickQuantity().add(record.getAssembleQuantity()).add(record.getKimbleQuantity())) > 0) {
                boolean allowPartial = false;
                SOPParameters param = paramBean.getParameterRecord(userData);
                if (param == null) {
                    Logger.getLogger("emc").log(Level.SEVERE, "SOP Parameters are not set up.", userData);
                    valid = false;
                }

                if (soType == null) {
                    soType = SalesOrderTypes.SALES_ORDER.toString();
                }
                if (assyType == null) {
                    assyType = EnumAssemblyTypes.AFS.toString();
                }
                if (SalesOrderTypes.SALES_ORDER.equals(SalesOrderTypes.fromString(soType)) && !EnumAssemblyTypes.DIST.toString().equals(assyType)) {
                    //UD Pos 5 indicates back order
                    if (userData.getUserData(5) == Boolean.TRUE) {
                        allowPartial = true;
                    } else {
                        query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderPostMaster.class);
                        query.addAnd("postMasterId", record.getPostMasterId());
                        SOPSalesOrderPostMaster master = (SOPSalesOrderPostMaster) util.executeSingleResultQuery(query, userData);
                        SOPCustomers customer = customerBean.findCustomer(master.getCustomerId(), userData);

                        if (customer == null) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find customer.", userData);
                            valid = false;
                        } else {
                            if (SalesDeliveryRules.fromString(customer.getDeliveryRules()) == SalesDeliveryRules.STK &&
                                    userData.getUserData(4) == Boolean.TRUE) {      //true in position 4 of user data indicates that this is the Assemble/Reserve scenario.
                                //This only applies when stock is being assembled and not picked for STK orders.
                                allowPartial = param.isAllowPartialSTKAssembly();
                            } else {
                                allowPartial = param.isAllowPartialPost() && customer.isAllowPartialDelivery();
                            }
                        }
                    }
                } else {
                        allowPartial = param.isAllowPartialBOPost();
                }
                if (valid && !allowPartial) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Partial Posting is not allowed.", userData);
                    valid = false;
                }
            }
        }
        return valid;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);
        if (valid) {
            SOPSalesOrderPostLines record = (SOPSalesOrderPostLines) vobject;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
            query.addTableAnd(SOPSalesOrderPostMaster.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderId");
            query.addAnd("postMasterId", record.getPostMasterId(), SOPSalesOrderPostMaster.class.getName());
            query.addField("salesOrderType", SOPSalesOrderMaster.class.getName());

            String soType = (String) util.executeSingleResultQuery(query, userData);

            boolean allowPartial = false;
            SOPParameters param = paramBean.getParameterRecord(userData);
            if (param == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "SOP Parameters are not set up.", userData);
                valid = false;
            }

            if (soType == null) {
                soType = SalesOrderTypes.SALES_ORDER.toString();
            }
            if (SalesOrderTypes.SALES_ORDER.equals(SalesOrderTypes.fromString(soType))) {
                //UD Pos 5 indicates back order
                if (userData.getUserData(5) == Boolean.TRUE) {
                    allowPartial = true;
                } else {
                    query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderPostMaster.class);
                    query.addAnd("postMasterId", record.getPostMasterId());
                    SOPSalesOrderPostMaster master = (SOPSalesOrderPostMaster) util.executeSingleResultQuery(query, userData);
                    SOPCustomers customer = customerBean.findCustomer(master.getCustomerId(), userData);

                    if (customer == null) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to find customer.", userData);
                        valid = false;
                    } else {
                        if (SalesDeliveryRules.fromString(customer.getDeliveryRules()) == SalesDeliveryRules.STK &&
                                userData.getUserData(4) == Boolean.TRUE) {      //true in position 4 of user data indicates that this is the Assemble/Reserve scenario.
                            //This only applies when stock is being assembled and not picked for STK orders.
                            allowPartial = param.isAllowPartialSTKAssembly();
                        } else {
                            allowPartial = param.isAllowPartialPost() && customer.isAllowPartialDelivery();
                        }
                    }
                }
            } else {
                if (SalesOrderTypes.BLANKET_ORDER.equals(SalesOrderTypes.fromString(soType))) {
                    allowPartial = param.isAllowPartialBOPost();
                }
            }
            if (valid && !allowPartial) {
                Logger.getLogger("emc").log(Level.SEVERE, "Partial Posting is not allowed, you may not remove lines from the post form.", userData);
                valid = false;
            }
        }
        return valid;
    }
}
