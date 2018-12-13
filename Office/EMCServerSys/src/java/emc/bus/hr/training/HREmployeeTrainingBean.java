/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.hr.training;

import emc.bus.workflow.WFEmpSkillLocal;
import emc.entity.hr.HREmployeeTraining;
import emc.entity.workflow.WorkFlowEmployeeSkills;
import emc.enums.hr.HRTrainingStatuses;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HREmployeeTrainingBean extends EMCEntityBean implements HREmployeeTrainingLocal {

    @EJB
    private WFEmpSkillLocal skillBean;

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.update(uobject, userData);
        updateSkill((HREmployeeTraining) uobject, userData);
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        updateSkill((HREmployeeTraining) iobject, userData);
        return ret;
    }

    private void updateSkill(HREmployeeTraining record, EMCUserData userData) throws EMCEntityBeanException {
        if (HRTrainingStatuses.COMPLETED.toString().equals(record.getStatus()) && !isBlank(record.getSkill()) && !isBlank(record.getRating())) {
            WorkFlowEmployeeSkills skill = new WorkFlowEmployeeSkills();
            skill.setEmployeeNumber(record.getEmployeeNumber());
            skill.setSkill(record.getSkill());
            skill.setRating(record.getRating());
            skill.setRatingDate(record.getDateCompleted());
            skillBean.insert(skill, userData);
        }
    }
}
