package com.ra.bakerysystem.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {
    @JsonProperty("eat-in")
    EAT_IN,
    @JsonProperty("takeaway")
    TAKEAWAY
}
