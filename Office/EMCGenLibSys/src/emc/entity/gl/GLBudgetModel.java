package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.budgetmodel.Description;
import emc.datatypes.gl.budgetmodel.ModelId;
import emc.framework.EMCEntityClass;
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
//"modelId"
@Table(name = "GLBudgetModel", uniqueConstraints = {@UniqueConstraint(columnNames = {"modelId", "companyId"})})
public class GLBudgetModel extends EMCEntityClass {

    private String modelId;
    private String description;
    private String fromPeriod;
    private String toPeriod;

    /** Creates a new instance of GLBudgetModel. */
    public GLBudgetModel() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("modelId", new ModelId());
        toBuild.put("description", new Description());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("modelId");
        toBuild.add("description");
        return toBuild;
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}