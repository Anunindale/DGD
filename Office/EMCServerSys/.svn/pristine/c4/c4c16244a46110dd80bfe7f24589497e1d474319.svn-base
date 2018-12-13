/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.entity.pop.POPPurchaseOrderMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPPurchaseOrderMasterLocal extends EMCEntityBeanLocalInterface {

    void approvePurchaseOrder(String purchaseOrderId, EMCUserData userData);

    void removeApproval(String purchaseOrderId, EMCUserData userData);

    POPPurchaseOrderMaster findPurchaseOrder(String purchaseOrderId, EMCUserData userData);

    void removeApproval(POPPurchaseOrderMaster master, EMCUserData userData);

    double getOutstandingQuantity(String purchaseOrderId, EMCUserData userData);

    double[] getPurchaseOrderQuantities(String purchaseOrderId, EMCUserData userData);

    void unApprovePurchaseOrder(String orderId, EMCUserData userData);

    POPPurchaseOrderMaster getBlanketOrder(String purchaseOrderId, EMCUserData userData);

    public void setPOMasterStatus(String purchaseOrderId, EMCUserData userData) throws EMCEntityBeanException;

    public boolean updateLinesDate(String purchaseId, Date theDate, EMCUserData userData);

    /**
     * Checks if the return QC form should open
     * @param query
     * @param userData
     * @return
     */
    public boolean checkQCReturn(String query, EMCUserData userData);
}
