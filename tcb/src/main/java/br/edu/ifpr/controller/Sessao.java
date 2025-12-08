package br.edu.ifpr.controller;

import br.edu.ifpr.model.User;

// Classe estática para gerenciar o estado de login em toda a aplicação (Singleton global simples)
public class Sessao {
    
    // Armazena o objeto do usuário que está logado no momento
    public static User usuarioLogado;

    public static User getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(User usuario) {
        usuarioLogado = usuario;
    }

    // Verifica se há alguém logado (usado para controlar o fluxo do menu inicial)
    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    // Retorna apenas o ID para facilitar consultas de filtro
    public static Long getIdUsuario() {
        if (usuarioLogado == null) {
            return null;
        }
        return usuarioLogado.getId();
    }

    public static void logout() {
        usuarioLogado = null;
    }
}