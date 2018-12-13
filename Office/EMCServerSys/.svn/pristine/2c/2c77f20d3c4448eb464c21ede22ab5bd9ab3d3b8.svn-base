/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.entityrelationdiagram;

import emc.base.BaseEntityRelation;
import emc.bus.base.tables.TablesLocal;
import emc.datatypes.EMCDataType;
import emc.entity.base.BaseCompanyTable;
import emc.enums.base.entityrelationdiagram.EntityRelationTypes;
import emc.enums.base.entityrelationdiagram.EntityRelationUpdateTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.tables.EMCTableRelation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseEntityRelationDiagramBean extends EMCBusinessBean implements BaseEntityRelationDiagramLocal {

    @EJB
    private TablesLocal tablesBean;

    @Override
    public Map<Integer, Map<String, List<BaseEntityRelation>>> getRelationsForTable(String entityClassName, EMCUserData userData) {
        Map<String, List<EMCTableRelation>> relationData = tablesBean.getRelations(entityClassName, userData);
        Map<Integer, Map<String, List<BaseEntityRelation>>> leveRelationMap = new HashMap<Integer, Map<String, List<BaseEntityRelation>>>();
        List<BaseEntityRelation> entityRelationList = null;
        BaseEntityRelation entityRelation;
        BaseEntityRelation sourceEntityRelation;
        Class entityClass = null;
        EMCEntityClass entity;
        Class sourceEntityClass = null;
        EMCEntityClass sourceEntity;
        EMCDataType dt;
        boolean useBeanOnUpdate;
        boolean useBeanOnDelete;
        List<EMCTableRelation> relationList;

        try {
            sourceEntityClass = Class.forName(entityClassName);
            sourceEntity = (EMCEntityClass) sourceEntityClass.newInstance();
        } catch (Exception ex) {
            //Handle Exception
            sourceEntity = null;
        }
        List<BaseEntityRelation> sourceEntityRelationList = new ArrayList<BaseEntityRelation>();

        //Level 3
        Map<String, List<BaseEntityRelation>> relationMap = new HashMap<String, List<BaseEntityRelation>>();
        if (relationData != null && !relationData.isEmpty()) {
            for (String key : relationData.keySet()) {
                //Source
                sourceEntityRelation = new BaseEntityRelation();
                sourceEntityRelation.setLevel(2);
                sourceEntityRelation.setRelationLevel(3);
                sourceEntityRelation.setSqlFieldName(key);
                //Check Unique Constraints
                javax.persistence.Table table = (Table) sourceEntityClass.getAnnotation(javax.persistence.Table.class);
                if (table != null) {
                    UniqueConstraint[] uca = table.uniqueConstraints();
                    if (uca != null && uca.length > 0) {
                        String[] uniqueColumns = uca[0].columnNames();
                        if (uniqueColumns != null && uniqueColumns.length > 0) {
                            List<String> columnNames = new ArrayList(Arrays.asList(uniqueColumns));
                            columnNames.remove("companyId");
                            if (columnNames.size() == 1 && columnNames.contains(key)) {
                                sourceEntityRelation.setRelationType(EntityRelationTypes.ONE.toString());
                            }
                        }
                    }
                }
                if (sourceEntity != null) {
                    sourceEntityRelation.setClassName(sourceEntity.getClass().getName());
                    sourceEntityRelation.setDisplayName(sourceEntity.getEmcLabel());
                    dt = sourceEntity.getFieldDataTypeMapper().get(key);
                } else {
                    sourceEntityRelation.setClassName(entityClassName);
                    sourceEntityRelation.setDisplayName(entityClassName);
                    dt = null;
                }
                if (dt != null) {
                    sourceEntityRelation.setFieldName(dt.getEmcLabel());
                } else {
                    sourceEntityRelation.setFieldName(key);
                }
                sourceEntityRelationList.add(sourceEntityRelation);
                //Relation
                relationList = relationData.get(key);
                for (EMCTableRelation relation : relationList) {
                    if (relation.getChildClassPath().equals(entityClassName)) {
                        continue;
                    }
                    entityRelationList = relationMap.get(relation.getChildTableEmcLabel());
                    if (entityRelationList == null) {
                        entityRelationList = new ArrayList<BaseEntityRelation>();
                    }
                    try {
                        entityClass = Class.forName(relation.getChildClassPath());
                        entity = (EMCEntityClass) entityClass.newInstance();
                        dt = entity.getFieldDataTypeMapper().get(relation.getChildFieldName());
                    } catch (Exception ex) {
                        //Handle Exception
                        dt = null;
                    }
                    entityRelation = new BaseEntityRelation();
                    entityRelation.setLevel(3);
                    entityRelation.setRelationLevel(2);
                    entityRelation.setClassName(relation.getChildClassPath());
                    entityRelation.setDisplayName(relation.getChildTableEmcLabel());
                    entityRelation.setSqlFieldName(relation.getChildFieldName());
                    entityRelation.setRelatedTable(sourceEntity.getEmcLabel());
                    entityRelation.setRelatedTableClassName(sourceEntity.getClass().getName());
                    entityRelation.setRelatedField(key);
                    entityRelation.setRelatedSqlField(key);

                    //Check Unique Constraints
                    table = (Table) entityClass.getAnnotation(javax.persistence.Table.class);
                    if (table != null) {
                        UniqueConstraint[] uca = table.uniqueConstraints();
                        if (uca != null && uca.length > 0) {
                            String[] uniqueColumns = uca[0].columnNames();
                            if (uniqueColumns != null && uniqueColumns.length > 0) {
                                List<String> columnNames = new ArrayList(Arrays.asList(uniqueColumns));
                                columnNames.remove("companyId");
                                if (columnNames.size() == 1 && columnNames.contains(relation.getChildFieldName())) {
                                    entityRelation.setRelationType(EntityRelationTypes.ONE.toString());
                                }
                            }
                        }
                    }
                    entityRelation.setRelatedRelationType(sourceEntityRelation.getRelationType());

                    if (dt != null) {
                        entityRelation.setFieldName(dt.getEmcLabel());
                    } else {
                        entityRelation.setFieldName(relation.getChildFieldName());
                    }
                    switch (relation.getCallBeanOption()) {
                        case SQL:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = false;
                            break;
                        case UPDATE_AND_DELETE:
                            useBeanOnDelete = true;
                            useBeanOnUpdate = true;
                            break;
                        case DELETE_ONLY:
                            useBeanOnDelete = true;
                            useBeanOnUpdate = false;
                            break;
                        case UPDATE_ONLY:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = true;
                            break;
                        default:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = false;
                    }
                    switch (relation.getDeleteAction()) {
                        case CASCADE:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_CASCADE_BEAN.toString() : EntityRelationUpdateTypes.DELETE_CASCADE_SQL.toString());
                            break;
                        case CLEARFIELD:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_CLEARFIELD_BEAN.toString() : EntityRelationUpdateTypes.DELETE_CLEARFIELD_SQL.toString());
                            break;
                        case IGNORE:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_IGNORE_BEAN.toString() : EntityRelationUpdateTypes.DELETE_IGNORE_SQL.toString());
                            break;
                        case RESTRICT:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_RESTRICT_BEAN.toString() : EntityRelationUpdateTypes.DELETE_RESTRICT_SQL.toString());
                            break;
                    }
                    switch (relation.getUpdateAction()) {
                        case CASCADE:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_CASCADE_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_CASCADE_SQL.toString());
                            break;
                        case CLEARFIELD:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_CLEARFIELD_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_CLEARFIELD_SQL.toString());
                            break;
                        case IGNORE:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_IGNORE_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_IGNORE_SQL.toString());
                            break;
                        case RESTRICT:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_RESTRICT_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_RESTRICT_SQL.toString());
                            break;
                    }
                    entityRelationList.add(entityRelation);
                    relationMap.put(relation.getChildTableEmcLabel(), entityRelationList);
                }
            }
        }
        if (!relationMap.isEmpty()) {
            leveRelationMap.put(3, relationMap);
        }
        //Level 1
        relationMap = new HashMap<String, List<BaseEntityRelation>>();
        if (sourceEntity != null) {
            Map<String, EMCDataType> dtMap = sourceEntity.getFieldDataTypeMapper();
            EMCDataType relatedDT;
            EMCEntityClass relatedEntity;
            Class relatedEntityClass = null;
            for (String key : dtMap.keySet()) {
                dt = (EMCDataType) dtMap.get(key);
                if (dt != null && dt.getRelatedTable() != null && dt.getRelatedField() != null) {
                    if (dt.getRelatedTable().equals(entityClassName)) {
                        continue;
                    }
                    entityRelationList = new ArrayList<BaseEntityRelation>();
                    try {
                        relatedEntityClass = Class.forName(dt.getRelatedTable());
                        relatedEntity = (EMCEntityClass) relatedEntityClass.newInstance();
                    } catch (Exception ex) {
                        //Handle Exception
                        relatedEntity = null;
                        relatedEntityClass = null;
                    }
                    sourceEntityRelation = new BaseEntityRelation();
                    sourceEntityRelation.setLevel(1);
                    sourceEntityRelation.setRelationLevel(2);
                    sourceEntityRelation.setSqlFieldName(dt.getRelatedField());
                    //Check Unique Constraints
                    javax.persistence.Table table = (Table) relatedEntityClass.getAnnotation(javax.persistence.Table.class);
                    if (table != null) {
                        UniqueConstraint[] uca = table.uniqueConstraints();
                        if (uca != null && uca.length > 0) {
                            String[] uniqueColumns = uca[0].columnNames();
                            if (uniqueColumns != null && uniqueColumns.length > 0) {
                                List<String> columnNames = new ArrayList(Arrays.asList(uniqueColumns));
                                if (!dt.getRelatedTable().equals(BaseCompanyTable.class.getName())) {
                                    columnNames.remove("companyId");
                                }
                                if (columnNames.size() == 1 && columnNames.contains(dt.getRelatedField())) {
                                    sourceEntityRelation.setRelationType(EntityRelationTypes.ONE.toString());
                                }
                            }
                        }
                    }
                    if (relatedEntity != null) {
                        sourceEntityRelation.setClassName(relatedEntity.getClass().getName());
                        sourceEntityRelation.setDisplayName(relatedEntity.getEmcLabel());
                        relatedDT = relatedEntity.getFieldDataTypeMapper().get(dt.getRelatedField());
                    } else {
                        sourceEntityRelation.setClassName(dt.getRelatedTable());
                        sourceEntityRelation.setDisplayName(dt.getRelatedTable());
                        relatedDT = null;
                    }
                    if (relatedDT != null) {
                        sourceEntityRelation.setFieldName(relatedDT.getEmcLabel());
                    } else {
                        sourceEntityRelation.setFieldName(dt.getRelatedField());
                    }
                    entityRelationList.add(sourceEntityRelation);


                    relationMap.put(relatedEntity == null ? dt.getRelatedTable() : relatedEntity.getEmcLabel(), entityRelationList);

                    //Source
                    entityRelation = new BaseEntityRelation();
                    entityRelation.setLevel(2);
                    entityRelation.setRelationLevel(1);
                    entityRelation.setSqlFieldName(key);
                    entityRelation.setRelatedTable(relatedEntity.getEmcLabel());
                    entityRelation.setRelatedTableClassName(relatedEntity.getClass().getName());
                    entityRelation.setRelatedField(dt.getRelatedField());
                    entityRelation.setRelatedSqlField(dt.getRelatedField());
                    //Check Unique Constraints
                    table = (Table) sourceEntityClass.getAnnotation(javax.persistence.Table.class);
                    if (table != null) {
                        UniqueConstraint[] uca = table.uniqueConstraints();
                        if (uca != null && uca.length > 0) {
                            String[] uniqueColumns = uca[0].columnNames();
                            if (uniqueColumns != null && uniqueColumns.length > 0) {
                                List<String> columnNames = new ArrayList(Arrays.asList(uniqueColumns));
                                columnNames.remove("companyId");
                                if (columnNames.size() == 1 && columnNames.contains(key)) {
                                    entityRelation.setRelationType(EntityRelationTypes.ONE.toString());
                                }
                            }
                        }
                    }
                    entityRelation.setRelatedRelationType(sourceEntityRelation.getRelationType());
                    if (sourceEntity != null) {
                        entityRelation.setClassName(sourceEntity.getClass().getName());
                        entityRelation.setDisplayName(sourceEntity.getEmcLabel());
                        dt = sourceEntity.getFieldDataTypeMapper().get(key);
                    } else {
                        entityRelation.setClassName(entityClassName);
                        entityRelation.setDisplayName(entityClassName);
                        dt = null;
                    }
                    if (dt != null) {
                        entityRelation.setFieldName(dt.getEmcLabel());
                    } else {
                        entityRelation.setFieldName(key);
                    }
                    switch (dt.getCallBeanOptions()) {
                        case SQL:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = false;
                            break;
                        case UPDATE_AND_DELETE:
                            useBeanOnDelete = true;
                            useBeanOnUpdate = true;
                            break;
                        case DELETE_ONLY:
                            useBeanOnDelete = true;
                            useBeanOnUpdate = false;
                            break;
                        case UPDATE_ONLY:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = true;
                            break;
                        default:
                            useBeanOnDelete = false;
                            useBeanOnUpdate = false;
                    }
                    switch (dt.getDeleteAction()) {
                        case CASCADE:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_CASCADE_BEAN.toString() : EntityRelationUpdateTypes.DELETE_CASCADE_SQL.toString());
                            break;
                        case CLEARFIELD:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_CLEARFIELD_BEAN.toString() : EntityRelationUpdateTypes.DELETE_CLEARFIELD_SQL.toString());
                            break;
                        case IGNORE:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_IGNORE_BEAN.toString() : EntityRelationUpdateTypes.DELETE_IGNORE_SQL.toString());
                            break;
                        case RESTRICT:
                            entityRelation.setDeleteRelationType(useBeanOnDelete ? EntityRelationUpdateTypes.DELETE_RESTRICT_BEAN.toString() : EntityRelationUpdateTypes.DELETE_RESTRICT_SQL.toString());
                            break;
                    }
                    switch (dt.getUpdateAction()) {
                        case CASCADE:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_CASCADE_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_CASCADE_SQL.toString());
                            break;
                        case CLEARFIELD:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_CLEARFIELD_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_CLEARFIELD_SQL.toString());
                            break;
                        case IGNORE:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_IGNORE_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_IGNORE_SQL.toString());
                            break;
                        case RESTRICT:
                            entityRelation.setUpdateRelationType(useBeanOnUpdate ? EntityRelationUpdateTypes.UPDATE_RESTRICT_BEAN.toString() : EntityRelationUpdateTypes.UPDATE_RESTRICT_SQL.toString());
                            break;
                    }
                    sourceEntityRelationList.add(entityRelation);
                }
            }
        }
        if (!relationMap.isEmpty()) {
            leveRelationMap.put(1, relationMap);
        }
        //Level 2
        relationMap = new HashMap<String, List<BaseEntityRelation>>();
        relationMap.put(entityClassName, sourceEntityRelationList);
        leveRelationMap.put(2, relationMap);
        return leveRelationMap;
    }
}
