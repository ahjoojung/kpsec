package com.kakao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    @GeneratedValue
    @Column(name = "trade_id")
    private Long id;
    private String tradeDate;
    private Integer tradeNo;
    private Long money;
    private Long charge;
    private String isCancel;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
