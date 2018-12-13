/*
 * transactionLogForm.java
 *
 * Created on 20 December 2007, 09:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.personal.display.transactionlog;

import emc.app.components.emcJLabel;
import emc.app.messagehandler.emcHandler;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTree;
import emc.app.components.emcLoggerRenderer;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCDebug;
import emc.framework.EMCLogRecord;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author rico
 */
public class transactionLogForm extends BaseInternalFrame {

    private emcJTree recent;
    private emcJTree history;
    private DefaultMutableTreeNode recentRoot;
    private DefaultMutableTreeNode historyRoot;
    private DefaultTreeModel recentModel;
    private DefaultTreeModel historyModel;
    private emcJPanel recentPanel = new emcJPanel();
    private emcJPanel historyPanel = new emcJPanel();
    private emcJPanel storeLog = new emcJPanel();
    private emcJTabbedPane tabbedPane = new emcJTabbedPane();
    private emcJScrollPane recentScroll;
    private emcJScrollPane historyScroll;
    private EMCFilePicker filePicker;
    private EMCUserData userData;

    /** Creates a new instance of transactionLogForm */
    public transactionLogForm(EMCUserData userData) {
        super("Message Log", true, true, true, true, userData);
        this.setBounds(20, 20, 370, 310);
        this.userData = userData;
        this.setLogger(true);
        storeLog();
        recentPanel.setLayout(new GridLayout(1, 1));
        historyPanel.setLayout(new GridLayout(1, 1));
        tabbedPane.addTab("Recent", recentPanel);
        tabbedPane.addTab("History", historyPanel);
        tabbedPane.addTab("Store Log", storeLog);
        this.add(tabbedPane);

        createTrees();
        initDisplay();
        updateLogDisplay();
    }

    private void createTrees() {
        this.recentRoot = new DefaultMutableTreeNode();
        this.recentModel = new DefaultTreeModel(recentRoot);
        this.recent = new emcJTree();
        recent.setModel(recentModel);
        recent.setCellRenderer(new emcLoggerRenderer());

        this.historyRoot = new DefaultMutableTreeNode();
        this.historyModel = new DefaultTreeModel(historyRoot);
        this.history = new emcJTree();
        history.setModel(historyModel);
        history.setCellRenderer(new emcLoggerRenderer());
    }

    private void initDisplay() {
        recentScroll = new emcJScrollPane(recent);
        recentPanel.add(recentScroll);

        historyScroll = new emcJScrollPane(history);
        historyPanel.add(historyScroll);
    }

    private synchronized void updateDisplay() {
        recentModel.setRoot(generateRecentRoot());
        historyModel.setRoot(generateHistoryRoot());
        recentModel.reload();
        historyModel.reload();
    }

    public void updateLogDisplay() {
        updateDisplay();
    }

    private void storeLog() {
        storeLog.setLayout(new GridBagLayout());
        filePicker = new EMCFilePicker() {

            private String doWriteFormat(EMCLogRecord record) {
                String msg = record.getMessage();
                long milis = record.getTime();
                String level = record.getLevel().toString();
                if (level.compareTo("ERROR") == 0) {
                    level = "SEVERE";
                }
                return Functions.date2TimeStringSeconds(new Date(milis)) + "\t" + level.toString() + "\t" + msg + "\n";
            }

            @Override
            public void setValue(Object value) {
                super.setValue(value);
                FileWriter fstream = null;
                BufferedWriter out = null;
                try {
                    fstream = new FileWriter(this.getFilePath());
                    out = new BufferedWriter(fstream);
                    List<EMCLogRecord> x = emcHandler.getMessageList(userData);
                    String msg = "";
                    for (EMCLogRecord logRecord : x) {
                        msg = doWriteFormat(logRecord);
                        out.write(msg);
                    }
                } catch (IOException ex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Could not write to file", userData);
                } finally {
                    try {
                        out.close();
                        fstream.close();
                        Logger.getLogger("emc").log(Level.INFO, "File written", userData);
                    } catch (IOException ex) {
                        if (EMCDebug.getDebug()) {
                            Logger.getLogger("emc").log(Level.SEVERE, "Could not close file stream", userData);
                        }
                    }
                }
            }
        };
        //filePicker.setSaveDialog(true);
        //filePicker.setPreferredSize(new Dimension(300, 10));
        emcJLabel lblFile = new emcJLabel("Save Log To");
        lblFile.setPreferredSize(new Dimension(150, 10));
        Component[][] components = {{new emcJLabel()},
            {lblFile, filePicker}};
        storeLog = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.HORIZONTAL, false);
        int y = components.length;
        GridBagConstraints gbc = new GridBagConstraints();
        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);
        storeLog.add(new emcJLabel(), gbc);
    }

    private DefaultMutableTreeNode generateRecentRoot() {
        DefaultMutableTreeNode root = emcHandler.getRecentMessages(this.getUserData());
        if (root == null) {
            root = new DefaultMutableTreeNode("No recent messages.");
        }
        return root;
    }

    private DefaultMutableTreeNode generateHistoryRoot() {
        DefaultMutableTreeNode root = emcHandler.getHistoryMessages(this.getUserData());
        if (root == null) {
            root = new DefaultMutableTreeNode("No history messages.");
        }
        return root;
    }

    @Override
    public void doDefaultCloseAction() {
       emcHandler.moveAllToHistory(userData);
        super.doDefaultCloseAction();
    }
    
    
}
