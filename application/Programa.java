package application;

import java.sql.Connection;
import java.util.Scanner;
import DAO.ProdutoJDBC;
import DAO.CarrinhoJDBC;
import db.DB;
import entities.Carrinho;
import entities.Produto;
import exceptions.CarrinhoException;
import exceptions.ProdutoException;

public class Programa 
{
    public static void main(String[] args) 
    {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try 
        {
            conn = DB.getConexao();
            ProdutoJDBC produtoDao = new ProdutoJDBC(conn);
            CarrinhoJDBC carrinhoDao = new CarrinhoJDBC(conn);

            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("---------                       Bem-vindo à Loja                              ---------");
            System.out.println("Modo:");
            System.out.println("1 - Cliente");
            System.out.println("2 - Vendedor");
            
            String tipoUsuario = scanner.nextLine().trim().toLowerCase();

            System.out.println("---------------------------------------------------------------------------------------");

            if (tipoUsuario.equals("1")) 
            {
                System.out.println("Insira o nome do cliente:");
                String nomeCliente = scanner.nextLine();
                Carrinho meuCarrinho = new Carrinho(nomeCliente);
                
                
                int opcao;
                do 
                {
                    System.out.println("\n---------------------------------------------------------------------------------------");

                    System.out.println("\n----------                        Menu Cliente                               ----------");
                    System.out.println("1. Listar produtos do estoque");
                    System.out.println("2. Adicionar produto ao carrinho");
                    System.out.println("3. Listar produtos do carrinho");
                    System.out.println("4. Atualizar quantidade de produto no carrinho");
                    System.out.println("5. Remover produto do carrinho");
                    System.out.println("6. Finalizar compra");
                    System.out.println("0. Sair");
                    System.out.print("Escolha uma opção: ");
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) 
                    {
                        case 1:
                        {
                            System.out.println("Produtos disponíveis no estoque:");
                            produtoDao.listarProdutos().forEach(produto -> 
                            {
                                System.out.printf("ID: %d | Nome: %s | Descrição: %s | Categoria: %s | Valor: R$%.2f | Estoque: %d%n",
                                    produto.getID(), produto.getProduto(), produto.getDescricao(), produto.getCategoria(),
                                    produto.getValor(), produto.getQuant());
                            });
                            break;
                        }
                        case 2:
                        {
                            System.out.print("Digite o ID do produto que deseja adicionar ao carrinho: ");
                            int idProduto = scanner.nextInt();
                            System.out.print("Quantidade desejada: ");
                            int quant = scanner.nextInt();
                            scanner.nextLine();

                            try 
                            {
                                Produto produto = produtoDao.buscarProdutoPorID(idProduto);
                                meuCarrinho.inserirProduto(produto, quant);
                                System.out.println("Produto adicionado ao carrinho com sucesso!");
                            } 
                            catch (ProdutoException | CarrinhoException e) 
                            {
                                System.out.println("Erro: " + e.getMessage());
                            }
                            break;
                        }
                        case 3:
                        {
                            System.out.println("Produtos no carrinho:");
                            System.out.println(meuCarrinho.listarProdutos());
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Digite o ID do produto que deseja atualizar no carrinho: ");
                            int idProduto = scanner.nextInt();
                            System.out.print("Nova quantidade: ");
                            int novaQuant = scanner.nextInt();
                            scanner.nextLine();
                            try 
                            {
                                Produto produto = produtoDao.buscarProdutoPorID(idProduto);
                                double novoSubtotal = produto.getValor() * novaQuant;
                                carrinhoDao.atualizarProduto(meuCarrinho.getID(), produto, novaQuant, novoSubtotal);
                                System.out.println("Produto atualizado no carrinho com sucesso!");
                            } 
                            catch (ProdutoException | CarrinhoException e) 
                            {
                                System.out.println("Erro: " + e.getMessage());
                            }
                            break;
                        }
                        case 5:
                        {
                            System.out.print("Digite o ID do produto que deseja remover do carrinho: ");
                            int idProduto = scanner.nextInt();
                            scanner.nextLine();

                            try 
                            {
                                carrinhoDao.removerProduto(meuCarrinho.getID(), idProduto);
                                System.out.println("Produto removido do carrinho com sucesso!");
                            } 
                            catch (CarrinhoException e) 
                            {
                                System.out.println("Erro: " + e.getMessage());
                            }
                            break;
                        }
                        case 6:
                        {
                            System.out.println("Finalizando compra...");
                            meuCarrinho.encerrarCompra(produtoDao);
                            System.out.printf("Compra finalizada com sucesso! Valor total: R$%.2f%n", meuCarrinho.getValorTotal());
                            break;
                        }
                        case 0:
                        {
                            System.out.println("Saindo do sistema...");
                            System.out.println("---------------------------------------------------------------------------------------");

                            break;
                        }
                        default:
                        {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                } while (opcao != 0);
            } 
            else if (tipoUsuario.equals("2")) 
            {
                int opcao;
                do 
                {
                    System.out.println("\n---------------------------------------------------------------------------------------");

                    System.out.println("\n----------                        Menu Vendedor                              ----------");
                    System.out.println("1. Adicionar produto ao estoque");
                    System.out.println("2. Listar produtos do estoque");
                    System.out.println("3. Atualizar produto no estoque");
                    System.out.println("4. Remover produto do estoque");
                    System.out.println("0. Sair");
                    System.out.print("Escolha uma opção: ");
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (opcao) 
                    {
                        case 1:
                        {
                            System.out.print("Nome do produto: ");
                            String nomeProduto = scanner.nextLine();
                            System.out.print("Descrição do produto: ");
                            String descricao = scanner.nextLine();
                            System.out.print("Categoria do produto: ");
                            String categoria = scanner.nextLine();
                            System.out.print("Valor do produto: ");
                            double valor = scanner.nextDouble();
                            System.out.print("Quantidade em estoque: ");
                            int quant = scanner.nextInt();
                            scanner.nextLine();

                            Produto novoProduto = new Produto(nomeProduto, descricao, categoria, valor, quant);
                            produtoDao.inserirProduto(novoProduto);
                            System.out.println("Produto adicionado ao estoque com sucesso!");
                            break;
                        }
                        case 2:
                        {
                            System.out.println("Produtos disponíveis no estoque:");
                            produtoDao.listarProdutos().forEach(produto -> 
                            {
                                System.out.printf("ID: %d | Nome: %s | Descrição: %s | Categoria: %s | Valor: R$%.2f | Estoque: %d%n",
                                    produto.getID(), produto.getProduto(), produto.getDescricao(), produto.getCategoria(),
                                    produto.getValor(), produto.getQuant());
                            });
                            break;
                        }
                        case 3:
                        {
                            System.out.print("Digite o ID do produto que deseja atualizar no estoque: ");
                            int idProduto = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Nome do produto: ");
                            String nomeProduto = scanner.nextLine();
                            System.out.print("Descrição do produto: ");
                            String descricao = scanner.nextLine();
                            System.out.print("Categoria do produto: ");
                            String categoria = scanner.nextLine();
                            System.out.print("Valor do produto: ");
                            double valor = scanner.nextDouble();
                            System.out.print("Quantidade em estoque: ");
                            int quant = scanner.nextInt();
                            scanner.nextLine();

                            Produto produtoAtualizado = new Produto(idProduto, nomeProduto, descricao, categoria, valor, quant);
                            produtoDao.atualizarProduto(produtoAtualizado);
                            System.out.println("Produto atualizado no estoque com sucesso!");
                            break;
                        }
                        case 4:
                        {
                            System.out.print("Digite o ID do produto que deseja remover do estoque: ");
                            int idProduto = scanner.nextInt();
                            scanner.nextLine();
                            produtoDao.removerProduto(idProduto);
                            System.out.println("Produto removido do estoque com sucesso!");
                            break;
                        }
                        case 0:
                        {
                            System.out.println("Saindo do sistema...");
                            System.out.println("\n---------------------------------------------------------------------------------------");
                            break;
                        }
                        default:
                        {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                } while (opcao != 0);
            } 
            else 
            {
                System.out.println("Tipo de usuário inválido. Encerrando o sistema.");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Erro ao conectar com o banco de dados ou executar operações: " + e.getMessage());
        } 
        finally 
        {
            if (conn != null) 
            {
                DB.fecharConexao();
            }
            scanner.close();
        }
    }
}
