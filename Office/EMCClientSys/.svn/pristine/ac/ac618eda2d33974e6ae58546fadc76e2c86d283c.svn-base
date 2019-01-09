/*
 * POPMainMenu.java
 *
 * Created on 29 November 2007, 11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.menus.pop;

import emc.enums.modules.enumEMCModules;
import emc.framework.EMCMenu;
import emc.menus.pop.menuitems.display.POPPlannedPurchaseOrderMenu;
import emc.menus.pop.menuitems.display.PurchaseOrders;
import emc.menus.pop.menuitems.display.SupplierReceivedJournals;
import emc.menus.pop.menuitems.display.Suppliers;

/**
 *
 * @author rico
 */
public class POPMainMenu extends EMCMenu {

    //Class variables
    private PurchaseOrders purchaseOrders = new PurchaseOrders();
    private Suppliers suppliers = new Suppliers();
    private SupplierReceivedJournals supplierRecievedJournals = new SupplierReceivedJournals();
    // private POPJournalsMenu journals = new POPJournalsMenu();
    private POPSystem system = new POPSystem();
    private POPReports reports = new POPReports();
    private POPSetupMenu setup = new POPSetupMenu();
    private POPPeriodic periodic = new POPPeriodic();

    /** Creates a new instance of POPMainMenu */
    public POPMainMenu() {
        this.setEmcModule(enumEMCModules.POP);
        this.setMenuName(this.getEmcModule().toString());
        this.setMenuList(purchaseOrders);
        this.setMenuList(new POPPlannedPurchaseOrderMenu());
        this.setMenuList(suppliers);
        this.setMenuList(supplierRecievedJournals);
        this.setMenuList(periodic);
        this.setMenuList(reports);
        this.setMenuList(setup);
        this.setMenuList(system);
    }
}
