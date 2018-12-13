/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.display;

import emc.enums.enumMenuItems;
import emc.forms.debtors.display.creditheld.CreditHeldForm;
import emc.forms.debtors.display.creditinsurer.CreditInsurerForm;
import emc.forms.debtors.output.DebtorsAgeAnalysisByAgentReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsAgeAnalysisByAgentMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsAgeAnalysisByAgentMI */
    public DebtorsAgeAnalysisByAgentMI() {
        this.setClassPath(DebtorsAgeAnalysisByAgentReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Agent's Customer Activity");
        this.setToolTipText("View Customer Activity by Agent.");
    }
}
