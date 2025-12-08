package br.edu.ifpr.view;

import java.util.Scanner;
import br.edu.ifpr.controller.*;
import br.edu.ifpr.model.*;

public class ViewUtils {
    
    public static Scanner sc = new Scanner(System.in);

    // Método para ler inteiros sem bugar o buffer
    public static int lerInteiro() {
        try {
            String s = sc.nextLine();
            if (s.isEmpty()) return -1;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Método padronizado para exibir obras
    public static void mostrarObraDetalhada(Artwork a, UserController uC, CategoryController cC) {
        String nomeArtista = uC.listUsers().stream()
            .filter(u -> u.getId() == a.getIdUser())
            .map(User::getUsername)
            .findFirst()
            .orElse("Desconhecido");

        String nomeCategoria = cC.listCategories().stream()
            .filter(c -> c.getId() == a.getIdCategory()) 
            .map(Category::getName)
            .findFirst()
            .orElse("Sem Categoria");
            
        System.out.println("-------------------------------------------------");
        System.out.println("[" + a.getId() + "] " + a.getTitle().toUpperCase());
        System.out.println("   Artista: " + nomeArtista + " | Categoria: " + nomeCategoria);
        System.out.println("   Desc: " + a.getDescription());
        System.out.println("-------------------------------------------------");
    }
}