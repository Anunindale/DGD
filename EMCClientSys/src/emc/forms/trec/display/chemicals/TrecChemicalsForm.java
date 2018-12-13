/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.chemicals;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
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
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECErgMaster;
import emc.entity.trec.TRECPhrases;
import emc.entity.trec.TRECPreferredShipName;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.trec.display.chemicals.resources.TRECChemicalsEditPhraseDialog;
import emc.forms.trec.display.chemicals.resources.TRECEncDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECClassesMenu;
import emc.menus.developertools.trec.TRECPhrasesMenu;
import emc.menus.trec.menuitems.display.TRECErgMenu;
import emc.menus.trec.menuitems.display.TRECLoadCompMenu;
import emc.menus.trec.menuitems.display.TRECPrefShipNameMI;
import emc.methods.trec.ServerTRECMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class TrecChemicalsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TrecChemicalsForm(EMCUserData userData) {
        super("Chemicals", true, true, true, true, userData);
        this.setBounds(20, 20, 990, 400);
        dataManager = new TRECEncDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECChemicals(), userData), ServerTRECMethods.UPDATE_ENC_CHEMICALS, userData) {
            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData generatedUD = super.generateRelatedFormUserData(formUserData, Index);

                switch (Index) {
                    case 1: {
                        String phrases = (String) getLastFieldValueAt("hPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 2: {
                        String phrases = (String) getLastFieldValueAt("pPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 3: {
                        String phrases = (String) getLastFieldValueAt("qPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 4: {
                        String phrases = (String) getLastFieldValueAt("dPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 5: {
                        String phrases = (String) getLastFieldValueAt("sPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 6: {
                        String phrases = (String) getLastFieldValueAt("fPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 7: {
                        String phrases = (String) getLastFieldValueAt("aPhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }
                    case 8: {
                        String phrases = (String) getLastFieldValueAt("ePhrases");

                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);

                        if (!Functions.checkBlank(phrases)) {
                            String[] phrasesSplit = phrases.split(";");

                            query.openAndConditionBracket();

                            for (String p : phrasesSplit) {
                                query.addOr("phraseId", p.trim());
                            }

                            query.closeConditionBracket();
                        }

                        generatedUD.setUserData(0, query);
                        break;
                    }

                    case 9: {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPreferredShipName.class);
                        query.addAnd("chemicalLink", dataManager.getLastFieldValueAt("recordID"));
                        List udList = new ArrayList();
                        udList.add(query);
                        udList.add("");
                        udList.add(dataManager.getLastFieldValueAt("shippingName"));
                        udList.add(dataManager.getLastFieldValueAt("unNumber"));
                        udList.add(dataManager.getLastFieldValueAt("recordID"));
                        generatedUD.setUserData(udList);
                        break;
                    }
                }

                return generatedUD;
            }
        };
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("unNumber");
        dataManager.setFormTextId2("chemicalId");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Forms", tablePane());
        tabbedPane.add("Phrases", phrasesPane1());
        tabbedPane.add("Phrases", phrasesPane2());

        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("unNumber");
//        keys.add("chemicalId");
        keys.add("erg");
//        keys.add("risk1");
//        keys.add("risk2");
//        keys.add("risk3");
        keys.add("classId");
        keys.add("shippingName");
        keys.add("addShippingName");
        keys.add("subsidairyRisk");
        keys.add("packingGroup");
        keys.add("ready");

        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpErg = new EMCLookupJTableComponent(new TRECErgMenu());
        lkpErg.setPopup(new EMCLookupPopup(new TRECErgMaster(), "erg", getUserData()));
        table.setLookupToColumn("erg", lkpErg);

        EMCLookupJTableComponent lkpClass = new EMCLookupJTableComponent(new TRECClassesMenu());
        lkpClass.setPopup(new EMCLookupPopup(new TRECClasses(), "classId", getUserData()));
        table.setLookupToColumn("classId", lkpClass);

        EMCLookupJTableComponent lkpSubRisk = new EMCLookupJTableComponent(new TRECClassesMenu());
        lkpSubRisk.setPopup(new EMCLookupPopup(new TRECClasses(), "subsidairyRisk", getUserData()));
        table.setLookupToColumn("subsidairyRisk", lkpSubRisk);

        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel phrasesPane1() {
        emcJTextArea txthPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "hPhrases"));
        emcJTextArea txtpPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "pPhrases"));
        emcJTextArea txtqPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "qPhrases"));
        emcJTextArea txtdPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "dPhrases"));

        Component[][] comp = {{txthPhrases, new emcJLabel("(H) Hazard Pharases")},
            {txtpPhrases, new emcJLabel("(P) PPE Phrases")},
            {txtqPhrases, new emcJLabel("(Q) Driver Equip Phrases")},
            {txtdPhrases, new emcJLabel("(D) First Action Phrases")}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Phrases");
    }

    private emcJPanel phrasesPane2() {
        emcJTextArea txtsPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "sPhrases"));
        emcJTextArea txtfPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "fPhrases"));
        emcJTextArea txtaPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "aPhrases"));
        emcJTextArea txtePhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "ePhrases"));

        Component[][] comp = {{txtsPhrases, new emcJLabel("(S) Special Action Phrases")},
            {txtfPhrases, new emcJLabel("(F) Fire Phrases")},
            {txtaPhrases, new emcJLabel("(A) First Aid Phrases")},
            {txtePhrases, new emcJLabel("(E) Special Info ES Phrases")}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Phrases");
    }

    private emcJPanel buttonPane() {
        emcJButton btnFix = new emcJButton("Fix Phrases") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.FIX_CHEMICALS);

                List toSend = new ArrayList();

                EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());

                dataManager.refreshDataIgnoreFocus();

                Logger.getLogger("emc").log(Level.INFO, "Chemical Phrases Fixed.", dataManager.getUserData());
            }
        };

        emcJButton btnRestructure = new emcJButton("Restructure") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                int dialogREsult = EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Restructure Chemicals", "Are you sure you want to restructure the chemicals table?");

                if (dialogREsult == JOptionPane.YES_OPTION) {

                    EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.RESTRUCTURE_CHEMICALS);

                    List toSend = new ArrayList();

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, dataManager.getUserData());

                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        dataManager.refreshDataIgnoreFocus();

                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed To Restructure the Chemicals Table.", dataManager.getUserData());
                    }
                }
            }
        };

        emcMenuButton btnPhrasesH = new emcMenuButton("(H) Hazard", new TRECPhrasesMenu(), this, 1, false);
        emcMenuButton btnPhrasesP = new emcMenuButton("(P) PPE", new TRECPhrasesMenu(), this, 2, false);
        emcMenuButton btnPhrasesQ = new emcMenuButton("(Q) Driver Equip", new TRECPhrasesMenu(), this, 3, false);
        emcMenuButton btnPhrasesD = new emcMenuButton("(D) First Action", new TRECPhrasesMenu(), this, 4, false);
        emcMenuButton btnPhrasesS = new emcMenuButton("(S)Special Action", new TRECPhrasesMenu(), this, 5, false);
        emcMenuButton btnPhrasesF = new emcMenuButton("(F) Fire", new TRECPhrasesMenu(), this, 6, false);
        emcMenuButton btnPhrasesA = new emcMenuButton("(A) First Aid", new TRECPhrasesMenu(), this, 7, false);
        emcMenuButton btnPhrasesE = new emcMenuButton("(E) Special Info ES", new TRECPhrasesMenu(), this, 8, false);

        emcJButton btnEdit = new emcJButton("Edit Phrases") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                TRECChemicalsEditPhraseDialog dialog = new TRECChemicalsEditPhraseDialog(utilFunctions.findEMCDesktop(this), dataManager);

                if (dialog.isRefresh()) {
                    dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
                }
            }
        };

        emcMenuButton btnPrefShipName = new emcMenuButton("Pref. Ship Name", new TRECPrefShipNameMI(), this, 9, false);


        List<emcJButton> buttonList = new ArrayList<>();
//        if (getUserData().getUserName().equalsIgnoreCase("EMC")) {
//            buttonList.add(btnFix);
//        }
//        buttonList.add(btnRestructure);
        buttonList.add(btnEdit);
        buttonList.add(btnPhrasesH);
        buttonList.add(btnPhrasesP);
        buttonList.add(btnPhrasesQ);
        buttonList.add(btnPhrasesD);
        buttonList.add(btnPhrasesS);
        buttonList.add(btnPhrasesF);
        buttonList.add(btnPhrasesA);
        buttonList.add(btnPhrasesE);
        buttonList.add(btnPrefShipName);

        if ("emc".equals(getUserData().getUserName())) {
            buttonList.add(new emcJButton("Check Data") {
                @Override
                public void doActionPerformed(ActionEvent evt) {
                    super.doActionPerformed(evt);

                    EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.CHECK_TREC_CHEMICALS);

                    EMCWSManager.executeGenericWS(cmd, new ArrayList<>(), getUserData());
                }
            });
        }

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
