package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifpr.model.ExhibitionArtwork;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.ConnectionFactory;

public class ExhibitionArtworkController {

    // Lista todas as obras vinculadas a uma exposição específica
    public void listarObrasDeExposicao(long exhibitionId) {
        // Usa try-with-resources para garantir que a conexão feche ao terminar a listagem
        try (Connection conn = ConnectionFactory.connect()) {
            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            ArtworkDAO artworkDAO = new ArtworkDAO(); 

            // 1. Busca na tabela de relacionamento (exhibition_artwork) apenas os IDs das obras dessa exposição
            List<Long> artworkIds = eaDAO.readAll().stream()
                    .filter(ea -> ea.getExhibitionId() == exhibitionId)
                    .map(ExhibitionArtwork::getArtworkId)
                    .collect(Collectors.toList());

            if (artworkIds.isEmpty()) {
                System.out.println("Nenhuma obra vinculada a exposicao " + exhibitionId + ".");
                return;
            }

            // 2. Com a lista de IDs, busca os objetos completos (Artwork) para exibir os detalhes (Título, etc)
            System.out.println("\nObras na exposicao " + exhibitionId + ":");
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

    // Adiciona uma obra a uma exposição (Cria o vínculo)
    public void vincularObraExposicao(long exhibitionId, long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            
            ArtworkDAO artworkDAO = new ArtworkDAO(); 
            Artwork obra = artworkDAO.findById((int) artworkId);

            // Validação 1: Obra existe?
            if (obra == null) {
                System.out.println("Obra nao encontrada.");
                return;
            }

            // Validação 2 (Regra de Negócio): Segurança
            // Impede que um usuário adicione obras de outros artistas à sua exposição.
            if (obra.getIdUser() != Sessao.getIdUsuario()) {
                System.out.println("ERRO: Voce so pode adicionar suas proprias obras em suas exposicoes.");
                return;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);

            // Validação 3: Evita duplicidade (Obra já está na exposição?)
            boolean jaVinculado = eaDAO.readAll().stream()
                    .anyMatch(ea -> ea.getExhibitionId() == exhibitionId && ea.getArtworkId() == artworkId);

            if (jaVinculado) {
                System.out.println("Essa obra ja esta nesta exposicao.");
                return;
            }

            // Cria o vínculo no banco
            ExhibitionArtwork ea = new ExhibitionArtwork();
            ea.setExhibitionId(exhibitionId);
            ea.setArtworkId(artworkId);

            eaDAO.create(ea);

        } catch (Exception e) {
            System.out.println("Erro ao vincular: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Remove uma obra de uma exposição (Apaga o vínculo)
    public void desvincularObraExposicao(long exhibitionId, long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);

            // Busca o ID específico do vínculo para deletar
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