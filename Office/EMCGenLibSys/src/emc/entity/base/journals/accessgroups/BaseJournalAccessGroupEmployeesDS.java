/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.journals.accessgroups;

/**
 *
 * @author wikus
 */
public class BaseJournalAccessGroupEmployeesDS extends BaseJournalAccessGroupEmployees {

    private String employeeName;

    public BaseJournalAccessGroupEmployeesDS() {
        this.setDataSource(true);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
