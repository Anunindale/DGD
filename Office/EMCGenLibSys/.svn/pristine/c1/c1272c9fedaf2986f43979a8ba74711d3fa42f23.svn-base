/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory.warehousestockenquiry;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.journals.superclass.Description;
import emc.datatypes.debtors.creditnoteregister.TotalQuantity;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.Classification1;
import emc.datatypes.inventory.itemmaster.Classification5;
import emc.datatypes.inventory.itemreference.ItemReferenceIdNotMandatory;
import emc.datatypes.inventory.location.foreignkeys.LocationFKNotManditory;
import emc.datatypes.inventory.productiongroup.foreignkeys.ProductGroupIdFKNM;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventoryWarehouseStockEnquiryBySize")
public class InventoryWarehouseStockEnquiryBySize extends EMCEntityClass {

    private String recordOwner;
    private String itemId;
    private String itemReference;
    private String itemDescription;
    private String productGroup;
    private String planningGroup;
    private String classification1;
    private String classification5;
    private String brandGroup;
    private String dimension1;
    private String dimension3;
    private String location;
    private BigDecimal quantity1 = BigDecimal.ZERO;
    private BigDecimal quantity2 = BigDecimal.ZERO;
    private BigDecimal quantity3 = BigDecimal.ZERO;
    private BigDecimal quantity4 = BigDecimal.ZERO;
    private BigDecimal quantity5 = BigDecimal.ZERO;
    private BigDecimal quantity6 = BigDecimal.ZERO;
    private BigDecimal quantity7 = BigDecimal.ZERO;
    private BigDecimal quantity8 = BigDecimal.ZERO;
    private BigDecimal quantity9 = BigDecimal.ZERO;
    private BigDecimal quantity10 = BigDecimal.ZERO;
    private BigDecimal quantity11 = BigDecimal.ZERO;
    private BigDecimal quantity12 = BigDecimal.ZERO;
    private BigDecimal quantity13 = BigDecimal.ZERO;
    private BigDecimal quantity14 = BigDecimal.ZERO;
    private BigDecimal quantity15 = BigDecimal.ZERO;
    private BigDecimal quantity16 = BigDecimal.ZERO;
    private BigDecimal quantity17 = BigDecimal.ZERO;
    private BigDecimal quantity18 = BigDecimal.ZERO;
    private BigDecimal quantity19 = BigDecimal.ZERO;
    private BigDecimal quantity20 = BigDecimal.ZERO;
    private BigDecimal quantity21 = BigDecimal.ZERO;
    private BigDecimal quantity22 = BigDecimal.ZERO;
    private BigDecimal quantity23 = BigDecimal.ZERO;
    private BigDecimal quantity24 = BigDecimal.ZERO;
    private BigDecimal quantity25 = BigDecimal.ZERO;
    private BigDecimal quantity26 = BigDecimal.ZERO;
    private BigDecimal quantity27 = BigDecimal.ZERO;
    private BigDecimal quantity28 = BigDecimal.ZERO;
    private BigDecimal quantity29 = BigDecimal.ZERO;
    private BigDecimal quantity30 = BigDecimal.ZERO;
    private BigDecimal quantity31 = BigDecimal.ZERO;
    private BigDecimal quantity32 = BigDecimal.ZERO;
    private BigDecimal quantity33 = BigDecimal.ZERO;
    private BigDecimal quantity34 = BigDecimal.ZERO;
    private BigDecimal quantity35 = BigDecimal.ZERO;
    private BigDecimal quantity36 = BigDecimal.ZERO;
    private BigDecimal quantity37 = BigDecimal.ZERO;
    private BigDecimal quantity38 = BigDecimal.ZERO;
    private BigDecimal quantity39 = BigDecimal.ZERO;
    private BigDecimal quantity40 = BigDecimal.ZERO;
    private BigDecimal quantity41 = BigDecimal.ZERO;
    private BigDecimal quantity42 = BigDecimal.ZERO;
    private BigDecimal quantity43 = BigDecimal.ZERO;
    private BigDecimal quantity44 = BigDecimal.ZERO;
    private BigDecimal quantity45 = BigDecimal.ZERO;
    private BigDecimal quantity46 = BigDecimal.ZERO;
    private BigDecimal quantity47 = BigDecimal.ZERO;
    private BigDecimal quantity48 = BigDecimal.ZERO;
    private BigDecimal quantity49 = BigDecimal.ZERO;
    private BigDecimal quantity50 = BigDecimal.ZERO;
    private BigDecimal quantity51 = BigDecimal.ZERO;
    private BigDecimal quantity52 = BigDecimal.ZERO;
    private BigDecimal quantity53 = BigDecimal.ZERO;
    private BigDecimal quantity54 = BigDecimal.ZERO;
    private BigDecimal quantity55 = BigDecimal.ZERO;
    private BigDecimal quantity56 = BigDecimal.ZERO;
    private BigDecimal quantity57 = BigDecimal.ZERO;
    private BigDecimal quantity58 = BigDecimal.ZERO;
    private BigDecimal quantity59 = BigDecimal.ZERO;
    private BigDecimal quantity60 = BigDecimal.ZERO;
    private BigDecimal quantityTotal = BigDecimal.ZERO;

