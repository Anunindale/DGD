/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.creditors.settlementdiscountterms;

import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface CreditorsSettlementDiscountTermsLocal extends EMCEntityBeanLocalInterface{

    /**
     * Returns a DebtorsSettlementDiscountTerms instance, matching the given id.
     * @param termId Term id to search for.
     * @param userData User data.
     * @return A DebtorsSettlementDiscountTerms instance, matching the given id, or null, if none is found.
     */
   public CreditorsSettlementDiscountTerms getSettlementDiscountTerms(String termId, EMCUserData userData);
}
