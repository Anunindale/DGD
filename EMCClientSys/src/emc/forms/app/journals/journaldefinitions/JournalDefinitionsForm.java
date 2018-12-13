/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.app.journals.journaldefinitions;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.inventory.journals.ContraTypesDropDown;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.gl.GLChartOfAccounts;
import emc.entity.base.journals.BaseJournalApprovalGroups;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.enums.enumQueryTypes;
import emc.enums.base.journals.Modules;
import emc.enums.inventory.journals.ContraTypes;
import emc.forms.app.journals.journaldefinitions.resources.JournalDefinitionsDRM;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : This form acts as a super class for the Journal Definition setup
 *                forms in the various EMC modules.
 *
 * @date        : 05 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public abstract class JournalDefinitionsForm extends BaseInternalFrame {

    private EMCLookupJTableComponent lkpApprovalGroups;
    private EMCLookupJTableComponent lkpContraAccount;
    private EMCLookupJTableComponent lkpAccessGroup;
    private emcJComboBox cmbContraType;
    private emcYesNoComponent ysnVATApplicable;
    private EMCFormComboBox movementDirections;
    protected JournalDefinitionsDRM dataRelation;

    /** Creates a new instance of JournalDefinitionsForm */
    public JournalDefinitionsForm(EMCUserData userData, Modules module) {
        super("Journal Definitions", true, true, true, true, userData);
        this.setBounds(10, 20, 800, 290);
        try {
            EMCQuery query;
            //If query is passed to this form, don't discard it.
            if (userData.getUserData(0) instanceof EMCQuery) {
                query = (EMCQuery) userData.getUserData(0);
            } else {
                query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
                userData.setUserData(0, query);
            }

            query.addAnd("journalModule", module.toString());

            dataRelation = new JournalDefinitionsDRM(module, userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("journalDefinitionId");
            dataRelation.setFormTextId2("journalDescription");

            setupComponents(module, userData);

            initFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupComponents(Modules module, EMCUserData userData) {
        lkpContraAccount = new EMCLookupJTableComponent(new emc.menus.gl.menuitems.display.GLChartOfAccountsMenu());
        EMCLookupPopup contraAccountPopup = new EMCLookupPopup(new GLChartOfAccounts(), "accountNumber", userData);
        lkpContraAccount.setPopup(contraAccountPopup);

        lkpApprovalGroups = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.JournalApprovalGroups());
        EMCLookupPopup approvalGroupsPopup = new EMCLookupPopup(new BaseJournalApprovalGroups(), "journalApprovalGroupId", userData);
        lkpApprovalGroups.setPopup(approvalGroupsPopup);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalApprovalGroups.class);
        query.addAnd("groupModule", module.toString());

        lkpApprovalGroups.setTheQuery(query);

        lkpAccessGroup = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.JournalAccessGroupsMI());
        EMCLookupPopup accessGroupsPopup = new EMCLookupPopup(new BaseJournalAccessGroups(), "accessGroupId", userData);
        lkpAccessGroup.setPopup(accessGroupsPopup);

        query = new EMCQuery(enumQueryTypes.SELECT, BaseJournalAccessGroups.class);
        query.addAnd("groupModule", module.toString());

        lkpAccessGroup.setTheQuery(query);

        movementDirections = new EMCFormComboBox(getJournalDirections(), dataRelation, "movementDirection");

        cmbContraType = new emcJComboBox(ContraTypes.values());

        ysnVATApplicable = new emcYesNoComponent(dataRelation, "vatApplicable");
    }

    private emcJPanel tabJournalDefinitions() {
        emcJPanel pnlJournalDefinitions = new emcJPanel(new GridLayout(1, 1));
        List keys = new ArrayList();
        keys.add("journalDefinitionId");
        keys.add("journalDescription");
        keys.add("journalType");
        keys.add("journalContraType");
        keys.add("journalContraAccount");
        keys.add("journalContraFixed");
        keys.add("journalEntryControl");
        keys.add("journalApprovalRequired");
        keys.add("approvalBy");
        keys.add("journalAccessGroup");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);

        //Add ComboBox
        toptable.setComboBoxLookupToColumn("journalType", new emcJComboBox(getJournalTypes()));
        toptable.setComboBoxLookupToColumn(4, new ContraTypesDropDown());
        //Add lookup
        toptable.setLookupToColumn("journalContraAccount", lkpContraAccount);
        toptable.setLookupToColumn("approvalBy", lkpApprovalGroups);
        toptable.setLookupToColumn("journalAccessGroup", lkpAccessGroup);
        toptable.setComboBoxLookupToColumn("journalContraType", cmbContraType);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        dataRelation.setTablePanel(topscroll);

        pnlJournalDefinitions.add(topscroll);

        return pnlJournalDefinitions;
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();

        tabbedPanetop.add("Journal Definitions", tabJournalDefinitions());
        tabbedPanetop.add("Movement", createMovementPanel());
        tabbedPanetop.add("VAT", createVATPanel());

        this.add(tabbedPanetop);
    }

    /** Creates movement panel.  */
    private emcJPanel createMovementPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel(dataRelation.getColumnName("movementDirection")), movementDirections}
        };
        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Movement");
    }

    /** Creates VAT panel. */
    private emcJPanel createVATPanel() {
        Component[][] components = new Component[][]{
            {new emcJLabel(dataRelation.getColumnName("vatApplicable")), ysnVATApplicable}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "VAT");
    }

    /** Returns valid directions for journals.  Module specific implementation. */
    protected abstract String[] getJournalDirections();

    /** Returns valid journal types for journals. Module specific implementation. */
    protected abstract String[] getJournalTypes();
}
