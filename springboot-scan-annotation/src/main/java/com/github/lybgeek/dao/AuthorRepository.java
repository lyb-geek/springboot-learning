package com.github.lybgeek.dao;

import com.github.lybgeek.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
