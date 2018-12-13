/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.formlookup.controllookup;

import emc.app.components.emcDialogue;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.framework.EMCUserData;
import javax.swing.JOptionPane;

/**
 * @Name         : ViewOnlyStockControlLookupDRM
 *
 * @Date         : 19 Jun 2009
 * 
 * @Description  : View only stock control lookup data relation manager.
 *
 * @author       : riaan
 */
public class ViewOnlyStockControlLookupDRM extends StockControlLookupDRM {

    /** Creates a new instance of ViewOnlyStockControlLookupDRM. */
    public ViewOnlyStockControlLookupDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
        super(tableDataSource, param, userData);
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        showMessage();
    }

    @Override
    public void updatePersist(int rowIndex) {
        showMessage();
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        showMessage();
    }

    @Override
    public boolean persistOnClosing() {
        return true;
    }

    private void showMessage() {
        emcDialogue resetOK = new emcDialogue("Info", "This form is view only.",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        if (resetOK.getDialogueResult() == JOptionPane.OK_OPTION) {
        }
    }
}
