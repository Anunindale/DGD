/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.treccards;

import emc.datatypes.EMCString;

/**
 * @description : Datatype for lineId on TRECTrecCardsLines.
 *
 * @date        : 19 April 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class LineId extends EMCString {

    /** Creates a new instance of LineId */
    public LineId() {
        this.setEmcLabel("Id");
        this.setMandatory(false);
        this.setNumberSeqAllowed(true);
    }
}
