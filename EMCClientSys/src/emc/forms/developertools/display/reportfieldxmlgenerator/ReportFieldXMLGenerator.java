/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.developertools.display.reportfieldxmlgenerator;

import emc.app.components.emcJButton;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name         : ReportFieldXMLGenerator
 *
 * @Date         : 24 Sep 2009
 *
 * @Description  : This form is used to generate XML for fields on Jasper Reports.
 *
 * @author       : riaan
 */
public class ReportFieldXMLGenerator extends BaseInternalFrame {


    /** Creates a new instance of ReportFieldXMLGenerator. */
    public ReportFieldXMLGenerator(EMCUserData userData) {
        super("Report Field XML Generator", true, true, true, true, userData);
        setBounds(20, 20, 500, 500);
        emcJTextField txtClassName = new emcJTextField();
        emcJTextArea txaOuput = new emcJTextArea();
        ReportXMLGenerateButton button = new ReportXMLGenerateButton(txtClassName, txaOuput, userData);

        this.add(txtClassName, BorderLayout.NORTH);
        this.add(button, BorderLayout.SOUTH);
        this.add(new emcJScrollPane(txaOuput), BorderLayout.CENTER);
    }

    private class ReportXMLGenerateButton extends emcJButton {

        private emcJTextArea txaOutput;
        private emcJTextField txtClassName;
        private EMCUserData userData;

        /** Creates a new instance of ReportXMLGenerateButton. */
        public ReportXMLGenerateButton(emcJTextField txtClassName, emcJTextArea txaOutput, EMCUserData userData) {
            super("Generate XML");
            this.txtClassName = txtClassName;
            this.txaOutput = txaOutput;
            this.userData = userData;
        }

        @Override
        public void doActionPerformed(ActionEvent evt) {
            super.doActionPerformed(evt);

            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.GENERATE_REPORT_XML.toString());

            List toSend = new ArrayList();
            toSend.add(txtClassName.getText());

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            txaOutput.setText(toSend.get(1).toString().replaceAll("<LF>", "\n"));
        }

    }
}