    public InventoryWarehouseStockEnquiryBySize() {
    }

    public InventoryWarehouseStockEnquiryBySize(String recordOwner, String itemId, String itemReference, String itemDescription, String productGroup,
            String planningGroup, String classification1, String classification5, String brandGroup, String dimension1, String dimension3, String location,
            BigDecimal quantity1,
            BigDecimal quantity2, BigDecimal quantity3, BigDecimal quantity4, BigDecimal quantity5, BigDecimal quantity6, BigDecimal quantity7,
            BigDecimal quantity8, BigDecimal quantity9, BigDecimal quantity10, BigDecimal quantity11, BigDecimal quantity12, BigDecimal quantity13,
            BigDecimal quantity14, BigDecimal quantity15, BigDecimal quantity16, BigDecimal quantity17, BigDecimal quantity18, BigDecimal quantity19,
            BigDecimal quantity20, BigDecimal quantity21, BigDecimal quantity22, BigDecimal quantity23, BigDecimal quantity24, BigDecimal quantity25,
            BigDecimal quantity26, BigDecimal quantity27, BigDecimal quantity28, BigDecimal quantity29, BigDecimal quantity30, BigDecimal quantity31,
            BigDecimal quantity32, BigDecimal quantity33, BigDecimal quantity34, BigDecimal quantity35, BigDecimal quantity36, BigDecimal quantity37,
            BigDecimal quantity38, BigDecimal quantity39, BigDecimal quantity40, BigDecimal quantity41, BigDecimal quantity42, BigDecimal quantity43,
            BigDecimal quantity44, BigDecimal quantity45, BigDecimal quantity46, BigDecimal quantity47, BigDecimal quantity48, BigDecimal quantity49,
            BigDecimal quantity50, BigDecimal quantity51, BigDecimal quantity52, BigDecimal quantity53, BigDecimal quantity54, BigDecimal quantity55,
            BigDecimal quantity56, BigDecimal quantity57, BigDecimal quantity58, BigDecimal quantity59, BigDecimal quantity60, BigDecimal quantityTotal) {
        this.recordOwner = recordOwner;
        this.itemId = itemId;
        this.itemReference = itemReference;
        this.itemDescription = itemDescription;
        this.productGroup = productGroup;
        this.planningGroup = planningGroup;
        this.classification1 = classification1;
        this.classification5 = classification5;
        this.brandGroup = brandGroup;
        this.dimension1 = dimension1;
        this.dimension3 = dimension3;
        this.location = location;
        this.quantity1 = quantity1;
        this.quantity2 = quantity2;
        this.quantity3 = quantity3;
        this.quantity4 = quantity4;
        this.quantity5 = quantity5;
        this.quantity6 = quantity6;
        this.quantity7 = quantity7;
        this.quantity8 = quantity8;
        this.quantity9 = quantity9;
        this.quantity10 = quantity10;
        this.quantity11 = quantity11;
        this.quantity12 = quantity12;
        this.quantity13 = quantity13;
        this.quantity14 = quantity14;
        this.quantity15 = quantity15;
        this.quantity16 = quantity16;
        this.quantity17 = quantity17;
        this.quantity18 = quantity18;
        this.quantity19 = quantity19;
        this.quantity20 = quantity20;
        this.quantity21 = quantity21;
        this.quantity22 = quantity22;
        this.quantity23 = quantity23;
        this.quantity24 = quantity24;
        this.quantity25 = quantity25;
        this.quantity26 = quantity26;
        this.quantity27 = quantity27;
        this.quantity28 = quantity28;
        this.quantity29 = quantity29;
        this.quantity30 = quantity30;
        this.quantity31 = quantity31;
        this.quantity32 = quantity32;
        this.quantity33 = quantity33;
        this.quantity34 = quantity34;
        this.quantity35 = quantity35;
        this.quantity36 = quantity36;
        this.quantity37 = quantity37;
        this.quantity38 = quantity38;
        this.quantity39 = quantity39;
        this.quantity40 = quantity40;
        this.quantity41 = quantity41;
        this.quantity42 = quantity42;
        this.quantity43 = quantity43;
        this.quantity44 = quantity44;
        this.quantity45 = quantity45;
        this.quantity46 = quantity46;
        this.quantity47 = quantity47;
        this.quantity48 = quantity48;
        this.quantity49 = quantity49;
        this.quantity50 = quantity50;
        this.quantity51 = quantity51;
        this.quantity52 = quantity52;
        this.quantity53 = quantity53;
        this.quantity54 = quantity54;
        this.quantity55 = quantity55;
        this.quantity56 = quantity56;
        this.quantity57 = quantity57;
        this.quantity58 = quantity58;
        this.quantity59 = quantity59;
        this.quantity60 = quantity60;
        this.quantityTotal = quantityTotal;
    }

