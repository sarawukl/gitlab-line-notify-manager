package com.sarawukl.gitlablinemgr.service;

import com.sarawukl.gitlablinemgr.model.Notify;
import org.springframework.http.ResponseEntity;

public interface ApiService {

    Notify findById(Long id);

    ResponseEntity<String> requestCallback(String gitLabEvent, String data, Long id, String uuid) throws Exception;
}
