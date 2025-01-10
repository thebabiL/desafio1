package Desafio1.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Desafio1.entities.Produto;
import Desafio1.exceptions.AtualizacaoException;
import Desafio1.exceptions.BuscaException;
import Desafio1.exceptions.ExclusaoException;
import Desafio1.exceptions.InsercaoException;

public class ProdutoDaoJDBC implements ProdutoDAO 
{

    private Connection conn;

    public ProdutoDaoJDBC(Connection conn) 
    {
        this.conn = conn;
    }

    @Override
    public void inserir(Produto produto) 
    {
        String sql = "INSERT INTO Produto (nome, descricao, categoria, valor, quantidade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCategoria());
            stmt.setDouble(4, produto.getValor());
            stmt.setInt(5, produto.getQuantidade());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) 
            {
                try (ResultSet rs = stmt.getGeneratedKeys()) 
                {
                    if (rs.next()) 
                    {
                        produto.setId(rs.getInt(1));
                    }
                }
            } 
            else 
            {
                throw new InsercaoException("Falha ao inserir produto, nenhuma linha foi afetada.", null);
            }
        } 
        catch (SQLException e) 
        {
            throw new InsercaoException("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Produto produto) 
    {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, categoria = ?, valor = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCategoria());
            stmt.setDouble(4, produto.getValor());
            stmt.setInt(5, produto.getQuantidade());
            stmt.setInt(6, produto.getId());

            int linhas = stmt.executeUpdate();

            if (linhas == 0) 
            {
                throw new AtualizacaoException("Nenhum produto encontrado para atualizar com o ID: " + produto.getId(), null);
            }
        } 
        catch (SQLException e) 
        {
            throw new AtualizacaoException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletarPorId(int id) 
    {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) 
            {
                throw new ExclusaoException("Nenhum produto encontrado para excluir com o ID: " + id, null);
            }
        } 
        catch (SQLException e) 
        {
            throw new ExclusaoException("Erro ao excluir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto buscarPorId(int id) 
    {
        String sql = "SELECT * FROM Produto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    return instanciarProduto(rs);
                }
                return null;
            }
        } 
        catch (SQLException e) 
        {
            throw new BuscaException("Erro ao buscar produto por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> buscarTudo() {
        String sql = "SELECT * FROM Produto";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) 
            {
                produtos.add(instanciarProduto(rs));
            }
            return produtos;
        } 
        catch (SQLException e) 
        {
            throw new BuscaException("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }
    }

    private Produto instanciarProduto(ResultSet rs) throws SQLException 
    {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setCategoria(rs.getString("categoria"));
        produto.setValor(rs.getDouble("valor"));
        produto.setQuantidade(rs.getInt("quantidade"));
        return produto;
    }
}