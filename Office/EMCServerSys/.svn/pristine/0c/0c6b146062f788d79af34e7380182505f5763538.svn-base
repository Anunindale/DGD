/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package emc.server.commandmanager.inventory;

import emc.bus.inventory.InventoryBarcodeLocal;
import emc.bus.inventory.InventoryBrandGroupLocal;
import emc.bus.inventory.dimensions.InventoryDimension3GroupLocal;
import emc.bus.inventory.dimensions.InventoryDimension1GroupLocal;
import emc.bus.inventory.InventoryCostingGroupLocal;
import emc.bus.inventory.InventoryItemAccessGroupLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.InventoryItemBatchLocal;
import emc.bus.inventory.InventoryItemGroupLocal;
import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionGroupLocal;
import emc.bus.inventory.InventoryItemRangeLocal;
import emc.bus.inventory.InventoryItemSerialLocal;
import emc.bus.inventory.InventoryLocationLocal;
import emc.bus.inventory.InventoryPalletLocal;
import emc.bus.inventory.InventoryParametersLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.InventoryReferenceTypeLocal;
import emc.bus.inventory.dimensions.InventoryDimension2GroupLocal;
import emc.bus.inventory.InventoryWarehouseLocal;
import emc.bus.inventory.agebins.AgeBinsBeansLocal;
import emc.bus.inventory.batchconsolidation.InventoryBatchConsolidationLinesLocal;
import emc.bus.inventory.batchconsolidation.InventoryBatchConsolidationMasterLocal;
import emc.bus.inventory.batchconsolidation.datasourse.InventoryBatchConsolidationLinesDSLocal;
import emc.bus.inventory.classifications.InventoryItemClassification1Local;
import emc.bus.inventory.classifications.InventoryItemClassification2Local;
import emc.bus.inventory.classifications.InventoryItemClassification3Local;
import emc.bus.inventory.classifications.InventoryItemClassification4Local;
import emc.bus.inventory.classifications.InventoryItemClassification5Local;
import emc.bus.inventory.classifications.InventoryItemClassification6Local;
import emc.bus.inventory.classifications.InventoryItemClassificationLocal;
import emc.bus.inventory.classifications.InventoryClassificationHierarchyLocal;
import emc.bus.inventory.colourdesignmaster.InventoryColourDisignMasterLocal;
import emc.bus.inventory.dimension1enquiry.InventoryDimension1EnquiryLocal;
import emc.bus.inventory.datasource.InventoryBarcodeDSLocal;
import emc.bus.inventory.datasource.InventoryItemAccessGroupEmployeesDSLocal;
import emc.bus.inventory.datasource.InventoryItemMasterDSLocal;
import emc.bus.inventory.datasource.InventoryItemMasterLookupDSLocal;
import emc.bus.inventory.datasource.InventoryReferenceDSLocal;
import emc.bus.inventory.dimensions.InventoryDimension1GroupSetupLocal;
import emc.bus.inventory.dimensions.InventoryDimension2GroupSetupLocal;
import emc.bus.inventory.dimensions.InventoryDimension3GroupSetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension1SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension2SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension3SetupLocal;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.bus.inventory.dimensions.datasource.InventoryDimension1GroupSetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryDimension2GroupSetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryDimension3GroupSetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension1SetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension1SetupFormDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension2SetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension2SetupFormDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension3SetupDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimension3SetupFormDSLocal;
import emc.bus.inventory.dimensions.datasource.InventoryItemDimensionCombinationsDSLocal;
import emc.bus.inventory.dimensions.datasource.additionaldimensions.InventoryAdditionalDimensionsDSLocal;
import emc.bus.inventory.dimensions.datasource.lines.InventoryDimension1LinesDSLocal;
import emc.bus.inventory.dimensions.lines.InventoryDimension1LinesLocal;
import emc.bus.inventory.dimensions.whereused.InventoryDimension1WhereUsedLocal;
import emc.bus.inventory.dimensions.whereused.InventoryDimension3WhereUsedLocal;
import emc.bus.inventory.inventorystocktakeunreserved.InventoryStocktakeUnreservedLocal;
import emc.bus.inventory.inventorystocktakeunreserved.datasource.InventoryStocktakeUnreservedDSLocal;
import emc.bus.inventory.journals.InventoryJournalLinesLocal;
import emc.bus.inventory.journals.InventoryJournalMasterLocal;
import emc.bus.inventory.journals.datasource.InventoryJournalLinesDSLocal;
import emc.bus.inventory.journals.datasource.InventoryJournalMasterDSLocal;
import emc.bus.inventory.journals.datasource.InventoryStockTakeCaptureDSLocal;
import emc.bus.inventory.logic.InventoryItemDimensionCombinationLogicLocal;
import emc.bus.inventory.movestock.InventoryMoveStockLinesLocal;
import emc.bus.inventory.movestock.InventoryMoveStockMasterDSLocal;
import emc.bus.inventory.movestock.InventoryMoveStockMasterLocal;
import emc.bus.inventory.movestock.InventoryMoveStockSummaryDSLocal;
import emc.bus.inventory.movestock.InventoryMoveStockSummaryLocal;
import emc.bus.inventory.pegging.InventoryPeggingLocal;
import emc.bus.inventory.productgroup.InventoryProductGroupLocal;
import emc.bus.inventory.register.removeregister.InventoryRemoveRegisterLocal;
import emc.bus.inventory.register.normanregister.InventoryRegisterLocal;
import emc.bus.inventory.register.stocktakeregister.InventoryStockTakeRegisterLocal;
import emc.bus.inventory.requirementsplanning.InventoryRequirementsPlanningLocal;
import emc.bus.inventory.requirementsplanning.ReqPlanAuditLocal;
import emc.bus.inventory.requirementsplanning.datasource.InventoryRequirementsPlanningDSLocal;
import emc.bus.inventory.requirementsplanning.datasource.InventoryRequirementsPlanningHistoryDSLocal;
import emc.bus.inventory.safetystock.InventorySafetyStockLocal;
import emc.bus.inventory.safetystock.datasource.InventorySafetyStockDSLocal;
import emc.bus.inventory.safetystockgenerationrules.InventorySafetyStockGenerationRulesLocal;
import emc.bus.inventory.transactions.datasource.InventorySummaryDSLocal;
import emc.bus.inventory.transactions.InventorySummaryLocal;
import emc.bus.inventory.transactions.InventoryTransactionsLocal;
import emc.bus.inventory.transactions.datasource.InventoryTransactionDSLocal;
import emc.bus.inventory.serialbatch.InventorySerialBatchLocal;
import emc.bus.inventory.settlement.InventorySettlementHistoryLocal;
import emc.bus.inventory.settlement.InventorySettlementLocal;
import emc.bus.inventory.settlement.logic.InventorySettlementLogicLocal;
import emc.bus.inventory.stocktake.InventoryStockTakeLocal;
import emc.bus.inventory.stocktakelogger.InventoryStockTakeLoggerLocal;
import emc.bus.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiryBySizeLocal;
import emc.bus.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiryLocal;
import emc.bus.inventory.warrantycheck.InventoryWarrantyCheckLocal;
import emc.commands.EMCCommands;
import emc.entity.inventory.InventoryBarcodes;
import emc.entity.inventory.InventoryBatchConsolidationLines;
import emc.entity.inventory.InventoryBatchConsolidationMaster;
import emc.entity.inventory.InventoryBrandGroup;
import emc.entity.inventory.dimensions.InventoryDimension3Group;
import emc.entity.inventory.dimensions.InventoryDimension1Group;
import emc.entity.inventory.InventoryCostingGroup;
import emc.entity.inventory.InventoryItemAccessGroup;
import emc.entity.inventory.InventoryItemAccessGroupEmployees;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.InventoryItemBatch;
import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.InventoryItemDimensionGroup;
import emc.entity.inventory.InventoryItemRange;
import emc.entity.inventory.dimensions.InventoryDimension2Group;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryItemSerial;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryPallet;
import emc.entity.inventory.InventoryParameters;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.InventoryReferenceType;
import emc.entity.inventory.InventorySafetyStock;
import emc.entity.inventory.InventorySafetyStockGenerationRules;
import emc.entity.inventory.InventoryStockTakeLogger;
import emc.entity.inventory.InventoryStocktakeUnreserved;
import emc.entity.inventory.agebins.InventoryAgeBins;
import emc.entity.inventory.classifications.InventoryItemClassification1;
import emc.entity.inventory.classifications.InventoryItemClassification2;
import emc.entity.inventory.classifications.InventoryItemClassification3;
import emc.entity.inventory.classifications.InventoryItemClassification4;
import emc.entity.inventory.classifications.InventoryItemClassification5;
import emc.entity.inventory.classifications.InventoryItemClassification6;
import emc.entity.inventory.classifications.InventoryClassificationHierarchy;
import emc.entity.inventory.classifications.superclasses.InventoryItemClassification;
import emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster;
import emc.entity.inventory.dimension1enquiry.InventoryDimension1Enquiry;
import emc.entity.inventory.datasource.InventoryBarcodesDS;
import emc.entity.inventory.datasource.InventoryBatchConsolidationLinesDS;
import emc.entity.inventory.datasource.InventoryItemMasterDS;
import emc.entity.inventory.datasource.InventoryItemMasterLookupDS;
import emc.entity.inventory.datasource.InventoryReferenceDS;
import emc.entity.inventory.datasource.InventoryReqPlanAudit;
import emc.entity.inventory.datasource.InventorySafetyStockDS;
import emc.entity.inventory.datasource.InventoryStocktakeUnreservedDS;
import emc.entity.inventory.dimensions.InventoryDimension1GroupSetup;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.entity.inventory.dimensions.InventoryDimension3GroupSetup;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.additionaldimensions.datasource.InventoryAdditionalDimensionsDS;
import emc.entity.inventory.dimensions.datasource.InventoryDimension1GroupSetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryDimension1LinesDS;
import emc.entity.inventory.dimensions.datasource.InventoryDimension2GroupSetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryDimension3GroupSetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupFormDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupFormDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupFormDS;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimensionCombinationsDS;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.entity.inventory.dimensions.whreused.InventoryDimension1WhereUsed;
import emc.entity.inventory.dimensions.whreused.InventoryDimension3WhereUsed;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.entity.inventory.journals.InventoryJournalMaster;
import emc.entity.inventory.journals.datasource.InventoryJournalLinesDS;
import emc.entity.inventory.journals.datasource.InventoryJournalMasterDS;
import emc.entity.inventory.journals.datasource.InventoryStocktakeCaptureDS;
import emc.entity.inventory.movestock.InventoryMoveStockLines;
import emc.entity.inventory.movestock.InventoryMoveStockMaster;
import emc.entity.inventory.movestock.InventoryMoveStockMasterDS;
import emc.entity.inventory.movestock.InventoryMoveStockSummary;
import emc.entity.inventory.movestock.InventoryMoveStockSummaryDS;
import emc.entity.inventory.pegging.InventoryPeggingTable;
import emc.entity.inventory.register.InventoryRegister;
import emc.entity.inventory.register.InventoryRemoveRegister;
import emc.entity.inventory.register.InventoryStocktakeRegister;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanning;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningDS;
import emc.entity.inventory.requirementsplanning.InventoryRequirementsPlanningHistoryDS;
import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.stocksettlement.InventoryStockSettlement;
import emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.inventory.transactions.datasource.InventoryTransactionsDS;
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiry;
import emc.entity.inventory.warehousestockenquiry.InventoryWarehouseStockEnquiryBySize;
import emc.enums.inventory.ageing.AgeingReportTypes;
import emc.enums.inventory.dimensions.EnumDimensions;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.helpers.inventory.JournalGeneratorHelper;
import emc.helpers.inventory.WarehouseEnquiryHelperClass;
import emc.inventory.helper.GenerateStockTake;
import emc.methods.inventory.ClientInventoryMethods;
import emc.methods.inventory.ServerInventoryMethods;
import emc.reports.inventory.InventoryOnHandReportLocal;
import emc.reports.inventory.ageing.InventoryAgeingReportBeanLocal;
import emc.reports.inventory.batchconsolidation.BatchConsolidationPickingListReportLocal;
import emc.reports.inventory.belowminimum.InventoryBelowMinimumReportLocal;
import emc.reports.inventory.dimension1.InventoryDimension1ReportLocal;
import emc.reports.inventory.factoryshop.InventoryFactoryShopReportLocal;
import emc.reports.inventory.inventorywithnodemandreport.InventoryWithNoDemandReportLocal;
import emc.reports.inventory.itemdimensions.InventoryItemDimensionReportLocal;
import emc.reports.inventory.journals.InventoryMovementJournalSummaryLocal;
import emc.reports.inventory.journals.detail.InventoryMovementJournalDetailLocal;
import emc.reports.inventory.opendispatchorders.InventoryOpenDispatchOrdersLocal;
import emc.reports.inventory.pickinglistsummary.PickingListSummaryReportLocal;
import emc.reports.inventory.pickinglistsummary.summary.PickingListSummaryReportSummaryLocal;
import emc.reports.inventory.stockbylocation.StockByLocationReportLocal;
import emc.reports.inventory.stockjournalreport.StockJournalReportLocal;
import emc.reports.inventory.stocktake.counting.InventoryStocktakeReportLocal;
import emc.reports.inventory.stocktake.recount.InventoryStocktakeRecountReportLocal;
import emc.reports.inventory.stocktake.variance.InventoryStocktakeVarianceReportLocal;
import emc.reports.inventory.stockusage.InventoryStockUsageReportLocal;
import emc.reports.inventory.stockvaluation.StockValuationReportLocal;
import emc.reports.inventory.warehouselocationtransfer.InventoryWarehouseLocationTransferReportLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class InventoryMethodMapperBean implements InventoryMethodMapperBeanLocal {

    @EJB
    private InventoryItemMasterDSLocal inventoryItemMasterDSBean;
    @EJB
    private InventoryItemMasterLocal inventoryItemMasterBean;
    @EJB
    private InventoryDimension2GroupLocal inventoryDimension2GroupBean;
    @EJB
    private InventoryCostingGroupLocal inventoryCostingGroupBean;
    @EJB
    private InventoryBrandGroupLocal inventoryBrandGroupBean;
    @EJB
    private InventoryItemRangeLocal inventoryItemRangeBean;
    @EJB
    private InventoryWarehouseLocal inventoryWarehouseBean;
    @EJB
    private InventoryBarcodeLocal inventoryBarCodeBean;
    @EJB
    private InventoryDimension1Local inventoryDimension1Bean;
    @EJB
    private InventoryDimension2Local inventoryDimension2Bean;
    @EJB
    private InventoryDimension3Local inventoryDimension3Bean;
    @EJB
    private InventoryItemDimensionCombinationsLocal inventoryItemDimensionCombinationsBean;
    @EJB
    private InventoryItemDimensionGroupLocal inventoryItemDimensionGroupBean;
    @EJB
    private InventoryItemBatchLocal inventoryItemBatchBean;
    @EJB
    private InventoryItemSerialLocal inventoryItemSerialBean;
    @EJB
    private InventoryJournalMasterLocal journalMasterBean;
    @EJB
    private InventoryJournalLinesLocal journalLinesBean;
    @EJB
    private InventoryDimension3GroupLocal dimension3GroupBean;
    @EJB
    private InventoryDimension1GroupLocal dimension1GroupBean;
    @EJB
    private InventoryDimension2GroupSetupLocal inventoryDimension2GroupSetupBean;
    @EJB
    private InventoryDimension1GroupSetupLocal inventoryDimension1GroupSetupBean;
    @EJB
    private InventoryDimension3GroupSetupLocal inventoryDimension3GroupSetupBean;
    @EJB
    private InventoryItemDimension1SetupLocal inventoryItemDimension1SetupBean;
    @EJB
    private InventoryItemDimension2SetupLocal inventoryItemDimension2SetupBean;
    @EJB
    private InventoryItemDimension3SetupLocal inventoryItemDimension3SetupBean;
    @EJB
    private InventoryItemDimensionCombinationsDSLocal itemDimensionCombinationDSBean;
    @EJB
    private InventoryDimension1GroupSetupDSLocal dimension1GroupSetupDSBean;
    @EJB
    private InventoryDimension2GroupSetupDSLocal dimension2GroupSetupDSBean;
    @EJB
    private InventoryDimension3GroupSetupDSLocal dimension3GroupSetupDSBean;
    @EJB
    private InventoryItemDimension1SetupDSLocal itemDimension1SetupDSBean;
    @EJB
    private InventoryItemDimension2SetupDSLocal itemDimension2SetupDSBean;
    @EJB
    private InventoryItemDimension3SetupDSLocal itemDimension3SetupDSBean;
    @EJB
    private InventoryItemGroupLocal inventoryItemGroupLocal;
    @EJB
    private InventoryItemClassification1Local itemClassification1Bean;
    @EJB
    private InventoryItemClassification2Local itemClassification2Bean;
    @EJB
    private InventoryItemClassification3Local itemClassification3Bean;
    @EJB
    private InventoryItemClassification4Local itemClassification4Bean;
    @EJB
    private InventoryItemClassification5Local itemClassification5Bean;
    @EJB
    private InventoryItemClassification6Local itemClassification6Bean;
    @EJB
    private InventoryReferenceTypeLocal referenceTypeBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventorySummaryLocal inventSumBean;
    @EJB
    private InventorySummaryDSLocal inventSumDS;
    @EJB
    private InventoryItemDimension1SetupFormDSLocal itemDimension1SetupFormDSBean;
    @EJB
    private InventoryItemDimension2SetupFormDSLocal itemDimension2SetupFormDSBean;
    @EJB
    private InventoryItemDimension3SetupFormDSLocal itemDimension3SetupFormDSBean;
    @EJB
    private InventoryTransactionsLocal transactionBean;
    @EJB
    private InventoryTransactionDSLocal transactionBeanDS;
    @EJB
    private InventoryItemAccessGroupLocal itemAccessGroupBean;
    @EJB
    private InventoryItemAccessGroupEmployeesDSLocal itemAccessGroupEmployeesBean;
    @EJB
    private InventoryJournalLinesDSLocal journalLinesDSBean;
    @EJB
    private InventoryClassificationHierarchyLocal classificationHierarchyBean;
    @EJB
    private InventoryColourDisignMasterLocal colourDisignBean;
    @EJB
    private InventoryItemClassificationLocal itemClassificationBean;
    @EJB
    private InventoryJournalMasterDSLocal journalMasterDSBean;
    @EJB
    private InventoryParametersLocal parametersBean;
    @EJB
    private InventoryItemMasterLookupDSLocal itemMasterLookupDSBean;
    @EJB
    private InventoryReferenceDSLocal refBeanDS;
    @EJB
    private InventoryBarcodeDSLocal barcodeBeanDS;
    @EJB
    private InventoryOnHandReportLocal onHandReportBean;
    @EJB
    private StockJournalReportLocal journalReportBean;
    @EJB
    private InventorySerialBatchLocal serialBatchBean;
    @EJB
    private InventoryRequirementsPlanningLocal requirementsPlanningBean;
    @EJB
    private InventoryPalletLocal palletBean;
    @EJB
    private InventoryLocationLocal locationBean;
    @EJB
    private InventoryMoveStockMasterDSLocal moveStockMasterDSBean;
    @EJB
    private InventoryMoveStockMasterLocal moveStockMasterBean;
    @EJB
    private InventoryMoveStockLinesLocal moveStockLinesBean;
    @EJB
    private InventoryMoveStockSummaryLocal moveStockSummaryBean;
    @EJB
    private InventoryMoveStockSummaryDSLocal moveStockSummaryDSBean;
    @EJB
    private InventoryRegisterLocal registerBean;
    @EJB
    private InventoryRemoveRegisterLocal removeRegisterBean;
    @EJB
    private InventoryBelowMinimumReportLocal belowMinBean;
    @EJB
    private InventoryAdditionalDimensionsLocal additionalDimensionsBean;
    @EJB
    private InventoryAdditionalDimensionsDSLocal additionalDimensionsDSBean;
    @EJB
    private PickingListSummaryReportLocal pickListSummaryReportBean;
    @EJB
    private StockValuationReportLocal stockValuationReportBean;
    @EJB
    private PickingListSummaryReportSummaryLocal pickListSummarySummaryReportBean;
    @EJB
    private StockByLocationReportLocal stockByLocationReportBean;
    @EJB
    private InventoryMovementJournalSummaryLocal movementJournalSummaryBean;
    @EJB
    private InventoryMovementJournalDetailLocal movementJournalDetailBean;
    @EJB
    private AgeBinsBeansLocal ageBinBean;
    @EJB
    private InventoryAgeingReportBeanLocal ageingReportBean;
    @EJB
    private InventoryItemDimensionReportLocal itemDimensionReportBean;
    @EJB
    private InventorySettlementHistoryLocal settleHistBean;
    @EJB
    private InventorySettlementLocal settlementBean;
    @EJB
    private InventorySettlementLogicLocal settlementLogicBean;
    @EJB
    private InventoryStockTakeLocal stockTakeBean;
    @EJB
    private InventoryStockTakeRegisterLocal stockTakeRegisterBean;
    @EJB
    private InventoryStocktakeVarianceReportLocal stockTakeVarianceReportBean;
    @EJB
    private InventoryStocktakeReportLocal stockTakeReportBean;
    @EJB
    private InventoryStocktakeRecountReportLocal stockTakeRecountReportBean;
    @EJB
    private InventoryStockTakeCaptureDSLocal stockTakeCaptureBean;
    @EJB
    private InventoryItemDimensionCombinationLogicLocal dimLogicBean;
    @EJB
    private InventoryDimension1LinesLocal dimension1LinesBean;
    @EJB
    private InventoryDimension1LinesDSLocal dimension1LinesDSBean;
    @EJB
    private InventoryStockUsageReportLocal stockUsageReportBean;
    @EJB
    private InventoryProductGroupLocal productionGroupBean;
    @EJB
    private InventoryDimension1ReportLocal dimension1ReportBean;
    @EJB
    private InventoryDimension1WhereUsedLocal dimension1WhereUsedBean;
    @EJB
    private InventoryRequirementsPlanningDSLocal requirementsPlanningDSBean;
    @EJB
    private InventoryRequirementsPlanningHistoryDSLocal requirementsPlanningHistoryDSBean;
    @EJB
    private InventoryOpenDispatchOrdersLocal openDispatchOrdersReportBean;
    @EJB
    private InventoryWarehouseStockEnquiryLocal warehouseStockEnquiryBean;
    @EJB
    private InventoryWarehouseStockEnquiryBySizeLocal warehouseStockEnquiryBySizeBean;
    @EJB
    private InventoryWithNoDemandReportLocal inventoryWithNoDemandReportBean;
    @EJB
    private InventoryStockTakeLoggerLocal stockTakeLoggerBean;
    @EJB
    private InventoryDimension3WhereUsedLocal dimension3WhereUsedBean;
    @EJB
    private InventoryStocktakeUnreservedLocal stocktakeUnreservedBean;
    @EJB
    private InventoryStocktakeUnreservedDSLocal stocktakeUnreservedDSBean;
    @EJB
    private InventoryFactoryShopReportLocal factoryShopReportBean;
    @EJB
    private InventorySafetyStockLocal safetyStockBean;
    @EJB
    private InventorySafetyStockDSLocal safetyStockDSBean;
    @EJB
    private InventorySafetyStockGenerationRulesLocal safetyStockGenerationRulesBean;
    @EJB
    private InventoryPeggingLocal peggingBean;
    @EJB
    private InventoryDimension1EnquiryLocal dimension1EnquiryBean;
    @EJB
    private InventoryWarrantyCheckLocal warrantyCheckBean;
    @EJB
    private InventoryBatchConsolidationMasterLocal batchConsolidationMasterBean;
    @EJB
    private InventoryBatchConsolidationLinesLocal batchConsolidationLinesBean;
    @EJB
    private InventoryBatchConsolidationLinesDSLocal batchConsolidationLinesDSBean;
    @EJB
    private BatchConsolidationPickingListReportLocal batchConsolidationPickingListReportBean;
    @EJB
    private InventoryWarehouseLocationTransferReportLocal warehouseLocationTransferReportBean;
    @EJB
    private ReqPlanAuditLocal reqPlanAuditBean;

    @Override
    public List mapMethodInventory(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.INVENTORY.getId());
        retCmd.setMethodId(ClientInventoryMethods.GENERAL_METHOD.toString());

        switch (ServerInventoryMethods.fromString(cmd.getMethodId())) {
            //Inventory Item Master
            case INSERT_INVENTORYITEMMASTER:
                theDataList.add(inventoryItemMasterBean.insert((InventoryItemMaster) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMMASTER:
                theDataList.add(inventoryItemMasterBean.update((InventoryItemMaster) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMMASTER:
                theDataList.add(inventoryItemMasterBean.delete((InventoryItemMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMMASTER:
                theDataList.add(inventoryItemMasterBean.getNumRows(InventoryItemMaster.class, userData));
                break;
            case GETDATA_INVENTORYITEMMASTER:
                theDataList = (List<Object>) inventoryItemMasterBean.getDataInRange(InventoryItemMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMMASTER:
                theDataList.add(inventoryItemMasterBean.validateField(dataList.get(1).toString(), (InventoryItemMaster) dataList.get(2), userData));
                break;
            case POPULATE_NEW_ITEM:
                theDataList.add(inventoryItemMasterBean.populateDefaultItemFields(userData));
                break;
            case FIND_ITEM:
                theDataList.add(inventoryItemMasterBean.findItem((String) dataList.get(1), userData));
                break;
            case GENERATE_ITEM_DIM_COMBINATIONS:
                theDataList.add(inventoryItemMasterBean.generateItemDimCombinations(dataList.get(1).toString(), userData));
                break;
            //InventoryItemMasterDS
            case INSERT_INVENTORYITEMMASTERDS:
                theDataList.add(inventoryItemMasterDSBean.insert((InventoryItemMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMMASTERDS:
                theDataList.add(inventoryItemMasterDSBean.update((InventoryItemMasterDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMMASTERDS:
                theDataList.add(inventoryItemMasterDSBean.delete((InventoryItemMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMMASTERDS:
                theDataList.add(inventoryItemMasterDSBean.getNumRows(InventoryItemMasterDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMMASTERDS:
                theDataList = (List<Object>) inventoryItemMasterDSBean.getDataInRange(InventoryItemMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMMASTERDS:
                theDataList.add(inventoryItemMasterDSBean.validateField(dataList.get(1).toString(), (InventoryItemMasterDS) dataList.get(2), userData));
                break;

            //InventoryDimension2Group
            case INSERT_INVENTORYDIMENSION2GROUP:
                theDataList.add(inventoryDimension2GroupBean.insert((InventoryDimension2Group) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION2GROUP:
                theDataList.add(inventoryDimension2GroupBean.update((InventoryDimension2Group) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION2GROUP:
                theDataList.add(inventoryDimension2GroupBean.delete((InventoryDimension2Group) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION2GROUP:
                theDataList.add(inventoryDimension2GroupBean.getNumRows(InventoryDimension2Group.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION2GROUP:
                theDataList = (List<Object>) inventoryDimension2GroupBean.getDataInRange(InventoryDimension2Group.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION2GROUP:
                theDataList.add(inventoryDimension2GroupBean.validateField(dataList.get(1).toString(), (InventoryDimension2Group) dataList.get(2), userData));
                break;
            //Inventory Costing Group
            case INSERT_INVENTORYCOSTINGGROUP:
                theDataList.add(inventoryCostingGroupBean.insert((InventoryCostingGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYCOSTINGGROUP:
                theDataList.add(inventoryCostingGroupBean.update((InventoryCostingGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYCOSTINGGROUP:
                theDataList.add(inventoryCostingGroupBean.delete((InventoryCostingGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYCOSTINGGROUP:
                theDataList.add(inventoryCostingGroupBean.getNumRows(InventoryCostingGroup.class, userData));
                break;
            case GETDATA_INVENTORYCOSTINGGROUP:
                theDataList = (List<Object>) inventoryCostingGroupBean.getDataInRange(InventoryCostingGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYCOSTINGGROUP:
                theDataList.add(inventoryCostingGroupBean.validateField(dataList.get(1).toString(), (InventoryCostingGroup) dataList.get(2), userData));
                break;
            case FIX_COST_ROUTINE:
                theDataList.add(inventoryCostingGroupBean.updateCostRoutine(userData));
                break;
            case FIX_TRANS_ROUTINE:
                theDataList.add(inventoryCostingGroupBean.fixTransactions(userData));
                break;


            //Inventory Brand Group
            case INSERT_INVENTORYBRANDGROUP:
                theDataList.add(inventoryBrandGroupBean.insert((InventoryBrandGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBRANDGROUP:
                theDataList.add(inventoryBrandGroupBean.update((InventoryBrandGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBRANDGROUP:
                theDataList.add(inventoryBrandGroupBean.delete((InventoryBrandGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBRANDGROUP:
                theDataList.add(inventoryBrandGroupBean.getNumRows(InventoryBrandGroup.class, userData));
                break;
            case GETDATA_INVENTORYBRANDGROUP:
                theDataList = (List<Object>) inventoryBrandGroupBean.getDataInRange(InventoryBrandGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBRANDGROUP:
                theDataList.add(inventoryBrandGroupBean.validateField(dataList.get(1).toString(), (InventoryBrandGroup) dataList.get(2), userData));
                break;
            //Inventory Item Range
            case INSERT_INVENTORYITEMRANGE:
                theDataList.add(inventoryItemRangeBean.insert((InventoryItemRange) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMRANGE:
                theDataList.add(inventoryItemRangeBean.update((InventoryItemRange) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMRANGE:
                theDataList.add(inventoryItemRangeBean.delete((InventoryItemRange) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMRANGE:
                theDataList.add(inventoryItemRangeBean.getNumRows(InventoryItemRange.class, userData));
                break;
            case GETDATA_INVENTORYITEMRANGE:
                theDataList = (List<Object>) inventoryItemRangeBean.getDataInRange(InventoryItemRange.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMRANGE:
                theDataList.add(inventoryItemRangeBean.validateField(dataList.get(1).toString(), (InventoryItemRange) dataList.get(2), userData));
                break;
            //InventoryWarehouse
            case INSERT_INVENTORYWAREHOUSE:
                theDataList.add(inventoryWarehouseBean.insert((InventoryWarehouse) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYWAREHOUSE:
                theDataList.add(inventoryWarehouseBean.update((InventoryWarehouse) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYWAREHOUSE:
                theDataList.add(inventoryWarehouseBean.delete((InventoryWarehouse) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYWAREHOUSE:
                theDataList.add(inventoryWarehouseBean.getNumRows(InventoryWarehouse.class, userData));
                break;
            case GETDATA_INVENTORYWAREHOUSE:
                theDataList = (List<Object>) inventoryWarehouseBean.getDataInRange(InventoryWarehouse.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYWAREHOUSE:
                theDataList.add(inventoryWarehouseBean.validateField(dataList.get(1).toString(), (InventoryWarehouse) dataList.get(2), userData));
                break;
            case POPULATE_SYSTEM_LOCATIONS:
                inventoryWarehouseBean.createSystemLocations(userData);
                break;
            //Inventory Barcodes
            case INSERT_INVENTORYBARCODES:
                theDataList.add(inventoryBarCodeBean.insert((InventoryBarcodes) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBARCODES:
                theDataList.add(inventoryBarCodeBean.update((InventoryBarcodes) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBARCODES:
                theDataList.add(inventoryBarCodeBean.delete((InventoryBarcodes) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBARCODES:
                theDataList.add(inventoryBarCodeBean.getNumRows(InventoryBarcodes.class, userData));
                break;
            case GETDATA_INVENTORYBARCODES:
                theDataList = (List<Object>) inventoryBarCodeBean.getDataInRange(InventoryBarcodes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBARCODES:
                theDataList.add(inventoryBarCodeBean.validateField(dataList.get(1).toString(), (InventoryBarcodes) dataList.get(2), userData));
                break;
            //Inventory Dimension 1
            case INSERT_INVENTORYDIMENSION1:
                theDataList.add(inventoryDimension1Bean.insert((InventoryDimension1) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1:
                theDataList.add(inventoryDimension1Bean.update((InventoryDimension1) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1:
                theDataList.add(inventoryDimension1Bean.delete((InventoryDimension1) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1:
                theDataList.add(inventoryDimension1Bean.getNumRows(InventoryDimension1.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1:
                theDataList = (List<Object>) inventoryDimension1Bean.getDataInRange(InventoryDimension1.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1:
                theDataList.add(inventoryDimension1Bean.validateField(dataList.get(1).toString(), (InventoryDimension1) dataList.get(2), userData));
                break;
            case SET_SORT_CODE_DIMENSION1:
                inventoryDimension1Bean.updateSortCodes(userData);
                break;
            //Inventory Dimension 2
            case INSERT_INVENTORYDIMENSION2:
                theDataList.add(inventoryDimension2Bean.insert((InventoryDimension2) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION2:
                theDataList.add(inventoryDimension2Bean.update((InventoryDimension2) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION2:
                theDataList.add(inventoryDimension2Bean.delete((InventoryDimension2) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION2:
                theDataList.add(inventoryDimension2Bean.getNumRows(InventoryDimension2.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION2:
                theDataList = (List<Object>) inventoryDimension2Bean.getDataInRange(InventoryDimension2.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION2:
                theDataList.add(inventoryDimension2Bean.validateField(dataList.get(1).toString(), (InventoryDimension2) dataList.get(2), userData));
                break;
            //InventoryDimension3
            case INSERT_INVENTORYDIMENSION3:
                theDataList.add(inventoryDimension3Bean.insert((InventoryDimension3) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION3:
                theDataList.add(inventoryDimension3Bean.update((InventoryDimension3) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION3:
                theDataList.add(inventoryDimension3Bean.delete((InventoryDimension3) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION3:
                theDataList.add(inventoryDimension3Bean.getNumRows(InventoryDimension3.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION3:
                theDataList = (List<Object>) inventoryDimension3Bean.getDataInRange(InventoryDimension3.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION3:
                theDataList.add(inventoryDimension3Bean.validateField(dataList.get(1).toString(), (InventoryDimension3) dataList.get(2), userData));
                break;
            case SET_SORT_CODE_DIMENSION3:
                inventoryDimension3Bean.updateSortCodes(userData);
                break;
            //InventoryItemDimensionCombinations
            case INSERT_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList.add(inventoryItemDimensionCombinationsBean.insert((InventoryItemDimensionCombinations) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList.add(inventoryItemDimensionCombinationsBean.update((InventoryItemDimensionCombinations) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList.add(inventoryItemDimensionCombinationsBean.delete((InventoryItemDimensionCombinations) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList.add(inventoryItemDimensionCombinationsBean.getNumRows(InventoryItemDimensionCombinations.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList = (List<Object>) inventoryItemDimensionCombinationsBean.getDataInRange(InventoryItemDimensionCombinations.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSIONCOMBINATIONS:
                theDataList.add(inventoryItemDimensionCombinationsBean.validateField(dataList.get(1).toString(), (InventoryItemDimensionCombinations) dataList.get(2), userData));
                break;
            case CHECK_COMBINATIONS_EXIST:
                theDataList.add(inventoryItemDimensionCombinationsBean.checkCombinationsExist((String) dataList.get(1), userData));
                break;
            case PRINT_ITEM_DIMENSION_REPORT:
                theDataList = itemDimensionReportBean.getReportResult(dataList, userData);
                break;
            case CHECK_DIMENSION_IN_USE:
                theDataList.add(dimLogicBean.isDimensionInUse(String.valueOf(dataList.get(1)), String.valueOf(dataList.get(2)), EnumDimensions.fromString(String.valueOf(dataList.get(3))), userData));
                break;
            //Inventory Item Dimension Group
            case INSERT_INVENTORYITEMDIMENSIONGROUP:
                theDataList.add(inventoryItemDimensionGroupBean.insert((InventoryItemDimensionGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSIONGROUP:
                theDataList.add(inventoryItemDimensionGroupBean.update((InventoryItemDimensionGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSIONGROUP:
                theDataList.add(inventoryItemDimensionGroupBean.delete((InventoryItemDimensionGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSIONGROUP:
                theDataList.add(inventoryItemDimensionGroupBean.getNumRows(InventoryItemDimensionGroup.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSIONGROUP:
                theDataList = (List<Object>) inventoryItemDimensionGroupBean.getDataInRange(InventoryItemDimensionGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSIONGROUP:
                theDataList.add(inventoryItemDimensionGroupBean.validateField(dataList.get(1).toString(), (InventoryItemDimensionGroup) dataList.get(2), userData));
                break;
            //Inventory Item Batch
            case INSERT_INVENTORYITEMBATCH:
                theDataList.add(inventoryItemBatchBean.insert((InventoryItemBatch) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMBATCH:
                theDataList.add(inventoryItemBatchBean.update((InventoryItemBatch) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMBATCH:
                theDataList.add(inventoryItemBatchBean.delete((InventoryItemBatch) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMBATCH:
                theDataList.add(inventoryItemBatchBean.getNumRows(InventoryItemBatch.class, userData));
                break;
            case GETDATA_INVENTORYITEMBATCH:
                theDataList = (List<Object>) inventoryItemBatchBean.getDataInRange(InventoryItemBatch.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMBATCH:
                theDataList.add(inventoryItemBatchBean.validateField(dataList.get(1).toString(), (InventoryItemBatch) dataList.get(2), userData));
                break;
            //InventoryItemSerial
            case INSERT_INVENTORYITEMSERIAL:
                theDataList.add(inventoryItemSerialBean.insert((InventoryItemSerial) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMSERIAL:
                theDataList.add(inventoryItemSerialBean.update((InventoryItemSerial) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMSERIAL:
                theDataList.add(inventoryItemSerialBean.delete((InventoryItemSerial) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMSERIAL:
                theDataList.add(inventoryItemSerialBean.getNumRows(InventoryItemSerial.class, userData));
                break;
            case GETDATA_INVENTORYITEMSERIAL:
                theDataList = (List<Object>) inventoryItemSerialBean.getDataInRange(InventoryItemSerial.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMSERIAL:
                theDataList.add(inventoryItemSerialBean.validateField(dataList.get(1).toString(), (InventoryItemSerial) dataList.get(2), userData));
                break;
            //Inventory Journal Master
            case INSERT_INVENTORYJOURNALMASTER:
                theDataList.add(journalMasterBean.insert((InventoryJournalMaster) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYJOURNALMASTER:
                theDataList.add(journalMasterBean.update((InventoryJournalMaster) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYJOURNALMASTER:
                theDataList.add(journalMasterBean.delete((InventoryJournalMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYJOURNALMASTER:
                theDataList.add(journalMasterBean.getNumRows(InventoryJournalMaster.class, userData));
                break;
            case GETDATA_INVENTORYJOURNALMASTER:
                theDataList = (List<Object>) journalMasterBean.getDataInRange(InventoryJournalMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYJOURNALMASTER:
                theDataList.add(journalMasterBean.validateField(dataList.get(1).toString(), (InventoryJournalMaster) dataList.get(2), userData));
                break;
            case APPROVE_JOURNAL:
                theDataList.add(journalMasterBean.approveJournal(dataList.get(1).toString(), userData));
                break;
            case POST_INVENTORYJOURNAL:
                theDataList.add(journalMasterBean.postJournal((InventoryJournalMaster) dataList.get(1), userData));
                break;
            case POST_INVENTORYJOURNAL_FROM_NUMBER:
                theDataList.add(journalMasterBean.postJournal((String) dataList.get(1), userData));
                break;
            case VALIDATE_INVENTORYJOURNAL:
                theDataList.add(journalMasterBean.validateJournal((InventoryJournalMaster) dataList.get(1), userData));
                break;
            case UNAPPROVE_JOURNAL:
                theDataList.add(journalMasterBean.unApproveJournal(dataList.get(1).toString(), userData));
                break;
            case GENERATE_STOCKTAKE:
                theDataList.add(stockTakeBean.generateStockTakeJournals((GenerateStockTake) dataList.get(1), userData));
                break;
            case GENERATE_TRANSFER_JOURNAL:
                theDataList.add(journalMasterBean.generateTransferJournal((JournalGeneratorHelper) dataList.get(1), userData));
                break;
            //InventoryJournalLines
            case INSERT_INVENTORYJOURNALLINES:
                theDataList.add(journalLinesBean.insert((InventoryJournalLines) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYJOURNALLINES:
                theDataList.add(journalLinesBean.update((InventoryJournalLines) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYJOURNALLINES:
                theDataList.add(journalLinesBean.delete((InventoryJournalLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYJOURNALLINES:
                theDataList.add(journalLinesBean.getNumRows(InventoryJournalLines.class, userData));
                break;
            case GETDATA_INVENTORYJOURNALLINES:
                theDataList = (List<Object>) journalLinesBean.getDataInRange(InventoryJournalLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYJOURNALLINES:
                theDataList.add(journalLinesBean.validateField(dataList.get(1).toString(), (InventoryJournalLines) dataList.get(2), userData));
                break;
            case MASS_UPDATE_LINE_DATE:
                theDataList.add(journalLinesBean.massUpdateLineDate((String) dataList.get(1), (Date) dataList.get(2), userData));
                break;
            case GENERATE_REGISTRATION:
                theDataList.add(journalLinesBean.generateRegistration((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3),
                        (String) dataList.get(4), (String) dataList.get(5), (String) dataList.get(6), (String) dataList.get(7), (BigDecimal) dataList.get(8),
                        (String) dataList.get(9), userData));
                break;
            //InventoryDimension3Group
            case INSERT_INVENTORYDIMENSION3GROUP:
                theDataList.add(dimension3GroupBean.insert((InventoryDimension3Group) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION3GROUP:
                theDataList.add(dimension3GroupBean.update((InventoryDimension3Group) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION3GROUP:
                theDataList.add(dimension3GroupBean.delete((InventoryDimension3Group) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION3GROUP:
                theDataList.add(dimension3GroupBean.getNumRows(InventoryDimension3Group.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION3GROUP:
                theDataList = (List<Object>) dimension3GroupBean.getDataInRange(InventoryDimension3Group.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION3GROUP:
                theDataList.add(dimension3GroupBean.validateField(dataList.get(1).toString(), (InventoryDimension3Group) dataList.get(2), userData));
                break;
            //InventoryDimension1Group
            case INSERT_INVENTORYDIMENSION1GROUP:
                theDataList.add(dimension1GroupBean.insert((InventoryDimension1Group) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1GROUP:
                theDataList.add(dimension1GroupBean.update((InventoryDimension1Group) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1GROUP:
                theDataList.add(dimension1GroupBean.delete((InventoryDimension1Group) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1GROUP:
                theDataList.add(dimension1GroupBean.getNumRows(InventoryDimension1Group.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1GROUP:
                theDataList = (List<Object>) dimension1GroupBean.getDataInRange(InventoryDimension1Group.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1GROUP:
                theDataList.add(dimension1GroupBean.validateField(dataList.get(1).toString(), (InventoryDimension1Group) dataList.get(2), userData));
                break;
            //Inventory Dimension 2 Group Setup
            case INSERT_INVENTORYDIMENSION2GROUPSETUP:
                theDataList.add(inventoryDimension2GroupSetupBean.insert((InventoryDimension2GroupSetup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION2GROUPSETUP:
                theDataList.add(inventoryDimension2GroupSetupBean.update((InventoryDimension2GroupSetup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION2GROUPSETUP:
                theDataList.add(inventoryDimension2GroupSetupBean.delete((InventoryDimension2GroupSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION2GROUPSETUP:
                theDataList.add(inventoryDimension2GroupSetupBean.getNumRows(InventoryDimension2GroupSetup.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION2GROUPSETUP:
                theDataList = (List<Object>) inventoryDimension2GroupSetupBean.getDataInRange(InventoryDimension2GroupSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION2GROUPSETUP:
                theDataList.add(inventoryDimension2GroupSetupBean.validateField(dataList.get(1).toString(), (InventoryDimension2GroupSetup) dataList.get(2), userData));
                break;
            //Inventory Dimension 1 Group Setup
            case INSERT_INVENTORYDIMENSION1GROUPSETUP:
                theDataList.add(inventoryDimension1GroupSetupBean.insert((InventoryDimension1GroupSetup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1GROUPSETUP:
                theDataList.add(inventoryDimension1GroupSetupBean.update((InventoryDimension1GroupSetup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1GROUPSETUP:
                theDataList.add(inventoryDimension1GroupSetupBean.delete((InventoryDimension1GroupSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1GROUPSETUP:
                theDataList.add(inventoryDimension1GroupSetupBean.getNumRows(InventoryDimension1GroupSetup.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1GROUPSETUP:
                theDataList = (List<Object>) inventoryDimension1GroupSetupBean.getDataInRange(InventoryDimension1GroupSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1GROUPSETUP:
                theDataList.add(inventoryDimension1GroupSetupBean.validateField(dataList.get(1).toString(), (InventoryDimension1GroupSetup) dataList.get(2), userData));
                break;
            //Inventory Dimension 3 Group Setup
            case INSERT_INVENTORYDIMENSION3GROUPSETUP:
                theDataList.add(inventoryDimension3GroupSetupBean.insert((InventoryDimension3GroupSetup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION3GROUPSETUP:
                theDataList.add(inventoryDimension3GroupSetupBean.update((InventoryDimension3GroupSetup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION3GROUPSETUP:
                theDataList.add(inventoryDimension3GroupSetupBean.delete((InventoryDimension3GroupSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION3GROUPSETUP:
                theDataList.add(inventoryDimension3GroupSetupBean.getNumRows(InventoryDimension3GroupSetup.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION3GROUPSETUP:
                theDataList = (List<Object>) inventoryDimension3GroupSetupBean.getDataInRange(InventoryDimension3GroupSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION3GROUPSETUP:
                theDataList.add(inventoryDimension3GroupSetupBean.validateField(dataList.get(1).toString(), (InventoryDimension3GroupSetup) dataList.get(2), userData));
                break;
            //Inventory Item Dimension 1 Setup
            case INSERT_INVENTORYITEMDIMENSION1SETUP:
                theDataList.add(inventoryItemDimension1SetupBean.insert((InventoryItemDimension1Setup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION1SETUP:
                theDataList.add(inventoryItemDimension1SetupBean.update((InventoryItemDimension1Setup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION1SETUP:
                theDataList.add(inventoryItemDimension1SetupBean.delete((InventoryItemDimension1Setup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION1SETUP:
                theDataList.add(inventoryItemDimension1SetupBean.getNumRows(InventoryItemDimension1Setup.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION1SETUP:
                theDataList = (List<Object>) inventoryItemDimension1SetupBean.getDataInRange(InventoryItemDimension1Setup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION1SETUP:
                theDataList.add(inventoryItemDimension1SetupBean.validateField(dataList.get(1).toString(), (InventoryItemDimension1Setup) dataList.get(2), userData));
                break;
            //InventoryItemDimension2Setup
            case INSERT_INVENTORYITEMDIMENSION2SETUP:
                theDataList.add(inventoryItemDimension2SetupBean.insert((InventoryItemDimension2Setup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION2SETUP:
                theDataList.add(inventoryItemDimension2SetupBean.update((InventoryItemDimension2Setup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION2SETUP:
                theDataList.add(inventoryItemDimension2SetupBean.delete((InventoryItemDimension2Setup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION2SETUP:
                theDataList.add(inventoryItemDimension2SetupBean.getNumRows(InventoryItemDimension2Setup.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION2SETUP:
                theDataList = (List<Object>) inventoryItemDimension2SetupBean.getDataInRange(InventoryItemDimension2Setup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION2SETUP:
                theDataList.add(inventoryItemDimension2SetupBean.validateField(dataList.get(1).toString(), (InventoryItemDimension2Setup) dataList.get(2), userData));
                break;
            //Inventory Item Dimension 3 Setup
            case INSERT_INVENTORYITEMDIMENSION3SETUP:
                theDataList.add(inventoryItemDimension3SetupBean.insert((InventoryItemDimension3Setup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION3SETUP:
                theDataList.add(inventoryItemDimension3SetupBean.update((InventoryItemDimension3Setup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION3SETUP:
                theDataList.add(inventoryItemDimension3SetupBean.delete((InventoryItemDimension3Setup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION3SETUP:
                theDataList.add(inventoryItemDimension3SetupBean.getNumRows(InventoryItemDimension3Setup.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION3SETUP:
                theDataList = (List<Object>) inventoryItemDimension3SetupBean.getDataInRange(InventoryItemDimension3Setup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION3SETUP:
                theDataList.add(inventoryItemDimension3SetupBean.validateField(dataList.get(1).toString(), (InventoryItemDimension3Setup) dataList.get(2), userData));
                break;
            case REMOVE_INACTIVE:
                inventoryItemDimension3SetupBean.removeInactive(userData, dataList.get(1).toString());
                break;
            //Inventory Item Dimension Combinations DS
            case INSERT_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList.add(itemDimensionCombinationDSBean.insert((InventoryItemDimensionCombinationsDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList.add(itemDimensionCombinationDSBean.update((InventoryItemDimensionCombinationsDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList.add(itemDimensionCombinationDSBean.delete((InventoryItemDimensionCombinationsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList.add(itemDimensionCombinationDSBean.getNumRows(InventoryItemDimensionCombinationsDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList = (List<Object>) itemDimensionCombinationDSBean.getDataInRange(InventoryItemDimensionCombinationsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSIONCOMBINATIONSDS:
                theDataList.add(itemDimensionCombinationDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimensionCombinationsDS) dataList.get(2), userData));
                break;
            //Inventory Item Classification 1
            case INSERT_INVENTORYITEMCLASSIFICATION1:
                theDataList.add(itemClassification1Bean.insert((InventoryItemClassification1) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION1:
                theDataList.add(itemClassification1Bean.update((InventoryItemClassification1) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION1:
                theDataList.add(itemClassification1Bean.delete((InventoryItemClassification1) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION1:
                theDataList.add(itemClassification1Bean.getNumRows(InventoryItemClassification1.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION1:
                theDataList = (List<Object>) itemClassification1Bean.getDataInRange(InventoryItemClassification1.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION1:
                theDataList.add(itemClassification1Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification1) dataList.get(2), userData));
                break;


            //InventoryItemClassification2
            case INSERT_INVENTORYITEMCLASSIFICATION2:
                theDataList.add(itemClassification2Bean.insert((InventoryItemClassification2) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION2:
                theDataList.add(itemClassification2Bean.update((InventoryItemClassification2) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION2:
                theDataList.add(itemClassification2Bean.delete((InventoryItemClassification2) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION2:
                theDataList.add(itemClassification2Bean.getNumRows(InventoryItemClassification2.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION2:
                theDataList = (List<Object>) itemClassification2Bean.getDataInRange(InventoryItemClassification2.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION2:
                theDataList.add(itemClassification2Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification2) dataList.get(2), userData));
                break;
            //InventoryItemClassification3
            case INSERT_INVENTORYITEMCLASSIFICATION3:
                theDataList.add(itemClassification3Bean.insert((InventoryItemClassification3) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION3:
                theDataList.add(itemClassification3Bean.update((InventoryItemClassification3) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION3:
                theDataList.add(itemClassification3Bean.delete((InventoryItemClassification3) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION3:
                theDataList.add(itemClassification3Bean.getNumRows(InventoryItemClassification3.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION3:
                theDataList = (List<Object>) itemClassification3Bean.getDataInRange(InventoryItemClassification3.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION3:
                theDataList.add(itemClassification3Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification3) dataList.get(2), userData));
                break;
            //InventoryItemClassification4
            case INSERT_INVENTORYITEMCLASSIFICATION4:
                theDataList.add(itemClassification4Bean.insert((InventoryItemClassification4) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION4:
                theDataList.add(itemClassification4Bean.update((InventoryItemClassification4) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION4:
                theDataList.add(itemClassification4Bean.delete((InventoryItemClassification4) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION4:
                theDataList.add(itemClassification4Bean.getNumRows(InventoryItemClassification4.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION4:
                theDataList = (List<Object>) itemClassification4Bean.getDataInRange(InventoryItemClassification4.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION4:
                theDataList.add(itemClassification4Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification4) dataList.get(2), userData));
                break;
            //InventoryItemClassification5
            case INSERT_INVENTORYITEMCLASSIFICATION5:
                theDataList.add(itemClassification5Bean.insert((InventoryItemClassification5) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION5:
                theDataList.add(itemClassification5Bean.update((InventoryItemClassification5) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION5:
                theDataList.add(itemClassification5Bean.delete((InventoryItemClassification5) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION5:
                theDataList.add(itemClassification5Bean.getNumRows(InventoryItemClassification5.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION5:
                theDataList = (List<Object>) itemClassification5Bean.getDataInRange(InventoryItemClassification5.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION5:
                theDataList.add(itemClassification5Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification5) dataList.get(2), userData));
                break;
            //InventoryItemClassification6
            case INSERT_INVENTORYITEMCLASSIFICATION6:
                theDataList.add(itemClassification6Bean.insert((InventoryItemClassification6) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION6:
                theDataList.add(itemClassification6Bean.update((InventoryItemClassification6) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION6:
                theDataList.add(itemClassification6Bean.delete((InventoryItemClassification6) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION6:
                theDataList.add(itemClassification6Bean.getNumRows(InventoryItemClassification6.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION6:
                theDataList = (List<Object>) itemClassification6Bean.getDataInRange(InventoryItemClassification6.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION6:
                theDataList.add(itemClassification6Bean.validateField(dataList.get(1).toString(), (InventoryItemClassification6) dataList.get(2), userData));
                break;
            //InventoryDimension1GroupSetupDS
            case INSERT_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList.add(dimension1GroupSetupDSBean.insert((InventoryDimension1GroupSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList.add(dimension1GroupSetupDSBean.update((InventoryDimension1GroupSetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList.add(dimension1GroupSetupDSBean.delete((InventoryDimension1GroupSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList.add(dimension1GroupSetupDSBean.getNumRows(InventoryDimension1GroupSetupDS.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList = (List<Object>) dimension1GroupSetupDSBean.getDataInRange(InventoryDimension1GroupSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1GROUPSETUPDS:
                theDataList.add(dimension1GroupSetupDSBean.validateField(dataList.get(1).toString(), (InventoryDimension1GroupSetupDS) dataList.get(2), userData));
                break;
            //Inventory Dimension 2 Group Setup DS
            case INSERT_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList.add(dimension2GroupSetupDSBean.insert((InventoryDimension2GroupSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList.add(dimension2GroupSetupDSBean.update((InventoryDimension2GroupSetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList.add(dimension2GroupSetupDSBean.delete((InventoryDimension2GroupSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList.add(dimension2GroupSetupDSBean.getNumRows(InventoryDimension2GroupSetupDS.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList = (List<Object>) dimension2GroupSetupDSBean.getDataInRange(InventoryDimension2GroupSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION2GROUPSETUPDS:
                theDataList.add(dimension2GroupSetupDSBean.validateField(dataList.get(1).toString(), (InventoryDimension2GroupSetupDS) dataList.get(2), userData));
                break;
            //Inventory Dimension 3 Group Setup DS
            case INSERT_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList.add(dimension3GroupSetupDSBean.insert((InventoryDimension3GroupSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList.add(dimension3GroupSetupDSBean.update((InventoryDimension3GroupSetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList.add(dimension3GroupSetupDSBean.delete((InventoryDimension3GroupSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList.add(dimension3GroupSetupDSBean.getNumRows(InventoryDimension3GroupSetupDS.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList = (List<Object>) dimension3GroupSetupDSBean.getDataInRange(InventoryDimension3GroupSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION3GROUPSETUPDS:
                theDataList.add(dimension3GroupSetupDSBean.validateField(dataList.get(1).toString(), (InventoryDimension3GroupSetupDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension1SetupDS
            case INSERT_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList.add(itemDimension1SetupDSBean.insert((InventoryItemDimension1SetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList.add(itemDimension1SetupDSBean.update((InventoryItemDimension1SetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList.add(itemDimension1SetupDSBean.delete((InventoryItemDimension1SetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList.add(itemDimension1SetupDSBean.getNumRows(InventoryItemDimension1SetupDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList = (List<Object>) itemDimension1SetupDSBean.getDataInRange(InventoryItemDimension1SetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION1SETUPDS:
                theDataList.add(itemDimension1SetupDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension1SetupDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension2SetupDS
            case INSERT_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList.add(itemDimension2SetupDSBean.insert((InventoryItemDimension2SetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList.add(itemDimension2SetupDSBean.update((InventoryItemDimension2SetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList.add(itemDimension2SetupDSBean.delete((InventoryItemDimension2SetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList.add(itemDimension2SetupDSBean.getNumRows(InventoryItemDimension2SetupDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList = (List<Object>) itemDimension2SetupDSBean.getDataInRange(InventoryItemDimension2SetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION2SETUPDS:
                theDataList.add(itemDimension2SetupDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension2SetupDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension3SetupDS
            case INSERT_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList.add(itemDimension3SetupDSBean.insert((InventoryItemDimension3SetupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList.add(itemDimension3SetupDSBean.update((InventoryItemDimension3SetupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList.add(itemDimension3SetupDSBean.delete((InventoryItemDimension3SetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList.add(itemDimension3SetupDSBean.getNumRows(InventoryItemDimension3SetupDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList = (List<Object>) itemDimension3SetupDSBean.getDataInRange(InventoryItemDimension3SetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION3SETUPDS:
                theDataList.add(itemDimension3SetupDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension3SetupDS) dataList.get(2), userData));
                break;
            //InventoryItemGroup
            case INSERT_INVENTORYITEMGROUP:
                theDataList.add(inventoryItemGroupLocal.insert((InventoryItemGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMGROUP:
                theDataList.add(inventoryItemGroupLocal.update((InventoryItemGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMGROUP:
                theDataList.add(inventoryItemGroupLocal.delete((InventoryItemGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMGROUP:
                theDataList.add(inventoryItemGroupLocal.getNumRows(InventoryItemGroup.class, userData));
                break;
            case GETDATA_INVENTORYITEMGROUP:
                theDataList = (List<Object>) inventoryItemGroupLocal.getDataInRange(InventoryItemGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMGROUP:
                theDataList.add(inventoryItemGroupLocal.validateField(dataList.get(1).toString(), (InventoryItemGroup) dataList.get(2), userData));
                break;
            //InventoryReferenceType
            case INSERT_INVENTORYREFERENCETYPE:
                theDataList.add(referenceTypeBean.insert((InventoryReferenceType) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREFERENCETYPE:
                theDataList.add(referenceTypeBean.update((InventoryReferenceType) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREFERENCETYPE:
                theDataList.add(referenceTypeBean.delete((InventoryReferenceType) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREFERENCETYPE:
                theDataList.add(referenceTypeBean.getNumRows(InventoryReferenceType.class, userData));
                break;
            case GETDATA_INVENTORYREFERENCETYPE:
                theDataList = (List<Object>) referenceTypeBean.getDataInRange(InventoryReferenceType.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREFERENCETYPE:
                theDataList.add(referenceTypeBean.validateField(dataList.get(1).toString(), (InventoryReferenceType) dataList.get(2), userData));
                break;
            //InventoryReference
            case INSERT_INVENTORYREFERENCE:
                theDataList.add(referenceBean.insert((InventoryReference) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREFERENCE:
                theDataList.add(referenceBean.update((InventoryReference) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREFERENCE:
                theDataList.add(referenceBean.delete((InventoryReference) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREFERENCE:
                theDataList.add(referenceBean.getNumRows(InventoryReference.class, userData));
                break;
            case GETDATA_INVENTORYREFERENCE:
                theDataList = (List<Object>) referenceBean.getDataInRange(InventoryReference.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREFERENCE:
                theDataList.add(referenceBean.validateField(dataList.get(1).toString(), (InventoryReference) dataList.get(2), userData));
                break;
            case IMPORT_REFERENCES:
                referenceBean.importData(userData);
                break;
            //InventorySummary
            case INSERT_INVENTORYSUMMARY:
                theDataList.add(inventSumBean.insert((InventorySummary) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSUMMARY:
                theDataList.add(inventSumBean.update((InventorySummary) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSUMMARY:
                theDataList.add(inventSumBean.delete((InventorySummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSUMMARY:
                theDataList.add(inventSumBean.getNumRows(InventorySummary.class, userData));
                break;
            case GETDATA_INVENTORYSUMMARY:
                theDataList = (List<Object>) inventSumBean.getDataInRange(InventorySummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSUMMARY:
                theDataList.add(inventSumBean.validateField(dataList.get(1).toString(), (InventorySummary) dataList.get(2), userData));
                break;
            case FIX_BLANKET_ORDER_SUMMARIES:
                theDataList.add(inventSumBean.fixBlanketOrderSummaries(userData));
                break;
            //Below Minimum
            case PRINT_BELOW_MINIMUM:
                theDataList = belowMinBean.getReportResult(dataList, userData);
                break;
            //InventorySummaryDS
            case INSERT_INVENTORYSUMMARYDS:
                theDataList.add(inventSumDS.insert((InventorySummaryDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSUMMARYDS:
                theDataList.add(inventSumDS.update((InventorySummaryDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSUMMARYDS:
                theDataList.add(inventSumDS.delete((InventorySummaryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSUMMARYDS:
                theDataList.add(inventSumDS.getNumRows(InventorySummaryDS.class, userData));
                break;
            case GETDATA_INVENTORYSUMMARYDS:
                theDataList = (List<Object>) inventSumDS.getDataInRange(InventorySummaryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSUMMARYDS:
                theDataList.add(inventSumDS.validateField(dataList.get(1).toString(), (InventorySummaryDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension1SetupFormDS
            case INSERT_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList.add(itemDimension1SetupFormDSBean.insert((InventoryItemDimension1SetupFormDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList.add(itemDimension1SetupFormDSBean.update((InventoryItemDimension1SetupFormDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList.add(itemDimension1SetupFormDSBean.delete((InventoryItemDimension1SetupFormDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList.add(itemDimension1SetupFormDSBean.getNumRows(InventoryItemDimension1SetupFormDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList = (List<Object>) itemDimension1SetupFormDSBean.getDataInRange(InventoryItemDimension1SetupFormDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION1SETUPFORMDS:
                theDataList.add(itemDimension1SetupFormDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension1SetupFormDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension2SetupFormDS
            case INSERT_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList.add(itemDimension2SetupFormDSBean.insert((InventoryItemDimension2SetupFormDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList.add(itemDimension2SetupFormDSBean.update((InventoryItemDimension2SetupFormDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList.add(itemDimension2SetupFormDSBean.delete((InventoryItemDimension2SetupFormDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList.add(itemDimension2SetupFormDSBean.getNumRows(InventoryItemDimension2SetupFormDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList = (List<Object>) itemDimension2SetupFormDSBean.getDataInRange(InventoryItemDimension2SetupFormDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION2SETUPFORMDS:
                theDataList.add(itemDimension2SetupFormDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension2SetupFormDS) dataList.get(2), userData));
                break;
            //InventoryItemDimension3SetupFormDS
            case INSERT_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList.add(itemDimension3SetupFormDSBean.insert((InventoryItemDimension3SetupFormDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList.add(itemDimension3SetupFormDSBean.update((InventoryItemDimension3SetupFormDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList.add(itemDimension3SetupFormDSBean.delete((InventoryItemDimension3SetupFormDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList.add(itemDimension3SetupFormDSBean.getNumRows(InventoryItemDimension3SetupFormDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList = (List<Object>) itemDimension3SetupFormDSBean.getDataInRange(InventoryItemDimension3SetupFormDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMDIMENSION3SETUPFORMDS:
                theDataList.add(itemDimension3SetupFormDSBean.validateField(dataList.get(1).toString(), (InventoryItemDimension3SetupFormDS) dataList.get(2), userData));
                break;
            //InventoryTransactionsDS
            case INSERT_INVENTORYTRANSACTIONSDS:
                theDataList.add(transactionBeanDS.insert((InventoryTransactionsDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYTRANSACTIONSDS:
                theDataList.add(transactionBeanDS.update((InventoryTransactionsDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYTRANSACTIONSDS:
                theDataList.add(transactionBeanDS.delete((InventoryTransactionsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYTRANSACTIONSDS:
                theDataList.add(transactionBeanDS.getNumRows(InventoryTransactionsDS.class, userData));
                break;
            case GETDATA_INVENTORYTRANSACTIONSDS:
                theDataList = (List<Object>) transactionBeanDS.getDataInRange(InventoryTransactionsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYTRANSACTIONSDS:
                theDataList.add(transactionBeanDS.validateField(dataList.get(1).toString(), (InventoryTransactionsDS) dataList.get(2), userData));
                break;
            //InventoryTransactions
            case INSERT_INVENTORYTRANSACTIONS:
                theDataList.add(transactionBean.insert((InventoryTransactions) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYTRANSACTIONS:
                theDataList.add(transactionBean.update((InventoryTransactions) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYTRANSACTIONS:
                theDataList.add(transactionBean.delete((InventoryTransactions) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYTRANSACTIONS:
                theDataList.add(transactionBean.getNumRows(InventoryTransactions.class, userData));
                break;
            case GETDATA_INVENTORYTRANSACTIONS:
                theDataList = (List<Object>) transactionBean.getDataInRange(InventoryTransactions.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYTRANSACTIONS:
                theDataList.add(transactionBean.validateField(dataList.get(1).toString(), (InventoryTransactions) dataList.get(2), userData));
                break;
            //InventoryItemAccessGroup
            case INSERT_INVENTORYITEMACCESSGROUP:
                theDataList.add(itemAccessGroupBean.insert((InventoryItemAccessGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMACCESSGROUP:
                theDataList.add(itemAccessGroupBean.update((InventoryItemAccessGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMACCESSGROUP:
                theDataList.add(itemAccessGroupBean.delete((InventoryItemAccessGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMACCESSGROUP:
                theDataList.add(itemAccessGroupBean.getNumRows(InventoryItemAccessGroup.class, userData));
                break;
            case GETDATA_INVENTORYITEMACCESSGROUP:
                theDataList = (List<Object>) itemAccessGroupBean.getDataInRange(InventoryItemAccessGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMACCESSGROUP:
                theDataList.add(itemAccessGroupBean.validateField(dataList.get(1).toString(), (InventoryItemAccessGroup) dataList.get(2), userData));
                break;
            //InventoryItemAccessGroupEmployees
            case INSERT_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList.add(itemAccessGroupEmployeesBean.insert((InventoryItemAccessGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList.add(itemAccessGroupEmployeesBean.update((InventoryItemAccessGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList.add(itemAccessGroupEmployeesBean.delete((InventoryItemAccessGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList.add(itemAccessGroupEmployeesBean.getNumRows(InventoryItemAccessGroupEmployees.class, userData));
                break;
            case GETDATA_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList = (List<Object>) itemAccessGroupEmployeesBean.getDataInRange(InventoryItemAccessGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMACCESSGROUPEMPLOYEES:
                theDataList.add(itemAccessGroupEmployeesBean.validateField(dataList.get(1).toString(), (InventoryItemAccessGroupEmployees) dataList.get(2), userData));
                break;
            //InventoryJournalLinesDS
            case INSERT_INVENTORYJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.insert((InventoryJournalLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.update((InventoryJournalLinesDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.delete((InventoryJournalLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.getNumRows(InventoryJournalLinesDS.class, userData));
                break;
            case GETDATA_INVENTORYJOURNALLINESDS:
                theDataList = (List<Object>) journalLinesDSBean.getDataInRange(InventoryJournalLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYJOURNALLINESDS:
                theDataList.add(journalLinesDSBean.validateField(dataList.get(1).toString(), (InventoryJournalLinesDS) dataList.get(2), userData));
                break;

            //InventoryClassificationHierarchy
            case INSERT_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList.add(classificationHierarchyBean.insert((InventoryClassificationHierarchy) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList.add(classificationHierarchyBean.update((InventoryClassificationHierarchy) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList.add(classificationHierarchyBean.delete((InventoryClassificationHierarchy) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList.add(classificationHierarchyBean.getNumRows(InventoryClassificationHierarchy.class, userData));
                break;
            case GETDATA_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList = (List<Object>) classificationHierarchyBean.getDataInRange(InventoryClassificationHierarchy.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYCLASSIFICATIONHIERARCHY:
                theDataList.add(classificationHierarchyBean.validateField(dataList.get(1).toString(), (InventoryClassificationHierarchy) dataList.get(2), userData));
                break;

            //InventoryColourDesignMaster
            case INSERT_INVENTORYCOLOURDESIGNMASTER:
                theDataList.add(colourDisignBean.insert((InventoryColourDesignMaster) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYCOLOURDESIGNMASTER:
                theDataList.add(colourDisignBean.update((InventoryColourDesignMaster) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYCOLOURDESIGNMASTER:
                theDataList.add(colourDisignBean.delete((InventoryColourDesignMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYCOLOURDESIGNMASTER:
                theDataList.add(colourDisignBean.getNumRows(InventoryColourDesignMaster.class, userData));
                break;
            case GETDATA_INVENTORYCOLOURDESIGNMASTER:
                theDataList = (List<Object>) colourDisignBean.getDataInRange(InventoryColourDesignMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYCOLOURDESIGNMASTER:
                theDataList.add(colourDisignBean.validateField(dataList.get(1).toString(), (InventoryColourDesignMaster) dataList.get(2), userData));
                break;
            //InventoryItemClassification
            case INSERT_INVENTORYITEMCLASSIFICATION:
                theDataList.add(itemClassificationBean.insert((InventoryItemClassification) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMCLASSIFICATION:
                theDataList.add(itemClassificationBean.update((InventoryItemClassification) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMCLASSIFICATION:
                theDataList.add(itemClassificationBean.delete((InventoryItemClassification) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMCLASSIFICATION:
                theDataList.add(itemClassificationBean.getNumRows(InventoryItemClassification.class, userData));
                break;
            case GETDATA_INVENTORYITEMCLASSIFICATION:
                theDataList = (List<Object>) itemClassificationBean.getDataInRange(InventoryItemClassification.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMCLASSIFICATION:
                theDataList.add(itemClassificationBean.validateField(dataList.get(1).toString(), (InventoryItemClassification) dataList.get(2), userData));
                break;
            //InventoryJournalMasterDS
            case INSERT_INVENTORYJOURNALMASTERDS:
                theDataList.add(journalMasterDSBean.insert((InventoryJournalMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYJOURNALMASTERDS:
                theDataList.add(journalMasterDSBean.update((InventoryJournalMasterDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYJOURNALMASTERDS:
                theDataList.add(journalMasterDSBean.delete((InventoryJournalMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYJOURNALMASTERDS:
                theDataList.add(journalMasterDSBean.getNumRows(InventoryJournalMasterDS.class, userData));
                break;
            case GETDATA_INVENTORYJOURNALMASTERDS:
                theDataList = (List<Object>) journalMasterDSBean.getDataInRange(InventoryJournalMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYJOURNALMASTERDS:
                theDataList.add(journalMasterDSBean.validateField(dataList.get(1).toString(), (InventoryJournalMasterDS) dataList.get(2), userData));
                break;
            //InventoryParameters
            case INSERT_INVENTORYPARAMETERS:
                theDataList.add(parametersBean.insert((InventoryParameters) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYPARAMETERS:
                theDataList.add(parametersBean.update((InventoryParameters) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYPARAMETERS:
                theDataList.add(parametersBean.delete((InventoryParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYPARAMETERS:
                theDataList.add(parametersBean.getNumRows(InventoryParameters.class, userData));
                break;
            case GETDATA_INVENTORYPARAMETERS:
                theDataList = (List<Object>) parametersBean.getDataInRange(InventoryParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYPARAMETERS:
                theDataList.add(parametersBean.validateField(dataList.get(1).toString(), (InventoryParameters) dataList.get(2), userData));
                break;
            //InventoryItemMasterLookupDS
            case INSERT_INVENTORYITEMMASTERLOOKUPDS:
                theDataList.add(itemMasterLookupDSBean.insert((InventoryItemMasterLookupDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYITEMMASTERLOOKUPDS:
                theDataList.add(itemMasterLookupDSBean.update((InventoryItemMasterLookupDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYITEMMASTERLOOKUPDS:
                theDataList.add(itemMasterLookupDSBean.delete((InventoryItemMasterLookupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYITEMMASTERLOOKUPDS:
                theDataList.add(itemMasterLookupDSBean.getNumRows(InventoryItemMasterLookupDS.class, userData));
                break;
            case GETDATA_INVENTORYITEMMASTERLOOKUPDS:
                theDataList = (List<Object>) itemMasterLookupDSBean.getDataInRange(InventoryItemMasterLookupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYITEMMASTERLOOKUPDS:
                theDataList.add(itemMasterLookupDSBean.validateField(dataList.get(1).toString(), (InventoryItemMasterLookupDS) dataList.get(2), userData));
                break;
            //InventoryReferenceDS
            case INSERT_INVENTORYREFERENCEDS:
                theDataList.add(refBeanDS.insert((InventoryReferenceDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREFERENCEDS:
                theDataList.add(refBeanDS.update((InventoryReferenceDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREFERENCEDS:
                theDataList.add(refBeanDS.delete((InventoryReferenceDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREFERENCEDS:
                theDataList.add(refBeanDS.getNumRows(InventoryReferenceDS.class, userData));
                break;
            case GETDATA_INVENTORYREFERENCEDS:
                theDataList = (List<Object>) refBeanDS.getDataInRange(InventoryReferenceDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREFERENCEDS:
                theDataList.add(refBeanDS.validateField(dataList.get(1).toString(), (InventoryReferenceDS) dataList.get(2), userData));
                break;
            //InventoryBarcodesDS
            case INSERT_INVENTORYBARCODESDS:
                theDataList.add(barcodeBeanDS.insert((InventoryBarcodesDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBARCODESDS:
                theDataList.add(barcodeBeanDS.update((InventoryBarcodesDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBARCODESDS:
                theDataList.add(barcodeBeanDS.delete((InventoryBarcodesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBARCODESDS:
                theDataList.add(barcodeBeanDS.getNumRows(InventoryBarcodesDS.class, userData));
                break;
            case GETDATA_INVENTORYBARCODESDS:
                theDataList = (List<Object>) barcodeBeanDS.getDataInRange(InventoryBarcodesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBARCODESDS:
                theDataList.add(barcodeBeanDS.validateField(dataList.get(1).toString(), (InventoryBarcodesDS) dataList.get(2), userData));
                break;
            //OnHand Report
            case PRINT_SUMMARY:
                theDataList = onHandReportBean.getReportResult(dataList, userData);
                break;
            //Journals Report
            case PRINT_STOCK_JOURNALS:
                theDataList = journalReportBean.getReportResult(dataList, userData);
                break;
            //InventoryRemoveSerialBatch
            case INSERT_INVENTORYREMOVESERIALBATCH:
                theDataList.add(serialBatchBean.insert((InventoryRemoveSerialBatch) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREMOVESERIALBATCH:
                theDataList.add(serialBatchBean.update((InventoryRemoveSerialBatch) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREMOVESERIALBATCH:
                theDataList.add(serialBatchBean.delete((InventoryRemoveSerialBatch) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREMOVESERIALBATCH:
                theDataList.add(serialBatchBean.getNumRows(InventoryRemoveSerialBatch.class, userData));
                break;
            case GETDATA_INVENTORYREMOVESERIALBATCH:
                theDataList = (List<Object>) serialBatchBean.getDataInRange(InventoryRemoveSerialBatch.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREMOVESERIALBATCH:
                theDataList.add(serialBatchBean.validateField(dataList.get(1).toString(), (InventoryRemoveSerialBatch) dataList.get(2), userData));
                break;
            case CHECK_REMOVED_REG:
                theDataList.add(serialBatchBean.checkRegQuantity(dataList.get(1).toString(), dataList.get(2).toString(), userData));
                break;
            case CHECK_REMOVED_REG_ALL:
                theDataList.add(serialBatchBean.checkAllRemovedQty(dataList.get(1).toString(), userData));
                break;
            case DELETE_POST_DATA:
                serialBatchBean.deleteRemovedSB((String) dataList.get(1), userData);
                break;
            //SERIAL BATCHdataList.get(1).toString()
            //InventorySerialBatch
            case INSERT_INVENTORYSERIALBATCH:
                theDataList.add(serialBatchBean.insert((InventorySerialBatch) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSERIALBATCH:
                theDataList.add(serialBatchBean.update((InventorySerialBatch) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSERIALBATCH:
                theDataList.add(serialBatchBean.delete((InventorySerialBatch) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSERIALBATCH:
                theDataList.add(serialBatchBean.getNumRows(InventorySerialBatch.class, userData));
                break;
            case GETDATA_INVENTORYSERIALBATCH:
                theDataList = (List<Object>) serialBatchBean.getDataInRange(InventorySerialBatch.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSERIALBATCH:
                theDataList.add(serialBatchBean.validateField(dataList.get(1).toString(), (InventorySerialBatch) dataList.get(2), userData));
                break;
            //InventoryRequirementsPlanning
            case INSERT_INVENTORYREQUIREMENTSPLANNING:
                theDataList.add(requirementsPlanningBean.insert((InventoryRequirementsPlanning) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREQUIREMENTSPLANNING:
                theDataList.add(requirementsPlanningBean.update((InventoryRequirementsPlanning) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREQUIREMENTSPLANNING:
                theDataList.add(requirementsPlanningBean.delete((InventoryRequirementsPlanning) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREQUIREMENTSPLANNING:
                theDataList.add(requirementsPlanningBean.getNumRows(InventoryRequirementsPlanning.class, userData));
                break;
            case GETDATA_INVENTORYREQUIREMENTSPLANNING:
                theDataList = (List<Object>) requirementsPlanningBean.getDataInRange(InventoryRequirementsPlanning.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREQUIREMENTSPLANNING:
                theDataList.add(requirementsPlanningBean.validateField(dataList.get(1).toString(), (InventoryRequirementsPlanning) dataList.get(2), userData));
                break;
            case CLEAN_UP_REQUIREMENTS:
                theDataList.add(requirementsPlanningBean.cleanupRequirementsPlanningAndPegging(userData));
                break;
            //InventoryPallet
            case INSERT_INVENTORYPALLET:
                theDataList.add(palletBean.insert((InventoryPallet) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYPALLET:
                theDataList.add(palletBean.update((InventoryPallet) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYPALLET:
                theDataList.add(palletBean.delete((InventoryPallet) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYPALLET:
                theDataList.add(palletBean.getNumRows(InventoryPallet.class, userData));
                break;
            case GETDATA_INVENTORYPALLET:
                theDataList = (List<Object>) palletBean.getDataInRange(InventoryPallet.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYPALLET:
                theDataList.add(palletBean.validateField(dataList.get(1).toString(), (InventoryPallet) dataList.get(2), userData));
                break;
            //InventoryLocation
            case INSERT_INVENTORYLOCATION:
                theDataList.add(locationBean.insert((InventoryLocation) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYLOCATION:
                theDataList.add(locationBean.update((InventoryLocation) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYLOCATION:
                theDataList.add(locationBean.delete((InventoryLocation) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYLOCATION:
                theDataList.add(locationBean.getNumRows(InventoryLocation.class, userData));
                break;
            case GETDATA_INVENTORYLOCATION:
                theDataList = (List<Object>) locationBean.getDataInRange(InventoryLocation.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYLOCATION:
                theDataList.add(locationBean.validateField(dataList.get(1).toString(), (InventoryLocation) dataList.get(2), userData));
                break;
            //InventoryMoveStockMaster
            case INSERT_INVENTORYMOVESTOCKMASTER:
                theDataList.add(moveStockMasterBean.insert((InventoryMoveStockMaster) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYMOVESTOCKMASTER:
                theDataList.add(moveStockMasterBean.update((InventoryMoveStockMaster) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYMOVESTOCKMASTER:
                theDataList.add(moveStockMasterBean.delete((InventoryMoveStockMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYMOVESTOCKMASTER:
                theDataList.add(moveStockMasterBean.getNumRows(InventoryMoveStockMaster.class, userData));
                break;
            case GETDATA_INVENTORYMOVESTOCKMASTER:
                theDataList = (List<Object>) moveStockMasterBean.getDataInRange(InventoryMoveStockMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYMOVESTOCKMASTER:
                theDataList.add(moveStockMasterBean.validateField(dataList.get(1).toString(), (InventoryMoveStockMaster) dataList.get(2), userData));
                break;
            case MOVE_STOCK:
                theDataList.add(moveStockMasterBean.moveStock((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData));
                break;
            case POPULATE_MOVE_STOCK:
                theDataList.add(moveStockMasterBean.populateTable((String) dataList.get(1), (String) dataList.get(2), userData));
                break;
            case POPULATE_MOVE_RESERVED_STOCK:
                theDataList.add(moveStockMasterBean.populateReserveTable((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), (String) dataList.get(4), userData));
                break;
            case MOVE_RESERVED_STOCK:
                theDataList.add(moveStockMasterBean.moveReservedStock((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), userData));
                break;
            //InventoryMoveStockMasterDS
            case INSERT_INVENTORYMOVESTOCKMASTERDS:
                theDataList.add(moveStockMasterDSBean.insert((InventoryMoveStockMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYMOVESTOCKMASTERDS:
                theDataList.add(moveStockMasterDSBean.update((InventoryMoveStockMasterDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYMOVESTOCKMASTERDS:
                theDataList.add(moveStockMasterDSBean.delete((InventoryMoveStockMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYMOVESTOCKMASTERDS:
                theDataList.add(moveStockMasterDSBean.getNumRows(InventoryMoveStockMasterDS.class, userData));
                break;
            case GETDATA_INVENTORYMOVESTOCKMASTERDS:
                theDataList = (List<Object>) moveStockMasterDSBean.getDataInRange(InventoryMoveStockMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYMOVESTOCKMASTERDS:
                theDataList.add(moveStockMasterDSBean.validateField(dataList.get(1).toString(), (InventoryMoveStockMasterDS) dataList.get(2), userData));
                break;
            //InventoryMoveStockLines
            case INSERT_INVENTORYMOVESTOCKLINES:
                theDataList.add(moveStockLinesBean.insert((InventoryMoveStockLines) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYMOVESTOCKLINES:
                theDataList.add(moveStockLinesBean.update((InventoryMoveStockLines) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYMOVESTOCKLINES:
                theDataList.add(moveStockLinesBean.delete((InventoryMoveStockLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYMOVESTOCKLINES:
                theDataList.add(moveStockLinesBean.getNumRows(InventoryMoveStockLines.class, userData));
                break;
            case GETDATA_INVENTORYMOVESTOCKLINES:
                theDataList = (List<Object>) moveStockLinesBean.getDataInRange(InventoryMoveStockLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYMOVESTOCKLINES:
                theDataList.add(moveStockLinesBean.validateField(dataList.get(1).toString(), (InventoryMoveStockLines) dataList.get(2), userData));
                break;
            //InventoryMoveStockSummary
            case INSERT_INVENTORYMOVESTOCKSUMMARY:
                theDataList.add(moveStockSummaryBean.insert((InventoryMoveStockSummary) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYMOVESTOCKSUMMARY:
                theDataList.add(moveStockSummaryBean.update((InventoryMoveStockSummary) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYMOVESTOCKSUMMARY:
                theDataList.add(moveStockSummaryBean.delete((InventoryMoveStockSummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYMOVESTOCKSUMMARY:
                theDataList.add(moveStockSummaryBean.getNumRows(InventoryMoveStockSummary.class, userData));
                break;
            case GETDATA_INVENTORYMOVESTOCKSUMMARY:
                theDataList = (List<Object>) moveStockSummaryBean.getDataInRange(InventoryMoveStockSummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYMOVESTOCKSUMMARY:
                theDataList.add(moveStockSummaryBean.validateField(dataList.get(1).toString(), (InventoryMoveStockSummary) dataList.get(2), userData));
                break;
//InventoryMoveStockSummaryDS
            case INSERT_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList.add(moveStockSummaryDSBean.insert((InventoryMoveStockSummaryDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList.add(moveStockSummaryDSBean.update((InventoryMoveStockSummaryDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList.add(moveStockSummaryDSBean.delete((InventoryMoveStockSummaryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList.add(moveStockSummaryDSBean.getNumRows(InventoryMoveStockSummaryDS.class, userData));
                break;
            case GETDATA_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList = (List<Object>) moveStockSummaryDSBean.getDataInRange(InventoryMoveStockSummaryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYMOVESTOCKSUMMARYDS:
                theDataList.add(moveStockSummaryDSBean.validateField(dataList.get(1).toString(), (InventoryMoveStockSummaryDS) dataList.get(2), userData));
                break;
            //InventoryRegister
            case INSERT_INVENTORYREGISTER:
                theDataList.add(registerBean.insert((InventoryRegister) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREGISTER:
                theDataList.add(registerBean.update((InventoryRegister) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREGISTER:
                theDataList.add(registerBean.delete((InventoryRegister) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREGISTER:
                theDataList.add(registerBean.getNumRows(InventoryRegister.class, userData));
                break;
            case GETDATA_INVENTORYREGISTER:
                theDataList = (List<Object>) registerBean.getDataInRange(InventoryRegister.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREGISTER:
                theDataList.add(registerBean.validateField(dataList.get(1).toString(), (InventoryRegister) dataList.get(2), userData));
                break;
            case GET_REGISTER_COLUMNS:
                theDataList = registerBean.isRegistrationRequired((String) dataList.get(1), userData);
                break;
            //InventoryRemoveRegister
            case INSERT_INVENTORYREMOVEREGISTER:
                theDataList.add(removeRegisterBean.insert((InventoryRemoveRegister) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREMOVEREGISTER:
                theDataList.add(removeRegisterBean.update((InventoryRemoveRegister) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREMOVEREGISTER:
                theDataList.add(removeRegisterBean.delete((InventoryRemoveRegister) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREMOVEREGISTER:
                theDataList.add(removeRegisterBean.getNumRows(InventoryRemoveRegister.class, userData));
                break;
            case GETDATA_INVENTORYREMOVEREGISTER:
                theDataList = (List<Object>) removeRegisterBean.getDataInRange(InventoryRemoveRegister.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREMOVEREGISTER:
                theDataList.add(removeRegisterBean.validateField(dataList.get(1).toString(), (InventoryRemoveRegister) dataList.get(2), userData));
                break;
            //InventoryAdditionalDimensionsDS
            case INSERT_INVENTORYADDITIONALDIMENSIONS:
                theDataList.add(additionalDimensionsBean.insert((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYADDITIONALDIMENSIONS:
                theDataList.add(additionalDimensionsBean.update((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYADDITIONALDIMENSIONS:
                theDataList.add(additionalDimensionsBean.delete((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYADDITIONALDIMENSIONS:
                theDataList.add(additionalDimensionsBean.getNumRows(InventoryAdditionalDimensionsDS.class, userData));
                break;
            case GETDATA_INVENTORYADDITIONALDIMENSIONS:
                theDataList = (List<Object>) additionalDimensionsBean.getDataInRange(InventoryAdditionalDimensionsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            //InventoryAdditionalDimensionsDS
            case INSERT_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList.add(additionalDimensionsDSBean.insert((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList.add(additionalDimensionsDSBean.update((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList.add(additionalDimensionsDSBean.delete((InventoryAdditionalDimensionsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList.add(additionalDimensionsDSBean.getNumRows(InventoryAdditionalDimensionsDS.class, userData));
                break;
            case GETDATA_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList = (List<Object>) additionalDimensionsDSBean.getDataInRange(InventoryAdditionalDimensionsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYADDITIONALDIMENSIONSDS:
                theDataList.add(additionalDimensionsDSBean.validateField(dataList.get(1).toString(), (InventoryAdditionalDimensionsDS) dataList.get(2), userData));
                break;

            //Stock Valuation Report
            case PRINT_STOCK_VALUATION_REPORT:
                theDataList = stockValuationReportBean.getReportResult(dataList, userData);
                break;

            case PRINT_STOCK_BY_LOCATION_REPORT:
                theDataList = stockByLocationReportBean.getReportResult(dataList, userData);
                break;
            case PRINT_MOVEMENT_JOURNAL_SUMMARY:
                theDataList = movementJournalSummaryBean.getReportResult(dataList, userData);
                break;
            case PRINT_MOVEMENT_JOURNAL_DETAIL:
                theDataList = movementJournalDetailBean.getReportResult(dataList, userData);
                break;
            //InventoryReReservePickListDS
            case INSERT_INVENTORYAGEBINS:
                theDataList.add(ageBinBean.insert((InventoryAgeBins) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYAGEBINS:
                theDataList.add(ageBinBean.update((InventoryAgeBins) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYAGEBINS:
                theDataList.add(ageBinBean.delete((InventoryAgeBins) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYAGEBINS:
                theDataList.add(ageBinBean.getNumRows(InventoryAgeBins.class, userData));
                break;
            case GETDATA_INVENTORYAGEBINS:
                theDataList = (List<Object>) ageBinBean.getDataInRange(InventoryAgeBins.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYAGEBINS:
                theDataList.add(ageBinBean.validateField(dataList.get(1).toString(), (InventoryAgeBins) dataList.get(2), userData));
                break;
            case PRINT_INVENTAGEING:
                theDataList = ageingReportBean.getReportResult(dataList, AgeingReportTypes.SUMMARY, cmd.getReportConfig(), userData);
                break;
            case GET_BIN_LABELS:
                theDataList.addAll(ageBinBean.getBinLabels(userData));
                break;
            case PRINT_INVENTAGEING_DETAIL:
                theDataList = ageingReportBean.getReportResult(dataList, AgeingReportTypes.DETAIL, cmd.getReportConfig(), userData);
                break;
            case PRINT_INVENTAGEING_FULL_DETAIL:
                theDataList = ageingReportBean.getReportResult(dataList, AgeingReportTypes.FULL_DETAIL, cmd.getReportConfig(), userData);
                break;
            //InventoryStockSettlementHistory
            case INSERT_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList.add(settleHistBean.insert((InventoryStockSettlementHistory) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList.add(settleHistBean.update((InventoryStockSettlementHistory) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList.add(settleHistBean.delete((InventoryStockSettlementHistory) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList.add(settleHistBean.getNumRows(InventoryStockSettlementHistory.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList = (List<Object>) settleHistBean.getDataInRange(InventoryStockSettlementHistory.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKSETTLEMENTHISTORY:
                theDataList.add(settleHistBean.validateField(dataList.get(1).toString(), (InventoryStockSettlementHistory) dataList.get(2), userData));
                break;
            //InventoryStockSettlement
            case INSERT_INVENTORYSTOCKSETTLEMENT:
                theDataList.add(settlementBean.insert((InventoryStockSettlement) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKSETTLEMENT:
                theDataList.add(settlementBean.update((InventoryStockSettlement) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKSETTLEMENT:
                theDataList.add(settlementBean.delete((InventoryStockSettlement) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKSETTLEMENT:
                theDataList.add(settlementBean.getNumRows(InventoryStockSettlement.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKSETTLEMENT:
                theDataList = (List<Object>) settlementBean.getDataInRange(InventoryStockSettlement.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKSETTLEMENT:
                theDataList.add(settlementBean.validateField(dataList.get(1).toString(), (InventoryStockSettlement) dataList.get(2), userData));
                break;
            case RUN_STOCKSETTLEMENT:
                this.settlementLogicBean.doStockClose((InventoryStockSettlement) dataList.get(1), userData);
                break;
            case ROLL_BACK_STOCKSETTLEMENT:
                this.settlementLogicBean.doRollBack((InventoryStockSettlement) dataList.get(1), userData);
                break;
            //InventoryStocktakeRegister
            case INSERT_INVENTORYSTOCKTAKEREGISTER:
                theDataList.add(stockTakeRegisterBean.insert((InventoryStocktakeRegister) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKTAKEREGISTER:
                theDataList.add(stockTakeRegisterBean.update((InventoryStocktakeRegister) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKTAKEREGISTER:
                theDataList.add(stockTakeRegisterBean.delete((InventoryStocktakeRegister) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKTAKEREGISTER:
                theDataList.add(stockTakeRegisterBean.getNumRows(InventoryStocktakeRegister.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKTAKEREGISTER:
                theDataList = (List<Object>) stockTakeRegisterBean.getDataInRange(InventoryStocktakeRegister.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKTAKEREGISTER:
                theDataList.add(stockTakeRegisterBean.validateField(dataList.get(1).toString(), (InventoryStocktakeRegister) dataList.get(2), userData));
                break;
            case GET_ST_REGISTER_COLUMNS:
                theDataList = stockTakeRegisterBean.isRegistrationRequired((String) dataList.get(1), userData);
                break;
            case PRINT_STOCK_TAKE_VARIANCE:
                theDataList = stockTakeVarianceReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_STOCK_TAKE_REPORT:
                theDataList = stockTakeReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_STOCK_TAKE_RECOUNT_REPORT:
                theDataList = stockTakeRecountReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryStocktakeCaptureDS case INSERT_INVENTORYSTOCKTAKEREGISTER:
            case INSERT_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList.add(stockTakeCaptureBean.insert((InventoryStocktakeCaptureDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList.add(stockTakeCaptureBean.delete((InventoryStocktakeCaptureDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList.add(stockTakeCaptureBean.update((InventoryStocktakeCaptureDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList.add(stockTakeCaptureBean.getNumRows(InventoryStocktakeCaptureDS.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList = (List<Object>) stockTakeCaptureBean.getDataInRange(InventoryStocktakeCaptureDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKTAKECAPTUREDS:
                theDataList.add(stockTakeCaptureBean.validateField(dataList.get(1).toString(), (InventoryStocktakeCaptureDS) dataList.get(2), userData));
                break;
            case SET_CLOSE_DATE_NULL:
                transactionBean.setStockCloseDate(null, userData);
                break;
            //Inventory Dimension 1 Lines
            case INSERT_INVENTORYDIMENSION1LINES:
                theDataList.add(dimension1LinesBean.insert((InventoryDimension1Lines) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1LINES:
                theDataList.add(dimension1LinesBean.update((InventoryDimension1Lines) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1LINES:
                theDataList.add(dimension1LinesBean.delete((InventoryDimension1Lines) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1LINES:
                theDataList.add(dimension1LinesBean.getNumRows(InventoryDimension1Lines.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1LINES:
                theDataList = (List<Object>) dimension1LinesBean.getDataInRange(InventoryDimension1Lines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1LINES:
                theDataList.add(dimension1LinesBean.validateField(dataList.get(1).toString(), (InventoryDimension1Lines) dataList.get(2), userData));
                break;
            //Inventory Dimension 1 Lines DS
            case INSERT_INVENTORYDIMENSION1LINESDS:
                theDataList.add(dimension1LinesDSBean.insert((InventoryDimension1LinesDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1LINESDS:
                theDataList.add(dimension1LinesDSBean.update((InventoryDimension1LinesDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1LINESDS:
                theDataList.add(dimension1LinesDSBean.delete((InventoryDimension1LinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1LINESDS:
                theDataList.add(dimension1LinesDSBean.getNumRows(InventoryDimension1LinesDS.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1LINESDS:
                theDataList = (List<Object>) dimension1LinesDSBean.getDataInRange(InventoryDimension1LinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1LINESDS:
                theDataList.add(dimension1LinesDSBean.validateField(dataList.get(1).toString(), (InventoryDimension1LinesDS) dataList.get(2), userData));
                break;
            //PRint Stock Usage Report
            case PRINT_STOCK_USAGE:
                theDataList = stockUsageReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryProductGroup
            case INSERT_INVENTORYPRODUCTGROUP:
                theDataList.add(productionGroupBean.insert((InventoryProductGroup) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYPRODUCTGROUP:
                theDataList.add(productionGroupBean.update((InventoryProductGroup) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYPRODUCTGROUP:
                theDataList.add(productionGroupBean.delete((InventoryProductGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYPRODUCTGROUP:
                theDataList.add(productionGroupBean.getNumRows(InventoryProductGroup.class, userData));
                break;
            case GETDATA_INVENTORYPRODUCTGROUP:
                theDataList = (List<Object>) productionGroupBean.getDataInRange(InventoryProductGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYPRODUCTGROUP:
                theDataList.add(productionGroupBean.validateField(dataList.get(1).toString(), (InventoryProductGroup) dataList.get(2), userData));
                break;
            case PRINT_DIMENSION1_REPORT:
                theDataList = dimension1ReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryDimension1WhereUsed
            case INSERT_INVENTORYDIMENSION1WHEREUSED:
                theDataList.add(dimension1WhereUsedBean.insert((InventoryDimension1WhereUsed) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1WHEREUSED:
                theDataList.add(dimension1WhereUsedBean.update((InventoryDimension1WhereUsed) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1WHEREUSED:
                theDataList.add(dimension1WhereUsedBean.delete((InventoryDimension1WhereUsed) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1WHEREUSED:
                theDataList.add(dimension1WhereUsedBean.getNumRows(InventoryDimension1WhereUsed.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1WHEREUSED:
                theDataList = (List<Object>) dimension1WhereUsedBean.getDataInRange(InventoryDimension1WhereUsed.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1WHEREUSED:
                theDataList.add(dimension1WhereUsedBean.validateField(dataList.get(1).toString(), (InventoryDimension1WhereUsed) dataList.get(2), userData));
                break;
            //InventoryRequirementsPlanningDS
            case INSERT_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList.add(requirementsPlanningDSBean.insert((InventoryRequirementsPlanningDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList.add(requirementsPlanningDSBean.update((InventoryRequirementsPlanningDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList.add(requirementsPlanningDSBean.delete((InventoryRequirementsPlanningDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList.add(requirementsPlanningDSBean.getNumRows(InventoryRequirementsPlanningDS.class, userData));
                break;
            case GETDATA_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList = (List<Object>) requirementsPlanningDSBean.getDataInRange(InventoryRequirementsPlanningDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREQUIREMENTSPLANNINGDS:
                theDataList.add(requirementsPlanningDSBean.validateField(dataList.get(1).toString(), (InventoryRequirementsPlanningDS) dataList.get(2), userData));
                break;
            //InventoryRequirementsPlanningHistoryDS
            case INSERT_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList.add(requirementsPlanningHistoryDSBean.insert((InventoryRequirementsPlanningHistoryDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList.add(requirementsPlanningHistoryDSBean.update((InventoryRequirementsPlanningHistoryDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList.add(requirementsPlanningHistoryDSBean.delete((InventoryRequirementsPlanningHistoryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList.add(requirementsPlanningHistoryDSBean.getNumRows(InventoryRequirementsPlanningHistoryDS.class, userData));
                break;
            case GETDATA_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList = (List<Object>) requirementsPlanningHistoryDSBean.getDataInRange(InventoryRequirementsPlanningHistoryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYREQUIREMENTSPLANNINGHISTORYDS:
                theDataList.add(requirementsPlanningHistoryDSBean.validateField(dataList.get(1).toString(), (InventoryRequirementsPlanningHistoryDS) dataList.get(2), userData));
                break;
            case PRINT_OPEN_DISPATCH_ORDERS:
                theDataList = openDispatchOrdersReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryWarehouseStockEnquiry
            case INSERT_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.insert((InventoryWarehouseStockEnquiry) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.update((InventoryWarehouseStockEnquiry) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.delete((InventoryWarehouseStockEnquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.getNumRows(InventoryWarehouseStockEnquiry.class, userData));
                break;
            case GETDATA_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList = (List<Object>) warehouseStockEnquiryBean.getDataInRange(InventoryWarehouseStockEnquiry.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.validateField(dataList.get(1).toString(), (InventoryWarehouseStockEnquiry) dataList.get(2), userData));
                break;
            case POPULATE_INVENTORYWAREHOUSESTOCKENQUIRY:
                theDataList.add(warehouseStockEnquiryBean.populateEnquiry(dataList.get(1).toString(), dataList.get(2).toString(), dataList.get(3).toString(),
                        dataList.get(4).toString(), dataList.get(5).toString(), dataList.get(6).toString(), dataList.get(7).toString(),
                        dataList.get(8).toString(), dataList.get(9).toString(), dataList.get(10).toString(), dataList.get(11).toString(),
                        (String) dataList.get(12), (Integer) dataList.get(13), (Boolean) dataList.get(14), userData));
                break;

            //InventoryWarehouseStockEnquiryBySize
            case INSERT_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.insert((InventoryWarehouseStockEnquiryBySize) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.update((InventoryWarehouseStockEnquiryBySize) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.delete((InventoryWarehouseStockEnquiryBySize) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.getNumRows(InventoryWarehouseStockEnquiryBySize.class, userData));
                break;
            case GETDATA_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList = (List<Object>) warehouseStockEnquiryBySizeBean.getDataInRange(InventoryWarehouseStockEnquiryBySize.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.validateField(dataList.get(1).toString(), (InventoryWarehouseStockEnquiryBySize) dataList.get(2), userData));
                break;
            case POPULATE_INVENTORYWAREHOUSESTOCKENQUIRYBYSIZE:
                theDataList.add(warehouseStockEnquiryBySizeBean.populateEnquiry((WarehouseEnquiryHelperClass) dataList.get(1), userData));
                break;
            //Inventory With No Demand Report
            case PRINT_INVENTORY_WITH_NO_DEMANS:
                theDataList = inventoryWithNoDemandReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryStockTakeLogger
            case INSERT_INVENTORYSTOCKTAKELOGGER:
                theDataList.add(stockTakeLoggerBean.insert((InventoryStockTakeLogger) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKTAKELOGGER:
                theDataList.add(stockTakeLoggerBean.update((InventoryStockTakeLogger) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKTAKELOGGER:
                theDataList.add(stockTakeLoggerBean.delete((InventoryStockTakeLogger) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKTAKELOGGER:
                theDataList.add(stockTakeLoggerBean.getNumRows(InventoryStockTakeLogger.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKTAKELOGGER:
                theDataList = (List<Object>) stockTakeLoggerBean.getDataInRange(InventoryStockTakeLogger.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKTAKELOGGER:
                theDataList.add(stockTakeLoggerBean.validateField(dataList.get(1).toString(), (InventoryStockTakeLogger) dataList.get(2), userData));
                break;
            //InventoryDimension3WhereUsed
            case INSERT_INVENTORYDIMENSION3WHEREUSED:
                theDataList.add(dimension3WhereUsedBean.insert((InventoryDimension3WhereUsed) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION3WHEREUSED:
                theDataList.add(dimension3WhereUsedBean.update((InventoryDimension3WhereUsed) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION3WHEREUSED:
                theDataList.add(dimension3WhereUsedBean.delete((InventoryDimension3WhereUsed) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION3WHEREUSED:
                theDataList.add(dimension3WhereUsedBean.getNumRows(InventoryDimension3WhereUsed.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION3WHEREUSED:
                theDataList = (List<Object>) dimension3WhereUsedBean.getDataInRange(InventoryDimension3WhereUsed.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION3WHEREUSED:
                theDataList.add(dimension3WhereUsedBean.validateField(dataList.get(1).toString(), (InventoryDimension3WhereUsed) dataList.get(2), userData));
                break;
            //InventoryStocktakeUnreserved
            case INSERT_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.insert((InventoryStocktakeUnreserved) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.update((InventoryStocktakeUnreserved) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.delete((InventoryStocktakeUnreserved) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.getNumRows(InventoryStocktakeUnreserved.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList = (List<Object>) stocktakeUnreservedBean.getDataInRange(InventoryStocktakeUnreserved.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.validateField(dataList.get(1).toString(), (InventoryStocktakeUnreserved) dataList.get(2), userData));
                break;
            case CLEAR_STOCKTAKEUNRESERVED:
                theDataList.add(stocktakeUnreservedBean.deleteStockTakeUnreserved((Boolean) dataList.get(1), dataList.size() == 3 ? (Date) dataList.get(2) : null, userData));
                break;
            //InventoryStocktakeUnreservedDS
            case INSERT_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList.add(stocktakeUnreservedDSBean.insert((InventoryStocktakeUnreservedDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList.add(stocktakeUnreservedDSBean.update((InventoryStocktakeUnreservedDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList.add(stocktakeUnreservedDSBean.delete((InventoryStocktakeUnreservedDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList.add(stocktakeUnreservedDSBean.getNumRows(InventoryStocktakeUnreservedDS.class, userData));
                break;
            case GETDATA_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList = (List<Object>) stocktakeUnreservedDSBean.getDataInRange(InventoryStocktakeUnreservedDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSTOCKTAKEUNRESERVEDDS:
                theDataList.add(stocktakeUnreservedDSBean.validateField(dataList.get(1).toString(), (InventoryStocktakeUnreservedDS) dataList.get(2), userData));
                break;
            //Factory Shop Report
            case PRINT_FACTORY_SHOP_REPORT:
                theDataList = factoryShopReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventorySafetyStock
            case INSERT_INVENTORYSAFETYSTOCK:
                theDataList.add(safetyStockBean.insert((InventorySafetyStock) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSAFETYSTOCK:
                theDataList.add(safetyStockBean.update((InventorySafetyStock) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSAFETYSTOCK:
                theDataList.add(safetyStockBean.delete((InventorySafetyStock) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSAFETYSTOCK:
                theDataList.add(safetyStockBean.getNumRows(InventorySafetyStock.class, userData));
                break;
            case GETDATA_INVENTORYSAFETYSTOCK:
                theDataList = (List<Object>) safetyStockBean.getDataInRange(InventorySafetyStock.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSAFETYSTOCK:
                theDataList.add(safetyStockBean.validateField(dataList.get(1).toString(), (InventorySafetyStock) dataList.get(2), userData));
                break;
            case GENERATE_SAFETY_STOCK:
                theDataList.add(safetyStockBean.generateSafetyStock((InventorySafetyStockGenerationRules) dataList.get(2), userData));
                break;
            case DELETE_SAFETY_STOCK:
                theDataList.add(safetyStockBean.deleteSafetyStock((InventorySafetyStockGenerationRules) dataList.get(2), dataList.size() > 3 ? (Date) dataList.get(3) : null, (String) dataList.get(1), userData));
                break;
            //InventorySafetyStockDS
            case INSERT_INVENTORYSAFETYSTOCKDS:
                theDataList.add(safetyStockDSBean.insert((InventorySafetyStockDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSAFETYSTOCKDS:
                theDataList.add(safetyStockDSBean.update((InventorySafetyStockDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSAFETYSTOCKDS:
                theDataList.add(safetyStockDSBean.delete((InventorySafetyStockDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSAFETYSTOCKDS:
                theDataList.add(safetyStockDSBean.getNumRows(InventorySafetyStockDS.class, userData));
                break;
            case GETDATA_INVENTORYSAFETYSTOCKDS:
                theDataList = (List<Object>) safetyStockDSBean.getDataInRange(InventorySafetyStockDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSAFETYSTOCKDS:
                theDataList.add(safetyStockDSBean.validateField(dataList.get(1).toString(), (InventorySafetyStockDS) dataList.get(2), userData));
                break;
            //InventorySafetyStockGenerationRules
            case INSERT_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList.add(safetyStockGenerationRulesBean.insert((InventorySafetyStockGenerationRules) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList.add(safetyStockGenerationRulesBean.update((InventorySafetyStockGenerationRules) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList.add(safetyStockGenerationRulesBean.delete((InventorySafetyStockGenerationRules) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList.add(safetyStockGenerationRulesBean.getNumRows(InventorySafetyStockGenerationRules.class, userData));
                break;
            case GETDATA_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList = (List<Object>) safetyStockGenerationRulesBean.getDataInRange(InventorySafetyStockGenerationRules.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYSAFETYSTOCKGENERATIONRULES:
                theDataList.add(safetyStockGenerationRulesBean.validateField(dataList.get(1).toString(), (InventorySafetyStockGenerationRules) dataList.get(2), userData));
                break;

            //InventoryPeggingTable
            case INSERT_INVENTORYPEGGINGTABLE:
                theDataList.add(peggingBean.insert((InventoryPeggingTable) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYPEGGINGTABLE:
                theDataList.add(peggingBean.update((InventoryPeggingTable) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYPEGGINGTABLE:
                theDataList.add(peggingBean.delete((InventoryPeggingTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYPEGGINGTABLE:
                theDataList.add(peggingBean.getNumRows(InventoryPeggingTable.class, userData));
                break;
            case GETDATA_INVENTORYPEGGINGTABLE:
                theDataList = (List<Object>) peggingBean.getDataInRange(InventoryPeggingTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYPEGGINGTABLE:
                theDataList.add(peggingBean.validateField(dataList.get(1).toString(), (InventoryPeggingTable) dataList.get(2), userData));
                break;

            //InventoryDimension1Enquiry
            case INSERT_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.insert((InventoryDimension1Enquiry) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.update((InventoryDimension1Enquiry) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.delete((InventoryDimension1Enquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.getNumRows(InventoryDimension1Enquiry.class, userData));
                break;
            case GETDATA_INVENTORYDIMENSION1ENQUIRY:
                theDataList = (List<Object>) dimension1EnquiryBean.getDataInRange(InventoryDimension1Enquiry.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.validateField(dataList.get(1).toString(), (InventoryDimension1Enquiry) dataList.get(2), userData));
                break;

            case POPULATE_INVENTORYDIMENSION1ENQUIRY:
                theDataList.add(dimension1EnquiryBean.populate((List<String>) dataList.get(1), (Long) dataList.get(2), userData));
                break;

            case CLEAR_DIMENSION1ENQUIRYSESSION:
                theDataList.add(dimension1EnquiryBean.clearOldData((Long) dataList.get(1), userData));
                break;
            //InventoryBatchConsolidationMaster
            case INSERT_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList.add(batchConsolidationMasterBean.insert((InventoryBatchConsolidationMaster) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList.add(batchConsolidationMasterBean.update((InventoryBatchConsolidationMaster) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList.add(batchConsolidationMasterBean.delete((InventoryBatchConsolidationMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList.add(batchConsolidationMasterBean.getNumRows(InventoryBatchConsolidationMaster.class, userData));
                break;
            case GETDATA_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList = (List<Object>) batchConsolidationMasterBean.getDataInRange(InventoryBatchConsolidationMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBATCHCONSOLIDATIONMASTER:
                theDataList.add(batchConsolidationMasterBean.validateField(dataList.get(1).toString(), (InventoryBatchConsolidationMaster) dataList.get(2), userData));
                break;
            case CREATE_BATCH_CONSOLIDATION:
                theDataList.add(batchConsolidationMasterBean.createBatchConsolidation((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), (String) dataList.get(4),
                        (BigDecimal) dataList.get(5), (BigDecimal) dataList.get(6), (Integer) dataList.get(7), userData));
                break;
            case FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_APPROVAL:
                theDataList.add(batchConsolidationMasterBean.getMovementJournalsForConsolidationApproval((String) dataList.get(1), userData));
                break;
            case FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_APPROVAL:
                theDataList.add(batchConsolidationMasterBean.getTransferJournalsForConsolidationApproval((String) dataList.get(1), userData));
                break;
            case APPROVE_CONSOLIDATION:
                theDataList.add(batchConsolidationMasterBean.approveConsolidation((String) dataList.get(1), userData));
                break;
            case FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_UN_APPROVAL:
                theDataList.add(batchConsolidationMasterBean.getMovementJournalsForConsolidationUnApproval((String) dataList.get(1), userData));
                break;
            case FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_UN_APPROVAL:
                theDataList.add(batchConsolidationMasterBean.getTransferJournalsForConsolidationUnApproval((String) dataList.get(1), userData));
                break;
            case UN_APPROVE_CONSOLIDATION:
                theDataList.add(batchConsolidationMasterBean.unApproveConsolidation((String) dataList.get(1), userData));
                break;
            case FETCH_MOVEMENT_JOURNAL_FOR_CONSOLIDATION_POSTING:
                theDataList.add(batchConsolidationMasterBean.getMovementJournalsForConsolidationPosting((String) dataList.get(1), userData));
                break;
            case FETCH_TRANSFER_JOURNAL_FOR_CONSOLIDATION_POSTING:
                theDataList.add(batchConsolidationMasterBean.getTransferJournalsForConsolidationPosting((String) dataList.get(1), userData));
                break;
            case POST_CONSOLIDATION:
                theDataList.add(batchConsolidationMasterBean.postConsolidation((String) dataList.get(1), userData));
                break;
            //InventoryBatchConsolidationLines
            case INSERT_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList.add(batchConsolidationLinesBean.insert((InventoryBatchConsolidationLines) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList.add(batchConsolidationLinesBean.update((InventoryBatchConsolidationLines) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList.add(batchConsolidationLinesBean.delete((InventoryBatchConsolidationLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList.add(batchConsolidationLinesBean.getNumRows(InventoryBatchConsolidationLines.class, userData));
                break;
            case GETDATA_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList = (List<Object>) batchConsolidationLinesBean.getDataInRange(InventoryBatchConsolidationLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBATCHCONSOLIDATIONLINES:
                theDataList.add(batchConsolidationLinesBean.validateField(dataList.get(1).toString(), (InventoryBatchConsolidationLines) dataList.get(2), userData));
                break;
            //InventoryBatchConsolidationLinesDS
            case INSERT_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList.add(batchConsolidationLinesDSBean.insert((InventoryBatchConsolidationLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList.add(batchConsolidationLinesDSBean.update((InventoryBatchConsolidationLinesDS) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList.add(batchConsolidationLinesDSBean.delete((InventoryBatchConsolidationLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList.add(batchConsolidationLinesDSBean.getNumRows(InventoryBatchConsolidationLinesDS.class, userData));
                break;
            case GETDATA_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList = (List<Object>) batchConsolidationLinesDSBean.getDataInRange(InventoryBatchConsolidationLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_INVENTORYBATCHCONSOLIDATIONLINESDS:
                theDataList.add(batchConsolidationLinesDSBean.validateField(dataList.get(1).toString(), (InventoryBatchConsolidationLinesDS) dataList.get(2), userData));
                break;
            //Batch Consolidation Picking List
            case PRINT_BATCH_CONSOLIDATION_PICKING_LIST:
                theDataList = batchConsolidationPickingListReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Warehouse/Location Transfer
            case PRINT_WAREHOUSE_LOCATION_TRANSFER:
                theDataList = warehouseLocationTransferReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //InventoryReqPlan
            case INSERT_INVENTORYREQPLANAUDIT:
                theDataList.add(reqPlanAuditBean.insert((InventoryReqPlanAudit) dataList.get(1), userData));
                break;
            case UPDATE_INVENTORYREQPLANAUDIT:
                theDataList.add(reqPlanAuditBean.update((InventoryReqPlanAudit) dataList.get(1), userData));
                break;
            case DELETE_INVENTORYREQPLANAUDIT:
                theDataList.add(reqPlanAuditBean.delete((InventoryReqPlanAudit) dataList.get(1), userData));
                break;
            case GETNUMROWS_INVENTORYREQPLANAUDIT:
                theDataList.add(reqPlanAuditBean.getNumRows(InventoryReqPlanAudit.class, userData));
                break;
            case GETDATA_INVENTORYREQPLANAUDIT:
                theDataList = (List<Object>) reqPlanAuditBean.getDataInRange(InventoryReqPlanAudit.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case GET_INVENTORY_GROUPS:
                theDataList.add(productionGroupBean.getProductGroups(userData));
                break;
            case GET_INVENTORY_ITEMS_FOR_WEB:
                theDataList.add(inventoryItemMasterBean.getWebItems((String) dataList.get(1), userData));
                break;
            case GET_ITEM_WEB_TYPE:
                theDataList.add(inventoryItemMasterBean.getItemWebStoreType((String) dataList.get(1), userData));
                break;
            case GET_FILTERED_INVENTORY_ITEMS_FOR_WEB:
                theDataList.add(inventoryItemMasterBean.filterWebItems((String) dataList.get(1),(String) dataList.get(2),(String) dataList.get(3), userData));
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
