/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.web;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author stu
 */
@Local
public interface TRECWebPrinterLogicBeanLocal extends EMCEntityBeanLocalInterface {

    public java.util.List getPhrasesString(emc.framework.EMCUserData userData);

    public java.lang.String getTrecCardDataset(java.lang.String invoiceId, java.lang.String key, int lineNo, boolean redLine, boolean blackLines, emc.framework.EMCUserData userData);

    public boolean updateBasketLinesPrintQty(java.lang.String invoiceId, java.lang.String key, int lineNo, int qtyPrinted, emc.framework.EMCUserData userData);

    public java.util.List<java.lang.Object[]> getInvoiceData(java.lang.String invoiceId, java.lang.String key, emc.framework.EMCUserData userData);
}
