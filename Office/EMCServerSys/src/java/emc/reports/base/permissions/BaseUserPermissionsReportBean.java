/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.base.permissions;

import emc.bus.base.permissions.BasePermissionsTableLocal;
import emc.bus.base.permissions.BaseUserPermissionsTableLocal;
import emc.entity.base.Usertable;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.helpers.base.BaseUserPermissionsReportMIHelper;
import emc.messages.ServerBaseMessageEnum;
import emc.app.permissions.DefaultPermissions;
import emc.app.permissions.FieldPermissions;
import emc.app.permissions.FormPermissions;
import emc.app.permissions.PermissionTreeObject;
import emc.reporttools.EMCReportConfig;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseUserPermissionsReportBean extends EMCReportBean implements BaseUserPermissionsReportLocal {

    @EJB
    private BasePermissionsTableLocal permissionsBean;
    @EJB
    private BaseUserPermissionsTableLocal userPermissionsBean;

    /** Creates a new instance of BaseUserPermissionsReportBean. */
    public BaseUserPermissionsReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        //Indicates whether fields should only be printed when the field permission differs from the form permission.
        userData.setUserData(4, reportConfig.getParameters().get("print_exceptions_only") == Boolean.TRUE);
        return super.getReportResult(queryList, reportConfig, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<Object>();

        boolean exceptionsOnly = userData.getUserData(4) == Boolean.TRUE;
        
        //HashMap with menu item name/label mappings is stored as an XML String in user data.
        Map<String, BaseUserPermissionsReportMIHelper> menuItemLabels = (Map<String, BaseUserPermissionsReportMIHelper>) new EMCXMLHandler().XMLToObject((String) userData.getUserData(3), userData);
        if (menuItemLabels == null) {
            logMessage(Level.SEVERE, ServerBaseMessageEnum.NO_MENU_MAPPING, userData);
        }

        for (Object result : queryResult) {
            //Detach entities after each user.  For first iteration, clear selected users from entity manager.
            util.detachEntities(userData);
            Usertable user = (Usertable) result;

            List<String> menuItems = permissionsBean.getUserMenuItems(user.getUserId(), userData);

            for (String menuItem : menuItems) {
                BaseUserPermissionsReportDS formDS = new BaseUserPermissionsReportDS();
                formDS.setUserId(user.getUserId());
                formDS.setUserName(user.getUserName());

                BaseUserPermissionsReportMIHelper helper = menuItemLabels.get(menuItem);

                String moduleName = String.valueOf(Functions.getEMCModule(menuItem));

                //If module is "null" (String), do not display anything
                formDS.setModuleName("null".equals(moduleName) ? "" : moduleName);

                if (helper != null) {
                    formDS.setFormName(helper.getMenuItemLabel());

                    //Get detailed form permissions
                    BaseUserPermissionsTable formPermissions = userPermissionsBean.getUserFormPermissions(helper.getFormClassName(), user.getUserId(), userData);

                    if (formPermissions != null) {
                        formDS.setFormAccess(formPermissions.getFormAccess());

                        int listSize = ret.size();
                        addComponents(ret, formDS, new EMCXMLHandler().xmltoTree(formPermissions.getPermissionTree(), "Permissions"), exceptionsOnly, userData);

                        if (ret.size() == listSize) {
                            //No component-level permission added to list, use default permissions
                            formDS.setFormAccess(DefaultPermissions.getDefaultFormPermission().getPermission());
                            formDS.setComponent("N/A");
                            formDS.setComponentType("N/A");
                            formDS.setComponentAccess("N/A");

                            ret.add(formDS);
                        }
                    } else {
                        //Use default permissions.
                        formDS.setFormAccess(DefaultPermissions.getDefaultFormPermission().getPermission());
                        formDS.setComponent("N/A");
                        formDS.setComponentType("N/A");
                        formDS.setComponentAccess("N/A");

                        ret.add(formDS);
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Adds data source instances for all components on the specified form.
     * @param dataSourceList Report data source list.
     * @param formDS Data source instance with form information.
     * @param components Tree node containing component permissions.
     * @param exceptionsOnly Indicates whether only fields with permissions that
     * differ from form permissions will be included.
     * @param userData User data.
     */
    private void addComponents(List<Object> dataSourceList, BaseUserPermissionsReportDS formDS, DefaultMutableTreeNode components, boolean exceptionsOnly, EMCUserData userData) {
        Enumeration<DefaultMutableTreeNode> children = components.children();

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode child = children.nextElement();

            if (child.isLeaf()) {
                FormPermissions formPermissions = FormPermissions.fromString(formDS.getFormAccess());

                if (child.getUserObject() instanceof PermissionTreeObject) {
                    PermissionTreeObject permissionObject = (PermissionTreeObject) child.getUserObject();

                    BaseUserPermissionsReportDS componentDS = (BaseUserPermissionsReportDS) util.doClone(formDS, BaseUserPermissionsReportDS.class, userData);
                    componentDS.setComponent(permissionObject.getEmcLabel());
                    componentDS.setComponentAccess(permissionObject.getPermission().getPermission());

                    //Parent should have a String as user object.  For fields, check two levels up - level above child will contain entity class.
                    if (util.checkObjectsEqual(((DefaultMutableTreeNode) ((DefaultMutableTreeNode) child.getParent()).getParent()).getUserObject(), "Datasources")) {
                        componentDS.setComponentType("Field");

                        //Field permission may be blank.  If so, use form permission.
                        if (isBlank(componentDS.getComponentAccess())) {
                            if (exceptionsOnly) {
                                //Ignore record
                                continue;
                            }
                            switch (formPermissions) {
                                case CREATE:    //Fall through
                                case EDIT:
                                    componentDS.setComponentAccess(FieldPermissions.EDIT.getPermission());
                                    break;
                                case VIEW:
                                    componentDS.setComponentAccess(FieldPermissions.VIEW.getPermission());
                                    break;
                                case NO_ACCESS:
                                    componentDS.setComponentAccess(FieldPermissions.NO_ACCESS.toString());
                                    break;
                            }
                        } else {
                            if (exceptionsOnly) {
                                FieldPermissions fieldPermission = FieldPermissions.fromString(componentDS.getComponentAccess());
                                if (!(((FormPermissions.CREATE == formPermissions || FormPermissions.EDIT == formPermissions) && fieldPermission != FieldPermissions.EDIT) ||
                                        (FormPermissions.NO_ACCESS == formPermissions && FieldPermissions.NO_ACCESS != fieldPermission) ||
                                        (FormPermissions.VIEW == formPermissions && FieldPermissions.VIEW != fieldPermission))) {
                                    //Ignore record
                                    continue;
                                }
                            }
                        }
                    } else if (util.checkObjectsEqual(((DefaultMutableTreeNode) child.getParent()).getUserObject(), "Buttons")) {
                        componentDS.setComponentType("Button");
                    }

                    //Compare form permissions to DS permissions
                    dataSourceList.add(componentDS);
                }
            } else {
                addComponents(dataSourceList, formDS, child, exceptionsOnly, userData);
            }
        }
    }
}
