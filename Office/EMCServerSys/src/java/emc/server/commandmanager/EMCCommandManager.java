/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager;

import emc.bus.base.batchprocess.BaseBatchProcessLocal;
import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.bus.base.reporttools.BaseReportPrintOptionsLocal;
import emc.commands.EMCCommands;
import emc.constants.systemConstants;
import emc.entity.base.datasource.EMCTransactions;
import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.servertransactions.BaseServerTransactionsLog;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCLogRecord;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.functions.xml.external.EMCExternalWSXMLException;
import emc.functions.xml.external.EMCExternalWSXMLHandler;
import emc.reporttools.EMCReportConfig;
import emc.reporttools.ReportEnumInterface;
import emc.server.commandmanager.base.BaseMethodMapperBeanLocal;
import emc.server.commandmanager.creditors.CreditorsMethodMapperBeanLocal;
import emc.server.commandmanager.crm.CRMMethodMapperBeanLocal;
import emc.server.commandmanager.dangerousgoods.DGMethodMapperBeanLocal;
import emc.server.commandmanager.debtors.DebtorsMethodMapperBeanLocal;
import emc.server.commandmanager.developertools.DeveloperToolsMethodMapperBeanLocal;
import emc.server.commandmanager.gl.GLMethodMapperBeanLocal;
import emc.server.commandmanager.hr.HRMethodMapperBeanLocal;
import emc.server.commandmanager.inventory.InventoryMethodMapperBeanLocal;
import emc.server.commandmanager.pop.POPMethodMapperBeanLocal;
import emc.server.commandmanager.sop.SOPMethodMapperBeanLocal;
import emc.server.commandmanager.trec.TRECMethodMapperBeanLocal;
import emc.server.commandmanager.workflow.WorkFlowMethodMapperBeanLocal;
import emc.server.datehandler.EMCDateHandlerLocal;
import emc.server.preupdel.EMCPreUpdateDeleteLocal;
import emc.server.utility.EMCServerUtilityLocal;
import emc.tables.EMCTable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author rico
 */
@Stateless
public class EMCCommandManager implements EMCCommandManagerLocal {

    @EJB
    private GLMethodMapperBeanLocal gLMethodMapperBean;
    @EJB
    private CreditorsMethodMapperBeanLocal creditorsMethodMapperBean;
    @EJB
    private WorkFlowMethodMapperBeanLocal workFlowMethodMapperBean;
    @EJB
    private BaseMethodMapperBeanLocal baseMethodMapperBean;
    @EJB
    private InventoryMethodMapperBeanLocal inventoryMethodMapperBean;
    @EJB
    private DeveloperToolsMethodMapperBeanLocal developerToolsMethodMapperBean;
    @EJB
    private EMCPreUpdateDeleteLocal preUpdateDelete;
    @EJB
    private DebtorsMethodMapperBeanLocal debtorsMethodMapperBean;
    @EJB
    private POPMethodMapperBeanLocal popMethodMapperBean;
    @EJB
    private HRMethodMapperBeanLocal HRMethodMapperBean;
    @EJB
    private CRMMethodMapperBeanLocal CRMMethodMapperBean;
    @EJB
    private SOPMethodMapperBeanLocal SOPMethodMapperBean;
    @EJB
    private TRECMethodMapperBeanLocal TRECMethodMapperBean;
    @EJB
    private DGMethodMapperBeanLocal dgMethodMapperBean;
    @EJB
    private BaseBatchProcessLocal batchProcessBean;
    @PersistenceContext
    private EntityManager entityMan;
    @Resource
    private EJBContext context;
    @Resource
    private TransactionSynchronizationRegistry registry;
    @EJB
    private EMCServerUtilityLocal util;
    @EJB
    private EMCDateHandlerLocal dateHandler;
    @EJB
    private EMCCommandManagerLocal localBean;
    @EJB
    private OnlineUsersLocal onlineUsersBean;
    @EJB
    private BaseReportPrintOptionsLocal reportPrintOptionsBean;
    //
    private static Map<Object, EMCTransactions> emcTransactions;
    private static Map<Object, List<String>> transactionFailQueries;
    private static Map<Object, List<String>> transactionSucceedQueries;
    private static List<BaseServerTransactionsLog> transactionLog;
    private static boolean logTransactions;
    private static Date clearLogDate;

