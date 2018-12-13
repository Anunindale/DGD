/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.prospect.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.crm.CRMDocuments;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.crm.menuitems.display.CRMDocumentsMenu;
import emc.methods.crm.ServerCRMMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author wikus
 */
public class CRMDocumentSelectForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcJList toProcess = new emcJList();
    private List<String> docList = new ArrayList();

    public CRMDocumentSelectForm(EMCUserData userData) {
        super("Documents", true, true, true, true, userData);
        this.userData = userData;
        this.setSize(650, 300);
        initFrame();
        this.setVisible(true);
    }

    private void initFrame() {
        final EMCLookup lkpDocuments = new EMCLookup(new CRMDocumentsMenu());
        lkpDocuments.setPopup(new EMCLookupPopup(new CRMDocuments(), "documentId", userData));
        emcJButton btnAdd = new emcJButton("Add") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                Object doc = lkpDocuments.getValue();
                if (!Functions.checkBlank(doc)) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.CRM.getId(), ServerCRMMethods.VALIDATE_DOCUMENT.toString());
                    List toSend = new ArrayList();
                    toSend.add(doc);
                    docList.add(doc.toString());
                    if ((Boolean) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1)) {
                        toProcess.setListData(new Vector(docList));
                    }
                }
            }
        };
        emcJButton btnRemove = new emcJButton("Remove") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                Object[] toRemove = toProcess.getSelectedValues();
                for (int i = 0; i < toRemove.length; i++) {
                    docList.remove(toRemove[i].toString());
                }
                toProcess.setListData(new Vector(docList));
                toProcess.revalidate();
            }
        };
        toProcess.setVisibleRowCount(-1);

        Component[][] components = null;
        emcJLabel attacheText = new emcJLabel("To Print");
        attacheText.setPreferredSize(new Dimension(370, 75));
        attacheText.setUsePreferedDimensions(true);
        components = new Component[][]{
                    {new emcJLabel("Documents"), lkpDocuments, btnAdd, btnRemove},
                    {toProcess, attacheText}};

        emcJPanel pnlMain = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        emcJButton btnOk = new emcJButton("Ok") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                String docString = "";
                boolean firstTime = true;
                for (String cur : docList) {
                    if (firstTime) {
                        docString += cur;
                        firstTime = false;
                    } else {
                        docString += ";" + cur;
                    }
                }
                List docList = new ArrayList();
//                if (!Functions.checkBlank(docString)) {
//                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.CRM.getId(), ServerCRMMethods.GET_DOCUMENTS.toString());
//                    List toSend = new ArrayList();
//                    toSend.add(docString);
//                    docList = EMCWSManager.executeGenericWS(cmd, toSend, userData);
//                    docList.remove(0);
//                }
                if (!docList.isEmpty()) {
                    CRMDocumentSelectForm.this.doPrint(docList);
                } else {
                    Logger.getLogger("emc").log(Level.WARNING, "No document selected", userData);
                }

            }
        };
        buttonList.add(btnOk);
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                CRMDocumentSelectForm.this.dispose();
            }
        };
        buttonList.add(btnCancel);


        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tab = new emcJTabbedPane();
        tab.addTab("Complete", pnlMain);
        contentPane.add(tab, BorderLayout.CENTER);
        contentPane.add(emcSetGridBagConstraints.createButtonPanel(buttonList), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private void doPrint(List docList) {
        try {
            DocFlavor psFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(psFlavor, aset);
            PrintService services = null;
            services = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 250, 250, printService, services, psFlavor, aset);
            if (service != null) {
                for (Object o : docList) {
                    DocPrintJob job = service.createPrintJob();
                    DocAttributeSet d = new HashDocAttributeSet();
                    FileInputStream fis = null;
                    Doc doc = null;
//                    fis = new FileInputStream(((CRMDocuments) o).getDocumentPath());
                    doc = new SimpleDoc(fis, psFlavor, d);
                    job.print(doc, aset);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to print document: " + ex.getMessage(), userData);
            return;
        }
        Logger.getLogger("emc").log(Level.INFO, "Documents Printed", userData);
        this.dispose();
    }
}


