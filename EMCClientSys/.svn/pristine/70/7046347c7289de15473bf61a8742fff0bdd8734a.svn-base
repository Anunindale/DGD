/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.batchprocessing;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.batchprocess.BaseBatchProcess;
import emc.enums.enumQueryTypes;
import emc.forms.base.display.batchprocessing.resources.BaseBatchProcessPriorityDialog;
import emc.framework.EMCQuery;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class BaseBatchProcessingForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public BaseBatchProcessingForm(EMCUserData userData) {
        super("Batch Processing", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 290);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseBatchProcess(), userData), userData) {

            @Override
            public void insertRowCache(int rowIndex, boolean addRowAfter) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to add records to this table.", getUserData());
            }

            @Override
            public void deleteRowCache(int rowIndex) {
                Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to remove records from this table.", getUserData());
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("command");
        dataManager.setFormTextId2("status");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Batch Processing", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("createdDate");
        keys.add("createdBy");
        keys.add("source");
        keys.add("command");
        keys.add("priority");
        keys.add("status");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnPriority = new emcJButton("Priority") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                new BaseBatchProcessPriorityDialog(dataManager);
            }
        };
        emcJButton btnMessages = new emcJButton("Messages") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.LOG_BASEBATCHPROCESS_MESSAGES);

                List toSend = new ArrayList();
                toSend.add(dataManager.getLastFieldValueAt("recordID"));

                EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());
            }
        };


        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnPriority);
        buttonList.add(btnMessages);

        EMCQuery defaultQuery = new BaseBatchProcess().getQuery();

        EMCQuery allQuery = new EMCQuery(enumQueryTypes.SELECT, BaseBatchProcess.class);

        EMCQuerySwitchButton querySwitchButton = new EMCQuerySwitchButton("All", dataManager);
        querySwitchButton.addQuery("Unprocessed", defaultQuery);
        querySwitchButton.addQuery("All", allQuery);

        buttonList.add(querySwitchButton);
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
