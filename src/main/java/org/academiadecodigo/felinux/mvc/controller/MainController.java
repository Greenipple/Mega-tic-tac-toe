package org.academiadecodigo.felinux.mvc.controller;

import org.academiadecodigo.felinux.mvc.model.PlayerHandler;
import org.academiadecodigo.felinux.mvc.view.MenuView;
import org.academiadecodigo.felinux.mvc.view.textIO.MenuOptions;

public class MainController implements Controller {

    private MenuView menuView;

    private SinglePlayerController singlePlayerController;
    private ExitController exitController;
    private MultiPlayerController multiPlayerController;
    private MegaModeController megaModeController;
    private PlayerHandler player;
    private CentralService centralService;
    private InstructionController instructionController;
    //all controllers

    private int userInput = 0;

    @Override
    public void init() {

        if (userInput == 0) {
            menuView.show();
        }
    }

    public void chooseItem() {

        MenuOptions menuOption = MenuOptions.values()[userInput - 1];
        userInput = 0;

        switch (menuOption){

            case VS_COM: {
                singlePlayerController.init();
                break;
            }

            case VS_PLAYER: {

                centralService.registerPlayer(player);
                multiPlayerController.init();
                break;
            }

            case MEGA_VS_PLAYER:{

                centralService.registerMegaPlayer(player);
                megaModeController.init();
                break;
            }

            case INSTRUCTIONS: {

                instructionController.init();
                break;
            }

            case EXIT: {

                exitController.init();
            }
        }
    }

    public void onMenuSelection(Integer userInput) {

        this.userInput = userInput;

    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    public void setSinglePlayerController(SinglePlayerController singlePlayerController) {
        this.singlePlayerController = singlePlayerController;
    }

    public void setExitController(ExitController exitController) {
        this.exitController = exitController;
    }

    public void setMultiPlayerController(MultiPlayerController multiPlayerController) {

        this.multiPlayerController = multiPlayerController;
    }

    public void setCentralService(CentralService centralService) {
        this.centralService = centralService;
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.player = playerHandler;
    }

    public void setInstructionController(InstructionController instructionController) {
        this.instructionController = instructionController;
    }

    public void setMegaModeController(MegaModeController megaModeController) {
        this.megaModeController = megaModeController;
    }
}
