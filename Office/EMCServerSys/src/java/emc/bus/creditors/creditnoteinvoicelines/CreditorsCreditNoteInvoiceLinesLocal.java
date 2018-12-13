/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicelines;

import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CreditorsCreditNoteInvoiceLinesLocal extends EMCEntityBeanLocalInterface {

    public List<CreditorsCreditNoteInvoiceLines> getCreditNoteInvoiceLines(String creditNoteInvoiceNumber, EMCUserData userData);

    public CreditorsCreditNoteInvoiceLines getInvoiceLine(String invoiceNo, CreditorsCreditNoteInvoiceLines creditNoteLine, EMCUserData userData);
}
