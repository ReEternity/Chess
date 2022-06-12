import info.gridworld.actor.Bug;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class ChessBug extends Bug{
    public int piece;
    // 1 = pawn; 2 = rook; 3 = knight; 4 = bishop; 5 = queen; 6 = king
    public int turn;
    public boolean notCastled = true;
    public ChessBug(int p){
        piece = p;
        piece = p;
        switch (p) {
            case 1:  setColor(Color.WHITE);
                break;
            case 2:  setColor(Color.GREEN);
                break;
            case 3:  setColor(Color.BLUE);
                break;
            case 4:  setColor(Color.MAGENTA);
                break;
            case 5:  setColor(Color.ORANGE);
                break;
            case 6:  setColor(Color.RED);
                break;
        }
    }

    public ArrayList<Location> getMoveLocations(){
        ArrayList<Location> temp = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        if (gr==null) return null;
        switch (piece) {
            case 1:  
                if (turn == 0){
                    //The 2 starting points
                    Location next = this.getLocation().getAdjacentLocation(this.getDirection());
                    if (gr.get(next)==null){
                        temp.add(next);
                        Location next2 = next.getAdjacentLocation(this.getDirection());
                        if (gr.get(next2)==null){
                            temp.add(next2);
                        }
                    }

                    //Eatable spots left or right
                    next = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()-1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }

                    next = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()+1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }

                    //En passant
                    next = new Location(this.getLocation().getRow(), this.getLocation().getCol()+1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(new Location(this.getLocation().getRow()-1, this.getLocation().getCol()+1));
                    }

                    next = new Location(this.getLocation().getRow(), this.getLocation().getCol()-1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(new Location(this.getLocation().getRow()-1, this.getLocation().getCol()-1));
                    }

                }else{
                    //Moving forward
                    Location next = this.getLocation().getAdjacentLocation(this.getDirection());
                    if (gr.get(next)==null){
                        temp.add(next);
                    }

                    //Eatable spots left or right
                    next = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()-1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }

                    next = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()+1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }

                    //En passant
                    next = new Location(this.getLocation().getRow(), this.getLocation().getCol()+1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(new Location(this.getLocation().getRow()-1, this.getLocation().getCol()+1));
                    }

                    next = new Location(this.getLocation().getRow(), this.getLocation().getCol()-1);
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(new Location(this.getLocation().getRow()-1, this.getLocation().getCol()-1));
                    }

                }
                break;

            case 2:
                int ang = 0;
                for (int i = 0; i < 4; i++){
                    Location next = this.getLocation().getAdjacentLocation(ang);
                    while (gr.isValid(next) && gr.get(next)==null){
                        temp.add(next);
                        next = next.getAdjacentLocation(ang);
                    }
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }
                    ang += 90;

                }
                break;

            case 3:
                Location current = new Location(this.getLocation().getRow()-2, this.getLocation().getCol()+1);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()+2);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()+1, this.getLocation().getCol()+2);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()+2, this.getLocation().getCol()+1);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()+2, this.getLocation().getCol()-1);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()+1, this.getLocation().getCol()-2);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()-1, this.getLocation().getCol()-2);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }

                current = new Location(this.getLocation().getRow()-2, this.getLocation().getCol()-1);
                if (gr.isValid(current) && (gr.get(current) instanceof Critter || gr.get(current) == null)){
                    temp.add(current);
                }
                break;

            case 4:
                int ang1 = 45;
                for (int i = 0; i < 4; i++){
                    Location next = this.getLocation().getAdjacentLocation(ang1);
                    while (gr.isValid(next) && gr.get(next)==null){
                        temp.add(next);
                        next = next.getAdjacentLocation(ang1);
                    }
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }
                    ang1+=90;
                }
                break;

            case 5:  
                int ang2 = 0;
                for (int i = 0; i < 8; i++){
                    Location next = this.getLocation().getAdjacentLocation(ang2);
                    while (gr.isValid(next) && gr.get(next)==null){
                        temp.add(next);
                        next = next.getAdjacentLocation(ang2);
                    }
                    if (gr.isValid(next) && gr.get(next) instanceof Critter){
                        temp.add(next);
                    }
                    ang2+=45;
                }
                break;

            case 6:
                ArrayList<Location> z = gr.getEmptyAdjacentLocations(getLocation());
                for (int i = 0; i < z.size(); i++){
                    Location curren = z.get(i);
                    if (gr.isValid(curren)){
                        ArrayList<Boolean> check1 = checkDiag(curren);

                        ArrayList<Boolean> check2 = checkCross(curren);

                        ArrayList<Boolean> check3 = checkK(curren);

                        if (!check1.contains(true) && !check2.contains(true) && !check3.contains(true)){
                            temp.add(curren);
                        }
                    }
                }
                //Castle
                if (this.turn == 0){
                    ChessBug rook1 = (ChessBug) gr.get(new Location(7,0));
                    if (rook1 != null && rook1.turn == 0){
                        Location between = rook1.getLocation().getAdjacentLocation(90);
                        while (gr.isValid(between) && gr.get(between)==null){
                            between=between.getAdjacentLocation(90);
                        }
                        if (gr.isValid(between) && gr.get(between)==this){
                            temp.add(new Location(7,2));
                        }
                    }

                    ChessBug rook2 = (ChessBug) gr.get(new Location(7,7));
                    if (rook2 != null && rook2.turn == 0){
                        Location between = rook2.getLocation().getAdjacentLocation(270);
                        while (gr.isValid(between) && gr.get(between)==null){
                            between=between.getAdjacentLocation(270);
                        }
                        if (gr.isValid(between) && gr.get(between)==this){
                            temp.add(new Location(7,6));
                        }
                    }
                }

                break;
        }
        return temp;
    }

    public boolean check(Location a){
        Grid<Actor> gr = getGrid();
        if (gr == null)return false;
        ArrayList<Boolean> check1 = checkDiag(a);
        ArrayList<Boolean> check2 = checkCross(a);
        ArrayList<Boolean> check3 = checkK(a);

        if (!check1.contains(true) && !check2.contains(true) && !check3.contains(true)){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Boolean> checkDiag(Location a){
        ArrayList<Boolean> z = new ArrayList<>();
        Grid<Actor> gr = getGrid();
        int ang = 45;
        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            int space = 0;
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);
                space++;
            }
            if (gr.isValid(next) && gr.get(next) instanceof Critter){
                if ((i==0 || i == 3) && space==0 && gr.isValid(next)){
                    ChessCritter temp = (ChessCritter)gr.get(next);
                    if (temp.piece==1 || temp.piece == 5 || temp.piece == 4){
                        z.add(true);
                    }else{
                        z.add(false);
                    }
                }else{
                    ChessCritter temp = (ChessCritter)gr.get(next);
                    if (temp.piece == 5 || temp.piece == 4){
                        z.add(true);
                    }else{
                        z.add(false);
                    }
                }
            }else{
                z.add(false);
            }
            ang += 90;
        }
        return z;
    }

    public ArrayList<Boolean> checkCross(Location a){
        ArrayList<Boolean> z = new ArrayList<>();
        Grid<Actor> gr = getGrid();
        int ang = 0;
        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);
            }
            if (gr.isValid(next) && gr.get(next) instanceof Critter){
                ChessCritter temp = (ChessCritter)gr.get(next);
                if (temp.piece == 5 || temp.piece == 2){
                    z.add(true);
                }else{
                    z.add(false);
                }

            }else{
                z.add(false);
            }
            ang += 90;
        }
        return z;
    }

    public ArrayList<Boolean> checkK(Location a){
        ArrayList<Boolean> z = new ArrayList<>();
        Grid<Actor> gr = getGrid();
        ArrayList<Location> z1 = new ArrayList<>();
        z1.add(new Location(a.getRow()-2, a.getCol()+1));
        z1.add(new Location(a.getRow()-1, a.getCol()+2));
        z1.add(new Location(a.getRow()+1, a.getCol()+2));
        z1.add(new Location(a.getRow()+2, a.getCol()+1));
        z1.add(new Location(a.getRow()+2, a.getCol()-1));
        z1.add(new Location(a.getRow()+1, a.getCol()-2));
        z1.add(new Location(a.getRow()-1, a.getCol()-2));
        z1.add(new Location(a.getRow()-2, a.getCol()-1));
        for (Location z2: z1){
            if (gr.isValid(z2) && gr.get(z2) instanceof Critter){
                ChessCritter y = (ChessCritter) gr.get(z2);
                if (y.piece==3){
                    z.add(true);
                }else{
                    z.add(false);
                }
            }else{
                z.add(false);
            }
        }
        return z;
    }

    public Set<ChessCritter> find(Location a){
        Grid<Actor> gr = getGrid();
        if (gr == null)return null;
        Set<ChessCritter> total = new HashSet<>();
        Set<ChessCritter> check1 = findDiag(a);
        Set<ChessCritter> check2 = findCross(a);
        Set<ChessCritter> check3 = findK(a);

        total.addAll(check1);
        total.addAll(check2);
        total.addAll(check3);
        return total;
    }

    public Set<ChessCritter> findDiag(Location a){
        Set<ChessCritter> z = new HashSet<>();
        Grid<Actor> gr = getGrid();
        int ang = 45;
        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            int space = 0;
            if (gr.isValid(next) && gr.get(next) instanceof Bug && ((ChessBug)gr.get(next)).piece==6){
                next = next.getAdjacentLocation(ang);
                space++;
            }
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);
                space++;

            }
            if (gr.isValid(next) && gr.get(next) instanceof Critter){
                if (space==0){
                    ChessCritter temp = (ChessCritter)gr.get(next);
                    //if there's nothing to attack, the pawn can't move diagonally
                    if ((i==0 || i == 3) && ((temp.piece==1 && gr.get(a)!=null) || temp.piece == 5 || temp.piece == 4)){
                        z.add(temp);
                    }else if (temp.piece==6){
                        z.add(temp);
                    }
                }else{
                    ChessCritter temp = (ChessCritter)gr.get(next);
                    if (temp.piece == 5 || temp.piece == 4){
                        z.add(temp);
                    }
                }
            }
            ang += 90;
        }
        return z;
    }

    public Set<ChessCritter> findCross(Location a){
        Set<ChessCritter> z = new HashSet<>();
        Grid<Actor> gr = getGrid();
        int ang = 0;

        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            int space = 0;
            if (gr.isValid(next) && gr.get(next) instanceof Bug && ((ChessBug)gr.get(next)).piece==6){
                next = next.getAdjacentLocation(ang);
                space++;
            }
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);

                space++;
            }
            if (gr.isValid(next) && gr.get(next) instanceof Critter){
                ChessCritter temp = (ChessCritter)gr.get(next);
                if (space==0){
                    if (i==0 && ((temp.piece==1 && gr.get(a)==null) || temp.piece == 5 || temp.piece == 2)){
                        z.add(temp);
                    }else if (temp.piece==6){
                        z.add(temp);
                    }
                }else if (i==0 && space == 1){
                    if ((temp.piece==1 && gr.get(a)==null && temp.turn==0) || temp.piece == 5 || temp.piece == 2){
                        z.add(temp);
                    }
                }else{
                    if (temp.piece == 5 || temp.piece == 2){
                        z.add(temp);
                    }

                }

            }
            ang += 90;
        }

        return z;
    }

    public Set<ChessCritter> findK(Location a){
        Set<ChessCritter> z = new HashSet<>();
        Grid<Actor> gr = getGrid();
        ArrayList<Location> z1 = new ArrayList<>();
        z1.add(new Location(a.getRow()-2, a.getCol()+1));
        z1.add(new Location(a.getRow()-1, a.getCol()+2));
        z1.add(new Location(a.getRow()+1, a.getCol()+2));
        z1.add(new Location(a.getRow()+2, a.getCol()+1));
        z1.add(new Location(a.getRow()+2, a.getCol()-1));
        z1.add(new Location(a.getRow()+1, a.getCol()-2));
        z1.add(new Location(a.getRow()-1, a.getCol()-2));
        z1.add(new Location(a.getRow()-2, a.getCol()-1));
        for (Location z2: z1){
            if (gr.isValid(z2) && gr.get(z2) instanceof Critter){
                ChessCritter y = (ChessCritter) gr.get(z2);
                if (y.piece==3){
                    z.add(y);
                }
            }
        }
        return z;
    }

    public boolean reinforce(Location a){
        Grid<Actor> gr = getGrid();
        if (gr == null)return false;
        boolean check1 = reinforceDiag(a);
        boolean check2 = reinforceCross(a);
        boolean check3 = reinforceK(a);

        return check1 || check2 || check3;
    }

    public boolean reinforceDiag(Location a){
        Grid<Actor> gr = getGrid();
        int ang = 45;
        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            int space = 0;
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);
                space++;
            }
            if (gr.isValid(next) && gr.get(next) instanceof Bug){
                if ((i==1 || i == 2) && space==0 && gr.isValid(next)){
                    ChessBug temp = (ChessBug)gr.get(next);
                    if (temp.piece==1 || temp.piece == 5 || temp.piece == 4){
                        return true;
                    }
                }else{
                    ChessBug temp = (ChessBug)gr.get(next);
                    if (temp.piece == 5 || temp.piece == 4){
                        return true;
                    }
                }
            }
            ang += 90;
        }
        return false;
    }

    public boolean reinforceCross(Location a){
        Grid<Actor> gr = getGrid();
        int ang = 0;
        for (int i = 0; i < 4; i++){
            Location next = a.getAdjacentLocation(ang);
            while (gr.isValid(next) && gr.get(next)==null){
                next = next.getAdjacentLocation(ang);
            }
            if (gr.isValid(next) && gr.get(next) instanceof Bug){
                ChessBug temp = (ChessBug)gr.get(next);
                if (temp.piece == 5 || temp.piece == 2){
                    return true;
                }

            }
            ang += 90;
        }
        return false;
    }

    public boolean reinforceK(Location a){
        Grid<Actor> gr = getGrid();
        ArrayList<Location> z1 = new ArrayList<>();
        z1.add(new Location(a.getRow()-2, a.getCol()+1));
        z1.add(new Location(a.getRow()-1, a.getCol()+2));
        z1.add(new Location(a.getRow()+1, a.getCol()+2));
        z1.add(new Location(a.getRow()+2, a.getCol()+1));
        z1.add(new Location(a.getRow()+2, a.getCol()-1));
        z1.add(new Location(a.getRow()+1, a.getCol()-2));
        z1.add(new Location(a.getRow()-1, a.getCol()-2));
        z1.add(new Location(a.getRow()-2, a.getCol()-1));
        for (Location z2: z1){
            if (gr.isValid(z2) && gr.get(z2) instanceof Bug){
                ChessBug y = (ChessBug) gr.get(z2);
                if (y.piece==3){
                    return true;
                }
            }
        }
        return false;
    }

    public Actor move(Location a){
        Grid<Actor> gr = getGrid();
        if (gr==null) return null;
        if (gr.get(a)!=null){
            Actor eaten = gr.get(a);
            gr.get(a).removeSelfFromGrid();
            this.removeSelfFromGrid();
            this.putSelfInGrid(gr, a);
            if (piece==1 && getLocation().getRow()==0){
                promotion();
            }
            return eaten;
        }else{
            this.removeSelfFromGrid();
            this.putSelfInGrid(gr, a);
            if (piece==1){
                //En passant
                ChessCritter b = (ChessCritter) gr.get(this.getLocation().getAdjacentLocation(this.getDirection()-180));
                if (b!=null && b.piece == 1){
                    b.removeSelfFromGrid();
                    return b;
                }
                if (getLocation().getRow()==0){
                    promotion();
                }
            }else if (piece==6 && notCastled){
                if (a.getRow() == 7 && a.getCol()==2){
                    notCastled = false;
                    ChessBug rook1 = (ChessBug) gr.get(new Location(7,0));
                    rook1.removeSelfFromGrid();
                    rook1.putSelfInGrid(gr, new Location(7, 3));
                }else if (a.getRow() == 7 && a.getCol()==6){
                    notCastled = false;
                    ChessBug rook2 = (ChessBug) gr.get(new Location(7,7));
                    rook2.removeSelfFromGrid();
                    rook2.putSelfInGrid(gr, new Location(7, 5));
                }
            }

        }
        return null;
    }

    public String toString(){
        String a = "Bug ";
        switch (piece) {
            case 1:  a+="Pawn";
                break;
            case 2:  a+="Rook";
                break;
            case 3:  a+="Knight";
                break;
            case 4:  a+="Bishop";
                break;
            case 5:  a+="Queen";
                break;
            case 6:  a+="King";
                break;
        }
        return a;
    }

    public void promotion(){
        System.out.println("*****************************************************************");
        System.out.println("Bug can be promoted: 1: Queen, 2: Bishop, 3: Knight, 4: Rook (Insert a number)");
        Scanner in = new Scanner(System.in);
        int i = 0;
        while (!(i<=4) || !(i >= 1)){
            i = in.nextInt();
        }
        switch (i) {
            case 1:  setColor(Color.ORANGE);
                piece = 5;
                break;
            case 2:  setColor(Color.MAGENTA);
                piece = 4;
                break;
            case 3:  setColor(Color.BLUE);
                piece = 3;
                break;
            case 4:  setColor(Color.GREEN);
                piece = 2;
                break;
        }
    }

    public void act(){
        return;
    }

}