    public String getBrandGroup() {
        return brandGroup;
    }

    public void setBrandGroup(String brandGroup) {
        this.brandGroup = brandGroup;
    }

    public String getClassification1() {
        return classification1;
    }

    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    public String getClassification5() {
        return classification5;
    }

    public void setClassification5(String classification5) {
        this.classification5 = classification5;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public String getPlanningGroup() {
        return planningGroup;
    }

    public void setPlanningGroup(String planningGroup) {
        this.planningGroup = planningGroup;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public BigDecimal getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(BigDecimal quantity1) {
        this.quantity1 = quantity1;
    }

    public BigDecimal getQuantity10() {
        return quantity10;
    }

    public void setQuantity10(BigDecimal quantity10) {
        this.quantity10 = quantity10;
    }

    public BigDecimal getQuantity11() {
        return quantity11;
    }

    public void setQuantity11(BigDecimal quantity11) {
        this.quantity11 = quantity11;
    }

    public BigDecimal getQuantity12() {
        return quantity12;
    }

    public void setQuantity12(BigDecimal quantity12) {
        this.quantity12 = quantity12;
    }

    public BigDecimal getQuantity13() {
        return quantity13;
    }

    public void setQuantity13(BigDecimal quantity13) {
        this.quantity13 = quantity13;
    }

    public BigDecimal getQuantity14() {
        return quantity14;
    }

    public void setQuantity14(BigDecimal quantity14) {
        this.quantity14 = quantity14;
    }

    public BigDecimal getQuantity15() {
        return quantity15;
    }

    public void setQuantity15(BigDecimal quantity15) {
        this.quantity15 = quantity15;
    }

    public BigDecimal getQuantity16() {
        return quantity16;
    }

    public void setQuantity16(BigDecimal quantity16) {
        this.quantity16 = quantity16;
    }

    public BigDecimal getQuantity17() {
        return quantity17;
    }

    public void setQuantity17(BigDecimal quantity17) {
        this.quantity17 = quantity17;
    }

    public BigDecimal getQuantity18() {
        return quantity18;
    }

    public void setQuantity18(BigDecimal quantity18) {
        this.quantity18 = quantity18;
    }

    public BigDecimal getQuantity19() {
        return quantity19;
    }

    public void setQuantity19(BigDecimal quantity19) {
        this.quantity19 = quantity19;
    }

    public BigDecimal getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(BigDecimal quantity2) {
        this.quantity2 = quantity2;
    }

    public BigDecimal getQuantity20() {
        return quantity20;
    }

    public void setQuantity20(BigDecimal quantity20) {
        this.quantity20 = quantity20;
    }

    public BigDecimal getQuantity21() {
        return quantity21;
    }

    public void setQuantity21(BigDecimal quantity21) {
        this.quantity21 = quantity21;
    }

    public BigDecimal getQuantity22() {
        return quantity22;
    }

    public void setQuantity22(BigDecimal quantity22) {
        this.quantity22 = quantity22;
    }

    public BigDecimal getQuantity23() {
        return quantity23;
    }

    public void setQuantity23(BigDecimal quantity23) {
        this.quantity23 = quantity23;
    }

    public BigDecimal getQuantity24() {
        return quantity24;
    }

    public void setQuantity24(BigDecimal quantity24) {
        this.quantity24 = quantity24;
    }

    public BigDecimal getQuantity25() {
        return quantity25;
    }

    public void setQuantity25(BigDecimal quantity25) {
        this.quantity25 = quantity25;
    }

    public BigDecimal getQuantity26() {
        return quantity26;
    }

    public void setQuantity26(BigDecimal quantity26) {
        this.quantity26 = quantity26;
    }

    public BigDecimal getQuantity27() {
        return quantity27;
    }

    public void setQuantity27(BigDecimal quantity27) {
        this.quantity27 = quantity27;
    }

    public BigDecimal getQuantity28() {
        return quantity28;
    }

    public void setQuantity28(BigDecimal quantity28) {
        this.quantity28 = quantity28;
    }

    public BigDecimal getQuantity29() {
        return quantity29;
    }

    public void setQuantity29(BigDecimal quantity29) {
        this.quantity29 = quantity29;
    }

    public BigDecimal getQuantity3() {
        return quantity3;
    }

    public void setQuantity3(BigDecimal quantity3) {
        this.quantity3 = quantity3;
    }

    public BigDecimal getQuantity30() {
        return quantity30;
    }

    public void setQuantity30(BigDecimal quantity30) {
        this.quantity30 = quantity30;
    }

    public BigDecimal getQuantity31() {
        return quantity31;
    }

    public void setQuantity31(BigDecimal quantity31) {
        this.quantity31 = quantity31;
    }

    public BigDecimal getQuantity32() {
        return quantity32;
    }

    public void setQuantity32(BigDecimal quantity32) {
        this.quantity32 = quantity32;
    }

    public BigDecimal getQuantity33() {
        return quantity33;
    }

    public void setQuantity33(BigDecimal quantity33) {
        this.quantity33 = quantity33;
    }

    public BigDecimal getQuantity34() {
        return quantity34;
    }

    public void setQuantity34(BigDecimal quantity34) {
        this.quantity34 = quantity34;
    }

    public BigDecimal getQuantity35() {
        return quantity35;
    }

    public void setQuantity35(BigDecimal quantity35) {
        this.quantity35 = quantity35;
    }

    public BigDecimal getQuantity36() {
        return quantity36;
    }

    public void setQuantity36(BigDecimal quantity36) {
        this.quantity36 = quantity36;
    }

    public BigDecimal getQuantity37() {
        return quantity37;
    }

    public void setQuantity37(BigDecimal quantity37) {
        this.quantity37 = quantity37;
    }

    public BigDecimal getQuantity38() {
        return quantity38;
    }

    public void setQuantity38(BigDecimal quantity38) {
        this.quantity38 = quantity38;
    }

    public BigDecimal getQuantity39() {
        return quantity39;
    }

    public void setQuantity39(BigDecimal quantity39) {
        this.quantity39 = quantity39;
    }

    public BigDecimal getQuantity4() {
        return quantity4;
    }

    public void setQuantity4(BigDecimal quantity4) {
        this.quantity4 = quantity4;
    }

    public BigDecimal getQuantity40() {
        return quantity40;
    }

    public void setQuantity40(BigDecimal quantity40) {
        this.quantity40 = quantity40;
    }

    public BigDecimal getQuantity41() {
        return quantity41;
    }

    public void setQuantity41(BigDecimal quantity41) {
        this.quantity41 = quantity41;
    }

    public BigDecimal getQuantity42() {
        return quantity42;
    }

    public void setQuantity42(BigDecimal quantity42) {
        this.quantity42 = quantity42;
    }

    public BigDecimal getQuantity43() {
        return quantity43;
    }

    public void setQuantity43(BigDecimal quantity43) {
        this.quantity43 = quantity43;
    }

    public BigDecimal getQuantity44() {
        return quantity44;
    }

    public void setQuantity44(BigDecimal quantity44) {
        this.quantity44 = quantity44;
    }

    public BigDecimal getQuantity45() {
        return quantity45;
    }

    public void setQuantity45(BigDecimal quantity45) {
        this.quantity45 = quantity45;
    }

    public BigDecimal getQuantity46() {
        return quantity46;
    }

    public void setQuantity46(BigDecimal quantity46) {
        this.quantity46 = quantity46;
    }

    public BigDecimal getQuantity47() {
        return quantity47;
    }

    public void setQuantity47(BigDecimal quantity47) {
        this.quantity47 = quantity47;
    }

    public BigDecimal getQuantity48() {
        return quantity48;
    }

    public void setQuantity48(BigDecimal quantity48) {
        this.quantity48 = quantity48;
    }

    public BigDecimal getQuantity49() {
        return quantity49;
    }

    public void setQuantity49(BigDecimal quantity49) {
        this.quantity49 = quantity49;
    }

    public BigDecimal getQuantity5() {
        return quantity5;
    }

    public void setQuantity5(BigDecimal quantity5) {
        this.quantity5 = quantity5;
    }

    public BigDecimal getQuantity50() {
        return quantity50;
    }

    public void setQuantity50(BigDecimal quantity50) {
        this.quantity50 = quantity50;
    }

    public BigDecimal getQuantity51() {
        return quantity51;
    }

    public void setQuantity51(BigDecimal quantity51) {
        this.quantity51 = quantity51;
    }

    public BigDecimal getQuantity52() {
        return quantity52;
    }

    public void setQuantity52(BigDecimal quantity52) {
        this.quantity52 = quantity52;
    }

    public BigDecimal getQuantity53() {
        return quantity53;
    }

    public void setQuantity53(BigDecimal quantity53) {
        this.quantity53 = quantity53;
    }

    public BigDecimal getQuantity54() {
        return quantity54;
    }

    public void setQuantity54(BigDecimal quantity54) {
        this.quantity54 = quantity54;
    }

    public BigDecimal getQuantity55() {
        return quantity55;
    }

    public void setQuantity55(BigDecimal quantity55) {
        this.quantity55 = quantity55;
    }

    public BigDecimal getQuantity56() {
        return quantity56;
    }

    public void setQuantity56(BigDecimal quantity56) {
        this.quantity56 = quantity56;
    }

    public BigDecimal getQuantity57() {
        return quantity57;
    }

    public void setQuantity57(BigDecimal quantity57) {
        this.quantity57 = quantity57;
    }

    public BigDecimal getQuantity58() {
        return quantity58;
    }

    public void setQuantity58(BigDecimal quantity58) {
        this.quantity58 = quantity58;
    }

    public BigDecimal getQuantity59() {
        return quantity59;
    }

    public void setQuantity59(BigDecimal quantity59) {
        this.quantity59 = quantity59;
    }

    public BigDecimal getQuantity6() {
        return quantity6;
    }

    public void setQuantity6(BigDecimal quantity6) {
        this.quantity6 = quantity6;
    }

    public BigDecimal getQuantity60() {
        return quantity60;
    }

    public void setQuantity60(BigDecimal quantity60) {
        this.quantity60 = quantity60;
    }

    public BigDecimal getQuantity7() {
        return quantity7;
    }

    public void setQuantity7(BigDecimal quantity7) {
        this.quantity7 = quantity7;
    }

    public BigDecimal getQuantity8() {
        return quantity8;
    }

    public void setQuantity8(BigDecimal quantity8) {
        this.quantity8 = quantity8;
    }

    public BigDecimal getQuantity9() {
        return quantity9;
    }

    public void setQuantity9(BigDecimal quantity9) {
        this.quantity9 = quantity9;
    }

    public BigDecimal getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(BigDecimal quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public String getRecordOwner() {
        return recordOwner;
    }

    public void setRecordOwner(String recordOwner) {
        this.recordOwner = recordOwner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public EMCQuery getQuery() {
        EMCQuery query = super.getQuery();
        query.addAnd("recordOwner", null);
        query.addGroupBy("recordOwner");
        query.addOrderBy("recordOwner");
        return query;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("itemReference", new ItemReferenceIdNotMandatory());
        toBuild.put("itemDescription", new Description());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());
        toBuild.put("location", new LocationFKNotManditory());
        toBuild.put("quantityTotal", new TotalQuantity());
        toBuild.put("productGroup", new ProductGroupIdFKNM());
        toBuild.put("classification1", new Classification1());
        toBuild.put("classification5", new Classification5());
        return toBuild;
    }
}
