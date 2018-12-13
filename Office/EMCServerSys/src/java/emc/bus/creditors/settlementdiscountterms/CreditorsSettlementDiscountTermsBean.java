/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 

package emc.bus.creditors.settlementdiscountterms;

import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
 *
 * @author riaan
 */
@Stateless
public class CreditorsSettlementDiscountTermsBean extends EMCEntityBean implements CreditorsSettlementDiscountTermsLocal {
    
    /** Creates a new instance of CreditorsSettlementDiscountBean */
    public CreditorsSettlementDiscountTermsBean() {
        
    }

    /**
     * Returns a DebtorsSettlementDiscountTerms instance, matching the given id.
     * @param termId Term id to search for.
     * @param userData User data.
     * @return A DebtorsSettlementDiscountTerms instance, matching the given id, or null, if none is found.
     */
    public CreditorsSettlementDiscountTerms getSettlementDiscountTerms(String termId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsSettlementDiscountTerms.class);
        query.addAnd("settlementDiscountTermId", termId);

        return (CreditorsSettlementDiscountTerms)util.executeSingleResultQuery(query, userData);
    }
}
