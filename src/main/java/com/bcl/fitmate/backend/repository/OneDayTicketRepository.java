package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.OneDayTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayTicketRepository extends JpaRepository<OneDayTicket, Long> {
    List<OneDayTicket> findByMemberId(Long id);
    List<OneDayTicket> findByTrainerId(Long id);
}
