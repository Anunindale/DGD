/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.itemmaster.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.itemmaster.ItemMasterDRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class GenerateCombinationsButton extends emcMenuButton {

    private EMCUserData userData;
    private ItemMasterDRM drm;

    /** Creates a new instance of GenerateCombinationsButton */
    public GenerateCombinationsButton(String label, EMCMenuItem buttonitem, BaseInternalFrame form, int relatedFormIndex, boolean lines) {
        super(label, buttonitem, form, relatedFormIndex, lines);
    }

    /** Creates a new instance of GenerateCombinationsButton */
    public GenerateCombinationsButton(EMCUserData userData, ItemMasterDRM drm, int relatedFormIndex) {
        super("Generate Dim Combinations", new emc.menus.inventory.menuitems.display.ItemDimensionCombinations(), drm.getTheForm(), relatedFormIndex, false);
        this.userData = userData;
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        emcDialogue dialog = new emcDialogue("Are You Sure", "Generate Combination?", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        if (dialog.getDialogueResult() == 0) {
            Object itemId = drm.getFieldValueAt(drm.getLastRowAccessed(), "itemId");

            if (itemId != null) {
                EMCCommandClass cmd = new EMCCommandClass();
                cmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
                cmd.setModuleNumber(enumEMCModules.INVENTORY.getId());
                cmd.setMethodId(ServerInventoryMethods.GENERATE_ITEM_DIM_COMBINATIONS.toString());

                ArrayList toSend = new ArrayList();
                toSend.add(itemId);

                List ret = EMCWSManager.executeGenericWS(cmd, toSend, userData);

                //Generation was succesful
                if ((Boolean) ret.get(1)) {
                    super.doActionPerformed(evt);
                }
            }
        }
    }
}
