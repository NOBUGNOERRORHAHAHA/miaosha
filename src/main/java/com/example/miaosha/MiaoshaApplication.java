package com.example.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan({"com.example.miaosha.db.dao"})
public class MiaoshaApplication {

//    @Autowired
//    private UserInfoDOMapper userInfoDOMapper;
//
//    @RequestMapping("/")
//    public String home() {
//       UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(1);
//       if (userInfoDO == null) {
//           return "用户对象不存在";
//       } else {
//           return userInfoDO.getName();
//       }
//
//    }

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}
