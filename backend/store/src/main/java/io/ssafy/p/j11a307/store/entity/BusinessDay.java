package io.ssafy.p.j11a307.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(length = 20)
    private String mondayStart;

    @Column(length = 20)
    private String mondayEnd;

    @Column(length = 20)
    private String tuesdayStart;

    @Column(length = 20)
    private String tuesdayEnd;

    @Column(length = 20)
    private String wednesdayStart;

    @Column(length = 20)
    private String wednesdayEnd;

    @Column(length = 20)
    private String thursdayStart;

    private String thursdayEnd;
    private String fridayStart;
    private String fridayEnd;
    private String saturdayStart;
    private String saturdayEnd;
    private String sundayStart;
    private String sundayEnd;

    // 필드별 값을 변경하는 명시적인 메서드
    public void changeMondayHours(String start, String end) {
        this.mondayStart = start;
        this.mondayEnd = end;
    }

    public void changeTuesdayHours(String start, String end) {
        this.tuesdayStart = start;
        this.tuesdayEnd = end;
    }

    public void changeWednesdayHours(String start, String end) {
        this.wednesdayStart = start;
        this.wednesdayEnd = end;
    }

    public void changeThursdayHours(String start, String end) {
        this.thursdayStart = start;
        this.thursdayEnd = end;
    }

    public void changeFridayHours(String start, String end) {
        this.fridayStart = start;
        this.fridayEnd = end;
    }

    public void changeSaturdayHours(String start, String end) {
        this.saturdayStart = start;
        this.saturdayEnd = end;
    }

    public void changeSundayHours(String start, String end) {
        this.sundayStart = start;
        this.sundayEnd = end;
    }

}