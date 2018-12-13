/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.uomdetailconversiontable.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.entity.base.BaseUOMConversionTable;
import emc.entity.base.datasource.BaseUOMDetailedConversionTableDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseUOMDetailedConversionTableDSBean extends EMCDataSourceBean implements BaseUOMDetailedConversionTableDSLocal {

    @EJB
    private InventoryReferenceLocal itemRefereneceBean;

    public BaseUOMDetailedConversionTableDSBean() {
        this.setDataSourceClassName(BaseUOMDetailedConversionTableDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseUOMDetailedConversionTableDS ds = (BaseUOMDetailedConversionTableDS) dataSourceInstance;

        if (ds.getMasterRecordID() != 0) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMConversionTable.class);
            query.addAnd("recordID ", ds.getMasterRecordID());
            BaseUOMConversionTable masterRec = (BaseUOMConversionTable) util.executeSingleResultQuery(query, userData);

            if (masterRec != null) {
                ds.setUnit(masterRec.getUnit());
                ds.setBaseUnit(masterRec.getBaseUnit());
            }
        }

        if (!isBlank(ds.getItemId())) {
            itemRefereneceBean.populateDSFields(ds, userData);
        }

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            BaseUOMDetailedConversionTableDS record = (BaseUOMDetailedConversionTableDS) theRecord;
            if (fieldNameToValidate.equals("itemReference")) {
                if (itemRefereneceBean.doItemRefValidation(record, userData)) {
                    if (record.getMasterRecordID() != 0) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMConversionTable.class);
                        query.addAnd("recordID ", record.getMasterRecordID());
                        BaseUOMConversionTable masterRec = (BaseUOMConversionTable) util.executeSingleResultQuery(query, userData);

                        if (masterRec != null) {
                            record.setUnit(masterRec.getUnit());
                            record.setBaseUnit(masterRec.getBaseUnit());
                        }
                    }
                    return record;
                } else {
                    ret = false;
                }
            }
        }
        return ret;
    }
}
