/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.action;

import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseCloneLocal {
    /**
     * Clones a record and its siblings if setup correclty in clone parameter on the entity class
     * @param recordToClone 
     * @param primaryKey value of the new primary key applicable on tables that has no numberseq
     * @param userData 
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean cloneRecord(emc.framework.EMCEntityClass recordToClone, java.lang.Object primaryKey, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

}
