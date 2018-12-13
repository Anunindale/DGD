/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.gl;

import emc.framework.EMCMenu;
import emc.menus.gl.menuitems.display.GLAllocationRulesMenu;
import emc.menus.gl.menuitems.display.GLCurrency;
import emc.menus.gl.menuitems.display.GLFinancialPeriods;
import emc.menus.gl.menuitems.display.GLGroupCompanyAccountMI;
import emc.menus.gl.menuitems.display.GLGroupRulesMI;
import emc.menus.gl.menuitems.display.GLNumberSequenceMenu;
import emc.menus.gl.menuitems.display.GLParametersMI;
import emc.menus.gl.menuitems.display.GLSecurityRulesMenu;
import emc.menus.gl.menuitems.display.GLVATCode;

/**
 *
 * @author riaan
 */
public class GLSetup extends EMCMenu {

    /** Creates a new instance of GLSetup*/
    public GLSetup() {
        this.setMenuName("Setup");
        this.setMenuList(new GLAnalysisCodesMenu());
        this.setMenuList(new GLJournalsSetup());
        this.setMenuList(new GLAllocationRulesMenu());
        this.setMenuList(new GLCurrency());
        this.setMenuList(new GLFinancialPeriods());
        this.setMenuList(new GLGroupRulesMI());
        this.setMenuList(new GLGroupCompanyAccountMI());
        this.setMenuList(new GLNumberSequenceMenu());
        this.setMenuList(new GLParametersMI());
        this.setMenuList(new GLSecurityRulesMenu());
        this.setMenuList(new GLVATCode());
    }
}
