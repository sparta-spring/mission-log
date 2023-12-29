package com.sparta.missionreport.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = 314140691L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final ListPath<CardWorker, QCardWorker> cardWorkerList = this.<CardWorker, QCardWorker>createList("cardWorkerList", CardWorker.class, QCardWorker.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.missionreport.domain.checklist.entity.Checklist, com.sparta.missionreport.domain.checklist.entity.QChecklist> checklistList = this.<com.sparta.missionreport.domain.checklist.entity.Checklist, com.sparta.missionreport.domain.checklist.entity.QChecklist>createList("checklistList", com.sparta.missionreport.domain.checklist.entity.Checklist.class, com.sparta.missionreport.domain.checklist.entity.QChecklist.class, PathInits.DIRECT2);

    public final EnumPath<com.sparta.missionreport.global.enums.Color> color = createEnum("color", com.sparta.missionreport.global.enums.Color.class);

    public final com.sparta.missionreport.domain.column.entity.QColumns columns;

    public final ListPath<com.sparta.missionreport.domain.comment.entity.Comment, com.sparta.missionreport.domain.comment.entity.QComment> commentList = this.<com.sparta.missionreport.domain.comment.entity.Comment, com.sparta.missionreport.domain.comment.entity.QComment>createList("commentList", com.sparta.missionreport.domain.comment.entity.Comment.class, com.sparta.missionreport.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sparta.missionreport.domain.user.entity.QUser createdBy;

    public final DateTimePath<java.time.LocalDateTime> deadLine = createDateTime("deadLine", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.columns = inits.isInitialized("columns") ? new com.sparta.missionreport.domain.column.entity.QColumns(forProperty("columns"), inits.get("columns")) : null;
        this.createdBy = inits.isInitialized("createdBy") ? new com.sparta.missionreport.domain.user.entity.QUser(forProperty("createdBy")) : null;
    }

}

