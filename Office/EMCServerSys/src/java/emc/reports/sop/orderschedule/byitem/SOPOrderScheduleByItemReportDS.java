/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.sop.orderschedule.byitem;

import java.math.BigDecimal;

/**
 *
 * @author riaan
 */
public class SOPOrderScheduleByItemReportDS {

    private String salesOrderNo;
    private String customerId;
    private String customerName;
    private String itemReference;
    private String itemDescription;
    private String deliveryDate;
    private String dimension1;
    private String dimension3;
    private String dimension2Label1;
    private String dimension2Label2;
    private String dimension2Label3;
    private String dimension2Label4;
    private String dimension2Label5;
    private String dimension2Label6;
    private String dimension2Label7;
    private String dimension2Label8;
    private BigDecimal dimension2Total1 = BigDecimal.ZERO;
    private BigDecimal dimension2Total2 = BigDecimal.ZERO;
    private BigDecimal dimension2Total3 = BigDecimal.ZERO;
    private BigDecimal dimension2Total4 = BigDecimal.ZERO;
    private BigDecimal dimension2Total5 = BigDecimal.ZERO;
    private BigDecimal dimension2Total6 = BigDecimal.ZERO;
    private BigDecimal dimension2Total7 = BigDecimal.ZERO;
    private BigDecimal dimension2Total8 = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal totalValue = BigDecimal.ZERO;  //Excluding VAT

    /** Creates a new instance of SOPOrderScheduleByItemReportDS. */
    public SOPOrderScheduleByItemReportDS() {
        
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public BigDecimal getDimension2Total1() {
        return dimension2Total1;
    }

    public void setDimension2Total1(BigDecimal dimension2Total1) {
        this.dimension2Total1 = dimension2Total1;
    }

    public BigDecimal getDimension2Total2() {
        return dimension2Total2;
    }

    public void setDimension2Total2(BigDecimal dimension2Total2) {
        this.dimension2Total2 = dimension2Total2;
    }

    public BigDecimal getDimension2Total3() {
        return dimension2Total3;
    }

    public void setDimension2Total3(BigDecimal dimension2Total3) {
        this.dimension2Total3 = dimension2Total3;
    }

    public BigDecimal getDimension2Total4() {
        return dimension2Total4;
    }

    public void setDimension2Total4(BigDecimal dimension2Total4) {
        this.dimension2Total4 = dimension2Total4;
    }

    public BigDecimal getDimension2Total5() {
        return dimension2Total5;
    }

    public void setDimension2Total5(BigDecimal dimension2Total5) {
        this.dimension2Total5 = dimension2Total5;
    }

    public BigDecimal getDimension2Total6() {
        return dimension2Total6;
    }

    public void setDimension2Total6(BigDecimal dimension2Total6) {
        this.dimension2Total6 = dimension2Total6;
    }

    public BigDecimal getDimension2Total7() {
        return dimension2Total7;
    }

    public void setDimension2Total7(BigDecimal dimension2Total7) {
        this.dimension2Total7 = dimension2Total7;
    }

    public BigDecimal getDimension2Total8() {
        return dimension2Total8;
    }

    public void setDimension2Total8(BigDecimal dimension2Total8) {
        this.dimension2Total8 = dimension2Total8;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getDimension2Label1() {
        return dimension2Label1;
    }

    public void setDimension2Label1(String dimension2Label1) {
        this.dimension2Label1 = dimension2Label1;
    }

    public String getDimension2Label2() {
        return dimension2Label2;
    }

    public void setDimension2Label2(String dimension2Label2) {
        this.dimension2Label2 = dimension2Label2;
    }

    public String getDimension2Label3() {
        return dimension2Label3;
    }

    public void setDimension2Label3(String dimension2Label3) {
        this.dimension2Label3 = dimension2Label3;
    }

    public String getDimension2Label4() {
        return dimension2Label4;
    }

    public void setDimension2Label4(String dimension2Label4) {
        this.dimension2Label4 = dimension2Label4;
    }

    public String getDimension2Label5() {
        return dimension2Label5;
    }

    public void setDimension2Label5(String dimension2Label5) {
        this.dimension2Label5 = dimension2Label5;
    }

    public String getDimension2Label6() {
        return dimension2Label6;
    }

    public void setDimension2Label6(String dimension2Label6) {
        this.dimension2Label6 = dimension2Label6;
    }

    public String getDimension2Label7() {
        return dimension2Label7;
    }

    public void setDimension2Label7(String dimension2Label7) {
        this.dimension2Label7 = dimension2Label7;
    }

    public String getDimension2Label8() {
        return dimension2Label8;
    }

    public void setDimension2Label8(String dimension2Label8) {
        this.dimension2Label8 = dimension2Label8;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }
}
