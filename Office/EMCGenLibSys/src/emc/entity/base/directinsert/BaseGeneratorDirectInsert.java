/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.base.directinsert;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseGeneratorDirectInsert", uniqueConstraints = {@UniqueConstraint(columnNames = { "companyId"})})
public class BaseGeneratorDirectInsert extends EMCEntityClass{
    private long nextRecordID = 6000000000L;

    /**
     * @return the nextRecordID
     */
    public long getNextRecordID() {
        return nextRecordID;
    }

    /**
     * @param nextRecordID the nextRecordID to set
     */
    public void setNextRecordID(long nextRecordID) {
        this.nextRecordID = nextRecordID;
    }
    

}
