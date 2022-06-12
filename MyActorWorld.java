import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class MyActorWorld extends ActorWorld{
    ChessRunner.DLLHNode tail;
    ChessRunner main2;
    int turn = 0;
    public MyActorWorld(){

    }

    public MyActorWorld(Grid<Actor> grid){
        super(grid);
    }

    public void step(){
        ChessRunner.DLLHNode tail = main2.tail;
        turn = main2.totalTurn;
        ChessRunner.DLLHNode current = tail;
        /*
        public DLLHNode next, prev;
        public boolean eaten;
        public Location before;
        public Location now;
        public Actor Cpiece;
         */

        if(turn%2 == 0 && turn>0){//Critter is last in Llist
            ChessCritter temp;
            ChessBug temp2;

            for (int i = 0; i < 2; i++){

                current.Cpiece.removeSelfFromGrid();
                current.Cpiece.putSelfInGrid(main2.gr, current.before);
                if (i==1){
                    
                    ((ChessBug)(current.Cpiece)).turn--;
                    if (current.eaten){
                        temp = (ChessCritter)main2.allDead.pop();
                        main2.aliveC.add(temp);
                        main2.all.add(temp);
                        temp.putSelfInGrid(main2.gr, current.now);

                    }
                }else if (i==0){
                    ((ChessCritter)current.Cpiece).turn--;
                    if (current.eaten){
                        temp2 = (ChessBug)main2.allDead.pop();
                        main2.aliveB.add(temp2);
                        main2.all.add(temp2);
                        temp2.putSelfInGrid(main2.gr, current.now);

                    }
                }
                current = current.prev;
                main2.totalTurn--;

            }
            main2.tail = current;

        }else if(turn%2==1 && turn>1){//Bug is last in LList
            ChessCritter temp;
            ChessBug temp2;
            for (int i = 0; i < 2; i++){

                current.Cpiece.removeSelfFromGrid();
                current.Cpiece.putSelfInGrid(main2.gr, current.before);
                if (i==1){
                    ((ChessCritter)(current.Cpiece)).turn--;
                    if (current.eaten){
                        temp2 = (ChessBug)main2.allDead.pop();
                        main2.aliveB.add(temp2);
                        main2.all.add(temp2);
                        temp2.putSelfInGrid(main2.gr, current.now);

                    }
                }else if (i==0){
                    ((ChessBug)current.Cpiece).turn--;
                    if (current.eaten){
                        temp = (ChessCritter)main2.allDead.pop();
                        main2.aliveC.add(temp);
                        main2.all.add(temp);
                        temp.putSelfInGrid(main2.gr, current.now);

                    }
                }
                current = current.prev;
                main2.totalTurn--;
            }
            main2.tail = current;

        }
    }

    public void setMain(ChessRunner main){
        main2 = main;
        tail = main.tail;
    }
}
