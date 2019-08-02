package com.github.lybgeek.service;

import com.github.lybgeek.dto.AuthorDTO;
import com.github.lybgeek.model.Author;

import java.util.List;

public interface AuthorService {

    Author saveAuthor(Author author);

    Author updateAuthor(AuthorDTO authorDTO);

    boolean deleteAuthorById(Long id);

    Author getAuthordById(Long id);

    List<Author> listAuthors();
}
