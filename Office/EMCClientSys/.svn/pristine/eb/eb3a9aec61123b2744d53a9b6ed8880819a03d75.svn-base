package emc.app.components.inventory;

import emc.app.components.emctable.DataRelationManagerInterface;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.menus.inventory.menuitems.display.InventoryView;
import emc.menus.inventory.menuitems.display.OnHand;
import emc.menus.inventory.menuitems.display.Transactions;

public class emcStockButton extends emcMenuButtonList {

    public emcStockButton(BaseInternalFrame frame, Boolean lines) {
        super("Inventory", frame);

        super.addMenuItem("Inventory View", new InventoryView(), -6, lines);
        super.addMenuItem("Transactions", new Transactions(), 10, lines);
        super.addMenuItem("On Hand", new OnHand(), 11, lines);
        if (lines) {
            DataRelationManagerInterface DRMInt = (DataRelationManagerInterface) frame.getDataManager();
            DRMInt = DRMInt.getLinesTable();
        }
    }

    /**
     * Constructs the emcStockButton with only sertane menus.
     * The boolean array shows : 0 - Inventory View
     *                           1 - Transactions
     *                           2 - On Hand
     * @param frame
     * @param lines
     * @param whatToAdd
     */
    public emcStockButton(BaseInternalFrame frame, Boolean lines, boolean[] whatToAdd) {
        super("Inventory", frame);

        if (whatToAdd[0]) super.addMenuItem("Inventory View", new InventoryView(), -6, lines);
        if (whatToAdd[1]) super.addMenuItem("Transactions", new Transactions(), 10, lines);
        if (whatToAdd[2]) super.addMenuItem("On Hand", new OnHand(), 11, lines);
        if (lines) {
            DataRelationManagerInterface DRMInt = (DataRelationManagerInterface) frame.getDataManager();
            DRMInt = DRMInt.getLinesTable();
        }
    }
}
