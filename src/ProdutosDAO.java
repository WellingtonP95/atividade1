/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    Connection conn;
    PreparedStatement prep = null;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
            conn = new conectaDAO().connectDB();

            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            prep.close();
            conn.close();

            System.out.println("Produto cadastrado com sucesso.");
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar" + ex.getMessage());
        }
    }
    
    public int venderProduto (int id) {
        try {
            conn = new conectaDAO().connectDB();
            
            String sqlSelect = "SELECT status FROM produtos WHERE id = ?";
            PreparedStatement prepSelect = conn.prepareStatement(sqlSelect);
            prepSelect.setInt(1, id);
            resultset = prepSelect.executeQuery();
            
            if (resultset.next() && "A Venda".equals(resultset.getString("status"))) {
                String sqlUpdate = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
                prep = conn.prepareStatement(sqlUpdate);
                prep.setInt(1, id);
                prep.executeUpdate();
                
                System.out.println("Produto vendido com sucesso.");
                return 1;
            } else {
                System.out.println("Produto indisponível para venda.");
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao vender o produto " + ex.getMessage());
            return 0;
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        try {
            conn = new conectaDAO().connectDB();
            
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(resultset.getInt("id"));
                produtos.setNome(resultset.getString("nome"));
                produtos.setStatus(resultset.getString("status"));
                produtos.setValor(resultset.getInt("valor"));
                
                listagem.add(produtos);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar" + ex.getMessage());
        }
        
        return listagem;
    }
    
    
    
        
}

