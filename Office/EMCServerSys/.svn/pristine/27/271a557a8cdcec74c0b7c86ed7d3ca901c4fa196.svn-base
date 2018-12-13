/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.mailmanager;

import emc.framework.EMCUserData;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Local
public interface EMCMailManagerLocal {

    public void postMail(java.lang.String[] recipients, java.lang.String[] cc, java.lang.String[] bcc, java.lang.String subject, java.lang.String message, java.lang.String signaturePath, java.lang.String from, Object[] attachmentFileNames, EMCUserData userData) throws javax.mail.MessagingException;

    public boolean sendEmail(emc.helpers.base.EMCEmail message, emc.framework.EMCUserData userData);

    public Object previewEmail(emc.helpers.base.EMCEmail message, emc.framework.EMCUserData userData);

    public boolean validateEmail(emc.helpers.base.EMCEmail message, emc.framework.EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long updateCorrespondenceLog(String reference, String resipient, String subject, String[] attachments, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteInNewTx(Long recordID, EMCUserData userData);

}
