package com.sparta.missionreport.domain.column.repository;

import com.sparta.missionreport.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    Optional<Columns> findByName(String name);
}
