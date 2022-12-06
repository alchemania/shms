package com.shms.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.shms.demo.Service.RiskAreaService;
import com.shms.demo.controller.RiskAreaController;
import com.shms.demo.entity.RiskArea;
import com.shms.demo.entity.RiskAreaLog;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class RiskAreaUtils {
    /*
     * 接口调用
     * SHA-256 加密
     * 国务院疫情风险接口
     * <a href="http://bmfw.www.gov.cn/bjww/interface/interfaceJson">...</a>
     * <a href="http://bmfw.www.gov.cn/yqfxdjcx/risk.html">...</a>
     */
    @Data
    public static class AreasTmp {
        private int type;
        private String province;
        private String city;
        private String county;
        private String area_name;
        private List<String> communitys;
    }

    private static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            for (byte aByte : bytes) {
                temp = Integer.toHexString(aByte & 0xFF);
                if (temp.length() == 1) {
                    //1得到一位的进行补0操作
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            encodeStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 创建指定字符集的RestTemplate
     *
     * @param charset 编码
     * @return 返回结果
     */
    private static @NotNull RestTemplate getInstance(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
        return restTemplate;
    }

    /**
     * 测试
     */
    private static JSONObject getall() throws IOException {
        //获取时间戳
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String url = "http://bmfw.www.gov.cn/bjww/interface/interfaceJson";
        //以下常量值都是从网页源代码上获取到的------start
        final String STATE_COUNCIL_SIGNATURE_KEY = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";
        final String STATE_COUNCIL_X_WIF_NONCE = "QkjjtiLM2dCratiA";
        final String STATE_COUNCIL_X_WIF_PAASID = "smt-application";
        //国务院疫情防控查询 appId
        final String STATE_COUNCIL_APP_ID = "NcApplication";
        //国务院疫情防控查询 PASSID
        final String STATE_COUNCIL_PASSID = "zdww";
        //国务院疫情防控查询 秘钥
        final String STATE_COUNCIL_NONCE = "123456789abcdefg";
        //国务院疫情防控查询 token
        final String STATE_COUNCIL_TOEKN = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";
        //国务院疫情防控查询 key
        final String STATE_COUNCIL_KEY = "3C502C97ABDA40D0A60FBEE50FAAD1DA";
        //以下常量值都是从网页源代码上获取到的------end
        HttpHeaders requestHeaders = new HttpHeaders();
        Map<String, Object> body = new HashMap<>(10);
        //计算签名要用的字符串
        String signatureStr = String.format("%d%s%d", timestamp, STATE_COUNCIL_SIGNATURE_KEY, timestamp);
        //计算签名
        String signature = getSHA256StrJava(signatureStr).toUpperCase();
        //请求头参数封装
        requestHeaders.add("x-wif-nonce", STATE_COUNCIL_X_WIF_NONCE);
        requestHeaders.add("x-wif-paasid", STATE_COUNCIL_X_WIF_PAASID);
        requestHeaders.add("x-wif-signature", signature);
        requestHeaders.add("x-wif-timestamp", String.valueOf(timestamp));
        //body参数封装
        body.put("appId", STATE_COUNCIL_APP_ID);
        body.put("paasHeader", STATE_COUNCIL_PASSID);
        body.put("timestampHeader", timestamp);
        body.put("nonceHeader", STATE_COUNCIL_NONCE);
        //请求参数
        signatureStr = String.format("%d%s%s%d", timestamp, STATE_COUNCIL_TOEKN, STATE_COUNCIL_NONCE, timestamp);
        String signatureHeader = getSHA256StrJava(signatureStr).toUpperCase();
        body.put("signatureHeader", signatureHeader);
        body.put("key", STATE_COUNCIL_KEY);
        HttpEntity httpEntity = new HttpEntity(body, requestHeaders);
        ResponseEntity<String> response = getInstance("utf-8").exchange(url, HttpMethod.POST, httpEntity, String.class);
        return JSONObject.parseObject(response.getBody());
    }

    private static String createlogid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * type 1 常态化，没有 2 高风险 3 中风险，没有 4 低风险
     * @param areasTmpList
     * @param logid
     * @param type
     * @return
     */
    private static List<RiskArea> plain(List<AreasTmp> areasTmpList, String logid,int type) {
        List<RiskArea> riskAreaList = new ArrayList<>();
        for (AreasTmp i : areasTmpList) {
            for (String j : i.getCommunitys()) {
                RiskArea epareas = new RiskArea();
                epareas.setArea_name(i.area_name);
                epareas.setCity(i.getCity());
                epareas.setCounty(i.getCounty());
                epareas.setType(type);
                epareas.setProvince(i.getProvince());
                epareas.setCommunity(j);
                epareas.setLogid(logid);
                riskAreaList.add(epareas);
            }
        }
        return riskAreaList;
    }

    public static @NotNull Map<String, Object> getallInfo() throws IOException, ParseException {
        JSONObject all = getall();
        JSONObject data = all.getJSONObject("data");
        JSONArray highlist = data.getJSONArray("highlist");
        JSONArray lowlist = data.getJSONArray("lowlist");
        //useful
        Integer highcount = (Integer) data.get("hcount");
        Integer lowcount = (Integer) data.get("lcount");
        String end_update_time = (String) data.get("end_update_time");

        List<AreasTmp> highlistparsed = highlist.toJavaList(AreasTmp.class);
        List<AreasTmp> lowlistparsed = lowlist.toJavaList(AreasTmp.class);
        String logid = createlogid();

        //useful => <1>
        List<RiskArea> hlistplain = plain(highlistparsed, logid,2);
        List<RiskArea> llistplain = plain(lowlistparsed, logid,4);

        //useful
        end_update_time = end_update_time.split(" ")[0];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fresh = simpleDateFormat.parse(end_update_time);

        //useful => <2>
        RiskAreaLog riskAreaLog=new RiskAreaLog();
        riskAreaLog.setLogid(logid);
        riskAreaLog.setHcount(highcount);
        riskAreaLog.setLcount(lowcount);
        riskAreaLog.setTime(fresh);
        riskAreaLog.setStatus(false);

        Map<String, Object> rt = new HashMap<>();
        rt.put("hl", hlistplain);
        rt.put("ll", llistplain);
        rt.put("log",riskAreaLog);
        return rt;
    }
}