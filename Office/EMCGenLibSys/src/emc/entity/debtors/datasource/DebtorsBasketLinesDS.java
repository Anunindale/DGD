/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.basket.UNNUmber;
import emc.datatypes.debtors.creditheld.Rep;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author riaan
 */
public class DebtorsBasketLinesDS extends DebtorsBasketLines {

    private String unNumber;
   

    /** Creates a new instance of DebtorsCreditHeldMasterDS. */
    public DebtorsBasketLinesDS() {
        this.setDataSource(true);
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("unNumber", new UNNUmber());

        return toBuild;
    }
}
