/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.logic.edconstatement;

import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsEdconStatementLocal {

    /**
     * Generates and returns an Edcon statement as a String.
     * @param customerId Customer Id.  This should always be the Edcon customer id.
     *        It's passed in as a method parameter, instead of being hard-coded as it may, for some reason, change in the system.
     * @param from From date.
     * @param to To date.
     * @param edconSupplierRef Edcon supplier reference number.
     * @param userData User data.
     * @return A String that can be written to a file in order to produce an Edcon statement.
     */
    public java.lang.String getEdconStatement(java.lang.String customerId, java.util.Date from, java.util.Date to, java.lang.String edconSupplierRef, emc.framework.EMCUserData userData);
}
