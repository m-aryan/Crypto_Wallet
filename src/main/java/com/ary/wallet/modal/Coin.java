package com.ary.wallet.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Coin {

    @Id
    @JsonProperty
    private String id;

    @JsonProperty
    private String symbol;

    @JsonProperty
    private String name;

    @JsonProperty
    private String image;

    @JsonProperty("current_price")
    private double currentPrice;

    @JsonProperty("market_cap")
    private long marketCap;

    @JsonProperty("market_cap_rank")
    private int marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    private long fullyDilutedValuation;

    @JsonProperty("total_volume")
    private long totalVolume;

    @JsonProperty("high_24h")
    private double high24h;

    @JsonProperty("low_24h")
    private double low24h;

    @JsonProperty("price_change_24h")
    private double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;

    @JsonProperty("circulating_supply")
    private long circulatingSupply;

    @JsonProperty("total_supply")
    private long totalSupply;

    @JsonProperty("max_supply")
    private long maxSupply;

    @JsonProperty("ath")
    private double allTimeHigh;

    @JsonProperty("ath_change_percentage")
    private double allTimeHighChangePercentage;

    @JsonProperty("ath_date")
    private String allTimeHighDate;

    @JsonProperty("atl")
    private double allTimeLow;

    @JsonProperty("atl_change_percentage")
    private double allTimeLowChangePercentage;

    @JsonProperty("atl_date")
    private String allTimeLowDate;

    @JsonProperty
    @JsonIgnore
    private String roi;

    @JsonProperty("last_updated")
    private String lastUpdated;
}
