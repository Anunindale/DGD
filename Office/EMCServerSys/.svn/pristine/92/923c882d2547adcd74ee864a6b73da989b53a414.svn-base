/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package emc.bus.pop.posting;

import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.enums.pop.posting.PostingQuantities;
import emc.enums.enumQueryTypes;
import emc.enums.pop.posting.DocumentTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.utility.EMCServerUtilityLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPPurchasePostSetupTableBean extends EMCEntityBean implements POPPurchasePostSetupTableLocal {

    @EJB
    private POPPurchasePostMasterLocal postMasterBean;

    /** Creates a new instance of POPPurchasePostSetupTableBean */
    public POPPurchasePostSetupTableBean() {

    }

    public POPPurchasePostSetupTable getSetupForMaster(String postId, EMCUserData userData) {
        //Get the setup for the post master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostSetupTable.class.getName());
        query.addAnd("postId", postId);

        return (POPPurchasePostSetupTable) util.executeSingleResultQuery(query, userData);

    }

    /** In order for this method to work, position 3 in the user data list must have a value. */
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {

        //Get the setup for the post master
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostSetupTable.class.getName());
        query.addAnd("createdBy", userData.getUserName());
        query.addOrderBy("autoNumber DESC");

        POPPurchasePostSetupTable prevSetup = null;

        List setupList = util.executeGeneralSelectQuery(query, userData);

        if (setupList != null && setupList.size() > 0) {
            prevSetup = (POPPurchasePostSetupTable) setupList.get(0);
        }

        
        POPPurchasePostSetupTable setup;
        
        if (prevSetup != null) {
            setup = setupNewPost(prevSetup.getPost(), prevSetup.getPrint(), PostingQuantities.fromString(prevSetup.getQuantitySelection()), DocumentTypes.fromString(userData.getUserData(3).toString()), new EMCQuery(userData.getUserData(0).toString()), userData);
        } else {
            setup = setupNewPost(false, false, PostingQuantities.ALL, DocumentTypes.fromString(userData.getUserData(3).toString()), new EMCQuery(userData.getUserData(0).toString()), userData);
        }

        ArrayList ret = new ArrayList();
        ret.add(setup);
        return ret;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return "1" + ", " + Long.MAX_VALUE;
    }
    
    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        POPPurchasePostSetupTable setup = (POPPurchasePostSetupTable) iobject;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostSetupTable.class.getName());
        query.addFieldAggregateFunction("autoNumber", POPPurchasePostSetupTable.class.getName(), "MAX");

        Long maxNumber = (Long) util.executeSingleResultQuery(query, userData);

        if (maxNumber == null) {
            maxNumber = -1l;
        }

        setup.setAutoNumber(++maxNumber);

        return super.insert(iobject, userData);
    }

    /**
     * Creates and returns a new POPPurchasePostSetupTable record.
     * @param post Indicates the value of the post flag to be set on the new record.
     * @param print Indicates the value of the print flag to be set on the new record.
     * @param postingQuantity Indicates the quantity selection value to be set on the new record.
     * @param documentType Indicates the document type of the newly created post setup.
     * @param purchaseOrderQuery Query used to select the purchase orders to be included in this post.
     * @param userData User data.
     * @return The POPPurchasePostSetup table instance that has been created.
     */
    public POPPurchasePostSetupTable setupNewPost(boolean post, boolean print, PostingQuantities postingQuantity, DocumentTypes documentType, EMCQuery purchaseOrderQuery, EMCUserData userData) {
        POPPurchasePostSetupTable setup = new POPPurchasePostSetupTable();
        setup.setCompanyId(util.getCompanyId(POPPurchasePostSetupTable.class.getName(), userData));
        setup.setCreatedBy(userData.getUserName());

        setup.setPost(post);
        setup.setPrint(print);
        setup.setQuantitySelection(postingQuantity.toString());
        setup.setDocumentType(documentType.toString());

        //Insert the setup
        try {
            insert(setup, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to create post setup", userData);
        }

        try {
            postMasterBean.createPost(purchaseOrderQuery.toString(), setup, false, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not create post", userData);
        }

        return setup;
    }
}
