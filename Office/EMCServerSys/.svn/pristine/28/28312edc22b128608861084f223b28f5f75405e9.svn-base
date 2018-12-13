/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningDS;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.entity.sop.SOPSalesOrderLines;
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
public class InventoryRequirementsPlanningDSBean extends EMCDataSourceBean implements InventoryRequirementsPlanningDSLocal {

    @EJB
    private InventoryReferenceLocal itemRefernceBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    public InventoryRequirementsPlanningDSBean() {
        this.setDataSourceClassName(InventoryRequirementsPlanningDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryRequirementsPlanningDS ds = (InventoryRequirementsPlanningDS) dataSourceInstance;
        itemRefernceBean.populateDSFields(ds, userData);
        dimensionBean.populateDimensions(ds, userData);
        EMCQuery query;

        RequirementsPlanningReferenceTypes refType = RequirementsPlanningReferenceTypes.fromId(ds.getReferenceRecordType());
        //Demand
        if (refType == RequirementsPlanningReferenceTypes.SALES_ORDER) {
            if (isBlank(ds.getReferenceRecordRef())) {
                query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
                query.addAnd("recordID", ds.getReferenceRecordID());
                SOPSalesOrderLines so = (SOPSalesOrderLines) util.executeSingleResultQuery(query, userData);
                if (so != null) {
                    ds.setReferenceRecordRef(so.getSalesOrderNo());
                }
            }
            ds.setDemandId(ds.getReferenceRecordRef());
            ds.setDemandType(RequirementsPlanningReferenceTypes.SALES_ORDER.toShortString());
        } else {
           if (refType == RequirementsPlanningReferenceTypes.SAFETY_STOCK) {
                ds.setDemandId(ds.getReferenceRecordRef());
                ds.setDemandType(RequirementsPlanningReferenceTypes.SAFETY_STOCK.toShortString());
            }
        }
        //Supply
        if (refType == RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER) {
            if (isBlank(ds.getReferenceRecordRef())) {
                query = new EMCQuery(enumQueryTypes.SELECT, POPPlannedPurchaseOrders.class);
                query.addAnd("recordID", ds.getReferenceRecordID());
                POPPlannedPurchaseOrders ppo = (POPPlannedPurchaseOrders) util.executeSingleResultQuery(query, userData);
                if (ppo != null) {
                    ds.setReferenceRecordRef(ppo.getPlannedPOId());
                }
            }
            ds.setSupplyId(ds.getReferenceRecordRef());
            ds.setSupplyType(RequirementsPlanningReferenceTypes.PLANNED_PURCHASE_ORDER.toShortString());
        } else {
            if (refType == RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER) {
              
                ds.setSupplyId(ds.getReferenceRecordRef());
                ds.setSupplyType(RequirementsPlanningReferenceTypes.PLANNED_WORKS_ORDER.toShortString());
            } else {
                if (refType == RequirementsPlanningReferenceTypes.PURCHASE_ORDER) {
                    if (isBlank(ds.getReferenceRecordRef())) {
                        query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
                        query.addAnd("recordID", ds.getRecordID());
                        POPPurchaseOrderMaster po = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
                        if (po != null) {
                            ds.setReferenceRecordRef(po.getPurchaseOrderId());
                        }
                    }
                    ds.setSupplyId(ds.getReferenceRecordRef());
                    ds.setSupplyType(RequirementsPlanningReferenceTypes.PURCHASE_ORDER.toShortString());
                } else {
                    if (refType == RequirementsPlanningReferenceTypes.WORKS_ORDER) {
                      
                        ds.setSupplyId(ds.getReferenceRecordRef());
                        ds.setSupplyType(RequirementsPlanningReferenceTypes.WORKS_ORDER.toShortString());
                    }
                }
            }
        }
        return ds;
    }
}
