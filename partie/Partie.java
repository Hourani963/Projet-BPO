package partie;

import joueur.Humain;
import table.Couleur;
import table.Plateau;

import java.util.Scanner;

public class Partie {
    private IJoueur[] joueurs;
    private Couleur courant;
    private boolean finie;

    public Partie() {
        joueurs = new Humain[Couleur.values().length];
        joueurs[Couleur.BLANC.ordinal()] = new Humain();
        joueurs[Couleur.NOIR.ordinal()] = new Humain();
        finie=false;
        courant = Couleur.BLANC;
    }

    public Couleur getJoueurCourant() {
        return courant;
    }

    public Couleur getJoueurAdverse() {
        return Couleur.values()[Math.abs(1 - courant.ordinal())];
    }

    /**
     * Changer le joueur courant
     */
    public void changerJoueur() {
        courant = getJoueurAdverse();
    }

    public String toString() {
        return courant.name();
    }

    /**
     * Commencer une Partie
     * @param p la partie
     */
    public void commencer(Plateau p) {
        System.out.println(p);
        Scanner scanner = new Scanner(System.in);
        System.out.println(this);
        System.out.print("> ");
        String ligne = scanner.nextLine();
        while (!ligne.equals("fin")) // Tant qu'entrée n'est pas "fin"
        {
            Coup coup = new Coup();
            if (!décomposer(ligne, coup, p)) {
                System.out.print("#");
            } else {
                if (!p.jouer(coup, this)) {
                    System.out.print("#");
                } else {
                    if(finie){
                        System.out.println("Le vainqueur est "+this);
                        break;
                    }
                    this.changerJoueur();
                    System.out.println(this);
                    System.out.println(p);
                }
            }

            System.out.print("> ");
            ligne = scanner.nextLine();
        }
        System.out.println(p);

        scanner.close();
    }

    /**
     * Décompose le coup et retourne si il est bien entré
     * @param ligne entrée du joueur
     * @param[in-out] coup le coup crée si la fonction retourne true
     * @param p plateau
     * @return true si il est syntaxiquement correct
     */
    private boolean décomposer(String ligne, Coup coup, Plateau p) {
        ligne.toLowerCase();

        if (ligne.length() != 4) {
            return false;
        }

        int
                var = 0, var1 = 0, var2 = 0, var3 = 0;

        if (Character.isLetter(ligne.charAt(0)) && (int) ligne.charAt(0) - (int)
                'a' >= 0 && (int) ligne.charAt(0) - (int)
                'a' <= 7) {
            var = (int) ligne.charAt(0) - (int)
                    'a';
        } else {
            return false;
        }
        if (Character.isDigit(ligne.charAt(1)) && Integer.parseInt(ligne.substring(1, 2)) < 9 && Integer.parseInt(ligne.substring(1, 2)) > 0) {
            var1 = ligne.charAt(1) - '0' - 1;
        } else {
            return false;
        }
        if (Character.isLetter(ligne.charAt(2)) && (int) ligne.charAt(2) - (int)
                'a' >= 0 && (int) ligne.charAt(2) - (int)
                'a' <= 7) {
            var2 = (int) ligne.charAt(2) - (int)
                    'a';
        } else {
            return false;
        }
        if (Character.isDigit(ligne.charAt(3)) && Integer.parseInt(ligne.substring(3)) < 9 && Integer.parseInt(ligne.substring(3)) > 0) {
            var3 = ligne.charAt(3) - '0' - 1;
        } else {
            return false;
        }

        Coordonnées départ = new Coordonnées(var, var1);
        Coordonnées arrivée = new Coordonnées(var2, var3);

        coup.setCoord(départ, arrivée);

        if (p.getPièce(coup.getDépart().getX(), coup.getDépart().getY()) == null) {
            return false;
        }

        if (!p.getPièce(coup.getDépart().getX(), coup.getDépart().getY()).getCouleur().equals(courant)) {
            return false;
        }

        return true;
    }

    public void setFinie(boolean finie) {
        this.finie = finie;
    }
}