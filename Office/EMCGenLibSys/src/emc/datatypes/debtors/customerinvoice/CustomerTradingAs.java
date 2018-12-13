/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.debtors.customerinvoice;

import emc.datatypes.EMCString;
import emc.datatypes.sop.customers.TradingAs;

/**
 *
 * @author Selaelo
 */
public class CustomerTradingAs extends EMCString{

    public CustomerTradingAs() {
        this.setEmcLabel("Customer Trading As");
    	this.setColumnWidth(70);
        this.setStringSize(255);
    }
    
}
