package Desafio1.exceptions;

public class InsercaoException extends DAOException
{
  public InsercaoException(String msg, Throwable causa) 
  {
    super(msg, causa);
  }
}
