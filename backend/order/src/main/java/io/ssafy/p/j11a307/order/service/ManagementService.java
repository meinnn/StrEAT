package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.*;
import io.ssafy.p.j11a307.order.entity.DistrictSales;
import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.TimeUtil;
import io.ssafy.p.j11a307.order.global.YearWeek;
import io.ssafy.p.j11a307.order.repository.DistrictSalesRepository;
import io.ssafy.p.j11a307.order.repository.OrderProductRepository;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.IsoFields;
import java.time.format.DateTimeFormatter;
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
    private final OpenAPIClient openAPIClient;
    private final DistrictSalesRepository districtSalesRepository;
    private final KakaoClient kakaoClient;

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
            GetStoreSimpleLocationDTO storeSimpleLocation = storeClient.getStoreSimpleLocationInfo(entry.getKey());

            GetSalesTopPlace getSalesTopPlace = GetSalesTopPlace.builder()
                    .id(entry.getKey())
                    .src(storeSimpleLocation.src())
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

    @Transactional
    public GetSurroundingBusinessDistrictDTO getSurroundingBusinessDistrict(String token, Double latitude, Double longitude) {
        Integer ownerId = ownerClient.getOwnerId(token, internalRequestKey);
        Integer storeId = storeClient.getStoreIdByUserId(ownerId);

        ReadStoreCategoryDTO readStoreCategoryDTO = storeClient.getStoreCategoryByStoreId(storeId);
        String openAPIServiceKey = "QIwhnv5m5KXaXU9cFxE4rud/rEU0h8D2qAJnQ0r82sZkmTSJnI7p6GGvNbNgIA/YxSVqiYLBckspxPRElQ7ltQ==";

        String bCategory = "I2";
        String mCategory = readStoreCategoryDTO.topCode();
        String sCategory = readStoreCategoryDTO.subCode();
        String numOfRows = Integer.toString(1000);
        String pageNo = Integer.toString(0);
        String radius = Integer.toString(2000);
        String cx = Double.toString(longitude);
        String cy = Double.toString(latitude);

        ResponseEntity<ResponseData> response = openAPIClient.storeListInRadius(openAPIServiceKey, "json", numOfRows, pageNo, bCategory, mCategory, sCategory, radius, cx, cy);

        //System.out.println(li.getBody().getBody();

        List<SameStoreDTO> sameStoreDTOS = new ArrayList<>();

        for (StoreListInRadiusListDTO storeListInRadiusListDTO: response.getBody().getItems()) {
            SameStoreDTO sameStoreDTO = SameStoreDTO.builder()
                    .storeLat(Double.parseDouble(storeListInRadiusListDTO.lat()))
                    .storeLon(Double.parseDouble(storeListInRadiusListDTO.lon()))
                    .storeName(storeListInRadiusListDTO.bizesNm())
                    .storeType(storeListInRadiusListDTO.ksicNm())
                    .build();
            sameStoreDTOS.add(sameStoreDTO);
        }

        //해당 위치에서 가장 가까운 상권을 찾는다.
        //그러나 이때 8만개를 전부 돌면 안된다..!! -> 시간 오래 걸림
        //따라서 grid 방식을 써서 격자 안에 있는 부분만 도는 것.
        List<DistrictSales> districtSalesList = districtSalesRepository.findAll();

//        int count = 1;
//        for(DistrictSales districtSales: districtSalesList) {
//            System.out.println("count: " + count);
//            //카카오 api
//            ResponseEntity<ResponseData2> searchResult = kakaoClient.searchLocation(districtSales.getDistrictName(), "KakaoAK 22c4c321f3adbd17a9642917af948274");
//
//            if(searchResult.getBody().documents().size() != 0) {
//                Optional<DistrictSales> districtSales1 = districtSalesRepository.findById(count);
//
//                if(districtSales1.isPresent()) {
//                    districtSales1.get().setLatitude(Double.parseDouble(searchResult.getBody().documents().get(0).y()));
//                    districtSales1.get().setLongitude(Double.parseDouble(searchResult.getBody().documents().get(0).x()));
//                }
//
//                districtSalesRepository.save(districtSales1.get());
//
//                //System.out.println(searchResult.getBody().documents().get(0).x() + " " + searchResult.getBody().documents().get(0).y());
//            }
//            count++;
//        }


        //가장 가까운 상권을 찾아서 가져온 상황.
        //DistrictSales districtSales = districtSalesRepository.findByDistrictName("사가정역");

        DistrictSales districtSales = districtSalesRepository.findByIdAndDistrictName(1001, "역삼역 4번");

        //해당 상권
        // 구분코드명 : 발달상권, 골목상권, 전통시장, 관광특구
        // 코드명 : 사가정역
        // 요일별 건수: 어느 요일에 인기인지. 인기 없는지. 평균보다 몇 퍼센트 높거나 낮은지.

        int[] salesCounts = {
                districtSales.getMondaySalesCount(), districtSales.getTuesdaySalesCount(), districtSales.getWednesdaySalesCount(),
                districtSales.getThursdaySalesCount(), districtSales.getFridaySalesCount(), districtSales.getSaturdaySalesCount(), districtSales.getSundaySalesCount()
        };

        String[] daysOfWeek = {
                "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"
        };


        // 최대값, 최소값, 총합 구하기
        int maxSales = Integer.MIN_VALUE;
        int minSales = Integer.MAX_VALUE;
        String maxSalesDay = "";
        String minSalesDay = "";
        int totalSales = 0;

        for (int i = 0; i < salesCounts.length; i++) {
            if (salesCounts[i] > maxSales) {
                maxSales = salesCounts[i];
                maxSalesDay = daysOfWeek[i];
            }
            if (salesCounts[i] < minSales) {
                minSales = salesCounts[i];
                minSalesDay = daysOfWeek[i];
            }
            totalSales += salesCounts[i];
        }

        // 평균 구하기
        double averageSales = totalSales / (double) salesCounts.length;

        double maxSalesPercentageDiff = ((maxSales - averageSales) / averageSales) * 100;
        double minSalesPercentageDiff = ((minSales - averageSales) / averageSales) * 100;

        GroupDTO dayGroupDTO = GroupDTO.builder()
                .tag("요일")
                .average(averageSales)
                .bestGroup(maxSalesDay)
                .bestDegree(maxSalesPercentageDiff)
                .worstDegree(minSalesPercentageDiff)
                .worstGroup(minSalesDay)
                .build();


        // 성별별 건수: 어떤 성별에게 인기인지. 인기 없는지. 평균보다 몇 퍼센트 높거나 낮은지.

        salesCounts = new int[]{
                districtSales.getMaleSalesCount(), districtSales.getFemaleSalesCount()
        };

        daysOfWeek = new String[]{
                "남자", "여자"
        };

        // 최대값, 최소값, 총합 구하기
        maxSales = Integer.MIN_VALUE;
        minSales = Integer.MAX_VALUE;
        maxSalesDay = "";
        minSalesDay = "";
        totalSales = 0;

        for (int i = 0; i < salesCounts.length; i++) {
            if (salesCounts[i] > maxSales) {
                maxSales = salesCounts[i];
                maxSalesDay = daysOfWeek[i];
            }
            if (salesCounts[i] < minSales) {
                minSales = salesCounts[i];
                minSalesDay = daysOfWeek[i];
            }
            totalSales += salesCounts[i];
        }

        // 평균 구하기
        averageSales = totalSales / (double) salesCounts.length;

        maxSalesPercentageDiff = ((maxSales - averageSales) / averageSales) * 100;
        minSalesPercentageDiff = ((minSales - averageSales) / averageSales) * 100;

        GroupDTO genderGroupDTO = GroupDTO.builder()
                .tag("성별")
                .average(averageSales)
                .bestGroup(maxSalesDay)
                .bestDegree(maxSalesPercentageDiff)
                .worstDegree(minSalesPercentageDiff)
                .worstGroup(minSalesDay)
                .build();

        // 연령별 건수: 어떤 연령대한테 인기인지. 인기 없는지. 평균보다 몇 퍼센트 높거나 낮은지.
        salesCounts = new int[]{
                districtSales.getAgeGroup10SalesCount(),districtSales.getAgeGroup20SalesCount(), districtSales.getAgeGroup30SalesCount(),
                districtSales.getAgeGroup40SalesCount(), districtSales.getAgeGroup50SalesCount(), districtSales.getAgeGroup60PlusSalesCount()
        };

        daysOfWeek = new String[]{
                "10대", "20대", "30대", "40대", "50대", "60대 이상"
        };

        // 최대값, 최소값, 총합 구하기
        maxSales = Integer.MIN_VALUE;
        minSales = Integer.MAX_VALUE;
        maxSalesDay = "";
        minSalesDay = "";
        totalSales = 0;

        for (int i = 0; i < salesCounts.length; i++) {
            if (salesCounts[i] > maxSales) {
                maxSales = salesCounts[i];
                maxSalesDay = daysOfWeek[i];
            }
            if (salesCounts[i] < minSales) {
                minSales = salesCounts[i];
                minSalesDay = daysOfWeek[i];
            }
            totalSales += salesCounts[i];
        }

        // 평균 구하기
        averageSales = totalSales / (double) salesCounts.length;

        maxSalesPercentageDiff = ((maxSales - averageSales) / averageSales) * 100;
        minSalesPercentageDiff = ((minSales - averageSales) / averageSales) * 100;

        GroupDTO ageGroupDTO = GroupDTO.builder()
                .tag("연령대")
                .average(averageSales)
                .bestGroup(maxSalesDay)
                .bestDegree(maxSalesPercentageDiff)
                .worstDegree(minSalesPercentageDiff)
                .worstGroup(minSalesDay)
                .build();

        // 시간대별 건수: 어느 시간대에 인기인지. 인기 없는지.
        salesCounts = new int[]{
            districtSales.getTimeSlot00To06SalesCount(), districtSales.getTimeSlot06To11SalesCount(),
            districtSales.getTimeSlot11To14SalesCount(), districtSales.getTimeSlot14To17SalesCount(),
            districtSales.getTimeSlot17To21SalesCount(), districtSales.getTimeSlot21To24SalesCount()
        };

        daysOfWeek = new String[]{
                "0시~6시", "6시~11시", "11시~14시", "14시~17시", "17시~21시", "21시~24시"
        };

        // 최대값, 최소값, 총합 구하기
        maxSales = Integer.MIN_VALUE;
        minSales = Integer.MAX_VALUE;
        maxSalesDay = "";
        minSalesDay = "";
        totalSales = 0;

        for (int i = 0; i < salesCounts.length; i++) {
            if (salesCounts[i] > maxSales) {
                maxSales = salesCounts[i];
                maxSalesDay = daysOfWeek[i];
            }
            if (salesCounts[i] < minSales) {
                minSales = salesCounts[i];
                minSalesDay = daysOfWeek[i];
            }
            totalSales += salesCounts[i];
        }

        // 평균 구하기
        averageSales = totalSales / (double) salesCounts.length;

        maxSalesPercentageDiff = ((maxSales - averageSales) / averageSales) * 100;
        minSalesPercentageDiff = ((minSales - averageSales) / averageSales) * 100;

        GroupDTO timeGroupDTO = GroupDTO.builder()
                .tag("시간대")
                .average(averageSales)
                .bestGroup(maxSalesDay)
                .bestDegree(maxSalesPercentageDiff)
                .worstDegree(minSalesPercentageDiff)
                .worstGroup(minSalesDay)
                .build();



        GetSurroundingBusinessDistrictDTO getSurroundingBusinessDistrictDTO = GetSurroundingBusinessDistrictDTO.builder()
                .districtName(districtSales.getDistrictName())
                .businessDistrictName(districtSales.getBusinessDistrictName())
                .dayGroup(dayGroupDTO)
                .genderGroup(genderGroupDTO)
                .ageGroup(ageGroupDTO)
                .timeGroup(timeGroupDTO)
                .sameStoreNum(response.getBody().getItems().size())
                .sameStoreList(sameStoreDTOS)
                .build();

        return getSurroundingBusinessDistrictDTO;
    }
}
