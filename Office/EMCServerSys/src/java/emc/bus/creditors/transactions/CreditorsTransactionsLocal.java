package emc.bus.creditors.transactions;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

@Local
/** 
 *
 * @author Owner
 */
public interface CreditorsTransactionsLocal extends EMCEntityBeanLocalInterface {

    public void postInvoiceCreditNote(emc.entity.creditors.CreditorsCreditNoteInvoiceMaster masterRecord, java.util.List<emc.entity.creditors.CreditorsCreditNoteInvoiceLines> linesRecordList, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