    public EMCCommandManager() {
        if (transactionFailQueries == null) {
            transactionFailQueries = Collections.synchronizedMap(new HashMap<Object, List<String>>());
        }
        if (emcTransactions == null) {
            emcTransactions = Collections.synchronizedMap(new HashMap<Object, EMCTransactions>());
        }
        if (transactionSucceedQueries == null) {
            transactionSucceedQueries = Collections.synchronizedMap(new HashMap<Object, List<String>>());
        }
        if (transactionLog == null) {
            transactionLog = Collections.synchronizedList(new ArrayList<BaseServerTransactionsLog>());
            logTransactions = true;
            clearLogDate = null;
        }
    }

    /**
     * Used to map the WS command recieved. Does use compression.
     *
     * @param toDo
     * @param userData
     * @return
     */
    public byte[] executeCommandByte(byte[] toDo, EMCUserData userData) {
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        String ret = "";
        String command = xmlHandler.decompress(toDo, userData);
        ret = executeCommand(command, userData);
        byte[] result = xmlHandler.compress(ret, userData);
        return result;
    }

    /**
     * Used to map the WS command recieved. Does not use compression.
     *
     * @param toDo
     * @param userData
     * @return
     */
    public String executeCommand(String toDo, EMCUserData userData) {
        boolean addedTrans = false;
        EMCCommandClass cmd = new EMCCommandClass();
        EMCCommandClass retCmd = new EMCCommandClass();
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        EMCExternalWSXMLHandler externalXMLHandler = new EMCExternalWSXMLHandler();

        List<Object> theDataList = new ArrayList();
        List<Object> retList = new ArrayList();
        String ret = "";
        theDataList = null;

        boolean externalCall = false;

        try {
            //Hardcoded check to determine whether server call is coming from an external source.
            if (toDo.startsWith("<emcwscall source='external'")) {
                externalCall = true;
                onlineUsersBean.addMessageOnlySession(userData);
                theDataList = externalXMLHandler.parseRequestXML(toDo);
            } else {
                theDataList = xmlHandler.parseXML(toDo, userData);
            }

            cmd = (EMCCommandClass) theDataList.get(0);
            if (!(cmd.getMethodId().equals("CHECKIN") || cmd.getMethodId().equals("GET_MESSAGES") || cmd.getMethodId().contains("VALIDATEFIELD") || cmd.getMethodId().contains("GETNUMROWS"))) {
                addTransaction(registry.getTransactionKey(), cmd.toString(), userData);
                addedTrans = true;
            }
            retList = mapCommand(cmd, theDataList, userData);
            retCmd = (EMCCommandClass) retList.get(0);
            if (cmd.getReportConfig() != null) {
                EMCReportConfig config = cmd.getReportConfig();
                config.setDirectionFrom(ReportEnumInterface.FROM_SERVER);
                retCmd.setReportConfig(config);
            }
            retList.remove(0);
            if (!externalCall) {
                //Results for external XML calls will only be parsed at the end of this method.
                ret = xmlHandler.toXML(retCmd, retList, userData);
            }

            if (context.getRollbackOnly()) {
                //throw new EJBException("Transaction was rolled back...");
            } else {
                entityMan.flush();
                //now update queries if any
                List<String> qToEx = getTransactionSucceedQueries(registry.getTransactionKey());
                if (qToEx != null) {
                    for (String curQ : qToEx) {
                        //System.out.println(curQ);
                        entityMan.createQuery(curQ).executeUpdate();
                    }
                }
                entityMan.clear();
                getTransactionFailQueries(registry.getTransactionKey()); //clear fail queries if any
            }
        } catch (Exception ex) {

            if (!(cmd.getMethodId().equals("CHECKIN") || cmd.getMethodId().equals("GET_MESSAGES"))) {
                System.out.println("IN=" + registry.getTransactionKey() + "|" + cmd + "|" + userData);
            }

            ret = handleException(ex, cmd, userData);

            if (!(cmd.getMethodId().equals("CHECKIN") || cmd.getMethodId().equals("GET_MESSAGES"))) {
                System.out.println("OUT=" + registry.getTransactionKey() + "|" + cmd + "|" + userData);
            }
        }
        if (context.getRollbackOnly()) {
            getTransactionSucceedQueries(registry.getTransactionKey()); //clear succeed queries if any
            //run failure queries if any
            List<String> qToEx = getTransactionFailQueries(registry.getTransactionKey());
            if (qToEx != null) {
                localBean.executeFailQueries(qToEx);
            }
            ///
        }
        if (addedTrans) {
            removeTransaction(registry.getTransactionKey(), userData);
        }

        if (externalCall) {
            try {
                List<EMCLogRecord> logMessages = onlineUsersBean.getMessages(userData);
                ret = externalXMLHandler.generateResponseXML(retList, logMessages);
            } catch (EMCExternalWSXMLException ex) {
                context.setRollbackOnly();  //Rollback transaction

                //Log message for client based on exception
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to " + cmd.getMethodId() + ", Reason:" + mapException(ex), userData);
                getTransactionSucceedQueries(registry.getTransactionKey()); //clear succeed queries if any
                //run failure queries if any
                List<String> qToEx = getTransactionFailQueries(registry.getTransactionKey());
                if (qToEx != null) {
                    localBean.executeFailQueries(qToEx);
                }
            } finally {
                //Always release session
                onlineUsersBean.removeMessageOnlyUser(userData);
            }
        }
        return ret;
    }

