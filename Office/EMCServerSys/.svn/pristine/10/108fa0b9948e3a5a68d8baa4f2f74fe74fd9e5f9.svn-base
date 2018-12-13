/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.base;

import emc.entity.base.Usertable;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reporttools.EMCReportConfig;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BasePermissionsInfoReportBean extends EMCReportBean implements BasePermissionsInfoReportLocal {

    @Override
    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData) {
        List reportData = new ArrayList();
        List selectionList;
        String menuItemClassPath;
        String formClassPath;
        String module;
        String form;
        EMCQuery query;
        BasePermissionsInfoReportDS ds;

        queryList.remove(0);

        for (Object o : queryList) {
            if (o instanceof List) {
                selectionList = (List) o;

                menuItemClassPath = selectionList.get(0).toString();
                formClassPath = selectionList.get(1).toString();

                module = Functions.getEMCModule(menuItemClassPath).toString();
                form = formClassPath.substring(formClassPath.lastIndexOf(".") + 1);

                query = new EMCQuery(enumQueryTypes.SELECT, BasePermissionsTable.class);
                query.addTableAnd(Usertable.class.getName(), "userId", BasePermissionsTable.class.getName(), "userId");
                query.addAnd("userMenu", menuItemClassPath, EMCQueryConditions.LIKE);
                query.addField("userId", Usertable.class.getName());
                query.addField("userName", Usertable.class.getName());
                query.addOrderBy("userId", Usertable.class.getName());
                List<Object[]> userTableDataList = util.executeGeneralSelectQuery(query, userData);

                String formAccess;

                for (Object[] userTableData : userTableDataList) {
                    query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class);
                    query.addAnd("userId", (String) userTableData[0]);
                    query.addAnd("formClassName", formClassPath);
                    query.addField("formAccess");
                    formAccess = (String) util.executeSingleResultQuery(query, userData);
                    if (isBlank(formAccess)) {
                        formAccess = "Create";
                    }

                    ds = new BasePermissionsInfoReportDS();
                    ds.setUserId((String) userTableData[0]);
                    ds.setUserName((String) userTableData[1]);
                    ds.setModule(module);
                    ds.setForm(form);
                    ds.setAccess(formAccess);
                    reportData.add(ds);
                }
            }
        }
        return reportData;
    }
}
