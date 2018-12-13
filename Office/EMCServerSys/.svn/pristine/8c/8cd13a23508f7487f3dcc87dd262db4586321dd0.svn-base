/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.bus.base.BaseEmployeeLocal;
import emc.entity.workflow.datasource.WorkFlowActivityGroupEmpDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class WFActGroupEmpDSBean extends EMCDataSourceBean implements WFActGroupEmpDSLocal {

    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private WFActGroupEmpLocal actGroupEmpBean;
    
    /** Creates a new instance of WFActGroupEmpDSBean */
    public WFActGroupEmpDSBean() {
        this.setDataSourceClassName("emc.entity.workflow.datasource.WorkFlowActivityGroupEmpDS");
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        WorkFlowActivityGroupEmpDS instance = (WorkFlowActivityGroupEmpDS)dataSourceInstance;
        
        instance.setEmployeeName(employeeBean.findEmployeeNameAndSurname(instance.getEmployeeNumber(), userData));
        
        return instance;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return actGroupEmpBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }

}
