/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.inventory.requirementsplanning.InventoryRequirementsPlanningLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.requirementsplanning.RequirementsPlanningFromType;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerPOPMessageEnum;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.tables.EMCTable;
import java.util.Date;
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
public class POPPurchaseOrderMasterBean extends EMCEntityBean implements POPPurchaseOrderMasterLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private POPPurchaseOrderLinesLocal linesBean;
    @EJB
    private EMCDateHandlerLocal dateHandlerBean;
    @EJB
    private InventoryRequirementsPlanningLocal requirementsPlanningBean;
    @EJB
    private POPPurchaseOrderApprovalGroupsLocal approvalGroupsBean;

    /**
     * Creates a new instance of POPPurchaseOrderMasterBean
     */
    public POPPurchaseOrderMasterBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        POPPurchaseOrderMaster purchaseOrder = (POPPurchaseOrderMaster) theRecord;

        if (fieldNameToValidate.equals("purchaseOrderType")) {
            ret = validateType(purchaseOrder.getPurchaseOrderId(), purchaseOrder.getPurchaseOrderType(), userData);
        }

        if (ret) {
            POPPurchaseOrderMaster record = (POPPurchaseOrderMaster) theRecord;
            if (fieldNameToValidate.equals("supplier")) {
                record = doSupplier(record, userData);
                record = doAddress(record, userData);
            } else if (fieldNameToValidate.equals("pricesIncludeVat")) {
                Logger.getLogger("emc").log(Level.WARNING, "Changing this field will affect VAT on the Purchase Order.", userData);
            } else if (fieldNameToValidate.equals("warehouse")) {
                if (userData.getUserData().size() > 3) {
                    if (userData.getUserData(3).toString().equals("UPDATE")) {
                        //UPDATE hard coded on client.
                        record = doWarehouse(record, userData);
                    }
                }
            } else if (fieldNameToValidate.equals("qualityReportReq")) {
                if (record.getQualityReportReq()) {
                    record.setQualityTestReq(true);
                }
            }
            return record;
        }
        return ret;
    }

    /**
     * This method is used to pull the required fields from the suppliers table
     * when a supplier id is changed/added on a purchase order.
     */
    private POPPurchaseOrderMaster doSupplier(POPPurchaseOrderMaster master, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
        query.addAnd("supplierId", master.getSupplier());

        POPSuppliers supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);

        master.setCurrency(supplier.getSupplierCurrency());

        master.setContactPerson(supplier.getContactOrders());
        master.setContactEmail(supplier.getContactOrdersEmail());
        master.setContactNo(supplier.getContactOrdersPhone());

        master.setPriceGroup(supplier.getPriceGroup());

        master.setVatApplicable(supplier.getVatApplicable());

        //Calculate requested delivery date
        master.setRequestedDeliveryDate(dateHandlerBean.calculateEndDate("Default", Functions.nowDate(), ((Double) supplier.getStandardLeadTime()).intValue(), userData));
        return master;
    }

    /**
     * Sets the delivery address of the Purchase Order if it is not set yet. If
     * warehouse is not blank, sets the address to the warehouse address else,
     * sets the address to the companys address.
     *
     * @param master The record the address needs to be set on.
     * @param userData The user data
     */
    private POPPurchaseOrderMaster doAddress(POPPurchaseOrderMaster master, EMCUserData userData) {
        EMCQuery query;
        if (master.getWarehouse() != null) {
            query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
            query.addAnd("warehouseId", master.getWarehouse());
            InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);
            master.setAddressLine1(warehouse.getAddressLine1());
            master.setAddressLine2(warehouse.getAddressLine2());
            master.setAddressLine3(warehouse.getAddressLine3());
            master.setAddressLine4(warehouse.getAddressLine4());
            master.setAddressLine5(warehouse.getAddressLine5());
            master.setAddressPostalCode(warehouse.getPostalCode());
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class);
            query.addAnd("companyId", userData.getCompanyId());
            BaseCompanyTable company = (BaseCompanyTable) util.executeSingleResultQuery(query, userData);
            master.setAddressLine1(company.getCoPhysAdrs1());
            master.setAddressLine2(company.getCoPhysAdrs2());
            master.setAddressLine3(company.getCoPhysAdrs3());
            master.setAddressLine4(company.getCoPhysAdrs4());
            master.setAddressLine5(company.getCoPhysAdrs5());
            master.setAddressPostalCode(company.getCoPhysPostCode());
        }
        return master;
    }

    /**
     * Sets the address of a purchase order when warehouse is set. If the
     * warehouse has an address specified, the delivery address is set to that
     * of the warehouse, else, the company address is used.
     */
    private POPPurchaseOrderMaster doWarehouse(POPPurchaseOrderMaster master, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class);
        query.addAnd("warehouseId", master.getWarehouse());

        InventoryWarehouse warehouse = (InventoryWarehouse) util.executeSingleResultQuery(query, userData);

        if (warehouse != null && !isBlank(warehouse.getAddressLine1())) {
            master.setAddressLine1(warehouse.getAddressLine1());
            master.setAddressLine2(warehouse.getAddressLine2());
            master.setAddressLine3(warehouse.getAddressLine3());
            master.setAddressLine4(warehouse.getAddressLine4());
            master.setAddressLine5(warehouse.getAddressLine5());
            master.setAddressPostalCode(warehouse.getPostalCode());
        }
        return master;
    }

    public POPPurchaseOrderMaster getPurchaseOrder(String purchaseOrderNo, EMCUserData userData) {

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", purchaseOrderNo);

        return (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
    }

    /**
     * This method is used to approve a purchase order.
     */
    public void approvePurchaseOrder(String purchaseOrderId, EMCUserData userData) {
        String employee = employeeBean.findEmployee(userData.getUserName(), userData);
        POPPurchaseOrderMaster master = getPurchaseOrder(purchaseOrderId, userData);

        if (isBlank(employee)) {
            Logger.getLogger("emc").log(Level.SEVERE, "No employee found matching your user id.  You are not authorized to approve a purchase order.", userData);
            return;
        } else {
            if (checkLineItemPrices(purchaseOrderId, userData)) {


                POPPurchaseOrderApprovalGroups approvalGroup = approvalGroupsBean.getApprovalGroups(master.getApprovalGrp(), userData);

                boolean doApprove = false;

                if (approvalGroup != null) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderApprovalGroups.class);
                    query.addTableAnd(emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName(), "purchaseOrderApprovalGroupId", POPPurchaseOrderApprovalGroups.class.getName(), "purchaseOrderApprovalGroupId");
                    query.addAnd("employeeId", employee, emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees.class.getName());
                    query.addAnd("purchaseOrderApprovalGroupId", master.getApprovalGrp());
                    query.addField("purchaseOrderApprovalGroupId"); //0
                    query.addField("itemGroup"); //1
                    query.addField("maxValue"); //2

                    List approvalGrps = util.executeGeneralSelectQuery(query, userData);

                    if (approvalGrps == null || approvalGrps.size() == 0) {
                        Logger.getLogger("emc").log(Level.SEVERE, "User was not found as part of approval group selected.", userData);
                        return;
                    }
                    if (!isBlank(approvalGroup.getHigherLevelApprovalGroups())) {
                        master.setApprovalGrp(approvalGroup.getHigherLevelApprovalGroups());
                        master.setApprovedBy(employee);
                        logMessage(Level.WARNING, ServerPOPMessageEnum.APP_HIGHER_LEVEL, userData);
                        return;

                    } else {

                        if (PurchaseOrderStatus.fromString(master.getStatus()).getId() >= PurchaseOrderStatus.APPROVED.getId()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Purchase order already approved.  Cannot approve again.", userData);
                        } else {
                            //total that needs to be approved
                            double total = findPurchaseOrderTotal(master, userData);
                            double needApproval = total;
                            //get lines
                            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
                            qu.addAnd("purchaseOrderId", master.getPurchaseOrderId());
                            List<POPPurchaseOrderLines> lines = util.executeGeneralSelectQuery(qu, userData);
                            EMCQuery findItem = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
                            //check line for valid approval item groups
                            for (POPPurchaseOrderLines line : lines) {
                                findItem.removeAnd("itemId");
                                findItem.addAnd("itemId", line.getItemId());
                                InventoryItemMaster itemMast = (InventoryItemMaster) util.executeSingleResultQuery(findItem, userData);
                                if (itemMast != null) {
                                    String curGroup = itemMast.getItemGroup();
                                    if (!isBlank(curGroup)) {
                                        for (int j = 0; j < approvalGrps.size(); j++) {
                                            Object[] curRes = (Object[]) approvalGrps.get(j);
                                            //check if this group in on user Approvals
                                            if (curRes[1] != null && curRes[1].toString().equals(curGroup) && line.getNetAmount() <= (Double) curRes[2]) {
                                                j = approvalGrps.size();
                                                needApproval -= line.getNetAmount();
                                            }
                                        }
                                    }
                                }
                            }
                            if (needApproval > 0) {
                                for (int j = 0; j < approvalGrps.size(); j++) {
                                    Object[] curRes = (Object[]) approvalGrps.get(j);
                                    //check if this group in on user Approvals
                                    if (curRes[1] == null && needApproval <= (Double) curRes[2]) {
                                        needApproval = 0;
                                        j = approvalGrps.size();
                                    }
                                }
                            }
                            if (needApproval <= 0) {
                                doApprove = true;
                            }
                        }

                    }

                } else {
                    doApprove = true;
                }
                if (doApprove) {
                    Logger.getLogger("emc").log(Level.INFO, "Purchase order approved", userData);
                    master.setApprovedBy(employee);
                    master.setStatus(PurchaseOrderStatus.APPROVED.toString());
                    master.setApprovalGrp("");
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Maximum approval exceeded.", userData);
                    master.setApprovedBy(null);
                    master.setStatus(PurchaseOrderStatus.REQUISITION.toString());
                }
            }
        }
    }

    /**
     *
     * @param purchaseOrder
     * @param userData
     * @return
     */
    public double findPurchaseOrderTotal(POPPurchaseOrderMaster purchaseOrder, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addFieldAggregateFunction("netAmount", POPPurchaseOrderLines.class.getName(), "SUM");
        query.addAnd("purchaseOrderId", purchaseOrder.getPurchaseOrderId());

        Double ret = (Double) util.executeSingleResultQuery(query, userData);

        return ret == null ? 0 : ret;
    }

    private boolean checkLineItemPrices(String purchaseOrderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);
        query.addAnd("costChange", true);
        int i = ((List) util.executeGeneralSelectQuery(query, userData)).size();
        if (i != 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "There are " + i + " lines that need to be approved for Cost Price Changes", userData);
            return false;
        }
        return true;
    }

    /**
     * This method returns an array containing the total quantity to receive on
     * a Purchase Order in postion 0, and the total quantity received in
     * position 1 and the over received quantity in position 2.
     */
    public double[] getPurchaseOrderQuantities(String purchaseOrderId, EMCUserData userData) {
        double[] ret = new double[3];

        String linesClassName = POPPurchaseOrderLines.class.getName();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, linesClassName);
        query.addFieldAggregateFunction("quantity", linesClassName, "SUM");
        query.addFieldAggregateFunction("itemsReceived", linesClassName, "SUM");
        query.addFieldAggregateFunction("overReceiveQty", linesClassName, "SUM");
        query.addAnd("purchaseOrderId", purchaseOrderId);

        Object[] result = (Object[]) util.executeSingleResultQuery(query, userData);
        if (result != null && result.length > 2) {
            ret[0] = result[0] == null ? 0.0 : (Double) result[0];
            ret[1] = result[1] == null ? 0.0 : (Double) result[1];
            ret[2] = result[2] == null ? 0.0 : (Double) result[2];
        }

        return ret;
    }

    public void unApprovePurchaseOrder(String orderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", orderId);
        query.addAnd("companyId", userData.getCompanyId());
        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
        master.setApprovedBy(null);
        master.setStatus(PurchaseOrderStatus.REQUISITION.toString());
        Logger.getLogger("emc").log(Level.INFO, "Purchase order unapproved.", userData);
    }

    /**
     * This method removes approval from a purchase order
     */
    public void removeApproval(POPPurchaseOrderMaster master, EMCUserData userData) {
        master.setApprovedBy(null);
        master.setStatus(PurchaseOrderStatus.REQUISITION.toString());

        try {
            update(master, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to remove approval on purchase order - " + master.getPurchaseOrderId(), userData);
        }

        Logger.getLogger("emc").log(Level.INFO, "Purchase Order returned to Requisition status.", userData);
    }

    /**
     * Overloaded remove approval method.
     */
    public void removeApproval(String purchaseOrderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);

        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

        removeApproval(master, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) uobject;

        int listSize = 0;
        if (userData.getUserData() != null) {
            listSize = userData.getUserData().size();
        }

        if (listSize > 4) {
            if ("UPDATE".equals(userData.getUserData(4).toString())) {
                updateLinesWarehouse(master, userData);
            }
        }

        String status = master.getStatus();
        if (status.equals(PurchaseOrderStatus.RECEIVED.toString())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
            query.addAnd("purchaseOrderId", master.getPurchaseOrderId());
            query.addField("recordID");
            List<Long> recordIDList = util.executeGeneralSelectQuery(query, userData);
            for (Long recId : recordIDList) {
                requirementsPlanningBean.closeSupply(RequirementsPlanningFromType.PURCHASE_ORDER, recId, userData);
            }
        }

        return super.update(uobject, userData);
    }

    /**
     * This method is used to the warehouse on all lines of a specified purchase
     * order.
     */
    private void updateLinesWarehouse(POPPurchaseOrderMaster master, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", master.getPurchaseOrderId());

        List<POPPurchaseOrderLines> lines = (List<POPPurchaseOrderLines>) util.executeGeneralSelectQuery(query, userData);

        for (POPPurchaseOrderLines line : lines) {
            line.setWarehouse(master.getWarehouse());

            try {
                linesBean.update(line, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update warehouse on line.", userData);
            }
        }
    }

    /**
     * This method is used to the vat code on all lines of a specified purchase
     * order.
     */
//    private void updateLinesVatCode(POPPurchaseOrderMaster master, EMCUserData userData) {
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
//        query.addAnd("purchaseOrderId", master.getPurchaseOrderId());
//        
//        List<POPPurchaseOrderLines> lines = (List<POPPurchaseOrderLines>)util.executeGeneralSelectQuery(query, userData);
//        
//        for (POPPurchaseOrderLines line : lines) {
//            
//            try {
//                linesBean.update(line, userData);
//            } catch (EMCEntityBeanException ex) {
//                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update vat code on line.", userData);
//            }
//        }
//    }
    /**
     * Retrieves a purchase order.
     */
    public POPPurchaseOrderMaster findPurchaseOrder(String purchaseOrderId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);

        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

        if (master == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Purchase order - " + purchaseOrderId + " does not exist", userData);
            }
        }

        return master;
    }

    /**
     * Sets the PO status based on the qty
     *
     * @param purchaseOrderId
     * @param userData
     * @throws emc.framework.EMCEntityBeanException
     */
    public void setPOMasterStatus(String purchaseOrderId, EMCUserData userData) throws EMCEntityBeanException {
        double[] quantities = getPurchaseOrderQuantities(purchaseOrderId, userData);

        double outstanding = quantities[0] - quantities[1];

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);

        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

        if (quantities[1] == 0) {
            switch (emc.enums.pop.purchaseorders.PurchaseOrderStatus.fromString(master.getStatus())) {
                case REQUISITION:
                case APPROVED:
                    break;

                default:
                    master.setStatus(emc.enums.pop.purchaseorders.PurchaseOrderStatus.ORDERED.toString());
                    update(master, userData);
                    break;
            }
        } else if (outstanding == 0) {
//            switch(emc.enums.pop.purchaseorders.PurchaseOrderStatus.fromString(master.getStatus())){
//                case PARTIALLY_RECEIVED: 
            master.setStatus(emc.enums.pop.purchaseorders.PurchaseOrderStatus.RECEIVED.toString());
            update(master, userData);
//                    break;
//                default:break;
//            }

        } else if (outstanding > 0) {
//            switch(emc.enums.pop.purchaseorders.PurchaseOrderStatus.fromString(master.getStatus())){
//                case RECEIVED: 
            master.setStatus(emc.enums.pop.purchaseorders.PurchaseOrderStatus.PARTIALLY_RECEIVED.toString());
            update(master, userData);
//                    break;
//                default:break;
//            }

        }
    }

    /**
     * Get the outstanding quantity on a purchase order.
     */
    public double getOutstandingQuantity(String purchaseOrderId, EMCUserData userData) {
        //EMC Query cannot handle this type of query
        String query = "SELECT SUM(l.quantity - l.itemsReceived) FROM POPPurchaseOrderLines l WHERE l.purchaseOrderId = '" + purchaseOrderId + "'" + " AND l.companyId = '" + util.getCompanyId(POPPurchaseOrderLines.class.getName(), userData) + "'";

        Double result = (Double) util.executeSingleResultQuery(query, userData);

        return result == null ? 0 : result;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        POPPurchaseOrderMaster record = (POPPurchaseOrderMaster) vobject;


        if (ret && record.getQualityTestReq() && isBlank(record.getQualityTest())) {
            Logger.getLogger("emc").log(Level.WARNING, "Please select a quality test to perform.", userData);
            ret = false;
        }

        return ret;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        POPPurchaseOrderMaster record = (POPPurchaseOrderMaster) vobject;

        if (ret && record.getQualityTestReq() && isBlank(record.getQualityTest())) {
            Logger.getLogger("emc").log(Level.WARNING, "Please select a quality test to preform.", userData);
            ret = false;
        }

        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster) iobject;

        if (isBlank(master.getOrderedBy())) {
            String employee = employeeBean.findEmployee(userData.getUserName(), userData);

            if (isBlank(employee)) {
                Logger.getLogger("emc").log(Level.WARNING, "No employee found with your user id.  Ordered by field not set.", userData);
            } else {
                master.setOrderedBy(userData.getUserName());
            }
        }

        return super.insert(master, userData);
    }

    /**
     * Validates purchase order type.
     */
    private boolean validateType(String purchaseOrderId, String type, EMCUserData userData) {
        boolean ret = true;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", purchaseOrderId);

        if (util.executeGeneralSelectQuery(query, userData).size() != 0) {
            ret = false;
            Logger.getLogger("emc").log(Level.SEVERE, "Type may not be changed after lines have been added to a purchase order", userData);
        }

        return ret;
    }

