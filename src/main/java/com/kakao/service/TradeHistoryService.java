package com.kakao.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.kakao.entity.Account;
import com.kakao.entity.Branch;
import com.kakao.repository.BranchRepository;
import com.kakao.repository.SelectDataRepository;
import com.kakao.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradeHistoryService {
    private final BranchRepository branchRepository;
    private final SelectDataRepository selectDataRepository;

    /**
     * 1번. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발
     * @return
     */
    public String getBestCustomer() {

        JsonArray arr = new JsonArray();

        Consumer<String> process = (year) -> {
            JsonObject json = new JsonObject();

            Object[] obj = selectDataRepository.getBestCustomer(year);

            json.addProperty("year", year);
            json.addProperty("name", ((Account)obj[0]).getAccountName());
            json.addProperty("acctNo", ((Account)obj[0]).getId());
            json.addProperty("sumAmt", (Long)obj[1]);

            arr.add(json);
        };

        process.accept("2018");
        process.accept("2019");

        System.out.println(arr.toString());

        return arr.toString();
    }

    /**
     * 2번. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
     * @return
     */
    public String getAccountWithNoTrade() {
        JsonArray array = new JsonArray();

        Consumer<String> process = (year) -> {

            List<Object[]> list = selectDataRepository.getAccountWithNoTrade(year);
            for (Object[] o : list) {
                JsonObject json = new JsonObject();

                json.addProperty("year", (String) o[2]);
                json.addProperty("name", (String) o[0]);
                json.addProperty("accNo", (String) o[1]);

                array.add(json);
            }

        };

        process.accept("2018");
        process.accept("2019");

        return array.toString();
    }

    /**
     * 3번. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
     * @return
     */
    public String getBranchData() {
        JsonArray arr = new JsonArray();

        Consumer<String> process = (year) -> {
            JsonObject json = new JsonObject();
            JsonArray dataList = new JsonArray();

            List<Object[]> list = selectDataRepository.getBranchData(year);
            for (Object[] o : list) {
                log.info("test :: {}", o[0].toString());
                JsonObject js = new JsonObject();

                String code = (String)o[0];
                Branch branch = branchRepository.findOne(code);

                js.addProperty("brName", branch.getBranchName());
                js.addProperty("brCode", code);
                js.addProperty("sumAmt", (Long)o[1]);

                dataList.add(js);
            }

            json.addProperty("year", year);
            json.add("dataList", dataList);

            arr.add(json);
        };

        process.accept("2018");
        process.accept("2019");

        return arr.toString();
    }


    /**
     * 4번. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
     * @param branchName
     * @return
     */
    public JsonObject getBranchInfo(String branchName) {
        JsonObject json = new JsonObject();

        Branch branch = branchRepository.findOneByName(branchName);
        if (branch == null) {
            json.addProperty("code", "404");
            json.addProperty("메세지", "br code not found error");

            return json;
        }

        Object obj = selectDataRepository.getBranchInfo(branch.getCode());

        json.addProperty("brName", branch.getBranchName());
        json.addProperty("brCode", branch.getCode());
        json.addProperty("sumAmt", (Long)obj);

        return json;
    }


}
