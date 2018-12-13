/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.trec;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.trec.menuitems.output.CargoCheckReportMI;
import emc.menus.developertools.trec.ErgPhrasesReportMI;
import emc.menus.trec.menuitems.output.TRECLowHazardReportMI;
import emc.menus.trec.menuitems.output.TRECPacardsReportMI;
import emc.menus.trec.menuitems.output.TRECTrecReportMenu;
import emc.menus.trec.menuitems.output.TRECTrecTemplateReportMenu;

/**
 *
 * @author wikus
 */
public class TRECReports extends EMCMenu {

    /** Creates a new instance of TRECSetup*/
    public TRECReports() {
        this.setMenuName("Reports");
        this.setMenuList(new CargoCheckReportMI());
        this.setMenuList(new TRECTrecReportMenu());
        this.setMenuList(new ErgPhrasesReportMI());
        this.setMenuList(new TRECPacardsReportMI());
        this.setMenuList(new TRECLowHazardReportMI());
        this.setMenuList(new TRECTrecTemplateReportMenu());
        
       
    }
}
