/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.numbersequences;

import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.functions.BitArray;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseNumberSequenceLogicBean extends EMCBusinessBean implements BaseNumberSequenceLogicLocal {

    @EJB
    private BaseNumberSequenceLocal numberSequenceBean;
    @EJB
    private NumberSequenceConstantsLocal constants;
    @EJB
    private BaseAvailableSequenceNumbersLocal availableNumbersBean;
    @EJB
    private EMCDateHandlerLocal dateBean;
    private static Date lastRunDate;

    public BaseNumberSequenceLogicBean() {
    }

    /**
     * This method is used to request the next number in a number sequence
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getNextAvailableSequenceNumber(String refTable, String refField, Object record, EMCUserData userData) {
        //default return values are a arraylist with null in pos 0,1
        List retList = new ArrayList();
        retList.add(0, null);
        retList.add(1, null);
        try {
            BaseAvailableSequenceNumbers number = null;
            BaseNumberSequence numberSequence = null;
            BaseNumberSequence defaultSequence = null;
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseNumberSequence.class);
            query.addAnd("refTable", refTable);
            query.addAnd("refField", refField);
            query.setNativeForUpdate(true);

            EMCTable theRocord = (EMCTable) record;
            List<BaseNumberSequence> list = (List<BaseNumberSequence>) util.executeNativeQuery(query, BaseNumberSequence.class, userData);
            for (BaseNumberSequence sqlRecord : list) {
                if (isBlank(sqlRecord.getPerField())) {
                    defaultSequence = sqlRecord;
                } else if (theRocord.getValueForFieldInEntityObject(sqlRecord.getPerField(), theRocord.getClass(), theRocord).equals(sqlRecord.getPerValue())) {
                    numberSequence = sqlRecord;
                }
            }
            if (numberSequence == null) {
                numberSequence = defaultSequence;
            }
            if (numberSequence == null) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No sequence number id found", userData);
                }
                return retList;
            }
            //only select numbers with status of 0
            query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class);
            query.addAnd("sequenceNumberId", numberSequence.getNumberSequenceId());
            query.addAnd("status", 0);
            query.setNativeForUpdate(true);

            List<BaseAvailableSequenceNumbers> availableNumbers = util.executeNativeQuery(query, BaseAvailableSequenceNumbers.class, userData);

            if (availableNumbers == null || availableNumbers.size() == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "Sequence numbers for sequence number id: " + numberSequence.getNumberSequenceId() + " are exhausted", userData);
                return retList;
            }
            //find the number seq based on the user session
            int toFind = 0;
            //honour force seq
            /*if(!numberSequence.isForceSequence()){
            if (userData.getSessionID() < availableNumbers.size()) {
            toFind = (int) userData.getSessionID();
            } else {
            toFind = (int) (userData.getSessionID() % (availableNumbers.size()));
            }
            //safety to avoid index out of bounds but should not occur
            if (toFind >= availableNumbers.size()) {
            toFind = availableNumbers.size() - 1;
            }
            }*/
            number = ((BaseAvailableSequenceNumbers) availableNumbers.get(toFind));
            //Check whether more numbers are available
            if (availableNumbers.size() < 10) {
                generateNextNumber(numberSequence, userData);
            }

            String retNumber = number.getSequenceNumber();
            retList.add(0, retNumber);
            retList.add(1, number.getRecordID());
            //release suspence numbers not used from yesterday and earlier only if day moved on
            //this doen not worry about time - should be OK for now but could impact if there is activity arround midnight
            //now only clear up at startup
            if (lastRunDate == null) { //|| dateBean.compareDatesIgnoreTime(lastRunDate, dateBean.nowDate(), userData) < 0) {
                lastRunDate = dateBean.nowDate();
                EMCQuery relq = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                relq.addSet("status", 0); //set to free
                relq.addAnd("status", 1);
                //Date x = new Date();
                //x.setTime(dateBean.addToDate(dateBean.nowDate(), -1));
                //relq.addAnd("modifiedDate", x, EMCQueryConditions.LESS_THAN_EQ);
                util.executeUpdate(relq, userData);
                //delete used ones
                EMCQuery delq = new EMCQuery(enumQueryTypes.DELETE, BaseAvailableSequenceNumbers.class);
                delq.addAnd("status", 2);
                util.executeUpdate(delq, userData);
            }
            //Update Number Seq Status
            number.setStatus(1);
            availableNumbersBean.update(number, userData);

            return retList;
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to set number status.", userData);
            }
            return retList;
        }
    }

    /**
     * This method is used to check if a sequence is set up for a given table and field
     */
    public boolean sequenceExists(String refTable, String refField, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, "emc.entity.base.numbersequences.BaseNumberSequence");
        query.addAnd("refTable", refTable);
        query.addAnd("refField", refField);

        BaseNumberSequence numberSequence = (BaseNumberSequence) util.executeSingleResultQuery(query, userData);

        if (numberSequence == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method is used to return a number in a number sequence (Add it to the available numbers)
     */
    public void returnSeqNum(String refTable, String refField, String seqNum, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, "emc.entity.base.numbersequences.BaseNumberSequence");
        query.addAnd("refTable", refTable);
        query.addAnd("refField", refField);
        query.addField("numberSequenceId");

        String numberSequenceId = (String) util.executeSingleResultQuery(query, userData);

        if (numberSequenceId == null) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "No sequence setup for " + refField + " on " + refTable, userData);
            }
        } else {
            try {
                //Create new available number record
                String companyId = util.getCompanyId("emc.entity.base.numbersequences.BaseAvailableSequenceNumbers", userData);
                BaseAvailableSequenceNumbers availableNumber = new BaseAvailableSequenceNumbers();
                availableNumber.setSequenceNumberId(numberSequenceId);
                availableNumber.setCompanyId(companyId);
                availableNumber.setSequenceNumber(seqNum);
                availableNumbersBean.insert(availableNumber, userData);
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to return seq number", userData);
                }
            }
        }
    }

    /** 
     * This methods is used to check whether manual entry is allowed for a sequence number
     */
    public boolean isManualEntryAllowed(String refTable, String refField, Object record, EMCUserData userData) {
        BaseNumberSequence numberSequence = null;
        BaseNumberSequence defaultSequence = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, "emc.entity.base.numbersequences.BaseNumberSequence");
        query.addAnd("refTable", refTable);
        query.addAnd("refField", refField);
        EMCTable theRocord = (EMCTable) record;
        List<BaseNumberSequence> list = (List<BaseNumberSequence>) util.executeGeneralSelectQuery(query, userData);
        Object value;
        for (BaseNumberSequence sqlRecord : list) {
            if (isBlank(sqlRecord.getPerField())) {
                defaultSequence = sqlRecord;
            } else {
                value = theRocord.getValueForFieldInEntityObject(sqlRecord.getPerField(), theRocord.getClass(), theRocord);
                if (value != null && value.equals(sqlRecord.getPerValue())) {
                    numberSequence = sqlRecord;
                }
            }
        }
        if (numberSequence == null) {
            numberSequence = defaultSequence;
        }
        if (numberSequence != null) {
            return numberSequence.getAllowManualEntry();
        } else {
            return true;
        }
    }

    /**
     * This method is used to generate the next number in a sequence
     * It returns an updated BaseNumberSequence record
     * This method should preferably not be called from outside without a good reason.
     * If no numbers are available -999 is used.
     */
    public Object generateNextNumber(BaseNumberSequence numberSequence, EMCUserData userData) {
        try {
            long nextNumber = numberSequence.getNextAvailableNumber();

            if (nextNumber >= numberSequence.getMaxNumber() || nextNumber == constants.getNumSeqFlag()) {
                nextNumber = constants.getNumSeqFlag();
            } else {
                try {
                    nextNumber++;
                    long tempNextNum = nextNumber;

                    //Create new available number record
                    BaseAvailableSequenceNumbers availableNumber = new BaseAvailableSequenceNumbers();
                    availableNumber.setSequenceNumberId(numberSequence.getNumberSequenceId());
                    availableNumber.setSequenceNumber(createNumberWithLeadingZeroes(numberSequence, tempNextNum, userData));
                    availableNumbersBean.insert(availableNumber, userData);
                } catch (EMCEntityBeanException ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to generate nextNumber", userData);
                    }
                }
            }

            numberSequence.setNextAvailableNumber(nextNumber);
            return numberSequenceBean.update(numberSequence, userData);
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to update next seq number", userData);
            }
            return numberSequence;
        }
    }

    /**
     * This method is used to add leading zeroes to a number
     */
    private String createNumberWithLeadingZeroes(BaseNumberSequence numSeq, long nextNumber, EMCUserData userData) {
        if (numSeq.isRandom()) {
            int maxNumber = new Long(numSeq.getMaxNumber()).intValue();
            BitArray bitArray = new BitArray(numSeq.getRandomNumbers(), 0, maxNumber);
            Random randomGenerator = new Random();
            //generate a random number in the range required
            int randomInt = randomGenerator.nextInt(maxNumber); //seqLength -1?
            //find the first 0 bit after randomInt
            boolean wrapped = false;//possible endless loop here if outer loop where to try and get more numbers than length
            for (int i = randomInt; i < maxNumber; i++) {
                if (bitArray.get(i) == 0) {
                    nextNumber = i;
                    //flip bit i in Array
                    bitArray.set(i);
                    break;
                } else if ((i == maxNumber - 1) && (wrapped == false)) {
                    i = 0; //wrap arround
                    wrapped = true;
                }
            }
            numSeq.setRandomNumbers(bitArray.toBytes());
        }
        int zeroesToAdd = (numSeq.getMaxNumber() + "").length() - (nextNumber + "").length();
        String zeroes = "";
        for (int i = 0; i < zeroesToAdd; i++) {
            zeroes += "0";
        }
        return (numSeq.getPrefixString() == null ? "" : numSeq.getPrefixString()) + zeroes + nextNumber + (numSeq.getSuffixString() == null ? "" : numSeq.getSuffixString());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<List> getNextAvailableSequenceNumber(String refTable, String refField, Object record, int increaseBy, EMCUserData userData) {
        //default return values are a arraylist with null in pos 0,1
        List retList = new ArrayList();
        try {
            BaseAvailableSequenceNumbers number = null;
            BaseNumberSequence numberSequence = null;
            BaseNumberSequence defaultSequence = null;
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseNumberSequence.class);
            query.addAnd("refTable", refTable);
            query.addAnd("refField", refField);
            EMCTable theRocord = (EMCTable) record;
            List<BaseNumberSequence> list = (List<BaseNumberSequence>) util.executeGeneralSelectQuery(query, userData);
            for (BaseNumberSequence sqlRecord : list) {
                if (isBlank(sqlRecord.getPerField())) {
                    defaultSequence = sqlRecord;
                } else if (theRocord.getValueForFieldInEntityObject(sqlRecord.getPerField(), theRocord.getClass(), theRocord).equals(sqlRecord.getPerValue())) {
                    numberSequence = sqlRecord;
                }
            }
            if (numberSequence == null) {
                numberSequence = defaultSequence;
            }
            if (numberSequence == null) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "No sequence number id found", userData);
                }
                return retList;
            }
            //only select numbers with status of 0
            query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class);
            query.addAnd("sequenceNumberId", numberSequence.getNumberSequenceId());
            query.addAnd("status", 0);

            long available = (Long) util.executeSingleResultQuery(query.getCountQuery(), userData);

            while (increaseBy + 10 > available) {
                generateNextNumber(numberSequence, userData);
                available++;
            }

            query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class);
            query.addAnd("sequenceNumberId", numberSequence.getNumberSequenceId());
            query.addAnd("status", 0);

            List<BaseAvailableSequenceNumbers> availableNumbers = util.executeGeneralSelectQuery(query, userData);

            if (availableNumbers == null || availableNumbers.size() < increaseBy) {
                Logger.getLogger("emc").log(Level.SEVERE, "Sequence numbers for sequence number id: " + numberSequence.getNumberSequenceId() + " are exhausted", userData);
                return retList;
            }

            List<String> numberList = new ArrayList<String>();
            List<Long> recordList = new ArrayList<Long>();
            for (int i = 0; i < increaseBy; i++) {
                number = availableNumbers.get(i);

                number.setStatus(1);
                availableNumbersBean.update(number, userData);

                numberList.add(number.getSequenceNumber());
                recordList.add(number.getRecordID());
            }
            retList.add(numberList);
            retList.add(recordList);

            //release suspence numbers not used from yesterday and earlier only if day moved on
            //this doen not worry about time - should be OK for now but could impact if there is activity arround midnight
            if (lastRunDate == null || dateBean.compareDatesIgnoreTime(lastRunDate, dateBean.nowDate(), userData) < 0) {
                lastRunDate = dateBean.nowDate();
                EMCQuery relq = new EMCQuery(enumQueryTypes.UPDATE, BaseAvailableSequenceNumbers.class);
                relq.addSet("status", 0); //set to free
                relq.addAnd("status", 1);
                Date x = new Date();
                x.setTime(dateBean.addToDate(dateBean.nowDate(), -1));
                relq.addAnd("modifiedDate", x, EMCQueryConditions.LESS_THAN_EQ);
                util.executeUpdate(relq, userData);
                //delete used ones
                EMCQuery delq = new EMCQuery(enumQueryTypes.DELETE, BaseAvailableSequenceNumbers.class);
                delq.addAnd("status", 2);
                util.executeUpdate(delq, userData);
            }
        } catch (EMCEntityBeanException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to set number status.", userData);
            }
        }
        return retList;
    }
}
