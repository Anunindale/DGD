/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop;

import emc.datatypes.EMCDataType;
import emc.datatypes.sop.garmentrequirementspersales.Dimension1;
import emc.datatypes.sop.garmentrequirementspersales.Dimension3;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "SOPGarmentRequirementsPerSales", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userSessionId", "itemId", "dimension1", "dimension3", "companyId"})})
public class SOPGarmentRequirementsPerSales extends EMCEntityClass {

    private String userSessionId;
    private String itemId;
    private String dimension1;
    private String dimension3;
    private BigDecimal sizeRequirements1 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements2 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements3 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements4 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements5 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements6 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements7 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements8 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements9 = BigDecimal.ZERO;
    private BigDecimal sizeRequirements10 = BigDecimal.ZERO;

    public SOPGarmentRequirementsPerSales() {
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public BigDecimal getSizeRequirements1() {
        return sizeRequirements1;
    }

    public void setSizeRequirements1(BigDecimal sizeRequirements1) {
        this.sizeRequirements1 = sizeRequirements1;
    }

    public BigDecimal getSizeRequirements2() {
        return sizeRequirements2;
    }

    public void setSizeRequirements2(BigDecimal sizeRequirements2) {
        this.sizeRequirements2 = sizeRequirements2;
    }

    public BigDecimal getSizeRequirements3() {
        return sizeRequirements3;
    }

    public void setSizeRequirements3(BigDecimal sizeRequirements3) {
        this.sizeRequirements3 = sizeRequirements3;
    }

    public BigDecimal getSizeRequirements4() {
        return sizeRequirements4;
    }

    public void setSizeRequirements4(BigDecimal sizeRequirements4) {
        this.sizeRequirements4 = sizeRequirements4;
    }

    public BigDecimal getSizeRequirements5() {
        return sizeRequirements5;
    }

    public void setSizeRequirements5(BigDecimal sizeRequirements5) {
        this.sizeRequirements5 = sizeRequirements5;
    }

    public BigDecimal getSizeRequirements6() {
        return sizeRequirements6;
    }

    public void setSizeRequirements6(BigDecimal sizeRequirements6) {
        this.sizeRequirements6 = sizeRequirements6;
    }

    public BigDecimal getSizeRequirements7() {
        return sizeRequirements7;
    }

    public void setSizeRequirements7(BigDecimal sizeRequirements7) {
        this.sizeRequirements7 = sizeRequirements7;
    }

    public BigDecimal getSizeRequirements8() {
        return sizeRequirements8;
    }

    public void setSizeRequirements8(BigDecimal sizeRequirements8) {
        this.sizeRequirements8 = sizeRequirements8;
    }

    public BigDecimal getSizeRequirements9() {
        return sizeRequirements9;
    }

    public void setSizeRequirements9(BigDecimal sizeRequirements9) {
        this.sizeRequirements9 = sizeRequirements9;
    }

    public BigDecimal getSizeRequirements10() {
        return sizeRequirements10;
    }

    public void setSizeRequirements10(BigDecimal sizeRequirements10) {
        this.sizeRequirements10 = sizeRequirements10;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("dimension1", new Dimension1());
        toBuild.put("dimension3", new Dimension3());
        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("createdBy", null);
        return query;
    }
}
