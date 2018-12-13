/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.budgetlines.DescriptionDS;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "GLBudgetLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class GLBudgetLinesDS extends GLBudgetLines {

    public GLBudgetLinesDS() {
        this.setDataSource(true);
    }
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("description", new DescriptionDS());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("description");
        return toBuild;
    }
}
