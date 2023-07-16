package com.nro.footballmanager.repository;

import com.nro.footballmanager.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result,Long> {
}
