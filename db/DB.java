package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  
  public class DB
  {
      private static final String url = "jdbc:mysql://localhost:3306/desafio1";
      private static final String usuario = "root";
      private static final String senha = "Bb050405.";
      private static Connection conn;
  
      public static Connection getConexao()
      {
          if (conn == null)
          {
              try
              {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  conn = DriverManager.getConnection(url, usuario, senha);
              } 
              catch (ClassNotFoundException e)
              {
                  throw new RuntimeException("Driver JDBC não encontrado.", e);
              } 
              catch (SQLException e)
              {
                  throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
              }
          }
          return conn;
      }
  
      public static void fecharConexao()
      {
          if (conn != null)
          {
              try
              {
                  conn.close();
                  conn = null;
              } 
              catch (SQLException e)
              {
                  throw new RuntimeException("Erro ao encerrar a conexão com o banco de dados: " + e.getMessage(), e);
              }
          }
      }
  
      public static boolean isConexaoAtiva()
      {
          try
          {
              return conn != null && !conn.isClosed();
          } 
          catch (SQLException e)
          {
              throw new RuntimeException("Erro ao verificar o estado da conexão: " + e.getMessage(), e);
          }
      }
  
      public static void reiniciarConexao()
      {
          fecharConexao();
          getConexao();
      }
  }  