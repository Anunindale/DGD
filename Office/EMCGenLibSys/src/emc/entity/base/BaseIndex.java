package emc.entity.base;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.index.ClassName;
import emc.datatypes.base.index.IndexEight;
import emc.datatypes.base.index.IndexFive;
import emc.datatypes.base.index.IndexFour;
import emc.datatypes.base.index.IndexNine;
import emc.datatypes.base.index.IndexOne;
import emc.datatypes.base.index.IndexSeven;
import emc.datatypes.base.index.IndexSix;
import emc.datatypes.base.index.IndexTen;
import emc.datatypes.base.index.IndexThree;
import emc.datatypes.base.index.IndexTwo;
import emc.framework.EMCEntityClass;
import java.lang.Override;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** 
*
* @author claudette
*/
@Entity
@Table(name="BaseIndex", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId", "className"})})
public class BaseIndex extends EMCEntityClass {

    private String className;
    private String indexOne;
    private String indexTwo;
    private String indexThree;
    private String indexFour;
    private String indexFive;
    private String indexSix;
    private String indexSeven;
    private String indexEight;
    private String indexNine;
    private String indexTen;

    /** Creates a new instance of BaseIndex. */
    public BaseIndex() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("className", new ClassName());
        toBuild.put("indexOne", new IndexOne());
        toBuild.put("indexTwo", new IndexTwo());
        toBuild.put("indexThree", new IndexThree());
        toBuild.put("indexFour", new IndexFour());
        toBuild.put("indexFive", new IndexFive());
        toBuild.put("indexSix", new IndexSix());
        toBuild.put("indexSeven", new IndexSeven());
        toBuild.put("indexEight", new IndexEight());
        toBuild.put("indexNine", new IndexNine());
        toBuild.put("indexTen", new IndexTen());
        return toBuild;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIndexOne() {
        return this.indexOne;
    }

    public void setIndexOne(String indexOne) {
        this.indexOne = indexOne;
    }

    public String getIndexTwo() {
        return this.indexTwo;
    }

    public void setIndexTwo(String indexTwo) {
        this.indexTwo = indexTwo;
    }

    public String getIndexThree() {
        return this.indexThree;
    }

    public void setIndexThree(String indexThree) {
        this.indexThree = indexThree;
    }

    public String getIndexFour() {
        return this.indexFour;
    }

    public void setIndexFour(String indexFour) {
        this.indexFour = indexFour;
    }

    public String getIndexFive() {
        return this.indexFive;
    }

    public void setIndexFive(String indexFive) {
        this.indexFive = indexFive;
    }

    public String getIndexSix() {
        return this.indexSix;
    }

    public void setIndexSix(String indexSix) {
        this.indexSix = indexSix;
    }

    public String getIndexSeven() {
        return this.indexSeven;
    }

    public void setIndexSeven(String indexSeven) {
        this.indexSeven = indexSeven;
    }

    public String getIndexEight() {
        return this.indexEight;
    }

    public void setIndexEight(String indexEight) {
        this.indexEight = indexEight;
    }

    public String getIndexNine() {
        return this.indexNine;
    }

    public void setIndexNine(String indexNine) {
        this.indexNine = indexNine;
    }

    public String getIndexTen() {
        return this.indexTen;
    }

    public void setIndexTen(String indexTen) {
        this.indexTen = indexTen;
    }

}