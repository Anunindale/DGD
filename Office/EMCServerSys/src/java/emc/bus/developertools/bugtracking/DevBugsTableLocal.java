/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.developertools.bugtracking;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DevBugsTableLocal extends EMCEntityBeanLocalInterface {
  public boolean closeTasksFromDate(String client,Date from,Date to,EMCUserData userData)throws EMCEntityBeanException;
  public boolean closeTasksFromNumber(String client,String taskNo,EMCUserData userData) throws EMCEntityBeanException;
}
