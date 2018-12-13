/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.timesheet;

import emc.entity.developertools.DevTimeSheet;
import emc.entity.developertools.bugtracking.DevBugsTable;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.devtools.DevTimeSheetTaskHelper;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DevTimeSheetLocal extends EMCEntityBeanLocalInterface {

    public List<DevBugsTable> fetchPosibleMatchingTasks(DevTimeSheet timeSheet, EMCUserData userData);

    public long saveTask(DevBugsTable task, EMCUserData userData) throws EMCEntityBeanException;

    public boolean completeTask(DevTimeSheet timeSheet, DevTimeSheetTaskHelper helper, EMCUserData userData) throws EMCEntityBeanException;

    public java.util.Date getLastTaskEndTime(java.util.Date atDate, emc.framework.EMCUserData userData);
}
