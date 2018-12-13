/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.trec.cargocheck.foreignkeys;

import emc.datatypes.trec.cargocheck.CargoCheckNumer;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class CargoCheckNumberFK extends CargoCheckNumer {

    public CargoCheckNumberFK() {
        this.setRelatedTable(TRECCargoCheckMaster.class.getName());
        this.setRelatedField("cargoCheckNumber");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setNumberSeqAllowed(false);
    }
}
