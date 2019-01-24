package com.sarawukl.gitlablinemgr.controller;

import com.sarawukl.gitlablinemgr.handler.CustomException;
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
    ResponseEntity requestCallback(@RequestHeader(value = "X-Gitlab-Event") String gitLabEvent, @RequestBody String data, @PathVariable String id, @RequestParam("uuid") String uuid) throws Exception {
        return apiService.requestCallback(gitLabEvent, data, Long.valueOf(id), uuid);
    }

}
