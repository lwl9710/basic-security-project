package com.basic.main.controller;

import com.basic.main.dto.Result;
import com.basic.main.utils.JwtUtil;
import com.basic.main.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: 南天
 * Date: 2020-06-12 15:49
 * Content:
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("create")
    public Result<String> create() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "小明");
        return ResultUtil.getSuccessResult(JwtUtil.createToken(data));
    }

    @PostMapping("parser")
    public Result<Map<String, Object>> parser(@RequestBody String token) {
        return ResultUtil.getSuccessResult(JwtUtil.parserToken(token));
    }
}
