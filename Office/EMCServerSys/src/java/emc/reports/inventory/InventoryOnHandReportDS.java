/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory;

/**
 *
 * @author wikus
 */
public class InventoryOnHandReportDS {

    private String itemPrimaryReference;
    private String itemDesc;
    private String dimension1;
    private String dimension2;
    private String dimension3;
    private String warehouse;
    private double ohAvailable;
    private double ohReserved;
    private double ohTotal;
    private double oAvailable;
    private double oReserved;
    private double oTotal;
    private double blanketOrderOS;
//    private double salesOrderOS;
    private double oOut;
    private double minQty;
    private double qc;

    public double getBlanketOrderOS() {
        return blanketOrderOS;
    }

    public void setBlanketOrderOS(double blanketOrderOS) {
        this.blanketOrderOS = blanketOrderOS;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemPrimaryReference() {
        return itemPrimaryReference;
    }

    public void setItemPrimaryReference(String itemPrimaryReference) {
        this.itemPrimaryReference = itemPrimaryReference;
    }

    public double getOAvailable() {
        return oAvailable;
    }

    public void setOAvailable(double oAvailable) {
        this.oAvailable = oAvailable;
    }

    public double getOReserved() {
        return oReserved;
    }

    public void setOReserved(double oReserved) {
        this.oReserved = oReserved;
    }

    public double getOTotal() {
        return oTotal;
    }

    public void setOTotal(double oTotal) {
        this.oTotal = oTotal;
    }

    public double getOhAvailable() {
        return ohAvailable;
    }

    public void setOhAvailable(double ohAvailable) {
        this.ohAvailable = ohAvailable;
    }

    public double getOhReserved() {
        return ohReserved;
    }

    public void setOhReserved(double ohReserved) {
        this.ohReserved = ohReserved;
    }

    public double getOhTotal() {
        return ohTotal;
    }

    public void setOhTotal(double ohTotal) {
        this.ohTotal = ohTotal;
    }

//    public double getSalesOrderOS() {
//        return salesOrderOS;
//    }
//    public void setSalesOrderOS(double salesOrderOS) {
//        this.salesOrderOS = salesOrderOS;
//    }
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public double getOOut() {
        return oOut;
    }

    public void setOOut(double oOut) {
        this.oOut = oOut;
    }

    public double getMinQty() {
        return minQty;
    }

    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    public double getoAvailable() {
        return oAvailable;
    }

    public void setoAvailable(double oAvailable) {
        this.oAvailable = oAvailable;
    }

    public double getoOut() {
        return oOut;
    }

    public void setoOut(double oOut) {
        this.oOut = oOut;
    }

    public double getoReserved() {
        return oReserved;
    }

    public void setoReserved(double oReserved) {
        this.oReserved = oReserved;
    }

    public double getoTotal() {
        return oTotal;
    }

    public void setoTotal(double oTotal) {
        this.oTotal = oTotal;
    }

    public double getQc() {
        return qc;
    }

    public void setQc(double qc) {
        this.qc = qc;
    }
}
