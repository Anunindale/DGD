/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseReportOrderTableBean extends EMCEntityBean implements BaseReportOrderTableLocal {

    @EJB
    private BaseReportUserQueryTableLocal queryTableBean;

    /** Creates a new instance of BaseReportOrderTableBean. */
    public BaseReportOrderTableBean() {

    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportOrderTable record = (BaseReportOrderTable) uobject;
        return super.update(doSave(record, true, userData), userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportOrderTable record = (BaseReportOrderTable) iobject;
        return super.insert(doSave(record, false, userData), userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseReportOrderTable record = (BaseReportOrderTable) dobject;
        return super.delete(doSave(record, true, userData), userData);
    }

    /**
     * Calls super.insert()
     * @param iobject
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    @Override
    public Object superInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.insert(iobject, userData);
    }

    private BaseReportOrderTable doSave(BaseReportOrderTable record, boolean update, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportUserQueryTable.class.getName());
        query.addAnd("recordID", record.getReportQueryId());
        BaseReportUserQueryTable queryRecord = (BaseReportUserQueryTable) util.executeSingleResultQuery(query, userData);
        if (queryRecord != null && !queryRecord.getUserRecordName().equals("Custom")) {
            BaseReportOrderTable retRecord = (BaseReportOrderTable) doClone(record, userData);
            retRecord.setReportQueryId(queryTableBean.createCustom(queryRecord, record.getRecordID(), userData));
            if (update) superInsert(retRecord, userData);
            return retRecord;
        }
        return record;
    }
}
