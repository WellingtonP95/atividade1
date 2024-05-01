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

