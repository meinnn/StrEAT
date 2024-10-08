package io.ssafy.p.j11a307.announcement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecruitAnnouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String recruitPostTitle;
    private String eventName;
    private String eventDays;
    private String eventTimes;
    private String eventPlace;
    private String recruitSize;
    private String entryConditions;
    private String specialNotes;

}
