package org.lt.basic.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lt.basic.model.User;
import org.lt.basic.test.util.AbstractDbUnitTestCase;
import org.lt.basic.test.util.EntitiesHelper;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
	@Inject
	private UserDao userDao;
	@Inject
    private SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException{
		Session session=sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
//		this.backupAllTable();
		
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
		SessionHolder holder= (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session session=holder.getSession();
        session.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
//		this.  resumeTable();
	}
	@Test
	public void testLoad() throws DatabaseUnitException, SQLException {
		IDataSet dSet=createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dSet);
		User user=userDao.load(1);
		EntitiesHelper.assertUser(user);
	}
//	@Test
//	public void testAdd() {
//		User user =new User(1,"admin");
//		userDao.add(user);
//		
//	}
	
}
