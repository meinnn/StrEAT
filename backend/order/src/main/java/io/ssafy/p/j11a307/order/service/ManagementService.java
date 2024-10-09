package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.GetDailySalesDTO;
import io.ssafy.p.j11a307.order.dto.GetDailySalesListDTO;
import io.ssafy.p.j11a307.order.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.order.dto.UserInfoResponse;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final OwnerClient ownerClient;
    private final OrdersRepository ordersRepository;
    private final StoreClient storeClient;

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
                if(orders.getCreatedAt().getDayOfMonth() == i && orders.getStatus() != OrderCode.REJECTED) {
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
}
