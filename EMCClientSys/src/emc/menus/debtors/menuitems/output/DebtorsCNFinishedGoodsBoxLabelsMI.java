/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.debtors.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.debtors.output.DebtorsCreditNoteReportForm;
import emc.forms.debtors.output.cnfinishedgoodsboxlabels.CNFinishedGoodsBoxLabelReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author riaan
 */
public class DebtorsCNFinishedGoodsBoxLabelsMI extends EMCMenuItem {

    /** Creates a new instance of DebtorsCNFinishedGoodsBoxLabelsMI. */
    public DebtorsCNFinishedGoodsBoxLabelsMI() {
        this.setClassPath(CNFinishedGoodsBoxLabelReportForm.class.getName());
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setMenuItemName("Credit Note Box Labels");
        this.setToolTipText("Print Credit Note Box Labels.");
    }
}
