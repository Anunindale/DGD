/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.workflow;

import emc.bus.base.BaseDocRefLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.entity.workflow.datasource.WorkFlowActivityDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class WFActivityDSBean extends EMCDataSourceBean implements WFActivityDSLocal {
    
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private BaseDocRefLocal docuRefBean;
    @EJB
    private WFActivityLocal activityBean;
    
    public WFActivityDSBean(){
        //Set main entity class
        this.setDataSourceClassName("emc.entity.workflow.datasource.WorkFlowActivityDS");
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        WorkFlowActivityDS instance = (WorkFlowActivityDS)dataSourceInstance;
        
        instance.setComplete(instance.getCompletionDate() == null || instance.getClosedDate() == null ? false: true);
        instance.setOverdue((instance.getClosedDate() == null) && (instance.getRequiredCompletionDate() != null && Functions.DateAddSub(instance.getRequiredCompletionDate(), 1).before(Functions.nowDate())));
        instance.setEmployeeName(employeeBean.findEmployeeNameAndSurname(instance.getEmployeeNumber(), userData));
        instance.setManagerName(employeeBean.findEmployeeNameAndSurname(instance.getManagerResponsible(), userData));
        instance.setDocumentAttached(docuRefBean.isDocumentAttached(instance.getClass().getSuperclass().getSimpleName(), String.valueOf(dataSourceInstance.getRecordID()), userData));
        
        return dataSourceInstance;
    }
    
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return activityBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }


    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return activityBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return activityBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }
    
    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return activityBean.insert(convertDataSourceToSuper(iobject, userData), userData);
       
    }
    
}
