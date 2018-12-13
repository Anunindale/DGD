/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.employeecategoryhistory.datasource;

import emc.entity.base.datasource.BaseEmployeeCategoryHistoryDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseEmployeeCategoryHistoryDSBean extends EMCDataSourceBean implements BaseEmployeeCategoryHistoryDSLocal {

    public BaseEmployeeCategoryHistoryDSBean() {
        this.setDataSourceClassName(BaseEmployeeCategoryHistoryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseEmployeeCategoryHistoryDS ds = (BaseEmployeeCategoryHistoryDS) dataSourceInstance;

      

        return ds;
    }
}
