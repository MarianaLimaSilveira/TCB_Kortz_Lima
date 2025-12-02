package br.edu.ifpr.controller;

import br.edu.ifpr.model.User;

public class Sessao {
    public static User usuarioLogado;

    public static User getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(User usuario) {
        usuarioLogado = usuario;
    }

    public static boolean isLogado() {
        return usuarioLogado != null;
    }

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
