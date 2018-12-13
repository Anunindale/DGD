/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.workflow.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.functions.xml.EMCXMLHandler;
import emc.wf.common.EvaluationTree;
import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author rico
 */
public class evaluateButton extends emcMenuButton {

    private emcDataRelationManagerUpdate manager;
    private BaseInternalFrame theForm;

    public evaluateButton(String label, EMCMenuItem buttonitem,
                          BaseInternalFrame form, int relatedFormIndex, emcDataRelationManagerUpdate manager) {
        super(label, buttonitem, form, relatedFormIndex, false);
        this.manager = manager;
        this.theForm = form;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCCommandClass cmd = new EMCCommandClass();
        cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        cmd.setModuleNumber(enumEMCModules.WORKFLOW.getId());
        cmd.setMethodId(emc.methods.workflow.ServerWorkFlowMethods.EVALUATE_WFMASTER_LINES.toString());
        List toSend = new ArrayList();
        toSend.add((WorkFlowMaster) manager.getRowCache(manager.getLastRowAccessed()));
        List result = EMCWSManager.executeGenericWS(cmd, toSend, manager.getUserData());
        String x = result.get(1).toString();

        EvaluationTreeModel theModel = loadTree(x);
        theForm.getDeskTop().createAndAdd(new emc.menus.workflow.menuitems.display.evaluteWF(), -1, -1, null, theModel, -3);



    }

    private EvaluationTreeModel loadTree(String toParse) {
        toParse = new EMCXMLHandler().reinstateNewLines(toParse);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        EvaluationTreeModel theModel = new EvaluationTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(toParse));

            int type = 0;
            EvaluationTree node = null;

            DefaultMutableTreeNode n = root;

            while (reader.hasNext()) {
                type = reader.next();

                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:

                        String name = reader.getLocalName();
                        if (name.equalsIgnoreCase("EVALUATIONTREE")) {
                            node = new EvaluationTree();
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if ("DESCRIPTION".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setDescription(reader.getAttributeValue(i));
                                } else if ("LINENO".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setLineNo(Double.parseDouble(reader.getAttributeValue(i)));
                                } else if ("NEXTLINENO".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setNextLineNo(Double.parseDouble(reader.getAttributeValue(i)));
                                } else if ("PRIMARYINDICATOR".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setPrimaryIndicator(reader.getAttributeValue(i));
                                }

                            }

                            DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
                            theModel.insertNodeInto(child, n, n.getChildCount());
                            n = child;
                            break;
                        }

                    case XMLStreamConstants.END_ELEMENT:
                        if (reader.getLocalName().equalsIgnoreCase("EVALUATIONTREE")) {
                            n = (DefaultMutableTreeNode) n.getParent();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theModel;
    }
}
