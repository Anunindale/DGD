/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.royaltysetup;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsRoyaltySetupLocal extends EMCEntityBeanLocalInterface {

    /** Returns an object array containing the fields names of the royalty fields set up on DebtorsParameters.
     *
     * @param userData User data.
     * @return An object array containing the fields names of the royalty fields set up on DebtorsParameters.
     */
    public java.lang.Object[] getRoyaltyFields(emc.framework.EMCUserData userData);

    /**
     * Checks whether royalty records have been set up for the company specified
     * in userData.
     * @param userData User data.
     * @return A boolean indicating whether royalties have been set up in the
     *          current company.
     */
    public boolean checkRoyaltySetupExists(EMCUserData userData);
}
