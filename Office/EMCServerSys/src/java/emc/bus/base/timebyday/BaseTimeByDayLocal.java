/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.timebyday;

import emc.entity.base.BaseTimeByDay;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseTimeByDayLocal extends EMCEntityBeanLocalInterface {

    public void populateTimeByDays(Date fromDate, Date toDate, EMCUserData userData) throws EMCEntityBeanException;

    public BaseTimeByDay findTimeByDayRecord(Date theDate, EMCUserData userData);

    public BaseTimeByDay findTimeByDayRecord(long recordID, EMCUserData userData);
}
