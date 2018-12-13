/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.invoiceregister;

import emc.entity.creditors.CreditorsInvoiceRegister;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface CreditorsInvoiceRegisterLocal extends EMCEntityBeanLocalInterface {

    /** Returns all register lines matching the specified criteria.
     *
     * @param creditNote Credit note number.
     * @param transId Transaction id.
     * @param userData User data.
     * @return Register lines for the specified transaction id on the specified Credit Note.
     */
    public List<CreditorsInvoiceRegister> getRegisterLines(String creditNote, String transId, EMCUserData userData);

    /** Updates the specified record without doing any validations. */
    public Object doUpdate(CreditorsInvoiceRegister register, EMCUserData userData);
}
