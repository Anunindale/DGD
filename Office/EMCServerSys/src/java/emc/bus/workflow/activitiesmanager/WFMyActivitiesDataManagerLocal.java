/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.workflow.activitiesmanager;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface WFMyActivitiesDataManagerLocal {

    public java.util.Map<java.lang.String, java.lang.String> getNumberOfActivities(emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesToday(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesOverdue(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesNotStarted(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesNotClosed(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesToClose(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.workflow.WorkFlowActivity> getActivitiesAll(java.lang.String group, java.lang.String type, emc.framework.EMCUserData userData);
}
