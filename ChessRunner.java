import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.*;
import java.awt.Color;

public class ChessRunner{
    //maybe reading a custom DLLHNode LList and auto the chess
    public DLLHNode tail;
    public DLLHNode head;
    public Scanner in = new Scanner(System.in);
    public ArrayList<Actor> all = new ArrayList<>();
    public ArrayList<ChessBug> aliveB = new ArrayList<>();
    public ArrayList<ChessCritter> aliveC = new ArrayList<>();
    public Stack<Actor> allDead = new Stack<>();
    public BoundedGrid gr = new BoundedGrid(8,8);
    public Location currentBKingLocation;
    public ChessBug AlterKB;
    public Location currentCKingLocation;
    public ChessCritter AlterKC;
    public int turn = 0;
    public int totalTurn = 0;
    public static ChessRunner main;
    public String MOD;
    public MyActorWorld world;
    public String line = "\n===========================================================================================\n";
    public static void main(){
        main = new ChessRunner();
        //Mode: normal, testing, WIP...
        main.begin("testing");
    }

    public void begin(String mode){
        world = new MyActorWorld(gr);
        world.setMain(main);
        switch (mode){
            case "normal":
                putC(world,2,new Location(0, 0));      
                putC(world,2,new Location(0, 7));
                putC(world,3,new Location(0, 1));
                putC(world,3,new Location(0, 6));
                putC(world,4,new Location(0, 2));
                putC(world,5,new Location(0, 5));
                putC(world,5,new Location(0, 3));
                putC(world,6,new Location(0, 4));
                for (int i = 0; i<= 7; i++){
                    putC(world,1,new Location(1, i));
                }
                
                putB(world,2,new Location(7, 0));
                putB(world,2,new Location(7, 7));
                putB(world,3,new Location(7, 1));
                putB(world,3,new Location(7, 6));
                putB(world,4,new Location(7, 2));
                putB(world,4,new Location(7, 5));
                putB(world,5,new Location(7, 3));
                putB(world,6,new Location(7, 4));
                for (int i = 0; i<= 7; i++){
                    putB(world,1,new Location(6, i));
                }
                world.show();
                break;

            case "testing":
                /*
                putB(world,6,new Location(3,5));
                putC(world,6,new Location(3,7));
                putB(world,2,new Location(7,6));
                */
               putB(world,6,new Location(7,4));
               putB(world,5,new Location(7,3));
               putB(world,1,new Location(6,1));
               putB(world,1,new Location(6,2));
               putC(world,6,new Location(0,0));
               putC(world,5,new Location(4,1));
                world.show();
                break;

                //case
        }
        MOD = mode;
        all = gr.getOccupiedLocations();
        boolean end = false;
        //=======================================================
        while(!end){
            checkw:{
                boolean Check = check();
                ArrayList<Boolean> stalemateCheck = new ArrayList<>();
                if (!Check){
                    System.out.println(line);

                    if (turn == 0){
                        for (ChessBug B: aliveB){
                            if ((B.getMoveLocations()).isEmpty()){
                                stalemateCheck.add(true);
                            }else{
                                stalemateCheck.add(false);
                                break;
                            }
                        }
                        if (!stalemateCheck.contains(false)){
                            System.out.println(line);
                            System.out.println("This is a Stalemate Draw! Nobody won!");
                            System.out.println(line);
                            end = true;
                            break checkw;
                        }
                        System.out.println("It's the bug's turn. (Hover the mouse over the grid to see the location)");
                    }else{
                        for (ChessCritter C: aliveC){
                            
                            if ((C.getMoveLocations()).isEmpty()){
                                stalemateCheck.add(true);
                            }else{
                                stalemateCheck.add(false);
                                break;
                            }
                        }
                        if (!stalemateCheck.contains(false)){
                            System.out.println(line);
                            System.out.println("This is a Stalemate Draw! Nobody won!");
                            System.out.println(line);
                            end = true;
                            break checkw;
                        }

                        System.out.println("It's the critter's turn. (Hover the mouse over the grid to see the location)");
                    }

                    boolean confirmed = false;

                    while (!confirmed){
                        label:{
                            System.out.println("Which pieces do you want to move? (Type in a location: Row Column)");
                            Location current = new Location(-1,-1);
                            int x0 = 0;
                            if (turn == 0){
                                while(!gr.isValid(current) || gr.get(current)==null || gr.get(current) instanceof Critter){
                                    if (x0!=0){
                                        System.out.println("Please choose a valid BUG piece!");
                                    }
                                    x0++;
                                    String next = in.nextLine();
                                    if (next.equals("")){
                                        next = in.nextLine();
                                    }

                                    if (next.length()==3){
                                        String Srow = next.substring(0,1);
                                        String Scol = next.substring(2);
                                        if (isNumeric(Srow) && isNumeric(Scol)){
                                            int row = Integer.parseInt(Srow);
                                            int col = Integer.parseInt(Scol);
                                            current = new Location (row, col);
                                        }
                                    }
                                }
                            }else{
                                while(!gr.isValid(current) || gr.get(current)==null || gr.get(current) instanceof Bug){
                                    if (x0!=0){
                                        System.out.println("Please choose a valid CRITTER piece!");
                                    }
                                    x0++;
                                    String next = in.nextLine();
                                    if (next.equals("")){
                                        next = in.nextLine();
                                    }

                                    if (next.length()==3){
                                        String Srow = next.substring(0,1);
                                        String Scol = next.substring(2);
                                        if (isNumeric(Srow) && isNumeric(Scol)){
                                            int row = Integer.parseInt(Srow);
                                            int col = Integer.parseInt(Scol);
                                            current = new Location (row, col);
                                        }
                                    }
                                }
                            }
                            ArrayList<Location> moveL;
                            ChessBug curB = null;
                            ChessCritter curC = null;
                            if (turn ==0){
                                curB = (ChessBug) gr.get(current);
                                System.out.println("You selected " + curB + " at location: " + current);
                                moveL = curB.getMoveLocations();

                            }else{
                                curC = (ChessCritter) gr.get(current);
                                System.out.println("You selected " + curC + " at location: " + current);
                                moveL = curC.getMoveLocations();
                            }
                            System.out.print("\nPlease choose a location you want to move to");
                            System.out.print("(Type in a location: Row Column; OR choose location from typing INDEX)\n");
                            Location current2 = new Location(-1,-1);
                            int x = 0;
                            while (!gr.isValid(current) || gr.get(current)==null || !moveL.contains(current2)){
                                if (x!=0){
                                    System.out.println("Location is invalid! Please choose a new location from typing INDEX or pick a new valid location!");
                                }
                                x++;
                                String next = in.nextLine();
                                if (next.equals("")){
                                    next = in.nextLine();
                                }
                                

                                if (next.equalsIgnoreCase("INDEX")){
                                    if (moveL.size()!=0){
                                        System.out.print(1 + ": " + moveL.get(0));
                                    }else{
                                        System.out.println("This piece can't move any spaces!");
                                        confirmed = false;
                                        break;
                                    }
                                    for (int i = 1; i < moveL.size(); i++){
                                        System.out.print(" " + (i+1) + ": " + moveL.get(i));
                                    }
                                    System.out.print(", " + (moveL.size()+1) + ": choose another piece");
                                    int choice = 99;
                                    String temp = "";
                                    while (!(choice <= moveL.size()+1)){
                                        temp = in.nextLine();
                                        if (temp.equals("")){
                                            temp = in.nextLine();
                                        }
                                        temp = temp.substring(0,1);
                                        choice = Integer.parseInt(temp);
                                    }
                                    if (choice==moveL.size()+1){
                                        confirmed = false;
                                        break label;
                                    }else{
                                        current2 = moveL.get(choice-1);
                                        confirmed = true; 
                                    }
                                }else if (next.length()==3){
                                    
                                    String Srow = next.substring(0,1);
                                    String Scol = next.substring(2);
                                    if (isNumeric(Srow) && isNumeric(Scol)){
                                        int row = Integer.parseInt(Srow);
                                        int col = Integer.parseInt(Scol);
                                        current2 = new Location (row, col);
                                    }
                                    confirmed = true;
                                }

                            }
                            if (confirmed){
                                System.out.println("You selected the location: " + current2);
                                System.out.println("Do you confirm (Y/N)");
                                String next2 = in.next();
                                if (next2.equalsIgnoreCase("Y") || next2.equalsIgnoreCase("Yes")){
                                    confirmed = true;

                                    if (turn == 0){
                                        curB.turn++;
                                        totalTurn++;
                                        if (tail==null){
                                            head=null;
                                        }
                                        if (head == null){
                                            head = new DLLHNode(null, curB.getLocation(), current2, curB);
                                            tail = head;
                                        }else{
                                            tail = new DLLHNode(tail, curB.getLocation(), current2, curB);
                                            tail.prev.next = tail;
                                        }
                                        Actor temp = curB.move(current2);
                                        if (temp!=null){
                                            tail.eaten = true;
                                            all.remove(temp);
                                            aliveC.remove(temp);
                                            allDead.add(temp);
                                        }
                                        if (curB.piece==6){
                                            currentBKingLocation = curB.getLocation();
                                        }
                                    }else{
                                        curC.turn++;
                                        totalTurn++;
                                        if (tail==null){
                                            head=null;
                                        }
                                        if (head == null){
                                            head = new DLLHNode(null, curC.getLocation(), current2, curC);
                                            tail = head;
                                        }else{
                                            tail = new DLLHNode(tail, curC.getLocation(), current2, curC);
                                            tail.prev.next = tail;
                                        }
                                        Actor temp = curC.move(current2);
                                        if (temp!=null){
                                            tail.eaten = true;
                                            all.remove(temp);
                                            aliveB.remove(temp);
                                            allDead.add(temp);
                                        }
                                        if (curC.piece==6){
                                            currentCKingLocation = curC.getLocation();
                                        }

                                    }

                                    boolean Same = false;
                                    if (totalTurn>=6){
                                        DLLHNode currentT = tail;
                                        DLLHNode currentT2 = tail.prev;
                                        Actor same = currentT.Cpiece;
                                        Actor same2 = currentT2.Cpiece;
                                        Location begin = currentT.before;
                                        Location saigo = currentT.now;
                                        Location begin2 = currentT2.before;
                                        Location saigo2 = currentT2.now;

                                        DLLHNode compare = currentT2.prev.prev.prev;
                                        DLLHNode compare2 = compare.prev;
                                        Actor acto = compare.Cpiece;
                                        Actor acto2 = compare2.Cpiece;
                                        Location beginning = compare.before;
                                        Location ender = compare.now;
                                        Location beginning2 = compare2.before;
                                        Location ender2 = compare2.now;

                                        if (same.equals(acto) && same2.equals(acto2)){
                                            if (begin.equals(beginning) && saigo.equals(ender)){
                                                if (begin2.equals(beginning2) && saigo2.equals(ender2)){
                                                    Same = true;
                                                }
                                            }
                                        }

                                        if (Same){
                                            end = true;
                                            System.out.println(line);
                                            System.out.println("Repeating 3 rounds of move. Stalemate Draw!");
                                            System.out.println(line);
                                            break checkw;
                                        }
                                    }
                                    if (totalTurn >= 50){
                                        DLLHNode currentFinB = tail;
                                        boolean staled = true;
                                        for (int i = 0; i < 50; i++){
                                            if (currentFinB.Cpiece != AlterKB){
                                                staled = false;
                                            }
                                            currentFinB = currentFinB.prev;

                                        }
                                        if (!staled){
                                            end = true;
                                            System.out.println(line);
                                            System.out.println("King's Repeating 50 rounds of move. Stalemate Draw!");
                                            System.out.println(line);
                                            break checkw;
                                        }

                                        DLLHNode currentFinC = tail;
                                        staled = true;
                                        for (int i = 0; i < 50; i++){
                                            if (currentFinC.Cpiece != AlterKC){
                                                staled = false;
                                            }
                                            currentFinC = currentFinC.prev;

                                        }
                                        if (!staled){
                                            end = true;
                                            System.out.println(line);
                                            System.out.println("King's Repeating 50 rounds of move. Stalemate Draw!");
                                            System.out.println(line);
                                            break checkw;
                                        }
                                    }
                                    world.show();
                                }else{
                                    confirmed = false;
                                }
                            }
                        }

                    }
                    
                    System.out.println("Dead: " + allDead);
                    if (turn == 0){
                        turn++;
                    }else{
                        turn = 0;
                    }
                }else{
                    //You checked ;)
                    System.out.println(line);
                    if (turn == 0){
                        System.out.println("Bug is being checked!!! Protect the Bug King");
                    }else{
                        System.out.println("Critter is being checked!!! Protect the Critter King");
                    }
                    System.out.println();

                    ChessBug kingB = (ChessBug) gr.get(currentBKingLocation);
                    ChessCritter kingC = (ChessCritter) gr.get(currentCKingLocation);
                    Set<ChessBug> totalBatk = kingC.find(currentCKingLocation);
                    Set<ChessCritter> totalCatk = kingB.find(currentBKingLocation);
                    ArrayList<Location> canMove = new ArrayList<>();//Only for the king
                    ArrayList<Location> ProtNAtk = new ArrayList<>();//restrict where the pieces can move so that they have to protect the king or kill the atkers
                    Location a = null;
                    if (turn == 0){
                        a = currentBKingLocation;
                    }else{
                        a = currentCKingLocation;
                    }

                    ArrayList<Location> z = gr.getEmptyAdjacentLocations(a);
                    for (Location curren: z){
                        if (gr.isValid(curren)){
                            if (turn == 0){
                                Set<ChessCritter> temp = kingB.find(curren);
                                
                                if (temp.isEmpty()){
                                    canMove.add(curren);
                                }else{
                                    totalCatk.addAll(temp);
                                }

                            }else{
                                Set<ChessBug> temp = kingC.find(curren);
                                
                                if (temp.isEmpty()){
                                    canMove.add(curren);
                                }else{
                                    totalBatk.addAll(temp);
                                }
                            }
                        }
                    }
                    
                    ArrayList<Location> z2 = gr.getOccupiedAdjacentLocations(a);
                    for (Location curren: z2){
                        if (gr.isValid(curren)){
                            if (turn == 0 && gr.get(curren) instanceof Critter){
                                ChessCritter temp = (ChessCritter)gr.get(curren);
                                if (!temp.reinforce(curren)){
                                    canMove.add(curren);
                                }
                            }else if (turn == 1 && gr.get(curren) instanceof Bug){
                                ChessBug temp = (ChessBug)gr.get(curren);
                                if (!temp.reinforce(curren)){
                                    canMove.add(curren);
                                }
                            }
                        }
                    }
                    
                    if (turn == 0 && totalCatk.size()>=1 && canMove.isEmpty()){
                        //gg
                        System.out.println("Checkmate! Critters Won!");
                        end = true;
                        break;
                    }else if (turn == 1 && totalBatk.size()>=1 && canMove.isEmpty()){
                        //gg
                        System.out.println("Checkmate! Bugs Won!");
                        end = true;
                        break;
                    }

                    Set<ChessBug> Ballies = new HashSet<ChessBug>();
                    Set<ChessCritter> Callies = new HashSet<ChessCritter>();
                    if (turn == 0){
                        for (ChessCritter i: totalCatk){
                            Location i2 = i.getLocation();
                            int angle = i2.getDirectionToward(currentBKingLocation);
                            if (i.piece!=3){
                                while (!currentBKingLocation.equals(i2)){
                                    
                                    Ballies.addAll(i.find(i2));
                                    ProtNAtk.add(i2);
                                    i2 = i2.getAdjacentLocation(angle);
                                }
                            }else{
                                
                                Ballies.addAll(i.find(i2));
                                ProtNAtk.add(i2);
                            }
                        }
                        Ballies.remove(AlterKB);
                    }else{
                        for (ChessBug i: totalBatk){
                            Location i2 = i.getLocation();
                            int angle = i2.getDirectionToward(currentCKingLocation);
                            if (i.piece!=3){
                                while (!currentCKingLocation.equals(i2.getAdjacentLocation(angle))){
                                    Callies.addAll(i.find(i2));
                                    ProtNAtk.add(i2);
                                    i2 = i2.getAdjacentLocation(angle);
                                }
                            }else{
                                Callies.addAll(i.find(i2));
                                ProtNAtk.add(i2);
                            }
                        }
                        Callies.remove(AlterKC);
                    }

                    boolean confirmed = false;
                    while (!confirmed){
                        label:{
                            System.out.println("Which pieces do you want to move?");
                            Location current = new Location(-1,-1);
                            int x = 0;
                            ArrayList<Location> moveL = null;
                            ChessBug iFinB = null;
                            ChessCritter iFinC = null;

                            if (turn == 0){
                                while (!gr.isValid(current) || gr.get(current)==null || (!Ballies.contains(gr.get(current))
                                    &&(!(((ChessBug)gr.get(current)).piece==6)))){
                                    if (x!=0){
                                        System.out.println("Location is invalid! Please choose a new Bug piece!");
                                    }
                                    x++;
                                    int i = 1;
                                    boolean print = false;
                                    for (ChessBug i2: Ballies){
                                        if (i!=Ballies.size()){
                                            System.out.print(i + ": " + i2 + ", ");
                                        }else{
                                            System.out.print(i + ": " + i2);
                                            if (!canMove.isEmpty()){
                                                System.out.print(", " + (i+1) + ": " + kingB);
                                                print = true;
                                            }
                                        }
                                        i++;
                                    }
                                    if (!canMove.isEmpty() && !print){
                                        System.out.print("1: " + kingB);
                                    }
                                    int choice = 99;
                                    String temp = "";
                                    while (!(choice <= Ballies.size()+1)){
                                        temp = in.nextLine();
                                        if (temp.equals("")){
                                            temp = in.nextLine();
                                        }
                                        temp = temp.substring(0,1);
                                        choice = Integer.parseInt(temp);
                                    }
                                    if (choice==Ballies.size()+1){
                                        current = currentBKingLocation;
                                        moveL = canMove;
                                        iFinB = (ChessBug) gr.get(current);
                                    }else{
                                        int j = 1;
                                        for (ChessBug i2: Ballies){
                                            if (j==choice){
                                                iFinB = i2;
                                                
                                                break;
                                            }
                                            j++;
                                        }
                                        current = iFinB.getLocation();
                                        moveL = iFinB.getMoveLocations();
                                    }

                                }
                            }else{
                                while (!gr.isValid(current) || gr.get(current)==null || (!Callies.contains(gr.get(current))) 
                                && (!(((ChessCritter)gr.get(current)).piece==6))){
                                    if (x!=0){
                                        System.out.println("Location is invalid! Please choose a new Critter piece!");
                                    }
                                    x++;
                                    int i = 1;
                                    boolean print = false;
                                    for (ChessCritter i2: Callies){
                                        if (i!=Callies.size()){
                                            System.out.print(i + ": " + i2 + "(" + i2.getLocation() + "), ");
                                        }else{
                                            System.out.print(i + ": " + i2 + "(" + i2.getLocation() + ")");
                                            if (!canMove.isEmpty()){
                                                System.out.print(", " + (i+1) + ": " + kingC);
                                                print = true;
                                            }
                                        }
                                        i++;
                                    }
                                    if (!canMove.isEmpty() && !print){
                                        System.out.print("1: " + kingC);
                                    }
                                    int choice = 99;
                                    String temp = "";
                                    while (!(choice <= Callies.size()+1)){
                                        temp = in.nextLine();
                                        if (temp.equals("")){
                                            temp = in.nextLine();
                                        }
                                        temp = temp.substring(0,1);
                                        choice = Integer.parseInt(temp);
                                    }

                                    if (choice==Callies.size()+1){
                                        current = currentCKingLocation;
                                        moveL = canMove;
                                        iFinC = (ChessCritter) gr.get(current);

                                    }else{
                                        int j = 1;
                                        for (ChessCritter i2: Callies){
                                            if (j==choice){
                                                iFinC = i2;
                                                break;
                                            }
                                        }
                                        j++;
                                        current = iFinC.getLocation();
                                        moveL = iFinC.getMoveLocations();
                                    }

                                }
                            }
                            
                            if (turn ==0){
                                System.out.println("You selected " + iFinB + " at location: " + iFinB.getLocation());

                            }else{
                                System.out.println("You selected " + iFinC + " at location: " + iFinC.getLocation());
                            }
                            System.out.print("\nPlease choose a location you want to move to");
                            System.out.print("(Type in a location: Row Column; OR choose location from typing INDEX)\n");
                            Location current2 = new Location(-1,-1);
                            int x1 = 0;
                            while (!gr.isValid(current) || gr.get(current)==null || !moveL.contains(current2)){
                                if (x1!=0){
                                    System.out.println("Location is invalid! Please choose a new location from typing INDEX or pick a new valid location!");
                                }
                                x1++;
                                String next = in.nextLine();
                                if (next.equals("")){
                                    next = in.nextLine();
                                }

                                if (next.equalsIgnoreCase("INDEX")){
                                    //only filter the pieces that are not the king because the king move is already artifically chosen.
                                    if ((turn==0&&iFinB.piece!=6) || (turn ==1 && iFinC.piece!=6)){
                                        moveL.retainAll(ProtNAtk);
                                    }
                                    
                                    if (moveL.size()!=0){
                                        System.out.print(1 + ": " + moveL.get(0));
                                    }else{
                                        System.out.println("This piece can't move any spaces!");
                                        confirmed = false;
                                        break;
                                    }
                                    for (int i = 1; i < moveL.size(); i++){
                                        System.out.print(" " + (i+1) + ": " + moveL.get(i));
                                    }
                                    System.out.print(", " + (moveL.size()+1) + ": choose another piece");
                                    int choice = 99;
                                    String temp = "";
                                    while (!(choice <= moveL.size()+1)){
                                        temp = in.nextLine();
                                        if (temp.equals("")){
                                            temp = in.nextLine();
                                        }
                                        temp = temp.substring(0,1);
                                        choice = Integer.parseInt(temp);
                                    }
                                    if (choice==moveL.size()+1){
                                        confirmed = false;
                                        break label;
                                    }else{
                                        current2 = moveL.get(choice-1);
                                        confirmed = true; 
                                    }
                                }else if (next.length()==3){
                                    
                                    String Srow = next.substring(0,1);
                                    String Scol = next.substring(2);
                                    if (isNumeric(Srow) && isNumeric(Scol)){
                                        int row = Integer.parseInt(Srow);
                                        int col = Integer.parseInt(Scol);
                                        current2 = new Location (row, col);
                                    }
                                    confirmed = true; 
                                }

                            }
                            if (confirmed){
                                System.out.println("You selected the location: " + current2);
                                System.out.println("Do you confirm (Y/N)");
                                String next2 = in.next();
                                if (next2==""){
                                    next2 = in.next();
                                }
                                if (next2.equalsIgnoreCase("Y") || next2.equalsIgnoreCase("Yes")){
                                    confirmed = true;

                                    if (turn == 0){
                                        iFinB.turn++;
                                        if (head == null){
                                            head = new DLLHNode(null, iFinB.getLocation(), current2, iFinB);
                                            tail = head;
                                        }else{
                                            tail = new DLLHNode(tail, iFinB.getLocation(), current2, iFinB);
                                            tail.prev.next = tail;
                                        }
                                        Actor temp = iFinB.move(current2);
                                        if (temp!=null){
                                            tail.eaten = true;
                                            all.remove(temp);
                                            aliveC.remove(temp);
                                            allDead.add(temp);
                                        }
                                        if (iFinB.piece==6){
                                            currentBKingLocation = iFinB.getLocation();
                                        }

                                    }else{
                                        iFinC.turn++;
                                        if (head == null){
                                            head = new DLLHNode(null, iFinC.getLocation(), current2, iFinC);
                                            tail = head;
                                        }else{
                                            tail = new DLLHNode(tail, iFinC.getLocation(), current2, iFinC);
                                            tail.prev.next = tail;
                                        }
                                        Actor temp = iFinC.move(current2);
                                        if (temp!=null){
                                            tail.eaten = true;
                                            all.remove(temp);
                                            aliveB.remove(temp);
                                            allDead.add(temp);
                                        }
                                        if (iFinC.piece==6){
                                            currentCKingLocation = iFinC.getLocation();
                                        }

                                    }
                                    world.show();
                                }else{
                                    confirmed = false;
                                }
                            }
                        }

                    }
                    System.out.println("Dead: " + allDead);
                    if (turn == 0){
                        turn++;
                    }else{
                        turn = 0;
                    }

                }
                //}
            }
        }
    }

