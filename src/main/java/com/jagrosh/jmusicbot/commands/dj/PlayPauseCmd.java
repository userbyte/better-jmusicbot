/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.commands.DJCommand;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class PlayPauseCmd extends DJCommand 
{
    public PlayPauseCmd(Bot bot)
    {
        super(bot);
        this.name = "playpause";
        this.help = "toggles play status of the current song";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        if (handler.getPlayer().getPlayingTrack()!=null && handler.getPlayer().isPaused()) {
            if (DJCommand.checkDJPermission(event)) {
                handler.getPlayer().setPaused(false);
                event.replySuccess("Resumed **"+handler.getPlayer().getPlayingTrack().getInfo().title+"**");
            } else {
                event.replyError("Only DJs can use this command");
            }
            return;
        } else {
            handler.getPlayer().setPaused(true);
            event.replySuccess("Paused **"+handler.getPlayer().getPlayingTrack().getInfo().title+"**");
        }
    }
}
