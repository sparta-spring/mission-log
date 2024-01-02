package com.sparta.missionreport.domain.column.util;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.global.enums.Color;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

import java.time.LocalDateTime;

public class ColumnsTestUtil {

    public static Columns get(Columns columns, Board board, Long sequence, Boolean isDeleted) {
        return get(columns, 1L, LocalDateTime.now(), LocalDateTime.now(), board, sequence, isDeleted);
    }

    public static Columns get(Columns columns, Long id
            , LocalDateTime createdDate, LocalDateTime modifiedDate
            , Board board, Long sequence, Boolean isDeleted) {
        Columns newColumns = SerializationUtils.clone(columns);
        ReflectionTestUtils.setField(newColumns, Columns.class, "id", id, Long.class);
        ReflectionTestUtils.setField(newColumns, Columns.class, "createdAt", createdDate, LocalDateTime.class);
        ReflectionTestUtils.setField(newColumns, Columns.class, "modifiedAt", modifiedDate, LocalDateTime.class);
        ReflectionTestUtils.setField(newColumns, Columns.class, "board", board, Board.class);
        ReflectionTestUtils.setField(newColumns, Columns.class, "sequence", sequence, Long.class);
        ReflectionTestUtils.setField(newColumns, Columns.class, "isDeleted", isDeleted, Boolean.class);
        return newColumns;
    }
}
