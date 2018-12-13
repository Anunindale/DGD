/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.currency.foreignkeys;

import emc.datatypes.gl.currency.Currency;
import emc.entity.gl.GLCurrency;

/**
 *
 * @author riaan
 */
public class CurrencyFK extends Currency {

    /** Creates a new instance of CurrencyFK */
    public CurrencyFK() {
        this.setRelatedTable(GLCurrency.class.getName());
        this.setRelatedField("currency");
        this.setMandatory(false);
        this.setEditable(false);
    }
}
