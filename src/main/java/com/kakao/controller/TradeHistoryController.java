package com.kakao.controller;

import com.google.gson.JsonObject;
import com.kakao.service.TradeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TradeHistoryController {

    private final TradeHistoryService tradeHistoryService;

    /**
     * 1번. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발
     * @return
     */
    @GetMapping(value = "trade")
    public ResponseEntity<String> tradeAccount() throws IOException {
        String rs = tradeHistoryService.getBestCustomer();

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * 2번. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
     * @return
     * @throws IOException
     */
    @GetMapping(value = "branch")
    public ResponseEntity<String> branchData() throws IOException {
        String rs = tradeHistoryService.getAccountWithNoTrade();

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * 3번. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
     * @return
     */
    @GetMapping(value="account")
    public ResponseEntity<String> getAccount() {
        String rs = tradeHistoryService.getBranchData();

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * 4번. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
     * @param brName
     * @return
     */
    @GetMapping(value="branchInfo")
    public ResponseEntity<String> branchInfo(@RequestParam("brName") String brName) {
        JsonObject rs = tradeHistoryService.getBranchInfo(brName);

        if(rs.get("code")==null)
            return new ResponseEntity<>(rs.toString(),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(rs.toString(), HttpStatus.OK);
    }
}
