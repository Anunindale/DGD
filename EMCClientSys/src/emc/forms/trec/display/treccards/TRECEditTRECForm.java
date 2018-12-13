/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards;

import emc.forms.trec.display.treccards.resources.TRECEditEButton;
import emc.forms.trec.display.treccards.resources.TRECEditButton;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.jasper.display.EMCJasperViewer;
import emc.app.jasper.emcJasperSetup;
import emc.app.reporttools.JasperInformation;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.CastEMCUserDataToWSUserData;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECTrecCardsLines;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECTypeEnum;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.trec.ServerTRECMethods;
import emc.reporttools.EMCReportConfig;
import emc.ws.EMCGenericWS;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author asd_admin
 */
public class TRECEditTRECForm extends BaseInternalFrame {
    
    private TRECTrecCardsLines trecLine;
    private TRECCustomerChemicals customerChemical;
    //
    private emcDataRelationManagerUpdate trecLinesDRM;
    //
    private JasperInformation jasperInfo;
    private EMCCommandClass reportCMD;
    private String diamondFilePath;
    private EMCXMLHandler xmlHandler;
    //
    private EMCJasperViewer jasperViewer;
    
    public TRECEditTRECForm(EMCUserData userData) {
        super("Edit TREC", true, true, true, true, userData);
        
        if (userData.getUserData(1) != null && userData.getUserData(1) instanceof TRECTrecCardsLines) {
            trecLine = (TRECTrecCardsLines) userData.getUserData().remove(1);
        }
        if (userData.getUserData(1) != null && userData.getUserData(1) instanceof TRECCustomerChemicals) {
            customerChemical = (TRECCustomerChemicals) userData.getUserData().remove(1);
        }
        if (userData.getUserData(1) != null && userData.getUserData(1) instanceof emcDataRelationManagerUpdate) {
            trecLinesDRM = (emcDataRelationManagerUpdate) userData.getUserData().remove(1);
        }
        
        this.setBounds(20, 20, 950, 750);
        
        setupPrint();
        setupViewer(null);
        
        initForm();
        
        printTREC();
    }
    
    private void setupPrint() {
        String fileName = "diamonds";
        String path = System.getProperty("java.class.path");//appPath.toString();
        String[] pathSplit = path.split(":");
        path = pathSplit[pathSplit.length - 1];
        
        File appPath = new File(path);
        try {
            appPath = appPath.getCanonicalFile().getParentFile();
        } catch (IOException e) {
            if (EMCDebug.getDebug()) {
                System.out.println("Failed to get the path for servername");
            }
        }
        
        path = appPath.getPath();
        
        String filePath = path + File.separator + fileName;

        //Windows seems to append an ';' at the end of the app path.  This is a hack to enable this code to work on Windows.
        filePath = filePath.replaceAll(";", "");
        boolean p = new File(filePath).exists();
        
        if (p) {
            diamondFilePath = filePath;
        } else {
            
            diamondFilePath = "";
        }
        
        jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/trec/treccard/TrecCard.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.trec.treccard.TRECTrecCardDS");
        jasperInfo.setReportTitle("TREC");
        jasperInfo.addParameter("clasPicPath", diamondFilePath);
        jasperInfo.addParameter("hazardPic1Path", diamondFilePath);
        jasperInfo.addParameter("hazardPic2Path", diamondFilePath);
        
        reportCMD = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.TREC.getId(), ServerTRECMethods.PRINT_SINGLE_TREC.toString());
        