    public boolean check(){
        if (AlterKB.getLocation()==null || AlterKC.getLocation()==null){
            System.out.println("Game ended!");
            System.exit(0);
        }
        ChessBug kingB = (ChessBug) gr.get(currentBKingLocation);
        ChessCritter kingC = (ChessCritter) gr.get(currentCKingLocation);
        if (turn == 0){
            return kingB.check(currentBKingLocation);
        }else{
            return kingC.check(currentCKingLocation);
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
    
    public void putB(MyActorWorld world, int piece, Location a){
        ChessBug zero = new ChessBug(piece);
        world.add(a, zero);
        aliveB.add(zero);
        if (piece==6){
            AlterKB = zero;
            currentBKingLocation = a;
        }
    }
    
    public void putC(MyActorWorld world, int piece, Location a){
        ChessCritter one = new ChessCritter(piece);
        world.add(a, one);
        aliveC.add(one);
        if (piece==6){
            AlterKC = one;
            currentCKingLocation = a;
        }
    }

    public class DLLHNode{
        public DLLHNode next, prev;
        public boolean eaten;
        public Location before;
        public Location now;
        public Actor Cpiece;

        public DLLHNode(){
            eaten = false;
            before = null;
            now = null;
            prev = null;
            next = null;
            Cpiece = null;
        }

        public DLLHNode(DLLHNode a, Location d, Location e, Actor current){
            before = d;
            now = e;
            prev = a;
            Cpiece = current;
        }

        public String toString(){
            return "before: " + before + "\tnow: " + now + "\tActor: " + Cpiece + "\teaten: " + eaten;
        }

        public String toStringLinked(){
            if (next==null){
                return "before: " + before + "\tnow: " + now + "\tActor: " + Cpiece + "\teaten: " + eaten + "\n";
            }else{
                return "before: " + before + "\tnow: " + now + "\tActor: " + Cpiece + "\teaten: " + eaten + "\n" + next.toStringLinked();
            }
        }

    }
}


