package com.kakao.service;

import com.google.gson.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class TradeHistoryServiceTest {

    @Autowired InitService initService;
    @Autowired TradeHistoryService tradeHistoryService;
    @Autowired BranchService branchService;

    @Test
    void 테스트1() {

        // given
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        String test1 = "{\"year\":\"2018\",\"name\":\"테드\",\"acctNo\":\"11111114\",\"sumAmt\":28992000}";
        String test2 = "{\"year\":\"2019\",\"name\":\"에이스\",\"acctNo\":\"11111112\",\"sumAmt\":40998400}";

        // 2018
        JsonObject tJson1 = gson.fromJson(test1, JsonObject.class);
        // 2019
        JsonObject tJson2 = gson.fromJson(test2, JsonObject.class);


        // when
        String rs = tradeHistoryService.getBestCustomer();
        JsonArray array = gson.fromJson(rs, JsonArray.class);
        JsonObject rJson1 = (JsonObject) array.get(0);
        JsonObject rJson2 = (JsonObject) array.get(1);

        // then
        // 2018
        assertEquals(tJson1.get("year"), rJson1.get("year"));
        assertEquals(tJson1.get("name"), rJson1.get("name"));
        assertEquals(tJson1.get("acctNo"), rJson1.get("acctNo"));
        assertEquals(tJson1.get("sumAmt"), rJson1.get("sumAmt"));

        // 2019
        assertEquals(tJson2.get("year"), rJson2.get("year"));
        assertEquals(tJson2.get("name"), rJson2.get("name"));
        assertEquals(tJson2.get("acctNo"), rJson2.get("acctNo"));
        assertEquals(tJson2.get("sumAmt"), rJson2.get("sumAmt"));
    }

    @Test
    void 테스트2() {
        // given
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        JsonObject[] tJson = new JsonObject[6];

        // 2018
        tJson[0] = gson.fromJson("{\"year\":\" 2018\",\"name\":\"11111115\",\"accNo\":\"사라\"}", JsonObject.class);
        tJson[1] = gson.fromJson("{\"year\":\" 2018\",\"name\":\"11111118\",\"accNo\":\"제임스\"}", JsonObject.class);
        tJson[2] = gson.fromJson("{\"year\":\" 2018\",\"name\":\"11111121\",\"accNo\":\"에이스\"}", JsonObject.class);

        // 2019
        tJson[3] = gson.fromJson("{\"year\":\" 2019\",\"name\":\"11111114\",\"accNo\":\"테드\"}", JsonObject.class);
        tJson[4] = gson.fromJson("{\"year\":\" 2019\",\"name\":\"11111118\",\"accNo\":\"제임스\"}", JsonObject.class);
        tJson[5] = gson.fromJson("{\"year\":\" 2019\",\"name\":\"11111121\",\"accNo\":\"에이스\"}", JsonObject.class);

        // when
        String rs = tradeHistoryService.getAccountWithNoTrade();
        JsonArray array = gson.fromJson(rs, JsonArray.class);
        JsonObject[] rJson = new JsonObject[array.size()];

        for (int i=0; i<array.size(); i++) {
            rJson[i] = (JsonObject) array.get(i);
        }

        // then

        assertEquals(tJson.length, rJson.length);

        for (int i=0; i<rJson.length; i++) {
            assertEquals(tJson[i].get("year"), rJson[i].get("year"));
            assertEquals(tJson[i].get("name"), rJson[i].get("name"));
            assertEquals(tJson[i].get("accNo"), rJson[i].get("accNo"));
        }

    }

    @Test
    void 테스트3() {
        // given
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        JsonObject[] tJson = new JsonObject[6];

        String test = "[{\"year\":\"2018\",\"dataList\":[{\"brName\":\"분당점\",\"brCode\":\"B\",\"sumAmt\":38500000},{\"brName\":\"판교점\",\"brCode\":\"A\",\"sumAmt\":20510000},{\"brName\":\"강남점\",\"brCode\":\"C\",\"sumAmt\":20234567},{\"brName\":\"잠실점\",\"brCode\":\"D\",\"sumAmt\":14000000}]},{\"year\":\"2019\",\"dataList\":[{\"brName\":\"판교점\",\"brCode\":\"A\",\"sumAmt\":66800000},{\"brName\":\"분당점\",\"brCode\":\"B\",\"sumAmt\":45400000},{\"brName\":\"강남점\",\"brCode\":\"C\",\"sumAmt\":19500000},{\"brName\":\"잠실점\",\"brCode\":\"D\",\"sumAmt\":6000000}]}]";
        JsonArray testArray = gson.fromJson(test, JsonArray.class);

        JsonObject[] testObjects = new JsonObject[testArray.size()];
        List<JsonObject> jsonTestList = new ArrayList<>();

        for (int i=0; i<testArray.size(); i++) {
            testObjects[i] = (JsonObject) testArray.get(i);
            JsonArray jsonArray = testObjects[i].getAsJsonArray("dataList");

            for (int j=0; j<jsonArray.size(); j++) {
                jsonTestList.add((JsonObject) jsonArray.get(i));
            }
        }

        // when
        String rs = tradeHistoryService.getBranchData();
        JsonArray array = gson.fromJson(rs, JsonArray.class);

        JsonObject[] objects = new JsonObject[array.size()];
        List<JsonObject> jsonObjectList = new ArrayList<>();

        for (int i=0; i<array.size(); i++) {
            objects[i] = (JsonObject) array.get(i);
            JsonArray jsonArray = objects[i].getAsJsonArray("dataList");

            for (int j=0; j<jsonArray.size(); j++) {
                jsonObjectList.add((JsonObject) jsonArray.get(i));
            }
        }

        // then
        for (int i=0; i< jsonObjectList.size(); i++) {
            assertEquals(jsonTestList.get(i).get("brName"), jsonObjectList.get(i).get("brName"));
            assertEquals(jsonTestList.get(i).get("brCode"), jsonObjectList.get(i).get("brCode"));
            assertEquals(jsonTestList.get(i).get("sumAmt"), jsonObjectList.get(i).get("sumAmt"));
        }

    }

    @Test
    @Transactional
    @Rollback(value = true)
    void 테스트4() {
        // given
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        String testSuccStr = "{\"brName\":\"판교점\",\"brCode\":\"A\",\"sumAmt\":483010000}";
        String testFailedStr = "{\"code\":\"404\",\"메세지\":\"br code not found error\"}";

        JsonObject testSucc = gson.fromJson(testSuccStr, JsonObject.class);
        JsonObject testFailed = gson.fromJson(testFailedStr, JsonObject.class);

        // when
        // 관리점 이전, 기존 관리점은 삭제
        branchService.moveBranch("B", "A");

        // then

        // 기존의 분당점과 판교점이 통합되었기 때문에 기존 분담점의 고객들은 판교점으로 이전됨.
        // sumAmt = 판교점 + 기존 분당점
        JsonObject rsSucc = tradeHistoryService.getBranchInfo("판교점");

        assertEquals(testSucc.get("brName"), rsSucc.get("brName"));
        assertEquals(testSucc.get("brCode"), rsSucc.get("brCode"));
        assertEquals(testSucc.get("sumAmt"), rsSucc.get("sumAmt"));

        // 분당점은 삭제됨
        JsonObject rsFailed = tradeHistoryService.getBranchInfo("분당점");
        assertEquals(testFailed.get("code"), rsFailed.get("code"));
        assertEquals(testFailed.get("메시지"), rsFailed.get("메시지"));

    }
}