/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.evaluatewf;

import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTree;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.forms.workflow.display.workflow.resources.EvaluationTreeModel;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author rico
 */
public class evaluateWFForm extends BaseInternalFrame {

    private emcJTree evalResult;
    DefaultTreeModel theModel;
    private emcJPanel resultPanel = new emcJPanel();
    private emcJTabbedPane tabbedPane = new emcJTabbedPane();

    public evaluateWFForm(EMCUserData userData) {
        super("Evaluate Work Flow", true, true, true, true, userData);
        evalResult = new emcJTree();
        this.setBounds(20, 20, 350, 250);
        this.addTheListener();
        resultPanel.setLayout(new GridLayout(1, 1));
        resultPanel.add(new emcJScrollPane(evalResult));
        tabbedPane.addTab("Evaluation", resultPanel);

        this.add(tabbedPane);
    }

    public void setDisplayTreeModel(EvaluationTreeModel theModel) {
        this.theModel = theModel;
        evalResult.setModel(this.theModel);
        resultPanel.repaint();
        this.revalidate();
    }
}
