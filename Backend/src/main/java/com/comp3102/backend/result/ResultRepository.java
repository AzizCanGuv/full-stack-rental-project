package com.comp3102.backend.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
