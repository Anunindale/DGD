/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop.posting;

import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.posting.PostingQuantities;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPPurchasePostSetupTableLocal extends EMCEntityBeanLocalInterface {
    POPPurchasePostSetupTable getSetupForMaster(String postId, EMCUserData userData);

    /**
     * Creates and returns a new POPPurchasePostSetupTable record.
     * @param post Indicates the value of the post flag to be set on the new record.
     * @param print Indicates the value of the print flag to be set on the new record.
     * @param postingQuantity Indicates the quantity selection value to be set on the new record.
     * @param documentType Indicates the document type of the newly created post setup.
     * @param purchaseOrderQuery Query used to select the purchase orders to be included in this post.
     * @param userData User data.
     * @return The POPPurchasePostSetup table instance that has been created.
     */
    public POPPurchasePostSetupTable setupNewPost(boolean post, boolean print, PostingQuantities postingQuantity, DocumentTypes documentType, EMCQuery purchaseOrderQuery, EMCUserData userData);
}
