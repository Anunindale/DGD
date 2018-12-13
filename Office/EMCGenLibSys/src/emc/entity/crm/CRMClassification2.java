/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CRMClassification2", uniqueConstraints = {@UniqueConstraint(columnNames = {"classification", "companyId"})})
public class CRMClassification2 extends CRMClassificationSuper {
}