//    @Override
//    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
//        boolean ret = super.doDeleteValidation(vobject, userData);
//        
//        POPPurchaseOrderMaster master = (POPPurchaseOrderMaster)vobject;
//        
//        PurchaseOrderStatus status = PurchaseOrderStatus.fromString(master.getStatus());
//        
//        if (!(status.equals(PurchaseOrderStatus.REQUISITION) || status.equals(PurchaseOrderStatus.APPROVED))) {
//            ret = false;
//            Logger.getLogger("emc").log(Level.SEVERE, "A Purchase Order with a status of Ordered or higher may not be deleted.", userData);
//        }
//        
//        return ret;
//    }
    /**
     * Returns the blanket order from which a Purchase Order was created. May
     * return null.
     */
    public POPPurchaseOrderMaster getBlanketOrder(POPPurchaseOrderMaster master, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
        query.addAnd("purchaseOrderId", master.getBlanketOrderRef());

        return (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns the blanket order from which a Purchase Order was created. May
     * return null.
     */
    public POPPurchaseOrderMaster getBlanketOrder(String purchaseOrderId, EMCUserData userData) {
        String className = POPPurchaseOrderMaster.class.getName();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addField("blanketOrderRef");
        query.addAnd("purchaseOrderId", purchaseOrderId);

        String ref = (String) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addAnd("purchaseOrderId", ref);

        return (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
    }

    public boolean updateLinesDate(String purchaseId, Date theDate, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
        query.addAnd("purchaseOrderId", purchaseId);
        query.addAnd("companyId", userData.getCompanyId());
        List lines = util.executeGeneralSelectQuery(query, userData);
        POPPurchaseOrderLines line;
        for (Object o : lines) {
            line = (POPPurchaseOrderLines) o;
            line.setDeliveryDate(theDate);
            try {
                linesBean.update(line, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update lines date.", userData);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the return QC form should open
     *
     * @param query
     * @param userData
     * @return
     */
    public boolean checkQCReturn(String query, EMCUserData userData) {
        List theList = util.executeGeneralSelectQuery(query, userData);
        if (theList == null || theList.isEmpty()) {
            return false;
        }
        return true;
    }
}
