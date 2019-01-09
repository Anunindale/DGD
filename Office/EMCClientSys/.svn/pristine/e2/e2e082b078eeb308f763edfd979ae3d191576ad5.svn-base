/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.customerchemicals.resources;

import emc.forms.trec.display.chemicals.resources.*;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.entity.trec.TRECPhrases;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECPhrasesMenu;
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
public class TRECCustomerChemicalsEditPhraseDialog extends emcJDialog {
    
    private emcDataRelationManagerUpdate dataManager;
    //
    private List<String> hPhrases;
    private List<String> pPhrases;
    private List<String> qPhrases;
    private List<String> dPhrases;
    private List<String> sPhrases;
    private List<String> fPhrases;
    private List<String> aPhrases;
    private List<String> ePhrases;
    //
    private emcJComboBox cbType;
    private emcJComboBox cbRemove;
    private EMCLookup lkpAdd;
    //
    private boolean refresh;
    
    public TRECCustomerChemicalsEditPhraseDialog(EMCDesktop owner, emcDataRelationManagerUpdate dataManager) {
        super(owner, "Edit Phrases", true);
        
        this.dataManager = dataManager;

        //H
        String phraseString = (String) dataManager.getLastFieldValueAt("hPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        String[] phraseSplit = phraseString.split(";");
        
        hPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                hPhrases.add(phrase.trim());
            }
        }
        //P
        phraseString = (String) dataManager.getLastFieldValueAt("pPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        pPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                pPhrases.add(phrase.trim());
            }
        }
        //Q
        phraseString = (String) dataManager.getLastFieldValueAt("qPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        qPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                qPhrases.add(phrase.trim());
            }
        }
        //D
        phraseString = (String) dataManager.getLastFieldValueAt("dPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        dPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                dPhrases.add(phrase.trim());
            }
        }
        //S
        phraseString = (String) dataManager.getLastFieldValueAt("sPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        sPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                sPhrases.add(phrase.trim());
            }
        }
        //F
        phraseString = (String) dataManager.getLastFieldValueAt("fPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        fPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                fPhrases.add(phrase.trim());
            }
        }
        //A
        phraseString = (String) dataManager.getLastFieldValueAt("aPhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        aPhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                aPhrases.add(phrase.trim());
            }
        }
        //E
        phraseString = (String) dataManager.getLastFieldValueAt("ePhrases");
        if (phraseString == null) {
            phraseString = "";
        }
        
        phraseSplit = phraseString.split(";");
        
        ePhrases = new ArrayList<>();
        
        for (String phrase : phraseSplit) {
            if (!Functions.checkBlank(phrase)) {
                ePhrases.add(phrase.trim());
            }
        }
        
        initDialog();
        
        pack();
        
        setVisible(true);
    }
    
    private void initDialog() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Edit Phrases", editPane());
        
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        
        this.setContentPane(contentPane);
    }
    
    private Component editPane() {
        cbRemove = new emcJComboBox();
        cbRemove.setSelectedIndex(0);
        
        List<String> lkpFields = new ArrayList<>();
        lkpFields.add("phraseId");
        lkpFields.add("typeId");
        lkpFields.add("phrase");
        
        lkpAdd = new EMCLookup(new TRECPhrasesMenu());
        lkpAdd.setPopup(new EMCLookupPopup(new TRECPhrases(), "phraseId", lkpFields, dataManager.getUserData()));
        
        cbType = new emcJComboBox(new String[]{"H", "P", "Q", "D", "S", "F", "A", "E"}) {
            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                
                cbRemove.removeAllItems();
                cbRemove.addItem("");
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
                
                
                if (!Functions.checkBlank(anObject)) {
                    if (anObject.toString().equals("H")) {
                        for (String phrase : hPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "H");
                    } else if (anObject.toString().equals("P")) {
                        for (String phrase : pPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "P");
                    } else if (anObject.toString().equals("Q")) {
                        for (String phrase : qPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "Q");
                    } else if (anObject.toString().equals("D")) {
                        for (String phrase : dPhrases) {
                            cbRemove.addItem(phrase);
                        }
                    } else if (anObject.toString().equals("D")) {
                        for (String phrase : sPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "S");
                    } else if (anObject.toString().equals("F")) {
                        for (String phrase : fPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "F");
                    } else if (anObject.toString().equals("A")) {
                        for (String phrase : aPhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "A");
                    } else if (anObject.toString().equals("E")) {
                        for (String phrase : ePhrases) {
                            cbRemove.addItem(phrase);
                        }
                        query.addAnd("typeId", "E");
                    }
                    
                    cbRemove.setSelectedIndex(0);
                    lkpAdd.setTheQuery(query);
                }
            }
        };
        cbType.setSelectedIndex(0);
        
        Component[][] comp = {{new emcJLabel("Phrase Type"), cbType},
            {new emcJLabel("Add Phrase"), lkpAdd},
            {new emcJLabel("Remove Phrase"), cbRemove}};
        
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
        
    }
    
    private Component buttonPane() {
        emcJButton btnOK = new emcJButton("Update") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                if (Functions.checkBlank(cbType.getSelectedItem())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please select the phrase type.", dataManager.getUserData());
                    return;
                }
                
                String type = cbType.getSelectedItem().toString();
                
                if (Functions.checkBlank(lkpAdd.getValue()) && Functions.checkBlank(cbRemove.getSelectedItem())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "There are no updates to be done.", dataManager.getUserData());
                    return;
                }
                
                List<String> phrasesList = null;
                String phraseField = null;
                
                if (type.toString().equals("H")) {
                    phrasesList = hPhrases;
                    phraseField = "hPhrases";
                } else if (type.toString().equals("P")) {
                    phrasesList = pPhrases;
                    phraseField = "pPhrases";
                } else if (type.toString().equals("Q")) {
                    phrasesList = qPhrases;
                    phraseField = "qPhrases";
                } else if (type.toString().equals("D")) {
                    phrasesList = dPhrases;
                    phraseField = "dPhrases";
                } else if (type.toString().equals("S")) {
                    phrasesList = sPhrases;
                    phraseField = "sPhrases";
                } else if (type.toString().equals("F")) {
                    phrasesList = fPhrases;
                    phraseField = "fPhrases";
                } else if (type.toString().equals("A")) {
                    phrasesList = aPhrases;
                    phraseField = "aPhrases";
                } else if (type.toString().equals("E")) {
                    phrasesList = ePhrases;
                    phraseField = "ePhrases";
                }
                
                if (!Functions.checkBlank(lkpAdd.getValue())) {
                    phrasesList.add(lkpAdd.getValue().toString());
                }
                
                if (!Functions.checkBlank(cbRemove.getSelectedItem())) {
                    phrasesList.remove((String) cbRemove.getSelectedItem());
                }
                
                Collections.sort(phrasesList);
                
                StringBuilder phraseValue = new StringBuilder();
                
                for (String phrase : phrasesList) {
                    if (phraseValue.length() > 0) {
                        phraseValue.append("; ");
                    }
                    
                    phraseValue.append(phrase);
                }
                
                dataManager.setFieldValueAt(dataManager.getLastRowAccessed(), phraseField, phraseValue.toString());
                dataManager.updatePersist(dataManager.getLastRowAccessed());
                
                if (dataManager.getLastUpdateStatus()) {
                    Logger.getLogger("emc").log(Level.INFO, "Chemical Phrases Updated", dataManager.getUserData());
                    
                    refresh = true;
                    
                    TRECCustomerChemicalsEditPhraseDialog.this.dispose();
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to update Chemical Phrases.", dataManager.getUserData());
                }
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                
                refresh = false;
                
                TRECCustomerChemicalsEditPhraseDialog.this.dispose();
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
