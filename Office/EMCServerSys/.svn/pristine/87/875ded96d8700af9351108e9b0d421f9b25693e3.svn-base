/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.calendar;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseCalendarExceptionsLocal extends EMCEntityBeanLocalInterface {

    public List getMassData(EMCUserData userData);

    public void odMassUpdate(long recordId, List calendarList, EMCUserData userData) throws EMCEntityBeanException;

    public double findPercentageOfTarget(String calendarId, Date firstDate, Date secondDate, EMCUserData userData);
}
