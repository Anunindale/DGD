/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.servertransactionlog.datasource;

import emc.entity.base.servertransactions.datasource.BaseServerTransactionsLogDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author Owner
 */
@Stateless
public class BaseServerTransactionsLogDSBean extends EMCDataSourceBean implements BaseServerTransactionsLogDSLocal {

    public BaseServerTransactionsLogDSBean() {
        this.setDataSourceClassName(BaseServerTransactionsLogDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {

        BaseServerTransactionsLogDS ds = (BaseServerTransactionsLogDS) dataSourceInstance;
        ds.setTimeTakenMiliseconds((ds.getTransEndTime() - ds.getTransStartTime()) / 1000);
        
        return ds;
    }
}
