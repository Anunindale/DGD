/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.creditheld;

import emc.entity.debtors.creditheld.DebtorsCreditHeldMaster;
import emc.enums.debtors.creditheld.DebtorsCreditHeldReason;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsCreditHeldLinesIF;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 * @description : Local interface for DebtorsCreditHeldMasterBean.
 *
 * @date        : 29 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Local
public interface DebtorsCreditHeldMasterLocal extends EMCEntityBeanLocalInterface {

    /**
     * Creates a Credit Held master record for the specified reference, and sets a credit held status on its lines.
     * @param reference Reference of Sales Order/Invoice on which credit should be held.
     * @param reason  Reason why credit is being held.  The credit held master will always have the reason of the last held line.
     * @param refType Reference type.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean holdCredit(String reference, DebtorsCreditHeldReason reason, DebtorsCreditHeldRefType refType, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Approves all Credit Held lines related to the specified master.
     * @param reference Reference number.
     * @param refType Reference type.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean approveCreditHeldMaster(String reference, String refType, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Creates a Credit Held master record for the specified reference, and sets a credit held status on its lines.
     * @param line Line on which credit should be held.
     * @param reason Reason for which credit is being held.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean holdCredit(DebtorsCreditHeldLinesIF line, DebtorsCreditHeldReason reason, EMCUserData userData) throws EMCEntityBeanException;
    /**
     * Returns the total credit held for the specified customer.
     * @param customerId Customer id.
     * @param userData User data.
     * @return The total credit held for the specified customer.
     */
    public BigDecimal getTotalCreditHeldForCustomer(String customerId, EMCUserData userData);

     /**
     * Returns the specified Credit Held master, or null, if none is found.
     * @param reference Reference to fetch.
     * @param refType Reference type.
     * @param userData User data.
     * @return A DebtorsCreditHeldMaster instance matching the specified criteria, or null, if nothing is found.
     */
    public DebtorsCreditHeldMaster getCreditHeldMaster(String reference, DebtorsCreditHeldRefType refType, EMCUserData userData);
}
