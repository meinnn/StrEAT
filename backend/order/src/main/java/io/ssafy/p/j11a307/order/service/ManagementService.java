package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.*;
import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.TimeUtil;
import io.ssafy.p.j11a307.order.global.YearWeek;
import io.ssafy.p.j11a307.order.repository.OrderProductRepository;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import java.util.stream.Collectors;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final OwnerClient ownerClient;
    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;
    private final StoreClient storeClient;
    private final ProductClient productClient;
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
    public GetSalesListByTimeTypeDTO getSalesListByTimeType(String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        Integer storeId = storeClient.getStoreIdByUserId(ownerId);

        LocalDateTime now = timeUtil.getCurrentSeoulTime();
        LocalDateTime sixDaysAgo = now.minusDays(6);
        LocalDateTime fiveWeeksAgo = now.minusWeeks(5);
        LocalDateTime fiveMonthsAgo = now.minusMonths(5);
        LocalDateTime twoYearsAgo = now.minusYears(2);

        Map<LocalDate, Integer> dailyMap = new TreeMap<>();
        Map<YearWeek, Integer> weekMap = new TreeMap<>();
        Map<YearMonth, Integer> monthMap = new TreeMap<>();
        Map<Year, Integer> yearMap = new TreeMap<>();

        //일별 맵
        Map<String, GetSalesListByTimeTypeProductDTO> map = new HashMap<>();
        int totalDailyOrders = 0;

        //연별 맵
        Map<String, GetSalesListByTimeTypeProductDTO> ymap = new HashMap<>();
        int totalYearlyOrders = 0;

        //월별 맵
        Map<String, GetSalesListByTimeTypeProductDTO> mmap = new HashMap<>();
        int totalMonthlyOrders = 0;

        //주별 맵
        Map<String, GetSalesListByTimeTypeProductDTO> wmap = new HashMap<>();
        int totalWeeklyOrders = 0;

        //일별 매출
        //오늘로부터 6일 전부터 시작.
        for(LocalDateTime date= sixDaysAgo; !date.isAfter(now); date= date.plusDays(1)) {
            List<Orders> dailyOrders = ordersRepository.findOrdersByYearAndMonthAndDay(storeId, date);
            Integer sales = dailyOrders.stream().mapToInt(Orders::getTotalPrice).sum();

            if(date.isEqual(now)) {
                //Orders의 상품들 모두 가져오기


                for(Orders orders : dailyOrders) {
                    //상품 이름들 가져와서 map에다 집어넣기.
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orders);

                    //상품들 돌면서 몇개인지
                    for (OrderProduct orderProduct : orderProducts) {
                        totalDailyOrders += orderProduct.getCount();
                        ReadProductDTO readProductDTO = productClient.getProductById(orderProduct.getProductId()).getData();

                        if(map.containsKey(readProductDTO.getName())) {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = map.get(readProductDTO.getName());

                            getSalesListByTimeTypeProductDTO.setQuantity(getSalesListByTimeTypeProductDTO.getQuantity() + orderProduct.getCount());

                            map.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }else {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = GetSalesListByTimeTypeProductDTO.builder().quantity(orderProduct.getCount()).build();

                            map.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }
                    }
                }


                //맵을 돌면서 퍼센트 계산
                for(Map.Entry<String, GetSalesListByTimeTypeProductDTO> entry : map.entrySet()) {
                    entry.getValue().setPercent((double)entry.getValue().getQuantity() /totalDailyOrders * 100);
                }



            }

            dailyMap.put(getFormattedDate(date), sales);
        }

        //주별 매출
        for(LocalDateTime date= fiveWeeksAgo; !date.isAfter(now); date= date.plusWeeks(1)) {
            LocalDate localDate = date.toLocalDate();
            //YearWeek yearWeek = YearWeek.from(localDate);

            // 주의 시작일과 종료일 계산
            // 주의 시작일 계산 (해당 날짜가 속한 주의 월요일 0시)
            LocalDate weekStartDate = localDate.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR))
                    .with(IsoFields.WEEK_BASED_YEAR, localDate.get(IsoFields.WEEK_BASED_YEAR))
                    .with(DayOfWeek.MONDAY); // 월요일로 설정
            LocalDateTime weekStart = weekStartDate.atStartOfDay(); // 0시로 설정

            // 주의 종료일을 일요일의 23시 59분으로 계산
            LocalDate weekEndDate = weekStartDate.plusDays(6); // 일요일
            LocalDateTime weekEnd = weekEndDate.atTime(23, 59, 59); // 23시 59분으로 설정

            YearMonth yearMonth = YearMonth.from(localDate); // 몇 월인지
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekOfMonth = localDate.get(weekFields.weekOfMonth()); // 몇 번째 주인지

            // "몇 월 몇 번째 주" 문자열 생성
            //String monthAndWeek = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + weekOfMonth + "주";
            YearWeek yearWeek = new YearWeek(yearMonth, weekOfMonth);

            List<Orders> weeklyOrders = ordersRepository.findOrdersByWeek(storeId, weekStart, weekEnd);
            Integer sales = weeklyOrders.stream().mapToInt(Orders::getTotalPrice).sum();

            if(date.isEqual(now)) {
                //Orders의 상품들 모두 가져오기


                for(Orders orders : weeklyOrders) {
                    //상품 이름들 가져와서 map에다 집어넣기.
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orders);

                    //상품들 돌면서 몇개인지
                    for (OrderProduct orderProduct : orderProducts) {
                        totalWeeklyOrders += orderProduct.getCount();
                        ReadProductDTO readProductDTO = productClient.getProductById(orderProduct.getProductId()).getData();

                        if(wmap.containsKey(readProductDTO.getName())) {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = wmap.get(readProductDTO.getName());

                            getSalesListByTimeTypeProductDTO.setQuantity(getSalesListByTimeTypeProductDTO.getQuantity() + orderProduct.getCount());

                            wmap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }else {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = GetSalesListByTimeTypeProductDTO.builder().quantity(orderProduct.getCount()).build();

                            wmap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }
                    }
                }


                //맵을 돌면서 퍼센트 계산
                for(Map.Entry<String, GetSalesListByTimeTypeProductDTO> entry : wmap.entrySet()) {
                    entry.getValue().setPercent((double)entry.getValue().getQuantity() /totalWeeklyOrders * 100);
                }



            }




            weekMap.put(yearWeek, sales);
        }

        //월별 매출
        for(LocalDateTime date= fiveMonthsAgo; !date.isAfter(now); date= date.plusMonths(1)) {
            List<Orders> monthlyOrders = ordersRepository.findOrdersByYearAndMonth(storeId, date);
            Integer sales = monthlyOrders.stream().mapToInt(Orders::getTotalPrice).sum();

            if(date.isEqual(now)) {
                for (Orders orders : monthlyOrders) {
                    //상품 이름들 가져와서 map에다 집어넣기.
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orders);

                    for (OrderProduct orderProduct : orderProducts) {
                        totalMonthlyOrders += orderProduct.getCount();
                        ReadProductDTO readProductDTO = productClient.getProductById(orderProduct.getProductId()).getData();

                        if(mmap.containsKey(readProductDTO.getName())) {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = mmap.get(readProductDTO.getName());

                            getSalesListByTimeTypeProductDTO.setQuantity(getSalesListByTimeTypeProductDTO.getQuantity() + orderProduct.getCount());

                            mmap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }else {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = GetSalesListByTimeTypeProductDTO.builder().quantity(orderProduct.getCount()).build();

                            mmap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }
                    }
                }

                //맵을 돌면서 퍼센트 계산
                for(Map.Entry<String, GetSalesListByTimeTypeProductDTO> entry : mmap.entrySet()) {
                    entry.getValue().setPercent((double)entry.getValue().getQuantity() /totalMonthlyOrders * 100);
                }

            }

            monthMap.put(YearMonth.from(date), sales);
        }

        //연별 매출
        for(LocalDateTime date= twoYearsAgo; !date.isAfter(now); date= date.plusYears(1)) {
            List<Orders> yearlyOrders = ordersRepository.findOrdersByYear(storeId, date);
            Integer sales = yearlyOrders.stream().mapToInt(Orders::getTotalPrice).sum();

            if(date.isEqual(now)) {
                for(Orders orders : yearlyOrders) {
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrdersId(orders);

                    //상품들 돌면서 몇개인지
                    for (OrderProduct orderProduct : orderProducts) {
                        totalYearlyOrders += orderProduct.getCount();
                        ReadProductDTO readProductDTO = productClient.getProductById(orderProduct.getProductId()).getData();

                        if(ymap.containsKey(readProductDTO.getName())) {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO = ymap.get(readProductDTO.getName());

                            getSalesListByTimeTypeProductDTO.setQuantity(getSalesListByTimeTypeProductDTO.getQuantity() + orderProduct.getCount());

                            ymap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }else {
                            GetSalesListByTimeTypeProductDTO getSalesListByTimeTypeProductDTO
                                    = GetSalesListByTimeTypeProductDTO.builder().quantity(orderProduct.getCount()).build();

                            ymap.put(readProductDTO.getName(), getSalesListByTimeTypeProductDTO);
                        }
                    }
                }

                //맵을 돌면서 퍼센트 계산
                for(Map.Entry<String, GetSalesListByTimeTypeProductDTO> entry : ymap.entrySet()) {
                    entry.getValue().setPercent((double)entry.getValue().getQuantity() /totalYearlyOrders * 100);
                }
            }
            yearMap.put(Year.from(date), sales);
        }


        GetSalesListByTimeTypeDTO getSalesListByTimeTypeDTO = GetSalesListByTimeTypeDTO.builder()
                .daily(dailyMap)
                .dailyProduct(map)
                .dailyTotal(totalDailyOrders)
                .weekly(weekMap)
                .weeklyProduct(wmap)
                .weeklyTotal(totalWeeklyOrders)
                .monthly(monthMap)
                .monthlyProduct(mmap)
                .monthlyTotal(totalMonthlyOrders)
                .yearly(yearMap)
                .yearlyProduct(ymap)
                .yearlyTotal(totalYearlyOrders)
                .build();

        return getSalesListByTimeTypeDTO;

    }

    public LocalDate getFormattedDate(LocalDateTime localDateTime) {
        // 날짜를 'yyyy-M-d' 형식으로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(localDateTime.format(formatter), formatter);
    }

    @Transactional
    public List<GetSalesTopPlace> getSalesTopPlace(String token) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        Integer storeId = storeClient.getStoreIdByUserId(ownerId);

        //주문내역에서 해당 storeId랑 동일한 데이터 중, simple_location 아이디 1~3위 찾기
        List<Integer> li = ordersRepository.findByStoreId(storeId).stream().map(Orders::getStoreSimpleLocationId).toList();

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer num : li) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // 빈도 순으로 정렬된 리스트 생성 (내림차순)
        List<Map.Entry<Integer, Integer>> sortedEntries = frequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // 빈도 수 기준 내림차순
                .limit(3) // 상위 3개만 가져옴
                .toList();

        List<GetSalesTopPlace> getSalesTopPlaceList = new ArrayList<>();

        Integer rank = 1;
        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey()+ " " + entry.getValue());
            //map은 (간편위치, 나온 횟수)
            StoreSimpleLocationDTO storeSimpleLocation = storeClient.getStoreSimpleLocationInfo(entry.getKey());

            GetSalesTopPlace getSalesTopPlace = GetSalesTopPlace.builder()
                    .id(entry.getKey())
                    .address(storeSimpleLocation.address())
                    .longitude(storeSimpleLocation.longitude())
                    .latitude(storeSimpleLocation.latitude())
                    .nickname(storeSimpleLocation.nickname())
                    .orderCount(entry.getValue())
                    .rank(rank)
                    .build();

            rank++;
            getSalesTopPlaceList.add(getSalesTopPlace);
        }

        return getSalesTopPlaceList;
    }
}
