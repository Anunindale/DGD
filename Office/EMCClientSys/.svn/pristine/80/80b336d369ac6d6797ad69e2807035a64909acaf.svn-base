/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base.sms;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.action.EMCSMSFormMenu;

/**
 *
 * @author wikus
 */
public class EMCSMSButton extends emcMenuButtonList {

    private emcDataRelationManagerUpdate formDataManager;
    private String cellNumberFiled;
    private String postalCodeField;

    public EMCSMSButton(emcDataRelationManagerUpdate formDataManager, String cellNumberFiled, String postalCodeField) {
        super("SMS", null);
        this.formDataManager = formDataManager;
        this.cellNumberFiled = cellNumberFiled;
        this.postalCodeField = postalCodeField;
        this.addMenuItem("General", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        EMCSMSFormMenu theMenu = new EMCSMSFormMenu();
        theMenu.setDoNotOpenForm(false);

        EMCUserData generatedUD = formDataManager.getUserData().copyUserData();
        generatedUD.setUserData(0, formDataManager);
        generatedUD.setUserData(1, cellNumberFiled);
        generatedUD.setUserData(2, postalCodeField);
        formDataManager.getTheForm().getDeskTop().createAndAdd(theMenu, -1, -1, generatedUD, null, 0);
    }

}
