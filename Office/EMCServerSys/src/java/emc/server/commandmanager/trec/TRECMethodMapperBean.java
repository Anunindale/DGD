/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.trec;

import emc.bus.trec.cargocheck.TRECCargoCheckLinesLocal;
import emc.bus.trec.cargocheck.TRECCargoCheckMasterLocal;
import emc.bus.trec.chemicals.TRECChemicalsLocal;
import emc.bus.trec.classes.TRECClassesLocal;
import emc.bus.trec.colours.TRECColoursLocal;
import emc.bus.trec.customerchemicals.TRECCustomerChemicalsLocal;
import emc.bus.trec.erg.TRECErgMasterLocal;
import emc.bus.trec.forms.TRECFormsLocal;
import emc.bus.trec.loadcompatibility.TRECLoadCompatibilityLocal;
import emc.bus.trec.odours.TRECOdoursLocal;
import emc.bus.trec.parameters.TRECParametersLocal;
import emc.bus.trec.phrasecombinations.TRECPhraseCombinationsLocal;
import emc.bus.trec.phrases.TRECPhrasesLocal;
import emc.bus.trec.phrasetypes.TRECPhraseTypesLocal;
import emc.bus.trec.preferredshipname.TRECPreferredShipNameLocal;
import emc.bus.trec.treccards.TRECTrecCardsLinesLocal;
import emc.bus.trec.treccards.TRECTrecCardsMasterLocal;
import emc.bus.trec.trectypes.TRECTrecTypesLocal;
import emc.bus.trec.web.TRECWebOrderProcessLogicBeanLocal;
import emc.bus.trec.web.TRECWebPrinterLogicBeanLocal;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECColours;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECErgMaster;
import emc.entity.trec.TRECForms;
import emc.entity.trec.TRECLoadCompatibility;
import emc.entity.trec.TRECOdours;
import emc.entity.trec.TRECParameters;
import emc.entity.trec.TRECPhraseCombinations;
import emc.entity.trec.TRECPhraseTypes;
import emc.entity.trec.TRECPhrases;
import emc.entity.trec.TRECPreferredShipName;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import emc.entity.trec.TRECTrecTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECTypeEnum;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.helpers.trec.TRECWebInvoiceHelper;
import emc.helpers.trec.TRECWebOrderProcessHelper;
import emc.methods.trec.ClientTRECMethods;
import emc.methods.trec.ServerTRECMethods;
import emc.reports.trec.cargocheck.CargoCheckReportLocal;
import emc.reports.trec.customerselection.TRECProducedCardTemplateLocal;
import emc.reports.trec.ergphrases.TRECErgPhrasesLocal;
import emc.reports.trec.placard.TRECPlacardLocal;
import emc.reports.trec.treccard.TRECTrecCardLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class TRECMethodMapperBean implements TRECMethodMapperBeanLocal {

    @EJB
    private TRECColoursLocal coloursBean;
    @EJB
    private TRECErgMasterLocal ergBean;
    @EJB
    private TRECFormsLocal formsBean;
    @EJB
    private TRECOdoursLocal odoursBean;
    @EJB
    private TRECTrecCardsMasterLocal trecCardMasterBean;
    @EJB
    private TRECTrecCardsLinesLocal trecCardLinesBean;
    @EJB
    private TRECChemicalsLocal trecChemicalsBean;
    @EJB
    private TRECClassesLocal trecClassesBean;
    @EJB
    private TRECPhraseCombinationsLocal trecPhraseCombBean;
    @EJB
    private TRECPhraseTypesLocal trecPhraseTypesBean;
    @EJB
    private TRECPhrasesLocal trecPhrasesBean;
    @EJB
    private TRECTrecTypesLocal trecTypesBean;
    @EJB
    private TRECTrecCardLocal trecCardBean;
    @EJB
    private TRECParametersLocal paramBean;
    @EJB
    private TRECLoadCompatibilityLocal loadCompBean;
    @EJB
    private TRECCargoCheckMasterLocal cargoCheckMasterBean;
    @EJB
    private TRECCargoCheckLinesLocal cargoCheckLinesBean;
    @EJB
    private CargoCheckReportLocal cargoCheckReportBean;
    @EJB
    private TRECCustomerChemicalsLocal trecCustomerChemicalsBean;
    @EJB
    private TRECWebOrderProcessLogicBeanLocal trecWebLogicBean;
    @EJB
    private TRECWebPrinterLogicBeanLocal trecWebPrinterLogicBean;
    @EJB
    private TRECErgPhrasesLocal ergPhrasesReportBean;
    @EJB
    private TRECPreferredShipNameLocal preferredShipNameBean;
    @EJB
    private TRECProducedCardTemplateLocal trecCardTemplateBean;
    @EJB
    private TRECPlacardLocal placardBean;

    public TRECMethodMapperBean() {
    }

    public List mapMethodTREC(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.TREC.getId());
        retCmd.setMethodId(ClientTRECMethods.GENERAL_METHOD.toString());

        switch (ServerTRECMethods.fromString(cmd.getMethodId())) {
            //TRECColours
            case INSERT_TRECCOLOURS:
                theDataList.add(coloursBean.insert((TRECColours) dataList.get(1), userData));
                break;
            case UPDATE_TRECCOLOURS:
                theDataList.add(coloursBean.update((TRECColours) dataList.get(1), userData));
                break;
            case DELETE_TRECCOLOURS:
                theDataList.add(coloursBean.delete((TRECColours) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCOLOURS:
                theDataList.add(coloursBean.getNumRows(TRECColours.class, userData));
                break;
            case GETDATA_TRECCOLOURS:
                theDataList = (List<Object>) coloursBean.getDataInRange(TRECColours.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCOLOURS:
                theDataList.add(coloursBean.validateField(dataList.get(1).toString(), (TRECColours) dataList.get(2), userData));
                break;
            //TRECErgMaster
            case INSERT_TRECERGMASTER:
                theDataList.add(ergBean.insert((TRECErgMaster) dataList.get(1), userData));
                break;
            case UPDATE_TRECERGMASTER:
                theDataList.add(ergBean.update((TRECErgMaster) dataList.get(1), userData));
                break;
            case DELETE_TRECERGMASTER:
                theDataList.add(ergBean.delete((TRECErgMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECERGMASTER:
                theDataList.add(ergBean.getNumRows(TRECErgMaster.class, userData));
                break;
            case GETDATA_TRECERGMASTER:
                theDataList = (List<Object>) ergBean.getDataInRange(TRECErgMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECERGMASTER:
                theDataList.add(ergBean.validateField(dataList.get(1).toString(), (TRECErgMaster) dataList.get(2), userData));
                break;
            //TRECForms
            case INSERT_TRECFORMS:
                theDataList.add(formsBean.insert((TRECForms) dataList.get(1), userData));
                break;
            case UPDATE_TRECFORMS:
                theDataList.add(formsBean.update((TRECForms) dataList.get(1), userData));
                break;
            case DELETE_TRECFORMS:
                theDataList.add(formsBean.delete((TRECForms) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECFORMS:
                theDataList.add(formsBean.getNumRows(TRECForms.class, userData));
                break;
            case GETDATA_TRECFORMS:
                theDataList = (List<Object>) formsBean.getDataInRange(TRECForms.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECFORMS:
                theDataList.add(formsBean.validateField(dataList.get(1).toString(), (TRECForms) dataList.get(2), userData));
                break;
            //TRECOdours
            case INSERT_TRECODOURS:
                theDataList.add(odoursBean.insert((TRECOdours) dataList.get(1), userData));
                break;
            case UPDATE_TRECODOURS:
                theDataList.add(odoursBean.update((TRECOdours) dataList.get(1), userData));
                break;
            case DELETE_TRECODOURS:
                theDataList.add(odoursBean.delete((TRECOdours) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECODOURS:
                theDataList.add(odoursBean.getNumRows(TRECOdours.class, userData));
                break;
            case GETDATA_TRECODOURS:
                theDataList = (List<Object>) odoursBean.getDataInRange(TRECOdours.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECODOURS:
                theDataList.add(odoursBean.validateField(dataList.get(1).toString(), (TRECOdours) dataList.get(2), userData));
                break;
            //TRECTrecCardsMaster
            case INSERT_TRECTRECCARDSMASTER:
                theDataList.add(trecCardMasterBean.insert((TRECTrecCardsMaster) dataList.get(1), userData));
                break;
            case UPDATE_TRECTRECCARDSMASTER:
                theDataList.add(trecCardMasterBean.update((TRECTrecCardsMaster) dataList.get(1), userData));
                break;
            case DELETE_TRECTRECCARDSMASTER:
                theDataList.add(trecCardMasterBean.delete((TRECTrecCardsMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECTRECCARDSMASTER:
                theDataList.add(trecCardMasterBean.getNumRows(TRECTrecCardsMaster.class, userData));
                break;
            case GETDATA_TRECTRECCARDSMASTER:
                theDataList = (List<Object>) trecCardMasterBean.getDataInRange(TRECTrecCardsMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECTRECCARDSMASTER:
                theDataList.add(trecCardMasterBean.validateField(dataList.get(1).toString(), (TRECTrecCardsMaster) dataList.get(2), userData));
                break;
            case EXPORT_TREC_DATA:
                theDataList.add(trecCardMasterBean.exportTRECData(userData));
                break;
            case IMPORT_TREC_DATA:
                theDataList.add(trecCardMasterBean.importTRECData((List<String>) dataList.get(1), (Boolean) dataList.get(2), userData));
                break;
            //TRECTrecCardsLines
            case INSERT_TRECTRECCARDSLINES:
                theDataList.add(trecCardLinesBean.insert((TRECTrecCardsLines) dataList.get(1), userData));
                break;
            case UPDATE_TRECTRECCARDSLINES:
                theDataList.add(trecCardLinesBean.update((TRECTrecCardsLines) dataList.get(1), userData));
                break;
            case DELETE_TRECTRECCARDSLINES:
                theDataList.add(trecCardLinesBean.delete((TRECTrecCardsLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECTRECCARDSLINES:
                theDataList.add(trecCardLinesBean.getNumRows(TRECTrecCardsLines.class, userData));
                break;
            case GETDATA_TRECTRECCARDSLINES:
                theDataList = (List<Object>) trecCardLinesBean.getDataInRange(TRECTrecCardsLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECTRECCARDSLINES:
                theDataList.add(trecCardLinesBean.validateField(dataList.get(1).toString(), (TRECTrecCardsLines) dataList.get(2), userData));
                break;
            case PRINT_SINGLE_TREC:
                theDataList = trecCardBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_SINGLE_TREC_TEMPLATE:
                theDataList = trecCardTemplateBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case UPDATE_LINE_FIELDS:
                trecCardLinesBean.updateLinesFields((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData);
                break;
            case APPROVE_TREC:
                theDataList.add(trecCardLinesBean.approveTREC((Long) dataList.get(1), userData));
                break;
            case VALIDATE_PACKING_GROUP:
                theDataList.add(trecCardLinesBean.validatePackingGroup((TRECTrecCardsLines) dataList.get(1), userData));
                break;
            //TRECChemicals
            case INSERT_TRECCHEMICALS:
                theDataList.add(trecChemicalsBean.insert((TRECChemicals) dataList.get(1), userData));
                break;
            case UPDATE_TRECCHEMICALS:
                theDataList.add(trecChemicalsBean.update((TRECChemicals) dataList.get(1), userData));
                break;
            case DELETE_TRECCHEMICALS:
                theDataList.add(trecChemicalsBean.delete((TRECChemicals) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCHEMICALS:
                theDataList.add(trecChemicalsBean.getNumRows(TRECChemicals.class, userData));
                break;
            case GETDATA_TRECCHEMICALS:
                theDataList = (List<Object>) trecChemicalsBean.getDataInRange(TRECChemicals.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCHEMICALS:
                theDataList.add(trecChemicalsBean.validateField(dataList.get(1).toString(), (TRECChemicals) dataList.get(2), userData));
                break;
            case UPDATE_ENC_CHEMICALS:
                trecChemicalsBean.updateEncryptedFields((TRECChemicals) dataList.get(1), userData);
                break;
            case RESTRUCTURE_CHEMICALS:
                theDataList.add(trecChemicalsBean.restructureChemicals(userData));
                break;
            case FIX_CHEMICALS:
                trecChemicalsBean.fixChemicalPhrases(userData);
                break;
            case VALIDATE_CHEMICALS_PHRASE:
                theDataList.add(trecChemicalsBean.validatePhraseToBeAdded((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case CHECK_TREC_CHEMICALS:
                trecChemicalsBean.checkTRECChemicals(userData);
                break;
            //TRECClasses
            case INSERT_TRECCLASSES:
                theDataList.add(trecClassesBean.insert((TRECClasses) dataList.get(1), userData));
                break;
            case UPDATE_TRECCLASSES:
                theDataList.add(trecClassesBean.update((TRECClasses) dataList.get(1), userData));
                break;
            case DELETE_TRECCLASSES:
                theDataList.add(trecClassesBean.delete((TRECClasses) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCLASSES:
                theDataList.add(trecClassesBean.getNumRows(TRECClasses.class, userData));
                break;
            case GETDATA_TRECCLASSES:
                theDataList = (List<Object>) trecClassesBean.getDataInRange(TRECClasses.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCLASSES:
                theDataList.add(trecClassesBean.validateField(dataList.get(1).toString(), (TRECClasses) dataList.get(2), userData));
                break;
            //TRECPhraseCombinations
            case INSERT_TRECPHRASECOMBINATIONS:
                theDataList.add(trecPhraseCombBean.insert((TRECPhraseCombinations) dataList.get(1), userData));
                break;
            case UPDATE_TRECPHRASECOMBINATIONS:
                theDataList.add(trecPhraseCombBean.update((TRECPhraseCombinations) dataList.get(1), userData));
                break;
            case DELETE_TRECPHRASECOMBINATIONS:
                theDataList.add(trecPhraseCombBean.delete((TRECPhraseCombinations) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECPHRASECOMBINATIONS:
                theDataList.add(trecPhraseCombBean.getNumRows(TRECPhraseCombinations.class, userData));
                break;
            case GETDATA_TRECPHRASECOMBINATIONS:
                theDataList = (List<Object>) trecPhraseCombBean.getDataInRange(TRECPhraseCombinations.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECPHRASECOMBINATIONS:
                theDataList.add(trecPhraseCombBean.validateField(dataList.get(1).toString(), (TRECPhraseCombinations) dataList.get(2), userData));
                break;
            case UPDATE_ENC_PHRASECOMBINATIONS:
                trecPhraseCombBean.updateEncryptedFields((TRECPhraseCombinations) dataList.get(1), userData);
                break;
            //TRECPhraseTypes
            case INSERT_TRECPHRASETYPES:
                theDataList.add(trecPhraseTypesBean.insert((TRECPhraseTypes) dataList.get(1), userData));
                break;
            case UPDATE_TRECPHRASETYPES:
                theDataList.add(trecPhraseTypesBean.update((TRECPhraseTypes) dataList.get(1), userData));
                break;
            case DELETE_TRECPHRASETYPES:
                theDataList.add(trecPhraseTypesBean.delete((TRECPhraseTypes) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECPHRASETYPES:
                theDataList.add(trecPhraseTypesBean.getNumRows(TRECPhraseTypes.class, userData));
                break;
            case GETDATA_TRECPHRASETYPES:
                theDataList = (List<Object>) trecPhraseTypesBean.getDataInRange(TRECPhraseTypes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECPHRASETYPES:
                theDataList.add(trecPhraseTypesBean.validateField(dataList.get(1).toString(), (TRECPhraseTypes) dataList.get(2), userData));
                break;
            //TRECPhrases
            case INSERT_TRECPHRASES:
                theDataList.add(trecPhrasesBean.insert((TRECPhrases) dataList.get(1), userData));
                break;
            case UPDATE_TRECPHRASES:
                theDataList.add(trecPhrasesBean.update((TRECPhrases) dataList.get(1), userData));
                break;
            case DELETE_TRECPHRASES:
                theDataList.add(trecPhrasesBean.delete((TRECPhrases) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECPHRASES:
                theDataList.add(trecPhrasesBean.getNumRows(TRECPhrases.class, userData));
                break;
            case GETDATA_TRECPHRASES:
                theDataList = (List<Object>) trecPhrasesBean.getDataInRange(TRECPhrases.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECPHRASES:
                theDataList.add(trecPhrasesBean.validateField(dataList.get(1).toString(), (TRECPhrases) dataList.get(2), userData));
                break;
            case UPDATE_ENC_PHRASES:
                trecPhrasesBean.updateEncryptedFields((TRECPhrases) dataList.get(1), userData);
                break;
            case CHECK_TREC_PHRASES:
                trecPhrasesBean.checkTRECPhrases((Boolean) dataList.get(1), userData);
                break;
            //TRECTrecTypes
            case INSERT_TRECTRECTYPES:
                theDataList.add(trecTypesBean.insert((TRECTrecTypes) dataList.get(1), userData));
                break;
            case UPDATE_TRECTRECTYPES:
                theDataList.add(trecTypesBean.update((TRECTrecTypes) dataList.get(1), userData));
                break;
            case DELETE_TRECTRECTYPES:
                theDataList.add(trecTypesBean.delete((TRECTrecTypes) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECTRECTYPES:
                theDataList.add(trecTypesBean.getNumRows(TRECTrecTypes.class, userData));
                break;
            case GETDATA_TRECTRECTYPES:
                theDataList = (List<Object>) trecTypesBean.getDataInRange(TRECTrecTypes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECTRECTYPES:
                theDataList.add(trecTypesBean.validateField(dataList.get(1).toString(), (TRECTrecTypes) dataList.get(2), userData));
                break;
            //Print TREC
            case PRINT_TREC:
                theDataList = trecCardBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //TRECParameters
            case INSERT_TRECPARAMETERS:
                theDataList.add(paramBean.insert((TRECParameters) dataList.get(1), userData));
                break;
            case UPDATE_TRECPARAMETERS:
                theDataList.add(paramBean.update((TRECParameters) dataList.get(1), userData));
                break;
            case DELETE_TRECPARAMETERS:
                theDataList.add(paramBean.delete((TRECParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECPARAMETERS:
                theDataList.add(paramBean.getNumRows(TRECParameters.class, userData));
                break;
            case GETDATA_TRECPARAMETERS:
                theDataList = (List<Object>) paramBean.getDataInRange(TRECParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECPARAMETERS:
                theDataList.add(paramBean.validateField(dataList.get(1).toString(), (TRECParameters) dataList.get(2), userData));
                break;
            case GET_MAXPHRASES:
                theDataList.add(paramBean.getParameterRecord(userData));
                break;
            //TRECLoadCompatibility
            case INSERT_TRECLOADCOMPATIBILITY:
                theDataList.add(loadCompBean.insert((TRECLoadCompatibility) dataList.get(1), userData));
                break;
            case UPDATE_TRECLOADCOMPATIBILITY:
                theDataList.add(loadCompBean.update((TRECLoadCompatibility) dataList.get(1), userData));
                break;
            case DELETE_TRECLOADCOMPATIBILITY:
                theDataList.add(loadCompBean.delete((TRECLoadCompatibility) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECLOADCOMPATIBILITY:
                theDataList.add(loadCompBean.getNumRows(TRECLoadCompatibility.class, userData));
                break;
            case GETDATA_TRECLOADCOMPATIBILITY:
                theDataList = (List<Object>) loadCompBean.getDataInRange(TRECLoadCompatibility.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECLOADCOMPATIBILITY:
                theDataList.add(loadCompBean.validateField(dataList.get(1).toString(), (TRECLoadCompatibility) dataList.get(2), userData));
                break;
            //TRECCargoCheckMaster
            case INSERT_TRECCARGOCHECKMASTER:
                theDataList.add(cargoCheckMasterBean.insert((TRECCargoCheckMaster) dataList.get(1), userData));
                break;
            case UPDATE_TRECCARGOCHECKMASTER:
                theDataList.add(cargoCheckMasterBean.update((TRECCargoCheckMaster) dataList.get(1), userData));
                break;
            case DELETE_TRECCARGOCHECKMASTER:
                theDataList.add(cargoCheckMasterBean.delete((TRECCargoCheckMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCARGOCHECKMASTER:
                theDataList.add(cargoCheckMasterBean.getNumRows(TRECCargoCheckMaster.class, userData));
                break;
            case GETDATA_TRECCARGOCHECKMASTER:
                theDataList = (List<Object>) cargoCheckMasterBean.getDataInRange(TRECCargoCheckMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCARGOCHECKMASTER:
                theDataList.add(cargoCheckMasterBean.validateField(dataList.get(1).toString(), (TRECCargoCheckMaster) dataList.get(2), userData));
                break;
            case PRINT_CARGO_CHECK_REPORT:
                theDataList = (cargoCheckReportBean.getReportResult(dataList, cmd.getReportConfig(), userData));
                break;
            //TRECCargoCheckLines
            case INSERT_TRECCARGOCHECKLINES:
                theDataList.add(cargoCheckLinesBean.insert((TRECCargoCheckLines) dataList.get(1), userData));
                break;
            case UPDATE_TRECCARGOCHECKLINES:
                theDataList.add(cargoCheckLinesBean.update((TRECCargoCheckLines) dataList.get(1), userData));
                break;
            case DELETE_TRECCARGOCHECKLINES:
                theDataList.add(cargoCheckLinesBean.delete((TRECCargoCheckLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCARGOCHECKLINES:
                theDataList.add(cargoCheckLinesBean.getNumRows(TRECCargoCheckLines.class, userData));
                break;
            case GETDATA_TRECCARGOCHECKLINES:
                theDataList = (List<Object>) cargoCheckLinesBean.getDataInRange(TRECCargoCheckLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCARGOCHECKLINES:
                theDataList.add(cargoCheckLinesBean.validateField(dataList.get(1).toString(), (TRECCargoCheckLines) dataList.get(2), userData));
                break;
            case INSERT_TRECCUSTOMERCHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.insert((TRECCustomerChemicals) dataList.get(1), userData));
                break;
            case UPDATE_TRECCUSTOMERCHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.update((TRECCustomerChemicals) dataList.get(1), userData));
                break;
            case DELETE_TRECCUSTOMERCHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.delete((TRECCustomerChemicals) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECCUSTOMERCHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.getNumRows(TRECCustomerChemicals.class, userData));
                break;
            case GETDATA_TRECCUSTOMERCHEMICALS:
                theDataList = (List<Object>) trecCustomerChemicalsBean.getDataInRange(TRECCustomerChemicals.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECCUSTOMERCHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.validateField(dataList.get(1).toString(), (TRECCustomerChemicals) dataList.get(2), userData));
                break;
            case UPDATE_ENC_CUSTOMER_CHEMICALS:
                trecCustomerChemicalsBean.updateEncryptedFields((TRECCustomerChemicals) dataList.get(1), userData);
                break;
            case CREATE_CUSTOMER_CHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.createCustomerChemical((long) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), (String) dataList.get(4), userData));
                break;
            case ENCRYPT_CUSTOMER_CHEMICALS:
                trecCustomerChemicalsBean.updateEncryptedFields((long) dataList.get(1), userData);
                break;
            case FETCH_CUSTOMER_CHEMICALS:
                theDataList.add(trecCustomerChemicalsBean.fetchCustomerChemical((long) dataList.get(1), userData));
                break;
            case FETCH_PHRASES_FOR_CHEMICAL:
                theDataList.add(trecCustomerChemicalsBean.fetchPhrasesForChemical((int) dataList.get(1), (String) dataList.get(2), (dataList.size() > 3 ? (Boolean) dataList.get(3) : false), userData));
                break;
            case FETCH_PHRASES_FOR_CHEMICAL_NEW:
                theDataList.add(trecPhrasesBean.getPhrases(dataList.get(1).toString(), (String) dataList.get(2), userData));
                break;
            case FETCH_E_PHRASES_FOR_CHEMICAL_NEW:
                theDataList.add(trecPhrasesBean.getEPhrases((TRECCustomerChemicals) dataList.get(1), userData));
                break;
            case RESET_CUSTOMER_CHEMICAL:
                theDataList.add(trecCustomerChemicalsBean.resetCustomerChemical((Long) dataList.get(1), userData));
                break;
            case TRECWEBINSFETCHMASTER:
                theDataList.add(trecWebLogicBean.insertFetchTrecMasterLinesFromWeb((TRECWebOrderProcessHelper) dataList.get(1), (Long) dataList.get(2), (String) dataList.get(3),(Long) dataList.get(4), userData));
                break;
            case TRECWEBUPDATECARDSMASTER:
                theDataList.add(trecWebLogicBean.updateCardMaster((TRECWebOrderProcessHelper) dataList.get(1), userData));
                break;
            case CREATE_UPDATE_BASKET_WEB:
                theDataList.add(trecWebLogicBean.createUpdateBasketWeb((TRECWebOrderProcessHelper) dataList.get(1), userData));
                break;
            case UPDATE_BASKET_MASTER:
                theDataList.add(trecWebLogicBean.updateBasketMaster((TRECWebOrderProcessHelper) dataList.get(1), userData));
                break;
            case DELETE_BASKET_LINE:
                theDataList.add(trecWebLogicBean.deleteBasketLine((TRECWebInvoiceHelper) dataList.get(1), userData));
                break;
            case REMOVE_DEBTORS_BASKET_LINE:
                theDataList.add(trecWebLogicBean.removeBasketLine((DebtorsBasketLines) dataList.get(1), userData));
                break;
            case CREATE_UPDATE_DEBTORS_LINES:
                theDataList.add(trecWebLogicBean.createUpdateDebtorsLines((TRECWebOrderProcessHelper) dataList.get(1), userData));
                break;
            case UPDATE_DEBTORS_BASKET_LINES:
                theDataList.add(trecWebLogicBean.updateBasketLine((TRECWebOrderProcessHelper) dataList.get(1),(DebtorsBasketLines) dataList.get(2), userData));
                break;
            case FETCH_TREC_CARDS_LINES:
                theDataList.add(trecWebLogicBean.fetchTrecCardLine((Long) dataList.get(1), userData));
                break;
            case CREATE_DEBTORS_INVOICE:
                theDataList.add(trecWebLogicBean.createDebtorsInvoice((TRECWebOrderProcessHelper) dataList.get(1), userData));
                break;
            case EMAIL_CUSTOMER_INVOICE:
                theDataList.add(trecWebLogicBean.emailCustomerInvoice((TRECWebOrderProcessHelper) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3),(boolean) dataList.get(4), userData));
                break;
            case TRECWEBUPDATECARDLINES:
                theDataList.add(trecWebLogicBean.updateCardLines((TRECTrecCardsLines) dataList.get(1), userData));
                break;
            //TREC web portal printer intergration
            case UPDATE_BASKET_LINES_PRINT_QTY:
                theDataList.add(trecWebPrinterLogicBean.updateBasketLinesPrintQty((String) dataList.get(1), (String) dataList.get(2), (Integer) dataList.get(3), (Integer) dataList.get(4), userData));
                break;
            case GET_PHRASES_STRING:
                theDataList.add(trecWebPrinterLogicBean.getPhrasesString(userData));
                break;
            case GET_TREC_CARD_DATASET:
                theDataList.add(trecWebPrinterLogicBean.getTrecCardDataset((String) dataList.get(1), (String) dataList.get(2), (Integer) dataList.get(3), (Boolean) dataList.get(4), (Boolean) dataList.get(5), userData));
                break;
            case GET_INVOICE_DATE:
                theDataList.add(trecWebPrinterLogicBean.getInvoiceData((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
                
            case PRINT_ERG_PHRASES:
                theDataList = ergPhrasesReportBean.getReportResult(dataList, cmd.getReportConfig(), userData); 
                break;
                
            case GET_PACKING_GROUP:
                theDataList.add(trecCardLinesBean.getPackingGroup((String) dataList.get(1), userData));
                break;
                
                
             case INSERT_TRECPREFERREDSHIPNAME:
                theDataList.add(preferredShipNameBean.insert((TRECPreferredShipName) dataList.get(1), userData));
                break;
            case UPDATE_TRECPREFERREDSHIPNAME:
                theDataList.add(preferredShipNameBean.update((TRECPreferredShipName) dataList.get(1), userData));
                break;
            case DELETE_TRECPREFERREDSHIPNAME:
                theDataList.add(preferredShipNameBean.delete((TRECPreferredShipName) dataList.get(1), userData));
                break;
            case GETNUMROWS_TRECPREFERREDSHIPNAME:
                theDataList.add(preferredShipNameBean.getNumRows(TRECPreferredShipName.class, userData));
                break;
            case GETDATA_TRECPREFERREDSHIPNAME:
                theDataList = (List<Object>) preferredShipNameBean.getDataInRange(TRECPreferredShipName.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_TRECPREFERREDSHIPNAME:
                theDataList.add(preferredShipNameBean.validateField(dataList.get(1).toString(), (TRECPreferredShipName) dataList.get(2), userData));
                break;
            case GET_PREF_SHIPNAME:
               theDataList.add(preferredShipNameBean.getPreferredShipNameList(dataList.get(1).toString(),dataList.get(2).toString(), userData));
                break;
                
            case PRINT_PLACARD:
                theDataList = placardBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;

            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Method not found", userData);
                }
                break;
        }
        theDataList.add(0, retCmd);
        return theDataList;
    }
}
