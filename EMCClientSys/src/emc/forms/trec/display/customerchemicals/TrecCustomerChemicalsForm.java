/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.customerchemicals;

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
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECErgMaster;
import emc.entity.trec.TRECPhrases;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.trec.display.customerchemicals.resources.TRECCustomerChemicalsEditPhraseDialog;
import emc.forms.trec.display.customerchemicals.resources.TRECEncDRM;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECClassesMenu;
import emc.menus.developertools.trec.TRECPhrasesMenu;
import emc.menus.trec.menuitems.display.TRECErgMenu;
import emc.methods.trec.ServerTRECMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asd_admin
 */
public class TrecCustomerChemicalsForm  extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TrecCustomerChemicalsForm(EMCUserData userData) {
        super("Customer Chemicals", true, true, true, true, userData);
        this.setBounds(20, 20, 900, 400);
        dataManager = new TRECEncDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECCustomerChemicals(), userData), ServerTRECMethods.UPDATE_ENC_CUSTOMER_CHEMICALS, userData) {
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
        keys.add("customerId");
        keys.add("unNumber");
        keys.add("chemicalId");
        keys.add("erg");
        keys.add("risk1");
        keys.add("risk2");
        keys.add("risk3");
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
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel phrasesPane1() {
        emcJTextArea txthPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "hPhrases"));
        emcJTextArea txtpPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "pPhrases"));
        emcJTextArea txtqPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "qPhrases"));
        emcJTextArea txtdPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "dPhrases"));

        Component[][] comp = {{txthPhrases, new emcJLabel("H Pharases")},
            {txtpPhrases, new emcJLabel("P Phrases")},
            {txtqPhrases, new emcJLabel("Q Phrases")},
            {txtdPhrases, new emcJLabel("D Phrases")}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Phrases");
    }

    private emcJPanel phrasesPane2() {
        emcJTextArea txtsPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "sPhrases"));
        emcJTextArea txtfPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "fPhrases"));
        emcJTextArea txtaPhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "aPhrases"));
        emcJTextArea txtePhrases = new emcJTextArea(new EMCStringFormDocument(dataManager, "ePhrases"));

        Component[][] comp = {{txtsPhrases, new emcJLabel("S Phrases")},
            {txtfPhrases, new emcJLabel("F Phrases")},
            {txtaPhrases, new emcJLabel("A Phrases")},
            {txtePhrases, new emcJLabel("E Phrases")}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Phrases");
    }

    private emcJPanel buttonPane() {
        emcJButton btnEdit = new emcJButton("Edit") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                TRECCustomerChemicalsEditPhraseDialog dialog = new TRECCustomerChemicalsEditPhraseDialog(utilFunctions.findEMCDesktop(this), dataManager);

                if (dialog.isRefresh()) {
                    dataManager.refreshRecordIgnoreFocus(dataManager.getLastRowAccessed());
                }
            }
        };
        
        emcMenuButton btnPhrasesH = new emcMenuButton("H Phrases", new TRECPhrasesMenu(), this, 1, false);
        emcMenuButton btnPhrasesP = new emcMenuButton("P Phrases", new TRECPhrasesMenu(), this, 2, false);
        emcMenuButton btnPhrasesQ = new emcMenuButton("Q Phrases", new TRECPhrasesMenu(), this, 3, false);
        emcMenuButton btnPhrasesD = new emcMenuButton("D Phrases", new TRECPhrasesMenu(), this, 4, false);
        emcMenuButton btnPhrasesS = new emcMenuButton("S Phrases", new TRECPhrasesMenu(), this, 5, false);
        emcMenuButton btnPhrasesF = new emcMenuButton("F Phrases", new TRECPhrasesMenu(), this, 6, false);
        emcMenuButton btnPhrasesA = new emcMenuButton("A Phrases", new TRECPhrasesMenu(), this, 7, false);
        emcMenuButton btnPhrasesE = new emcMenuButton("E Phrases", new TRECPhrasesMenu(), this, 8, false);

        List<emcJButton> buttonList = new ArrayList<>();
        buttonList.add(btnEdit);
        buttonList.add(btnPhrasesH);
        buttonList.add(btnPhrasesP);
        buttonList.add(btnPhrasesQ);
        buttonList.add(btnPhrasesD);
        buttonList.add(btnPhrasesS);
        buttonList.add(btnPhrasesF);
        buttonList.add(btnPhrasesA);
        buttonList.add(btnPhrasesE);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
    
}