    private String handleException(Exception ex, EMCCommandClass cmd, EMCUserData userData) {
        EMCXMLHandler xmlHandler = new EMCXMLHandler();
        EMCCommandClass retCmd = new EMCCommandClass();
        context.setRollbackOnly();

        //Log message for client based on exception
        Logger.getLogger("emc").log(Level.SEVERE, "Failed to " + cmd.getMethodId() + ", Reason:" + mapException(ex), userData);

        retCmd.setCommandId(EMCCommands.SERVER_EXCEPTION_OCCURRED.getId());
        retCmd.setModuleNumber(cmd.getModuleNumber());
        retCmd.setMethodId(cmd.getMethodId());
        return xmlHandler.toXML(retCmd, new ArrayList(), userData);
    }

    private String mapException(Exception ex) {
        ex.printStackTrace();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String whatHappend = sw.toString();
        int pos = whatHappend.indexOf("Caused by:");
        while (pos != -1) {
            whatHappend = whatHappend.substring(pos + 10);
            pos = whatHappend.indexOf("Caused by:");
        }
        pos = whatHappend.indexOf("at ");
        int startPos = whatHappend.indexOf("Exception:");
        if ((pos > -1) && (startPos > -1)) {
            whatHappend = whatHappend.substring(startPos + 10, pos - 2);
        }
        return whatHappend;
    }

