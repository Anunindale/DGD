/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJCheckBox;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.trec.TRECParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.trec.TRECPhraseHelper;
import emc.methods.trec.ServerTRECMethods;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author asd_admin
 */
public class TrecCheckBox extends emcJCheckBox {

    private TRECPhraseHelper helper;
    private EMCUserData userData;
    private TRECEditDialogInterface editDialog;
    private TRECParameters params;

    public TrecCheckBox(TRECPhraseHelper helper, TRECEditDialogInterface editDialog, EMCUserData userData) {
        super(helper.getPhraseDescription(), helper.isTicked());
        final EMCUserData copyUserData = userData.copyUserData();
        formatLabel();

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check Mandatory phrases
                if (TrecCheckBox.this.helper != null && TrecCheckBox.this.helper.isMandatory() && !isSelected()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The pharase is a required phrase for the selected chemical. It may not be removed.", TrecCheckBox.this.userData);
                    EMCDialogFactory.createMessageDialog(utilFunctions.findEMCDesktop(TrecCheckBox.this), "Phrase Mandatory", "The pharase is a required phrase for the selected chemical. It may not be removed.");
                    setSelected(true);
                }

                //Check 5 phrases
                if (isSelected() && TrecCheckBox.this.editDialog != null && !TrecCheckBox.this.editDialog.getSelectionCompList().isEmpty()) {
                    int count = 0;

                    for (TrecCheckBox c : TrecCheckBox.this.editDialog.getSelectionCompList()) {
                        if (c.isSelected()) {
                            count++;
                        }
                        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.GET_MAXPHRASES);

                        List toSend = new ArrayList();
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, copyUserData);

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof TRECParameters) {
                            params = (TRECParameters) toSend.get(1);
                            if(Functions.checkBlank(params) || Functions.checkBlank(params.getMaxPhrases()) ||!Functions.checkBlank(params.getMaxPhrases()) && params.getMaxPhrases()<1){
                               Logger.getLogger("emc").log(Level.SEVERE, "Please set up Max Phrases on  TREC Parameters.", copyUserData);  
                               break;
                            }
                            
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Please set up TREC Parameters.", copyUserData);
                        }


                        if (count > params.getMaxPhrases()) {
                            break;
                        }
                    }



                    if (count > params.getMaxPhrases()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Warning: It is recommended to have no more than "+ params.getMaxPhrases()+" most relevant phrases per section.", TrecCheckBox.this.userData);
                        EMCDialogFactory.createMessageDialog(utilFunctions.findEMCDesktop(TrecCheckBox.this), "Warning", "It is recommended to have no more than  "+ params.getMaxPhrases()+" most relevant phrases per section.");
                    }
                }
            }
        });

        this.helper = helper;
        this.userData = userData;
        this.editDialog = editDialog;
    }

    private void formatLabel() {
        int length = 100;
        String label = getText();

        if (label.length() > length) {
            String newLabel = "";

            while (label.length() > length) {
                int tempLength = length;

                while (label.charAt(tempLength) != ' ') {
                    tempLength--;
                }

                newLabel += label.substring(0, tempLength);
                newLabel += "<br />";

                label = label.substring(tempLength);
            }

            if (label.length() != 0) {
                newLabel += label;
            }

            setText("<html>" + newLabel + "</html>");
        }
    }

    @Override
    protected void fireStateChanged() {
        super.fireStateChanged();


    }

    public TRECPhraseHelper getHelper() {
        return helper;
    }
}
