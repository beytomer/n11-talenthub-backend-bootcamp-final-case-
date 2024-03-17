package com.n11.loggerservice.repository;

import com.n11.loggerservice.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author BeytullahBilek
 */
public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long> {
}
