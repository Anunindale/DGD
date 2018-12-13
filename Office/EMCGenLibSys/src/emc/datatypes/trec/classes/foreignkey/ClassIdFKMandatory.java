/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.classes.foreignkey;

import emc.datatypes.trec.classes.ClassId;
import emc.entity.trec.TRECClasses;

/**
 *
 * @author wikus
 */
public class ClassIdFKMandatory extends ClassId {

    public ClassIdFKMandatory() {
        this.setRelatedTable(TRECClasses.class.getName());
        this.setRelatedField("classId");
        this.setMandatory(true);
    }
}
