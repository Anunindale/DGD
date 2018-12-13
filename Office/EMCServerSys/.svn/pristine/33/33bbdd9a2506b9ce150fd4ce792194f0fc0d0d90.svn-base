/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.inventory.movestock;

import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.movestock.InventoryMoveStockLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.movestock.InventoryMoveStockMasterDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMoveStockLinesBean extends EMCEntityBean implements InventoryMoveStockLinesLocal {

    @EJB
    private InventoryMoveStockMasterDSLocal masterBean;

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            InventoryMoveStockLines line = (InventoryMoveStockLines) theRecord;
            if (line.getMasterId() == 0) {
                return false;
            }
            EMCQuery query;
            InventoryMoveStockMasterDS master = getMasterRecord(line.getMasterId(), userData);
            if (fieldNameToValidate.equals("quantity")) {
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockLines.class.getName());
                query.addAnd("masterId", line.getMasterId());
                query.addGroupBy("masterId");
                query.addFieldAggregateFunction("quantity", InventoryMoveStockLines.class.getName(), "SUM");
                Object o = util.executeSingleResultQuery(query, userData);
                double linesTotal = line.getQuantity();
                if (o != null) {
                    linesTotal += (Double) o;
                }
                if (linesTotal > master.getAvailableQty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The quantity to move can't be greater than the available quantity.", userData);
                    ret = false;
                }
            } else if (fieldNameToValidate.equals("toLocation")) {
                if (line.getToLocation().equals("QC")) {
                    logMessage(Level.SEVERE, "Stock may not be moved back into QC.", userData);
                    return false;
                }
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                query.addAnd("locationId", line.getToLocation());
                query.addAnd("warehouseId", master.getWarehouse());
                if (util.exists(query, userData)) {
                    ret = true;
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "The specified location is not found in the selected warehouse.", userData);
                    ret = false;
                }
            }
        }
        return ret;
    }

    private InventoryMoveStockMasterDS getMasterRecord(long masterId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class.getName());
        query.addAnd("recordID", masterId);
        InventoryMoveStockMaster master = (InventoryMoveStockMaster) util.executeSingleResultQuery(query, userData);
        return (InventoryMoveStockMasterDS) masterBean.populateDataSourceFields(
                (InventoryMoveStockMasterDS) util.convertEntityToDatasource(master, InventoryMoveStockMasterDS.class, userData), userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            InventoryMoveStockLines line = (InventoryMoveStockLines) vobject;
            if ((line.getQuantity() != 0 || !isBlank(line.getToLocation())) && (line.getQuantity() == 0 || isBlank(line.getToLocation()))) {
                Logger.getLogger("emc").log(Level.SEVERE, "Both Quantity and Location needs to be specified.", userData);
                return false;
            }
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            InventoryMoveStockLines line = (InventoryMoveStockLines) vobject;
            if ((line.getQuantity() != 0 || !isBlank(line.getToLocation())) && (line.getQuantity() == 0 || isBlank(line.getToLocation()))) {
                Logger.getLogger("emc").log(Level.SEVERE, "Both Quantity and Location needs to be specified.", userData);
                return false;
            }
        }
        return ret;
    }
}
