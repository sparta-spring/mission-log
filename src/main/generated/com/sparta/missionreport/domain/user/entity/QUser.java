package com.sparta.missionreport.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -749205239L;

    public static final QUser user = new QUser("user");

    public final com.sparta.missionreport.global.entity.QCommonEntity _super = new com.sparta.missionreport.global.entity.QCommonEntity(this);

    public final ListPath<com.sparta.missionreport.domain.board.entity.Board, com.sparta.missionreport.domain.board.entity.QBoard> boardList = this.<com.sparta.missionreport.domain.board.entity.Board, com.sparta.missionreport.domain.board.entity.QBoard>createList("boardList", com.sparta.missionreport.domain.board.entity.Board.class, com.sparta.missionreport.domain.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.missionreport.domain.board.entity.BoardWorker, com.sparta.missionreport.domain.board.entity.QBoardWorker> boardWorkerList = this.<com.sparta.missionreport.domain.board.entity.BoardWorker, com.sparta.missionreport.domain.board.entity.QBoardWorker>createList("boardWorkerList", com.sparta.missionreport.domain.board.entity.BoardWorker.class, com.sparta.missionreport.domain.board.entity.QBoardWorker.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.missionreport.domain.card.entity.Card, com.sparta.missionreport.domain.card.entity.QCard> cardList = this.<com.sparta.missionreport.domain.card.entity.Card, com.sparta.missionreport.domain.card.entity.QCard>createList("cardList", com.sparta.missionreport.domain.card.entity.Card.class, com.sparta.missionreport.domain.card.entity.QCard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.missionreport.domain.card.entity.CardWorker, com.sparta.missionreport.domain.card.entity.QCardWorker> cardWorkerList = this.<com.sparta.missionreport.domain.card.entity.CardWorker, com.sparta.missionreport.domain.card.entity.QCardWorker>createList("cardWorkerList", com.sparta.missionreport.domain.card.entity.CardWorker.class, com.sparta.missionreport.domain.card.entity.QCardWorker.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.missionreport.domain.comment.entity.Comment, com.sparta.missionreport.domain.comment.entity.QComment> commentList = this.<com.sparta.missionreport.domain.comment.entity.Comment, com.sparta.missionreport.domain.comment.entity.QComment>createList("commentList", com.sparta.missionreport.domain.comment.entity.Comment.class, com.sparta.missionreport.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<com.sparta.missionreport.domain.user.enums.UserRole> role = createEnum("role", com.sparta.missionreport.domain.user.enums.UserRole.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

