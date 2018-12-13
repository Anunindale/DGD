/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.query;

import emc.framework.EMCQuery;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author rico
 */
public class BaseQueryConstructionHelper {
    private TreeMap<Integer,BaseQueryActionHelper> helperMap;
    private EMCQuery theQuery;
    private List<String> errorList;
    private List<String> mergeFields;
    private String messageText;

    public BaseQueryConstructionHelper(TreeMap<Integer,BaseQueryActionHelper> helperMap,EMCQuery theQuery,List<String> errorList){
        this.helperMap = helperMap;
        this.theQuery = theQuery;
        this.errorList = errorList;
    }

    /**
     * @return the helperMap
     */
    public TreeMap<Integer, BaseQueryActionHelper> getHelperMap() {
        return helperMap;
    }

    /**
     * @param helperMap the helperMap to set
     */
    public void setHelperMap(TreeMap<Integer, BaseQueryActionHelper> helperMap) {
        this.helperMap = helperMap;
    }

    /**
     * @return the theQuery
     */
    public EMCQuery getTheQuery() {
        return theQuery;
    }

    /**
     * @param theQuery the theQuery to set
     */
    public void setTheQuery(EMCQuery theQuery) {
        this.theQuery = theQuery;
    }

    /**
     * @return the errorList
     */
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * @param errorList the errorList to set
     */
    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    /**
     * @return the mergeFields
     */
    public List<String> getMergeFields() {
        return mergeFields;
    }

    /**
     * @param mergeFields the mergeFields to set
     */
    public void setMergeFields(List<String> mergeFields) {
        this.mergeFields = mergeFields;
    }

    /**
     * @return the messageText
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * @param messageText the messageText to set
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
