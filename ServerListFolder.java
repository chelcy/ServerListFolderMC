//part of the Server List Folder mod by palechip
//license GPLv3 (http://www.gnu.org/licenses/quick-guide-gplv3.html)
//to extend the mod, you need to decompile Minecraft and copy the modifyed code to the given function.
//that's because it isn't allowed to release decompiled minecraft code.

package net.minecraft.src;

import java.util.ArrayList;

public class ServerListFolder {
	//saves the opened hierarchy
	private static ArrayList<String> openedFolders;
	private static int index;
	
	//makes the refresh button not changing to the main servers
	private static Boolean lockNextReset;
	
	static{
			openedFolders = new ArrayList<String>(3);
			openedFolders.add(0, "servers");
			openedFolders.add(1, "");
			openedFolders.add(2, "");
			//I don't think many people will have a deeper folder structure.
			index = 0;
			lockNextReset = false;
	}
	
	//returns the correct folder file(FOLDER-foldername.dat) of the currently opened folder
	public static String getFolderFile(){
			//if the users uses dir:servers, he will get to the main servers.
			 if(openedFolders.get(index).equals("servers")){
				 return "servers.dat"; 
			 }
			 else{
				 return "FOLDER-"+ openedFolders.get(index) + ".dat";
			 }
	}
	
	//this function returns true if it converted the serverToCheck from a server without IP to one with a dir:-IP
	public static Boolean checkIfNewFolder(ServerData serverToCheck){
		if(serverToCheck.serverIP.isEmpty()){
    		serverToCheck.serverIP = "dir:"+ serverToCheck.serverName;
    		serverToCheck.setHideAddress(true); //Automatically hides the adress
    		return true;
		}
		return false;
	}
	
	
	//returns true if the joined server is a folder and automatically sets it to the current folder
	public static Boolean checkIfFolder(ServerData serverToCheck){
    	if(serverToCheck.serverIP.startsWith("dir:")){ //dir: is the prefix for all folders
    		if(serverToCheck.serverIP.substring(4).equals("...")){ // dir:... is the command to get upwards in the hierarchy
    			if(index <= 0){
    				return true; //although you can't get upwards, it needs to be true to cancel the connecting.
    			}
    			else{
    				index--; //go upwards
    			}
    		}
    		else if(serverToCheck.serverIP.substring(4).equals("servers")){ //dir:servers changes to the main servers.
    			index=0;
    		}else{
    			index++;
    			if(openedFolders.size() <= index){ //checks if the array has already space for the folder
    				openedFolders.add(serverToCheck.serverIP.substring(4)); //the substing cuts the dir:
    			}
    			else{
    				openedFolders.set(index,serverToCheck.serverIP.substring(4));
    			}
    		}
    		return true;
    	}
    	return false; //not a folder
	}
	
	//this function adds a ... folder if there is no, makes sure that the ... folder is always the last one.
	public static void ManageUpwardsFolders(ServerList list){
		if(!(index == 0)){ //if the loaded list is the main server list, no ... folder will be added
			int serverCount = list.countServers();
			ServerData upwardsServer = new ServerData("...", "dir:...");
			upwardsServer.setHideAddress(true);
		
			if((serverCount > 1) && (list.getServerData(serverCount-2).serverIP.equals("dir:..."))){ //there was a server added
				list.removeServerData(serverCount-2);
				list.addServerData(upwardsServer);
			}
		
			Boolean upwardsServerFound = false;
			for(int c=0;c<serverCount;c++){ //if there is any ... folder, no new shall be added
				if(list.getServerData(c).serverIP.equals("dir:...")){
					upwardsServerFound = true;
					break;
				}
			}
			if(!upwardsServerFound){
				list.addServerData(upwardsServer);
			}
		}
	}
	
	//when the server list gets refreshed or reopened this function gets called
	public static void resetToMainServerList(){
		//if not a refresh
		if(lockNextReset){
			lockNextReset = false;
		}
		//go to the main server list
		else{
			index = 0;
		}
	}
	
	//this gets only called when the list gets refreshed
	public static void lockNextReset(){
		lockNextReset = true;
	}

}
