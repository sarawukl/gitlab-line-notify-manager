package com.sarawukl.gitlablinemgr.service;

import com.sarawukl.gitlablinemgr.model.Notify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotifyManagerService {

    Notify findById(Long id);

    List<Notify> findAll();

    List<Notify> getUserNotify();

    List<Notify> findByUserName(String userName);

    List<Notify> findByType(String type);

    Page<Notify> findPaginated(Pageable pageable);

    Notify save(Notify notify);

    boolean deleteById(Long id);

}
