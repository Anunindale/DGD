/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesorderpostregister;

import emc.bus.inventory.register.superregister.InventoryRegisterSuperBean;
import emc.entity.inventory.register.InventoryRegisterSuper;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderPostRegister;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesOrderPostRegisterBean extends InventoryRegisterSuperBean implements SOPSalesOrderPostRegisterLocal {

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            SOPSalesOrderPostRegister record = (SOPSalesOrderPostRegister) theRecord;
            if (fieldNameToValidate.equals("quantity")) {
                Object qtyRet = validateQuantity(record, userData);
                if (qtyRet instanceof InventoryRegisterSuper) {
                    return (SOPSalesOrderPostRegister) qtyRet;
                } else {
                    return false;
                }
            }
        }
        return ret;
    }
    
    @Override
    protected boolean validateSave(InventoryRegisterSuper record, EMCUserData userData) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderLines.class);
            query.addAnd("inventTransId", record.getTransId());
            SOPSalesOrderLines soLine = (SOPSalesOrderLines) util.executeSingleResultQuery(query, userData);
            List fields = isRegistrationRequired(soLine.getItemId(), userData);
            if (isBlank(record.getSerial()) && fields.contains("serial")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Serial Number is mandatory, please enter a value.", userData);
                return false;
            }
            if (isBlank(record.getBatch()) && fields.contains("batch")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Batch Number is mandatory, please enter a value.", userData);
                return false;
            }
            if (isBlank(record.getLocation()) && fields.contains("location")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Location is mandatory, please enter a value.", userData);
                return false;
            }
            if (isBlank(record.getWarehouse()) && fields.contains("warehouse")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Warehouse is mandatory, please enter a value.", userData);
                return false;
            }
            if (isBlank(record.getPallet()) && fields.contains("pallet")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Pallet is mandatory, please enter a value.", userData);
                return false;
            }
        updateRegisteredQty(record, userData);
        return true;
    }

    @Override
    protected void updateRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.UPDATE, SOPSalesOrderPostRegister.class);
        query.addSet("registeredQty", record.getRegisteredQty());
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        util.executeUpdate(query, userData);
    }

    @Override
    protected double getRegisteredQty(InventoryRegisterSuper record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderPostRegister.class);
        query.addAnd("masterId", record.getMasterId());
        query.addAnd("transId", record.getTransId());
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        query.addFieldAggregateFunction("quantity", "SUM");
        Object res = util.executeSingleResultQuery(query, userData);
        return res == null ? 0.0 : (Double) res;
    }

    protected Object validateQuantity(SOPSalesOrderPostRegister record, EMCUserData userData) {
        boolean ret = (Boolean) super.validateQuantity(record, userData);
        if (ret) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderPostRegister.class);
            query.addAnd("masterId", record.getMasterId());
            query.addAnd("transId", record.getTransId());
            query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
            query.addFieldAggregateFunction("quantity", "SUM");
            Double queryQty = (Double) util.executeSingleResultQuery(query, userData);
            double regQty = (queryQty == null ? 0d : queryQty) + record.getQuantity();
            double total = record.getTotalQty();
            if (util.compareDouble(record.getTotalQty(), 0) > 0 ? util.compareDouble(regQty, total) > 0 : util.compareDouble(regQty, total) < 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "The register quantity will be exceeded.", userData);
                return false;
            } else {
                record.setRegisteredQty(regQty);
                return record;
            }
        }
        return ret;
    }
}
