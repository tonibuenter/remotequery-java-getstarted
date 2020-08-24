import org.apache.commons.io.FileUtils;
import org.remotequery.RemoteQuery;
import org.remotequery.RemoteQuery.Request;
import org.remotequery.RemoteQuery.Result;
import org.remotequery.RemoteQueryUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.logging.Logger;

public class RemotequeryGetstarted {

  private static DataSource dataSource;

  public static void main(String[] args) throws Exception {

    Logger logger = Logger.getLogger("main");
    //
    String url = "jdbc:mysql://localhost:3306/remotequery_getstarted?serverTimezone=UTC";
    String user = "foo";
    String password = "bar";

    com.mysql.cj.jdbc.MysqlDataSource mysqlDS = new com.mysql.cj.jdbc.MysqlDataSource();

    mysqlDS.setURL(url);
    mysqlDS.setUser(user);
    mysqlDS.setPassword(password);
    mysqlDS.setDatabaseName("remotequery_getstarted");


    RemoteQuery.DataSources.register(mysqlDS);

    logger.info("Register default ...");
    RemoteQuery.ServiceRepositorySql serviceRepository = new RemoteQuery.ServiceRepositorySql(mysqlDS, "T_RQ_SERVICE");
    RemoteQuery.ServiceRepositoryHolder.set(serviceRepository);


    String[] sqlFiles = {"src/main/sql/bootstrap.sql", "src/main/sql/getstarted.sql"};
    String[] rqSqlFiles = {"src/main/sql/bootstrap.rq.sql", "src/main/sql/note.rq.sql"};

    // load sql files
    try (Connection con = mysqlDS.getConnection()) {
      for (String filename : sqlFiles) {
        String sqlText = FileUtils.readFileToString(new File(filename), "UTF-8");
        RemoteQueryUtils.processSqlText(con, sqlText, filename);
      }
    }
    // load rq sql files
    for (String filename : rqSqlFiles) {
      String rqSqlText = FileUtils.readFileToString(new File(filename), "UTF-8");
      RemoteQueryUtils.processRqSqlText(rqSqlText, "rqService.save", filename);
    }

    //

    addNote();
  }


  public static void addNote() {
    Request request = new Request();
    request.setServiceId("note.insert");
    request.put("noteTid", Math.floor(Math.random() * 1000000));
    request.put("info", "This is a test input " + new Date());
    request.addRole("WRITER");
    Result result = request.run();
  }
}
