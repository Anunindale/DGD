/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.creditnotes.InvCNNumber;
import emc.datatypes.debtors.creditnotes.Quantity;
import emc.entity.debtors.superclasses.DebtorsInvoiceLinesSuper;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : This entity class represents Credit Note lines in EMC.
 *
 * @date        : 14 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Entity
@Table(name = "DebtorsCreditNoteLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"invCNNumber", "lineNo", "companyId"})})
public class DebtorsCreditNoteLines extends DebtorsInvoiceLinesSuper {

    /** Creates a new instance of DebtorsCreditNoteLines */
    public DebtorsCreditNoteLines() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("invCNNumber", new InvCNNumber());
        toBuild.put("quantity", new Quantity());

        return toBuild;
    }
}
