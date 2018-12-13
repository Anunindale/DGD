/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.inventory;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.safetystockgenerationrules.Customer;
import emc.datatypes.inventory.safetystockgenerationrules.CustomerGroup;
import emc.datatypes.inventory.safetystockgenerationrules.FromForecast;
import emc.datatypes.inventory.safetystockgenerationrules.GenerationRule;
import emc.datatypes.inventory.safetystockgenerationrules.Granularity;
import emc.datatypes.inventory.safetystockgenerationrules.IncludeCurrentWeek;
import emc.datatypes.inventory.safetystockgenerationrules.Item;
import emc.datatypes.inventory.safetystockgenerationrules.ItemGroup;
import emc.datatypes.inventory.safetystockgenerationrules.NumGranularityForSum;
import emc.datatypes.inventory.safetystockgenerationrules.RegenerateOnMPS;
import emc.datatypes.inventory.safetystockgenerationrules.SafetyStockForecastOnly;
import emc.datatypes.inventory.safetystockgenerationrules.SafetyStockHorizon;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "InventorySafetyStockGenerationRules", uniqueConstraints = {@UniqueConstraint(columnNames = {"companyId"})})
public class InventorySafetyStockGenerationRules extends EMCEntityClass {

    private boolean fromForecast;
    private boolean safetyStockForecastOnly;
    private String customerGroup;
    private String customer;
    private String itemGroup;
    private String item;
    private String granularity;
    private int safetyStockHorizon;
    private String generationRule;
    private boolean regenerateOnMPS;
    private int numGranularityForSum;
    private boolean includeCurrentWeek;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public boolean isFromForecast() {
        return fromForecast;
    }

    public void setFromForecast(boolean fromForecast) {
        this.fromForecast = fromForecast;
    }

    public String getGenerationRule() {
        return generationRule;
    }

    public void setGenerationRule(String generationRule) {
        this.generationRule = generationRule;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public boolean isIncludeCurrentWeek() {
        return includeCurrentWeek;
    }

    public void setIncludeCurrentWeek(boolean includeCurrentWeek) {
        this.includeCurrentWeek = includeCurrentWeek;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public int getNumGranularityForSum() {
        return numGranularityForSum;
    }

    public void setNumGranularityForSum(int numGranularityForSum) {
        this.numGranularityForSum = numGranularityForSum;
    }

    public boolean isRegenerateOnMPS() {
        return regenerateOnMPS;
    }

    public void setRegenerateOnMPS(boolean regenerateOnMPS) {
        this.regenerateOnMPS = regenerateOnMPS;
    }

    public boolean isSafetyStockForecastOnly() {
        return safetyStockForecastOnly;
    }

    public void setSafetyStockForecastOnly(boolean safetyStockForecastOnly) {
        this.safetyStockForecastOnly = safetyStockForecastOnly;
    }

    public int getSafetyStockHorizon() {
        return safetyStockHorizon;
    }

    public void setSafetyStockHorizon(int safetyStockHorizon) {
        this.safetyStockHorizon = safetyStockHorizon;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("fromForecast", new FromForecast());
        toBuild.put("safetyStockForecastOnly", new SafetyStockForecastOnly());
        toBuild.put("customerGroup", new CustomerGroup());
        toBuild.put("customer", new Customer());
        toBuild.put("itemGroup", new ItemGroup());
        toBuild.put("item", new Item());
        toBuild.put("granularity", new Granularity());
        toBuild.put("safetyStockHorizon", new SafetyStockHorizon());
        toBuild.put("generationRule", new GenerationRule());
        toBuild.put("regenerateOnMPS", new RegenerateOnMPS());
        toBuild.put("numGranularityForSum", new NumGranularityForSum());
        toBuild.put("includeCurrentWeek", new IncludeCurrentWeek());
        return toBuild;
    }
}
