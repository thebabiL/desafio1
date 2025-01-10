package Desafio1.exceptions;

public class AtualizacaoException extends DAOException
{
  public AtualizacaoException(String msg, Throwable causa) 
  {
    super(msg, causa);
  }
}
