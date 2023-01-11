package com.sherryagustin.api.repository;

import com.sherryagustin.api.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAllByIsDoneTrue();
    List<ToDo> findAllByIsDoneFalse();
}