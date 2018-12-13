/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow.skillsenquiry;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.workflow.WorkFlowSkillsEnquiry;
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
public class WorkFlowSkillEnquiryBean extends EMCDataSourceBean implements WorkFlowSkillEnquiryLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;

    public WorkFlowSkillEnquiryBean() {
        this.setDataSourceClassName(WorkFlowSkillsEnquiry.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        WorkFlowSkillsEnquiry enquiry = (WorkFlowSkillsEnquiry) dataSourceInstance;

        enquiry.setEmployeeName(employeeBean.findEmployeeNameAndSurname(enquiry.getEmployeeNumber(), userData));

        return enquiry;
    }
}
