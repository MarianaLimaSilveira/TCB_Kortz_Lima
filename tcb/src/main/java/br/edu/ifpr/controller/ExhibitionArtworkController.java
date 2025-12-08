package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.model.ExhibitionArtwork;

public class ExhibitionArtworkController {

    // Método para listar obras de uma exposição (no console)
    public void listarObrasDeExposicao(long exhibitionId) {
        try (Connection conn = ConnectionFactory.connect()) {
            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            ArtworkDAO artworkDAO = new ArtworkDAO(); 

            // Pega os IDs das obras vinculadas
            List<Long> artworkIds = eaDAO.readAll().stream()
                    .filter(ea -> ea.getExhibitionId() == exhibitionId)
                    .map(ExhibitionArtwork::getArtworkId)
                    .collect(Collectors.toList());

            if (artworkIds.isEmpty()) {
                System.out.println("Nenhuma obra vinculada a exposicao " + exhibitionId + ".");
                return;
            }

            System.out.println("\nObras na exposicao " + exhibitionId + ":");
            // Busca os detalhes das obras
            List<Artwork> obras = artworkDAO.readAll().stream()
                    .filter(a -> artworkIds.contains((long) a.getId()))
                    .collect(Collectors.toList());

            for (Artwork a : obras) {
                System.out.println("- " + a.getTitle() + " (ID: " + a.getId() + ")");
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }

    // VINCULAR (Adicionar Obra) - COM PROTEÇÃO DE DONO
    public void vincularObraExposicao(long exhibitionId, long artworkId) {
        // Abre conexão UMA vez para todas as operações
        try (Connection conn = ConnectionFactory.connect()) {
            
            ArtworkDAO artworkDAO = new ArtworkDAO(); // ArtworkDAO gerencia sua própria conexão
            Artwork obra = artworkDAO.findById((int) artworkId);

            // 1. Verifica se a obra existe
            if (obra == null) {
                System.out.println("Obra nao encontrada.");
                return;
            }

            // 2. REGRA DE NEGÓCIO: Só pode adicionar se a obra for SUA
            if (obra.getIdUser() != Sessao.getIdUsuario()) {
                System.out.println("ERRO: Voce so pode adicionar suas proprias obras em suas exposicoes.");
                return;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);

            // 3. Verifica se já está vinculada
            boolean jaVinculado = eaDAO.readAll().stream()
                    .anyMatch(ea -> ea.getExhibitionId() == exhibitionId && ea.getArtworkId() == artworkId);

            if (jaVinculado) {
                System.out.println("Essa obra ja esta nesta exposicao.");
                return;
            }

            // 4. Cria o vínculo
            ExhibitionArtwork ea = new ExhibitionArtwork();
            ea.setExhibitionId(exhibitionId);
            ea.setArtworkId(artworkId);

            eaDAO.create(ea);
            // Mensagem de sucesso já sai no DAO

        } catch (Exception e) {
            System.out.println("Erro ao vincular: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // DESVINCULAR (Remover Obra)
    public void desvincularObraExposicao(long exhibitionId, long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);

            ExhibitionArtwork link = eaDAO.readAll().stream()
                    .filter(ea -> ea.getExhibitionId() == exhibitionId && ea.getArtworkId() == artworkId)
                    .findFirst()
                    .orElse(null);

            if (link == null) {
                System.out.println("Vinculo nao encontrado.");
                return;
            }

            eaDAO.delete(link.getId());

        } catch (Exception e) {
            System.out.println("Erro ao desvincular: " + e.getMessage());
        }
    }
}