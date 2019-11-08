package com.example.dbms.controller;

import com.example.dbms.api.ErrorCode;
import com.example.dbms.api.ResponseMessage;
import com.example.dbms.dao.InvitationMapper;
import com.example.dbms.domain.po.Users;
import com.example.dbms.service.UsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {
        private Logger log = LoggerFactory.getLogger(this.getClass());
        @Autowired
        private UsersService usersService;
        @Autowired
        private InvitationMapper invitationMapper;

    @PostMapping("/auth/login")
        @ResponseBody
        public ResponseMessage login(String username, String password )
        {
            //password = MD5Utils.encrypt(username, password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                log.info("认证成功");
                //设置会话超时时间，单位ms
                subject.getSession().setTimeout(6000000);
                log.info("会话超时时间：{}",subject.getSession().getTimeout()) ;
                //System.out.println("认证成功");
                return new ResponseMessage(ErrorCode.SUCCESS,"认证成功");
            } catch (UnknownAccountException e) {
                return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
            } catch (IncorrectCredentialsException e) {
                return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
            } catch (LockedAccountException e) {
                return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
            } catch (AuthenticationException e) {
                return  new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,"认证失败！");
            }
        }

    @GetMapping ("/auth/logout")
    @ResponseBody
    public ResponseMessage logout(String username, String password )
    {
        try {
            Subject subject =SecurityUtils.getSubject();
            if (subject != null)
                subject.logout();
                System.out.println("注销成功");
                return new ResponseMessage(ErrorCode.SUCCESS,"注销成功");
        } catch (UnknownAccountException e) {
            return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
        } catch (LockedAccountException e) {
            return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR,e.getMessage());
        }
    }
    @PostMapping ("/auth/register")
    @ResponseBody
    public ResponseMessage register(@RequestBody Users users,@RequestParam ("code") String code)
    {
            System.out.println(users.gethospital());
            System.out.println("code:"+code);
            if(invitationMapper.findByCode(code)==null)
            {
                return new ResponseMessage(ErrorCode.QUERY_DATA_ERROR, "邀请码错误！");
            }
            else
            {
                try {
                    log.info(users.getRealname());
                    log.info(users.gethospital());
                    usersService.insert(users);
                    return new ResponseMessage(ErrorCode.SUCCESS,"注册成功");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    return new ResponseMessage(ErrorCode.INSET_DATA_ERROR,"注册失败");
                }

            }

    }
}
