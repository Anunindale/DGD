/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.customerbysalesrep;

import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author ivan
 */
@Stateless
public class SOPCustomerBySalesRepBean extends EMCReportBean implements SOPCustomerBySalesRepInterface {

    private Helper help;

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("repId", SOPSalesRepCommission.class.getName());//0
        query.addField("customerItemField1", SOPSalesRepCommission.class.getName());//1

        query.addGroupBy("repId");
        query.addGroupBy("customerItemField1");

        List<Object> q2l = new ArrayList<Object>();
        List<String> v1 = new ArrayList<String>();
        List<String> v2 = new ArrayList<String>();
        List<Object> q1l = (List<Object>) util.executeGeneralSelectQuery(query, userData);
        List<Object> allLists = new ArrayList<Object>(q1l);
        TreeMap<String, List<String>> repMap = new TreeMap<String, List<String>>();


        for (Object data : q1l) {
            Object[] values = (Object[]) data;
            if (repMap.get((String) values[0]) == null) {
                List<String> temp = new ArrayList<String>();
                temp.add((String) values[1]);
                repMap.put((String) values[0], temp);
                help = new Helper(repMap);
            } else {
                List<String> temp = repMap.get((String) values[0]);
                temp.add((String) values[1]);
                repMap.put((String) values[0], temp);
                help = new Helper(repMap);
            }
        }
        v1 = query.getFieldValue(SOPSalesRepCommission.class.getName(), "repId", EMCQueryFieldTypes.STRING, false);
        v2 = query.getFieldValue(SOPSalesRepCommission.class.getName(), "customerItemField1", EMCQueryFieldTypes.STRING, false);

        EMCQuery Q2 = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        Q2.addAnd("repService", "Single", EMCQueryConditions.EQUALS);
        Q2.addField("salesRep");
        Q2.addField("customerId");
        if (!v1.isEmpty() && !isBlank(v1.get(0))) {
            Q2.addAnd("salesRep", v1.get(0));
        }
        if (!v2.isEmpty() && !isBlank(v2.get(0))) {
            Q2.addAnd("customerId", v2.get(0));
        }
        q2l = (List<Object>) util.executeGeneralSelectQuery(Q2, userData);

