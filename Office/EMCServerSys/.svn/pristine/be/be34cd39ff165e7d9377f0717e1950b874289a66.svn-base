/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.orderschedule.byitem;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.salesorders.SalesOrderStatus;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class SOPOrderScheduleByItemReportBean extends EMCReportBean implements SOPOrderScheduleByItemReportLocal {

    /** Creates a new instance of SOPOrderScheduleByItemReportBean. */
    public SOPOrderScheduleByItemReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.INVOICED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);
        query.addAnd("salsesOrderStatus", SalesOrderStatus.CANCELLED.toString(), SOPSalesOrderMaster.class.getName(), EMCQueryConditions.NOT);

        //Get all dimension 2 labels to show on the report.
        EMCQuery dim2NestedQuery = query.copyQuery();
        dim2NestedQuery.addField("dimension2", SOPSalesOrderLines.class.getName());
        dim2NestedQuery.addGroupBy(SOPSalesOrderLines.class.getName(), "dimension2");

        EMCQuery dim2Query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2.class.getName());
        dim2Query.addField("dimensionId");
        dim2Query.addAnd("dimensionId", dim2NestedQuery, EMCQueryConditions.IN);
        dim2Query.addOrderBy("sortCode");
        List<String> sortedDim2Labels = (List<String>) util.executeGeneralSelectQuery(dim2Query, userData);

        userData.setUserData(3, sortedDim2Labels);
        
        //Only select the fields that we want.
        query.addField("salesOrderNo", SOPSalesOrderMaster.class.getName());    //0
        query.addField("customerNo", SOPSalesOrderMaster.class.getName());  //1
        query.addField("itemReference", InventoryItemMaster.class.getName());   //2
        query.addField("dimension1", SOPSalesOrderLines.class.getName());   //3
        query.addField("dimension2", SOPSalesOrderLines.class.getName());   //4
        query.addField("quantity", SOPSalesOrderLines.class.getName()); //5
        query.addField("lineTotal", SOPSalesOrderLines.class.getName()); //6
        query.addField("requiredDate", SOPSalesOrderMaster.class.getName());    //7
        query.addField("customerName", SOPCustomers.class.getName());    //8
        query.addField("description", InventoryItemMaster.class.getName()); //9
        query.addField("dimension3", SOPSalesOrderLines.class.getName());   //10

        return super.getReportResult(queryList, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        //Get sorted dimension 2 labels to show on the report.
        List<String> sortedDim2Labels = (List<String>) userData.getUserData(3);

        //Keep track of all the records which have been populated.
        Map<Integer, SOPOrderScheduleByItemReportDS> dsMap = new HashMap<Integer, SOPOrderScheduleByItemReportDS>();
        //Keep track of the last record populated - reduce number of 'get' calls to Map.
        int lastKey = 0;
        SOPOrderScheduleByItemReportDS lastDS = null;

        List<Object> ret = new ArrayList<Object>();

        for (Object ob : queryResult) {
            Object[] result = (Object[]) ob;

            String salesOrderNo = (String) result[0];
            String customerNo = (String) result[1];
            String itemRef = (String) result[2];
            String dimension1 = (String) result[3];
            String dimension2 = (String) result[4];
            BigDecimal quantity = (BigDecimal) result[5];
            BigDecimal lineTotal = (BigDecimal) result[6];
            Date requiredDate = (Date) result[7];
            String customerName = (String)result[8];
            String itemDescription = (String)result[9];
            String dimension3 = (String)result[10];

            StringBuilder keySB = new StringBuilder(salesOrderNo);
            keySB.append(itemRef);
            keySB.append(dimension1);
            keySB.append(dimension3);

            String keyString = keySB.toString();

            SOPOrderScheduleByItemReportDS ds = null;
            //Prevent unnecessary fetching from the Map.
            if (keyString.hashCode() != lastKey) {
                //Check whether record is in the Map.
                ds = dsMap.get(keyString.hashCode());
                if (ds == null) {
                    ds = new SOPOrderScheduleByItemReportDS();
                    ds.setCustomerId(customerNo);
                    ds.setCustomerName(customerName);
                    ds.setDeliveryDate(Functions.date2String(requiredDate));
                    ds.setDimension1(dimension1);
                    ds.setDimension3(dimension3);
                    ds.setItemReference(itemRef);
                    ds.setItemDescription(itemDescription);
                    ds.setSalesOrderNo(salesOrderNo);

                    if (sortedDim2Labels.size() > 0) {
                        ds.setDimension2Label1(sortedDim2Labels.get(0));
                    }
                    if (sortedDim2Labels.size() > 1) {
                        ds.setDimension2Label2(sortedDim2Labels.get(1));
                    }
                    if (sortedDim2Labels.size() > 2) {
                        ds.setDimension2Label3(sortedDim2Labels.get(2));
                    }
                    if (sortedDim2Labels.size() > 3) {
                        ds.setDimension2Label4(sortedDim2Labels.get(3));
                    }
                    if (sortedDim2Labels.size() > 4) {
                        ds.setDimension2Label5(sortedDim2Labels.get(4));
                    }
                    if (sortedDim2Labels.size() > 5) {
                        ds.setDimension2Label6(sortedDim2Labels.get(5));
                    }
                    if (sortedDim2Labels.size() > 6) {
                        ds.setDimension2Label7(sortedDim2Labels.get(6));
                    }
                    if (sortedDim2Labels.size() > 7) {
                        ds.setDimension2Label8(sortedDim2Labels.get(7));
                    }

                    dsMap.put(keyString.hashCode(), ds);
                    lastDS = ds;
                    lastKey = keyString.hashCode();

                    ret.add(ds);
                }
            } else {
                //Same DS as last iteration
                ds = lastDS;
            }

            //Get index of dimension 2 in sorted dimensions list to determine
            //which column should be populated.
            int dimension2Index = sortedDim2Labels.indexOf(dimension2);

            switch (dimension2Index) {
                case 0:
                    ds.setDimension2Total1(ds.getDimension2Total1().add(quantity));
                    break;
                case 1:
                    ds.setDimension2Total2(ds.getDimension2Total2().add(quantity));
                    break;
                case 2:
                    ds.setDimension2Total3(ds.getDimension2Total3().add(quantity));
                    break;
                case 3:
                    ds.setDimension2Total4(ds.getDimension2Total4().add(quantity));
                    break;
                case 4:
                    ds.setDimension2Total5(ds.getDimension2Total5().add(quantity));
                    break;
                case 5:
                    ds.setDimension2Total6(ds.getDimension2Total6().add(quantity));
                    break;
                case 6:
                    ds.setDimension2Total7(ds.getDimension2Total7().add(quantity));
                    break;
                case 7:
                    ds.setDimension2Total8(ds.getDimension2Total8().add(quantity));
                    break;
            }
            ds.setTotal(ds.getTotal().add(quantity));
            ds.setTotalValue(ds.getTotalValue().add(util.roundBigDecimal(lineTotal)));
        }

        return ret;
    }
}
