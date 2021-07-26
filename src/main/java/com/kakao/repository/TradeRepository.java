package com.kakao.repository;

import com.kakao.entity.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TradeRepository {
    private final EntityManager em;

    public void initZoneData(List<Trade> list) {
        em.persist(list);
    }

    @Transactional
    public void save(Trade trade) {
        em.persist(trade);
    }
//
//    // 1번
//    public Object[] getData (String year) {
//        Object o = em.createQuery("select t.account, sum((t.money - t.charge)) as sumAmt from Trade t " +
//                        "where t.tradeDate between :stDt and :edDt and t.isCancel = 'N' " +
//                        "group by t.account " +
//                        "order by sumAmt desc")
//                .setParameter("stDt", year+"0101")
//                .setParameter("edDt", year+"1231")
//                .setMaxResults(1)
//                .getSingleResult();
//
//        return (Object[]) o;
//    }
//
//    // 2번
//
//    public List<Object[]> getAccountData (String year) {
//        List<Object[]> rs = em.createQuery("select a.id as acctNo, a.accountName as name, ' " + year + "' as year from Account a " +
//                "where a.id not in ( select t.account.id from Trade t where t.tradeDate between :stDt and :edDt " +
//                "and t.isCancel = 'N' group by t.account.id)")
//                .setParameter("stDt", year+"0101")
//                .setParameter("edDt", year+"1231")
//                .getResultList();
//
//        return rs;
//    }
//
//    // 3번
//    public List<Object[]> getBranchData (String year) {
//        List<Object[]> o = em.createQuery("select a.branch.code, sum(t.money) as sumAmt " +
//                "from Account a left join Trade t " +
//                "on a.id = t.account.id " +
//                "where trade_date between :stDt and :edDt and is_cancel = 'N' " +
//                "group by a.branch.code order by sumAmt desc ")
//                .setParameter("stDt", year+"0101")
//                .setParameter("edDt", year+"1231")
//                .getResultList();
//
//        return o;
//    }
//
//    // 4번
//
//    public Object getBranchSum(String code) {
//        return em.createQuery("select sum(t.money) from Trade t " +
//                "where t.account.id in (select a.id from Account a where a.branch.code = :code)")
//                .setParameter("code", code)
//                .getSingleResult();
//
//    }

}
