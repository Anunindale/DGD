/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.pricevariance;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPPriceVarianceBean extends EMCReportBean implements POPPriceVarianceLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        List<Object> results = executeQuery(parameters.get(1).toString(), userData);
        return manipulateQueryResult(results, null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        POPPurchaseOrderMaster master;
        POPPurchaseOrderLines line;
        POPPriceVarianceDS ds;
        List lineResults;
        List dsList = new ArrayList();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
        query.addAnd("whoApproved", null, EMCQueryConditions.NOT);
        for (Object om : queryResult) {
            master = (POPPurchaseOrderMaster) om;
            query.removeAnd("purchaseOrderId");
            query.addAnd("purchaseOrderId", master.getPurchaseOrderId());
            lineResults = util.executeGeneralSelectQuery(query, userData);
            for (Object ol : lineResults) {
                ds = new POPPriceVarianceDS();
                line = (POPPurchaseOrderLines) ol;
                ds.setApprovedBy(line.getWhoApproved());
                ds.setDimension1(line.getItemDimension1());
                ds.setDimension2(line.getItemDimension2());
                ds.setDimension3(line.getItemDimension3());
                ds.setItemId(referenceBean.checkItemReference(line.getItemId(), userData)[1]);
                ds.setNewPrice(line.getItemPrice());
                ds.setOldPrice(line.getOldPrice());
                ds.setPurchaseOrderId(line.getPurchaseOrderId());
                ds.setSupplierId(master.getSupplier());
                ds.setUpdateItemMaster(line.getUpdateItem() == true ? "Yes" : "No");
                ds.setDateChanged(line.getDateApproved() == null ? null : Functions.date2String(line.getDateApproved()));
                ds.setReasonId(line.getReason());
                dsList.add(ds);
            }
        }
        return dsList;
    }
}
