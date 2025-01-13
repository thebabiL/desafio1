package DAO;

import entities.Carrinho;
import entities.Produto;
import exceptions.CarrinhoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoJDBC 
{
    private Connection conn;

    public CarrinhoJDBC(Connection conn) 
    {
        this.conn = conn;
    }

    public int criarCarrinho(Carrinho c) 
    {
        String sql = "INSERT INTO carrinho (valor_total) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            stmt.setDouble(1, c.getValorTotal());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) 
            {
                if (rs.next()) 
                {
                    return rs.getInt(1);
                }
            }
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao criar carrinho no banco: " + e.getMessage());
        }
        return -1;
    }

    public void inserirProduto(int carrinhoId, Produto p, int quant, double subtotal) 
    {
        String sql = "INSERT INTO produto_carrinho (id_carrinho, id_produto, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, carrinhoId);
            stmt.setInt(2, p.getID());
            stmt.setInt(3, quant);
            stmt.setDouble(4, subtotal);
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao adicionar produto ao carrinho no banco: " + e.getMessage());
        }
    }

    public void atualizarProduto(int carrinhoId, Produto p, int novaQuant, double novoSubtotal) 
    {
        String sql = "UPDATE produto_carrinho SET quantidade = ?, subtotal = ? WHERE id_carrinho = ? AND id_produto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, novaQuant);
            stmt.setDouble(2, novoSubtotal);
            stmt.setInt(3, carrinhoId);
            stmt.setInt(4, p.getID());
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao atualizar produto no carrinho no banco: " + e.getMessage());
        }
    }

    public void removerProduto(int carrinhoId, int produtoId) 
    {
        String sql = "DELETE FROM produto_carrinho WHERE id_carrinho = ? AND id_produto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, carrinhoId);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao remover produto do carrinho no banco: " + e.getMessage());
        }
    }

    public List<String> listarProdutos(int carrinhoId) 
    {
        List<String> listagem = new ArrayList<>();
        String sql = "SELECT e.nome, pc.quantidade, pc.subtotal FROM produto_carrinho pc JOIN estoque e ON pc.id_produto = e.id WHERE pc.id_carrinho = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, carrinhoId);
            try (ResultSet rs = stmt.executeQuery()) 
            {
                while (rs.next()) 
                {
                    String nome = rs.getString("nome");
                    int quantidade = rs.getInt("quantidade");
                    double subtotal = rs.getDouble("subtotal");
                    listagem.add("- " + nome + " | Quantidade: " + quantidade + " | Subtotal: R$" + subtotal);
                }
            }
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao listar produtos do carrinho: " + e.getMessage());
        }
        return listagem;
    }

    public void encerrarCompra(int carrinhoId) 
    {
        String sql = "UPDATE carrinho SET finalizado = TRUE WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, carrinhoId);
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new CarrinhoException("Erro ao finalizar compra no banco: " + e.getMessage());
        }
    }
}