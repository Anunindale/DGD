/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.posting;

import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPPurchasePostMasterLocal extends EMCEntityBeanLocalInterface {

    public boolean createPost(String query, POPPurchasePostSetupTable setup, boolean populateAllLines, EMCUserData userData) throws EMCEntityBeanException;

    public void receivePurchaseOrder(String query, EMCUserData userData) throws EMCEntityBeanException;

    public boolean postPurchaseOrder(java.lang.String purchaseOrderId, emc.framework.EMCUserData userData);

    public boolean releaseBlanketOrder(String query, EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean checkBatchSerial(String postSetupId, EMCUserData userData);

    public boolean postBlanketOrder(java.lang.String purchaseOrderId, emc.framework.EMCUserData userData);

    public void returnToSupplier(String postMasterQuery, boolean receiveLater, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns String representations of the labels applicable to the specified post.
     * @param postMasterId Post master id.
     * @param userData User data.
     * @return A Map containing String representations of labels.
     */
    public Map<String, String> printLabels(String postMasterId, EMCUserData userData);

    public boolean createPost(java.lang.String query, java.lang.String setupId, boolean populateAllLines, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
