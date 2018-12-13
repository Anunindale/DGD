/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.treccards;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class TradingName extends EMCString{

    public TradingName() {
        this.setEmcLabel("Trade Name");
        this.setStringSize(200);
    	this.setColumnWidth(106);
        this.setLowerCaseAllowed(true);
    }

}
