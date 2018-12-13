/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
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
public class BaseReportWhereTableBean extends EMCEntityBean implements BaseReportWhereTableLocal {

    @EJB
    private BaseReportUserQueryTableLocal queryTableBean;

    /**
     * Creates a new instance of BaseReportWhereTableBean.
     */
    public BaseReportWhereTableBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (valid) {
            BaseReportWhereTable record = (BaseReportWhereTable) theRecord;
            
            /*switch (fieldNameToValidate) {
                case "fieldValue":
                    if (record.isLockValue()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The selected record values has been locked. You may not enter a field value.", userData);
                        valid = false;
                    }
                    break;
                case "lockValue":
                    if (record.isLockValue()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Please use the \'Lock\' button on the form to specify the lock criteria.", userData);
                        valid = false;
                    }
                    break;
            }*/
        }
        

        return valid;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportWhereTable record = (BaseReportWhereTable) uobject;
        return super.update(doSave(record, true, userData), userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportWhereTable record = (BaseReportWhereTable) iobject;
        return super.insert(doSave(record, false, userData), userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportWhereTable record = (BaseReportWhereTable) dobject;
        return super.delete(doSave(record, true, userData), userData);
    }

    /**
     * Calls super.insert()
     *
     * @param iobject
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public Object superInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.insert(iobject, userData);
    }

    private BaseReportWhereTable doSave(BaseReportWhereTable record, boolean update, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addAnd("recordID", record.getReportQueryId());
        BaseReportUserQueryTable queryRecord = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);
        if (queryRecord != null && !queryRecord.getUserRecordName().equals("Custom")) {
            BaseReportWhereTable retRecord = (BaseReportWhereTable) doClone(record, userData);
            retRecord.setReportQueryId(queryTableBean.createCustom(queryRecord, record.getRecordID(), userData));
            if (update) {
                superInsert(retRecord, userData);
            }
            return retRecord;
        }
        return record;
    }
}
