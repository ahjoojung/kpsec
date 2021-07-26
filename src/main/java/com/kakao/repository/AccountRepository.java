package com.kakao.repository;

import com.kakao.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    @Transactional
    public void save(Account account) {
        em.persist(account);
    }

    public Account findOne(String code) {
        return em.find(Account.class, code);
    }

    @Transactional
    public void updateBranch(String oldCode, String newCode) {
        em.createQuery("update Account a set a.branch.code = :newCode where a.branch.code = :oldCode")
                .setParameter("newCode", newCode)
                .setParameter("oldCode", oldCode)
                .executeUpdate();
    }
}
