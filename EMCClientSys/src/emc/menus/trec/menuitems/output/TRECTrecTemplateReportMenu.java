/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec.menuitems.output;

import emc.enums.enumMenuItems;
import emc.forms.trec.output.template.TrecTemplateReportForm;
import emc.forms.trec.output.treccard.TRECTrecCardReportForm;
import emc.framework.EMCMenuItem;

/**
 *
 * @author wikus
 */
public class TRECTrecTemplateReportMenu extends EMCMenuItem {

    public TRECTrecTemplateReportMenu() {
        this.setClassPath(TrecTemplateReportForm.class.getName());
        this.setMenuItemName("TREC Template Report");
        this.setMenuItemType(enumMenuItems.OUTPUT);
        this.setToolTipText("Print Trec Card Template");
    }
}
