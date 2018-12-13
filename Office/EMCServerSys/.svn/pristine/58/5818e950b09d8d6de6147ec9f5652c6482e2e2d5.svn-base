/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.stocktake.recount;

import emc.bus.base.reporttools.BaseReportUserQueryTableLocal;
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
import emc.functions.xml.EMCXMLHandler;
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
public class InventoryStocktakeRecountReportBean extends EMCReportBean implements InventoryStocktakeRecountReportLocal {

    @EJB
    private InventoryStockTakeCaptureDSLocal stockTakeCaptureBean;
    @EJB
    private BaseReportUserQueryTableLocal reportParametersBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCReportConfig reportConfig, EMCUserData userData) {
        Map<String, Object> reportParameters = reportConfig.getParameters();
        Boolean splitPerLocation = null;
        if (reportParameters != null) {
            splitPerLocation = (Boolean) reportParameters.get("splitPerLocation");
        } else {
            List param = reportParametersBean.getLastQueryForUser(reportConfig.getReportId(), userData);
            if (param != null && param.size() > 2) {
                String paramString = (String) param.get(2);
                if (!isBlank(paramString)) {
                    EMCXMLHandler xml = new EMCXMLHandler();
                    reportParameters = (Map<String, Object>) xml.XMLToObject(paramString, userData);
                    splitPerLocation = (Boolean) reportParameters.get("splitPerLocation");
                }
            }
        }
        if (splitPerLocation == null) {
            splitPerLocation = true;
        }
        userData.setUserData(2, true);
        userData.setUserData(5, splitPerLocation);

        return new ArrayList<Object>(stockTakeCaptureBean.populateDataSourceFields(super.getReportResult(parameters, userData), userData));
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
