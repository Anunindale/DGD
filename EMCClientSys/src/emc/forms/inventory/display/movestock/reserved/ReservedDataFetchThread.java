/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock.reserved;

import emc.app.components.emcJProgressBar;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class ReservedDataFetchThread {

    private emcJProgressBar progressBar;
    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataRelation;
    private String warehouse;
    private String location;
    private String salesOrder;
    private String worksOrder;

    public ReservedDataFetchThread(emcJProgressBar progressBar, emcDataRelationManagerUpdate dataRelation, String warehouse, String location, String salesOrder, String worksOrder) {
        this.progressBar = progressBar;
        this.dataRelation = dataRelation;
        this.userData = dataRelation.getUserData();
        this.location = location;
        this.warehouse = warehouse;
        this.salesOrder = salesOrder;
        this.worksOrder = worksOrder;
        this.fetch();
    }

    public void fetch() {
        progressBar.setIndeterminate(true);
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.POPULATE_MOVE_RESERVED_STOCK.toString());
        List toSend = new ArrayList();
        toSend.add(warehouse);
        toSend.add(location);
        toSend.add(salesOrder);
        toSend.add(worksOrder);
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        String sessionId = "NODATA";
        if (toSend.size() > 1) {
            sessionId = toSend.get(1).toString();
        } else {
            Logger.getLogger("emc").log(Level.INFO, "No Data found.", userData);
        }
        //FETCH DATA
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class.getName());
        query.addTableAnd(InventoryDimensionTable.class.getName(), "dimensionId", InventoryMoveStockMaster.class.getName(), "recordID");
        query.addAnd("warehouseId", warehouse, InventoryDimensionTable.class.getName());
        query.addAnd("locationId", location, InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        query.addAnd("masterSessionId", sessionId);
        query.addOrderBy("batchId", InventoryDimensionTable.class.getName());
        List ud = new ArrayList();
        ud.add(query);
        userData.setUserData(ud);
        dataRelation.setUserData(userData);
        progressBar.setIndeterminate(false);
        dataRelation.setOriginalQuery(query);
    }
}
