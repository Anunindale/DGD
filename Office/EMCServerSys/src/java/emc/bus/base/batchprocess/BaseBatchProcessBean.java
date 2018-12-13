/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.batchprocess;

import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.commands.EMCCommands;
import emc.entity.base.batchprocess.BaseBatchProcess;
import emc.enums.base.batchprocess.BatchProcessStatus;
import emc.enums.enumLogTypes;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCLogRecord;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.workflow.ClientWorkFlowMethods;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author rico
 */
@Stateless
public class BaseBatchProcessBean extends EMCEntityBean implements BaseBatchProcessLocal {

    @EJB
    private EMCCommandManagerLocal managerBean;
    @EJB
    private BaseBatchProcessLocal localBean;
    @EJB
    private OnlineUsersLocal onlineBean;
    private static List<String> sourceList = Collections.synchronizedList(new ArrayList<String>());
    private static List<Long> currentProcess = Collections.synchronizedList(new ArrayList<Long>());
    private static Map<String, Integer> runningBatch = Collections.synchronizedMap(new HashMap<String, Integer>());

    @Override
    public List addBatchProcess(EMCCommandClass cmd, List<Object> data, EMCUserData userData) throws EMCEntityBeanException {
//        if (isBlank(cmd.getParameter1()) || isBlank(cmd.getParameter2()) || isBlank(cmd.getParameter3()) || isBlank(cmd.getParameter4())) {
//            /*
//             * The following needs to be set in the CMD Parameter Fields
//             * 1 - Source Table Name
//             * 2 - Source Field Name
//             * 3 - Source Field Value
//             * 4 - Source Record Version (After all updates to the source record has completed)
//             */
//            throw new EMCEntityBeanException("Source record information not set.");
//        }
        List ret = new ArrayList();
        checkSourceList(cmd.getParameter3());
        data.remove(0);
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        BaseBatchProcess process = new BaseBatchProcess();
        cmd.setCommandId(EMCCommands.SERVER_GENERAL_COMMAND.getId());
        process.setToDo(xmlHandler.toXML(cmd, data, userData));
        process.setCommand(cmd.toString());
        process.setStatus(BatchProcessStatus.WAITING.toString());
        process.setSourceTable(cmd.getParameter1());
        process.setSourceField(cmd.getParameter2());
        process.setSource(cmd.getParameter3());
        process.setSourceVersion(cmd.getParameter4() == null ? 0 : Long.valueOf(cmd.getParameter4()));
        process.setPriority(5);
        insert(process, userData);
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.BASE.getId());
        retCmd.setMethodId(ClientWorkFlowMethods.GENERAL_METHOD.toString());
        ret.add(0, retCmd);
        ret.add(true);
        this.logMessage(Level.INFO, "Action added to the Batch Queue.", userData);
        return ret;
    }

    private void checkSourceList(String source) throws EMCEntityBeanException {
        if (!isBlank(source)) {
            Iterator<String> it = sourceList.iterator();
            int size = sourceList.size();
            while (it.hasNext()) {
                String cur = it.next();
                if (source.equals(cur)) {
                    throw new EMCEntityBeanException("This Action has already been requested.");
                }
                if (!it.hasNext() && size >= 5) {
                    it.remove();
                }
            }
            sourceList.add(0, source);
        }
    }

    @Override
    public void runBatchProcess(String cmd, EMCUserData userData) {
        Integer batchProcessRunning = runningBatch.get(cmd);
        if (batchProcessRunning == null) {
            batchProcessRunning = 0;
        }
        if (batchProcessRunning > 0) {
            return;
        } else {
            batchProcessRunning++;
            runningBatch.put(cmd, batchProcessRunning);

            onlineBean.addMessageOnlySession(userData);

            EMCXMLHandler xmlHandler = new EMCXMLHandler();
            String msg;
            List messages;
            List<BaseBatchProcess> batchList;

            EMCQuery bq = new EMCQuery(enumQueryTypes.SELECT, BaseBatchProcess.class);
            bq.addAnd("status", BatchProcessStatus.WAITING.toString());
            bq.addAnd("command", cmd);
            bq.addOrderBy("priority");
            bq.addOrderBy("createdDate");
            bq.addOrderBy("createdTime");

            batchList = util.executeGeneralSelectQueryLimit(bq.toString(), userData, 0, 1);

            util.detachEntities(userData);

            boolean checkVersion;
            Long currentVersion = null;

            for (BaseBatchProcess batch : batchList) {
                try {
                    currentProcess.add(batch.getRecordID());
                    
                    if (!isBlank(batch.getSourceTable())) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, batch.getSourceTable());
                        query.addAnd(batch.getSourceField(), batch.getSource());
                        query.addField("version");
                        currentVersion = (Long) util.executeSingleResultQuery(query, userData);

                        if (currentVersion == null) {
                            currentVersion = -1L;
                        }
                        checkVersion = true;
                    } else {
                        checkVersion = false;
                    }
                    batch.setStartProcessing(new Date(System.currentTimeMillis()));
                    if (!checkVersion || currentVersion == batch.getSourceVersion()) {

                        userData.setUserName(batch.getCreatedBy());

                        if (localBean.executeTheBatch(batch.getToDo(), userData)) {
                            batch.setStatus(BatchProcessStatus.COMPLETE.toString());
                        } else {
                            batch.setStatus(BatchProcessStatus.ERROR.toString());
                        }


                    } else {

                        Logger.getLogger("emc").log(Level.SEVERE, "Source record has been updated since it was added to the batch queue.", userData);

                        batch.setStatus(BatchProcessStatus.ERROR.toString());

                    }
                    batch.setEndProcessing(new Date(System.currentTimeMillis()));
                    messages = onlineBean.getMessages(userData);
                    msg = xmlHandler.ObjectToXML(messages, userData);
                    batch.setMessages(msg);

                    batch = (BaseBatchProcess) localBean.updateInNewTx(batch, userData);

                    currentProcess.remove(batch.getRecordID());

                    sourceList.remove(batch.getSource());

                } catch (Exception e) {
                    System.out.println("Exception occured while processing batch recordId:" + batch.getRecordID() + " " + e.getMessage());
                }
                batchList = null;
            }

            onlineBean.removeMessageOnlyUser(userData);

            batchProcessRunning--;
            runningBatch.put(cmd, batchProcessRunning);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean executeTheBatch(String toDo, EMCUserData userData) {
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        String result = managerBean.executeCommand(toDo, userData);
        List theDataList = xmlHandler.parseXML(result, userData);
        EMCCommandClass cmd = (EMCCommandClass) theDataList.get(0);
        if (EMCCommands.fromId(cmd.getCommandId()).equals(EMCCommands.SERVER_EXCEPTION_OCCURRED)) {
            //log here?
            return false;
        }
        return true;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateInNewTx(BaseBatchProcess record, EMCUserData userData) throws EMCEntityBeanException {
        doUpdate(record, userData);

        util.refreshEntity(record, userData);

        return record;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid) {
            BaseBatchProcess record = (BaseBatchProcess) vobject;
            if (!isBlank(record.getSource())) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseBatchProcess.class);
                query.addAnd("source", record.getSource());
                query.openAndConditionBracket();
                query.addOr("status", BatchProcessStatus.WAITING);
                query.addOr("status", BatchProcessStatus.PROCESSING);
                query.closeConditionBracket();
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "A batch processing job already exists for the source record " + record.getSource(), userData);
                    valid = false;
                }
            }
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            BaseBatchProcess record = (BaseBatchProcess) vobject;
            if (currentProcess.contains(record.getRecordID())) {
                Logger.getLogger("emc").log(Level.SEVERE, "The selected record is currently being processed.", userData);
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public void logBatchRecordMessages(long recordID, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseBatchProcess.class);
        query.addAnd("recordID", recordID);
        BaseBatchProcess batchRecord = (BaseBatchProcess) util.executeSingleResultQuery(query, userData);
        if (batchRecord == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Could not find batch processing record.", userData);
            return;
        }
        List messages = (List) new EMCXMLHandler().XMLToObject(batchRecord.getMessages(), userData);
        if (messages == null || messages.isEmpty()) {
            Logger.getLogger("emc").log(Level.INFO, "Selected batch processing record has no messages.", userData);
            return;
        }
        EMCLogRecord message;
        for (Object o : messages) {
            message = (EMCLogRecord) o;
            Logger.getLogger("emc").log(message.getLevel().equals(enumLogTypes.ERROR.toString()) ? Level.SEVERE : Level.parse(message.getLevel()), message.getMessage(), userData);
        }
    }
}
