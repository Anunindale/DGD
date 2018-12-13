/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeecategoryhistory.CategoryChangeDate;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseEmployeeCategoryHistory")
public class BaseEmployeeCategoryHistory extends EMCEntityClass {

    private String employeeNumber;
    private String fromCategory;
    private String toCategory;
    @Temporal(TemporalType.DATE)
    private Date categoryChangeDate;

    public Date getCategoryChangeDate() {
        return categoryChangeDate;
    }

    public void setCategoryChangeDate(Date categoryChangeDate) {
        this.categoryChangeDate = categoryChangeDate;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFromCategory() {
        return fromCategory;
    }

    public void setFromCategory(String fromCategory) {
        this.fromCategory = fromCategory;
    }

    public String getToCategory() {
        return toCategory;
    }

    public void setToCategory(String toCategory) {
        this.toCategory = toCategory;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("categoryChangeDate", new CategoryChangeDate());
        return toBuild;
    }
}
