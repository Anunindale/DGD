/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.additionaldimensions.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.swing.JOptionPane;

/**
 * @Name         : AdditionalDimensionsDRM
 *
 * @Date         : 29 Jun 2009
 * 
 * @Description  : Data relation manager for the Additional Dimensions form.
 *
 * @author       : riaan
 */
public class AdditionalDimensionsDRM extends StockDRM {

    /** Creates a new instance of AdditionalDimensionsDRM. */
    public AdditionalDimensionsDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        if (Index != 10) {
            return super.generateRelatedFormUserData(formUserData, Index);
        } else {
            //Transactions
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryTransactions.class.getName());
            query.addAnd("itemId", getLastFieldValueAt("itemId"));
            
            formUserData.setUserData(0, query.toString());
            formUserData.setUserData(1, query.getCountQuery());
            
            return formUserData;
        } 
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        showMessage();
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        showMessage();
    }
    
    private void showMessage() {
        emcDialogue resetOK = new emcDialogue("Info", "This form is view only.",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        if (resetOK.getDialogueResult() == JOptionPane.OK_OPTION) {
        }
    }

}
