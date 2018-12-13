/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.inventory.stockvaluation;

/**
 * @Name         : StockValuationReportDS
 *
 * @Date         : 03 Aug 2009
 * 
 * @Description  : Data source for the Stock Valuation.
 *
 * @author       : riaan
 */
public class StockValuationReportDS {

    private String itemGroup;
    private String itemRef;
    private String itemDescription;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String uom;
    private double quantityOnHand;
    private double itemCost;
    private double value;
     
    /** Creates a new instance of StockValuationReportDS. */
    public StockValuationReportDS() {

    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(double quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
