package emc.bus.pop.datasource.pricearrangements;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.logic.InventoryItemLogicLocal;
import emc.bus.pop.POPPriceGroupLocal;
import emc.bus.pop.POPSupplierLocal;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.datasource.pricearrangements.POPPriceArrangementDS;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.enums.enumQueryTypes;
import emc.enums.pop.pricearrangement.PricingSupplierType;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author riaan
 */
@Stateless
public class POPPriceArrangementDSBean extends EMCDataSourceBean implements POPPriceArrangementDSLocal {

    @EJB
    private POPPriceArrangementsLocal priceArrangementBean;
    @EJB
    private InventoryItemLogicLocal itemLogicBean;
    @EJB
    private InventoryReferenceLocal refBean;
    @EJB
    private POPSupplierLocal supplierBean;
    @EJB
    private POPPriceGroupLocal priceGroupBean;

    /** Creates a new instance of POPPriceArrangementDSBean. */
    public POPPriceArrangementDSBean() {
        this.setDataSourceClassName(POPPriceArrangementDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = priceArrangementBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret == Boolean.TRUE || ret instanceof POPPriceArrangements) {
            //Validation succeeded
            POPPriceArrangementDS ds = (POPPriceArrangementDS) (ret instanceof POPPriceArrangementDS ? convertSuperToDataSource(ret, userData) : theRecord);

            if ("supplierDisplayField".equals(fieldNameToValidate)) {
                PricingSupplierType type = PricingSupplierType.fromString(ds.getSupplierType());
                if (type == PricingSupplierType.SUPPLIER) {
                    ds.setSupplierId(ds.getSupplierDisplayField());
                    Object supplierFieldValidationRes = priceArrangementBean.validateField("supplierId", convertDataSourceToSuper(theRecord, userData), userData);
                    if (supplierFieldValidationRes != Boolean.TRUE && !(supplierFieldValidationRes instanceof POPPriceArrangements)) {
                        return false;
                    }
                } else if (type == PricingSupplierType.GROUP) {
                    ds.setPriceGroup(ds.getSupplierDisplayField());
                    Object priceGroupFieldValidationRes = priceArrangementBean.validateField("priceGroup", convertDataSourceToSuper(theRecord, userData), userData);
                    if (priceGroupFieldValidationRes != Boolean.TRUE && !(priceGroupFieldValidationRes instanceof POPPriceArrangements)) {
                        return false;
                    }
                }
            } else if ("supplierType".equals(fieldNameToValidate)) {
                PricingSupplierType type = PricingSupplierType.fromString(ds.getSupplierType());
                if (type == PricingSupplierType.SUPPLIER) {
                    ds.setPriceGroup(null);
                } else if (type == PricingSupplierType.GROUP) {
                    ds.setSupplierId(null);
                    ds.setSupplierName(null);
                } else if (type == PricingSupplierType.ALL) {
                    ds.setPriceGroup(null);
                    ds.setSupplierId(null);
                    ds.setSupplierName(null);
                }
            } else if ("itemReference".equals(fieldNameToValidate)) {
                if (!itemLogicBean.doItemRefValidation(ds, userData)) {
                    return false;
                }
            }

            return populateDataSourceFields(ds, userData);
        }

        return ret;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        POPPriceArrangementDS ds = (POPPriceArrangementDS) dataSourceInstance;

        PricingSupplierType type = PricingSupplierType.fromString(ds.getSupplierType());
        if (type == PricingSupplierType.SUPPLIER) {
            ds.setSupplierDisplayField(ds.getSupplierId());
        } else if (type == PricingSupplierType.GROUP) {
            ds.setSupplierDisplayField(ds.getPriceGroup());
        }

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

        if (!isBlank(ds.getSupplierId())) {
            POPSuppliers supplier = supplierBean.getSupplier(ds.getSupplierId(), userData);
            if (supplier != null) {
                ds.setSupplierName(supplier.getSupplierName());
            }
        }

        if (!isBlank(ds.getPriceGroup())) {
            POPPriceGroup priceGroup = priceGroupBean.getPriceGroup(ds.getPriceGroup(), userData);
            if (priceGroup != null) {
                ds.setSupplierName(priceGroup.getDescription());
            }
        }

        return ds;
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return priceArrangementBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return priceArrangementBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return priceArrangementBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}