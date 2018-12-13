/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.creditnoteinvoicemaster;

import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CreditorsCreditNoteInvoiceMasterLocal extends EMCEntityBeanLocalInterface {

    public boolean approveCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData) throws EMCEntityBeanException;

    public CreditorsCreditNoteInvoiceMaster getCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData);

    public boolean postCreditNoteInvoiceMaster(String creditNoteInvoiceNumber, EMCUserData userData) throws EMCEntityBeanException;
}
