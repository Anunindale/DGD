/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.output.treccard;

import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportOKButton;
import emc.app.wsmanager.EMCReportWSManager;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ivan
 */
public class TrecReportFormOkButton extends ReportOKButton {

    protected ReportFrame reportFrame;

    public TrecReportFormOkButton(ReportFrame reportFrame) {
        super(reportFrame);
        this.reportFrame = reportFrame;
        this.setMargin(new Insets(2, 2, 2, 2));
        this.setPreferredSize(new Dimension(10, 22));
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        JasperInformation jasperInfo = reportFrame.getJasperInfo();

        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("printLines", reportFrame.getReportParameterValues().get("printLines"));
        parametersMap.put("boldExpDate", reportFrame.getReportParameterValues().get("boldExpDate"));
        parametersMap.put("clasPicPath", jasperInfo.getParameters().get("clasPicPath"));
        parametersMap.put("printHazardZone", reportFrame.getReportParameterValues().get("printHazardZone"));
        parametersMap.put("hazardPic1Path", jasperInfo.getParameters().get("hazardPic1Path"));
        parametersMap.put("hazardPic2Path", jasperInfo.getParameters().get("hazardPic2Path"));

        reportFrame.setReportParameterValues(parametersMap);

        super.doActionPerformed(evt);
    }
}
