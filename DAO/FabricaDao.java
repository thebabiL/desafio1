package Desafio1.DAO;

import Desafio1.db.DB;

public class FabricaDao 
{
  public FabricaDao() 
  {
  
  }

  public static ProdutoDAO criarProdutoDao() 
  {
    return new ProdutoDaoJDBC(DB.getConnection());
  }

  public static CarrinhoDAO createCarrinhoDao() 
  {
    return new CarrinhoDaoJDBC(DB.getConnection());
  }
}
