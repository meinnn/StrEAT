package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DistrictSales {
        @Id // 적절한 기본 키를 설정해야 합니다. 필요시 복합키를 만들 수 있습니다.
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "business_district_code")
        private String businessDistrictCode;

        @Column(name = "business_district_name")
        private String businessDistrictName;

        @Column(name = "district_code")
        private String districtCode;

        @Column(name = "district_name")
        private String districtName;

        @Column(name = "service_type_code")
        private String serviceTypeCode;

        @Column(name = "service_type_name")
        private String serviceTypeName;

        @Column(name = "current_month_sales_amount")
        private long currentMonthSalesAmount;

        @Column(name = "current_month_sales_count")
        private int currentMonthSalesCount;

        @Column(name = "weekday_sales_amount")
        private long weekdaySalesAmount;

        @Column(name = "weekend_sales_amount")
        private long weekendSalesAmount;

        @Column(name = "monday_sales_amount")
        private long mondaySalesAmount;

        @Column(name = "tuesday_sales_amount")
        private long tuesdaySalesAmount;

        @Column(name = "wednesday_sales_amount")
        private long wednesdaySalesAmount;

        @Column(name = "thursday_sales_amount")
        private long thursdaySalesAmount;

        @Column(name = "friday_sales_amount")
        private long fridaySalesAmount;

        @Column(name = "saturday_sales_amount")
        private long saturdaySalesAmount;

        @Column(name = "sunday_sales_amount")
        private long sundaySalesAmount;

        @Column(name = "time_slot_00_to_06_sales_amount")
        private long timeSlot00To06SalesAmount;

        @Column(name = "time_slot_06_to_11_sales_amount")
        private long timeSlot06To11SalesAmount;

        @Column(name = "time_slot_11_to_14_sales_amount")
        private long timeSlot11To14SalesAmount;

        @Column(name = "time_slot_14_to_17_sales_amount")
        private long timeSlot14To17SalesAmount;

        @Column(name = "time_slot_17_to_21_sales_amount")
        private long timeSlot17To21SalesAmount;

        @Column(name = "time_slot_21_to_24_sales_amount")
        private long timeSlot21To24SalesAmount;

        @Column(name = "male_sales_amount")
        private long maleSalesAmount;

        @Column(name = "female_sales_amount")
        private long femaleSalesAmount;

        @Column(name = "age_group_10_sales_amount")
        private long ageGroup10SalesAmount;

        @Column(name = "age_group_20_sales_amount")
        private long ageGroup20SalesAmount;

        @Column(name = "age_group_30_sales_amount")
        private long ageGroup30SalesAmount;

        @Column(name = "age_group_40_sales_amount")
        private long ageGroup40SalesAmount;

        @Column(name = "age_group_50_sales_amount")
        private long ageGroup50SalesAmount;

        @Column(name = "age_group_60_plus_sales_amount")
        private long ageGroup60PlusSalesAmount;

        @Column(name = "weekday_sales_count")
        private int weekdaySalesCount;

        @Column(name = "weekend_sales_count")
        private int weekendSalesCount;

        @Column(name = "monday_sales_count")
        private int mondaySalesCount;

        @Column(name = "tuesday_sales_count")
        private int tuesdaySalesCount;

        @Column(name = "wednesday_sales_count")
        private int wednesdaySalesCount;

        @Column(name = "thursday_sales_count")
        private int thursdaySalesCount;

        @Column(name = "friday_sales_count")
        private int fridaySalesCount;

        @Column(name = "saturday_sales_count")
        private int saturdaySalesCount;

        @Column(name = "sunday_sales_count")
        private int sundaySalesCount;

        @Column(name = "time_slot_00_to_06_sales_count")
        private int timeSlot00To06SalesCount;

        @Column(name = "time_slot_06_to_11_sales_count")
        private int timeSlot06To11SalesCount;

        @Column(name = "time_slot_11_to_14_sales_count")
        private int timeSlot11To14SalesCount;

        @Column(name = "time_slot_14_to_17_sales_count")
        private int timeSlot14To17SalesCount;

        @Column(name = "time_slot_17_to_21_sales_count")
        private int timeSlot17To21SalesCount;

        @Column(name = "time_slot_21_to_24_sales_count")
        private int timeSlot21To24SalesCount;

        @Column(name = "male_sales_count")
        private int maleSalesCount;

        @Column(name = "female_sales_count")
        private int femaleSalesCount;

        @Column(name = "age_group_10_sales_count")
        private int ageGroup10SalesCount;

        @Column(name = "age_group_20_sales_count")
        private int ageGroup20SalesCount;

        @Column(name = "age_group_30_sales_count")
        private int ageGroup30SalesCount;

        @Column(name = "age_group_40_sales_count")
        private int ageGroup40SalesCount;

        @Column(name = "age_group_50_sales_count")
        private int ageGroup50SalesCount;

        @Column(name = "age_group_60_plus_sales_count")
        private int ageGroup60PlusSalesCount;

        @Column(name = "latitude")
        private Double latitude;

        @Column(name = "longitude")
        private Double longitude;

        @Builder
        public DistrictSales(String businessDistrictCode, String businessDistrictName, String districtCode, String districtName, String serviceTypeCode, String serviceTypeName, long currentMonthSalesAmount, int currentMonthSalesCount, long weekdaySalesAmount, long weekendSalesAmount, long mondaySalesAmount, long tuesdaySalesAmount, long wednesdaySalesAmount, long thursdaySalesAmount, long fridaySalesAmount, long saturdaySalesAmount, long sundaySalesAmount, long timeSlot00To06SalesAmount, long timeSlot06To11SalesAmount, long timeSlot11To14SalesAmount, long timeSlot14To17SalesAmount, long timeSlot17To21SalesAmount, long timeSlot21To24SalesAmount, long maleSalesAmount, long femaleSalesAmount, long ageGroup10SalesAmount, long ageGroup20SalesAmount, long ageGroup30SalesAmount, long ageGroup40SalesAmount, long ageGroup50SalesAmount, long ageGroup60PlusSalesAmount, int weekdaySalesCount, int weekendSalesCount, int mondaySalesCount, int tuesdaySalesCount, int wednesdaySalesCount, int thursdaySalesCount, int fridaySalesCount, int saturdaySalesCount, int sundaySalesCount, int timeSlot00To06SalesCount, int timeSlot06To11SalesCount, int timeSlot11To14SalesCount, int timeSlot14To17SalesCount, int timeSlot17To21SalesCount, int timeSlot21To24SalesCount, int maleSalesCount, int femaleSalesCount, int ageGroup10SalesCount, int ageGroup20SalesCount, int ageGroup30SalesCount, int ageGroup40SalesCount, int ageGroup50SalesCount, int ageGroup60PlusSalesCount, Double latitude, Double longitude) {
                this.businessDistrictCode = businessDistrictCode;
                this.businessDistrictName = businessDistrictName;
                this.districtCode = districtCode;
                this.districtName = districtName;
                this.serviceTypeCode = serviceTypeCode;
                this.serviceTypeName = serviceTypeName;
                this.currentMonthSalesAmount = currentMonthSalesAmount;
                this.currentMonthSalesCount = currentMonthSalesCount;
                this.weekdaySalesAmount = weekdaySalesAmount;
                this.weekendSalesAmount = weekendSalesAmount;
                this.mondaySalesAmount = mondaySalesAmount;
                this.tuesdaySalesAmount = tuesdaySalesAmount;
                this.wednesdaySalesAmount = wednesdaySalesAmount;
                this.thursdaySalesAmount = thursdaySalesAmount;
                this.fridaySalesAmount = fridaySalesAmount;
                this.saturdaySalesAmount = saturdaySalesAmount;
                this.sundaySalesAmount = sundaySalesAmount;
                this.timeSlot00To06SalesAmount = timeSlot00To06SalesAmount;
                this.timeSlot06To11SalesAmount = timeSlot06To11SalesAmount;
                this.timeSlot11To14SalesAmount = timeSlot11To14SalesAmount;
                this.timeSlot14To17SalesAmount = timeSlot14To17SalesAmount;
                this.timeSlot17To21SalesAmount = timeSlot17To21SalesAmount;
                this.timeSlot21To24SalesAmount = timeSlot21To24SalesAmount;
                this.maleSalesAmount = maleSalesAmount;
                this.femaleSalesAmount = femaleSalesAmount;
                this.ageGroup10SalesAmount = ageGroup10SalesAmount;
                this.ageGroup20SalesAmount = ageGroup20SalesAmount;
                this.ageGroup30SalesAmount = ageGroup30SalesAmount;
                this.ageGroup40SalesAmount = ageGroup40SalesAmount;
                this.ageGroup50SalesAmount = ageGroup50SalesAmount;
                this.ageGroup60PlusSalesAmount = ageGroup60PlusSalesAmount;
                this.weekdaySalesCount = weekdaySalesCount;
                this.weekendSalesCount = weekendSalesCount;
                this.mondaySalesCount = mondaySalesCount;
                this.tuesdaySalesCount = tuesdaySalesCount;
                this.wednesdaySalesCount = wednesdaySalesCount;
                this.thursdaySalesCount = thursdaySalesCount;
                this.fridaySalesCount = fridaySalesCount;
                this.saturdaySalesCount = saturdaySalesCount;
                this.sundaySalesCount = sundaySalesCount;
                this.timeSlot00To06SalesCount = timeSlot00To06SalesCount;
                this.timeSlot06To11SalesCount = timeSlot06To11SalesCount;
                this.timeSlot11To14SalesCount = timeSlot11To14SalesCount;
                this.timeSlot14To17SalesCount = timeSlot14To17SalesCount;
                this.timeSlot17To21SalesCount = timeSlot17To21SalesCount;
                this.timeSlot21To24SalesCount = timeSlot21To24SalesCount;
                this.maleSalesCount = maleSalesCount;
                this.femaleSalesCount = femaleSalesCount;
                this.ageGroup10SalesCount = ageGroup10SalesCount;
                this.ageGroup20SalesCount = ageGroup20SalesCount;
                this.ageGroup30SalesCount = ageGroup30SalesCount;
                this.ageGroup40SalesCount = ageGroup40SalesCount;
                this.ageGroup50SalesCount = ageGroup50SalesCount;
                this.ageGroup60PlusSalesCount = ageGroup60PlusSalesCount;
                this.latitude = latitude;
                this.longitude = longitude;
        }
}
