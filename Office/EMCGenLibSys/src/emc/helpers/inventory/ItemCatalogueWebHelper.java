/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.inventory;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author wikus
 */
public class ItemCatalogueWebHelper {

    private String catalogueNumber;
    private String catalogueDescription;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String gender;
    private String genderDescription;
    private String productGroup;
    private String productGroupDescription;
    private boolean dim1Active;
    private String dimension1;
    private String dimension1Description;
    private boolean dim2Active;
    private String dimension2;
    private String dimension2Description;
    private boolean dim3Active;
    private String dimension3;
    private String dimension3Description;
    private String imagePath;
    private BigDecimal price;
    private String webstoreType;
    private boolean showAddInfo;
    private String unNumber;
    private String operatorTelNo;
    private String specialistTelNo;
    private BigDecimal quantity;
    private String priceVal;
//    private List<SalesItemSize> sizeList;

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public void setCatalogueNumber(String catalogueNumber) {
        this.catalogueNumber = catalogueNumber;
    }

    public String getCatalogueDescription() {
        return catalogueDescription;
    }

    public void setCatalogueDescription(String catalogueDescription) {
        this.catalogueDescription = catalogueDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemReference() {
        return itemReference;
    }

    public void setItemReference(String itemReference) {
        this.itemReference = itemReference;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderDescription() {
        return genderDescription;
    }

    public void setGenderDescription(String genderDescription) {
        this.genderDescription = genderDescription;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductGroupDescription() {
        return productGroupDescription;
    }

    public void setProductGroupDescription(String productGroupDescription) {
        this.productGroupDescription = productGroupDescription;
    }

    public boolean isDim1Active() {
        return dim1Active;
    }

    public void setDim1Active(boolean dim1Active) {
        this.dim1Active = dim1Active;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension1Description() {
        return dimension1Description;
    }

    public void setDimension1Description(String dimension1Description) {
        this.dimension1Description = dimension1Description;
    }

    public boolean isDim2Active() {
        return dim2Active;
    }

    public void setDim2Active(boolean dim2Active) {
        this.dim2Active = dim2Active;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension2Description() {
        return dimension2Description;
    }

    public void setDimension2Description(String dimension2Description) {
        this.dimension2Description = dimension2Description;
    }

    public boolean isDim3Active() {
        return dim3Active;
    }

    public void setDim3Active(boolean dim3Active) {
        this.dim3Active = dim3Active;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getDimension3Description() {
        return dimension3Description;
    }

    public void setDimension3Description(String dimension3Description) {
        this.dimension3Description = dimension3Description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDimension() {
        return dimension1 == null ? dimension3 : dimension1;
    }

//    public List<SalesItemSize> getSizeList() {
//        return sizeList;
//    }
//
//    public void setSizeList(List<SalesItemSize> sizeList) {
//        this.sizeList = sizeList;
//    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getWebstoreType() {
        return webstoreType;
    }

    public void setWebstoreType(String webstoreType) {
        this.webstoreType = webstoreType;
    }

    public boolean isShowAddInfo() {
        return showAddInfo;
    }

    public void setShowAddInfo(boolean showAddInfo) {
        this.showAddInfo = showAddInfo;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    public String getOperatorTelNo() {
        return operatorTelNo;
    }

    public void setOperatorTelNo(String operatorTelNo) {
        this.operatorTelNo = operatorTelNo;
    }

    public String getSpecialistTelNo() {
        return specialistTelNo;
    }

    public void setSpecialistTelNo(String specialistTelNo) {
        this.specialistTelNo = specialistTelNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getPriceVal() {
        return priceVal;
    }

    public void setPriceVal(String priceVal) {
        this.priceVal = priceVal;
    }
    
    

}
