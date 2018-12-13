/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.reservationhelper;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "InventoryReservationHelper", uniqueConstraints = {@UniqueConstraint(columnNames = { "companyId"})})
public class InventoryReservationHelper extends EMCEntityClass{
    private String lastUpdateInfo;

    /**
     * @return the lastUpdateInfo
     */
    public String getLastUpdateInfo() {
        return lastUpdateInfo;
    }

    /**
     * @param lastUpdateInfo the lastUpdateInfo to set
     */
    public void setLastUpdateInfo(String lastUpdateInfo) {
        this.lastUpdateInfo = lastUpdateInfo;
    }

}
