/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.app.messages;

import emc.app.components.base.EMCModuleDropDown;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.message.PopulateMessagesButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.messages.BaseMessages;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 * @description : This is a superclass for all Message forms in the various EMCModules.
 * @author      : riaan
 */
public class MessagesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;
    private emcJTextArea txaMessage = new emcJTextArea();

    public MessagesForm(enumEMCModules module, EMCUserData userData) {
        super("Messages", true, true,true, true, userData);
        this.setBounds(20, 20, 750, 350);
        try{
            //If a query exists in userData, use that.
            if (Functions.checkBlank(userData.getUserData(0))) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseMessages.class.getName());
                query.addAnd("messageModule", module.toString());
                query.addOrderBy("recordID");

                userData.setUserData(0, query);
            }

            drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.BASE.getId(), new BaseMessages(), userData), userData) {

                @Override
                public void deleteRowCache(int rowIndex) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Rows may not be deleted on this form.", getUserData());
                }

                @Override
                public void insertRowCache(int rowIndex, boolean addRowAfter) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Rows may not be inserted on this form.", getUserData());
                }
            };

            this.setDataManager(drm);

            drm.setTheForm(this);
            drm.setFormTextId1("messageId");
            drm.setFormTextId2("description");

        }catch (Exception e){
            e.printStackTrace();
        }
        setDocuments();
        initFrame(userData);
    }

    /** Sets documents. */
    private void setDocuments() {
        this.txaMessage.setDocument(new EMCStringFormDocument(drm, "message"));
    }

    private void initFrame(EMCUserData userData){
        this.setLayout(new BorderLayout());

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, createMainPanel(), createMessagePanel());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(100);

        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Messages", topBottomSplit);

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(userData), BorderLayout.EAST);
    }

    /** Creates the main tab. */
    private emcJPanel createMainPanel() {
        List keys = new ArrayList();
        keys.add("description");
        keys.add("messageModule");
        keys.add("message");

        emcTableModelUpdate m = new emcTableModelUpdate(this.drm,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        drm.setMainTableComponent(toptable);

        toptable.setComboBoxLookupToColumn(1, new EMCModuleDropDown());

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        emcJPanel pnlMain = new emcJPanel();
        pnlMain.setLayout(new GridLayout(1,1));
        pnlMain.add(topscroll);
        this.setTablePanel(topscroll);

        return pnlMain;
    }

    /** Creates the message panel.  */
    private emcJPanel createMessagePanel() {
        emcJPanel bugPanel = new emcJPanel(new BorderLayout());

        bugPanel.setBorder(BorderFactory.createTitledBorder(drm.getColumnName("message")));
        bugPanel.add(new emcJScrollPane(txaMessage));

        return bugPanel;
    }

    /** Creates buttons panel. */
    public emcJPanel createButtonsPanel(EMCUserData userData) {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new PopulateMessagesButton(drm, enumEMCModules.BASE, userData));

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}
