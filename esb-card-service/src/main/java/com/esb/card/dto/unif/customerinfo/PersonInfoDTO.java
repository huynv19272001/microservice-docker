package com.esb.card.dto.unif.customerinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonInfoDTO {
    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "FirstName")
    private String firstName;
    @JsonProperty("secondName")
    @JacksonXmlProperty(localName = "SecondName")
    private String secondName;
    @JsonProperty("surName")
    @JacksonXmlProperty(localName = "Surname")
    private String surName;
    @JsonProperty("genderCode")
    @JacksonXmlProperty(localName = "GenderCode")
    private String genderCode;
    @JsonProperty("DOB")
    @JacksonXmlProperty(localName = "DOB")
    private String DOB;
    @JsonProperty("issueTypeCode")
    @JacksonXmlProperty(localName = "issueTypeCode")
    private String issueTypeCode;
    @JsonProperty("issueNo")
    @JacksonXmlProperty(localName = "issueNo")
    private String issueNo;
    @JsonProperty("issuePlace")
    @JacksonXmlProperty(localName = "issuePlace")
    private String issuePlace;
    @JsonProperty("issueDate")
    @JacksonXmlProperty(localName = "issueDate")
    private String issueDate;
    @JsonProperty("issueExpDate")
    @JacksonXmlProperty(localName = "issueExpDate")
    private String issueExpDate;
    @JsonProperty("visaNo")
    @JacksonXmlProperty(localName = "VisaNo")
    private String visaNo;
    @JsonProperty("countryCode")
    @JacksonXmlProperty(localName = "CountryCode")
    private String countryCode;
    @JsonProperty("region")
    @JacksonXmlProperty(localName = "Region")
    private String region;
    @JsonProperty("city")
    @JacksonXmlProperty(localName = "City")
    private String city;
    @JsonProperty("street")
    @JacksonXmlProperty(localName = "Street")
    private String street;
    @JsonProperty("streetNo")
    @JacksonXmlProperty(localName = "StreetNo")
    private String streetNo;
    @JsonProperty("mobileNo")
    @JacksonXmlProperty(localName = "MobileNo")
    private String mobileNo;
    @JsonProperty("email")
    @JacksonXmlProperty(localName = "Email")
    private String email;
}
