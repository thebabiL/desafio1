package Desafio1.exceptions;

public class ExclusaoException extends DAOException
{
  public ExclusaoException(String msg, Throwable causa) 
  {
    super(msg, causa);
  }
}
