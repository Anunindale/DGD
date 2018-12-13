/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.copytabledata;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseCopyTableDataLocal extends EMCEntityBeanLocalInterface {

    public boolean copyTableData(java.lang.String fromCompanyId, java.lang.String toCompanyId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void insertInNewTx(emc.framework.EMCEntityClass recordToCopy, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean addRelatedTables(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public String createBackupFile(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean importBackupFile(String fileName, EMCUserData userData) throws EMCEntityBeanException;

    public void deleteBackupFile(EMCUserData userData);

    public boolean deleteTableData(String companyId, EMCUserData userData) throws EMCEntityBeanException;
}
