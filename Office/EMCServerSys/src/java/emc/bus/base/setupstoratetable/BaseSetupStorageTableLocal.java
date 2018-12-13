/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.setupstoratetable;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseSetupStorageTableLocal extends EMCEntityBeanLocalInterface {

    public void saveSetup(java.lang.String type, java.lang.String setupId, java.lang.Object storageValue1, java.lang.Object storageValue2, java.lang.Object storageValue3, java.lang.Object storageValue4, java.lang.Object storageValue5, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List findSetup(java.lang.String type, java.lang.String setupId, emc.framework.EMCUserData userData);

    public void saveSetup(java.util.List setupList, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List findSetupId(java.lang.String type, emc.framework.EMCUserData userData);

    public void deleteSetup(java.lang.String type, java.lang.String setupId, emc.framework.EMCUserData userData);
}
