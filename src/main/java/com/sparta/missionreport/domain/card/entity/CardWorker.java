package com.sparta.missionreport.domain.card.entity;

import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_worker")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardWorker extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column
    private boolean isDeleted = false;

    public String getUserEmail() {
        return this.user.getEmail();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
