/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package emc.bus.inventory.movestock;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.movestock.InventoryMoveStockMasterDS;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InventoryMoveStockMasterDSBean extends EMCDataSourceBean implements InventoryMoveStockMasterDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryMoveStockMasterLocal masterBean;
    @EJB
    private InventoryDimensionTableLocal dimensionBean;

    public InventoryMoveStockMasterDSBean() {
        this.setDataSourceClassName(InventoryMoveStockMasterDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) masterBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
        if (ret) {
            InventoryMoveStockMasterDS master = (InventoryMoveStockMasterDS) theRecord;
            EMCQuery query;
            if (fieldNameToValidate.equals("toLocation") && !isBlank(master.getToLocation())) {
                if (master.getToLocation().equals("QC")) {
                    logMessage(Level.SEVERE, "Stock may not be moved back into QC.", userData);
                    return false;
                }
                query = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                query.addAnd("locationId", master.getToLocation());
                query.addAnd("warehouseId", master.getWarehouse());
                if (util.exists(query, userData)) {
                    if (master.isGroupLine()) {
                        try {
                            doGroupedUpdate(master, userData);
                        } catch (EMCEntityBeanException ex) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to do grouped update: " + ex.getMessage(), userData);
                        }
                    } else {
                        if (util.compareDouble(master.getQuantity(), 0) == 0) {
                            master.setQuantity(master.getAvailableQty());
                        }
                        return master;
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "The specified location is not found in the selected warehouse.", userData);
                    ret = false;
                }
            } else if (fieldNameToValidate.equals("quantity")) {
                if (master.getQuantity() > master.getAvailableQty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The quantity to move can't be greater than the available quantity.", userData);
                    ret = false;
                } else {
                    ret = true;
                }
            } else if (fieldNameToValidate.equals("group")) {
                if (master.isSplit()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "A record can't be grouped and split.", userData);
                    ret = false;
                } else {
                    if (master.isGroupLine() && master.getQuantity() != 0) {
                        master.setQuantity(master.getAvailableQty());
                        return master;
                    }
                    ret = true;
                }
            } else if (fieldNameToValidate.equals("split")) {
                if (master.isGroupLine()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "A record can't be split and grouped.", userData);
                    ret = false;
                } else {
                    if (master.isSplit()) {
                        master.setQuantity(0);
                        master.setLocation(null);
                        return master;
                    }
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryMoveStockMasterDS ds = (InventoryMoveStockMasterDS) dataSourceInstance;
        referenceBean.populateDSFields(ds, userData);
        dimensionBean.populateDimensions(ds, userData);
        return ds;
    }

    private void doGroupedUpdate(InventoryMoveStockMasterDS lds, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryMoveStockMaster.class.getName());
        query.addAnd("groupLine", true);
        query.addAnd("recordID", lds.getRecordID(), EMCQueryConditions.NOT);
        query.addAnd("posted", false);
        List<InventoryMoveStockMaster> msmList = util.executeGeneralSelectQuery(query, userData);
        InventoryMoveStockMasterDS ds;
        String location = lds.getToLocation();
        for (InventoryMoveStockMaster msm : msmList) {
            ds = (InventoryMoveStockMasterDS) this.populateDataSourceFields((InventoryMoveStockMasterDS) util.convertEntityToDatasource(
                    msm, InventoryMoveStockMasterDS.class, userData), userData);
            if (ds.getAvailableQty() != 0) {
                ds.setToLocation(location);
                ds.setQuantity(ds.getAvailableQty());
                this.update(ds, userData);
            }
        }
        if (lds.getAvailableQty() != 0) {
            lds.setToLocation(location);
            lds.setQuantity(lds.getAvailableQty());
            this.update(lds, userData);
        }
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(this.convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        super.checkCompanyId(query, userData);
        List dataList = util.executeLimitedNativeQuery(query, InventoryMoveStockMaster.class, start, end, userData);
        List dsList = new ArrayList();
        for (Object instance : dataList) {
            dsList.add(populateDataSourceFields((EMCEntityClass) convertSuperToDataSource(instance, userData), userData));
        }
        return dsList;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        return util.executeNativeQuery(query, InventoryMoveStockMaster.class, userData).size() + ", 0";

    }
}
