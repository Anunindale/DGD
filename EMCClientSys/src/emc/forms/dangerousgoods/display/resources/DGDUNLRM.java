/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;

/**
 *
 * @author pj
 */
public class DGDUNLRM extends EMCLookupRelationManager
{
    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm)
    {
        EMCQuery lineNum = new EMCQuery(enumQueryTypes.SELECT, DGDeclarationLines.class);
    }
    
    @Override 
    public void doRelation(EMCLookup lookup)
    {

    }
}
