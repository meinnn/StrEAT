package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.CreateReviewDTO;
import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.entity.OrdersId;
import io.ssafy.p.j11a307.order.entity.Review;
import io.ssafy.p.j11a307.order.exception.BusinessException;
import io.ssafy.p.j11a307.order.exception.ErrorCode;
import io.ssafy.p.j11a307.order.repository.OrdersRepository;
import io.ssafy.p.j11a307.order.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrdersRepository ordersRepository;

    private final OwnerClient ownerClient;

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    public void createReview(Integer id, CreateReviewDTO createReviewDTO, String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        Integer score = createReviewDTO.score();
        String content = createReviewDTO.content();

        //1. 주문 id가 유효하지 않다면?
        Orders orders =ordersRepository.findById(id).orElse(null);

        if(orders==null) {
           throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }

        //2. 해당 주문을 한 유저가 아니라면?
        if(orders.getUserId() != userId) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        Review review = Review.builder()
                .id(new OrdersId(orders))
                .score(score)
                .content(content).build();

        reviewRepository.save(review);
    }
}
