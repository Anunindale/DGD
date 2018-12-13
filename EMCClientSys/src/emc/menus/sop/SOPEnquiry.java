/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.sop;

import emc.framework.EMCMenu;
import emc.menus.sop.menuitems.display.SOPCustomerRepEnquiry;
import emc.menus.sop.menuitems.display.SOPPriceArangementEnquiryMenu;
import emc.menus.sop.menuitems.display.SOPPriceAutitTrailApprovalMenu;
import emc.menus.sop.menuitems.display.SOPPriceAutitTrailMenu;
import emc.menus.sop.menuitems.display.SOPSalesRepCommissionEnquiryMenu;
import emc.menus.sop.menuitems.display.SOPStatusEnquiryMenu;

/**
 *
 * @author wikus
 */
public class SOPEnquiry extends EMCMenu {

    /**
     * Creates a new instance of RESetup
     */
    public SOPEnquiry() {
        this.setMenuName("Enquiry");
        this.setMenuList(new SOPSalesRepCommissionEnquiryMenu());
        this.setMenuList(new SOPPriceArangementEnquiryMenu());
        this.setMenuList(new SOPCustomerRepEnquiry());
        this.setMenuList(new SOPStatusEnquiryMenu());
        this.setMenuList(new SOPPriceAutitTrailMenu());
        this.setMenuList(new SOPPriceAutitTrailApprovalMenu());
    }
}
