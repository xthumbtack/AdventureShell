/*
Ryanh Somphithack
csc15
HW#3
4/24/2015
section: tues thurs 8AM + fri 9AM

Ryanh's Adventure Game based off the graphic novel "Zero Escape: Virtue's Last Reward"
The goal of the game is to escape the facility alive.

MAP                     -----------------------------------------------------------------

AB room -> Warehouse_A -> Infirmary     ->  Warehouse_B -> B. Garden
                       -> Lounge        ->  Warehouse_B -> Q_Room
                       -> Crew_Quarters ->  Warehouse_B 
                       -> nine

GAME SOLUTION           -----------------------------------------------------------------
    1) The starting room is locked, unlock it with
        >use button
    2) Proceed to warehouse_a
        >go warehouse_a
    3) gather the three keycards 
        >go infirmary
        >get keycard
        >go lounge
        >get keycard
        >go crew_quarters
        >get keycard
    4) now use the keycards in the corresponding rooms
        >go infirmary
        >use keycard_star
        >go lounge
        >use keycard_sun
        >go crew_quarters
        >use keycard_moon
    5) warehouse_b is now unlocked
-----------------------------------------------------------------------------------------
 */

import java.util.Scanner;

public class AdventureShell 
{
	public static Scanner kb = new Scanner(System.in);
	
	//These will be used to determine how much space is needed in the array.
	static final int NUMBER_OF_ROOMS = 9;
	static final int NUMBER_OF_ATTRIBUTES = 7;

	//Same here
	static final int NUMBER_OF_ITEMS = 4;
	static final int NUMBER_OF_PROPERTIES = 2;
	
	//Labels to use for our character's array
	static final int NUMBER_OF_CHARACTER_ATTRIBUTES = 4;
	
	//Our character
	public static String[] character = new String[NUMBER_OF_CHARACTER_ATTRIBUTES];
        
        // Our inventory
        public static String[] inventory = new String[NUMBER_OF_ITEMS];
        static {
            for (int a = 0; a < inventory.length; a++)
            {
                inventory[a] = "empty";
            }
        }
        
        // count, to keep track of keyreaders cleared
        public static int count = 0;
	
	//Our map, defined here outside of any method, so we can access it inside all of them.
	public static String[][] rooms = new String[NUMBER_OF_ROOMS][NUMBER_OF_ATTRIBUTES];


	//Items that can be LOOKed at otherwise interacted with.
	public static String[][] items = new String[NUMBER_OF_ITEMS][NUMBER_OF_PROPERTIES];

	//Constants to give us cleaner naming instead of using numbers to index our arrays
	static final int NAME = 0;
	static final int DESCRIPTION = 1;
	static final int EXITS = 2;
	static final int ITEMS = 3;
        static final int LOCK = 4;
	
	
	//Labels for items
	static final int KEYCARD_SUN = 0;
        static final int KEYCARD_MOON = 1;
        static final int KEYCARD_STAR = 2;
        static final int KEYCARD_NINE = 3;
	
	//Labels for rooms
	static final int AB_ROOM = 0;
	static final int WAREHOUSE_A = 1;
        static final int INFIRMARY = 2;
        static final int LOUNGE = 3;
        static final int CREW_QUARTERS = 4;
        static final int WAREHOUSE_B = 5;
        static final int B_GARDEN = 6;
        static final int Q_ROOM = 7;
        static final int NINE = 8;
	
	//Labels for our character array
	static final int INVENTORY = 3;
	
	//For keeping track of our current position on the map.
	static String currentRoom = "AB_ROOM";
        static int CURRENT_ROOM_INDEX = getRoomNumber(currentRoom);
        
	static boolean gameOver = false;
        static boolean gameComplete = false;
        
	static String intro = "You awake in what looks to be a large elevator.\n" + "You are overwhelmed with a feeling of impending doom.\n"
                + "You must escape.\n";
	
