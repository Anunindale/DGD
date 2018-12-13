/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import emc.app.components.emcJButton;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.trec.TRECChemicalsSuper;
import emc.entity.trec.TRECCustomerChemicals;
import emc.enums.trec.TRECTypeEnum;
import emc.forms.trec.display.treccards.TRECEditTRECForm;
import emc.forms.trec.display.treccards.resources.TRECEditDialog;
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
public class TRECEditButton extends emcJButton {
    
    private TRECEditTRECForm editForm;
    private TRECTypeEnum type;
    private TRECCustomerChemicals customerChemical;
    private EMCUserData userData;
    
    public TRECEditButton(String title, TRECTypeEnum type, TRECEditTRECForm editForm, EMCUserData userData) {
        super(title);
        
        this.type = type;
        this.editForm = editForm;
        this.userData = userData.copyUserData();
    }
    
    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        
        customerChemical = editForm.getCustomerChemical();
        
        Field f;
        
        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.FETCH_PHRASES_FOR_CHEMICAL_NEW);
        
        List toSend = new ArrayList();
        toSend.add(customerChemical.getUnNumber());
        toSend.add(this.type.toString());
        
        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        
        List<TRECPhraseHelper> phraseList= null;
        if (toSend.size() > 1 && toSend.get(1) instanceof List) {
            phraseList =  (List<TRECPhraseHelper>) toSend.get(1);
        }
        
        if (phraseList == null || phraseList.isEmpty()) {
            Logger.getLogger("emc").log(Level.INFO, "There are no phrases of the selected type for the chemical.", userData);
            return;
        }
    
        List<String> activePhrases = new ArrayList<>();
        
        try {
            switch (type) {
                case A:
                    f = TRECChemicalsSuper.class.getDeclaredField("aPhrases");
                    break;
                case D:
                    f = TRECChemicalsSuper.class.getDeclaredField("dPhrases");
                    break;
                case E:
                    f = TRECChemicalsSuper.class.getDeclaredField("ePhrases");
                    break;
                case F:
                    f = TRECChemicalsSuper.class.getDeclaredField("fPhrases");
                    break;
                case H:
                    f = TRECChemicalsSuper.class.getDeclaredField("hPhrases");
                    break;
                case P:
                    f = TRECChemicalsSuper.class.getDeclaredField("pPhrases");
                    break;
                case Q:
                    f = TRECChemicalsSuper.class.getDeclaredField("qPhrases");
                    break;
                case S:
                    f = TRECChemicalsSuper.class.getDeclaredField("sPhrases");
                    break;
                default:
                    return;
            }
            
            f.setAccessible(true);
            String activePhraseString = (String) f.get(customerChemical);
            
            if (!Functions.checkBlank(activePhraseString)) {
                String[] activeArray = activePhraseString.split(";");
                
                for (String active : activeArray) {
                    activePhrases.add(active.trim());
                }
            }
        } catch (Exception ex) {
        }
        
        TRECEditDialog dialog = new TRECEditDialog(utilFunctions.findEMCDesktop(this), getText(), phraseList, activePhrases, userData);
        
        List<String> selectedPhrases = dialog.getSelectedPhrases();
        
        if (selectedPhrases != null) {
            StringBuilder phraseBuilder = new StringBuilder();
            
            for (String phrase : selectedPhrases) {
                if (phraseBuilder.length() > 0) {
                    phraseBuilder.append("; ");
                }
                
                phraseBuilder.append(phrase.trim());
            }
            
            try {
                switch (type) {
                    case A:
                        f = TRECChemicalsSuper.class.getDeclaredField("aPhrases");
                        break;
                    case D:
                        f = TRECChemicalsSuper.class.getDeclaredField("dPhrases");
                        break;
                    case E:
                        f = TRECChemicalsSuper.class.getDeclaredField("ePhrases");
                        break;
                    case F:
                        f = TRECChemicalsSuper.class.getDeclaredField("fPhrases");
                        break;
                    case H:
                        f = TRECChemicalsSuper.class.getDeclaredField("hPhrases");
                        break;
                    case P:
                        f = TRECChemicalsSuper.class.getDeclaredField("pPhrases");
                        break;
                    case Q:
                        f = TRECChemicalsSuper.class.getDeclaredField("qPhrases");
                        break;
                    case S:
                        f = TRECChemicalsSuper.class.getDeclaredField("sPhrases");
                        break;
                    default:
                        return;
                }
                
                f.setAccessible(true);
                f.set(customerChemical, phraseBuilder.toString());
                
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
            } catch (Exception ex) {
            }
        }
    }
    }
