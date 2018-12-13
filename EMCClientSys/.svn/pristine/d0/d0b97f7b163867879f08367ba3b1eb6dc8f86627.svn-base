/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.app.journals.journaldefinitions.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.enums.base.journals.Modules;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 * @description : DRM used on journal definition forms.
 *
 * @date        : 07 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class JournalDefinitionsDRM extends emcDataRelationManagerUpdate {

    private Modules module;

    /** Creates a new instance of JournalDefinitionsDRM */
    public JournalDefinitionsDRM(Modules module, EMCUserData userData) {
        super(new emcGenericDataSourceUpdate(
                enumEMCModules.BASE.getId(), new BaseJournalDefinitionTable(), userData), userData);
        this.module = module;
    }

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }

        super.updatePersist(rowIndex);
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (Functions.checkBlank(getFieldValueAt(rowIndex, "journalModule"))) {
            //Populate module before doing field validation.
            super.setFieldValueAt(rowIndex, "journalModule", module.toString());
        }
        
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
    }
}
