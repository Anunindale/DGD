/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.movestock;

import emc.app.components.emcJProgressBar;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
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
public class DataFetchThread {

    private emcJProgressBar progressBar;
    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataRelation;
    private String warehouse;
    private String location;

    public DataFetchThread(emcJProgressBar progressBar, emcDataRelationManagerUpdate dataRelation, String warehouse, String location) {
        this.progressBar = progressBar;
        this.dataRelation = dataRelation;
        this.userData = dataRelation.getUserData();
        this.location = location;
        this.warehouse = warehouse;
        this.fetch();
    }

    public void fetch() {
        progressBar.setIndeterminate(true);
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.POPULATE_MOVE_STOCK.toString());
        List toSend = new ArrayList();
        toSend.add(warehouse);
        toSend.add(location);
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
        query.addTableAnd(InventoryItemMaster.class.getName(), "itemId", InventoryMoveStockMaster.class.getName(), "itemId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension1Id", InventoryDimension1.class, "dimensionId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension2Id", InventoryDimension2.class, "dimensionId");
        query.addLeftOuterJoin(InventoryDimensionTable.class, "dimension3Id", InventoryDimension3.class, "dimensionId");
        query.addAnd("warehouseId", warehouse, InventoryDimensionTable.class.getName());
        query.addAnd("locationId", location, InventoryDimensionTable.class.getName());
        query.addAnd("posted", false, InventoryMoveStockMaster.class.getName());
        query.addAnd("masterSessionId", sessionId);
        query.addOrderBy("itemReference", InventoryItemMaster.class.getName());
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        query.addOrderBy(new String[]{"batchId", "itemSerialId", "palletId"}, InventoryDimensionTable.class.getName());
        List ud = new ArrayList();
        ud.add(query);
        userData.setUserData(ud);
        dataRelation.setUserData(userData);
        progressBar.setIndeterminate(false);
        dataRelation.setOriginalQuery(query);
    }
}
