/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stocktake.variance;

import emc.bus.inventory.journals.datasource.InventoryStockTakeCaptureDSLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import emc.reporttools.EMCReportDimensionSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryStocktakeVarianceReportBean extends EMCReportBean implements InventoryStocktakeVarianceReportLocal {

    @EJB
    private InventoryStockTakeCaptureDSLocal stockTakeCaptureBean;

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        //Variance
        userData.setUserData(3, true);
        //Variance Type
        String varianceType = null;
        Map<String, Object> paramMap = reportConfig.getParameters();
        if (paramMap != null) {
            varianceType = (String) paramMap.get("reportType");
        }
        userData.setUserData(4, varianceType);
        return new ArrayList<Object>(stockTakeCaptureBean.populateDataSourceFields(super.getReportResult(queryList, userData), userData));
    }

    @Override
    public List<Object> executeNativeQuery(EMCQuery sqlQuery, EMCReportDimensionSetup setup, EMCUserData userData) {
        sqlQuery.addOrderBy("warehouse", InventoryJournalLines.class.getName());
        sqlQuery.addOrderBy("location", InventoryStocktakeRegister.class.getName());
        sqlQuery.addLeftOuterJoin(InventoryJournalLines.class, "itemId", InventoryItemMaster.class, "itemId");
        sqlQuery.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        sqlQuery.addLeftOuterJoin(InventoryJournalLines.class, "dimension1", InventoryDimension1.class, "dimensionId");
        sqlQuery.addOrderBy("sortCode", InventoryDimension1.class.getName());
        sqlQuery.addLeftOuterJoin(InventoryJournalLines.class, "dimension3", InventoryDimension3.class, "dimensionId");
        sqlQuery.addOrderBy("sortCode", InventoryDimension3.class.getName());
        sqlQuery.addLeftOuterJoin(InventoryJournalLines.class, "dimension2", InventoryDimension2.class, "dimensionId");
        sqlQuery.addOrderBy("sortCode", InventoryDimension2.class.getName());
        sqlQuery.addOrderBy("batch", InventoryStocktakeRegister.class.getName());
        sqlQuery.addOrderBy("serial", InventoryStocktakeRegister.class.getName());
        return util.executeNativeQuery(sqlQuery, userData);
    }
}
