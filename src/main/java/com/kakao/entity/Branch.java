package com.kakao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @Column(name = "branch_code")
    private String code;
    private String branchName;

    @Setter
    private Boolean isDelete;

    @OneToMany(mappedBy = "branch")
    private List<Account> accounts = new ArrayList<>();


}