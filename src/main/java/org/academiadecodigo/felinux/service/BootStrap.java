package org.academiadecodigo.felinux.service;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerRangeInputScanner;
import org.academiadecodigo.felinux.mvc.controller.*;
import org.academiadecodigo.felinux.mvc.model.Lobby;
import org.academiadecodigo.felinux.mvc.model.PlayerHandler;
import org.academiadecodigo.felinux.mvc.model.Server;
import org.academiadecodigo.felinux.mvc.view.*;

import java.io.*;

public class BootStrap {

    public static CentralService centralService;
    public static Server server;

    public static void init(){

        try {

            Prompt serverPrompt = new Prompt(System.in, System.out);

            IntegerRangeInputScanner portScanner = new IntegerRangeInputScanner(0,16000);
            portScanner.setMessage("Insert a port number to initialize the server\n");
            portScanner.setError("A valid one\n");
            int port = serverPrompt.getUserInput(portScanner);
            //int port = 9000;

            centralService = new CentralService();
            Lobby lobby = new Lobby();
            PlayerService playerService = new PlayerService();

            centralService.setLobby(lobby);
            centralService.setPlayerService(playerService);

            playerService.setLobby(lobby);

            server = new Server(port);
            //This says Client Found
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initPlayer(PlayerHandler playerHandler) throws IOException {

        PrintWriter printWriter = new PrintWriter(playerHandler.getSocket().getOutputStream(), true);
        Prompt prompt = new Prompt(playerHandler.getSocket().getInputStream(),
                new PrintStream(playerHandler.getSocket().getOutputStream()));

        MenuView menuView = new MenuView();
        menuView.setPrompt(prompt);
        menuView.setWriter(printWriter);

        MainController mainController = new MainController();
        mainController.setMenuView(menuView);
        menuView.setMainController(mainController);

        MultiPlayerView multiPlayerView = new MultiPlayerView();
        multiPlayerView.setPrompt(prompt);
        multiPlayerView.setWriter(printWriter);

        GameOverView gameOverView = new GameOverView();
        gameOverView.setPrompt(prompt);
        gameOverView.setWriter(printWriter);

        SinglePlayerView singlePlayerView = new SinglePlayerView();
        singlePlayerView.setPrompt(prompt);
        singlePlayerView.setWriter(printWriter);

        InstructionView instructionView = new InstructionView();
        instructionView.setWriter(printWriter);
        instructionView.setPrompt(prompt);

        MultiPlayerController multiPlayerController = new MultiPlayerController();
        multiPlayerController.setGameView(multiPlayerView);
        multiPlayerController.setPlayer(playerHandler);

        //Megamode
        MegaModeView megaModeView = new MegaModeView();
        megaModeView.setPrompt(prompt);
        megaModeView.setWriter(printWriter);

        MegaModeController megaModeController = new MegaModeController();
        megaModeController.setMegaView(megaModeView);
        megaModeController.setPlayer(playerHandler);

        megaModeView.setController(megaModeController);

        GameOverController gameOverController = new GameOverController();
        gameOverController.setGameOverView(gameOverView);
        gameOverController.setMainController(mainController);

        InstructionController instructionController = new InstructionController();
        instructionController.setView(instructionView);
        instructionController.setMenuView(menuView);

        SinglePlayerController singlePlayerController = new SinglePlayerController();
        singlePlayerController.setSinglePlayerView(singlePlayerView);
        singlePlayerController.setGameOverController(gameOverController);
        gameOverController.setSinglePlayerController(singlePlayerController);

        ExitController exitController = new ExitController();
        exitController.setServer(server);
        exitController.setPlayer(playerHandler);

        mainController.setSinglePlayerController(singlePlayerController);
        mainController.setMultiPlayerController(multiPlayerController);
        mainController.setMegaModeController(megaModeController);
        mainController.setExitController(exitController);
        mainController.setPlayerHandler(playerHandler);
        mainController.setCentralService(centralService);
        mainController.setInstructionController(instructionController);

        multiPlayerView.setController(multiPlayerController);
        singlePlayerView.setSinglePlayerController(singlePlayerController);
        gameOverView.setGameOverController(gameOverController);

        playerHandler.setController(mainController);
        playerHandler.setMultiPlayerController(multiPlayerController);
        playerHandler.setMegaModeController(megaModeController);
    }
}
