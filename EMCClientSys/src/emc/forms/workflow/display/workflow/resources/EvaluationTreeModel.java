/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.workflow.display.workflow.resources;

import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.scrollabledesktop.formspecialization.FormSpecialization;
import emc.forms.workflow.display.evaluatewf.evaluateWFForm;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author wikus
 */
public class EvaluationTreeModel extends DefaultTreeModel implements FormSpecialization {

    public EvaluationTreeModel(TreeNode root) {
        super(root);
    }

    public void doSpecialization(BaseInternalFrame testFrame) {
        if (testFrame instanceof evaluateWFForm) {
            ((evaluateWFForm) testFrame).setDisplayTreeModel(this);
        }
    }
}
