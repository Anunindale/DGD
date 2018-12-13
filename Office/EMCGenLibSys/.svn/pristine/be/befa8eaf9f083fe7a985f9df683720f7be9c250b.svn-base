/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECParameters", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class TRECParameters extends EMCEntityClass {

    private boolean printExpiryDate =true;
    private String printHazZone;
    private int maxPhrases ;

    public boolean isPrintExpiryDate() {
        return printExpiryDate;
    }

    public void setPrintExpiryDate(boolean printExpiryDate) {
        this.printExpiryDate = printExpiryDate;
    }

    /**
     * @return the printHazZone
     */
    public String getPrintHazZone() {
        return printHazZone;
    }

    /**
     * @param printHazZone the printHazZone to set
     */
    public void setPrintHazZone(String printHazZone) {
        this.printHazZone = printHazZone;
    }

    public int getMaxPhrases() {
        return maxPhrases;
    }

    public void setMaxPhrases(int maxPhrases) {
        this.maxPhrases = maxPhrases;
    }

 
}
