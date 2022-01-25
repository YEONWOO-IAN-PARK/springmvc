package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("response-body-string ok");

    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("response-body-string ok");

    }

    /**
     * HttpEntity: HTTP header, body정보를 편리하게 조회
     *  - 메시지 바디 정보를 직접 조회
     *  - 요청 파라미터를 조회하는 기능과는 관계가 없음
     * HttpEntity는 응답에도 사용 가능
     *  - 메시지 바디 정보 직접 반환
     *  - 헤더 정보 포함 가능
     *  - view 조회 X
     *
     *  HttpEntity를 상속받은 "RequestEntity", "ResponseEntity"도 사용할 수 있다
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        //HttpHeaders headers = httpEntity.getHeaders(); Http 헤더정보도 받아올 수 있다.
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("request-body-v3 ok");
    }


    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody={}", messageBody);

        return "request-body-v4 ok";
    }
}
