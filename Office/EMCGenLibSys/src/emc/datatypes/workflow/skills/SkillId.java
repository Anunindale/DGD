/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.workflow.skills;

import emc.datatypes.EMCString;

/**
 *
 * @author rico
 */
public class SkillId extends EMCString {
    public SkillId(){
        this.setMandatory(true);
        this.setEmcLabel("Skill Id");
        this.setColumnWidth(50);
        
    }

}
