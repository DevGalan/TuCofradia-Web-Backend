package com.devgalan.tucofradia.data;

import org.springframework.core.io.Resource;

public interface FileReader {
    Resource read(String path);
}
