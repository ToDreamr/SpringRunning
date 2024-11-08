package com.pray.controller;

import com.pray.service.DefectMethodQcService;
import com.pray.utils.Result;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MethodQcController
 *
 * @author Cotton Eye Joe
 * @since 2024/11/8 0:09
 */
@RestController
@RequestMapping("/mQc")
@AllArgsConstructor
public class MethodQcController {

    DefectMethodQcService methodQcService;
    @GetMapping(value = "/method")
    public Result<Object> methodQC(){
        return Result.ok(methodQcService.getMethodQc());
    }
}
