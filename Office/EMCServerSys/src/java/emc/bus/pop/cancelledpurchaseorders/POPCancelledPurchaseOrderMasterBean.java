/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.cancelledpurchaseorders;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.pop.POPPurchaseOrderApprovalGroupEmployeesLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.pop.datasource.POPPurchaseOrderLinesDSLocal;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.pop.purchaseorders.PurchaseOrderCancelledStatus;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPCancelledPurchaseOrderMasterBean extends EMCEntityBean implements POPCancelledPurchaseOrderMasterLocal {

    @EJB
    private POPPurchaseOrderLinesLocal linesBean;
    @EJB
    private POPCancelledPurchaseOrderLinesLocal cancelledLinesBean;
    @EJB
    private POPPurchaseOrderLinesDSLocal linesDSBean;
    @EJB
    private POPPurchaseOrderMasterLocal masterBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private POPPurchaseOrderApprovalGroupEmployeesLocal approvalGroupEmployeesBean;

    /** Creates a new instance of POPCancelledPurchaseOrderMasterBean. */
    public POPCancelledPurchaseOrderMasterBean() {
    }

    /** This method is used to cancel a purchase order. */
    public boolean cancelPurchaseOrder(POPPurchaseOrderMaster purchaseOrder, EMCUserData userData) throws EMCEntityBeanException {
        if (checkCancelAllowed(userData)) {
            ensureCancelledPOMasterExists(purchaseOrder, userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
            query.addAnd("purchaseOrderId", purchaseOrder.getPurchaseOrderId());

            List<POPPurchaseOrderLines> lines = (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(query, userData);

            for (POPPurchaseOrderLines line : lines) {
                processLine(line, userData);
            }

            if (!purchaseOrder.isCancelled()) {
                long masterRecordID = purchaseOrder.getRecordID();
                purchaseOrder = null;

                query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
                query.addAnd("recordID", masterRecordID);
                purchaseOrder = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

                purchaseOrder.setCancelled(true);

                purchaseOrder.setCancelledStatus(PurchaseOrderCancelledStatus.CANCELLED.toString());
                masterBean.update(purchaseOrder, userData);
            }

//            if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.ORDERED.toString())) {
//                masterBean.delete(purchaseOrder, userData);
//            }

            Logger.getLogger("emc").log(Level.INFO, "Purchase Order cancelled", userData);

            return true;
        } else {
            return false;
        }
    }

    /** This method is used to cancel a single Purchase Order Line. */
    public boolean cancelPurchaseOrderLine(POPPurchaseOrderLines line, EMCUserData userData) throws EMCEntityBeanException {
        if (checkCancelAllowed(userData)) {
            ensureCancelledPOMasterExists(line.getPurchaseOrderId(), userData);

            processLine(line, userData);

            POPPurchaseOrderMaster purchaseOrder = masterBean.findPurchaseOrder(line.getPurchaseOrderId(), userData);
            if (!purchaseOrder.isCancelled()) {
                purchaseOrder.setCancelled(true);
            }
            //Check whether the entire order has been cancelled.
            if (checkOutstandingStockOnPO(line.getPurchaseOrderId(), line.getRecordID(), userData)) {
                purchaseOrder.setCancelledStatus(PurchaseOrderCancelledStatus.PARTIALLY_CANCELLED.toString());
            } else {
                purchaseOrder.setCancelledStatus(PurchaseOrderCancelledStatus.CANCELLED.toString());
            }
            masterBean.update(purchaseOrder, userData);

            Logger.getLogger("emc").log(Level.INFO, "Purchase Order line cancelled", userData);

            return true;
        } else {
            return true;
        }
    }

    /**
     * Returns a boolean indicating whether there is any outstanding stock on
     * the specified purchase order, excluding the specified line.
     * @param purchaseOrderId Purchase order ID.
     * @param ignoreLine Record ID of line to be excluded from check.  This should
     * be the line being cancelled.
     * @param userData User data.
     * @return A boolean indicating whether the is outstanding stock on the specified
     * purchase order.
     */
    private boolean checkOutstandingStockOnPO(String purchaseOrderId, long ignoreLine, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);
        query.addAnd("recordID", ignoreLine, EMCQueryConditions.NOT);

        //Quantity fields are double values.  Check them manually in Java to prevent
        //precision issues
        List<POPPurchaseOrderLines> lines = (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(query, userData);

        for (POPPurchaseOrderLines line : lines) {
            if (util.compareDouble(line.getItemsReceived(), line.getQuantity()) < 0) {
                //We still need to receive items
                return true;
            }
        }
        return false;
    }

    /** This method is used to process Purchase Order Lines being cancelled. */
    private void processLine(POPPurchaseOrderLines line, EMCUserData userData) throws EMCEntityBeanException {
        double receivedQty = line.getItemsReceived();
        double lineQuantity = line.getQuantity();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", line.getPurchaseOrderId());
        query.addAnd("lineNo", line.getLineNo());
        POPCancelledPurchaseOrderLines cancelledLine = (POPCancelledPurchaseOrderLines) util.executeSingleResultQuery(query, userData);

        if (cancelledLine == null) {
            cancelledLine = (POPCancelledPurchaseOrderLines) util.convertEntityToInstanceOfEntity(line, POPCancelledPurchaseOrderLines.class, userData);
            cancelledLine.setRecordID(0);
            cancelledLine.setQuantity(0);
        }

        double outstandingQuantity = lineQuantity - receivedQty;

        cancelledLine.setQuantity(cancelledLine.getQuantity() + outstandingQuantity);
        cancelledLine.setItemsReceived(receivedQty);

        if (receivedQty == 0) {
            linesDSBean.delete(line, userData);
        } else if (receivedQty < lineQuantity) {
            line.setQuantity(receivedQty);

            linesBean.update(line, userData);
        } else {
            //Line fully received, do not cancel
            return;
        }

        cancelledLinesBean.insert(cancelledLine, userData);
    }

    /** This method creates a cancelled Purchase Order Master if necessary. */
    private void ensureCancelledPOMasterExists(String purchaseOrderId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
        query.addAnd("purchaseOrderId", purchaseOrderId);

        ensureCancelledPOMasterExists((POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData), userData);
    }

    /** This method creates a cancelled Purchase Order Master if necessary. */
    private void ensureCancelledPOMasterExists(POPPurchaseOrderMaster purchaseOrder, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderMaster.class.getName());
        query.addAnd("purchaseOrderId", purchaseOrder.getPurchaseOrderId());

        if (util.executeSingleResultQuery(query, userData) == null) {
            POPCancelledPurchaseOrderMaster cancelledPOMaster = (POPCancelledPurchaseOrderMaster) util.convertEntityToInstanceOfEntity(purchaseOrder, POPCancelledPurchaseOrderMaster.class, userData);
            cancelledPOMaster.setRecordID(0);

            insert(cancelledPOMaster, userData);
        }
    }

    /** Checks whether a user is allowed to cancel a purchase order or purchase order line. */
    private boolean checkCancelAllowed(EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);

        List<POPPurchaseOrderApprovalGroups> approvalGroups = approvalGroupEmployeesBean.findEmployeeApprovalGroupRecords(employee, userData);

        for (POPPurchaseOrderApprovalGroups approvalGroup : approvalGroups) {
            if (approvalGroup.getAllowCancel()) {
                return true;
            }

        }

        Logger.getLogger("emc").log(Level.SEVERE, "You are not authorised to cancel Purchase Orders.", userData);
        return false;
    }
}
