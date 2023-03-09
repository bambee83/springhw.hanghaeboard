package com.sparta.crudbasedjwt.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass @EntityListeners(AuditingEntityListener.class) //사실 얘네는 걍 세트라고 외움
public class Timestamped { //상속을 이용해서 생성/수정 시간을 관리해준다.
    @CreatedDate
    private LocalDateTime createdAt; //생성시간
    @LastModifiedDate
    private LocalDateTime modifiedAt; //수정시간
}
