/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.inventory.consolidatedpickinglist;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "InventoryConsolidatedPLSetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"pickingListLineId", "sessionId", "companyId"})})
public class InventoryConsolidatedPLSetup extends EMCEntityClass {

    private long pickingListLineId;
    private long sessionId;
    
    /** Creates a new instance of InventoryConsolidatedPLSetup. */
    public InventoryConsolidatedPLSetup() {
        
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getPickingListLineId() {
        return pickingListLineId;
    }

    public void setPickingListLineId(long pickingListLineId) {
        this.pickingListLineId = pickingListLineId;
    }
}
