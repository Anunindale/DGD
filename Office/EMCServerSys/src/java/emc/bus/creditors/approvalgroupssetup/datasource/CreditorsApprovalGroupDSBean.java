/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.approvalgroupssetup.datasource;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.creditors.datasource.CreditorsApprovalGroupSetupDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class CreditorsApprovalGroupDSBean extends EMCDataSourceBean implements CreditorsApprovalGroupsDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public CreditorsApprovalGroupDSBean() {
        this.setDataSourceClassName(CreditorsApprovalGroupSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        CreditorsApprovalGroupSetupDS ds = (CreditorsApprovalGroupSetupDS) dataSourceInstance;

        if (!isBlank(ds.getEmployeeId())) {
            ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));
        }

        return ds;
    }
}
