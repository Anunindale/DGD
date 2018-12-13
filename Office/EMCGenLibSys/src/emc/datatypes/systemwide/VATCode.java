/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.systemwide;

import emc.datatypes.gl.vatcodes.VatId;
import emc.entity.gl.GLVATCode;

/**
 *
 * @author riaan
 */
public class VATCode extends VatId {

    /** Creates a new instance of VATCode */
    public VATCode() {
        this.setEmcLabel("VAT Code");
        this.setRelatedTable(GLVATCode.class.getName());
        this.setRelatedField("vatId");
    }
}
