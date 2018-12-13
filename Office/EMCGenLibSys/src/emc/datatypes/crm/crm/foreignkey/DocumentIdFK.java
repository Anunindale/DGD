/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.crm.crm.foreignkey;

import emc.datatypes.crm.crm.DocumentId;
import emc.entity.crm.CRMDocuments;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author kapeshi
 */
public class DocumentIdFK extends DocumentId {

    public DocumentIdFK() {
        this.setRelatedTable(CRMDocuments.class.getName());
        this.setRelatedField("documentId");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }

}
