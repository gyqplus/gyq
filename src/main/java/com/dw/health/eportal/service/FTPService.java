package com.dw.health.eportal.service;

import com.jcraft.jsch.SftpException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gyq on 2019-4-15.
 */
public interface FTPService {

    public void login();

    public String sftpUpload(MultipartFile mFile, String pathType);
}
