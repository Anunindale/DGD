/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.provence.foreignkeys;

import emc.datatypes.base.provence.*;
import emc.entity.base.BaseProvence;

/**
 *
 * @author wikus
 */
public class ProvenceFK extends Provence {

    public ProvenceFK() {
        this.setRelatedTable(BaseProvence.class.getName());
        this.setRelatedField("provence");
    }
}
