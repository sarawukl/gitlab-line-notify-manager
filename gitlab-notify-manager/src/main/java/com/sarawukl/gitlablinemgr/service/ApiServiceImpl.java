package com.sarawukl.gitlablinemgr.service;

import com.sarawukl.gitlablinemgr.handler.CustomException;
import com.sarawukl.gitlablinemgr.model.Notify;
import com.sarawukl.gitlablinemgr.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ApiServiceImpl implements ApiService {

    private final NotifyRepository notifyRepository;

    @Value("${callbackurl}")
    private String baseUrl;

    public ApiServiceImpl(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    @Override
    public Notify findById(Long id) {
        return notifyRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> requestCallback(String gitLabEvent, String data, Long id, String uuid) throws Exception {

        Notify notify = findById(id);
        if (notify == null) {
            String error = String.format("Error! Notify ID '%s' Data Not Found", id);
            throw new CustomException(error);
        }
        if (!notify.getUuid().equals(uuid)) {
            System.out.println("Invalid uuid");
            String error = String.format("Invalid uuid");
            throw new CustomException(error);
        }
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Line-Token", notify.getLineToken());
        headers.add("X-Gitlab-Event", gitLabEvent);

        String event = notify.getNotifyType().toLowerCase();
        String url = String.format("%s/%s", baseUrl, event);
        URI uri = URI.create(url);

        RestTemplate template = new RestTemplate();
        RequestEntity request = new RequestEntity(
                data, headers, HttpMethod.POST, uri);

        ResponseEntity responseEntity = template.exchange(request, String.class);
        return responseEntity;
    }
}
