/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.emcJButton;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.trec.TRECCustomerChemicals;
import emc.forms.trec.display.treccards.TRECEditTRECForm;
import emc.forms.trec.display.treccards.resources.TRECEditEDialog;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.trec.TRECPhraseHelper;
import emc.methods.trec.ServerTRECMethods;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asd_admin
 */
public class TRECEditEButton extends emcJButton {

    private TRECEditTRECForm editForm;
    private TRECCustomerChemicals customerChemical;
    private EMCUserData userData;

    public TRECEditEButton(String title, TRECEditTRECForm editForm, EMCUserData userData) {
        super(title);

        this.editForm = editForm;
        this.userData = userData.copyUserData();
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        customerChemical = editForm.getCustomerChemical();

        Field f;

        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.FETCH_E_PHRASES_FOR_CHEMICAL_NEW);

        List toSend = new ArrayList();
        toSend.add(customerChemical);

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        Map<String, List<TRECPhraseHelper>> phraseMap = null;
        if (toSend.size() > 1 && toSend.get(1) instanceof Map) {
            phraseMap = (Map<String, List<TRECPhraseHelper>>) toSend.get(1);
        }

        if (phraseMap == null || phraseMap.isEmpty()) {
            Logger.getLogger("emc").log(Level.INFO, "There are no phrases of the selected type for the chemical.", userData);
            return;
        }

        List<String> activePhrases = new ArrayList<>();

        String activePhraseString = customerChemical.getePhrases();

        if (!Functions.checkBlank(activePhraseString)) {
            String[] activeArray = activePhraseString.split(";");

            for (String active : activeArray) {
                activePhrases.add(active.trim());
            }
        }

        TRECEditEDialog dialog = new TRECEditEDialog(utilFunctions.findEMCDesktop(this), getText(), phraseMap, activePhrases, userData);

        List<String> selectedPhrases = dialog.getSelectedPhrases();

        if (selectedPhrases != null) {
            StringBuilder phraseBuilder = new StringBuilder();

            for (String phrase : selectedPhrases) {
                if (phraseBuilder.length() > 0) {
                    phraseBuilder.append("; ");
                }

                phraseBuilder.append(phrase.trim());
            }
            customerChemical.setePhrases(phraseBuilder.toString());

            cmd = new EMCCommandClass(ServerTRECMethods.UPDATE_TRECCUSTOMERCHEMICALS);
            toSend = new ArrayList();
            toSend.add(customerChemical);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            cmd = new EMCCommandClass(ServerTRECMethods.ENCRYPT_CUSTOMER_CHEMICALS);

            toSend = new ArrayList();
            toSend.add(customerChemical.getRecordID());

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            toSend = new ArrayList();
            toSend.add(customerChemical.getRecordID());

            cmd = new EMCCommandClass(ServerTRECMethods.FETCH_CUSTOMER_CHEMICALS);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

            customerChemical = (TRECCustomerChemicals) toSend.get(1);

            editForm.setCustomerChemical(customerChemical);

            editForm.refreshTRECLine();

            editForm.printTREC();
        }
    }
}
