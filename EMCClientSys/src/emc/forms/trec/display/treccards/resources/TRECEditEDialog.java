/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.frame.EMCDesktop;
import emc.enums.trec.TRECTypeEnum;
import emc.framework.EMCUserData;
import emc.helpers.trec.TRECPhraseHelper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author asd_admin
 */
public class TRECEditEDialog extends emcJDialog implements TRECEditDialogInterface {

    // private Map<String, String> phrasesMap;
    private Map<String, List<TRECPhraseHelper>> phrasesMap;
    private List<TrecCheckBox> selectionCompList;
    private List selectedPhrases;
    private List<String> activePhrases;
    private EMCUserData userData;
    private boolean busy;

    public TRECEditEDialog(EMCDesktop owner, String title, Map<String, List<TRECPhraseHelper>> phrasesMap, List<String> activePhrases, EMCUserData userData) {
        super(owner, "Edit Trec " + title, true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(Double.valueOf(screenSize.getWidth()).intValue() / 2 - 400, Double.valueOf(screenSize.getHeight()).intValue() / 2 - 300, 800, 600);

        this.phrasesMap = phrasesMap;
        this.activePhrases = activePhrases;
        this.userData = userData;

        initDialog();

        setVisible(true);
    }

    private void initDialog() {

        setLayout(new BorderLayout());
        add(phrasesPane(), BorderLayout.CENTER);
        add(buttonPane(), BorderLayout.EAST);
    }

    private emcJTabbedPane phrasesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();

        this.selectionCompList = new ArrayList<>();

        for (Map.Entry<String, List<TRECPhraseHelper>> phraseMapEntry : phrasesMap.entrySet()) {
            String title = null;
            String type = phraseMapEntry.getKey();
            List<TRECPhraseHelper> phrasesList = phraseMapEntry.getValue();

            Component[][] comp = new Component[phrasesList.size()][1];

            TrecCheckBox selectionComp;
            int i = 0;

            for (TRECPhraseHelper phraseEntry : phrasesList) {
                if (!activePhrases.isEmpty() && !phraseEntry.isMandatory()) {
                    phraseEntry.setTicked(false);
                }

                if (activePhrases.contains(phraseEntry.getPhraseId())) {
                    phraseEntry.setTicked(true);
                }

                selectionComp = new TrecCheckBox(phraseEntry, this, userData);

                comp[i][0] = selectionComp;

                selectionCompList.add(selectionComp);

                i++;
            }

            switch (TRECTypeEnum.fromString(type)) {
                case A:
                    title = "(A) First Aid";
                    break;
                case D:
                    title = "(D) First Action";
                    break;
                case E:
                    title = "(E) Special Info ES";
                    break;
                case F:
                    title = "(F) Fire";
                    break;
                case H:
                    title = "(H) Hazard";
                    break;
                case P:
                    title = "(P) PPE";
                    break;
                case Q:
                    title = "(Q) Driver Equip";
                    break;
                case S:
                    title = "(S) Special Action";
                    break;
            }
            
            tabbed.add(title, new emcJScrollPane(emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true)));
        }

        return tabbed;
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                List tempList = new ArrayList<>();

                for (TrecCheckBox select : selectionCompList) {
                    if (select.isSelected()) {
                        tempList.add(select.getHelper().getPhraseId());
                    }
                }

                selectedPhrases = tempList;

                TRECEditEDialog.this.dispose();
            }
        };

        emcJButton btnCancel = new emcJButton("Cancel") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                TRECEditEDialog.this.dispose();
            }
        };

        List<emcJButton> buttonList = new ArrayList<>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public List getSelectedPhrases() {
        return selectedPhrases;
    }

    public List<TrecCheckBox> getSelectionCompList() {
        return selectionCompList;
    }

    public boolean isBusy() {
        return busy;
    }
}
