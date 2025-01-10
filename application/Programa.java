package Desafio1.application;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Desafio1.DAO.CarrinhoDAO;
import Desafio1.DAO.FabricaDao;
import Desafio1.DAO.ProdutoDAO;
import Desafio1.db.DB;
import Desafio1.entities.Produto;
import Desafio1.entities.Carrinho;

public class Programa {

    public static void main(String[] args) 
    {

      ProdutoDAO produtoDao = FabricaDao.criarProdutoDao();
      CarrinhoDAO carrinhoDao = FabricaDao.createCarrinhoDao();


        Scanner sc = new Scanner(System.in);
        int opcao;

        // Menu de entrada
        System.out.println("Escolha o tipo de usuário: ");
        System.out.println("1 - Cliente");
        System.out.println("2 - Administrador");
        opcao = sc.nextInt();

        if (opcao == 1) {
            // Acesso para cliente
            menuCliente();
        } else if (opcao == 2) {
            // Acesso para administrador
            menuAdministrador();
        } else {
            System.out.println("Opção inválida.");
        }

        sc.close();
    }

    // Menu de ações para o cliente
    private static void menuCliente() {
        Scanner sc = new Scanner(System.in);
        int opcaoCliente;

        System.out.println("Menu Cliente:");
        System.out.println("1 - Nova Compra");
        System.out.println("2 - Atualizar Carrinho");
        System.out.println("3 - Deletar Carrinho");
        System.out.println("Escolha uma opção:");
        opcaoCliente = sc.nextInt();

        switch (opcaoCliente) {
            case 1:
                criarCarrinho(); // Criar carrinho
                break;

            case 2:
                atualizarCarrinho(); // Atualizar carrinho
                break;

            case 3:
                deletarCarrinho(); // Deletar carrinho
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    // Menu de ações para o administrador
    private static void menuAdministrador() {
        Scanner sc = new Scanner(System.in);
        int opcaoAdm;

        System.out.println("Menu Administrador:");
        System.out.println("1 - Criar Produto");
        System.out.println("2 - Atualizar Produto");
        System.out.println("3 - Deletar Produto");
        System.out.println("4 - Listar Produtos");
        System.out.println("Escolha uma opção:");
        opcaoAdm = sc.nextInt();

        switch (opcaoAdm) {
            case 1:
                criarProduto(); // Criar produto
                break;

            case 2:
                atualizarProduto(); // Atualizar produto
                break;

            case 3:
                deletarProduto(); // Deletar produto
                break;

            case 4:
                listarProdutos(); // Listar produtos
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    // CRUD para Carrinho

    // Criar carrinho
    private static void criarCarrinho() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do cliente:");
        String nomeCliente = sc.nextLine();

        Carrinho carrinho = new Carrinho(nomeCliente); // Criando novo carrinho
        System.out.println("Carrinho criado com sucesso para o cliente: " + nomeCliente);
    }

    // Atualizar carrinho
    private static void atualizarCarrinho() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do carrinho para atualizar:");
        int idCarrinho = sc.nextInt();
        // Lógica para encontrar carrinho pelo ID
        // Aqui você pode adicionar ou remover produtos conforme necessário
        // Exemplo fictício:
        Carrinho carrinho = buscarCarrinhoPorId(idCarrinho);
        if (carrinho != null) {
            System.out.println("Carrinho encontrado. Atualizando...");
            // Faça as operações de atualização de produtos no carrinho
        } else {
            System.out.println("Carrinho não encontrado.");
        }
    }

    // Deletar carrinho
    private static void deletarCarrinho() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do carrinho para deletar:");
        int idCarrinho = sc.nextInt();
        // Lógica para deletar o carrinho pelo ID
        // Exemplo fictício:
        Carrinho carrinho = buscarCarrinhoPorId(idCarrinho);
        if (carrinho != null) {
            System.out.println("Carrinho deletado com sucesso!");
            // Remover carrinho da lista
        } else {
            System.out.println("Carrinho não encontrado.");
        }
    }

    // CRUD para Produto

    // Criar produto
    private static void criarProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do produto:");
        String nome = sc.nextLine();
        System.out.println("Digite a descrição do produto:");
        String descricao = sc.nextLine();
        System.out.println("Digite a categoria do produto:");
        String categoria = sc.nextLine();
        System.out.println("Digite o preço do produto:");
        double preco = sc.nextDouble();
        System.out.println("Digite a quantidade do produto:");
        int quantidade = sc.nextInt();

        Produto produto = new Produto(nome, descricao, categoria, preco, quantidade); // Criando produto
        System.out.println("Produto criado com sucesso: " + produto);
    }

    // Atualizar produto
    private static void atualizarProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do produto para atualizar:");
        int idProduto = sc.nextInt();
        // Lógica para buscar o produto pelo ID e atualizar os detalhes
        // Exemplo fictício:
        Produto produto = buscarProdutoPorId(idProduto);
        if (produto != null) {
            System.out.println("Produto encontrado. Atualizando...");
            System.out.println("Digite a nova quantidade:");
            int novaQuantidade = sc.nextInt();
            produto.setQuantidade(novaQuantidade); // Atualiza a quantidade
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // Deletar produto
    private static void deletarProduto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do produto para deletar:");
        int idProduto = sc.nextInt();
        // Lógica para deletar o produto pelo ID
        Produto produto = buscarProdutoPorId(idProduto);
        if (produto != null) {
            System.out.println("Produto deletado com sucesso!");
            // Remover produto da lista
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // Listar produtos
    private static void listarProdutos() {
        // Aqui você pode implementar a listagem de todos os produtos cadastrados
        // Exemplo fictício:
        System.out.println("Lista de produtos:");
        // Lógica de listagem dos produtos (você pode iterar sobre a lista de produtos)
    }

    // Métodos fictícios para buscar produtos e carrinhos por ID

    private static Produto buscarProdutoPorId(int id) {
        // Lógica para buscar produto no banco de dados ou lista
        return new Produto("Produto Exemplo", "Descrição Exemplo", "Categoria", 10.0, 5); // Exemplo
    }

    private static Carrinho buscarCarrinhoPorId(int id) {
        // Lógica para buscar carrinho no banco de dados ou lista
        return new Carrinho("Cliente Exemplo"); // Exemplo
    }

    // Classe Produto
    static class Produto {
        private String nome;
        private String descricao;
        private String categoria;
        private double preco;
        private int quantidade;

        public Produto(String nome, String descricao, String categoria, double preco, int quantidade) {
            this.nome = nome;
            this.descricao = descricao;
            this.categoria = categoria;
            this.preco = preco;
            this.quantidade = quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        @Override
        public String toString() {
            return "Produto{nome='" + nome + "', descricao='" + descricao + "', categoria='" + categoria + "', preco=" + preco + ", quantidade=" + quantidade + "}";
        }
    }

    // Classe Carrinho
    static class Carrinho {
        private String cliente;
        private List<Produto> produtos;

        public Carrinho(String cliente) {
            this.cliente = cliente;
            this.produtos = new ArrayList<>();
        }

        public void adicionarProduto(Produto produto) {
            this.produtos.add(produto);
        }

        @Override
        public String toString() {
            return "Carrinho{cliente='" + cliente + "', produtos=" + produtos + "}";
        }
    }
}