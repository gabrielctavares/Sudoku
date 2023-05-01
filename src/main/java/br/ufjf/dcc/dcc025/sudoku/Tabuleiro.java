package br.ufjf.dcc.dcc025.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
public class Tabuleiro {
    private int[][] tabela;      
    private Set<String> jogadasInvalidas;

    public Tabuleiro() {
        this.tabela = new int[9][9];
        this.jogadasInvalidas = new HashSet();
    }
    
    private boolean ValidarIdx(int linha, int coluna){
        if(linha < 0 || linha > 8)
            return false;
        
        if(coluna < 0 || coluna > 8)
            return false;
        
        return true;
    }
    
    private void Validar(int linha, int coluna, int valor) throws Exception {
        if(!ValidarIdx(linha, coluna) || (valor < 1 || valor > 9))
            throw new Exception("Valores de linha/coluna/valor inválidos.");
        
        if(tabela[linha][coluna] > 0)
            throw new Exception("Campo já preenchido!");        
        
        String mensagemErro = String.format("(%d,%d,%d)", linha+1, coluna+1, valor);
        for (int i = 0; i < 9; i++) {
           if(tabela[linha][i] == valor)             
               throw new JogadaInvalidaException(mensagemErro);
           
           if(tabela[i][coluna] == valor)      
               throw new JogadaInvalidaException(mensagemErro);
        }        
        int quadranteLinha =  linha / 3;
        int quadranteColuna = coluna / 3;
        
        for (int i = 0; i < 3; i++) {            
           for (int j = 0; j < 3; j++) {
               int linhaQuadrante = (quadranteLinha * 3) + i;
               int colunaQuadrante = (quadranteColuna * 3) + j;
               
               if(tabela[linhaQuadrante][colunaQuadrante] == valor)   
                throw new JogadaInvalidaException(mensagemErro);
            }
        } 
    }
    
    public boolean AdicionarJogadaValida(int linha, int coluna, int valor){
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;        
       
        try {
           Validar(internalLinha, internalColuna, valor); 
            tabela[internalLinha][internalColuna] = valor;  
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    public void AdicionarJogada(int linha, int coluna, int valor){
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;        
       
        try {
           Validar(internalLinha, internalColuna, valor); 
        } catch (JogadaInvalidaException e) {
            jogadasInvalidas.add(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        
        tabela[internalLinha][internalColuna] = valor;  
    }
    
    public boolean RemoverJogada(int linha, int coluna){        
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;
        
        if(!ValidarIdx(internalLinha, internalColuna))
            return false;
        
        removeJogadaInvalida(linha, coluna);
        tabela[internalLinha][internalColuna] = 0;
        return true;
    }
    
    public void removeJogadaInvalida(int linha, int coluna) {
        Iterator<String> iterator = jogadasInvalidas.iterator();
        while (iterator.hasNext()) {
            String jogada = iterator.next();
            
            if (jogada.matches("^\\(" + String.format("%d,%d", linha, coluna) + ",.*")) // regex: tem que começar com (, os dois numeros informados e ", qualquer numero"
                iterator.remove();
        }
    }

    public void ImprimirTabela(){
        System.out.println("-------------------------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(j == 0)
                    System.out.print("| ");
                
                if(tabela[i][j] == 0)
                    System.out.print("  | ");
                else
                    System.out.print(tabela[i][j] + " | ");
            }
            System.out.println("");
            
            if(i == 8)
                System.out.println("-------------------------------------");
            else
                System.out.println("|---|---|---|---|---|---|---|---|---|");
        }
    }
    public void ImprimirErros(){
        if(jogadasInvalidas.isEmpty()){
            System.out.println("Jogo correto!");
            return;
        }
        
        for (String jogada : jogadasInvalidas) {
            System.out.println("Violação na jogada " + jogada);            
        }
            
    }
    
    public void ImprimirPossiveisJogadas(int linha, int coluna){
        int internalLinha = linha - 1;
        int internalColuna = coluna - 1;  
        
        if(!ValidarIdx(internalLinha, internalColuna)){
            System.out.println("Posição Inválida, não é possível obter dicas!");
            return;
        }
            
        if(tabela[internalLinha][internalColuna] > 0){
            System.out.println("Posição já preenchida, não é possível obter dicas!");
            return;
        }
        List<Integer> valores = new ArrayList();
        
        System.out.print(String.format("Valores possíveis para a posição (%d,%d): ", linha, coluna));
        for (int i = 1; i <= 9; i++) {
            try {
                Validar(internalLinha, internalColuna, i);                
                valores.add(i);
            } catch (Exception e) {
            }
        }
        System.out.println(valores);   
    }

    public boolean IsJogoCompleto() {
        if(!this.jogadasInvalidas.isEmpty())
            return false;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(tabela[i][j] < 1 || tabela[i][j] > 9)
                    return false;
            }
        }        
        return true;
    }
}
