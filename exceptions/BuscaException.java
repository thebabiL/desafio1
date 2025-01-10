package Desafio1.exceptions;

public class BuscaException extends DAOException
{
  public BuscaException(String msg, Throwable causa) 
  {
    super(msg, causa);
  }
}