	//First we create the map in the computer memory, then prompt the user for input repeatedly to take in commands.
	public static void main(String[] args)
        {
		createMap();
		System.out.println(intro);
		while(!gameOver)
                {
			getCommand();
		}
                if (gameComplete)
                {
                    System.out.println("You escaped the facility!\nGAME END.");
                }
                else
                    System.out.println("You failed to escape...\nGAME OVER.");
	}
        
	// Get commands as strings, I prompt the user with a ">"
        // Added try catch block to catch user input exceptions
	private static void getCommand()
        {       String command = "", target = "";            
		System.out.print("> ");
                try 
                {
                    command = kb.nextLine();
                    //this only happens if they typed at least two words:
                    if(command.contains(" ")){
                            String[] input = command.split(" ");
                            command = input[0];
                            target = input[1];

                            doCommand(command, target);
                    }
                    else{
                            doCommand(command);
                    }
                } 
                catch (Exception e) 
                {
                    // System.out.println("EXCEPTION: " + e);
                    System.out.println("Error, invalid command.\n");
                }
	}
        // fills in the rooms array with each room's
        // name, description, exits, items and lock.
	private static void createMap()
        {
		//AB_ROOM descriptions
		rooms[AB_ROOM][NAME] = "AB_ROOM";
		rooms[AB_ROOM][DESCRIPTION] = "You appear to be in a large elevator called the AB Room.\n"
                        + "There is a machine with a button and a large display.\n"
                        + "The door seems to be electronically locked.\n";
		rooms[AB_ROOM][EXITS] = "WAREHOUSE_A";
		rooms[AB_ROOM][ITEMS] = "";
                rooms[AB_ROOM][LOCK] = "unlocked";
		
		//Warehouse A Description
		rooms[WAREHOUSE_A][NAME] = "WAREHOUSE_A";
		rooms[WAREHOUSE_A][DESCRIPTION] = "A big empty warehouse with AB rooms lined up along one side.\nThe phrase \"TWO MILKMEN GO COMEDY\" "
                        + "is painted in an eerie splatter on the wall.\n"
                        + "Another wall contains a mysterious giant metal door with a \"9\" painted on it.\n"
                        + "There are three other doors colored magenta, cyan and yellow.\n";        
		rooms[WAREHOUSE_A][EXITS] = "AB_ROOM, INFIRMARY, LOUNGE, CREW_QUARTERS, 9 door";
		rooms[WAREHOUSE_A][ITEMS] = "";
                rooms[WAREHOUSE_A][LOCK] = "locked";
                		
                //INFIRMARY Description 
		rooms[INFIRMARY][NAME] = "INFIRMARY";
		rooms[INFIRMARY][DESCRIPTION] =  "The room with the Yellow Door.\n"
                        + "Beds and cabinets of medical supplies line this room.\n"
                        + "Inside one of the cabinets is a shiny keycard.\n";
		rooms[INFIRMARY][EXITS] = "WAREHOUSE_A, WAREHOUSE_B";
		rooms[INFIRMARY][ITEMS] = "KEYCARD_SUN";
                rooms[INFIRMARY][LOCK] = "locked";
                
                //LOUNGE Description
		rooms[LOUNGE][NAME] = "LOUNGE";
		rooms[LOUNGE][DESCRIPTION] = "The room with the Magenta Door.\n"
                        + "A cozy bar with just three stools and a pletora of drinks.\n"
                        + "The drinks all have strange names, who knows how long they've been here.\n"
                        + "You spot a keycard inside one of the wine glasses.\n";
		rooms[LOUNGE][EXITS] = "WAREHOUSE_A, WAREHOUSE_B";
		rooms[LOUNGE][ITEMS] = "KEYCARD_MOON";
                rooms[LOUNGE][LOCK] = "locked";
                        
                
                //CREW_QUARTERS Description 
		rooms[CREW_QUARTERS][NAME] = "CREW_QUARTERS";
		rooms[CREW_QUARTERS][DESCRIPTION] =  "The room with the Cyan Door.\n"
                        + "There are fold-out beds each imprinted with white outlines of a person\n"
                        + "There is a keycard on one of the four beds.\n";
		rooms[CREW_QUARTERS][EXITS] = "WAREHOUSE_A, WAREHOUSE_B";
		rooms[CREW_QUARTERS][ITEMS] = "KEYCARD_STAR";
                rooms[CREW_QUARTERS][LOCK] = "locked";
                
                //Warehouse B Description  
		rooms[WAREHOUSE_B][NAME] = "WAREHOUSE_B";
		rooms[WAREHOUSE_B][DESCRIPTION] = "Another big empty warehouse much like the floor B warehouse. \n"
                        + "Except this one lacks the AB Rooms and has something else painted on the walls.\n"
                        + "\"MEMENTO MORI, IF THE NINETH LION ATE THE SUN\"\n"
                        + "There is a blue door and a white door here.\n";
		rooms[WAREHOUSE_B][EXITS] = "B_GARDEN, Q_ROOM";
		rooms[WAREHOUSE_B][ITEMS] = "";
                rooms[WAREHOUSE_B][LOCK] = "locked";
                
                //B_GARDEN Description
                rooms[B_GARDEN][NAME] = "B_GARDEN";
                rooms[B_GARDEN][DESCRIPTION] = "The room with the Blue Door.\n"
                        + "A serene biome unlike any other room.\n"
                        + "There is a large tree providing shade from an artificial sun.\n"
                        + "There are vegatables planted in a small plot.\n"
                        + "There is even a flowing river that ends beside an inscribed tomb.\n"
                        + "It reads, \"Tu fui, ego eris\".\n";
                rooms[B_GARDEN][EXITS] = "WAREHOUSE_B";
                rooms[B_GARDEN][ITEMS] = "";
                rooms[B_GARDEN][LOCK] = "locked";
                        
                //Q_ROOM Description
                rooms[Q_ROOM][NAME] = "Q_ROOM";
                rooms[Q_ROOM][DESCRIPTION] = "The room with the White Door.\n"
                        + "A floating white cube is in the center of the room.\n"
                        + "A super-computer seems to be controlling this room.\n"
                        + "You feel unwelcomed in here.\n";
                rooms[Q_ROOM][EXITS] = "WAREHOUSE_B";
                rooms[Q_ROOM][LOCK] = "locked";
                        
                //NINE Description
                rooms[NINE][NAME] = "NINE";
                rooms[NINE][DESCRIPTION] = "You're....on Mars!!"
                        + "You see the satelittes Deimos and Phobos in the sky.\n"
                        + "The land around you is looks like a barren desert.\n";
                rooms[NINE][EXITS] = "none.\nThe large steel door is shut tight.\n";
                rooms[NINE][LOCK] = "locked";
	}

