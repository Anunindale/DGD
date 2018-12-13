/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.updateitemprices;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.developertools.ServerDeveloperToolMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @Name         : UpdateItemPricesForm
 *
 * @Date         : 24 Jul 2009
 * 
 * @Description  : Form used to update N & L item prices.
 *
 * @author       : riaan
 */
public class UpdateItemPricesForm extends BaseInternalFrame {

    /** Creates a new instance of UpdateItemPricesForm. */
    public UpdateItemPricesForm(EMCUserData userData) {
        super("Update Item Prices", true, true, true, true, userData);

        this.add(new emcJButton("Update Prices") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (EMCDialogFactory.createQuestionDialog(UpdateItemPricesForm.this, "Update Item Prices?", "Do you want to update item prices?\n(Please ensure that you have imported data into the DevNLPriceImportTable table)") == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEVELOPERTOOLS.getId(), ServerDeveloperToolMethods.UPDATE_ITEM_PRICES.toString());

                    List toSend = new ArrayList();
                    EMCWSManager.executeGenericWS(cmd, toSend, UpdateItemPricesForm.this.getUserData());
                }
            }
        });

        this.pack();
    }
}
