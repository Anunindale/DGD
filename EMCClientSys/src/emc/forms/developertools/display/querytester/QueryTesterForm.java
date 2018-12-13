/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.developertools.display.querytester;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTree;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import emc.forms.developertools.resources.ExecuteButton;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author riaan
 */
public class QueryTesterForm extends BaseInternalFrame {
    
    private emcJTextArea queryArea = new emcJTextArea();
    private emcJTree resultArea = new emcJTree(new DefaultMutableTreeNode("Results"));
    private emcJLabel lblQuery = new emcJLabel("Query");
    private emcJLabel lblResult = new emcJLabel("Result");
    private ExecuteButton btnExecute = new ExecuteButton("Execute query");
    
    //Creates a new instance of QueryTesterForm
    public  QueryTesterForm(EMCUserData userData) {
        super("Query Tester Utility", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 340);
        //this.setLayout(new GridBagLayout());

        this.setLayout(new GridLayout(1,1));
        
        this.queryArea.setLineWrap(true);
        this.queryArea.setWrapStyleWord(true);
        emcJPanel pnlQuery = new emcJPanel();
        pnlQuery.setLayout(new BorderLayout());
        pnlQuery.add(lblQuery, BorderLayout.NORTH);
        pnlQuery.add(new emcJScrollPane(queryArea), BorderLayout.CENTER);
        
        emcJPanel pnlResult = new emcJPanel();
        pnlResult.setLayout(new BorderLayout());
        pnlResult.add(lblResult, BorderLayout.NORTH);
        pnlResult.add(new emcJScrollPane(resultArea), BorderLayout.CENTER);
        
        
        emcJPanel pnlLeft = new emcJPanel();
        pnlLeft.setLayout(new GridLayout());

        pnlLeft.add(pnlQuery);
        pnlLeft.add(pnlResult);
        emcJSplitPane verticalSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, pnlQuery, pnlResult);
        verticalSplit.setDividerLocation(150);
        pnlLeft.add(verticalSplit);
        
        this.add(pnlLeft);
        
        emcJPanel pnlRight = new emcJPanel();
        pnlRight.setLayout(new GridBagLayout());
        
        Insets insets = new Insets(5, 5, 5, 5);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = insets;
        pnlRight.add(btnExecute, gbc);
        
        gbc = emcSetGridBagConstraints.endPanel(1);
        pnlRight.add(new emcJLabel(), gbc);
        
        this.add(pnlRight);
        
        emcJSplitPane horizontalSplit = new emcJSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeft, pnlRight);
        horizontalSplit.setDividerLocation(400);
        this.add(horizontalSplit);
        
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.insets = insets;
//        this.add(pnlQuery, gbc);
//        
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.weightx = 1;
//        gbc.weighty = 0.2;
//        gbc.fill = GridBagConstraints.NONE;
//        
//        this.add(btnExecute, gbc);
//
//        gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.insets = insets;
//        this.add(pnlResult, gbc);

        this.setVisible(true);
    }
    
    public emcJTextArea getQueryArea() {
        return this.queryArea;
    }
    
    public emcJTree getResultArea() {
        return this.resultArea;
    }
}
