
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop.datasource;

import emc.datatypes.inventory.dimension3.Category;
import emc.datatypes.pop.datasource.purchaseorders.ItemDescription;
import emc.datatypes.pop.datasource.purchaseorders.ItemReference;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.inventory.ItemDimensionInterface;
import emc.inventory.ItemReferenceInterface;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class POPPurchaseOrderLinesDS extends POPPurchaseOrderLines implements ItemReferenceInterface, ItemDimensionInterface {

    private String itemDescription;
    private String itemReference;
    //N&L Spesific
    private String colourCatagory;
    private String fabricColourNumber;
    private String printColourNumber;
    private String designNo;
    //N&L Spesific

    /** Creates a new instance of POPPurchaseOrderLinesDS */
    public POPPurchaseOrderLinesDS() {
        this.setDataSource(true);
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    //N&L Spesific
    public String getColourCatagory() {
        return colourCatagory;
    }

    public void setColourCatagory(String colourCatagory) {
        this.colourCatagory = colourCatagory;
    }

    public String getFabricColourNumber() {
        return fabricColourNumber;
    }

    public void setFabricColourNumber(String fabricColourNumber) {
        this.fabricColourNumber = fabricColourNumber;
    }

    public String getPrintColourNumber() {
        return printColourNumber;
    }

    public void setPrintColourNumber(String printColourNumber) {
        this.printColourNumber = printColourNumber;
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }
    //N&L Spesific

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("itemDescription", new ItemDescription());
        toBuild.put("itemReference", new ItemReference());
        toBuild.put("colourCatagory", new Category());

        return toBuild;
    }

    public void setDimension1(String dimension1) {
        this.setItemDimension1(dimension1);
    }

    public String getDimension1() {
        return this.getItemDimension1();
    }

    public void setDimension2(String dimension2) {
        this.setItemDimension2(dimension2);
    }

    public String getDimension2() {
        return this.getItemDimension2();
    }

    public void setDimension3(String dimension3) {
        this.setItemDimension3(dimension3);
    }

    public String getDimension3() {
        return this.getItemDimension3();
    }
}
