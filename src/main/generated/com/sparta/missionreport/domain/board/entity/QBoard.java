package com.sparta.missionreport.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -188235495L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final ListPath<BoardWorker, QBoardWorker> boardWorkerList = this.<BoardWorker, QBoardWorker>createList("boardWorkerList", BoardWorker.class, QBoardWorker.class, PathInits.DIRECT2);

    public final EnumPath<com.sparta.missionreport.global.enums.Color> color = createEnum("color", com.sparta.missionreport.global.enums.Color.class);

    public final ListPath<com.sparta.missionreport.domain.column.entity.Columns, com.sparta.missionreport.domain.column.entity.QColumns> columnsList = this.<com.sparta.missionreport.domain.column.entity.Columns, com.sparta.missionreport.domain.column.entity.QColumns>createList("columnsList", com.sparta.missionreport.domain.column.entity.Columns.class, com.sparta.missionreport.domain.column.entity.QColumns.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sparta.missionreport.domain.user.entity.QUser createdBy;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy = inits.isInitialized("createdBy") ? new com.sparta.missionreport.domain.user.entity.QUser(forProperty("createdBy")) : null;
    }

}

