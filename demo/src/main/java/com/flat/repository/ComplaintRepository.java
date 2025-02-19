package com.flat.repository;

import com.flat.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaints, Long> {
}
