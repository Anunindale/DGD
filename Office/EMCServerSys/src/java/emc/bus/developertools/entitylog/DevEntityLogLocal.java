/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.entitylog;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DevEntityLogLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<java.util.List<java.lang.String>> getEntityLogQueries(java.lang.String customerId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void executeEntityLogQueries(java.util.List<java.lang.String> queries, emc.framework.EMCUserData userData);
}
