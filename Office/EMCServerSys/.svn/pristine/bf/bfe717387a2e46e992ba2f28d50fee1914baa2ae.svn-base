package emc.bus.debtors.creditrating;

import emc.entity.debtors.DebtorsCreditRating;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 * 
 * @Author wikus
 */
@Stateless
public class DebtorsCreditRatingBean extends EMCEntityBean implements DebtorsCreditRatingLocal {

    /** Creates a new instance of DebtorsCreditRatingBean. */
    public DebtorsCreditRatingBean() {

    }

    /**
     * Returns the specified Credit Rating record.
     * @param creditRating Credit rating to search for.
     * @param userData User data.
     * @return The specified Credit Rating, or null if it does not exist.
     */
    public DebtorsCreditRating getCreditRating(String creditRating, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditRating.class);
        query.addAnd("creditRating", creditRating);

        return (DebtorsCreditRating)util.executeSingleResultQuery(query, userData);
    }
}
