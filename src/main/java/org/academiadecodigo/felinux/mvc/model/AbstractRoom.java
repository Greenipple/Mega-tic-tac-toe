package org.academiadecodigo.felinux.mvc.model;

import org.academiadecodigo.felinux.service.AbstractRoomService;

public abstract class AbstractRoom {


    private PlayerHandler player1;
    private PlayerHandler player2;

    private boolean roomIsFull = false;

    public AbstractRoom(PlayerHandler player1){

        this.player1 = player1;
        player1.setRoom(this);
    }

    public void addPlayer(PlayerHandler player2) {

        this.player2 = player2;
        player2.setRoom(this);
        roomIsFull = true;
    }

    public boolean checkRoomIsFull(){
        return roomIsFull;
    }

    public void broadcast(String message){

        if(this instanceof MegaRoom){

            player1.getMegaModeController().receive(message);

            if(!(player2 == null)) {

                player2.getMegaModeController().receive(message);
            }
            return;
        }

        player1.getMultiPlayerController().receive(message);

        if(!(player2 == null)) {
            player2.getMultiPlayerController().receive(message);
        }
    }

    public PlayerHandler getPlayer1(){
        return player1;
    }

    public PlayerHandler getPlayer2(){
        return player2;
    }

    public abstract AbstractRoomService getRoomService();
}
