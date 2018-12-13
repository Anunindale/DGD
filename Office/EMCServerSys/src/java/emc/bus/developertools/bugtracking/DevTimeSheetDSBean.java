/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.bugtracking;

import emc.bus.developertools.timesheet.DevTimeSheetLocal;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.entity.developertools.datasources.DevTimeSheetDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
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
public class DevTimeSheetDSBean extends EMCDataSourceBean implements DevTimeSheetDSLocal {

    @EJB
    private DevTimeSheetLocal masterBean;

    public DevTimeSheetDSBean() {
        this.setDataSourceClassName(DevTimeSheetDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DevTimeSheetDS ds = (DevTimeSheetDS) dataSourceInstance;

        ds.setTimeTaken(dateHandler.calculateTimeDiffInHourse(ds.getWorkStartTime(), ds.getWorkEndTime(), userData));

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevBugsTable.class);
        query.addAnd("recordID", ds.getTaskRecordId());
        query.addField("client");
        query.addField("bugNumber");
        Object[] taskInfo = (Object[]) util.executeSingleResultQuery(query, userData);

        if (taskInfo != null) {
            ds.setClient((String) taskInfo[0]);
            ds.setTaskNumber((String) taskInfo[1]);
        }

        return ds;
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        if (iobject instanceof DevTimeSheetDS) {
            return convertSuperToDataSource(masterBean.insert(convertDataSourceToSuper(iobject, userData), userData), userData);
        } else {
            return convertSuperToDataSource(masterBean.insert(iobject, userData), userData);
        }
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        if (uobject instanceof DevTimeSheetDS) {
            return convertSuperToDataSource(masterBean.update(convertDataSourceToSuper(uobject, userData), userData), userData);
        } else {
            return convertSuperToDataSource(masterBean.update(uobject, userData), userData);
        }
    }

}
