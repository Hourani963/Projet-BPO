package piece;


import partie.Coordonnées;
import partie.Coup;
import partie.Partie;
import table.Couleur;
import table.IPièce;
import table.Plateau;

import java.util.ArrayList;

public class Roi extends Pièce { //un roi est une pièce


    public Roi(Coordonnées coordonnées, Couleur c) {
        super( c, coordonnées,"r");
    }

    /**
     * Permet de savoir si le coup est légal
     * @param coord les coordonnées où la pièce va être placée
     * @param p le plateau
     * @return retourne true si le coup est légal
     */
    public boolean coupLegal(Coordonnées coord, Plateau p) {

    boolean ok = ((getCoordonnées().getX() - coord.getX() <= 1 && getCoordonnées().getY() - coord.getY() <= 1) && (getCoordonnées().getY() - coord.getX() >= -1 && getCoordonnées().getY() - coord.getY() >= -1)) &&
            ( (coord.getX() - getCoordonnées().getX() <= 1 && coord.getY() - getCoordonnées().getY() <= 1) && (coord.getX() - getCoordonnées().getX() >= -1 && coord.getY() - getCoordonnées().getY() >= -1));
        if(!(coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() <= 7 && coord.getY() <=7)){

            return false;
        }

        if(p.caseOccupée(coord) && p.getPièce(coord).getCouleur() == this.getCouleur()){
            return false;
        }else if(p.caseOccupée(coord) && p.getPièce(coord).getCouleur() != this.getCouleur()){
            //une pièce va être mangée
        }

        if(ok){
            return true;
        }else{
             return false;
        }
    }

    /**
     * Permet de savoir si la pièce craint l'echec
     * @return True si la pièce est un roi
     */
    public boolean craintEchec(){
        return true;
    }

    /**
     * Regarde si le roi est en échec
     * @param p le plateau
     * @return true si le Roi est en echec
     */
    public boolean enEchec(Plateau p){
        ArrayList<IPièce> pièces = p.pièces();
        for(IPièce pièce : pièces){
            if(pièce.coupLegal(this.getCoordonnées(),p)){
                if(!this.getCouleur().equals(pièce.getCouleur())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifie si le roi est en enchec et Mat
     * @param p le plateau
     * @param partie la partie
     * @return true si le roi est en échec, sinon false
     */
    public boolean enEchecEtMat(Plateau p, Partie partie){
        ArrayList<IPièce> pièces = p.pièces(getCouleur());
        for(int pièce = 0; pièce< pièces.size();pièce++){
            ArrayList<Coup> coups = pièces.get(pièce).coupsPossibles(p);
            for(Coup coup : coups){
                if(p.jouerEchec(coup,partie)){
                    if(!this.enEchec(p)){
                        p.annulerCoup(pièces.get(pièce));
                        return false;
                    }
                }
            }
            p.annulerCoup(pièces.get(pièce));
        }
        partie.setFinie(true);
        return true;
    }


    /**
     * Donne la liste des coups possibles de cette pièce
     * @param p le plateau
     * @return la liste des coups possibles
     */
    public ArrayList<Coup> coupsPossibles(Plateau p){
        Coup coup = new Coup();
        ArrayList<Coup> coups = new ArrayList<>();

        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()-1,getCoordonnées().getY()+1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(coups.size(), coup);
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX(),getCoordonnées().getY()+1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()+1,getCoordonnées().getY()+1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()+1,getCoordonnées().getY()));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()+1,getCoordonnées().getY()-1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX(),getCoordonnées().getY()-1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()-1,getCoordonnées().getY()-1));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        coup.setCoord(getCoordonnées(),new Coordonnées(getCoordonnées().getX()-1,getCoordonnées().getY()));
        if(coupLegal(coup.getArrivée(),p)){
            coups.add(new Coup(coup.getDépart(),coup.getArrivée()));
        }
        return coups;
    }
}