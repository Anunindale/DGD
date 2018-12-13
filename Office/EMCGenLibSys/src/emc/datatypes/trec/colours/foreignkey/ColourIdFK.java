/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.colours.foreignkey;

import emc.datatypes.trec.colours.ColourId;
import emc.entity.trec.TRECColours;

/**
 *
 * @author wikus
 */
public class ColourIdFK extends ColourId {

    public ColourIdFK() {
        this.setRelatedTable(TRECColours.class.getName());
        this.setRelatedField("colourId");
    	this.setColumnWidth(42);
        this.setMandatory(false);
    }
}
