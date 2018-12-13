/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.sop.priceaudittrail;

import emc.datatypes.EMCString;
import emc.datatypes.datasources.DSRelation;
import emc.entity.base.Usertable;

/**
 *
 * @author wikus
 */
public class UserName extends EMCString {

    public UserName() {
        DSRelation relation = new DSRelation();
        this.setColumnWidth(78);
        this.setEmcLabel("User");
        relation.setRelatedTable(Usertable.class.getName());
        relation.setRelatedField("userName");//UserTable, field that you want.
        relation.setPKField("userId");//Usertable
        relation.setFKField("userId");//soppriceAuditTrail
        this.setDsRelation(relation);
        this.setStringSize(10);
    }
}
