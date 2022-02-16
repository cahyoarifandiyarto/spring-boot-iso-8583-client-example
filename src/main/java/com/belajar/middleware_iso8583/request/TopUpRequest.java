package com.belajar.middleware_iso8583.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest implements Serializable {

    private static final long serialVersionUID = 6029823512727378078L;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("nilai")
    private BigDecimal nilai;

}
