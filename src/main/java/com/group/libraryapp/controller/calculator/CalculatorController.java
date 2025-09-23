package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // 주어진 Class를 Controller로 등록한다 / Controller : API의 입구
public class CalculatorController {

    @GetMapping("/add") // 아래 함수를 HTTP Method가 GET이고 HTTP Path가 /add인 API로 지정한다
    public int addTwoNumbers(CalculatorAddRequest request) { // @RequestParam : 주어지는 쿼리를 함수 파라미터에 넣는다
        // 여기서는 RequestParam 사용하지 않고 바로 객체 선언해서 그거 들고와서 사용함 -> 코드 깔끔~
        return request.getNumber1() + request.getNumber2();
    }

    @PostMapping("/multiply") // POST /multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }

}
