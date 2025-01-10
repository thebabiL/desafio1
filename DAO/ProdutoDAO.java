package Desafio1.DAO;

import java.util.List;
import Desafio1.entities.Produto;

public interface ProdutoDAO
{
  void inserir (Produto obj);
  void atualizar (Produto obj);
  void deletarPorId (int id);
  Produto buscarPorId (int id);
  List<Produto> buscarTudo();
}