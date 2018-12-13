/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.setupstoratetable;

import emc.entity.base.BaseSetupStorageTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseSetupStorageTableBean extends EMCEntityBean implements BaseSetupStorageTableLocal {

    @Override
    public void saveSetup(String type, String setupId, Object storageValue1, Object storageValue2,
            Object storageValue3, Object storageValue4, Object storageValue5, EMCUserData userData) throws EMCEntityBeanException {
        BaseSetupStorageTable storage = new BaseSetupStorageTable();
        storage.setSetupType(type);
        storage.setSetupId(setupId);
        storage.setStorageValue1(String.valueOf(storageValue1));
        storage.setStorageValue2(String.valueOf(storageValue2));
        storage.setStorageValue3(String.valueOf(storageValue3));
        storage.setStorageValue4(String.valueOf(storageValue4));
        storage.setStorageValue5(String.valueOf(storageValue5));
        this.insert(storage, userData);
    }

    @Override
    public List findSetup(String type, String setupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseSetupStorageTable.class);
        query.addAnd("setupType", type);
        query.addAnd("setupId", setupId);
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public void saveSetup(List setupList, EMCUserData userData) throws EMCEntityBeanException {
        setupList.remove(0);
        for (Object o : setupList) {
            BaseSetupStorageTable storage = (BaseSetupStorageTable) o;
            this.insert(storage, userData);
        }
    }

    @Override
    public List findSetupId(String type, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseSetupStorageTable.class);
        query.addAnd("setupType", type);
        query.addField("setupId");
        query.setSelectDistinctAll(true);
        return util.executeGeneralSelectQuery(query, userData);
    }

    @Override
    public void deleteSetup(String type, String setupId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, BaseSetupStorageTable.class);
        query.addAnd("setupType", type);
        query.addAnd("setupId", setupId);
        util.executeUpdate(query, userData);
    }
}
