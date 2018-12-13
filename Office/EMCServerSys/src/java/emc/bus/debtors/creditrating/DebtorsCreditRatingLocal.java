package emc.bus.debtors.creditrating;

import emc.entity.debtors.DebtorsCreditRating;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 * 
 * @Author wikus
 */
@Local
public interface DebtorsCreditRatingLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the specified Credit Rating record.
     * @param creditRating Credit rating to search for.
     * @param userData User data.
     * @return The specified Credit Rating, or null if it does not exist.
     */
    public DebtorsCreditRating getCreditRating(String creditRating, EMCUserData userData);
}
