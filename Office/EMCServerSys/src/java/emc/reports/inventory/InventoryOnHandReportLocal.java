/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.inventory;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryOnHandReportLocal {

    public java.util.List<java.lang.Object> getReportResult(java.util.List<java.lang.Object> parameters, emc.framework.EMCUserData userData);

}
