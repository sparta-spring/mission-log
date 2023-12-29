package com.sparta.missionreport.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardWorker is a Querydsl query type for CardWorker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardWorker extends EntityPathBase<CardWorker> {

    private static final long serialVersionUID = 1944653169L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardWorker cardWorker = new QCardWorker("cardWorker");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final QCard card;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.sparta.missionreport.domain.user.entity.QUser user;

    public QCardWorker(String variable) {
        this(CardWorker.class, forVariable(variable), INITS);
    }

    public QCardWorker(Path<? extends CardWorker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardWorker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardWorker(PathMetadata metadata, PathInits inits) {
        this(CardWorker.class, metadata, inits);
    }

    public QCardWorker(Class<? extends CardWorker> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.missionreport.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

