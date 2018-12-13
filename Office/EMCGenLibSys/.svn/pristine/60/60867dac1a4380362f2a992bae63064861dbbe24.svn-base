/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.royalty;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsRoyaltyReportTempDS")
public class DebtorsRoyaltyReportTempDS extends EMCEntityClass {

    private String royaltyGroup;
    private String royaltyField1;
    private String royaltyField2;
    private String royaltyField3;
    private BigDecimal units = BigDecimal.ZERO;
    private BigDecimal packs = BigDecimal.ZERO;
    private BigDecimal grossValue = BigDecimal.ZERO;
    private BigDecimal royaltyValue = BigDecimal.ZERO;
    private BigDecimal royaltyPercentage = BigDecimal.ZERO;
    //N & L specific
    private boolean printLessJockeyShops;
    private BigDecimal jockeyShopGrossValue = BigDecimal.ZERO;
    private BigDecimal jockeyShopUnits = BigDecimal.ZERO;
    private BigDecimal jockeyShopPacks = BigDecimal.ZERO;
    private BigDecimal jockeyShopRoyaltyValue = BigDecimal.ZERO;

    private long sessionId;
    
    /** Creates a new instance of DebtorsRoyaltyReportTempDS. */
    public DebtorsRoyaltyReportTempDS() {
        
    }

    public String getRoyaltyGroup() {
        return royaltyGroup;
    }

    public void setRoyaltyGroup(String royaltyGroup) {
        this.royaltyGroup = royaltyGroup;
    }

    public String getRoyaltyField1() {
        return royaltyField1;
    }

    public void setRoyaltyField1(String royaltyField1) {
        this.royaltyField1 = royaltyField1;
    }

    public String getRoyaltyField2() {
        return royaltyField2;
    }

    public void setRoyaltyField2(String royaltyField2) {
        this.royaltyField2 = royaltyField2;
    }

    public String getRoyaltyField3() {
        return royaltyField3;
    }

    public void setRoyaltyField3(String royaltyField3) {
        this.royaltyField3 = royaltyField3;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public BigDecimal getRoyaltyValue() {
        return royaltyValue;
    }

    public void setRoyaltyValue(BigDecimal royaltyValue) {
        this.royaltyValue = royaltyValue;
    }

    public BigDecimal getRoyaltyPercentage() {
        return royaltyPercentage;
    }

    public void setRoyaltyPercentage(BigDecimal royaltyPercentage) {
        this.royaltyPercentage = royaltyPercentage;
    }

    public BigDecimal getJockeyShopGrossValue() {
        return jockeyShopGrossValue;
    }

    public void setJockeyShopGrossValue(BigDecimal jockeyShopGrossValue) {
        this.jockeyShopGrossValue = jockeyShopGrossValue;
    }

    public BigDecimal getJockeyShopUnits() {
        return jockeyShopUnits;
    }

    public void setJockeyShopUnits(BigDecimal jockeyShopUnits) {
        this.jockeyShopUnits = jockeyShopUnits;
    }

    public BigDecimal getJockeyShopRoyaltyValue() {
        return jockeyShopRoyaltyValue;
    }

    public void setJockeyShopRoyaltyValue(BigDecimal jockeyShopRoyaltyValue) {
        this.jockeyShopRoyaltyValue = jockeyShopRoyaltyValue;
    }

    public boolean isPrintLessJockeyShops() {
        return printLessJockeyShops;
    }

    public void setPrintLessJockeyShops(boolean printLessJockeyShops) {
        this.printLessJockeyShops = printLessJockeyShops;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public BigDecimal getPacks() {
        return packs;
    }

    public void setPacks(BigDecimal packs) {
        this.packs = packs;
    }

    public BigDecimal getJockeyShopPacks() {
        return jockeyShopPacks;
    }

    public void setJockeyShopPacks(BigDecimal jockeyShopPacks) {
        this.jockeyShopPacks = jockeyShopPacks;
    }
}
