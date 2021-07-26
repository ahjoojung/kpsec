package com.kakao.service;

import com.kakao.entity.Account;
import com.kakao.entity.Branch;
import com.kakao.entity.Trade;
import com.kakao.repository.AccountRepository;
import com.kakao.repository.BranchRepository;
import com.kakao.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InitService {

    private final TradeRepository tradeRepository;
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    @Transactional
    public void init() throws IOException {
        initBranch();
        initAccount();
        initTradeHistory();
    }

    public void initBranch() throws IOException {
        Resource resource = new ClassPathResource("과제1_데이터_관리점정보.csv");
        List<String> list = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

        for (int i = 1; i < list.size(); i++) {
            String[] split = list.get(i).split(",");

            branchRepository.save(Branch.builder().code(split[0]).branchName(split[1]).isDelete(false).build());
        }
    }

    public void initAccount() throws IOException {
        Resource resource = new ClassPathResource("과제1_데이터_계좌정보.csv");
        List<String> list = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

        for (int i = 1; i < list.size(); i++) {
            String[] split = list.get(i).split(",");

            accountRepository.save(Account.builder().id(split[0])
                    .accountName(split[1])
                    .branch(branchRepository.findOne(split[2]))
                    .build());

        }
    }

    public void initTradeHistory() throws IOException {
        Resource resource = new ClassPathResource("과제1_데이터_거래내역.csv");
        List<String> list = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8);

        for (int i = 1; i < list.size(); i++) {
            String[] split = list.get(i).split(",");

            tradeRepository.save(Trade.builder().tradeDate(split[0])
                    .account(accountRepository.findOne(split[1]))
                    .tradeNo(Integer.parseInt(split[2]))
                    .money(Long.parseLong(split[3]))
                    .charge(Long.parseLong(split[4]))
                    .isCancel(split[5])
                    .build());

        }
    }

}
