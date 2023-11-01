package com.esb.transaction.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_CARD_CORE_USER_INFO")
public class EsbCardCoreUserInfo {
    @Id
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "CLIENT_PUBLICKEY")
    private String clientPublicKey;
    @Column(name = "ESB_PRIVATEKEY")
    private String esbPrivateKey;
    @Column(name = "ESB_PUBLICKEY")
    private String esbPublicKey;
    @Column(name = "ESB_CARD_PRIVATEKEY")
    private String esbCardPrivateKey;
    @Column(name = "ESB_CARD_PUBLICKEY")
    private String esbCardPublicKey;
    @Column(name = "CARD_ESB_PUBLICKEY")
    private String cardEsbPublicKey;
    @Column(name = "CREATED_DT")
    private String createdDt;
    @Column(name = "RECORD_STAT")
    private String recordStat;
}
