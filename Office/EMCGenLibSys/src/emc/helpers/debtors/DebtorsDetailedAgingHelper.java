/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description : This helper class is used to group DebtorsDetailedAgingLineDS instances together, based on the bins into which they fall.
 *
 * @date        : 08 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class DebtorsDetailedAgingHelper {

    private String binName;
    private Date binStartDate;
    private Date binEndDate;
    private List<DebtorsDetailedAgingLineDS> agingLines = new ArrayList<DebtorsDetailedAgingLineDS>();

    /** Creates a new instance of DebtorsDetailedAgingHelper */
    public DebtorsDetailedAgingHelper() {

    }

    public String getBinName() {
        return binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
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

    public List<DebtorsDetailedAgingLineDS> getAgingLines() {
        return agingLines;
    }

    public void addAgingLine(DebtorsDetailedAgingLineDS agingLine) {
        this.agingLines.add(agingLine);
    }
}
