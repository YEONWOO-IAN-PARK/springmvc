package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    // 1. HttpServletRequest객체에서 Http메시지바디를 읽어서
    // 2. JSON데이터의 형태로 있는 HelloData를 objectMapper로 객체로 변환시켜 원하는 정보를 만든다.
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    // 1. @RequestBody로 넘어온 Http메시지바디 정보를 가져와
    // 2. objectMapper를 사용해 JSON데이터를 객체로 변환해 원하는 정보를 만든다.
    // 3. @ResonpseBody를 사용해 응답에 메시지바디를 조작해서 클라이언트로 보낸다.
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // 1. @RequestBody로 가져온 Http메시지바디를 객체상태로 매핑시켜 가져와서 원하는 정보를 조회한다.
    // 2. @ResponseBody를 사용해 응답객체의 메시지바디를 조작해 클라이언트로 보낸다.
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
    * HttpEntity : HTTP 메시지 바디와 헤더 정보 조회 가능
    */
    // 1. HttpEntity<HelloData>를 선언해서 Http메시지 바디,헤더 정보를 받아와
    // 2. .getBody()메서드를 통해 제네릭타입으로 선언된 HelloData를 가져온다.
    // 3. @ResonseBody를 사용하여 response객체에 메시지바디를 조작해 응답으로 보낸다.
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData>  httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }


    /**
     * 문자도 ResponseBody가 적용되고 객체도 ResponseBody가 적용된다.
     * 1. 리턴값에 적용된 HelloData객체가 Http메시지컨버터에 의해서 JSON으로 변형된다.
     * 2. 응답으로 JSON형태의 HelloData객체가 화면으로 전달된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }

}
