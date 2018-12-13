/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.odour.foreignkey;

import emc.datatypes.trec.odour.OdourId;
import emc.entity.trec.TRECOdours;

/**
 *
 * @author wikus
 */
public class OdourIdFK extends OdourId {

    public OdourIdFK() {
        this.setRelatedTable(TRECOdours.class.getName());
        this.setRelatedField("odourId");
    	this.setColumnWidth(40);
        this.setMandatory(false);
    }
}
