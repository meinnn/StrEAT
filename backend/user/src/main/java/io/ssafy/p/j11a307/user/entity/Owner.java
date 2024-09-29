package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id") // 부모(User)의 id를 기본 키로 사용
public class Owner extends User {
}
