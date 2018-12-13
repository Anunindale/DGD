/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.webportal.datasource;

import emc.bus.base.webportal.BaseWebPortalUsersLocal;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.datasource.BaseWebPortalUsersDS;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
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
public class BaseWebPortalUsersDSBean extends EMCDataSourceBean implements BaseWebPortalUsersDSLocal {

    @EJB
    private BaseWebPortalUsersLocal masterBean;

    public BaseWebPortalUsersDSBean() {
        this.setDataSourceClassName(BaseWebPortalUsersDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseWebPortalUsersDS ds = (BaseWebPortalUsersDS) dataSourceInstance;

        if (!isBlank(ds.getLinkToSourceType()) && ds.getLinkToSourceRecId() != 0) {
            EMCQuery query;
            Object[] sourceData;
            switch (WebPortalUsersReferenceType.fromString(ds.getLinkToSourceType())) {
                case CAPTURED:
                    //Nothing to Do
                    break;
                case LECTURER:
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
                    query.addAnd("recordID", ds.getLinkToSourceRecId());
                    query.addField("employeeNumber");
                    query.addField("forenames");
                    query.addField("surname");
                    sourceData = (Object[]) util.executeSingleResultQuery(query, userData);
                    if (sourceData != null && sourceData.length == 3) {
                        ds.setSourceRef((String) sourceData[0]);
                        ds.setSourceRefDesc((String) sourceData[1] + " " + (String) sourceData[2]);
                    }
                    break;
            }
        }

        return ds;
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
