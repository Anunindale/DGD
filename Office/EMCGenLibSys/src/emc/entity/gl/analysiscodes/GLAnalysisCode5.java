/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.gl.analysiscodes;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author claudette
 */
@Entity
@Table(name = "GLAnalysisCode5", uniqueConstraints = {@UniqueConstraint(columnNames = {"analysisCode", "companyId"})})
public class GLAnalysisCode5 extends GLAnalysisCodeSuper {

}
