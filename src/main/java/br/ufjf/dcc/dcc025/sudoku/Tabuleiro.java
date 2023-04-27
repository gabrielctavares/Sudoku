package br.ufjf.dcc.dcc025.sudoku;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
public class Tabuleiro {
    private int[][] tabela = new int[9][9];      
    
    private boolean Validar(int linha, int coluna, int valor){
        for (int i = 0; i < 9; i++) {
           if(tabela[linha][i] == valor)             
               return false;
           
           if(tabela[i][coluna] == valor)             
               return false;
        }
        
        int quadranteLinha =  linha / 3;
        int quadranteColuna = coluna / 3;
        
        for (int i = 0; i < 3; i++) {            
           for (int j = 0; j < 3; j++) {
               int linhaQuadrante = (quadranteLinha * 3) + i;
               int colunaQuadrante = (quadranteColuna * 3) + j;
               
               if(tabela[linhaQuadrante][colunaQuadrante] == valor)             
                 return false;
                
            }
        } 
        return true;
    }
    
    public void AdicionarJogada(int linha, int coluna, int valor){
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;
        
        if(Validar(internalLinha, internalColuna, valor))
            tabela[internalLinha][internalColuna] = valor;
    }
    
    public void RemoverJogada(int linha, int coluna){
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;
        
        tabela[internalLinha][internalColuna] = 0;
    }
}
