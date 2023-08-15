package com.txxy.demo.attendance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 考勤
 *
 * @author zengch
 * @date 2021-08-03 16:50
 */
@Component
@Slf4j
public class Attendance {

    @Resource
    private RestTemplate restTemplate;

    public Map login() {
        String loginUrl = "http://10.195.2.218/logincheck.php";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        headers.set(HttpHeaders.REFERER, "http://10.195.2.218/");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("UNAME", "zengchao");
        multiValueMap.set("PASSWORD",
            "621298f1a9e81a4ad64531d19231560801b6a48fd93eb44eab6e4f7f1d93636381cb764a14956c3bafbe98abb16abf5072c609a2e503b83bb7aba85e680ab1c169fb0ec09b8822750065819cd14b25a53dd65c8b69891a4aee11a733b66c6bc0b298dab8e866a85ad1bb82624a2f1044a362c27c885b6f2158331ff40ec34769");
        multiValueMap.set("encode_type", "1");
        HttpEntity httpEntity = new HttpEntity(multiValueMap, headers);
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity, String.class);
        if (isSuccess(response)) {
            HashMap<String, String> map = new HashMap<>();
            List<String> setCookieList = response.getHeaders().get("Set-Cookie");
            //PHPSESSID=btp2g8aimmvd48v23jfr5fvrg6; path=/
            String phpsessid = setCookieList.get(1);
            phpsessid = phpsessid.split(";")[0].split("=")[1];
            //SID_906=cdfd8ab9; expires=Mon, 29-Apr-2024 09:11:12 GMT; path=/
            String sid = setCookieList.get(4);
            sid = sid.split(";")[0].split("=")[1];
            log.info("{},{}", phpsessid, sid);
            map.put("phpsessid", phpsessid);
            map.put("sid", sid);
            return map;
        }
        return null;
    }

    /**
     * 上班打卡
     */
    public String dutySignIn() {

        Map<String, String> loginMap = login();
        if (Objects.isNull(loginMap)) {
            //打卡失败，发送邮件
            return null;
        }

        String signInUrl =
            "http://10.195.2.218/general/attendance/personal/duty/submit.php?REGISTER_TYPE=1&USER_DUTP=1";

        HttpHeaders headers = createHeaders(loginMap);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(signInUrl, HttpMethod.GET, entity, String.class);
        log.info("responseEntity：{}", responseEntity);
        //校验打卡结果是否成功
        return dutyResult();

    }

    /**
     * 下班打卡
     */
    public String dutySignOut() {
        Map<String, String> loginMap = login();
        if (Objects.isNull(loginMap)) {
            //登录失败，发送邮件
            return null;
        }

        String currentDateStr = LocalDate.now().toString();
        String nextDateStr = LocalDate.now().plusDays(1).toString();
        String signOutUrl =
            "http://10.195.2.218/general/attendance/personal/duty/submit.php?REGISTER_TYPE=2&USER_DUTP=1&IDEN=1&stat_time=[currentDateStr] 00:01:00&end_time=[nextDateStr] 00:00:00";
        signOutUrl = signOutUrl.replace("[currentDateStr]", currentDateStr).replace("[nextDateStr]", nextDateStr);

        HttpHeaders headers = createHeaders(loginMap);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(signOutUrl, HttpMethod.GET, entity, String.class);
        log.info("responseEntity：{}", responseEntity);
        //校验打卡结果是否成功
        return dutyResult();

    }

    public String dutyResult() {

        Map<String, String> loginMap = login();
        if (Objects.isNull(loginMap)) {
            //打卡失败，发送邮件
            return null;
        }
        String checkUrl = "http://10.195.2.218/general/attendance/personal/duty/index.php?connstatus=1";
        HttpHeaders headers = createHeaders(loginMap);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity =
            restTemplate.exchange(checkUrl, HttpMethod.GET, httpEntity, String.class);
        return responseEntity.getBody();

    }

    /**
     * 判断response是否正常
     *
     * @param responseEntity
     * @return
     */
    public boolean isSuccess(ResponseEntity<?> responseEntity) {
        return Objects.nonNull(responseEntity) && responseEntity.getStatusCode().is2xxSuccessful();
    }

    public HttpHeaders createHeaders(Map<String, String> loginMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String cookie = "USER_NAME_COOKIE=zengchao; OA_USER_ID=906; PHPSESSID=[PHPSESSID]; SID_906=[SID]";
        cookie = cookie.replace("[PHPSESSID]", loginMap.get("phpsessid"));
        cookie = cookie.replace("[SID]", loginMap.get("sid"));
        httpHeaders.set(HttpHeaders.COOKIE, cookie);
        httpHeaders.set(HttpHeaders.USER_AGENT,
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        return httpHeaders;
    }

    /**
     * 生产密码经过RSA加密后的内容
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public String buildRsaEncryptResult()
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String psw = "xxxx";
        String modulus =
            "B87A3BE2184FED0973FFB0B02A862DCAD15A1A29172EC8FF67E841FE26749A6AA04E48E9B02D963ED81DCE2B0086C034F7D47CCBACF8539C36B9445ABA5EF484F3CA32593762641B4C9683C79801D087198370D5719BB4E422FADAA4D883D13874DE67D8B6E883EBAACC53A8480F41EE8BE70D2F70BECF3CB7F1023D2C901CC3";
        String exponent = "10001";
        //        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        BigInteger biModulus = new BigInteger(modulus, 16);
        BigInteger biExponent = new BigInteger(exponent);
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(biModulus, biExponent);
        RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(rsaPublicKeySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int key_length = publicKey.getModulus().bitLength() / 8;
        byte[] bytes = cipher.doFinal(psw.getBytes("utf8"));

        char[] temp = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            char c = (char)(((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char)(c > 9 ? c + 'A' - 10 : c + '0');
            c = (char)(bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char)(c > 9 ? c + 'A' - 10 : c + '0');
        }
        String encryptPsw = new String(temp);
        return encryptPsw;
    }
}
