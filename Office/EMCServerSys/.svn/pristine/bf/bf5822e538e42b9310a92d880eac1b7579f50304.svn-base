/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseDocRefLocal extends EMCEntityBeanLocalInterface {

    public boolean isDocumentAttached(java.lang.String tableName, java.lang.String refRecId, emc.framework.EMCUserData userData);

    public boolean isDocumentOfTypeAttached(String tableName, String refRecId, String type, EMCUserData userData);

    public List<String> getAttachment(Long recordId, Class refTable, EMCUserData userData);

    /** Sets the hasAttachment flag on all records with attachments. */
    public void fixData(EMCUserData userData);

    public List<String> getReportHeader(EMCQuery query, String summaryPrimary, String summarySecondary, EMCUserData userData);

    public List<String> getAttachment(Long recordId, Class refTable, String summaryPrimary, String summarySecondary, EMCUserData userData);
    /**
     * Create a DocRef record to attach a specific user input to.
     * @param recordID of the source table
     * @param summary - summary that will be used for the docref table record
     * @param simpleClassName - the classname of the sourcetable e.g .getSimpleName();
     * @param userData
     * @return
     * @throws EMCEntityBeanException
     */
    public boolean createSpecificAttachment(long recordID,String summary,String simpleClassName, EMCUserData userData) throws EMCEntityBeanException;
    
    public Object createWebAttachment(Object attachment, EMCUserData userData) throws EMCEntityBeanException;
}
