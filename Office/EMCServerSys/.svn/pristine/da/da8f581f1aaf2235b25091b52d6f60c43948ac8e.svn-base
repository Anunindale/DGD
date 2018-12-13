/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.financialperiods;

import emc.entity.gl.GLFinancialPeriods;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLFinancialPeriodsLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.gl.GLFinancialPeriods findFinancialPeriod(java.lang.String periodId, emc.framework.EMCUserData userData);

    public java.util.List<emc.entity.gl.GLFinancialPeriods> findAllPeriodsBetween(java.lang.String from, java.lang.String to, emc.framework.EMCUserData userData);

    public emc.entity.gl.GLFinancialPeriods findPeriodForDate(java.util.Date theDate, emc.framework.EMCUserData userData);

    public List<GLFinancialPeriods> findAllPeriodsBetweenDates(Date from, Date to, EMCUserData userData);

    public int findWeekInPeriod(java.util.Date theDate, emc.entity.gl.GLFinancialPeriods period, int weekStart, int minDaysInFirstWeek);
    /**
     * Updates the subledgers to any period changes.
     * @param userData
     */
    public boolean updateSubLedgersCloseDates(EMCUserData userData);

    /**
     * Generates financial periods.
     * @param generationTypeStr This identifieds a generation type.  It must be
     * a valid string representation of the values in the GLFinancialPeriodGenerationTypes
     * enum.
     * @param from From date.
     * @param to To date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean generateFinancialPeriods(final String generationTypeStr, final Date from, final Date to, EMCUserData userData) throws EMCEntityBeanException;
}
