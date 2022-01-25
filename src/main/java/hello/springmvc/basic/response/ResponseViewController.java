package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    /**
     * @ResponseBody가 없으면 response/hello 로 뷰 리졸버가 실행되어 뷰를 찾고, 렌더링한다.
     * @ResponseBody가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello라는 문자가 입력된다.
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }


    // @Controller에서 요청핸들러메서드의 반환타입이 String일 경우,
    // 그 String은 뷰리졸버에 의해 보여질 view페이지(jsp)의 이름으로 인식한다.
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!!");

        return "response/hello";
    }

    /**
     * void를 반환하는 경우
     * @Controller를 사용하고, HttpServletResponse, OutputStream(Writer)같은 HTTP메시지바디를 처리하는 파라미터가 없으면
     * 요청 URL을 참고해서 논리 뷰 이름으로 사용한다.
     * 요청URL: @RequestMapping("/response/hello") -> 실행: templates/response/hello.html
     * ### 이 방식은 명시성이 너무 떨어지므로 권장하지 않는다.
     */
    @RequestMapping("/response/hello")
    public void respㅍonseViewV3(Model model) {
        model.addAttribute("data", "hello!!!");
    }
}
