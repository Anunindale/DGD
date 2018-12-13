/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.deploymentupdatelog.datasource;

import emc.entity.developertools.datasources.DevDeploymentUpdateLogDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevDeploymentUpdateLogDSBean extends EMCDataSourceBean implements DevDeploymentUpdateLogDSLocal {

    public DevDeploymentUpdateLogDSBean() {
        this.setDataSourceClassName(DevDeploymentUpdateLogDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DevDeploymentUpdateLogDS ds = (DevDeploymentUpdateLogDS) dataSourceInstance;

        return ds;
    }
}
