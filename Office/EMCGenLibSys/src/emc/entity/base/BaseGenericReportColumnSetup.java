/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.framework.EMCEntityClass;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "BaseGenericReportColumnSetup", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"setupId", "formClassName", "companyId"})})
public class BaseGenericReportColumnSetup extends EMCEntityClass {

    private String setupId;
    private String formClassName;
    @Column(length = 1000)
    private String selectedColumns;
    private boolean printTotals;
    private boolean exportToExcel;

    public String getSetupId() {
        return setupId;
    }

    public void setSetupId(String setupId) {
        this.setupId = setupId;
    }

    public String getFormClassName() {
        return formClassName;
    }

    public void setFormClassName(String formClassName) {
        this.formClassName = formClassName;
    }


    public String getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(String selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public boolean isPrintTotals() {
        return printTotals;
    }

    public void setPrintTotals(boolean printTotals) {
        this.printTotals = printTotals;
    }

    public boolean isExportToExcel() {
        return exportToExcel;
    }

    public void setExportToExcel(boolean exportToExcel) {
        this.exportToExcel = exportToExcel;
    }
}
