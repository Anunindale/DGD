/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.phrases;

import emc.datatypes.EMCString;
import emc.entity.trec.TRECClasses;

/**
 *
 * @author wikus
 */
public class ParentClass extends EMCString {

    public ParentClass() {
        this.setEmcLabel("Class");
        this.setMandatory(false);
        this.setRelatedTable(TRECClasses.class.getName());
        this.setRelatedField("classId");
    }
}
