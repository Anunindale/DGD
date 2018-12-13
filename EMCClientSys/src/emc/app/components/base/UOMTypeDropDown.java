/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base;

import emc.enums.base.uom.UOMTypes;
import javax.swing.JComboBox;
 
/**
 *
 * @author wikus
 */
public class UOMTypeDropDown extends JComboBox {

    public UOMTypeDropDown() {
        super(new String[]{UOMTypes.AREA.toString(), UOMTypes.LENGTH.toString(), UOMTypes.MASS.toString(), UOMTypes.QUANTITY.toString(),
                           UOMTypes.TEMP.toString(), UOMTypes.TIME.toString(), UOMTypes.VOLUME.toString(), UOMTypes.OTHER.toString()
        });
    }
}