        for (Object x : q2l) {
            Object[] results = (Object[]) x;
            if (repMap.get((String) results[0]) == null) {
                List<String> temp = new ArrayList<String>();
                temp.add((String) results[1]);
                repMap.put((String) results[0], temp);
                help = new Helper(repMap);
            } else {
                List<String> temp = repMap.get((String) results[0]);
                temp.add((String) results[1]);
                repMap.put((String) results[0], temp);
                help = new Helper(repMap);
            }
        }
        return query;
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List<Object> resultList = new ArrayList<Object>();
        List<String> deliveryAddressList;
        List<String> invoiceAddressList;
        List<String> shipToAddressList;
        List<String> customersWritten = new ArrayList<String>();
        for (String key : help.getMap().keySet()) {

            for (String customer : help.getMap().get(key)) {
                SOPCustomerBySalesRepDataSource ds = new SOPCustomerBySalesRepDataSource();
                EMCQuery newQ = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                newQ.addAnd("customerId", customer);
                newQ.addOrderBy("customerId");
                List<SOPCustomers> cus2 = util.executeGeneralSelectQuery(newQ, userData);

                for (SOPCustomers cus : cus2) {
                    if (customersWritten.contains(cus.getCustomerId())) {
                        continue;
                    } else {

                        //Head Office information
                        customersWritten.add(cus.getCustomerId());
                        deliveryAddressList = concertinaAddress(cus.getPhysicalAddresLine1(), cus.getPhysicalAddresLine2(), cus.getPhysicalAddresLine3(), cus.getPhysicalAddresLine4(), cus.getPhysicalAddresLine5(), cus.getPhysicalPostalCode());
                        invoiceAddressList = concertinaAddress(cus.getPostalAddresLine1(), cus.getPostalAddresLine2(), cus.getPostalAddresLine3(), cus.getPostalAddresLine4(), cus.getPostalAddresLine5(), cus.getPostalPostalCode());


                        ds.setCustomerId(cus.getCustomerId());
                        ds.setDetailType(cus.getInvoiceToCustomer());
                        ds.setCustName(cus.getCustomerName());
                        ds.setCustComp(cus.getCustomerComapny());
                        ds.setTelNo(cus.getTelephoneNumber());
                        ds.setDeliveryAddresLine1(deliveryAddressList.get(0));
                        ds.setDeliveryAddresLine2(deliveryAddressList.get(1));
                        ds.setDeliveryAddresLine3(deliveryAddressList.get(2));
                        ds.setDeliveryAddresLine4(deliveryAddressList.get(3));
                        ds.setDeliveryAddresLine5(deliveryAddressList.get(4));
                        ds.setDeliveryPostalCode(deliveryAddressList.get(5));
                        ds.setInvoiceAddresLine1(invoiceAddressList.get(0));
                        ds.setInvoiceAddresLine2(invoiceAddressList.get(1));
                        ds.setInvoiceAddresLine3(invoiceAddressList.get(2));
                        ds.setInvoiceAddresLine4(invoiceAddressList.get(3));
                        ds.setInvoiceAddresLine5(invoiceAddressList.get(4));
                        ds.setInvoicePostalCode(invoiceAddressList.get(5));
                        ds.setOrderContactName(isBlank(cus.getOrderContactName()) ? "" : cus.getOrderContactName());
                        ds.setOrderContactTelephoneNumber(isBlank(cus.getOrderContactTelephoneNumber()) ? "" : cus.getOrderContactTelephoneNumber());
                        ds.setOrderContactCellNumber(isBlank(cus.getOrderContactCellNumber()) ? "" : cus.getOrderContactCellNumber());
                        ds.setOrderContactEmail(isBlank(cus.getOrderContactEmail()) ? "" : cus.getOrderContactEmail());
                        ds.setAccountContactName(isBlank(cus.getAccountContactName()) ? "" : cus.getAccountContactName());
                        ds.setAccountContactTelephoneNumber(isBlank(cus.getAccountContactTelephoneNumber()) ? "" : cus.getAccountContactTelephoneNumber());
                        ds.setAccountContactCellNumber(isBlank(cus.getAccountContactCellNumber()) ? "" : cus.getAccountContactCellNumber());
                        ds.setAccountContactEmail(isBlank(cus.getAccountContactEmail()) ? "" : cus.getAccountContactEmail());
                        ds.setDeliveryBeforeDate((Integer) cus.getDeliverBeforeDate() == null ? 0 : cus.getDeliverBeforeDate());
                        ds.setDeliveryCharges(cus.isDeliveryCharge());
                        ds.setBookingRequired(cus.isDeliveryBookingRequired());
                        ds.setDayOfWeek(isBlank(cus.getDeliveryDayOfWeek()) ? "" : cus.getDeliveryDayOfWeek());
                        ds.setDeliveryLTime((Integer) cus.getDeliveryLeadTime() == null ? 0 : cus.getDeliveryLeadTime());
                        ds.setDeliveryMthd(isBlank(cus.getDeliveryMethod()) ? "" : cus.getDeliveryMethod());
                        ds.setMrktGroup(isBlank(cus.getMarketingGroup()) ? "" : cus.getMarketingGroup());
                        ds.setRating(isBlank(cus.getCreditRating()) ? "" : cus.getCreditRating());
                        ds.setLimit((Double) cus.getCreaditLimit() == null ? 0 : (Double) cus.getCreaditLimit());
                        ds.setSettlementDisc(isBlank(cus.getSettlementDiscount()) ? "" : cus.getSettlementDiscount());
                        ds.setTerms(isBlank(cus.getTermsOfPayment()) ? "" : cus.getTermsOfPayment());
                        ds.setDebtorsGroup(isBlank(cus.getCustomerGroup()) ? "" : cus.getCustomerGroup());
                        ds.setTrandingAsName(isBlank(cus.getTrandingAs()) ? "" : cus.getTrandingAs());
                        ds.setCompanyRegistrationNo(isBlank(cus.getCompanyRegistrationNumber()) ? "" : cus.getCompanyRegistrationNumber());
                        ds.setVatNo(isBlank(cus.getVatRegistration()) ? "" : cus.getVatRegistration());

                        String yesNo = "No";
                        String shipTo = cus.getCustomerId();
                        String invoiceTo = cus.getInvoiceToCustomer();
                        if (shipTo.equals(invoiceTo)) {
                            yesNo = "Yes";
                        }
                        ds.setInvoiceCustomerYesNo(yesNo);


                        if (cus.getRepService().equals("Multiple")) {
                            EMCQuery repQ = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                            repQ.addAnd("customerItemField1", cus.getInvoiceToCustomer());
                            repQ.addField("repId");
                            repQ.addGroupBy("repId");
                            List<String> repObject = (List<String>) util.executeGeneralSelectQuery(repQ, userData);
                            String repName;
                            ds.setRep("");
                            for (int r = 0; r < repObject.size(); r++) {
                                repName = repObject.get(r);

                                ds.setRep(ds.getRep() + repName);
                                if (r < repObject.size() - 1) {
                                    ds.setRep(ds.getRep() + ", ");
                                }
                            }
                        } else {
                            ds.setRep(cus.getSalesRep());

                        }
                        ds.setPrint(true);
                        EMCQuery queryS = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                        queryS.addAnd("invoiceToCustomer", cus.getInvoiceToCustomer());
                        queryS.addOrderBy("customerId");
                        List<SOPCustomers> cus3 = (List<SOPCustomers>) util.executeGeneralSelectQuery(queryS, userData);

                        if (cus3.isEmpty()) {
                            resultList.add(ds);
                        } else {
                            //Branch Information
                            for (int i = 1; i < cus3.size(); i++) {
                                SOPCustomerBySalesRepDataSource temp = (SOPCustomerBySalesRepDataSource) util.doClone(ds, SOPCustomerBySalesRepDataSource.class, userData);

                                customersWritten.add(cus3.get(i).getCustomerId());
                                //build details lines
                                temp.setShipToNumber(cus3.get(i).getCustomerId());
                                shipToAddressList = concertinaAddress(cus3.get(i).getPhysicalAddresLine1(), cus3.get(i).getPhysicalAddresLine2(), cus3.get(i).getPhysicalAddresLine3(), cus3.get(i).getPhysicalAddresLine4(), cus3.get(i).getPhysicalAddresLine5(), cus3.get(i).getPhysicalPostalCode());
                                temp.setShipToDeliveryAddresLine1(shipToAddressList.get(0));
                                temp.setShipToDeliveryAddresLine2(shipToAddressList.get(1));
                                temp.setShipToDeliveryAddresLine3(shipToAddressList.get(2));
                                temp.setShipToDeliveryAddresLine4(shipToAddressList.get(3));
                                temp.setShipToDeliveryAddresLine5(shipToAddressList.get(4));
                                temp.setShipToDeliveryPostalCode(shipToAddressList.get(5));
                                temp.setShipToContactPerson(cus3.get(i).getOrderContactName());

                                if (cus3.get(i).getRepService().equals("Multiple")) {
                                    EMCQuery q2 = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                                    q2.addAnd("customerItemField1", cus3.get(i).getInvoiceToCustomer());
                                    q2.addField("repId");
                                    q2.addGroupBy("repId");
                                    List<String> repObject = (List<String>) util.executeGeneralSelectQuery(q2, userData);
                                    String repName;

                                    temp.setShipToSalesRep("");
                                    for (int r = 0; r < repObject.size(); r++) {
                                        repName = repObject.get(r);

                                        temp.setShipToSalesRep(temp.getShipToSalesRep() + repName);
                                        if (r < repObject.size() - 1) {
                                            temp.setShipToSalesRep(temp.getShipToSalesRep() + ", ");
                                        }
                                    }
                                } else {
                                    temp.setShipToSalesRep(cus3.get(i).getSalesRep());
                                }
                                resultList.add(temp);
                            }
                        }

                    }//else
                }//for loop
            }//map
        }//map
        return resultList;
    }
}
