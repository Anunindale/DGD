/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECChemicals", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"chemicalId", "companyId"})})
public class TRECChemicals extends TRECChemicalsSuper {
}
