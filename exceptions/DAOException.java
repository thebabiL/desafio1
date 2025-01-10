package Desafio1.exceptions;

public class DAOException extends RuntimeException 
{
  public DAOException(String msg) 
  {
      super(msg);
  }

  public DAOException(String msg, Throwable cause) 
  {
      super(msg, cause);
  }
}