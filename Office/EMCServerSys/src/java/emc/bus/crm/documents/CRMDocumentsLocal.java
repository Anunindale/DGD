/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.documents;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CRMDocumentsLocal extends EMCEntityBeanLocalInterface {

    public boolean validateSelectedDocument(java.lang.String document, emc.framework.EMCUserData userData);

    public java.lang.String getDocument(java.lang.String documentId, emc.framework.EMCUserData userData);
}
