/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.pop;

import emc.framework.EMCMenu;
import emc.menus.pop.menuitems.display.POPParametersMenu;
import emc.menus.pop.menuitems.display.DeliveryModes;
import emc.menus.pop.menuitems.display.DeliveryTerms;
import emc.menus.pop.menuitems.display.DiscountGroups;
import emc.menus.pop.menuitems.display.ExtraChargeGroups;
import emc.menus.pop.menuitems.display.PriceGroups;
import emc.menus.pop.menuitems.display.POPPriceArrangementsMI;
import emc.menus.pop.menuitems.display.QualityTestTypes;
import emc.menus.creditors.menuitems.display.SettlementDiscountTerms;
import emc.menus.pop.menuitems.display.SupplierGroups;
import emc.menus.creditors.menuitems.display.TermsOfPayment;
import emc.menus.inventory.menuitems.display.SupplierReferenceSupp;
import emc.menus.pop.menuitems.display.CostChangeReasonMenu;
import emc.menus.pop.menuitems.display.CostChangeSummaryMenu;
import emc.menus.pop.menuitems.display.POPGoodsReturnReasonMenu;
import emc.menus.pop.menuitems.display.POPItemPriceArrangementsMI;
import emc.menus.pop.menuitems.display.POPNumberSequenceMenu;
import emc.menus.pop.menuitems.display.POPSupplierPriceArrangementsMI;

/**
 *
 * @author riaan
 */
public class POPSetupMenu extends EMCMenu {
    //Menu items used on this menu

    private SupplierGroups supplierGroups = new SupplierGroups();
    private PriceGroups priceGroups = new PriceGroups();
    private DiscountGroups discountGroups = new DiscountGroups();
    private ExtraChargeGroups extraChargeGroups = new ExtraChargeGroups();
    private TermsOfPayment termsOfPayment = new TermsOfPayment();
    private SettlementDiscountTerms settlementDiscountTerms = new SettlementDiscountTerms();
    private POPPurchaseOrederApprovals approvals = new POPPurchaseOrederApprovals();
    private DeliveryTerms deliveryTerms = new DeliveryTerms();
    private DeliveryModes deliveryModes = new DeliveryModes();
    private QualityTestTypes quality = new QualityTestTypes();
    private POPParametersMenu params = new POPParametersMenu();
    private CostChangeReasonMenu reasons = new CostChangeReasonMenu();
    private CostChangeSummaryMenu costChange = new CostChangeSummaryMenu();
    private SupplierReferenceSupp suppRef = new SupplierReferenceSupp();

    /** Creates a new instance of POPSetupMenu */
    public POPSetupMenu() {
        this.setMenuName("Setup");
        this.setMenuList(approvals);
        this.setMenuList(reasons);
        this.setMenuList(costChange);
        this.setMenuList(deliveryModes);
        this.setMenuList(deliveryTerms);
        this.setMenuList(discountGroups);
        this.setMenuList(extraChargeGroups);
        this.setMenuList(supplierGroups);
        this.setMenuList(suppRef);
        this.setMenuList(params);
        this.setMenuList(priceGroups);
        this.setMenuList(new POPPriceArrangementsMI());
        this.setMenuList(new POPItemPriceArrangementsMI());
        this.setMenuList(new POPSupplierPriceArrangementsMI());
        this.setMenuList(quality);
        this.setMenuList(settlementDiscountTerms);
        this.setMenuList(termsOfPayment);
        this.setMenuList(new POPGoodsReturnReasonMenu());

        this.setMenuList(new POPNumberSequenceMenu());
    }
}
