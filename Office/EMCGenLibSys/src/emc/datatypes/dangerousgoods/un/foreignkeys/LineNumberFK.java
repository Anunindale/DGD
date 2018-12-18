/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.un.foreignkeys;

import emc.datatypes.dangerousgoods.declarationlines.LineNumber;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class LineNumberFK extends LineNumber {
    
    public LineNumberFK()
    {
        this.setRelatedTable(DGDeclarationLines.class.getName());
        this.setRelatedField("lineNumber");
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setNumberSeqAllowed(false);
    }
    
}
