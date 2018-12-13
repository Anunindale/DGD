/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.journals;

import emc.datatypes.EMCDataType;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.enums.base.journals.JournalStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @description : Entity class used to represent Debtors Journal Master records.
 *
 * @date        : 03 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Table(name = "DebtorsJournalMaster", uniqueConstraints = {
@UniqueConstraint(columnNames = {"journalNumber", "companyId"})})
@Entity
public class DebtorsJournalMaster extends JournalMasterSuperClass {

    /** Creates a new instance of DebtorsJournalMaster */
    public DebtorsJournalMaster() {

    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();

        query.addAnd("journalStatus", JournalStatus.POSTED, EMCQueryConditions.NOT);
        query.addOrderBy("journalNumber");

        return query;
    }

    @Override
    public EMCQuery buildLookupQuery() {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);

        return query;
    }

     @Override
    public List<String> getDefaultLookupFields(){
        List<String> fields = new ArrayList<String>();
        fields.add("journalDefinitionId");
        fields.add("journalNumber");
        fields.add("journalDescription");
        return fields;
    }
    
}
