/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.output.permissions;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.BooleanParameterObject;
import emc.app.util.populateMenu;
import emc.commands.EMCCommands;
import emc.entity.base.Usertable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.helpers.base.BaseUserPermissionsReportMIHelper;
import emc.methods.base.ServerBaseMethods;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan
 */
public class BaseUserPermissionsReportForm extends ReportFrame {

    private Map<String, BaseUserPermissionsReportMIHelper> menuItemLabels;

    /** Creates a new instance of BaseUserPermissionsReportForm */
    public BaseUserPermissionsReportForm(EMCUserData userData) {
        super("User Permissions", EnumReports.BASE_USER_PERMISSIONS, userData);

        this.addReportParameter("print_exceptions_only", new BooleanParameterObject("Exceptions Only"));
        menuItemLabels = new HashMap<String, BaseUserPermissionsReportMIHelper>();

        DefaultMutableTreeNode adminRoot = new DefaultMutableTreeNode("EMC");
        populateMenu.getNode(adminRoot, new emc.menus.mainMenu());

        addMenuItems(adminRoot, menuItemLabels, userData);

        userData.setUserData(3, new EMCXMLHandler().ObjectToXML(menuItemLabels, userData));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(Usertable.class.getName());
        whereTable.setField("userId");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(Usertable.class.getName());

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/base/permissions/BaseUserPermissionsReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.base.permissions.BaseUserPermissionsReportDS");
        jasperInfo.setReportTitle("User Permissions");

        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.PRINT_USER_PERMISSIONS_REPORT.toString());
    }

    /**
     * Adds menu items class names and labels from the specified menu tree node to
     * the specified Map.
     * @param menuNode Menu tree node to get menu items from.
     * @param menuItems Map in which menu item name/label mappings should be stored.
     * @param userData User data.
     */
    private void addMenuItems(DefaultMutableTreeNode menuNode, Map<String, BaseUserPermissionsReportMIHelper> menuItems, EMCUserData userData) {
        if (menuNode.isLeaf()) {
            EMCMenuItem menuItem = (EMCMenuItem) menuNode.getUserObject();

            BaseUserPermissionsReportMIHelper helper = new BaseUserPermissionsReportMIHelper();
            helper.setMenuItemLabel(menuItem.getMenuItemName());
            helper.setFormClassName(menuItem.getClassPath());
            menuItems.put(menuItem.getClass().getName(), helper);
        } else {
            Enumeration<DefaultMutableTreeNode> children = menuNode.children();

            while (children.hasMoreElements()) {
                addMenuItems(children.nextElement(), menuItems, userData);
            }
        }
    }
}
