package emc.app.components.emctable.stock;

import emc.app.components.emctable.*;
import emc.app.components.emcDialogue;
import emc.framework.EMCUserData;
import javax.swing.JOptionPane;

/**
 * 
 * @author wikus
 */
public class ViewOnlyStockDRM extends StockDRM {

    public ViewOnlyStockDRM() {
        super();
    }

    public ViewOnlyStockDRM(emcGenericDataSourceUpdate tableDataSource, StockDRMParameters param, EMCUserData userData) {
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
    //showMessage();
    }

    private void showMessage() {
        emcDialogue resetOK = new emcDialogue("Info", "This form is view only.",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        if (resetOK.getDialogueResult() == JOptionPane.OK_OPTION) {
        }
    }
}