	//If they didnt type a target, pass a blank one.
	private static void doCommand(String command)
        {
		doCommand(command, "");
	}

	//Trigger the matching function.
	public static void doCommand(String command, String target)
        {
		command = command.toLowerCase();
		target = target.toLowerCase();
		
		switch(command)
                {
			case "look":
				if(target.length() > 0){
					look(target);
				}
				else{
					look();
				}
				break;
			case "go":
				if(target.length() > 0){
					go(target);
				}
				else{
					System.out.println("Where to?");
                                        System.out.println("Exits : " + rooms[CURRENT_ROOM_INDEX][EXITS] + "\n");
				}
				break;
                        case "get":
                                if(target.length() > 0){
                                    get(target);
                                }
                                else{
                                    System.out.println("Get what?");
                                }
                                break;
                        case "use":
                                if(target.length() > 0){
                                    use(target);
                                }
                                else{
                                    System.out.println("Use what?");
                                }
                                break;
			default:
                            
				System.out.println("Sorry, \"" + command + "\" isn't a command.\n");
                                System.out.println("Commands: \"look\", \"go\", \"get\", \"use\"\n");
                                System.out.println("ex: look inventory, go warehouse_a, get item, use item\n");
		}
	}	
	// Move our character to a destination
        // If a destination is locked, they will not move anywhere.
	public static void go(String destination)
        {
                destination = destination.toLowerCase();

                int index = 0;
		boolean found = false;
		for(int room = 0; room < rooms.length; room++){
			if(rooms[room][NAME].equalsIgnoreCase(destination)){
				currentRoom = destination;
				found = true;
                                index = room;
			}
		}
		if(!found){
			System.out.println("Sorry, that room was not found: "+destination + "\n");
		}
                else if (rooms[index][LOCK].equalsIgnoreCase("locked"))
                {
                    System.out.println("That room is locked.\n");
		}
                else if (!(rooms[getRoomNumber(currentRoom)][EXITS].contains(destination)))
                {
                    System.out.println("That room is out of range.\n");
                }
                else
                {
                    System.out.println("\nYou moved to the " + destination +".\n");
                    currentRoom = destination;
                    CURRENT_ROOM_INDEX = getRoomNumber(currentRoom);
                }
                              
	}

