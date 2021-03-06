/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.trec;

import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECTrecCardsLines;
import emc.entity.trec.TRECTrecCardsMaster;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stuart
 */
public class TRECWebOrderProcessHelper {
    //entities
    private TRECTrecCardsMaster theMaster;
    private TRECTrecCardsLines currentLine;
    private DebtorsBasketMaster basketMaster;
    private DebtorsBasketLines basketLines;
    private TRECCustomerChemicals customerChemicals;
    private List<DebtorsBasketLines> basketLinesList = new ArrayList<DebtorsBasketLines>();
    //other variables
    private BigDecimal trecCardsQuantity;
    private BigDecimal trecCardsQuantityTotal;
    private String sessionId;
    private String properShippingName;
    private String packingGroup;
    private boolean credit;
    private String chemPackingGroup;
    private String chemicalForm;
    private String chemicalColour;
    private String chemicalOdour;
    private String unNumber;
    private String customerId;
    private boolean treccard;
    
    //invoice
    private DebtorsCustomerInvoiceMaster theInvoiceMaster = null;
    private DebtorsJournalMaster theJournalMaster = null;
    
    public TRECWebOrderProcessHelper(){
        
    }
    
    public TRECWebOrderProcessHelper(TRECTrecCardsMaster theMaster, TRECTrecCardsLines currentLine){
        this.theMaster = theMaster;
        this.currentLine = currentLine;
        this.basketMaster = new DebtorsBasketMaster();
        this.basketLines = new DebtorsBasketLines();
        
    }
    
    public TRECWebOrderProcessHelper(TRECTrecCardsMaster theMaster, String unNumber){
        this.theMaster = theMaster;
        this.currentLine = new TRECTrecCardsLines();
        currentLine.setUnNumber(unNumber);
        currentLine.setMasterId(theMaster.getMasterId());
        
        
        this.basketMaster = new DebtorsBasketMaster();
        this.basketLines = new DebtorsBasketLines();
    }

    public TRECTrecCardsMaster getTheMaster() {
        return theMaster;
    }

    public void setTheMaster(TRECTrecCardsMaster theMaster) {
        this.theMaster = theMaster;
    }

    public TRECTrecCardsLines getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(TRECTrecCardsLines currentLine) {
        this.currentLine = currentLine;
    }

    public BigDecimal getTrecCardsQuantity() {
        return trecCardsQuantity;
    }

    public void setTrecCardsQuantity(BigDecimal trecCardsQuantity) {
        this.trecCardsQuantity = trecCardsQuantity;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public DebtorsBasketMaster getBasketMaster() {
        return basketMaster;
    }

    public void setBasketMaster(DebtorsBasketMaster basketMaster) {
        this.basketMaster = basketMaster;
    }

    public DebtorsBasketLines getBasketLines() {
        return basketLines;
    }

    public void setBasketLines(DebtorsBasketLines basketLines) {
        this.basketLines = basketLines;
    }

    public List<DebtorsBasketLines> getBasketLinesList() {
        return basketLinesList;
    }

    public void setBasketLinesList(List<DebtorsBasketLines> basketLinesList) {
        this.basketLinesList = basketLinesList;
    }

    public String getProperShippingName() {
        return properShippingName;
    }

    public void setProperShippingName(String properShippingName) {
        this.properShippingName = properShippingName;
    }

    public String getPackingGroup() {
        return packingGroup;
    }

    public void setPackingGroup(String packingGroup) {
        this.packingGroup = packingGroup;
    }

    public BigDecimal getTrecCardsQuantityTotal() {
        return trecCardsQuantityTotal;
    }

    public void setTrecCardsQuantityTotal(BigDecimal trecCardsQuantityTotal) {
        this.trecCardsQuantityTotal = trecCardsQuantityTotal;
    }

    public DebtorsCustomerInvoiceMaster getTheInvoiceMaster() {
        return theInvoiceMaster;
    }

    public void setTheInvoiceMaster(DebtorsCustomerInvoiceMaster theInvoiceMaster) {
        this.theInvoiceMaster = theInvoiceMaster;
    }

    public TRECCustomerChemicals getCustomerChemicals() {
        return customerChemicals;
    }

    public void setCustomerChemicals(TRECCustomerChemicals customerChemicals) {
        this.customerChemicals = customerChemicals;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public DebtorsJournalMaster getTheJournalMaster() {
        return theJournalMaster;
    }

    public void setTheJournalMaster(DebtorsJournalMaster theJournalMaster) {
        this.theJournalMaster = theJournalMaster;
    }

    public String getChemPackingGroup() {
        return chemPackingGroup;
    }

    public void setChemPackingGroup(String chemPackingGroup) {
        this.chemPackingGroup = chemPackingGroup;
    }

    public String getChemicalForm() {
        return chemicalForm;
    }

    public void setChemicalForm(String chemicalForm) {
        this.chemicalForm = chemicalForm;
    }

    public String getChemicalColour() {
        return chemicalColour;
    }

    public void setChemicalColour(String chemicalColour) {
        this.chemicalColour = chemicalColour;
    }

    public String getChemicalOdour() {
        return chemicalOdour;
    }

    public void setChemicalOdour(String chemicalOdour) {
        this.chemicalOdour = chemicalOdour;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public void setUnNumber(String unNumber) {
        this.unNumber = unNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isTreccard() {
        return treccard;
    }

    public void setTreccard(boolean treccard) {
        this.treccard = treccard;
    }
    
}
