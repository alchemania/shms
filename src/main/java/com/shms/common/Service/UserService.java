package com.shms.common.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shms.common.entity.User;

import java.io.File;
import java.util.List;

public interface UserService extends IService<User> {
    public void sendSimpleMail(String to, String title, String content);
    public void sendAttachmentsMail(String to, String title, String cotent, List<File> fileList);
}
