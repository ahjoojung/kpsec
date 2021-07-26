package com.kakao.repository;

import com.kakao.entity.Branch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BranchRepository {
    private final EntityManager em;

    @Transactional
    public void save(Branch branch) {
        em.persist(branch);
    }

    public Branch findOne(String code) {
        return em.find(Branch.class, code);
    }

    public Branch findOneByName(String name) {
        Branch branch = null;
        try {
             branch = em.createQuery("select m from Branch m where m.branchName = :name and m.isDelete = false ", Branch.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return branch;
    }
}
