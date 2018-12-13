/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.customeractivity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description : Helper class used by the Customer Activity form.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CustomerActivityHelper {

    private String customerId;
    private String customerName;

    private int numberOfOpenOrders;
    private BigDecimal openOrderValue = BigDecimal.ZERO;
    
    private String lastInvoiceNo;
    private Date lastInvoiceDate;
    private BigDecimal lastInvoiceAmount = BigDecimal.ZERO;

    private String lastCreditNoteNo;
    private Date lastCreditNoteDate;
    private BigDecimal lastCreditNoteAmount = BigDecimal.ZERO;

    private String lastSalesOrderNo;
    private Date lastSalesOrderDate;
    private BigDecimal lastSalesOrderAmount = BigDecimal.ZERO;

    private String lastPaymentNo;
    private Date lastPaymentDate;
    private BigDecimal lastPaymentAmount = BigDecimal.ZERO;

    private String lastReturnedPaymentNo;
    private Date lastReturnedPaymentDate;
    private BigDecimal lastReturnedPaymentAmount = BigDecimal.ZERO;

    /** Creates a new instance of CustomerActivityHelper */
    public CustomerActivityHelper() {

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfOpenOrders() {
        return numberOfOpenOrders;
    }

    public void setNumberOfOpenOrders(int numberOfOpenOrders) {
        this.numberOfOpenOrders = numberOfOpenOrders;
    }

    public BigDecimal getOpenOrderValue() {
        return openOrderValue;
    }

    public void setOpenOrderValue(BigDecimal openOrderValue) {
        this.openOrderValue = openOrderValue;
    }

    public String getLastInvoiceNo() {
        return lastInvoiceNo;
    }

    public void setLastInvoiceNo(String lastInvoiceNo) {
        this.lastInvoiceNo = lastInvoiceNo;
    }

    public Date getLastInvoiceDate() {
        return lastInvoiceDate;
    }

    public void setLastInvoiceDate(Date lastInvoiceDate) {
        this.lastInvoiceDate = lastInvoiceDate;
    }

    public BigDecimal getLastInvoiceAmount() {
        return lastInvoiceAmount;
    }

    public void setLastInvoiceAmount(BigDecimal lastInvoiceAmount) {
        this.lastInvoiceAmount = lastInvoiceAmount;
    }

    public String getLastCreditNoteNo() {
        return lastCreditNoteNo;
    }

    public void setLastCreditNoteNo(String lastCreditNoteNo) {
        this.lastCreditNoteNo = lastCreditNoteNo;
    }

    public Date getLastCreditNoteDate() {
        return lastCreditNoteDate;
    }

    public void setLastCreditNoteDate(Date lastCreditNoteDate) {
        this.lastCreditNoteDate = lastCreditNoteDate;
    }

    public BigDecimal getLastCreditNoteAmount() {
        return lastCreditNoteAmount;
    }

    public void setLastCreditNoteAmount(BigDecimal lastCreditNoteAmount) {
        this.lastCreditNoteAmount = lastCreditNoteAmount;
    }

    public String getLastSalesOrderNo() {
        return lastSalesOrderNo;
    }

    public void setLastSalesOrderNo(String lastSalesOrderNo) {
        this.lastSalesOrderNo = lastSalesOrderNo;
    }

    public Date getLastSalesOrderDate() {
        return lastSalesOrderDate;
    }

    public void setLastSalesOrderDate(Date lastSalesOrderDate) {
        this.lastSalesOrderDate = lastSalesOrderDate;
    }

    public BigDecimal getLastSalesOrderAmount() {
        return lastSalesOrderAmount;
    }

    public void setLastSalesOrderAmount(BigDecimal lastSalesOrderAmount) {
        this.lastSalesOrderAmount = lastSalesOrderAmount;
    }

    public String getLastPaymentNo() {
        return lastPaymentNo;
    }

    public void setLastPaymentNo(String lastPaymentNo) {
        this.lastPaymentNo = lastPaymentNo;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public BigDecimal getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    public void setLastPaymentAmount(BigDecimal lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    public String getLastReturnedPaymentNo() {
        return lastReturnedPaymentNo;
    }

    public void setLastReturnedPaymentNo(String lastReturnedPaymentNo) {
        this.lastReturnedPaymentNo = lastReturnedPaymentNo;
    }

    public Date getLastReturnedPaymentDate() {
        return lastReturnedPaymentDate;
    }

    public void setLastReturnedPaymentDate(Date lastReturnedPaymentDate) {
        this.lastReturnedPaymentDate = lastReturnedPaymentDate;
    }

    public BigDecimal getLastReturnedPaymentAmount() {
        return lastReturnedPaymentAmount;
    }

    public void setLastReturnedPaymentAmount(BigDecimal lastReturnedPaymentAmount) {
        this.lastReturnedPaymentAmount = lastReturnedPaymentAmount;
    }
}
