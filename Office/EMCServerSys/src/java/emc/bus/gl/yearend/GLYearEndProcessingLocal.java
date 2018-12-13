/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.yearend;

import emc.framework.EMCEntityBeanException;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLYearEndProcessingLocal {

    /**
     * Performs year-end logic for the year ending prior to the specified
     * opening period.
     * @param newOpeningPeriod Opening period of the year after the year to
     * be ended.  Opening balances for this period will be calculated and added
     * to the GLTransactionPeriodSummary table.
     * @param userData User data.
     * @return A boolean indicating whether the operation was successful.
     * @throws EMCEntityBeanException
     */
    public boolean processYearEnd(java.lang.String newOpeningPeriod, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

}
