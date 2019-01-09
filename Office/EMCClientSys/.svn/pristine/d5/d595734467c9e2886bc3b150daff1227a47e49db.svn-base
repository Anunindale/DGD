/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.parameters;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.Usertable;
import emc.entity.developertools.DevParameters;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.users;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

/**
 *
 * @author wikus
 */
public class DevParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public DevParametersForm(EMCUserData userData) {
        super("Developer Tools Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 300);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(),
                    new DevParameters(), userData), userData);
            this.setDataManager(dataRelation);
        } catch (Exception e) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Parameters", parametersPane());
        thePanel.add(tabbed);
        this.add(thePanel);
    }

    private emcJPanel parametersPane() {
        emcJTextField txtRecipients1 = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugMailAddress1"));
        emcJTextField txtRecipients2 = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugMailAddress2"));
        emcJTextField txtRecipients3 = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugMailAddress3"));
        emcJTextField txtRecipients4 = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugMailAddress4"));
        emcJTextField txtRecipients5 = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugMailAddress5"));
        emcJTextField txtFrom = new emcJTextField(new EMCStringFormDocument(dataRelation, "completedBugFromMailAddress"));

        EMCLookupFormComponent lkpUser = new EMCLookupFormComponent(new users(), dataRelation, "generalUser");
        lkpUser.setPopup(new EMCLookupPopup(new Usertable(), "userId", dataRelation.getUserData()));

        Component[][] comp = {{new emcJLabel("Completed bug recipients 1"), txtRecipients1},
            {new emcJLabel("Completed bug recipients 2"), txtRecipients2},
            {new emcJLabel("Completed bug recipients 3"), txtRecipients3},
            {new emcJLabel("Completed bug recipients 4"), txtRecipients4},
            {new emcJLabel("Completed bug recipients 5"), txtRecipients5},
            {new emcJLabel("Completed bug from"), txtFrom},
            {new emcJLabel()},
            {new emcJLabel("General User"), lkpUser}};
        emcJPanel thePanel = emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        return thePanel;
    }
}
