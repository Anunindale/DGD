/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.allocationimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.allocationimport.AllowPartialAllocation;
import emc.datatypes.debtors.allocationimport.AutoAllocationPaymentOrder;
import emc.datatypes.debtors.allocationimport.CustomerId;
import emc.datatypes.debtors.allocationimport.Description;
import emc.datatypes.debtors.allocationimport.ImportCode;
import emc.datatypes.debtors.allocationimport.ImportDate;
import emc.datatypes.debtors.allocationimport.ImportFile;
import emc.datatypes.debtors.allocationimport.ImportStatus;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportStatus;
import emc.framework.EMCEntityClass;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsAllocationImport", uniqueConstraints = {@UniqueConstraint(columnNames = {"importCode", "companyId"})})
public class DebtorsAllocationImport extends EMCEntityClass {

    private String importCode;
    private String description;
    private String importFile;
    private String customerId;
    @Temporal(TemporalType.DATE)
    private Date importDate;
    private String importStatus = DebtorsAllocationImportStatus.CAPTURED.toString();

    //These values will default from DebtorsParameters
    private boolean allowPartialAllocation;
    private String autoAllocationPaymentOrder;

    /** Creates a new instance of DebtorsAllocationImport. */
    public DebtorsAllocationImport() {
        
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("importCode", new ImportCode());
        toBuild.put("description", new Description());
        toBuild.put("importFile", new ImportFile());
        toBuild.put("customerId", new CustomerId());
        toBuild.put("importDate", new ImportDate());
        toBuild.put("importStatus", new ImportStatus());
        toBuild.put("allowPartialAllocation", new AllowPartialAllocation());
        toBuild.put("autoAllocationPaymentOrder", new AutoAllocationPaymentOrder());

        return toBuild;
    }

    public String getImportCode() {
        return importCode;
    }

    public void setImportCode(String importCode) {
        this.importCode = importCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportFile() {
        return importFile;
    }

    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public boolean isAllowPartialAllocation() {
        return allowPartialAllocation;
    }

    public void setAllowPartialAllocation(boolean allowPartialAllocation) {
        this.allowPartialAllocation = allowPartialAllocation;
    }

    public String getAutoAllocationPaymentOrder() {
        return autoAllocationPaymentOrder;
    }

    public void setAutoAllocationPaymentOrder(String autoAllocationPaymentOrder) {
        this.autoAllocationPaymentOrder = autoAllocationPaymentOrder;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
}
