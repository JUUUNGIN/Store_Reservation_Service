package org.example.tablereservation.service;

import org.example.tablereservation.model.entity.ReviewEntity;
import org.example.tablereservation.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReservationService reservationService) {
        this.reservationService = reservationService;
        this.reviewRepository = reviewRepository;
    }

    //  매장 이용 후 리뷰 작성
    public String writeReview(String phoneNumber, String storeName, String content, int rating) {
//       방문 확인한 가게의 리뷰 작성 가능
        boolean visitConfirmed = reservationService.confirmVisitByPhoneNumberAndStoreName(phoneNumber, storeName);

        if (visitConfirmed) {
            ReviewEntity reviewEntity = new ReviewEntity();
            reviewEntity.setPhoneNumber(phoneNumber);
            reviewEntity.setStoreName(storeName);
            reviewEntity.setContent(content);
            reviewEntity.setRating(rating);

            reviewRepository.save(reviewEntity);
            return "success";
        } else {
            throw new IllegalStateException("방문 확인이 되야 리뷰작성이 가능합니다.");
        }
    }


    //    리뷰 수정(user만 가능: 핸드폰 번호와 매장 이름으로 확인, 아직 본인만 수정가능하게는 못했음)
    public String editReview(String phoneNumber, String storeName, String newContent, int newRating) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findByPhoneNumberAndStoreName(phoneNumber, storeName);

        if (optionalReviewEntity.isEmpty()) {
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");
        }

        ReviewEntity reviewEntity = optionalReviewEntity.get();
//      기존 리뷰는 사라지고 새 리뷰만 남음
        reviewEntity.setContent(newContent);
        reviewEntity.setRating(newRating);

        reviewRepository.save(reviewEntity);

        return "리뷰가 수정되었습니다";
    }

    //    리뷰 삭제(user, store 가능: 본인인증 및 매장 인증으로 본인, 본인가게만 삭제하게는 못했음)
    public String deleteReview(String phoneNumber, String storeName, boolean isUserRequest) {
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findByPhoneNumberAndStoreName(phoneNumber, storeName);

        if (optionalReviewEntity.isEmpty()) {
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");
        }

        ReviewEntity reviewEntity = optionalReviewEntity.get();

//        삭제권한 확인
        if (isUserRequest) { //삭제하는 경우
            if (!reviewEntity.getPhoneNumber().equals(phoneNumber)) {
                throw new IllegalStateException("삭제 권한이 없습니다.");
            }
        }

//        삭제하기
        reviewRepository.delete(reviewEntity);
        return "리뷰가 삭제되었습니다.";

    }

}
