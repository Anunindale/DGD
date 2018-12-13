/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.datasource;

import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.colourdesignmaster.InventoryColourDisignMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMasterLocal;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.datasource.POPPurchaseOrderLinesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchaseOrderLinesDSBean extends EMCDataSourceBean implements POPPurchaseOrderLinesDSLocal {

    @EJB
    private POPPurchaseOrderLinesLocal linesBean;
    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private POPCancelledPurchaseOrderMasterLocal cancelledMasterBean;
    @EJB
    private InventoryDimension3Local dim3Bean;
    @EJB
    private InventoryColourDisignMasterLocal designMasterBean;
    @EJB
    private POPPurchaseOrderMasterLocal masterBean;

    /** Creates a new instance of POPPurchaseOrderLinesDSBean */
    public POPPurchaseOrderLinesDSBean() {
        this.setDataSourceClassName(POPPurchaseOrderLinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = linesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof Boolean && !(Boolean) ret) {
            return ret;
        } else {
            POPPurchaseOrderLinesDS ds = (POPPurchaseOrderLinesDS) ret;
            //Fields that populate data on record
            if (fieldNameToValidate.equals("itemReference")) {
                if (ds.getRecordID() != 0) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Item may not be changed.", userData);
                    return false;
                }

                POPPurchaseOrderMaster master = masterBean.findPurchaseOrder(ds.getPurchaseOrderId(), userData);
                if (referenceBean.processItemReference(ds, master.getSupplier(), null, userData)) {
                    if (!linesBean.validateItem(ds, master, true, userData)) {
                        return false;
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "No reference found for this item on Item Reference.", userData);
                    return false;
                }
            }
            return ds;
        }
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPPurchaseOrderLinesDS ds = (POPPurchaseOrderLinesDS) dataSourceInstance;

        doItemDescription(ds, userData);
        ds.setItemReference(referenceBean.checkItemReference(ds.getItemId(), userData)[1]);

        if (!isBlank(ds.getItemDimension3())) {
            InventoryDimension3 dim3 = dim3Bean.getDimension3Record(ds.getItemDimension3(), userData);
            if (dim3 != null) {
                ds.setDesignNo(dim3.getDesignNo());
                ds.setColourCatagory(dim3.getCatagory());
                ds.setFabricColourNumber(dim3.getSourceColRef());
                ds.setPrintColourNumber(dim3.getColourWay());
                if (!isBlank(dim3.getDesignNo()) && isBlank(ds.getDesignCode())) {
                    InventoryColourDesignMaster designMaster = designMasterBean.findDesignMaster(dim3.getDesignNo(), userData);
                    if (designMaster != null) {
                        ds.setDesignCode(String.valueOf(designMaster.getDesignPerc()));
                    }
                }
            }

            POPPurchaseOrderMaster master = masterBean.findPurchaseOrder(ds.getPurchaseOrderId(), userData);
            if (master != null) {
                InventoryReference supplierReference = referenceBean.findSupplierReferenceRecord(ds.getItemId(), ds.getItemDimension1(), ds.getItemDimension2(), ds.getItemDimension3(), master.getSupplier(), false, userData);
                if (supplierReference != null && !isBlank(supplierReference.getDimension3Shade())) {
                    ds.setColourCatagory(supplierReference.getDimension3Shade());
                }
            }

        }
        return ds;
    }

    /** This method assigns a value to the item description field */
    private void doItemDescription(POPPurchaseOrderLinesDS ds, EMCUserData userData) {
        if (!isBlank(ds.getItemId())) {
            ds.setItemDescription(itemMasterBean.findItemDescription(ds.getItemId(), userData));
        }
    }

    /** This method is used to cancel Purchase Order Line. */
    public boolean cancelPurchaseOrderLine(POPPurchaseOrderLinesDS line, EMCUserData userData) throws EMCEntityBeanException {
        return cancelledMasterBean.cancelPurchaseOrderLine((POPPurchaseOrderLines) convertDataSourceToSuper(line, userData), userData);
    }
}