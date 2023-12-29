package com.sparta.missionreport.domain.checklist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChecklist is a Querydsl query type for Checklist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChecklist extends EntityPathBase<Checklist> {

    private static final long serialVersionUID = -205534055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChecklist checklist = new QChecklist("checklist");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final com.sparta.missionreport.domain.card.entity.QCard card;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> sequence = createNumber("sequence", Long.class);

    public QChecklist(String variable) {
        this(Checklist.class, forVariable(variable), INITS);
    }

    public QChecklist(Path<? extends Checklist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChecklist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChecklist(PathMetadata metadata, PathInits inits) {
        this(Checklist.class, metadata, inits);
    }

    public QChecklist(Class<? extends Checklist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.sparta.missionreport.domain.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
    }

}

