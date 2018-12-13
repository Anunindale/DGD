/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.picking;

import emc.datatypes.EMCDataType;
import emc.datatypes.pop.deliverymodes.foreignkeys.DeliveryModeIdFK;
import emc.datatypes.pop.deliveryterms.foreignkeys.DeliveryTermsIdFK;
import emc.datatypes.inventory.pickinglist.DeliveryDate;
import emc.datatypes.inventory.pickinglist.OrderType;
import emc.datatypes.inventory.pickinglist.PickingListDate;
import emc.datatypes.inventory.pickinglist.PickingListIdPK;
import emc.datatypes.inventory.pickinglist.SalesOrderRef;
import emc.datatypes.inventory.pickinglist.Status;
import emc.datatypes.inventory.pickinglist.ToPickFrom;
import emc.datatypes.inventory.pickinglist.WaybillNumber;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFKNotMandatory;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalPostalCode;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.pickinglist.PickingListCreationTypes;
import emc.enums.inventory.pickinglist.PickingListHeldStatusses;
import emc.enums.inventory.pickinglist.PickingListIssueTypes;
import emc.enums.inventory.pickinglist.PickingListPickFrom;
import emc.enums.inventory.pickinglist.PickingListStatusses;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryPickingListMaster", uniqueConstraints = {@UniqueConstraint(columnNames = {"pickingListId", "companyId"})})
public class InventoryPickingListMaster extends EMCEntityClass {

    private String orderId;
    private String pickingListId;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    private String deliveryMode;
    private String deliveryName;
    private String deliveryTerm;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String deliveryAddress3;
    private String deliveryAddress4;
    private String deliveryAddressCode;
    private String customerAccount;
    private String orderType;
    @Temporal(TemporalType.DATE)
    private Date pickingListDate;
    private String status = PickingListStatusses.OPEN.toString();
    private String issueType = PickingListIssueTypes.MANUAL.toString();
    private String creationType = PickingListCreationTypes.CAPTURED.toString();
    private BigDecimal numberOfCartons = BigDecimal.ZERO;
    private BigDecimal totalWeight = BigDecimal.ZERO;
    private BigDecimal deliveryCharge = BigDecimal.ZERO;
    private String waybillNumber;
    private String salesOrderRef;
    //Order Held - Delivery Time
    private String orderHeldStatus = PickingListHeldStatusses.NA.toString();
    @Temporal(TemporalType.DATE)
    private Date dateHeld;
    @Temporal(TemporalType.TIME)
    private Date timeHeld;
    private String heldBy;
    @Temporal(TemporalType.DATE)
    private Date dateHeldReleased;
    @Temporal(TemporalType.TIME)
    private Date timeHeldReleased;
    private String heldReleasedBy;
    //N & L Specific.  Indicates whether Picking List is for Fabric or Trims.  May be null
    private String productionPickingListType;
    //
    private String toPickFrom = PickingListPickFrom.INVENTORY.toString();

    public InventoryPickingListMaster() {
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap toBuild = super.buildFieldList();
        toBuild.put("pickingListId", new PickingListIdPK());
        toBuild.put("deliveryDate", new DeliveryDate());
        toBuild.put("deliveryMode", new DeliveryModeIdFK());
        toBuild.put("deliveryTerm", new DeliveryTermsIdFK());
        toBuild.put("deliveryAddress1", new PhysicalAddress1());
        toBuild.put("deliveryAddress2", new PhysicalAddress2());
        toBuild.put("deliveryAddress3", new PhysicalAddress3());
        toBuild.put("deliveryAddress4", new PhysicalAddress4());
        toBuild.put("deliveryAddressCode", new PhysicalPostalCode());
        toBuild.put("customerAccount", new CustomerIdFKNotMandatory());
        toBuild.put("orderType", new OrderType());
        toBuild.put("pickingListDate", new PickingListDate());
        toBuild.put("status", new Status());
        toBuild.put("waybillNumber", new WaybillNumber());
        toBuild.put("salesOrderRef", new SalesOrderRef());
        toBuild.put("toPickFrom", new ToPickFrom());
        return toBuild;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getDeliveryAddress4() {
        return deliveryAddress4;
    }

    public void setDeliveryAddress4(String deliveryAddress4) {
        this.deliveryAddress4 = deliveryAddress4;
    }

    public String getDeliveryAddressCode() {
        return deliveryAddressCode;
    }

    public void setDeliveryAddressCode(String deliveryAddressCode) {
        this.deliveryAddressCode = deliveryAddressCode;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryTerm() {
        return deliveryTerm;
    }

    public void setDeliveryTerm(String deliveryTerm) {
        this.deliveryTerm = deliveryTerm;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getPickingListDate() {
        return pickingListDate;
    }

    public void setPickingListDate(Date pickingListDate) {
        this.pickingListDate = pickingListDate;
    }

    public String getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(String pickingListId) {
        this.pickingListId = pickingListId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationType() {
        return creationType;
    }

    public void setCreationType(String creationType) {
        this.creationType = creationType;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getProductionPickingListType() {
        return productionPickingListType;
    }

    public void setProductionPickingListType(String productionPickingListType) {
        this.productionPickingListType = productionPickingListType;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListMaster.class.getName());
        query.addAnd("status", PickingListStatusses.OPEN.toString());
        return query;
    }

    public BigDecimal getNumberOfCartons() {
        return numberOfCartons;
    }

    public void setNumberOfCartons(BigDecimal numberOfCartons) {
        this.numberOfCartons = numberOfCartons;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(BigDecimal deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getToPickFrom() {
        return toPickFrom;
    }

    public void setToPickFrom(String toPickFrom) {
        this.toPickFrom = toPickFrom;
    }

    public String getSalesOrderRef() {
        return salesOrderRef;
    }

    public void setSalesOrderRef(String salesOrderRef) {
        this.salesOrderRef = salesOrderRef;
    }

    public String getOrderHeldStatus() {
        return orderHeldStatus;
    }

    public void setOrderHeldStatus(String orderHeldStatus) {
        this.orderHeldStatus = orderHeldStatus;
    }

    public Date getDateHeld() {
        return dateHeld;
    }

    public void setDateHeld(Date dateHeld) {
        this.dateHeld = dateHeld;
    }

    public Date getDateHeldReleased() {
        return dateHeldReleased;
    }

    public void setDateHeldReleased(Date dateHeldReleased) {
        this.dateHeldReleased = dateHeldReleased;
    }

    public String getHeldBy() {
        return heldBy;
    }

    public void setHeldBy(String heldBy) {
        this.heldBy = heldBy;
    }

    public String getHeldReleasedBy() {
        return heldReleasedBy;
    }

    public void setHeldReleasedBy(String heldReleasedBy) {
        this.heldReleasedBy = heldReleasedBy;
    }

    public Date getTimeHeld() {
        return timeHeld;
    }

    public void setTimeHeld(Date timeHeld) {
        this.timeHeld = timeHeld;
    }

    public Date getTimeHeldReleased() {
        return timeHeldReleased;
    }

    public void setTimeHeldReleased(Date timeHeldReleased) {
        this.timeHeldReleased = timeHeldReleased;
    }
}
