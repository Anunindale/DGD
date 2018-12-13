/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.trecPhrases.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECErgMaster;
import emc.entity.trec.TRECPhrases;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import emc.menus.developertools.trec.TRECPhrasesMenu;
import emc.menus.trec.menuitems.display.TRECErgMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asd_admin
 */
public class TRECPhrasesEditERGDialog extends emcJDialog {

    private emcDataRelationManagerUpdate dataManager;
    //
    private List<String> ergValues;
    //
    private emcJComboBox cbRemove;
    private EMCLookup lkpAdd;
    //
    private boolean refresh;

    public TRECPhrasesEditERGDialog(EMCDesktop owner, emcDataRelationManagerUpdate dataManager) {
        super(owner, "Edit ERG", true);

        this.dataManager = dataManager;

        String ergString = (String) dataManager.getLastFieldValueAt("ergNumber");

        if (ergString == null) {
            ergString = "";
        }

        String[] ergSplit = ergString.split(";");

        ergValues = new ArrayList<>();

        for (String erg : ergSplit) {
            if (!Functions.checkBlank(erg) && !ergValues.contains(erg.trim())) {
                ergValues.add(erg.trim());
            }
        }

        initDialog();

        pack();

        setVisible(true);
    }

    private void initDialog() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Edit ERG", editPane());

        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private Component editPane() {


        cbRemove = new emcJComboBox(ergValues.toArray());
        cbRemove.setSelectedIndex(0);

        List<String> lkpFields = new ArrayList<>();
        lkpFields.add("erg");
        
        lkpAdd = new EMCLookupJTableComponent(new TRECErgMenu());
        lkpAdd.setPopup(new EMCLookupPopup(new TRECErgMaster(), "erg", dataManager.getUserData()));
//
//        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECErgMaster.class);
//        query.addGroupBy("erg");
//        query.addOrderBy("erg");
//        
//        lkpAdd.setTheQuery(query);
        
        Component[][] comp = {{new emcJLabel("Add ERG"), lkpAdd},
            {new emcJLabel("Remove ERG"), cbRemove}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);

    }

    private Component buttonPane() {
        emcJButton btnOK = new emcJButton("Update") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                if (Functions.checkBlank(lkpAdd.getValue()) && Functions.checkBlank(cbRemove.getSelectedItem())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "There are no updates to be done.", dataManager.getUserData());
                    return;
                }

                if (!Functions.checkBlank(lkpAdd.getValue()) && !ergValues.contains((String) lkpAdd.getValue())) {
                    ergValues.add((String) lkpAdd.getValue());
                }

                if (!Functions.checkBlank(cbRemove.getSelectedItem())) {
                    ergValues.remove((String) cbRemove.getSelectedItem());
                }

                StringBuilder ergValue = new StringBuilder();

                Collections.sort(ergValues);
                
                for (String erg : ergValues) {
                    if (ergValue.length() > 0) {
                        ergValue.append("; ");
                    }

                    ergValue.append(erg);
                }

                dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), "ergNumber", ergValue.toString());
                dataManager.updatePersist(dataManager.getLastRowAccessed());

                if (dataManager.getLastUpdateStatus()) {
                    Logger.getLogger("emc").log(Level.INFO, "Phrase ERG Numbers Updated", dataManager.getUserData());

                    refresh = true;

                    TRECPhrasesEditERGDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to update Phrase ERG Numbers.", dataManager.getUserData());
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                refresh = false;

                TRECPhrasesEditERGDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public boolean isRefresh() {
        return refresh;
    }
}
