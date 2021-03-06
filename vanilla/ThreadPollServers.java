// part of the Server List Folder mod by palechip
// license GPLv3 (http://www.gnu.org/licenses/quick-guide-gplv3.html)
// to extend the mod, you need to decompile Minecraft and copy the modifyed code to the given function.
// that's because it isn't allowed to release decompiled minecraft code.
package net.minecraft.src;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class ThreadPollServers extends Thread{
    public void run()
    {
        boolean var27 = false;
        label183:
        {
            label184:
            {
                label185:
                {
                    label186:
                    {
                        label187:
                        {
                            try
                            {
                                var27 = true;
                                // MODIFIED
                                // checks for a Folder and sets its MOTD to Folder
                                if(this.pollServersServerData.serverIP.equals("") || this.pollServersServerData.serverIP.startsWith("dir:")) {
                                    this.pollServersServerData.pingToServer = -1L; // "(no-connection)"
                                    if(ServerListFolder.isApril1st()) {
                                       this.pollServersServerData.serverMOTD = EnumChatFormatting.OBFUSCATED + ServerListFolder.config.FolderMotdText;
                                    } else {
                                        this.pollServersServerData.serverMOTD = ServerListFolder.config.FolderMotdColorCode + ServerListFolder.config.FolderMotdText;
                                    }
                                    return;
                                }
                                // MODIFIED END
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_GRAY + "Polling..";
                                // there is more code in the decompiled version of Minecraft
}
