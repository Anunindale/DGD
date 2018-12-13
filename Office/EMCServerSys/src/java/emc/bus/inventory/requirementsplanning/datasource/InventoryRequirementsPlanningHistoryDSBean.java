/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningHistoryDS;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningReferenceTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryRequirementsPlanningHistoryDSBean extends EMCDataSourceBean implements InventoryRequirementsPlanningHistoryDSLocal {

    @EJB
    private InventoryReferenceLocal itemRefernceBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    public InventoryRequirementsPlanningHistoryDSBean() {
        this.setDataSourceClassName(InventoryRequirementsPlanningHistoryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryRequirementsPlanningHistoryDS ds = (InventoryRequirementsPlanningHistoryDS) dataSourceInstance;
        itemRefernceBean.populateDSFields(ds, userData);
        dimensionBean.populateDimensions(ds, userData);
        EMCQuery query;

        RequirementsPlanningReferenceTypes refType = RequirementsPlanningReferenceTypes.fromId(ds.getReferenceRecordType());
        //Demand
        if (refType == RequirementsPlanningReferenceTypes.SALES_ORDER) {
            if (!isBlank(ds.getReferenceRecordRef())) {
                ds.setSalesOrderId(ds.getReferenceRecordRef());
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
                query.addAnd("recordID", ds.getReferenceRecordID());
                SOPSalesOrderMaster so = (SOPSalesOrderMaster) util.executeSingleResultQuery(query, userData);
                if (so != null) {
                    ds.setSalesOrderId(so.getSalesOrderNo());
                }
            }
            ds.setDemandId(ds.getSalesOrderId());
            ds.setDemandType("SO");
        } else {
            if (refType == RequirementsPlanningReferenceTypes.FORECAST) {
                if (!isBlank(ds.getReferenceRecordRef())) {
                    ds.setForecastId(ds.getReferenceRecordRef());
                } 
                ds.setDemandId(ds.getForecastId());
                ds.setDemandType("DM");
            }
        }
        //Supply
        if (refType == RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER) {
            if (!isBlank(ds.getReferenceRecordRef())) {
                ds.setPlannedPurchaseOrderId(ds.getReferenceRecordRef());
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
                query.addAnd("recordID", ds.getReferenceRecordID());
                POPPlannedPurchaseOrders ppo = (POPPlannedPurchaseOrders) util.executeSingleResultQuery(query, userData);
                if (ppo != null) {
                    ds.setPlannedPurchaseOrderId(ppo.getPlannedPOId());
                }
            }
            ds.setSupplyId(ds.getPlannedPurchaseOrderId());
            ds.setSupplyType("PPO");
        } else {
            if (refType == RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER) {
                if (!isBlank(ds.getReferenceRecordRef())) {
                    ds.setPlannedWorksOrderId(ds.getReferenceRecordRef());
                } 
                ds.setSupplyId(ds.getPlannedWorksOrderId());
                ds.setSupplyType("PWO");
            } else {
                if (refType == RequirementsPlanningReferenceTypes.PURCHASE_ORDER) {
                    if (!isBlank(ds.getReferenceRecordRef())) {
                        ds.setPurchaseOrderId(ds.getReferenceRecordRef());
                    } else {
                        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
                        query.addAnd("recordID", ds.getRecordID());
                        POPPurchaseOrderMaster po = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
                        if (po != null) {
                            ds.setPurchaseOrderId(po.getPurchaseOrderId());
                        }
                    }
                    ds.setSupplyId(ds.getPurchaseOrderId());
                    ds.setSupplyType("PO");
                } else {
                    if (refType == RequirementsPlanningReferenceTypes.WORKS_ORDER) {
                        if (!isBlank(ds.getReferenceRecordRef())) {
                            ds.setWorksOrdewrId(ds.getReferenceRecordRef());
                        }
                        ds.setSupplyId(ds.getWorksOrdewrId());
                        ds.setSupplyType("WO");
                    }
                }
            }
        }
        return ds;
    }
}
