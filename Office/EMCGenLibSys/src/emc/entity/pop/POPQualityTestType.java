/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.pop;

/**
 *
 * @author wikus
 */
import emc.datatypes.systemwide.Description;
import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;

@Entity
@Table(name = "POPQualityTestType", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"companyId", "testTypeId"})})
public class POPQualityTestType extends EMCEntityClass {

    private String testTypeId;
    private String description;

    public POPQualityTestType() {
    }

    public void setTestTypeId(String testTypeId) {
        this.testTypeId = testTypeId;
    }

    public String getTestTypeId() {
        return this.testTypeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("description", new Description());

        return toBuild;
    }
}
