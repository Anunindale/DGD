/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop.datasource;

import emc.entity.pop.datasource.POPPurchaseOrderLinesDS;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface POPPurchaseOrderLinesDSLocal extends EMCEntityBeanLocalInterface {

    boolean cancelPurchaseOrderLine(POPPurchaseOrderLinesDS line, EMCUserData userData) throws EMCEntityBeanException;

}