    public List mapCommand(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> retList = null;
        
        switch (EMCCommands.fromId(cmd.getCommandId())) {
            case SERVER_GENERAL_COMMAND:
                retList = mapModule(cmd, dataList, userData);
                break;
            case PRE_UPDATE:
                retList = preUpdate(cmd, dataList, userData);
                break;
            case PRE_DELETE:
                retList = preDelete(cmd, dataList, userData);
                break;
            case REPORT_COMAND:
                retList = mapReportToModule(cmd, dataList, userData);
                break;
            case BATCH_PROCESS:
                retList = batchProcessBean.addBatchProcess(cmd, dataList, userData);
                break;
            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Command not found", userData);
                }
                break;
        }
        return retList;
    }

    private List preDelete(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        try {
            EMCXMLHandler xmlHandler = new EMCXMLHandler();
            EMCTable theTable = (EMCTable) dataList.get(1);
            String tree = xmlHandler.encodeTreeOld(preUpdateDelete.testRelations(enumPersistOptions.DELETE, theTable, userData));
            List<Object> theDataList = new ArrayList();
            EMCCommandClass retCmd = new EMCCommandClass();
            retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
            retCmd.setModuleNumber(cmd.getModuleNumber());
            retCmd.setMethodId(cmd.getMethodId());
            theDataList.add(retCmd);
            theDataList.add(tree);
            return theDataList;

        } catch (Exception ex) {
            throw new EMCEntityBeanException("Failed to do Delete Test");
        }
    }

    private List preUpdate(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        try {
            EMCXMLHandler xmlHandler = new EMCXMLHandler();
            EMCTable theTable = (EMCTable) dataList.get(1);
            String tree = xmlHandler.encodeTreeOld(preUpdateDelete.testRelations(enumPersistOptions.UPDATE, theTable, userData));
            List<Object> theDataList = new ArrayList();
            EMCCommandClass retCmd = new EMCCommandClass();
            retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
            retCmd.setModuleNumber(cmd.getModuleNumber());
            retCmd.setMethodId(cmd.getMethodId());
            theDataList.add(retCmd);
            theDataList.add(tree);
            return theDataList;
        } catch (Exception ex) {
            throw new EMCEntityBeanException("Failed to do Update Test");
        }
    }

    private List mapModule(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> ret = null;
        switch (enumEMCModules.fromId(cmd.getModuleNumber())) {
            case BASE:
                ret = baseMethodMapperBean.mapMethodBase(cmd, dataList, userData);
                break;
            case WORKFLOW:
                ret = workFlowMethodMapperBean.mapMethodWorkFlow(cmd, dataList, userData);
                break;
            case CREDITORS:
                ret = creditorsMethodMapperBean.mapMethodCreditors(cmd, dataList, userData);
                break;
            case GENERAL_LEDGER:
                ret = gLMethodMapperBean.mapMethodGL(cmd, dataList, userData);
                break;
            case INVENTORY:
                ret = inventoryMethodMapperBean.mapMethodInventory(cmd, dataList, userData);
                break;
            case DEVELOPERTOOLS:
                ret = developerToolsMethodMapperBean.mapMethodDeveloperTools(cmd, dataList, userData);
                break;
            case DEBTORS:
                ret = debtorsMethodMapperBean.mapMethodDebtors(cmd, dataList, userData);
                break;
            case POP:
                ret = popMethodMapperBean.mapMethodPOP(cmd, dataList, userData);
                break;
            case HR:
                ret = HRMethodMapperBean.mapMethodHR(cmd, dataList, userData);
                break;
            case CRM:
                ret = CRMMethodMapperBean.mapMethodCRM(cmd, dataList, userData);
                break;
            case SOP:
                ret = SOPMethodMapperBean.mapMethodSOP(cmd, dataList, userData);
                break;
            case TREC:
                ret = TRECMethodMapperBean.mapMethodTREC(cmd, dataList, userData);
                break;
            case DG:
                ret = dgMethodMapperBean.mapMethodDangerousGoods(cmd, dataList, userData);
                break;
            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Module not found", userData);
                }
                break;
        }
        return ret;
    }

    private List mapReportToModule(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> ret = null;
        EMCQuery query = null;
        BaseReportPrintOptions specialOptions = null;
        BaseReportPrintOptions printOptions = null;

        ret = mapModule(cmd, dataList, userData);
        //special print instructions may return PrintOptions
        if (ret.size() > 1 && ret.get(1) instanceof BaseReportPrintOptions) {
            specialOptions = (BaseReportPrintOptions) ret.remove(1);
        }
        EMCReportConfig config = cmd.getReportConfig();
        String reportId = config.getReportId();

        if (emc.functions.Functions.checkBlank(reportId)) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "The report ID is not set in the EMCReportConfig!", userData);
            }
        } else {
            //Get user query name
            String queryName = config.getUserReportQueryName();

            //Special for post reports.  Blank String means no name, thus no update.
            if (queryName != null) {
                query = new EMCQuery(enumQueryTypes.UPDATE, BaseReportUserQueryTable.class.getName());
                query.addSet("lastExecTimestamp", System.currentTimeMillis());
                query.addAnd("createdBy", userData.getUserName());
                query.addAnd("userRecordName", queryName);
                query.addAnd("reportId", reportId);
                query.addAnd("companyId", userData.getCompanyId());

                util.executeUpdateInNewTransaction(query, userData);
            }

            printOptions = reportPrintOptionsBean.findPrinterOptions(reportId, queryName, userData);
        }

        if (specialOptions != null) {
            int numRows = ret.size() - 1;
            ret.add(1, numRows);
            ret.add(1, specialOptions);
        } else if (printOptions != null) {
            int numRows = ret.size() - 1;
            ret.add(1, numRows);
            ret.add(1, printOptions);
        }

        return ret;
    }

    private void addTransaction(Object trans, String command, EMCUserData userData) {
        EMCTransactions tran = new EMCTransactions();
        tran.setSessionNumber(userData.getSessionID());
        String id = Integer.toHexString(trans.hashCode());
        while (id.length() < 16) {
            id = "0" + id;
        }
        id = id + "_00";
        tran.setTransaction(id);
        tran.setFullStartDate(dateHandler.nowDate());
        tran.setTransactionStartDate(dateHandler.nowDateString(systemConstants.systemDateFormat()));
        tran.setTransactionStartTime(dateHandler.date2TimeStringSeconds(tran.getFullStartDate()));
        tran.setTransactionId(trans);
        tran.setCommand(command);
        tran.setUserId(userData.getUserName());

        emcTransactions.put(trans, tran);
    }
    //synchronized to avoid concurrent access exception

    private synchronized void logTransaction(EMCTransactions trans, EMCUserData userData) {
        Date cur = Functions.nowDate();
        trans.setFullEndDate(cur);
        trans.calcAndSetTransactionRunTime(cur.getTime());
        transactionLog.add(new BaseServerTransactionsLog(trans, context.getRollbackOnly() == false ? "SUCCESS" : "FAIL"));
        //store it if it exceed 100 and the transaction is not marked for rollback
        if (transactionLog.size() >= 100 && !context.getRollbackOnly()) {
            logTransactions = false;//stop logging while storing            
            //clear old messages should only happen once per day
            if (clearLogDate == null || Functions.calculateDayDiff(clearLogDate, Functions.nowDate()) > 0) {
                EMCQuery delQ = new EMCQuery(enumQueryTypes.DELETE, BaseServerTransactionsLog.class);
                delQ.addAnd("createdDate", Functions.date2SQLString(Functions.DateAddSub(Functions.nowDate(), -3)), EMCQueryConditions.LESS_THAN);
                util.executeUpdate(delQ, userData);
                clearLogDate = Functions.nowDate();
                System.out.println("Clear log");
            } else {//log the transactions
                util.insertDirect(BaseServerTransactionsLog.class, transactionLog, userData);
                transactionLog.clear();
            }
            logTransactions = true;//enable again
        }
    }

    private void removeTransaction(Object trans, EMCUserData userData) {
        EMCTransactions tran = emcTransactions.remove(trans);
        if (logTransactions) {
            logTransaction(tran, userData);
        }
    }

    public List getActiveTransactions() {
        return new ArrayList(emcTransactions.values());
    }

    /**
     * Removes the entry from the map
     *
     * @param txInfo
     * @return
     */
    private List<String> getTransactionFailQueries(Object txInfo) {
        return transactionFailQueries.remove(txInfo);
    }

    /**
     * Sets a query for the specific transaction
     *
     * @param txInfo
     * @param query
     */
    public void setTransactionFailQuery(Object txInfo, String query) {
        List<String> qList = transactionFailQueries.get(txInfo);
        if (qList == null) {
            qList = new ArrayList();
            transactionFailQueries.put(txInfo, qList);
        }
        qList.add(query);
    }

    /**
     * Only call this method internal to the Command manager
     *
     * @param qToEx
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executeFailQueries(List<String> qToEx) {
        for (String curQ : qToEx) {
            //System.out.println(curQ);
            entityMan.createQuery(curQ).executeUpdate();
        }
    }

    /**
     * Removes the entry from the map
     *
     * @param txInfo
     * @return
     */
    private List<String> getTransactionSucceedQueries(Object txInfo) {
        return transactionSucceedQueries.remove(txInfo);
    }

    /**
     * Sets a query for the specific transaction
     *
     * @param txInfo
     * @param query
     */
    public void setTransactionSucceedQuery(Object txInfo, String query) {
        List<String> qList = transactionSucceedQueries.get(txInfo);
        if (qList == null) {
            qList = new ArrayList();
            transactionSucceedQueries.put(txInfo, qList);
        }
        qList.add(query);
    }
}
