package org.lt.basic.dao;

import org.lt.basic.model.User;
import org.springframework.stereotype.Repository;

@Repository()
public class UserDao extends BaseDao<User> implements IBaseDao<User> {

}
