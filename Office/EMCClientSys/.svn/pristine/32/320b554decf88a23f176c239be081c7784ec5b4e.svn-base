/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.activetransactions;

import emc.app.components.emcDialogue;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.StopWatch;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class ActiveTransactionsForm extends BaseInternalFrame {

    private emcJPanel pnlUsers;
    private emcJPanel pnlButtons;
    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData copyUD;
    private javax.swing.Timer refrehT;

    /** Creates a new instance of AdminUsersForm */
    public ActiveTransactionsForm(EMCUserData userData) {
        //Calls the super class constructor
        super("Active Transactions", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 300);

        //Sets up the data relation manager for this form
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                enumEMCModules.BASE.getId(), new emc.entity.base.datasource.EMCTransactions(), userData), userData){
            //refresh record does not make sense on this form
            @Override
            public void refreshRecord(int rowIndex) {
                super.refreshData();
            }
         };
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);

        //Creates a copy of the given user data
        copyUD = userData.copyUserData();
        copyUD.setUserData(null);

        dataRelation.setFormTextId1("sessionNumber");
        dataRelation.setFormTextId2("userId");

        //Initializes the frame
        initFrame();
        refrehT = new javax.swing.Timer(500, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                StopWatch sw = new StopWatch();
                sw.start();
                dataRelation.refreshData();
                sw.stop();
                if(sw.getElapsedTime()>500)System.out.println(Functions.date2String(Functions.nowDate())+"|" + Functions.date2TimeStringSeconds(Functions.nowDate())+ "|" + sw.getElapsedTime() );
            }
        });
        refrehT.start();
    }

    private void initFrame() {
        //Tabbed pane used on the form
        emcJTabbedPane tabbedPaneTop = new emcJTabbedPane();

        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int y = 0;

        //Sets up the tabs on the form
        tabUsers();
        initButtons();
        //Adds the tabs to the tabbed pane
        tabbedPaneTop.add(this.pnlUsers, "Active Transactions");

        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.99, GridBagConstraints.FIRST_LINE_START);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        thePanel.add(tabbedPaneTop, gbc);
        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.01, GridBagConstraints.FIRST_LINE_START);

        gbc.fill = GridBagConstraints.BOTH;
        thePanel.add(pnlButtons, gbc);

        this.add(thePanel);
    }

    private void tabUsers() {
        this.pnlUsers = new emcJPanel();

        List keys = new ArrayList();
        keys.add("sessionNumber");
        keys.add("userId");
        keys.add("command");
        keys.add("transactionStartDate");
        keys.add("transactionStartTime");
        keys.add("transactionRuntime");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlUsers.setLayout(new GridLayout(1, 1));
        pnlUsers.add(topscroll);
        this.setTablePanel(topscroll);
    }
    private void initButtons() {


        emcMenuButton rollBack = new emcMenuButton("Roll Back", null, this, 0, false){

            @Override
            public void doActionPerformed(ActionEvent e) {
                emcDialogue resetOK = new emcDialogue("Click a button", "Roll back transaction: Are you sure?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
                if (resetOK.getDialogueResult() == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass();
                    cmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
                    cmd.setModuleNumber(enumEMCModules.BASE.getId());
                    cmd.setMethodId(emc.methods.base.ServerBaseMethods.ROLL_BACK_TRANSACTION.toString());
                    List toSend = new ArrayList();
                    toSend.add(dataRelation.getFieldValueAt(dataRelation.getLastRowAccessed(),"transaction"));
                    try {
                        List result = EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData());
                        if(result.size()>1 && (Boolean)result.get(1)== true){
                            //Logger.getLogger("emc").log(Level.INFO, "Transaction sucessfully rolled back.", dataRelation.getUserData());
                        }else{
                           // Logger.getLogger("emc").log(Level.WARNING, "Failed to roll back transaction.", dataRelation.getUserData());
                        }
                    } catch (Exception ex) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to roll back transaction.", dataRelation.getUserData());
                    }
                }
            }
        };
        rollBack.setEnabled(true);
        emcMenuButton startTimer = new emcMenuButton("Auto Refresh", null, this, 0, false){

            @Override
            public void doActionPerformed(ActionEvent e) {
                refrehT.start();
            }
        };
        emcMenuButton stopTimer = new emcMenuButton("Stop Auto", null, this, 0, false){

            @Override
            public void doActionPerformed(ActionEvent e) {
                refrehT.stop();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(rollBack);
        buttonList.add(startTimer);
        buttonList.add(stopTimer);
        this.pnlButtons = emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
