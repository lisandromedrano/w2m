package com.w2m.lisandro.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
abstract class AuditableEntity {
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    @JsonIgnore
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @JsonIgnore
    protected LocalDateTime updatedAt;

}
