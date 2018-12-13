/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.pop.ServerPOPMethods;
import java.util.ArrayList;

/**
 *
 * @author riaan
 */
public class PurchasePostSetupDRM extends emcDataRelationManagerUpdate {

    /** Keeps a query indicating the purchase orders to post. */
    private String selectQuery;
    private PurchasePostMasterDRM masterDRM;

    /** Creates a new instance of PurchasePostSetupDRM */
    public PurchasePostSetupDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);

        selectQuery = userData.getUserData().get(0).toString();

        //On form, documents not yet set up when constructor executes
        //this.doRelation(0);
    }

    /** Set master data relation manager and get data */
    public void setMasterDRM(PurchasePostMasterDRM masterDRM) {
        this.masterDRM = masterDRM;
        masterDRM.setSetupDRM(this);
        generateMasterUserData();
        masterDRM.getUserData().setUserData(3, this.getUserData().getUserData(3));
    }

    /** This method generates user data for the master data relation manager */
    private void generateMasterUserData() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostMaster.class.getName());
        query.addAnd("postSetupId", this.getFieldValueAt(this.getLastRowAccessed(), "postSetupId"));

        EMCUserData masterUserData = masterDRM.getUserData().copyUserData();
        masterUserData.setUserData(0, query.toString());
        masterUserData.setUserData(1, query.getCountQuery());

        //Set user data again, in order to refresh
        masterDRM.setUserData(masterUserData);
    }

    /** This method is overridden in order to update master and lines, and save when quantity selection is changed */
    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        updatePersist(rowIndex);
        if (columnIndex.equals("quantitySelection")) {

            generatePost(false);

            generateMasterUserData();
        }
    }

    public void generatePost(boolean populateAll) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.CREATE_POST.toString());

        ArrayList toSend = new ArrayList();
        toSend.add(this.getFieldValueAt(this.getLastRowAccessed(), "postSetupId"));
        toSend.add(populateAll);

        EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());
    }
}
