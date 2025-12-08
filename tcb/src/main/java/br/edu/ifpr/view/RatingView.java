package br.edu.ifpr.view;

import java.util.List;
import java.util.stream.Collectors;
import br.edu.ifpr.controller.*;
import br.edu.ifpr.model.Rating;

public class RatingView {

    private RatingController ratingController;
    private ArtworkController artworkController;
    private ExhibitionController exhibitionController;

    public RatingView(RatingController rC, ArtworkController aC, ExhibitionController eC) {
        this.ratingController = rC;
        this.artworkController = aC;
        this.exhibitionController = eC;
    }

    public void menuMinhasAvaliacoes() {
        System.out.println("\n=== AVALIACOES ===");
        System.out.println("1. Avaliar um item");
        System.out.println("2. Minhas reviews");
        System.out.println("0. Voltar");
        
        int op = ViewUtils.lerInteiro();
        
        if(op == 1) {
            Rating r = new Rating();
            r.setUserId(Sessao.getIdUsuario());

            System.out.println("O que voce deseja avaliar?");
            System.out.println("1 - Uma Obra de Arte");
            System.out.println("2 - Uma Exposicao");
            int tipo = ViewUtils.lerInteiro();

            if (tipo == 1) {
                System.out.println("--- Obras Disponiveis ---");
                artworkController.listArtworks().forEach(a -> System.out.println("["+a.getId()+"] " + a.getTitle()));
                System.out.print("Digite o ID da Obra: ");
                long id = Long.parseLong(ViewUtils.sc.nextLine());
                r.setArtworkId(id);
                
            } else if (tipo == 2) {
                System.out.println("--- Exposicoes Disponiveis ---");
                exhibitionController.listExhibitions().forEach(e -> System.out.println("["+e.getId()+"] " + e.getName()));
                System.out.print("Digite o ID da Exposicao: ");
                long id = Long.parseLong(ViewUtils.sc.nextLine());
                r.setExhibitionId(id);
                
            } else {
                System.out.println("Opcao invalida. Cancelando.");
                return;
            }

            System.out.print("Nota (1-5): ");
            int nota = ViewUtils.lerInteiro();
            r.setNote(nota); 

            System.out.print("Comentario: ");
            r.setText(ViewUtils.sc.nextLine());

            ratingController.createRating(r);

        } else if (op == 2) {
            long meuId = Sessao.getIdUsuario();
            List<Rating> minhas = ratingController.listRatings().stream()
                    .filter(rev -> rev.getUserId() == meuId)
                    .collect(Collectors.toList());
            
            if(minhas.isEmpty()) System.out.println("Voce nao fez avaliacoes.");
            else {
                System.out.println("--- Suas Reviews ---");
                minhas.forEach(rev -> System.out.println("Review ID: " + rev.getId() + " | Nota: " + rev.getNote() + " | " + rev.getText()));
                
                System.out.print("Deseja apagar alguma? Digite o ID da Review (ou 0 para sair): ");
                long idDel = Long.parseLong(ViewUtils.sc.nextLine());
                if(idDel > 0 && minhas.stream().anyMatch(m -> m.getId() == idDel)) {
                    ratingController.deleteRating(idDel);
                }
            }
        }
    }
}