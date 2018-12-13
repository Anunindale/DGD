/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.budgetmodel.foreignkeys;

import emc.datatypes.gl.budgetlines.ModelId;
import emc.entity.gl.GLBudgetModel;

/**
 *
 * @author claudette
 */
public class ModelIdFK extends ModelId {

    public ModelIdFK() {
        this.setRelatedTable(GLBudgetModel.class.getName());
        this.setRelatedField("modelId");
    }
}
