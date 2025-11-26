package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.ExhibitionDAO;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.model.Exhibition;
import br.edu.ifpr.model.ExhibitionArtwork;

/**

 ExhibitionArtworkDAO exige um Connection no construtor, por isso
 cada operação abre uma Connection em try-with-resources para garantir fechamento.
 */
public class ExhibitionArtworkController {

    private Scanner scanner = new Scanner(System.in);

    // MENU INTERATIVO (mover pro main dps)
    public void menuExhibitionArtwork() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== Gerenciamento Exposição↔Obra ===");
            System.out.println("1 - Vincular obra a exposição");
            System.out.println("2 - Desvincular obra de exposição (por par)");
            System.out.println("3 - Listar todos vínculos");
            System.out.println("4 - Listar obras de uma exposição");
            System.out.println("5 - Listar exposições de uma obra");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opcao) {
                case 1 -> vincularObraExposicaoInteractive();
                case 2 -> desvincularObraExposicaoInteractive();
                case 3 -> listarVinculos();
                case 4 -> listarObrasDeExposicaoInteractive();
                case 5 -> listarExposicoesDeObraInteractive();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }


    public boolean vincularObraExposicao(long exhibitionId, long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            if (conn == null) {
                System.out.println("Erro: não foi possível conectar ao banco.");
                return false;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
            ArtworkDAO artworkDAO = new ArtworkDAO();

            boolean exExists = exhibitionDAO.readAll().stream()
                    .anyMatch(e -> e.getId() == exhibitionId);
            boolean artExists = artworkDAO.readAll().stream()
                    .anyMatch(a -> a.getId() == artworkId);

            if (!exExists) {
                System.out.println("Exposição não encontrada (id=" + exhibitionId + ").");
                return false;
            }
            if (!artExists) {
                System.out.println("Obra não encontrada (id=" + artworkId + ").");
                return false;
            }

            boolean jaVinculado = eaDAO.readAll().stream()
                    .anyMatch(ea -> ea.getExhibitionId() == exhibitionId && ea.getArtworkId() == artworkId);

            if (jaVinculado) {
                System.out.println("⚠ Essa obra já está vinculada a essa exposição.");
                return false;
            }

            ExhibitionArtwork ea = new ExhibitionArtwork();
            ea.setExhibitionId(exhibitionId);
            ea.setArtworkId(artworkId);

            eaDAO.create(ea);
            System.out.println("Vínculo criado com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao vincular obra/exposição: " + e.getMessage());
            return false;
        }
    }

    public boolean desvincularObraExposicao(long exhibitionId, long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            if (conn == null) {
                System.out.println("Erro: não foi possível conectar ao banco.");
                return false;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);

            List<ExhibitionArtwork> lista = eaDAO.readAll();
            ExhibitionArtwork encontrado = lista.stream()
                    .filter(ea -> ea.getExhibitionId() == exhibitionId && ea.getArtworkId() == artworkId)
                    .findFirst()
                    .orElse(null);

            if (encontrado == null) {
                System.out.println("Vínculo não encontrado entre exposição " + exhibitionId + " e obra " + artworkId + ".");
                return false;
            }

            eaDAO.delete(encontrado.getId());
            System.out.println("Vínculo removido com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao desvincular: " + e.getMessage());
            return false;
        }
    }

    public void listarVinculos() {
        try (Connection conn = ConnectionFactory.connect()) {
            if (conn == null) {
                System.out.println("Erro: não foi possível conectar ao banco.");
                return;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            List<ExhibitionArtwork> lista = eaDAO.readAll();

            if (lista.isEmpty()) {
                System.out.println("Nenhum vínculo encontrado.");
                return;
            }

            System.out.println("\n=== Vínculos Exposição ↔ Obra ===");
            for (ExhibitionArtwork ea : lista) {
                System.out.println("ID vínculo: " + ea.getId() +
                        " | Exposição: " + ea.getExhibitionId() +
                        " | Obra: " + ea.getArtworkId());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar vínculos: " + e.getMessage());
        }
    }

    public void listarObrasDeExposicao(long exhibitionId) {
        try (Connection conn = ConnectionFactory.connect()) {
            if (conn == null) {
                System.out.println("Erro: não foi possível conectar ao banco.");
                return;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            ArtworkDAO artworkDAO = new ArtworkDAO();

            List<Long> artworkIds = eaDAO.readAll().stream()
                    .filter(ea -> ea.getExhibitionId() == exhibitionId)
                    .map(ExhibitionArtwork::getArtworkId)
                    .collect(Collectors.toList());

            if (artworkIds.isEmpty()) {
                System.out.println("Nenhuma obra vinculada à exposição " + exhibitionId + ".");
                return;
            }

            System.out.println("\nObras vinculadas à exposição " + exhibitionId + ":");
            List<Artwork> obras = artworkDAO.readAll().stream()
                    .filter(a -> artworkIds.contains((long) a.getId()))
                    .collect(Collectors.toList());

            for (Artwork a : obras) {
                System.out.println("ID: " + a.getId() + " | Título: " + a.getTitle() + " | Categoria: " + a.getIdCategory());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar obras de exposição: " + e.getMessage());
        }
    }

    public void listarExposicoesDeObra(long artworkId) {
        try (Connection conn = ConnectionFactory.connect()) {
            if (conn == null) {
                System.out.println("Erro: não foi possível conectar ao banco.");
                return;
            }

            ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(conn);
            ExhibitionDAO exhibitionDAO = new ExhibitionDAO();

            List<Long> exhibitionIds = eaDAO.readAll().stream()
                    .filter(ea -> ea.getArtworkId() == artworkId)
                    .map(ExhibitionArtwork::getExhibitionId)
                    .collect(Collectors.toList());

            if (exhibitionIds.isEmpty()) {
                System.out.println("Nenhuma exposição vinculada à obra " + artworkId + ".");
                return;
            }

            System.out.println("\nExposições vinculadas à obra " + artworkId + ":");
            List<Exhibition> expos = exhibitionDAO.readAll().stream()
                    .filter(e -> exhibitionIds.contains((long) e.getId()))
                    .collect(Collectors.toList());

            for (Exhibition e : expos) {
                System.out.println("ID: " + e.getId() + " | Nome: " + e.getName() + " | Tema: " + e.getTheme());
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar exposições de obra: " + e.getMessage());
        }
    }

    private void vincularObraExposicaoInteractive() {
        try {
            System.out.print("ID da exposição: ");
            long exId = Long.parseLong(scanner.nextLine());
            System.out.print("ID da obra: ");
            long artId = Long.parseLong(scanner.nextLine());
            vincularObraExposicao(exId, artId);
        } catch (NumberFormatException e) {
            System.out.println("IDs inválidos.");
        }
    }

    private void desvincularObraExposicaoInteractive() {
        try {
            System.out.print("ID da exposição: ");
            long exId = Long.parseLong(scanner.nextLine());
            System.out.print("ID da obra: ");
            long artId = Long.parseLong(scanner.nextLine());
            desvincularObraExposicao(exId, artId);
        } catch (NumberFormatException e) {
            System.out.println("IDs inválidos.");
        }
    }

    private void listarObrasDeExposicaoInteractive() {
        try {
            System.out.print("ID da exposição: ");
            long exId = Long.parseLong(scanner.nextLine());
            listarObrasDeExposicao(exId);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void listarExposicoesDeObraInteractive() {
        try {
            System.out.print("ID da obra: ");
            long artId = Long.parseLong(scanner.nextLine());
            listarExposicoesDeObra(artId);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}
