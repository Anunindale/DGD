/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.adminusers;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.adminusers.resources.KillAllButton;
import emc.forms.base.display.adminusers.resources.KillUserButton;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class AdminUsersForm extends BaseInternalFrame {

    private emcJPanel pnlUsers;
    private emcJPanel pnlButtons;
    private AminUsersDRM dataRelation;
    private EMCUserData copyUD;

    /** Creates a new instance of AdminUsersForm */
    public AdminUsersForm(EMCUserData userData) {
        //Calls the super class constructor
        super("Logged in Users", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 300);

        //Sets up the data relation manager for this form
        dataRelation = new AminUsersDRM(new emcGenericDataSourceUpdate(
                enumEMCModules.BASE.getId(), new emc.entity.base.datasource.EMCSession(), userData), userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        
        this.setHelpFile(new emcHelpFile("Base/AdminActiveUsers.html"));
        //Creates a copy of the given user data
        copyUD = userData.copyUserData();
        copyUD.setUserData(null);

        dataRelation.setFormTextId1("sessionNumber");
        dataRelation.setFormTextId2("clientName");
        
        //Initializes the frame
        initFrame(); 
    }

    private void initFrame() {
        //Tabbed pane used on the form
        emcJTabbedPane tabbedPaneTop = new emcJTabbedPane();

        
        //Sets up the tabs on the form
        tabUsers();
        initButtons();

        //Adds the tabs to the tabbed pane
        tabbedPaneTop.add(this.pnlUsers, "Online Users");
        this.setLayout(new BorderLayout());
        this.add(tabbedPaneTop, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.EAST);
        
    }

    private void tabUsers() {
        this.pnlUsers = new emcJPanel();

        List keys = new ArrayList();
        keys.add("sessionNumber");
        //keys.add("timeStamp");
        keys.add("clientName");
        keys.add("clientIP");
        keys.add("userName");
        keys.add("loginDateTime");
        keys.add("loginTimeOnly");

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
        
        EMCMenuItem msgItem = new emc.menus.base.menuItems.display.SendMessageToClient();
        msgItem.setDoNotOpenForm(false);
        emcMenuButton msguser = new emcMenuButton("Message to User", msgItem, this, 0, false);
        
        emcMenuButton msgAlluser = new emcMenuButton("Message All Users", new emc.menus.base.menuItems.display.SendMessageToAllClients(), this, 1, false);
       
        emcMenuButton killUser = new KillUserButton("Kill User Session", null, this, 2, false);
       
        emcMenuButton killAllUser = new KillAllButton("Kill All Sessions", null, this, 2, false);
         ArrayList<emcJButton> list = new ArrayList<emcJButton>();
        list.add(msguser);
        list.add(msgAlluser);
        list.add(killUser);
        list.add(killAllUser);
        pnlButtons = emcSetGridBagConstraints.createButtonPanel(list);
       
    }
}
