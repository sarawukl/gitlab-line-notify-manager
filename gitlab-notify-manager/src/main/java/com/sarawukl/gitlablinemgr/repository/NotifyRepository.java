package com.sarawukl.gitlablinemgr.repository;

import com.sarawukl.gitlablinemgr.model.Notify;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifyRepository extends CrudRepository<Notify, Long> {

    Optional<Notify> findById(Long id);

    List<Notify> findByUsername(String username);

    List<Notify> findByNotifyType(String type);
}
