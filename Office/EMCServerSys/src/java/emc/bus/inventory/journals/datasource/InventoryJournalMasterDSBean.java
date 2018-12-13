/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.journals.datasource;

import emc.bus.base.journals.BaseJournalDefinitionLocal;
import emc.bus.inventory.journals.InventoryJournalMasterLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.inventory.journals.datasource.InventoryJournalMasterDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryJournalMasterDSBean extends EMCDataSourceBean implements InventoryJournalMasterDSLocal {

    @EJB
    private InventoryJournalMasterLocal journalMaster;
    @EJB
    private BaseJournalDefinitionLocal journalDefinition;
    
    public InventoryJournalMasterDSBean() {
        this.setDataSourceClassName(InventoryJournalMasterDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = journalMaster.validateField(fieldNameToValidate, (convertDataSourceToSuper(theRecord, userData)), userData);
        
        return ret instanceof Boolean ? ret : convertSuperToDataSource(ret, userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalMaster.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalMaster.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return journalMaster.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryJournalMasterDS ds = (InventoryJournalMasterDS)dataSourceInstance;
        
        BaseJournalDefinitionTable definition = journalDefinition.getJournalDefinition(ds.getJournalNumber(), userData);
        ds.setJournalType(definition.getJournalType());
        
        return ds;
    }

}
