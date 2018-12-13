/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.sop.stockavailableforsale;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.sop.SOPParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.math.BigDecimal;
import java.sql.Connection;
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
public class SOPStockAvailableForSaleBean extends EMCReportBean implements SOPStockAvailableForSaleLocal {

    @EJB
    private SOPPriceArangementsLocal priceBean;
    @EJB
    private InventoryItemMasterLocal itemBean;
    @EJB
    private SOPParametersLocal sopParamBean;
    @EJB
    private InventoryDimension1Local dim1Bean;
    @EJB
    private InventoryDimension3Local dim3Bean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        EMCQuery query = (EMCQuery) queryList.get(1);

        query.addLeftOuterJoin(InventorySummary.class, "dimension1", InventoryDimension1.class, "dimensionId");
        query.addLeftOuterJoin(InventorySummary.class, "dimension2", InventoryDimension2.class, "dimensionId");
        query.addLeftOuterJoin(InventorySummary.class, "dimension3", InventoryDimension3.class, "dimensionId");

        query.addField("itemId", InventorySummary.class.getName());//0
        query.addField("itemReference", InventoryItemMaster.class.getName());//1
        query.addField("description", InventoryItemMaster.class.getName());//2
        query.addField("dimension1", InventorySummary.class.getName());//3
        query.addField("description", InventoryDimension1.class.getName());//4
        query.addField("dimension2", InventorySummary.class.getName());//5
        query.addField("dimension3", InventorySummary.class.getName());//6
        query.addField("description", InventoryDimension3.class.getName());//7
        query.addField("serialNo", InventorySummary.class.getName());//8
        query.addFieldAggregateFunction("physicalAvailable", InventorySummary.class.getName(), "SUM");//9
        query.addFieldAggregateFunction("orderedOut", InventorySummary.class.getName(), "SUM");//10

        query.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());

        query.addGroupBy(InventorySummary.class.getName(), "itemId");
        query.addGroupBy(InventorySummary.class.getName(), "dimension1");
        query.addGroupBy(InventorySummary.class.getName(), "dimension2");
        query.addGroupBy(InventorySummary.class.getName(), "dimension3");
        //query.addGroupBy(InventorySummary.class.getName(), "serialNo");

        List<Object[]> dataList = util.executeNativeQuery(query, userData);

        List reportData = new ArrayList();

        if (!dataList.isEmpty()) {
            SOPStockAvailableForSaleDS ds;

            BigDecimal available;
            BigDecimal onOrder;


            SOPParameters sopParam = sopParamBean.getParameterRecord(userData);
            Date today = Functions.nowDate();
            Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
            InventoryItemMaster item;
            Map<String, InventoryCostingGroup> costingMap = new HashMap<String, InventoryCostingGroup>();
            InventoryCostingGroup costingGroup;
            Map<String, String> dimDescMap = new HashMap<String, String>();
            String dimDesc;
            Connection conn = null;

            try {

                conn = util.connectToDatabase(userData);
                for (Object[] data : dataList) {
                    available = util.getBigDecimal((Double) data[9]);
                    onOrder = util.getBigDecimal((Double) data[10]);
                    //Remove Zero and less
                    if (available.subtract(onOrder).compareTo(BigDecimal.ZERO) > 0) {
                        ds = new SOPStockAvailableForSaleDS();
                        ds.setCompanyId(userData.getCompanyId());
                        ds.setItemId((String) data[0]);
                        ds.setItemReference((String) data[1]);
                        ds.setItemDescription((String) data[2]);
                        ds.setDimension1((String) data[3]);
                        if (!isBlank(ds.getDimension1())) {
                            dimDesc = dimDescMap.get(ds.getDimension1());
                            if (dimDesc == null) {
                                dimDesc = dim1Bean.findDimensionDescription(ds.getDimension1(), userData);
                                dimDescMap.put(dimDesc, ds.getDimension1());
                            }
                            ds.setDimensionDescription(dimDesc);
                        }
                        ds.setDimension2((String) data[5]);
                        ds.setDimension3((String) data[6]);
                        if (!isBlank(ds.getDimension3())) {
                            dimDesc = dimDescMap.get(ds.getDimension3());
                            if (dimDesc == null) {
                                dimDesc = dim3Bean.findDimensionDescription(ds.getDimension3(), userData);
                                dimDescMap.put(dimDesc, ds.getDimension3());
                            }
                            ds.setDimensionDescription(dimDesc);
                        }
                        //ds.setSerialNo((String) data[8]);

                        available = util.getBigDecimal((Double) data[9]);
                        onOrder = util.getBigDecimal((Double) data[10]);

                        ds.setOutstandingQuantity(ds.getOutstandingQuantity().add(onOrder));
                        ds.setAvailableQuantity(ds.getAvailableQuantity().add(available));
                        ds.setBalanceQuantity(ds.getAvailableQuantity().subtract(ds.getOutstandingQuantity()));


                        item = itemMap.get((String) data[0]);
                        if (item == null) {
                            item = itemBean.findItem((String) data[0], userData);
                            itemMap.put((String) data[0], item);
                        }

                        costingGroup = costingMap.get(item.getCostingGroup());
                        if (costingGroup == null) {
                            query = new EMCQuery(enumQueryTypes.SELECT, InventoryCostingGroup.class);
                            query.addAnd("costingGroupId", item.getCostingGroup());
                            costingGroup = (InventoryCostingGroup) util.executeSingleResultQuery(query, userData);
                            costingMap.put(item.getCostingGroup(), costingGroup);
                        }

                        ds.setPricePerUnit(priceBean.findItemPrice(conn, item, null, costingGroup, null, sopParam, "1", null, (String) data[0],
                                (String) data[3], (String) data[5], (String) data[6], today, BigDecimal.ZERO, userData));

                        reportData.add(ds);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    util.closeConnectionToDB(conn, userData);
                }
            }
        }

        return reportData;
    }
}
