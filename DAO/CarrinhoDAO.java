package Desafio1.DAO;

import java.util.List;
import Desafio1.entities.Carrinho;

public interface CarrinhoDAO
{
  void inserir (Carrinho obj);
  void atualizar (Carrinho obj);
  void deletarPorId (int id);
  Carrinho buscarPorId (int id);
  List<Carrinho> buscarTudo();
}