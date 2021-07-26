package com.kakao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "account_id")
    private String id;
    private String accountName;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_code")
    private Branch branch;

    @OneToMany(mappedBy = "account")
    private List<Trade> trades = new ArrayList<>();
}
