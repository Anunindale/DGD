/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.vatcodes;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLVATCodeLocal extends EMCEntityBeanLocalInterface {

    public double getVatPercentage(java.lang.String vatCode, emc.framework.EMCUserData userData);

    /**
     * Returns the VAT input account for the specified account.
     * @param accountNumber Account number.
     * @param userData User data.
     * @return The account number of the VAT input account for the specified account.
     */
    public String getVATInputAccount(String accountNumber, EMCUserData userData);

     /**
     * Returns the VAT outut account for the specified account.
     * @param accountNumber Account number.
     * @param userData User data.
     * @return The account number of the VAT input account for the specified account.
     */
    public String getVATOutputAccount(String accountNumber, EMCUserData userData);
}
