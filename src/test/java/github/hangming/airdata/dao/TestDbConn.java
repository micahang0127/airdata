package github.hangming.airdata.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import github.hangming.airdata.dto.Station;
/**
 org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
### The error may exist in file [C:\Users\SangA\Documents\workspace\airstation\target\classes\mybatis\mappers\StationMapper.xml]
### The error may involve StationMapper.findAll
### The error occurred while executing a query
### Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:77)
	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)
	at com.sun.proxy.$Proxy18.selectList(Unknown Source)
	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:222)
	at github.hangming.airdata.dao.PmDao.findAll(PmDao.java:19)
	at github.hangming.airdata.dao.TestDbConn.testConn(TestDbConn.java:25)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:252)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:678)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
### The error may exist in file [C:\Users\SangA\Documents\workspace\airstation\target\classes\mybatis\mappers\StationMapper.xml]
### The error may involve StationMapper.findAll
### The error occurred while executing a query
### Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
	at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:30)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:150)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:136)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)
	... 33 more
Caused by: org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
	at org.springframework.jdbc.datasource.DataSourceUtils.getConnection(DataSourceUtils.java:80)
	at org.mybatis.spring.transaction.SpringManagedTransaction.openConnection(SpringManagedTransaction.java:82)
	at org.mybatis.spring.transaction.SpringManagedTransaction.getConnection(SpringManagedTransaction.java:68)
	at org.apache.ibatis.executor.BaseExecutor.getConnection(BaseExecutor.java:336)
	at org.apache.ibatis.executor.SimpleExecutor.prepareStatement(SimpleExecutor.java:84)
	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:62)
	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:324)
	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)
	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)
	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:83)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)
	... 40 more
Caused by: java.sql.SQLException: Access denied for user 'airuser'@'localhost' (using password: YES)
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:964)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3973)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3909)
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:873)
	at com.mysql.jdbc.MysqlIO.proceedHandshakeWithPluggableAuthentication(MysqlIO.java:1710)
	at com.mysql.jdbc.MysqlIO.doHandshake(MysqlIO.java:1226)
	at com.mysql.jdbc.ConnectionImpl.coreConnect(ConnectionImpl.java:2191)
	at com.mysql.jdbc.ConnectionImpl.connectOneTryOnly(ConnectionImpl.java:2222)
	at com.mysql.jdbc.ConnectionImpl.createNewIO(ConnectionImpl.java:2017)
	at com.mysql.jdbc.ConnectionImpl.<init>(ConnectionImpl.java:779)
	at com.mysql.jdbc.JDBC4Connection.<init>(JDBC4Connection.java:47)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)
	at java.lang.reflect.Constructor.newInstance(Unknown Source)
	at com.mysql.jdbc.Util.handleNewInstance(Util.java:425)
	at com.mysql.jdbc.ConnectionImpl.getInstance(ConnectionImpl.java:389)
	at com.mysql.jdbc.NonRegisteringDriver.connect(NonRegisteringDriver.java:330)
	at org.springframework.jdbc.datasource.SimpleDriverDataSource.getConnectionFromDriver(SimpleDriverDataSource.java:140)
	at org.springframework.jdbc.datasource.AbstractDriverBasedDataSource.getConnectionFromDriver(AbstractDriverBasedDataSource.java:196)
	at org.springframework.jdbc.datasource.AbstractDriverBasedDataSource.getConnection(AbstractDriverBasedDataSource.java:159)
	at org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection(DataSourceUtils.java:111)
	at org.springframework.jdbc.datasource.DataSourceUtils.getConnection(DataSourceUtils.java:77)
	... 50 more


 * @author SangA
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestDbConn {

	@Autowired DataSource ds;
	@Autowired IPmDao pmDao ;
	@Test
	public void testConn() {
		List<Station> all = pmDao.findAll();
		assertEquals ( 359, all.size());
	}

}
