package com.kakao.service;

import com.kakao.entity.Branch;
import com.kakao.repository.AccountRepository;
import com.kakao.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;

    /**
     * 관리점 삭제
     * @param code
     * @return
     */
    @Transactional
    public void deleteBranch(String code) {
        Branch branch = branchRepository.findOne(code);
        branch.setIsDelete(Boolean.TRUE);

        branchRepository.save(branch);
    }

    /**
     * 관리점 이전 처리 (고객의 관리점 업데이트, 기존 관리점 삭제)
     * @param oldCode
     * @param newCode
     */
    @Transactional
    public void moveBranch(String oldCode, String newCode) {
        // 고객의 이전 관리점을 새로운 관리점으로 업데이트
        accountRepository.updateBranch(oldCode, newCode);
        // 기존 관리점 삭제
        deleteBranch(oldCode);
    }

}
