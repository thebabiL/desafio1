package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Produto;
import exceptions.ProdutoException;

public class ProdutoJDBC 
{
    private Connection conn;

    public ProdutoJDBC(Connection conn) 
    {
        this.conn = conn;
    }

    public void inserirProduto(Produto p) 
    {
        String sql = "INSERT INTO produto (nome, descricao, categoria, valor, quantidade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, p.getProduto());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCategoria());
            stmt.setDouble(4, p.getValor());
            stmt.setInt(5, p.getQuant());
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao adicionar produto ao estoque: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutos() 
    {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                produtos.add(new Produto
                (
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("categoria"),
                    rs.getDouble("valor"),
                    rs.getInt("quantidade")
                ));
            }
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao listar produtos do estoque: " + e.getMessage());
        }
        return produtos;
    }

    public Produto buscarProdutoPorID(int id) throws ProdutoException 
    {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    return new Produto
                    (
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("categoria"),
                        rs.getDouble("valor"),
                        rs.getInt("quantidade")
                    );
                } 
                else 
                {
                    throw new ProdutoException("Produto com ID " + id + " não encontrado no estoque.");
                }
            }
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao buscar produto por ID: " + e.getMessage());
        }
    }

    public void atualizarQuantBanco(Produto p) 
    {
        String sql = "UPDATE produto SET quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, p.getQuant());
            stmt.setInt(2, p.getID());
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao atualizar quantidade do produto no banco: " + e.getMessage());
        }
    }

    public void atualizarProduto(Produto p) 
    {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, categoria = ?, valor = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, p.getProduto());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCategoria());
            stmt.setDouble(4, p.getValor());
            stmt.setInt(5, p.getQuant());
            stmt.setInt(6, p.getID());
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao atualizar produto no estoque: " + e.getMessage());
        }
    }

    public void removerProduto(int produtoId) 
    {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, produtoId);
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new ProdutoException("Erro ao remover produto do estoque: " + e.getMessage());
        }
    }
}