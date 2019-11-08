package com.example.dbms.service.impl;



import com.example.dbms.dao.UsersMapper;
import com.example.dbms.domain.po.Users;
import com.example.dbms.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UsersService")
public class UsersServiceImpl implements UsersService {
@Autowired
private UsersMapper usersMapper;

@Override
public int insert(Users users)
{
        return  usersMapper.insert(users);
}
@Override
    public  Users findByUsername(String username)
{
    return usersMapper.findByUsername(username);
}
@Override
    public Users findById(int id)
{
    return usersMapper.findById(id);
}
}
