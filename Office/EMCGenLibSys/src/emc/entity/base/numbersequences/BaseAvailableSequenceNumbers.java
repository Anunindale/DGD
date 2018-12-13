/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.numbersequences;

import emc.datatypes.base.availablenumbersequences.SequenceNumber;
import emc.datatypes.base.availablenumbersequences.SequenceNumberId;
import emc.datatypes.base.availablenumbersequences.Status;
import emc.datatypes.base.numbersequences.foreignkeys.NumberSequenceIdFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseAvailableSequenceNumbers", uniqueConstraints = {@UniqueConstraint(columnNames = {"sequenceNumberId", "sequenceNumber", "companyId"})})
public class BaseAvailableSequenceNumbers extends EMCEntityClass {

    private String sequenceNumber;
    private int status; //0 free, 1 in suspence,2 -used
    private String sequenceNumberId;

    /** Creates a new instance of BaseAvailableSequenceNumbers */
    public BaseAvailableSequenceNumbers() {

    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSequenceNumberId() {
        return sequenceNumberId;
    }

    public void setSequenceNumberId(String sequenceNumberId) {
        this.sequenceNumberId = sequenceNumberId;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("sequenceNumber", new SequenceNumber());
        toBuild.put("status", new Status());
        toBuild.put("sequenceNumberId", new NumberSequenceIdFK());
        
        return toBuild;
    }
}
