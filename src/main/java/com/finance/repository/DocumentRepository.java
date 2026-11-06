package com.finance.repository;

import com.finance.entity.Document;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import java.time.*;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	List<Document> findByCustomerId(Long customerId);
}
