package emc.entity.gl;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.budgetperiod.Amount;
import emc.datatypes.gl.budgetperiod.BudgetPeriod;
import emc.datatypes.gl.budgetperiod.foreignkeys.LineRecordIdFK;
import emc.framework.EMCEntityClass;
import java.math.BigDecimal;
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
@Table(name = "GLBudgetPeriod", uniqueConstraints = {@UniqueConstraint(columnNames = {"lineRecordId", "budgetPeriod", "companyId"})})
public class GLBudgetPeriod extends EMCEntityClass {

    private long lineRecordId;
    private String budgetPeriod;
    private BigDecimal amount;

    /** Creates a new instance of GLBudgetPeriod. */
    public GLBudgetPeriod() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("lineRecordId", new LineRecordIdFK());
        toBuild.put("budgetPeriod", new BudgetPeriod());
        toBuild.put("amount", new Amount());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("budgetPeriod");
        toBuild.add("amount");
        return toBuild;
    }

    public long getLineRecordId() {
        return this.lineRecordId;
    }

    public void setLineRecordId(long lineRecordId) {
        this.lineRecordId = lineRecordId;
    }

    public String getBudgetPeriod() {
        return this.budgetPeriod;
    }

    public void setBudgetPeriod(String budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}