	//Looks through the rooms and returns the index of our destination.
	private static int getRoomNumber(String destination)
        {
            int index = 0;
            
            destination = destination.toLowerCase();
            switch(destination)
            {
                case "ab_room":
                    index = 0;
                    break;
                case "warehouse_a":
                    index = 1;
                    break;
                case "infirmary":
                    index = 2;
                    break;
                case "lounge":
                    index = 3;
                    break;
                case "crew_quarters":
                    index = 4;
                    break;
                case "warehouse_b":
                    index = 5;
                    break;
                case "b_garden":
                    index = 6;
                    break;
                case "q_room":
                    index = 7;
                    break;
                case "nine":
                    index = 8;
                    break;	
            }
            return index;
        }

	// Prints the description of the item you are LOOKing at
	public static void look(String item)
        {
                if (item.equalsIgnoreCase("inventory"))
                {
                    System.out.println("INVENTORY : ");
                    for (int a = 0; a < inventory.length; a++)
                    {
                        System.out.println(a+1 + ". " + inventory[a]);
                    }
                    System.out.println();
                }
                else
                    for(int itemNum = 0; itemNum < items.length ; itemNum++)
                    {
			if(item.equalsIgnoreCase(items[itemNum][NAME]))
                        {
				System.out.println(items[itemNum][DESCRIPTION]);
			}
                    }
	}

	// Prints the description of the room, the items list, and the exits list.
	public static void look()
        {
                System.out.println("\n" + rooms[CURRENT_ROOM_INDEX][DESCRIPTION]);
	}
        
	// Add the item to our inventory, if it is in the room.
	public static void get(String item)
        {
            if (item.equalsIgnoreCase("keycard"))
            {
                if (CURRENT_ROOM_INDEX == 2) 
                {
                    inventory[KEYCARD_SUN] = "KEYCARD_SUN";
                    System.out.println("Added KEYCARD_SUN to your inventory.\n");
                    rooms[INFIRMARY][DESCRIPTION] = "The room with the Yellow Door.\n"
                        + "Beds and cabinets of medical supplies line this room.\n"
                        + "You already grabbed a keycard from one of the cabinets.\n"
                        + "You notice a keyreader with a STAR symbol.\n"
                        + "The keycard light is RED.\n";
                }
                else if (CURRENT_ROOM_INDEX == 3) 
                {
                    inventory[KEYCARD_MOON] = "KEYCARD_MOON";
                    System.out.println("Added KEYCARD_MOON to your inventory.\n");
                    rooms[LOUNGE][DESCRIPTION] = "The room with the Magenta Door.\n"
                        + "A cozy bar with just three stools and a pletora of drinks.\n"
                        + "The drinks all have strange names, who knows how long they've been here.\n"
                        + "The glass which you grabbed the keycard from is now empty.\n"
                        + "You notice a keyreader with a SUN symbol.\n"
                        + "The keycard light is RED.\n";
                    
                }
                else if (CURRENT_ROOM_INDEX == 4) 
                {
                    inventory[KEYCARD_STAR] = "KEYCARD_STAR";
                    System.out.println("Added KEYCARD_STAR to your inventory.\n");
                    rooms[CREW_QUARTERS][DESCRIPTION] =   "The room with the Cyan Door.\n"
                        + "There are fold-out beds each imprinted with white outlines of a person\n"
                        + "There are no other items of interest here\n"
                        + "You notice a keyreader with a MOON symbol.\n"
                        + "The keycard light is RED.\n";
                }
            }
            else
                System.out.println("No useable item in this room.\n");
	}
	
