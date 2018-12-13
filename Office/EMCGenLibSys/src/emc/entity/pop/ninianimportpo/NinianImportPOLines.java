/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.pop.ninianimportpo;

import emc.entity.pop.POPPurchaseOrderLines;

/**
 * @Name         : NinianImportPOLines
 *
 * @Date         : 08 Jul 2009
 * 
 * @Description  : Used to import old N & L purchase order lines, as these require special 
 *                 population logic which does not apply to normal Purchase Orders.
 *
 * @author       : riaan
 */
public class NinianImportPOLines extends POPPurchaseOrderLines {

    private String itemReference;
    
    /** Creates a new instance of NinianImportPOLines. */
    public NinianImportPOLines() {
        this.setDataSource(true);
    }
}
