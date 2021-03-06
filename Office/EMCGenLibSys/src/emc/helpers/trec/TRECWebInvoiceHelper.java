/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.trec;

import java.math.BigDecimal;

/**
 *
 * @author stuart Used to populate order confirmation table in
 * orderProcess.xhtml
 */
public class TRECWebInvoiceHelper {

    private String basketId;
    private int lineNumber;
    private String unNumber;
    private String description;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;
    private BigDecimal vat;
    private BigDecimal totalIncVat;
    private boolean showPreviewLine;
    private String printOption;    
    private boolean enablePrintOption = false;
    private boolean enableDeleteOption = false;
    

    public TRECWebInvoiceHelper() {
    }

    public String getBasketId() {
        return basketId;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getTotalIncVat() {
        return totalIncVat;
    }

    public void setTotalIncVat(BigDecimal totalIncVat) {
        this.totalIncVat = totalIncVat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isShowPreviewLine() {
        return showPreviewLine;
    }

    public void setShowPreviewLine(boolean showPreviewLine) {
        this.showPreviewLine = showPreviewLine;
    }

    public String getPrintOption() {
        return printOption;
    }

    public void setPrintOption(String printOption) {      
        this.printOption = printOption;     
    }

    public boolean isEnablePrintOption() {
        return enablePrintOption;
    }

    public void setEnablePrintOption(boolean enablePrintOption) {
        this.enablePrintOption = enablePrintOption;
    }

    public boolean isEnableDeleteOption() {
        return enableDeleteOption;
    }

    public void setEnableDeleteOption(boolean enableDeleteOption) {
        this.enableDeleteOption = enableDeleteOption;
    }

    
        
}
