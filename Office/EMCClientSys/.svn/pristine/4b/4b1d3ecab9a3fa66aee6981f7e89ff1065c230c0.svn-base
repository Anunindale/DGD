/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.mailsetup;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseMailReturnAddressSetup;
import emc.entity.base.Usertable;
import emc.entity.base.datasource.BaseMailReturnAddressSetupDS;
import emc.enums.base.docref.DocRefSummary;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.documentHandler;
import emc.menus.base.menuItems.display.users;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseMailReturnAddressSetupForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public BaseMailReturnAddressSetupForm(EMCUserData userData) {
        super("Return Address", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseMailReturnAddressSetupDS(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("userId");
        dataManager.setFormTextId2("userName");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());

        this.setLayout(new BorderLayout());
        this.add(tabbed, BorderLayout.CENTER);
        this.add(buttonPane(), BorderLayout.EAST);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("userId");
        keys.add("userName");
        keys.add("emailAddress");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupTableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupTableLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpUser = new EMCLookupJTableComponent(new users());
        lkpUser.setPopup(new EMCLookupPopup(new Usertable(), "userId", userData));
        table.setLookupToColumn("userId", lkpUser);
        table.setColumnEditable("userName", false);
    }

    private emcJPanel buttonPane(){
        emcMenuButton btnAddSignature = new emcMenuButton("Add Signature", new documentHandler(), this, -1, false) {

            @Override
            public void doActionPerformed(ActionEvent e) {
                BaseMailReturnAddressSetupForm.this.doSaveOnButtonPress();
                if ((Long) dataManager.getLastFieldValueAt("recordID") != 0) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CREATE_SPECIFIC_ATT);
                    List toSend = new ArrayList();
                    toSend.add(dataManager.getLastFieldValueAt("recordID"));
                    toSend.add(DocRefSummary.SIGNATURE.toString());
                    toSend.add(BaseMailReturnAddressSetup.class.getSimpleName());
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                }
                super.doActionPerformed(e);
            }
        };
        List buttonList = new ArrayList();
        buttonList.add(btnAddSignature);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
