package com.dev.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.inventory.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
