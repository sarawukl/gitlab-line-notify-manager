package com.sarawukl.gitlablinemgr.controller;

import com.sarawukl.gitlablinemgr.handler.TokenNotFoundException;
import com.sarawukl.gitlablinemgr.model.Notify;
import com.sarawukl.gitlablinemgr.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/{id}")
    ResponseEntity requestCallback(@RequestHeader(value = "X-Gitlab-Event") String gitLabEvent, @RequestBody String data, @PathVariable String id) throws TokenNotFoundException {
        return apiService.requestCallback(gitLabEvent, data, Long.valueOf(id));
    }

}
