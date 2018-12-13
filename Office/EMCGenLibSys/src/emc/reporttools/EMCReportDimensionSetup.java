/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools;

/**
 *
 * @author wikus
 */
public class EMCReportDimensionSetup {

    private String resultSetName;
    private String dim1Entity;
    private String dim1Field;
    private String dim2Entity;
    private String dim2Field;
    private String dim3Entity;
    private String dim3Field;
    //This should be set in order to select an entity
    private String resultClass;
    //Internally, this is an Objct array, so that the XML Handler can handle it.
    //For the outside, it is passed around ass an int[].
    private Object[] sortOrder;

    public EMCReportDimensionSetup() {
    }

    public EMCReportDimensionSetup(String resultSetName, String dim1Entity, String dim1Field, String dim2Entity, String dim2Field, String dim3Entity, String dim3Field) {
        this.resultSetName = resultSetName;
        this.dim1Entity = dim1Entity;
        this.dim1Field = dim1Field;
        this.dim2Entity = dim2Entity;
        this.dim2Field = dim2Field;
        this.dim3Entity = dim3Entity;
        this.dim3Field = dim3Field;
    }

    public String getDim1Entity() {
        return dim1Entity;
    }

    public void setDim1Entity(String dim1Entity) {
        this.dim1Entity = dim1Entity;
    }

    public String getDim1Field() {
        return dim1Field;
    }

    public void setDim1Field(String dim1Field) {
        this.dim1Field = dim1Field;
    }

    public String getDim2Entity() {
        return dim2Entity;
    }

    public void setDim2Entity(String dim2Entity) {
        this.dim2Entity = dim2Entity;
    }

    public String getDim2Field() {
        return dim2Field;
    }

    public void setDim2Field(String dim2Field) {
        this.dim2Field = dim2Field;
    }

    public String getDim3Entity() {
        return dim3Entity;
    }

    public void setDim3Entity(String dim3Entity) {
        this.dim3Entity = dim3Entity;
    }

    public String getDim3Field() {
        return dim3Field;
    }

    public void setDim3Field(String dim3Field) {
        this.dim3Field = dim3Field;
    }

    public String getResultSetName() {
        return resultSetName;
    }

    public void setResultSetName(String resultSetName) {
        this.resultSetName = resultSetName;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
    }

    public int[] getSortOrder() {
        if (sortOrder == null) {
            return null;
        } else {
            int[] sortArr = new int[sortOrder.length];

            for (int i = 0; i < sortOrder.length; i++) {
                sortArr[i] = Integer.valueOf(String.valueOf(sortOrder[i]));
            }

            return sortArr;
        }
    }

    public void setSortOrder(int[] sortOrder) {
        if (sortOrder == null) {
            this.sortOrder = null;
        } else {
            Object[] sortArr = new Object[sortOrder.length];

            for (int i = 0; i < sortOrder.length; i++) {
                sortArr[i] = sortOrder[i];
            }

            this.sortOrder = sortArr;
        }
    }
}
