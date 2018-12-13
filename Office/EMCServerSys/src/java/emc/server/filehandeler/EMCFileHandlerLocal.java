/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.filehandeler;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface EMCFileHandlerLocal {

    public boolean uploadFile(byte[] file, emc.framework.EMCUserData userData);

    public byte[] downloadFile(emc.framework.EMCUserData userData);

    public boolean deleteFile(java.lang.String module, java.lang.String fileName, emc.framework.EMCUserData userData);

    public void attachFileToRecord(emc.framework.EMCEntityClass sourceRecord, java.lang.String note, java.util.List<java.lang.String> fileContent, java.lang.String fileName, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void attachFileToRecord(emc.framework.EMCEntityClass sourceRecord, java.lang.String note, java.io.File attachmentFile, java.lang.String fileName, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}
