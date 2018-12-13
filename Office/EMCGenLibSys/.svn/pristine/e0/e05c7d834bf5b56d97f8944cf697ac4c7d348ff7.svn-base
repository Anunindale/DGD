/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.royaltygroups.foreignkeys.RoyaltyGroupIDFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsRoyaltySetup", uniqueConstraints = {@UniqueConstraint(columnNames = {"field1Value", "field2Value", "field3Value", "companyId"})})
public class DebtorsRoyaltySetup extends EMCEntityClass {

    //Shorten column lengths, otherwise unique constraint will be too large for Hibernate/MySQL to handle
    @Column(length = 100)
    private String field1Value;
    @Column(length = 100)
    private String field2Value;
    @Column(length = 100)
    private String field3Value;
    private String royaltyGroup;

    /** Creates a new instance of SOPRoyaltySetup. */
    public DebtorsRoyaltySetup() {
        
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("royaltyGroup", new RoyaltyGroupIDFK());

        return toBuild;
    }

    public String getField1Value() {
        return field1Value;
    }

    public void setField1Value(String field1Value) {
        this.field1Value = field1Value;
    }

    public String getField2Value() {
        return field2Value;
    }

    public void setField2Value(String field2Value) {
        this.field2Value = field2Value;
    }

    public String getField3Value() {
        return field3Value;
    }

    public void setField3Value(String field3Value) {
        this.field3Value = field3Value;
    }

    public String getRoyaltyGroup() {
        return royaltyGroup;
    }

    public void setRoyaltyGroup(String royaltyGroup) {
        this.royaltyGroup = royaltyGroup;
    }
}
