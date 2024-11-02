package com.github.lybgeek.togglz.env.factory.support;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileMode;
import com.github.lybgeek.togglz.env.factory.StateRepositoryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;

import java.io.File;
import java.io.IOException;

import static com.github.lybgeek.togglz.env.constant.TogglzConstant.STORE_TYPE_FILE;
import static com.github.lybgeek.togglz.env.constant.TogglzConstant.STORE_TYPE_INMEMORY;

@Slf4j
@RequiredArgsConstructor
public class FileStateRepositoryFactory implements StateRepositoryFactory {

    private final String fileStoreLocation;
    @Override
    public StateRepository create() {
        File file = new File(fileStoreLocation);
        if(!FileUtil.exist(file)){
            file = FileUtil.touch(fileStoreLocation);
        }
        return new FileBasedStateRepository(file);
    }

    @Override
    public String supportType() {
        return STORE_TYPE_FILE;
    }


}
