/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.base.permissions;

/**
 *
 * @author riaan
 */
public class BaseUserPermissionsReportDS {

    private String userId;
    private String userName;
    private String moduleName;
    private String formName;
    private String formAccess;
    private String component;
    private String componentType;
    private String componentAccess;

    /** Creates a new instance of BaseUserPermissionsReportDS. */
    public BaseUserPermissionsReportDS() {
        
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormAccess() {
        return formAccess;
    }

    public void setFormAccess(String formAccess) {
        this.formAccess = formAccess;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentAccess() {
        return componentAccess;
    }

    public void setComponentAccess(String componentAccess) {
        this.componentAccess = componentAccess;
    }
}
