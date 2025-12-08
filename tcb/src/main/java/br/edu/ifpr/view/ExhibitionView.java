package br.edu.ifpr.view;

import java.util.List;
import java.util.stream.Collectors;
import br.edu.ifpr.controller.*;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.model.*;

public class ExhibitionView {

    private ExhibitionController exhibitionController;
    private ExhibitionArtworkController eaController;
    private ArtworkController artworkController;
    private ExhibitionArtworkDAO eaDaoLeitura; // Para contagem

    public ExhibitionView(ExhibitionController eC, ExhibitionArtworkController eaC, ArtworkController aC, ExhibitionArtworkDAO dao) {
        this.exhibitionController = eC;
        this.eaController = eaC;
        this.artworkController = aC;
        this.eaDaoLeitura = dao;
    }

    public void menuMinhasExposicoes() {
        System.out.println("\n=== MINHAS EXPOSICOES ===");
        long meuId = Sessao.getIdUsuario();

        List<Exhibition> minhasExpos = exhibitionController.listExhibitions().stream()
                .filter(e -> e.getIdCreator() == meuId)
                .collect(Collectors.toList());
        
        List<ExhibitionArtwork> allLinks = eaDaoLeitura.readAll(); 

        for (Exhibition e : minhasExpos) {
            long count = allLinks.stream().filter(l -> l.getExhibitionId() == e.getId()).count();
            System.out.println("ID: " + e.getId() + " | " + e.getName() + " [" + count + " obras]");
        }

        System.out.println("\n1. Criar Nova Exposicao");
        System.out.println("2. Gerenciar Obras (Adicionar/Remover)");
        System.out.println("3. Editar Exposicao");
        System.out.println("4. Excluir Exposicao");
        System.out.println("0. Voltar");
        System.out.print(">> ");

        int op = ViewUtils.lerInteiro();
        switch(op) {
            case 1 -> criarExposicao(meuId);
            case 2 -> gerenciarObras(minhasExpos, meuId);
            case 3 -> editarExposicao(minhasExpos);
            case 4 -> excluirExposicao(minhasExpos);
        }
    }

    private void criarExposicao(long meuId) {
        Exhibition e = new Exhibition();
        e.setIdCreator(meuId);
        System.out.print("Nome: ");
        e.setName(ViewUtils.sc.nextLine());
        System.out.print("Tema: ");
        e.setTheme(ViewUtils.sc.nextLine());
        System.out.print("Descricao: ");
        e.setDescription(ViewUtils.sc.nextLine());
        System.out.print("Data Inicio (YYYY-MM-DD): ");
        e.setStartDate(ViewUtils.sc.nextLine());
        System.out.print("Data Fim (YYYY-MM-DD): ");
        e.setEndDate(ViewUtils.sc.nextLine());
        exhibitionController.createExhibition(e);
    }

    private void gerenciarObras(List<Exhibition> minhasExpos, long meuId) {
        System.out.print("ID da sua exposicao: ");
        long exId = Long.parseLong(ViewUtils.sc.nextLine());
        
        if (minhasExpos.stream().noneMatch(e -> e.getId() == exId)) {
            System.out.println("Exposicao nao encontrada ou nao e sua.");
            return;
        }
        
        System.out.println("1. Listar obras nesta exposicao");
        System.out.println("2. Adicionar obra");
        System.out.println("3. Remover obra");
        int subOp = ViewUtils.lerInteiro();
        
        if (subOp == 1) {
            eaController.listarObrasDeExposicao(exId);
        } else if (subOp == 2) {
            System.out.println("--- Suas Obras Disponiveis ---");
            artworkController.listArtworks().stream()
                .filter(a -> a.getIdUser() == meuId)
                .forEach(a -> System.out.println("["+a.getId()+"] "+a.getTitle()));
            
            System.out.print("Digite o ID da Obra para adicionar: ");
            eaController.vincularObraExposicao(exId, Long.parseLong(ViewUtils.sc.nextLine()));
            
        } else if (subOp == 3) {
            System.out.println("--- Obras na Exposicao ---");
            eaController.listarObrasDeExposicao(exId);
            System.out.print("Digite o ID da Obra para remover: ");
            eaController.desvincularObraExposicao(exId, Long.parseLong(ViewUtils.sc.nextLine()));
        }
    }

    private void editarExposicao(List<Exhibition> minhasExpos) {
        System.out.print("ID da exposicao: ");
        int idEx = ViewUtils.lerInteiro();
        Exhibition e = minhasExpos.stream().filter(x -> x.getId() == idEx).findFirst().orElse(null);
        if(e != null) {
            System.out.print("Novo Nome (ENTER pula): ");
            String nome = ViewUtils.sc.nextLine();
            if(!nome.isEmpty()) e.setName(nome);
            exhibitionController.updateExhibition(e);
        }
    }

    private void excluirExposicao(List<Exhibition> minhasExpos) {
        System.out.print("ID da exposicao para deletar: ");
        int idEx = ViewUtils.lerInteiro();
        if (minhasExpos.stream().anyMatch(e -> e.getId() == idEx)) {
            exhibitionController.deleteExhibition(idEx);
        }
    }
}