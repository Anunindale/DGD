/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.smsmanager;

import emc.framework.EMCUserData;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author wikus
 */
@Local
public interface EMCSMSManagerLocal {

    public int getMaxSMSLength(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.lang.Object sendSMS(emc.helpers.base.EMCSms message, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.lang.Object previewSMS(emc.helpers.base.EMCSms message, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean validateSMS(emc.helpers.base.EMCSms message, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long updateCorrespondenceLog(String reference, String resipient, String message, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteInNewTx(Long recordID, EMCUserData userData);

}
