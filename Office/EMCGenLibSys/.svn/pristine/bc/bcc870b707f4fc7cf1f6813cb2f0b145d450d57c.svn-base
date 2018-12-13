/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.workflow;

/**
 *
 * @author wikus
 */
public enum WFMyActivitiesEnum {

    //My Activities
    EMPLOYEE_ACTIVITIES_TODAY(0, "All My Active Activities For Today"),
    EMPLOYEE_ACTIVITIES_ALL(1, "All My Active Activities"),
    EMPLOYEE_ACTIVITIES_OVERDUE(2, "All My Overdue Activities"),
    EMPLOYEE_ACTIVITIES_NOT_CLOSED(3, "All My Activities Completed, But Not Closed"),
    //Activities: I am the Employee Manager
    MANAGER_ACTIVITIES_ALL(4, "All activities, I am the manager"),
    MANAGER_ACTIVITIES_OVERDUE(5, "Overdue activities, I am the manager"),
    //Activities: I am the Manager of the activity
    TASK_ACTIVITIES_ALL(7, "All activities, I am the task manager"),
    TASK_ACTIVITIES_OVERDUE(8, "Overdue activities, I am the task manager"),
    TASK_ACTIVITIES_NOT_STARTED(9, "Activities not Started"),
    TASK_ACTIVITIES_TO_CLOSE(10, "Activities that I have to close");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  HRDependantsTypes*/
    WFMyActivitiesEnum(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public static WFMyActivitiesEnum fromString(final String str) {
        for (WFMyActivitiesEnum e : WFMyActivitiesEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static WFMyActivitiesEnum fromId(final int id) {
        for (WFMyActivitiesEnum e : WFMyActivitiesEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
