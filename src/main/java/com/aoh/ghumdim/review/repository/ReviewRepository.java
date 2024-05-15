package com.aoh.ghumdim.review.repository;

import com.aoh.ghumdim.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  @Query("SELECT r FROM Review r where r.destinations.id = :id")
  List<Review> findAllReviewWithDestinationId(@Param("id") Integer id);

}
