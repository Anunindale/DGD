/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description : Helper class used to store Debtors aging information
 *
 * @date        : 21 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsAgingHelper {

    private String binName;
    private BigDecimal binAmount = new BigDecimal(0);
    private Date binStartDate;
    private Date binEndDate;
   
    /** Creates a new instance of DebtorsAgingHelper */
    public DebtorsAgingHelper() {

    }

    public String getBinName() {
        return binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
    }

    public BigDecimal getBinAmount() {
        return binAmount;
    }

    public void setBinAmount(BigDecimal binAmount) {
        this.binAmount = binAmount;
    }

    public Date getBinStartDate() {
        return binStartDate;
    }

    public void setBinStartDate(Date binStartDate) {
        this.binStartDate = binStartDate;
    }

    public Date getBinEndDate() {
        return binEndDate;
    }

    public void setBinEndDate(Date binEndDate) {
        this.binEndDate = binEndDate;
    }
}
