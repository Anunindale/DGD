/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.dangerousgoods.contacts.foreignkeys;

import emc.datatypes.base.postalcodes.PostalCode;
import emc.entity.base.BasePostalCodes;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author pj
 */
public class PostalCodeFK extends PostalCode{
    public PostalCodeFK()
    {
        this.setRelatedTable(BasePostalCodes.class.getName());
        this.setRelatedField("code");
        this.setDeleteAction(enumDeleteUpdateOptions.IGNORE);
        this.setUpdateAction(enumDeleteUpdateOptions.IGNORE);
    }
}
