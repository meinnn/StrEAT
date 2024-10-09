package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.*;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.TimeUtil;
import io.ssafy.p.j11a307.order.global.YearWeek;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.IsoFields;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final OwnerClient ownerClient;
    private final OrdersRepository ordersRepository;
    private final StoreClient storeClient;
    private final TimeUtil timeUtil;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public Map<Integer, GetDailySalesDTO> getDailySalesList(Integer month, String token) {
        Map<Integer, GetDailySalesDTO> dailySalesDTOMap = new HashMap<Integer, GetDailySalesDTO>();

        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        Integer storeId = storeClient.getStoreIdByUserId(ownerId);
        int[] monthDay = {31,28,31,30,31,30,31,31,30,31,30,31};

        //해당 사장이 가진 점포의 주문내역들 가져오는 것. 다만, 해당 달의 내역만.
        //일단 해당 달의 내역들 다 가져와서, 그 안에서 일별로 찾기
        List<Orders> ordersList = ordersRepository.findByStoreIdAndThisMonth(month, storeId);

        for(int i=1; i<= monthDay[month-1]; i++) {
            List<GetDailySalesListDTO> dailySalesList = new ArrayList<>();
            int totalPayment = 0;

            for (Orders orders : ordersList) {
                //orders의 i일인 주문내역을 찾아서.
                if(orders.getCreatedAt().getDayOfMonth() == i && orders.getStatus() != OrderCode.REJECTED && orders.getStatus() != OrderCode.WAITING_FOR_PAYING) {
                    UserInfoResponse userInfoResponse = ownerClient.getUserInformation(orders.getUserId());

                    GetDailySalesListDTO getDailySalesListDTO = GetDailySalesListDTO.builder()
                            .customerName(userInfoResponse.name())
                            .payAmount(orders.getTotalPrice())
                            .build();

                    totalPayment += getDailySalesListDTO.payAmount();
                    dailySalesList.add(getDailySalesListDTO);
                }
            }
            GetDailySalesDTO getDailySalesDTO = GetDailySalesDTO.builder()
                    .dailySalesList(dailySalesList)
                    .dailyTotalPayAmount(totalPayment)
                    .build();

            dailySalesDTOMap.put(i, getDailySalesDTO);
        }

        return dailySalesDTOMap;
    }

    @Transactional
    public GetSalesListByTimeTypeDTO getSalesListByTimeType(Integer storeId, String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        LocalDateTime now = timeUtil.getCurrentSeoulTime();
        LocalDateTime sixDaysAgo = now.minusDays(6);
        LocalDateTime fiveWeeksAgo = now.minusWeeks(5);
        LocalDateTime fiveMonthsAgo = now.minusMonths(5);
        LocalDateTime twoYearsAgo = now.minusYears(2);

        Map<LocalDate, Integer> dailyMap = new TreeMap<>();
        Map<YearWeek, Integer> weekMap = new TreeMap<>();
        Map<YearMonth, Integer> monthMap = new TreeMap<>();
        Map<Year, Integer> yearMap = new TreeMap<>();

        //일별 매출
        //오늘로부터 6일 전부터 시작.
        //그날 팔린 매출을 일별로 쭉 가져오면 됨!!
        for(LocalDateTime date= sixDaysAgo; !date.isAfter(now); date= date.plusDays(1)) {
            Integer sales = ordersRepository.findOrdersByYearAndMonthAndDay(storeId, date).stream().mapToInt(Orders::getTotalPrice).sum();

            dailyMap.put(getFormattedDate(date), sales);
        }

        //주별 매출
        for(LocalDateTime date= fiveWeeksAgo; !date.isAfter(now); date= date.plusWeeks(1)) {
            LocalDate localDate = date.toLocalDate();
            YearWeek yearWeek = YearWeek.from(localDate);

            // 주의 시작일과 종료일 계산
            // 주의 시작일 계산 (해당 날짜가 속한 주의 월요일 0시)
            LocalDate weekStartDate = localDate.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR))
                    .with(IsoFields.WEEK_BASED_YEAR, localDate.get(IsoFields.WEEK_BASED_YEAR))
                    .with(DayOfWeek.MONDAY); // 월요일로 설정
            LocalDateTime weekStart = weekStartDate.atStartOfDay(); // 0시로 설정

            // 주의 종료일을 일요일의 23시 59분으로 계산
            LocalDate weekEndDate = weekStartDate.plusDays(6); // 일요일
            LocalDateTime weekEnd = weekEndDate.atTime(23, 59, 59); // 23시 59분으로 설정
            Integer sales = ordersRepository.findOrdersByWeek(storeId, weekStart, weekEnd).stream().mapToInt(Orders::getTotalPrice).sum();

            weekMap.put(yearWeek, sales);
        }

        //월별 매출
        for(LocalDateTime date= fiveMonthsAgo; !date.isAfter(now); date= date.plusMonths(1)) {
            Integer sales = ordersRepository.findOrdersByYearAndMonth(storeId, date).stream().mapToInt(Orders::getTotalPrice).sum();

            monthMap.put(YearMonth.from(date), sales);
        }

        //연별 매출
        for(LocalDateTime date= twoYearsAgo; !date.isAfter(now); date= date.plusYears(1)) {
            Integer sales = ordersRepository.findOrdersByYear(storeId, date).stream().mapToInt(Orders::getTotalPrice).sum();

            yearMap.put(Year.from(date), sales);
        }


        GetSalesListByTimeTypeDTO getSalesListByTimeTypeDTO = GetSalesListByTimeTypeDTO.builder()
                .daily(dailyMap)
                .weekly(weekMap)
                .monthly(monthMap)
                .yearly(yearMap)
                .build();

        return getSalesListByTimeTypeDTO;

    }

    public LocalDate getFormattedDate(LocalDateTime localDateTime) {
        // 날짜를 'yyyy-M-d' 형식으로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(localDateTime.format(formatter), formatter);
    }

}
