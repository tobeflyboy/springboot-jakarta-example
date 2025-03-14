package com.nutcracker.example.demo.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * check alive controller
 *
 * @author 胡桃夹子
 * @since 2024-10-08 11:24
 */
@Tag(name = "健康检查", description = "用于健康检查")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CheckAliveController {

    /**
     * check alive
     *
     * @return {@link String }
     */
    @Operation(summary = "健康检查接口")
    @GetMapping("/alive")
    public String alive() {
        return "{\"code\": 200, \"message\": \"keep alive\", \"flag\": true, \"data\": null}";
    }
}
