/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.yearend;

import emc.bus.gl.transactionperiodsummary.GLTransactionPeriodSummaryLocal;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class GLYearEndProcessingBean extends EMCBusinessBean implements GLYearEndProcessingLocal {

    @EJB
    private GLTransactionPeriodSummaryLocal transactionPeriodSummaryBean;
    
    /**
     * Creates a new instance of GLYearEndProcessingBean.
     */
    public GLYearEndProcessingBean() {
        
    }

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
    public boolean processYearEnd(String newOpeningPeriod, EMCUserData userData) throws EMCEntityBeanException {
        return transactionPeriodSummaryBean.generateOpeningBalances(newOpeningPeriod, userData);
    }
}
