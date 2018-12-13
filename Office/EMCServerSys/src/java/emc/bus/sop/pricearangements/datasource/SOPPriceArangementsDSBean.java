/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.pricearangements.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.pop.POPPriceGroupLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.sop.datasource.SOPPriceArangementsDS;
import emc.enums.enumQueryTypes;
import emc.enums.sop.pricearangement.PricingCustomerType;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPPriceArangementsDSBean extends EMCDataSourceBean implements SOPPriceArangementsDSLocal {

    @EJB
    private SOPPriceArangementsLocal masterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private POPPriceGroupLocal priceGroupBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimensionCombinationLogicBean;

    public SOPPriceArangementsDSBean() {
        this.setDataSourceClassName(SOPPriceArangementsDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPPriceArangementsDS ds = (SOPPriceArangementsDS) dataSourceInstance;
        if (!isBlank(ds.getItemId())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class);
            query.addAnd("itemId", ds.getItemId());
            query.addField("itemReference");
            query.addField("description");
            query.addField("brandGroup");
            Object[] itemInfo = (Object[]) util.executeSingleResultQuery(query, userData);

            if (itemInfo != null) {
                if (itemInfo.length >= 1) {
                    ds.setItemReference((String) itemInfo[0]);
                }
                if (itemInfo.length >= 2) {
                    ds.setItemDescription((String) itemInfo[1]);
                }
                if (itemInfo.length >= 3) {
                    ds.setItemBrand((String) itemInfo[2]);
                }
            }
        }
        if (!isBlank(ds.getCustomerId())) {
            ds.setCustomerDisplayField(ds.getCustomerId());
            ds.setCustomerName(customerBean.findCustomer(ds.getCustomerId(), userData).getCustomerName());
        } else if (!isBlank(ds.getPriceGroup())) {
            ds.setCustomerDisplayField(ds.getPriceGroup());
            ds.setCustomerName(priceGroupBean.getPriceGroup(ds.getPriceGroup(), userData).getDescription());
        }
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        SOPPriceArangementsDS ds = (SOPPriceArangementsDS) theRecord;
        if (fieldNameToValidate.equals("itemReference") || fieldNameToValidate.equals("itemId")) {
            if (isBlank(ds.getItemReference())) {
                ds.setItemReference(ds.getItemId());
            }
            if (!itemReferenceBean.doItemRefValidation(ds, userData)) {
                ds.setItemReference(null);
                ds.setItemDescription(null);
                ds.setItemId(null);
            }
            return ds;
        } else if (fieldNameToValidate.equals("customerDisplayField")) {
            if (PricingCustomerType.CUSTOMER.toString().equals(ds.getCustomerType())) {
                if (!isBlank(ds.getCustomerDisplayField())) {
                    ds.setCustomerId(ds.getCustomerDisplayField());
                    if ((Boolean) masterBean.validateField("customerId", ds, userData)) {
                        return ds;
                    } else {
                        return false;
                    }
                }
            } else if (PricingCustomerType.GROUP.toString().equals(ds.getCustomerType())) {
                if (!isBlank(ds.getCustomerDisplayField())) {
                    ds.setPriceGroup(ds.getCustomerDisplayField());
                    if ((Boolean) masterBean.validateField("priceGroup", ds, userData)) {
                        return ds;
                    } else {
                        return false;
                    }
                }
            } else if (PricingCustomerType.ALL.toString().equals(ds.getCustomerType())) {
                if (!isBlank(ds.getCustomerDisplayField())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected field is not required if the type is \'All\'", userData);
                    return false;
                }
            }
        } else if (fieldNameToValidate.equals("customerType")) {
            switch (PricingCustomerType.fromString(ds.getCustomerType())) {
                case ALL:
                    ds.setCustomerId(null);
                    ds.setCustomerName(null);
                    ds.setPriceGroup(null);
                    ds.setCustomerDisplayField(null);
                    break;
                case CUSTOMER:
                    ds.setPriceGroup(null);
                    break;
                case GROUP:
                    ds.setCustomerId(null);
                    ds.setCustomerName(null);
                    break;
            }
            return ds;
        } else if (fieldNameToValidate.equals("dimension1") || fieldNameToValidate.equals("dimension2") || fieldNameToValidate.equals("dimension3")) {
            if (!isBlank(ds.getItemId())) {
                dimensionCombinationLogicBean.validateDimensionValues(ds, userData);
            }
        } else if (fieldNameToValidate.equals("fromDate") || fieldNameToValidate.equals("toDate")) {
            if (!isBlank(ds.getFromDate()) && !isBlank(ds.getToDate())) {
                if (ds.getFromDate().after(ds.getToDate())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The From Date needs to be before the To Date.", userData);
                    return false;
                }
            }
        } else if (fieldNameToValidate.equals("fromDate")) {
            if (!isBlank(ds.getCustomerType()) && PricingCustomerType.ALL.toString().equals(ds.getCustomerType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "A customer is not required for the " + PricingCustomerType.ALL.toString() + " pricing type.", userData);
                return false;
            }
        }
        return masterBean.validateField(fieldNameToValidate, theRecord, userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return masterBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}
