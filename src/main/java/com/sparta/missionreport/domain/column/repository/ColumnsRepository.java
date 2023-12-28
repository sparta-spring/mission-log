package com.sparta.missionreport.domain.column.repository;

import com.sparta.missionreport.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

}
