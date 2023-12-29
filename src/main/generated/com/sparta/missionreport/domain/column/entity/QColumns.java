package com.sparta.missionreport.domain.column.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QColumns is a Querydsl query type for Columns
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColumns extends EntityPathBase<Columns> {

    private static final long serialVersionUID = 1787410548L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QColumns columns = new QColumns("columns");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final com.sparta.missionreport.domain.board.entity.QBoard board;

    public final ListPath<com.sparta.missionreport.domain.card.entity.Card, com.sparta.missionreport.domain.card.entity.QCard> cardList = this.<com.sparta.missionreport.domain.card.entity.Card, com.sparta.missionreport.domain.card.entity.QCard>createList("cardList", com.sparta.missionreport.domain.card.entity.Card.class, com.sparta.missionreport.domain.card.entity.QCard.class, PathInits.DIRECT2);

    public final EnumPath<com.sparta.missionreport.global.enums.Color> color = createEnum("color", com.sparta.missionreport.global.enums.Color.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public QColumns(String variable) {
        this(Columns.class, forVariable(variable), INITS);
    }

    public QColumns(Path<? extends Columns> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QColumns(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QColumns(PathMetadata metadata, PathInits inits) {
        this(Columns.class, metadata, inits);
    }

    public QColumns(Class<? extends Columns> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.sparta.missionreport.domain.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

