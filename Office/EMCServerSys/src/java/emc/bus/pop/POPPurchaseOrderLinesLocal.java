/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPPurchaseOrderLinesLocal extends EMCEntityBeanLocalInterface {

    void calculateLineNetAmount(POPPurchaseOrderLines line, EMCUserData userData);

    /**
     * This method ensures that the item on the given Purchase Order line may be purchased.
     * Certain parts of this method are only applicable to field validation.
     */
    public boolean validateItem(POPPurchaseOrderLines line, POPPurchaseOrderMaster master, boolean fieldValidation, EMCUserData userData);
    

    public Object insertFromRelease(Object iobject, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Returns all lines belonging to the specified purchase order.
     * @param purchaseOrderId Purchase order id.
     * @param userData User data.
     * @return A list containing all the lines that belong to the specified purchase order.
     */
    public List<POPPurchaseOrderLines> getPurchaseOrderLines(String purchaseOrderId, EMCUserData userData);

    /**
     * Splits the specified Purchase Order line.
     * @param line Line to split.
     * @param splitQuantity Quantity to split.
     * @param revisedDate Revised date.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean splitLine(POPPurchaseOrderLines line, double splitQuantity, Date revisedDate, EMCUserData userData) throws EMCEntityBeanException;
}
