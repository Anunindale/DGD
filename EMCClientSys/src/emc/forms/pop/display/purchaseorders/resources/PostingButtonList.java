/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.pop.purchaseorders.PurchaseOrderTypes;
import emc.framework.EMCMenuItem;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class PostingButtonList extends emcMenuButtonList {

    emcDataRelationManagerUpdate drm;

    public PostingButtonList(BaseInternalFrame form) {
        super("Posting", form);
        drm = (emcDataRelationManagerUpdate) form.getDataManager();

        EMCMenuItem postMenuItem = new emc.menus.pop.menuitems.display.Posting();
        postMenuItem.setDoNotOpenForm(false);
        addMenuItem("Blanket Order", postMenuItem, -1004, false);
        addMenuItem("Release PO", postMenuItem, -1003, false);
        addSeperator();
        addMenuItem("Purchase Order", postMenuItem, -1000, false);
        addMenuItem("Receipts List", postMenuItem, -1002, false);
        addMenuItem("Receive", postMenuItem, -1001, false);
        addMenuItem("Return Goods", postMenuItem, -1007, false);
        addMenuItem("Invoice", null, 3, false);
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {

        setAllItemsEnabled(false);

        PurchaseOrderStatus status = PurchaseOrderStatus.fromString((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "status"));
        String typeString = (String) drm.getFieldValueAt(drm.getLastRowAccessed(), "purchaseOrderType");

        if (typeString == null || typeString.equals("")) {
            java.util.logging.Logger.getLogger("emc").log(Level.WARNING, "Purchase Order needs a type", drm.getUserData());
        } else {
            PurchaseOrderTypes type = PurchaseOrderTypes.fromString(typeString);

            if (type.equals(PurchaseOrderTypes.PURCHASE_ORDER)) {
                switch (status) {
                    //Fall through
                    case REQUISITION:
                    case APPROVED:
                        setItemEnabled("Purchase Order", true);
                        break;
                    case RECEIVED:
                        setItemEnabled("Return Goods", true);
                        setItemEnabled("Return From QC", true);
                        setItemEnabled("Purchase Order", true);
                        setItemEnabled("Receipts List", true);
                        break;
                    case PARTIALLY_RECEIVED:
                        setItemEnabled("Purchase Order", true);
                        setItemEnabled("Receipts List", true);
                        setItemEnabled("Receive", true);
                        setItemEnabled("Return Goods", true);
                        setItemEnabled("Return From QC", true);
                        break;

                    default:
                        setItemEnabled("Purchase Order", true);
                        setItemEnabled("Receipts List", true);
                        setItemEnabled("Receive", true);
                        break;

                }
            } else {
                switch (status) {
                    case REQUISITION:
                    case APPROVED:
                        setItemEnabled("Blanket Order", true);
                        break;

                    case ORDERED:
                    case PARTIALLY_RECEIVED:
                        setItemEnabled("Release PO", true);
                        setItemEnabled("Blanket Order", true);
                        break;

                    default:
                        break;
                }
            }
        }
        super.doActionPerformed(evt);
    }
}
