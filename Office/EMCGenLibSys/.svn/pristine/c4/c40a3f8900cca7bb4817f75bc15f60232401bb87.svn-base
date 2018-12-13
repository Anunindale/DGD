package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.budgetlines.LineNumber;
import emc.datatypes.gl.budgetmodel.foreignkeys.ModelIdFKNM;
import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFKNM;
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
@Table(name = "GLBudgetLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"modelId", "lineNumber", "companyId"})})
public class GLBudgetLines extends EMCEntityClass {

    private String modelId;
    private String account;
    private double lineNumber;

    /** Creates a new instance of GLBudgetLines. */
    public GLBudgetLines() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("modelId", new ModelIdFKNM());
        toBuild.put("account", new AccountNumberFKNM());
        toBuild.put("lineNumber", new LineNumber());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("modelId");
        toBuild.add("account");
        return toBuild;
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(double lineNumber) {
        this.lineNumber = lineNumber;
    }
}