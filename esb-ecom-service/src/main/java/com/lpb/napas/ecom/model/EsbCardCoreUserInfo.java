package com.lpb.napas.ecom.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private String username;
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
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "CREATED_DT")
    private Date createDate;
}
