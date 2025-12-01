package br.edu.ifpr.controller;

import br.edu.ifpr.model.User;

public class Sessao {

    // --------- Atributos privados ---------
    private static User usuarioLogado;


    // --------- Getters e Setters ---------

    /**
     * Retorna o usuário atualmente logado.
     */
    public static User getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Define o usuário logado.
     * Usado apenas após login bem-sucedido.
     */
    public static void setUsuarioLogado(User usuario) {
        usuarioLogado = usuario;
    }


    // --------- Métodos auxiliares ---------

    /**
     * Verifica se existe um usuário logado.
     */
    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    /**
     * Retorna o ID do usuário logado, caso exista.
     * Útil para avaliações, obras, etc.
     */
    public static Long getIdUsuario() {
        if (usuarioLogado == null) {
            return null;
        }
        return usuarioLogado.getId();
    }

    /**
     * Encerra a sessão do usuário, limpando os dados.
     */
    public static void logout() {
        usuarioLogado = null;
    }
}
