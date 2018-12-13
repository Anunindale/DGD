/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.releaseTrec;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.forms.trec.display.loadcompatibility.*;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECLoadCompatibility;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.calendar.CalendarForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsBasketMI;
import emc.menus.developertools.trec.TRECClassesMenu;
import emc.menus.trec.menuitems.display.TRECLoadCompMenu;
import emc.methods.base.ServerBaseMethods;
import emc.methods.crm.ServerCRMMethods;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSplitPane;

/**
 *
 * @author rico
 */
public class ReleaseTrecForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;
    private EMCLookupJTableComponent lkpClass;

    public ReleaseTrecForm(EMCUserData userData) {
        super("Release Trec", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEBTORS.getId(), new DebtorsBasketMaster(), userData), userData);
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("basketId");
        dataManager.setFormTextId2("sessionId");
        initFrame();
    }

    private void initFrame() {
        
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        this.setContentPane(contentPane);
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("OverView", lookupPane());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
        
    }
    

    

    private emcJPanel lookupPane() {
        emcJTextField txtDesc = new emcJTextField();
        lkpClass = new EMCLookupJTableComponent(new DebtorsBasketMI());
        lkpClass.setPopup(new EMCLookupPopup(new DebtorsBasketMaster(), "invoiceId", userData));
        Component[][] comp = {{new emcJLabel("Basket / InvoiceNumber"), lkpClass}};
        
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Class");
    }
    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        emcJButton btnOk = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.RELEASE_TREC.toString());
                List toSend = new ArrayList();
                
                toSend.add((String)lkpClass.getValue());
                toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                
                if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                Logger.getLogger("emc").log(Level.INFO, "Basket has been successfully updated and the print quantity has been set to 0. ", userData);
                }
            }
        };
        
         emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                ReleaseTrecForm.this.dispose();
            }

        };
       buttonList.add(btnOk);
       buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
                
}