        xmlHandler = new EMCXMLHandler();
    }
    
    private void setupViewer(JasperPrint print) {
        jasperViewer = new EMCJasperViewer(print, jasperInfo, getUserData());
        
        JPanel viewerToolbar = (JPanel) jasperViewer.getComponent(0);// Viewer Toolbar
        viewerToolbar.remove(0);//Remove Save
        viewerToolbar.remove(0);//Remove Print
        viewerToolbar.remove(0);//Remove Refresh
    }
    
    private void initForm() {
        this.setLayout(new BorderLayout());
        this.add(jasperViewer, BorderLayout.CENTER);
        this.add(buttonPane(), BorderLayout.EAST);
    }
    
    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<>();
        
        buttonList.add(new TRECEditButton("(H) Hazard", TRECTypeEnum.H, this, getUserData()));
        buttonList.add(new TRECEditButton("(P) PPE", TRECTypeEnum.P, this, getUserData()));
        buttonList.add(new TRECEditButton("(Q) Driver Equip", TRECTypeEnum.Q, this, getUserData()));
        buttonList.add(new TRECEditButton("(D) First Action", TRECTypeEnum.D, this, getUserData()));
        buttonList.add(new TRECEditButton("(S) Special Action", TRECTypeEnum.S, this, getUserData()));
        buttonList.add(new TRECEditButton("(F) Fire", TRECTypeEnum.F, this, getUserData()));
        buttonList.add(new TRECEditButton("(A) First Aid", TRECTypeEnum.A, this, getUserData()));
        buttonList.add(new TRECEditEButton("(E) Special Info ES", this, getUserData()));
        buttonList.add(new emcJButton("Approve") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.APPROVE_TREC);
                
                List toSend = new ArrayList();
                toSend.add(trecLine.getRecordID());
                
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());
                
                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    Logger.getLogger("emc").log(Level.INFO, "Trec line approved.", getUserData());
                    
                    trecLinesDRM.refreshRecordIgnoreFocus(trecLinesDRM.getLastRowAccessed());
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to approve trec line.", getUserData());
                }
            }
        });
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
    
    public void printTREC() {
        boolean printRedLines = true;
        
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("printLines", printRedLines);
        parametersMap.put("boldExpDate", true);
        parametersMap.put("clasPicPath", diamondFilePath);
        parametersMap.put("printHazardZone", "");
        parametersMap.put("hazardPic1Path", diamondFilePath);
        parametersMap.put("hazardPic2Path", diamondFilePath);
        parametersMap.put("ignoreApproval", true);
        
        EMCReportConfig config = new EMCReportConfig(EnumReports.TREC_REPORT, EnumReports.FROM_CLIENT, "Custom", parametersMap);
        
        reportCMD.setReportConfig(config);
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
        query.addAnd("recordID", trecLine.getRecordID());
        
        ArrayList toSend = new ArrayList();
        toSend.add(query);
        toSend.add(printRedLines);
        
        
        EMCGenericWS port = EMCWSManager.createPorts(1)[0];
        
        emc.ws.EmcUserData castedUD = (emc.ws.EmcUserData) CastEMCUserDataToWSUserData.cast("executeEMCGenericWS", port, getUserData(), 1);
        
        String reportResult = xmlHandler.toXML(reportCMD, toSend, getUserData());
        
        if (emc.app.config.emcserverpath.getDoCompression()) {
            reportResult = xmlHandler.decompress(port.executeEMCGenericWSByte(xmlHandler.compress(reportResult, getUserData()), castedUD), getUserData());
        } else {
            reportResult = port.executeEMCGenericWS(reportResult, castedUD);
        }
        
        reportResult = removeNullAndNaN(reportResult);
        
        reportResult = new EMCXMLHandler().reinstateNewLines(reportResult);
        
        emcJasperSetup jasp = new emcJasperSetup(reportResult, jasperInfo, false, config.getReportId(), false, null, getUserData());
        JasperPrint jaspPrint;
        try {
            jaspPrint = jasp.print(reportResult, jasperInfo.getJasperTemplate(), jasperInfo.getXmlNodePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        
        remove(jasperViewer);
        
        setupViewer(jaspPrint);
        
        add(jasperViewer, BorderLayout.CENTER);
        
        revalidate();
    }
    
    private static String removeNullAndNaN(String toParse) {
        toParse = toParse.replaceAll("\"NaN\"", "'0.0'");
        toParse = toParse.replaceAll("\"Infinity\"", "'0.0'");
        int pos = toParse.indexOf("'null'");
        String temp = "";
        while (pos != -1) {
            temp += toParse.substring(0, pos) + "''";
            toParse = toParse.substring(pos + 6, toParse.length());
            pos = toParse.indexOf("'null'");
        }
        
        return temp += toParse;
    }
    
    public void setCustomerChemical(TRECCustomerChemicals customerChemical) {
        this.customerChemical = customerChemical;
    }
    
    public TRECCustomerChemicals getCustomerChemical() {
        return customerChemical;
    }
    
    public void refreshTRECLine() {
        trecLinesDRM.refreshRecordIgnoreFocus(trecLinesDRM.getLastRowAccessed());
    }
}
