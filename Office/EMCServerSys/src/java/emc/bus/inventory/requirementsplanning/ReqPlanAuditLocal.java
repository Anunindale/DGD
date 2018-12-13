/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.requirementsplanning;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface ReqPlanAuditLocal extends EMCEntityBeanLocalInterface {

    public java.util.Map<java.lang.String, java.lang.Object[]> checkRequirementsPlanning(java.sql.Connection conn, emc.framework.EMCUserData userData);
}
