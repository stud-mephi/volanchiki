package com.badminton.tournament.repository;

import com.badminton.tournament.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByType(String type);

    List<Category> findByGender(String gender);
}
