/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.journalapprovalgroupemployees.datasourse;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.gl.datasource.GLJournalApprovalGroupEmployeesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class GLJournalApprovalGroupEmployeesDSBean extends EMCDataSourceBean implements GLJournalApprovalGroupEmployeesDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public GLJournalApprovalGroupEmployeesDSBean() {
        this.setDataSourceClassName(GLJournalApprovalGroupEmployeesDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        GLJournalApprovalGroupEmployeesDS ds = (GLJournalApprovalGroupEmployeesDS) dataSourceInstance;
        ds.setEmployeeName(employeeBean.findEmployeeNameAndSurname(ds.getEmployeeId(), userData));
        return ds;
    }
}
