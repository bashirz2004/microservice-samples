package com.zamani.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController extends BaseController {

    @GetMapping("/test1")
    @PreAuthorize("hasAuthority('test:read')")
    public String test() {
        return "Security Works!";
    }
}
