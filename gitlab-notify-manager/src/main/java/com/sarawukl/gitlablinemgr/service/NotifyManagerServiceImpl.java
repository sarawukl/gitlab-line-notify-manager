package com.sarawukl.gitlablinemgr.service;

import com.sarawukl.gitlablinemgr.model.Notify;
import com.sarawukl.gitlablinemgr.repository.NotifyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
@Slf4j
public class NotifyManagerServiceImpl implements NotifyManagerService {

    private final NotifyRepository notifyRepository;
    private final AuthenticationService authenticationService;

    public NotifyManagerServiceImpl(NotifyRepository notifyRepository, AuthenticationService authenticationService) {
        this.notifyRepository = notifyRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Notify> findAll() {
        log.debug("Find all notify");
        List<Notify> notifies = new ArrayList<>();
        notifyRepository.findAll().forEach(notifies::add);
        return notifies;
    }

    @Override
    public List<Notify> getUserNotify() {
        log.debug("Get user notify");
        return findByUserName(authenticationService.getAuthentication().getName());
    }

    @Override
    public List<Notify> findByUserName(String userName) {
        log.debug("Find notify by username");
        return notifyRepository.findByUsername(userName);
    }

    @Override
    public List<Notify> findByType(String type) {
        log.debug("Find notify by event type");
        return notifyRepository.findByNotifyType(type);
    }

    @Override
    public Notify save(Notify notify) {
        log.debug("Save notify");
        if (notify.getUuid().isEmpty()) {
            notify.setUuid(generateSafeToken());
        }
        notify.setUsername(authenticationService.getAuthentication().getName());
        return notifyRepository.save(notify);
    }

    @Override
    public Notify findById(Long id) {
        log.debug("Find notify by notifyId");
        String userAuthen = authenticationService.getAuthentication().getName();
        Notify returnedNotify = notifyRepository.findById(id).orElse(null);
        //validate user
        if (returnedNotify != null && matchedValue(returnedNotify.getUsername(), userAuthen)) {
            log.debug("Invalid notify");
            return returnedNotify;
        }
        return null;
    }

    @Override
    public Page<Notify> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Notify> returnNotifies = new ArrayList<>();
        List<Notify> notifies = this.getUserNotify();

        if (notifies.size() > startItem) {
            int toIndex = Math.min(startItem + pageSize, notifies.size());
            returnNotifies = notifies.subList(startItem, toIndex);
        }
        Page<Notify> notifyPage
                = new PageImpl<Notify>(returnNotifies, PageRequest.of(currentPage, pageSize), notifies.size());

        return notifyPage;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean isSuccess = false;
        String userAuthen = authenticationService.getAuthentication().getName();
        Notify returnedNotify = notifyRepository.findById(id).orElse(null);
        //validate user
        if (returnedNotify != null && matchedValue(returnedNotify.getUsername(), userAuthen)) {
            isSuccess = true;
            notifyRepository.deleteById(id);
        }
        return isSuccess;
    }

    public boolean matchedValue(String value1, String value2) {
        return value1.equals(value2);
    }

    private String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }

}
