package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatergoryRepository extends JpaRepository<Category, Long> {

    Category findCatByName(String category);
}