	// use the item, if it applies
	public static void use(String item){
            item = item.toLowerCase();
            
            if (item.equalsIgnoreCase("button"))
            {
                if (CURRENT_ROOM_INDEX == 0)
                {
                    System.out.println("You pressed the button.\nThe display now reads \"Betray\".");
                    System.out.println("You hear a beep from the electronic lock on the door.\n");
                    rooms[AB_ROOM][DESCRIPTION] = "You appear to be in a large elevator called the AB Room.\n"
                        + "There is a machine with a button and a large display that reads \"Betray\".\n"
                        + "The door is not locked anymore.\n";
                    rooms[WAREHOUSE_A][LOCK] = "unlocked";
                    rooms[LOUNGE][LOCK] = "unlocked";
                    rooms[INFIRMARY][LOCK] = "unlocked";
                    rooms[CREW_QUARTERS][LOCK] = "unlocked";
                }
                else
                {
                    System.out.println("There's not a single button in here that does anything useful.\n");
                }
            }
            else if (item.contains("keycard"))
            {
                if (CURRENT_ROOM_INDEX == 2)
                {
                    if (item.equalsIgnoreCase("KEYCARD_STAR"))
                    {
                        System.out.println("The keycard reader turned green!\n");
                        rooms[INFIRMARY][DESCRIPTION] = "The room with the Yellow Door.\n"
                            + "Beds and cabinets of medical supplies line this room.\n"
                            + "You already grabbed a keycard from one of the cabinets.\n"
                            + "You notice a keyreader with a STAR symbol.\n"
                            + "It's light is now is GREEN.\n";
                        count++;
                    }
                    else
                        System.out.println("...nothing happened.\n");
                }
                else if (CURRENT_ROOM_INDEX == 3)
                {
                    if (item.equalsIgnoreCase("KEYCARD_SUN"))
                    {
                        System.out.println("The keycard reader turned green!\n");
                        rooms[LOUNGE][DESCRIPTION] = "The room with the Magenta Door.\n"
                            + "A cozy bar with just three stools and a pletora of drinks.\n"
                            + "The drinks all have strange names, who knows how long they've been here.\n"
                            + "The glass which you grabbed the keycard from is now empty.\n"
                            + "You notice a keyreader with a SUN symbol.\n"
                            + "It's light is now GREEN.\n";
                        count++;                      
                    }
                    else
                        System.out.println("...nothing happened.\n");
                }
                else if (CURRENT_ROOM_INDEX == 4)
                {
                    if (item.equalsIgnoreCase("KEYCARD_MOON"))
                    {
                        System.out.println("The keycard reader turned green!\n");
                        rooms[CREW_QUARTERS][DESCRIPTION] = "The room with the Cyan Door.\n"
                            + "There are fold-out beds each imprinted with white outlines of a person\n"
                            + "There are no other items of interest here\n"
                            + "You notice a keyreader with a MOON symbol.\n"
                            + "It's light is now GREEN.\n";
                        count++;
                    }
                    else
                        System.out.println("...nothing happened.\n");
                }
                else
                    System.out.println("There's no keycard reader in here.\n");
            }
            if (item.contains("keycard") && count == 1) System.out.println("You've cleared one keycard.\n");
            else if (item.contains("keycard") && count == 2) System.out.println("You've cleared two keycard.\n");
            else if (count == 3)
            {
                System.out.println("That's all three keys, warehouse_b should be unlocked now.\n");
                unlock("warehouse_b");
                unlock("b_garden");
                unlock("q_room");
            }              
	}
        
        public static void unlock(String room)
        {
            int rn = getRoomNumber(room);
            rooms[rn][LOCK] = "unlock";
        }
}
