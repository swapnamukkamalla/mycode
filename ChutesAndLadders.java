/*NAME          :SWAPNA MUKKAMALLA
  DATE          :11-04-2019
  COURSE        :IT-516 DATA STRUCTURES AND ALGORITHMS
  HOMEWORK      :HW07
  TITLE         :Chutes And Ladders game
  DESCRIPTION   :This application takes the number of players from
                 the command prompt and startsoff the game and ends when
                 any player reaches  space 100 space or more and it also
                 displayes the interesting statistics on the over all game
                 includng the die rolls, ladders climbed and chutes slides*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChutesAndLadders {
    static int[] player1 = {0, 0, 0, 0, 0, 0};  //to store frequency of each die side  for player 1
    static int[] player2 = {0, 0, 0, 0, 0, 0};  //to store frequency of each die side  for player 2
    static int[] player3 = {0, 0, 0, 0, 0, 0};  //to store frequency of each die side  for player 3
    static int[] player4 = {0, 0, 0, 0, 0, 0};  //to store frequency of each die side  for player 4
    static int[] rollfreq = {1, 2, 3, 4, 5, 6}; // die sides
    static int[] totdieroll = {0, 0, 0, 0, 0, 0}; // to store frequency of die side for whole game
    static int[] p = new int[4];                  // die roll for each player
    static int[] climb = {0, 0, 0, 0};            // no of ladder climb for each player
    static int[] slid = {0, 0, 0, 0};             //no of chutes slid for each player

    static Map<Integer, Integer> map = new HashMap<Integer, Integer>(); // to store chutes and Ladders

    //Die roll for each player
    public static int rollIt(int player, int space) {
        int space2 = space;
        while (true) {

            int roll = StdRandom.uniform(1, 7); // random number from 1 to 6 including

            totdieroll[roll - 1] += 1;  //
            p[player - 1] = p[player - 1] + 1;  // number of die roll for each player
            if (player == 1) {
                player1[roll - 1] += 1;        // number for each player each  die side
            } else if (player == 2) {
                player2[roll - 1] += 1;
            } else if (player == 3) {
                player3[roll - 1] += 1;
            } else if (player == 4) {
                player4[roll - 1] += 1;
            }
            System.out.printf("Player %d, rolls a %d.\n", player, roll);
            //Win if space is 100 or more than that
            if (space2 + roll >= 100) {
                StdOut.println("          Moves to space 100");
                return 100;
            } else {
                space2 += roll;
                System.out.printf("          Moves to space %d\n", space2);
                if (space2 == 100) return 100;
                Integer jump = map.getOrDefault(space2, space2);
                //Checking if there is a ladder in the space
                if (space2 < jump) {
                    climb[player - 1] = climb[player - 1] + 1;   // number of climbs byeach player
                    System.out.printf("          Climbs ladder to space %d.\n", jump);
                    if (jump >= 100) return 100;
                    space2 = jump;
                } else if (space2 > jump) {  //checking if there is chute in the space
                    slid[player - 1] = slid[player - 1] + 1;  // number of slides by each player
                    System.out.printf("          Slides down chute to space %d.\n", jump);
                    space2 = jump;
                }
            }
            return space2;
        }
    }

    // method to check the most common die rolls
    public static int[] mostFrequent(int[] number, int[] rollfreq) {
        Arrays.sort(rollfreq);
        for (int x = 1; x < number.length; x++) {
            for (int l = x; l > 0 && (number[l] > number[l - 1]); l--) {
                int tempnumber = number[l];
                int temprollfreq = rollfreq[l];
                number[l] = number[l - 1];
                rollfreq[l] = rollfreq[l - 1];
                number[l - 1] = tempnumber;
                rollfreq[l - 1] = temprollfreq;
            }
        }
        return number;
    }

    //method to display the most common die rolls
    public static void displayHighest(int[] dis) {
        for (int z = 1; z < dis.length; z++) {
            int highest = dis[0];

            if (highest == dis[z]) {
                StdOut.print(" , " + rollfreq[z]);
            }
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        // Number of players from the command prompt
        int noofplayers = Integer.parseInt(args[0]);

        { // inserting all ladders and chutes in the game board
            map.put(1, 38);
            map.put(4, 14);
            map.put(9, 31);
            map.put(16, 6);
            map.put(28, 84);
            map.put(36, 44);
            map.put(40, 42);
            map.put(47, 26);
            map.put(49, 11);
            map.put(51, 67);
            map.put(56, 53);
            map.put(62, 19);
            map.put(64, 60);
            map.put(71, 91);
            map.put(80, 100);
            map.put(87, 24);
            map.put(93, 73);
            map.put(95, 75);
            map.put(98, 78);
        }

        int[] rank = new int[noofplayers];  //players rank
        int[] players = new int[noofplayers];  // to store space of the players

        //Initializing the space of each player at zero
        for (int j = 0; j < noofplayers; j++) {
            players[j] = 0;
            rank[j] = j + 1;
        }

        StdOut.println("------ Welcome to Chutes and Ladders ------");
        StdOut.println("::::: This will be a " + noofplayers + " player game :::::");
        StdOut.println("-------- Game Begins --------");
        StdOut.println("--------------------------------------------");

        while (true) {
            for (int i = 0; i < noofplayers; ++i) {
                int atspace = rollIt(i + 1, players[i]);  // calling the method for each turn
                if (atspace >= 100) {
                    players[i] = atspace;
                    System.out.printf("Player %d wins the game !\n", i + 1);
                    // if players are more than one
                    // then sorting the players based on the space
                    // displaying their ranks
                    if (noofplayers != 1) {
                        for (int k = 1; k < players.length; k++) {
                            for (int l = k; l > 0 && (players[l] > players[l - 1]); l--) {
                                int tempplayer = players[l];
                                int temprank = rank[l];
                                players[l] = players[l - 1];
                                rank[l] = rank[l - 1];
                                players[l - 1] = tempplayer;
                                rank[l - 1] = temprank;
                            }
                        }
                        for (int r = 1; r < noofplayers; r++) {

                            if (r == 1) {
                                StdOut.printf("player %d, comes in second at space %d .\n", rank[r], players[r]);
                            } else if (r == 2) {
                                StdOut.printf("player %d, comes in third at space %d .\n", rank[r], players[r]);
                            } else if (r == 3) {
                                StdOut.printf("player %d, comes in fourth at space %d .\n", rank[r], players[r]);
                            }
                        }
                    }
                    StdOut.println("  Game Over !!!!");

                    //Displaying total die rolls of whole game
                    int totaldierolls = totdieroll[0] + totdieroll[1] + totdieroll[2] + totdieroll[3] + totdieroll[4] +
                            totdieroll[5];
                    StdOut.println("-------------------------------------------------------");
                    StdOut.println("Interesting Statistics of the game");
                    StdOut.println("-------------------------------------------------------");
                    StdOut.println();
                    StdOut.println("Die Rolls::");
                    StdOut.println("Total Die rolls  by all players in whole game :: " + totaldierolls);

                    //displaying total die rolls by each player in the game
                    for (int d = 0; d < noofplayers; d++) {
                        int e = d + 1;
                        StdOut.println("Total Die rolls  by  player " + e + " :: " + p[d]);
                    }
                    // Finding the most common die roll in the whole game
                    for (int x = 1; x < totdieroll.length; x++) {
                        for (int l = x; l > 0 && (totdieroll[l] > totdieroll[l - 1]); l--) {
                            int temptotdieroll = totdieroll[l];
                            int temprollfreq = rollfreq[l];
                            totdieroll[l] = totdieroll[l - 1];
                            rollfreq[l] = rollfreq[l - 1];
                            totdieroll[l - 1] = temptotdieroll;
                            rollfreq[l - 1] = temprollfreq;
                        }
                    }

                    StdOut.print("Most common die roll(s) in whole game :: " + rollfreq[0]);
                    //Displaying the most common die rolls in the whole game
                    for (int z = 1; z < totdieroll.length; z++) {
                        int highest = totdieroll[0];
                        if (highest == totdieroll[z]) {
                            StdOut.print(" , " + rollfreq[z]);
                        }
                    }

                    //for each player most common die rolls
                    StdOut.println();
                    player1 = mostFrequent(player1, rollfreq);
                    StdOut.print("Most common die roll(s) for player 1 :: " + rollfreq[0]);
                    displayHighest(player1);

                    if (noofplayers >= 2) {
                        player2 = mostFrequent(player2, rollfreq);
                        StdOut.print("Most common die roll(s) for player 2 :: " + rollfreq[0]);
                        displayHighest(player2);
                    }

                    if (noofplayers >= 3) {
                        player3 = mostFrequent(player3, rollfreq);
                        StdOut.print("Most common die roll(s) for player 3 :: " + rollfreq[0]);
                        displayHighest(player3);
                    }

                    if (noofplayers == 4) {
                        player4 = mostFrequent(player4, rollfreq);
                        StdOut.print("Most common die roll(s) for player 4 :: " + rollfreq[0]);
                        displayHighest(player4);
                    }


                    StdOut.println();
                    StdOut.println("Ladders Climbed ::");
                    int totalclimb = 0;  // total ladders climbed in whole game by all players
                    // number of ladders climbed by each player
                    for (int m = 0; m < noofplayers; m++) {
                        totalclimb += climb[m];
                        int e = m + 1;
                        StdOut.println("Total ladders climbed by player " + e + " ::" + climb[m]);
                    }
                    StdOut.println("Total ladders climbed by all players in whole game ::" + totalclimb);
                    StdOut.println();
                    StdOut.println("Chutes Slid ::");
                    int totalslid = 0; // total chutes slided in whole game by all players
                    //number of chutes slided by each player
                    for (int m = 0; m < noofplayers; m++) {
                        int e = m + 1;
                        totalslid += slid[m];
                        StdOut.println("Total chutes slided by player " + e + " ::" + slid[m]);
                    }
                    StdOut.println("Total chutes slided by all players in whole game ::" + totalslid);

                    return;
                }
                players[i] = atspace;
                System.out.println();
            }
        }
    }


}
