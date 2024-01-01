package com.sparta.missionreport.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardWorker is a Querydsl query type for BoardWorker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardWorker extends EntityPathBase<BoardWorker> {

    private static final long serialVersionUID = 1534462455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardWorker boardWorker = new QBoardWorker("boardWorker");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.sparta.missionreport.domain.user.entity.QUser user;

    public QBoardWorker(String variable) {
        this(BoardWorker.class, forVariable(variable), INITS);
    }

    public QBoardWorker(Path<? extends BoardWorker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardWorker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardWorker(PathMetadata metadata, PathInits inits) {
        this(BoardWorker.class, metadata, inits);
    }

    public QBoardWorker(Class<? extends BoardWorker> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.missionreport.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

