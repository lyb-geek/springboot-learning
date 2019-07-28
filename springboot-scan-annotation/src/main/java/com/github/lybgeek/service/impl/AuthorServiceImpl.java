package com.github.lybgeek.service.impl;

import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.annotaiton.BindLog;
import com.github.lybgeek.annotaiton.BingLogService;
import com.github.lybgeek.dao.AuthorRepository;
import com.github.lybgeek.dto.AuthorDTO;
import com.github.lybgeek.model.Author;
import com.github.lybgeek.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@BingLogService
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Mapper dozerMapper;


    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public Author saveAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }

    @Override
    public Author updateAuthor(AuthorDTO authorDTO) {
        Author author = dozerMapper.map(authorDTO,Author.class);
        authorRepository.save(author);
        return getAuthordById(author.getId());
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public boolean deleteAuthorById(Long id) {

        authorRepository.deleteById(id);
        return !authorRepository.existsById(id);
    }

    @Override
    public Author getAuthordById(Long id) {
        return authorRepository.getOne(id);
    }

    @Override
    @BindLog(value = false)
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

}
