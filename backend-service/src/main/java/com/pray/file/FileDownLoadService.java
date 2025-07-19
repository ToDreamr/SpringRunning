package com.pray.file;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <p>
 * FileDownLoadService
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/28 13:11
 */
public interface FileDownLoadService {
    void download(HttpServletRequest request, String fileName,HttpServletResponse response) throws IOException;
}
