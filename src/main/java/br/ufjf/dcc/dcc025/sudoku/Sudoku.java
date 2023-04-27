/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.ufjf.dcc.dcc025.sudoku;

import java.util.Scanner;

/**
 *
 * @author Gabriel
 */
public class Sudoku {
    
    public static void main(String[] args) {        
        Tabuleiro tabela = new Tabuleiro();        
        configurarJogo(tabela);

    }
    
    public static void configurarJogo(Tabuleiro tabela){
        Scanner teclado = new Scanner(System.in);
        int escolha = 0;
        
        while(escolha != 1 && escolha != 2){
           System.out.println("Bem vindo, Jogador!");
           System.out.println("Vamos comecar um novo jogo, para isso, escolha:");
           System.out.println("  1) Jogo aleatório");
           System.out.println("  2) Definir jogo");
           escolha = teclado.nextInt();   
        }       
        
        if(escolha == 1){
            System.out.println("Quantos números você deseja gerar?");
            int numeros = teclado.nextInt();
            
            for (int i = 0; i < numeros; i++) {
                int linha = (int) (Math.random() * 9) + 1;
                int coluna = (int) (Math.random() * 9) + 1;
                int numero = (int) (Math.random() * 9) + 1;
                tabela.AdicionarJogada(linha, coluna, numero);                
            }
        }
        else{
            System.out.println("Informe os valores no padrão ([linha],[coluna],[valor]):");
            String entrada = teclado.nextLine();
            
            while(!entrada.equalsIgnoreCase("sair")){
               String[] substrings = entrada.split("\\)");
            
                for (String substring : substrings) {
                    String[] valores = substring.replace("(", "").split(",");                    
                    if (valores.length == 3) {
                        int linha = Integer.parseInt(valores[0]) - 1; 
                        int coluna = Integer.parseInt(valores[1]) - 1; 
                        int valor = Integer.parseInt(valores[2]);                    

                        tabela.AdicionarJogada(linha, coluna, valor);
                    }
                } 
            }
        }
    }
}
