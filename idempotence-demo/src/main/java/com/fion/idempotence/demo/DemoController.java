package com.fion.idempotence.demo;

import com.fion.idempotence.core.annotation.Idempotence;
import com.fion.idempotence.core.repository.SingleSupportTokenRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class DemoController {

    /**
     * 日志打印对象
     */
    private final static Logger log = Logger.getLogger(DemoController.class.getName());

    @Autowired
    private SingleSupportTokenRepositoryAdapter adapter;

    @PostMapping("demo/save/{id}")
    @Idempotence
    public Object save(@PathVariable("id") Integer id) {
        String info = id + " 保存成功成功";
        log.info(info);
        return info;
    }

    @GetMapping("demo/token")
    public Object token(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        adapter.set(uuid, 20);
        Cookie cookie = new Cookie("token", uuid);
        response.addCookie(cookie);
        return "token:  " + uuid;
    }
}
