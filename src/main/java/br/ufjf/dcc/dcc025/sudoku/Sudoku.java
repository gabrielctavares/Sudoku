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
        Scanner teclado = new Scanner(System.in); 
        Tabuleiro tabela = new Tabuleiro();        
        configurarJogo(tabela, teclado);
        jogar(tabela, teclado);
    }
    
    private static void configurarJogo(Tabuleiro tabela, Scanner teclado){
        int escolha = 0;        
        while(escolha != 1 && escolha != 2){
           System.out.println("Bem vindo, Jogador!");
           System.out.println("Vamos comecar um novo jogo, para isso, escolha:");
           System.out.println("  1) Jogo aleatório");
           System.out.println("  2) Definir jogo");
           System.out.print("Opção: ");
           escolha = teclado.nextInt();   
        }       
        
        if(escolha == 1){
            System.out.println("Quantos números você deseja gerar?");
            int numeros = teclado.nextInt();
            int jogadas = 0;
            int tentativas = 0;
            
            while(jogadas < numeros) {
                int linha = (int) (Math.random() * 9) + 1;
                int coluna = (int) (Math.random() * 9) + 1;
                int numero = (int) (Math.random() * 9) + 1;
                
                if(tabela.AdicionarJogadaValida(linha, coluna, numero))
                    jogadas++;
                
                tentativas++;
                
                if(tentativas > 1000 + numeros){
                    System.out.println("Não foi possível adicionar " + numeros + " números, tente reduzir a quantidade!");
                    break;
                }                    
            }            
        }
        else{
           AdicionarJogada(tabela, teclado, false);
        }
        
        System.out.println("Jogo configurado.");
        tabela.ImprimirTabela();
    }
    
    private static void jogar(Tabuleiro tabela, Scanner teclado){ 
        while(!tabela.IsJogoCompleto()){
            int escolha = 0;
            while(escolha < 1 || escolha > 5){
              System.out.println("Menu principal");
              System.out.println("  1) Adicionar jogada");
              System.out.println("  2) Remover jogada");
              System.out.println("  3) Verificar");
              System.out.println("  4) Receber dica");
              System.out.println("  5) Sair");
              System.out.print("Opção: ");
              escolha = teclado.nextInt();   
           }
            switch (escolha) {
               case 1 -> { 
                   AdicionarJogada(tabela, teclado, true);                   
                   tabela.ImprimirTabela();
               }
               case 2 -> { 
                   RemoverJogada(tabela, teclado);            
                   tabela.ImprimirTabela();
               }
               case 3 -> { tabela.ImprimirErros(); }
               case 4 -> { ReceberDica(tabela, teclado); }
               case 5 -> { 
                   System.out.println("Obrigado por jogar!");
                   System.exit(0);
               }
           } 
        }
        
        System.out.println("Parabéns campeão, você completou o jogo!");
    }
    
    private static void AdicionarJogada(Tabuleiro tabela, Scanner teclado, boolean leituraUnica){
        while(true){
            System.out.print("Informe os valores no padrão ([linha],[coluna],[valor]): ");
            String entrada = teclado.next();
            
            if(entrada.trim().equalsIgnoreCase("sair"))
                return;
            
            String[] substrings = entrada.split("\\)");

            for (String substring : substrings) {
                String[] valores = substring.replace("(", "").split(",");                    
                if (valores.length == 3) {
                    int linha = Integer.parseInt(valores[0]); 
                    int coluna = Integer.parseInt(valores[1]); 
                    int valor = Integer.parseInt(valores[2]);                    

                    tabela.AdicionarJogada(linha, coluna, valor);
                }
            }    
            
            if(leituraUnica)
                return;
        }            
    }
    private static void RemoverJogada(Tabuleiro tabela, Scanner teclado){
        System.out.print("Informe o campo no padrão ([linha],[coluna]): ");
        String entrada = teclado.next();
        String[] valores = entrada.replace("(", "").replace(")", "").split(",");                    
        if (valores.length == 2) {
            int linha = Integer.parseInt(valores[0]); 
            int coluna = Integer.parseInt(valores[1]);        
            tabela.RemoverJogada(linha, coluna);
        }   
        else {
            System.out.println("Campo digitado fora do padrão!");
        }
    }
    
    private static void ReceberDica(Tabuleiro tabela, Scanner teclado){
        System.out.print("Informe o campo no padrão ([linha],[coluna]): ");
        String entrada = teclado.next();
        String[] valores = entrada.replace("(", "").replace(")", "").split(",");                    
        if (valores.length == 2) {
            int linha = Integer.parseInt(valores[0]); 
            int coluna = Integer.parseInt(valores[1]);        
            tabela.ImprimirPossiveisJogadas(linha, coluna);
        }   
        else {
            System.out.println("Campo digitado fora do padrão!");
        }
    }
}
