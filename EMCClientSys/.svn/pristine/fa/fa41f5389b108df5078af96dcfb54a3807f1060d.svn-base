/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.emailing;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.parameters.ReportParameterObjectMap;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.action.EMCEmailFormMenu;
import emc.reporttools.EMCReportConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wikus
 */
public class EMCEmailButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate formDataManager;
    private String emailField;
    private String referenceField;
    private Map<String, EMCReportEmailHelper> reportHelperMap;

    public EMCEmailButton(emcDataRelationManagerUpdate formDataManager, String emailField, String referenceField) {
        super("Email", null);
        this.formDataManager = formDataManager;
        this.emailField = emailField;
        this.referenceField = referenceField;
        this.addMenuItem("General", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        EMCEmailFormMenu theMenu = new EMCEmailFormMenu();
        theMenu.setDoNotOpenForm(false);
        EMCUserData generatedUD = formDataManager.getUserData().copyUserData();
        generatedUD.setUserData(0, theCmd);
        generatedUD.setUserData(1, formDataManager);
        generatedUD.setUserData(2, emailField);
        generatedUD.setUserData(3, referenceField);
        generatedUD.setUserData(4, reportHelperMap);
        formDataManager.getTheForm().getDeskTop().createAndAdd(theMenu, -1, -1, generatedUD, null, 0);
    }

    /**
     * Email reports to who ever
     *
     * @param lable       The lable in the buttons list
     * @param reportId    The report to print
     * @param jasperInfo  The Jasper info for the report
     * @param cmd         The EMCCommand to print the report
     * @param config      The EMCReportConfig for the report
     * @param reportQuery The EMCQuery to generate the report
     * @param reportField The the field in the query to replace for each record
     * @param reportClass The class for the report field
     * @param formField   The field wich will be put into the report quey
     */
    public void addReport(String lable, EnumReports reportId, JasperInformation jasperInfo, EMCCommandClass cmd, EMCReportConfig config,
                          EMCQuery reportQuery, String reportField, Class reportClass, String formField, ReportParameterObjectMap reportParameterObjects) {
        this.addMenuItem(lable, null, 0, false);
        EMCReportEmailHelper helper = new EMCReportEmailHelper();
        helper.setReportId(reportId);
        helper.setJasperInfo(jasperInfo);
        helper.setCommandClass(cmd);
        helper.setReportConfig(config);
        helper.setReportQuery(reportQuery);
        helper.setReportQueryField(reportField);
        helper.setReportQueryClass(reportClass);
        helper.setFormField(formField);
        helper.setReportParameterObjects(reportParameterObjects);
        if (reportHelperMap == null) {
            reportHelperMap = new HashMap<String, EMCReportEmailHelper>();
        }
        reportHelperMap.put(lable, helper);
    }

}
