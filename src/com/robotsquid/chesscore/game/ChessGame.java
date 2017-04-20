package com.robotsquid.chesscore.game;

import com.robotsquid.chesscore.ChessCore;
import com.robotsquid.chesscore.entry.PlayerEntry;

import java.io.Serializable;

public class ChessGame implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int boardNum;

    private PlayerParticipation whiteParticipation;
    private PlayerParticipation blackParticipation;
    private boolean bye = false;

    public ChessGame(int round, int boardNum, PlayerEntry playerWhite, PlayerEntry playerBlack)
    {
        this.boardNum = boardNum;
        whiteParticipation = new PlayerParticipation(playerWhite, playerBlack, ChessSide.WHITE, round);
        blackParticipation = new PlayerParticipation(playerBlack, playerWhite, ChessSide.BLACK, round);
    }

    public ChessGame(int round)
    {
        this.whiteParticipation = new PlayerParticipation(ChessSide.WHITE, round);
        this.blackParticipation = new PlayerParticipation(ChessSide.BLACK, round);
    }

    public int getBoardNum()
    {
        return boardNum;
    }

    public PlayerParticipation getWhiteParticipation()
    {
        return whiteParticipation;
    }

    public PlayerParticipation getBlackParticipation()
    {
        return blackParticipation;
    }

    public void setBoardNum(int boardNum)
    {
        this.boardNum = boardNum;
    }

    public void setWhitePlayer(PlayerEntry whitePlayer)
    {
        this.whiteParticipation.setMe(whitePlayer);
        this.blackParticipation.setOpponent(whitePlayer);
    }

    public void setBlackPlayer(PlayerEntry blackPlayer)
    {
        this.blackParticipation.setMe(blackPlayer);
        this.whiteParticipation.setOpponent(blackPlayer);
    }

    public void setWhiteScore(PlayerResult result)
    {
        if (result != null)
        {
            this.whiteParticipation.setResult(result);
            this.blackParticipation.setResult(PlayerResult.valueOf(1F - result.toFloat()));

            this.whiteParticipation.getMe().updateScores();
            this.blackParticipation.getMe().updateScores();
            ChessCore.data.settings.buchholzOption.updateBuchholz(this.whiteParticipation.getMe());
            ChessCore.data.settings.buchholzOption.updateBuchholz(this.blackParticipation.getMe());
            this.whiteParticipation.getMe().updatePerf();
            this.blackParticipation.getMe().updatePerf();

            if (!ChessCore.data.historyGames.contains(this))
            {
                ChessCore.data.historyGames.add(this);
            }
        }
    }

    public void startGame()
    {
        whiteParticipation.getMe().gamesPlayed.add(whiteParticipation);
        blackParticipation.getMe().gamesPlayed.add(blackParticipation);

        whiteParticipation.getMe().scoreBeforeCurrentRound = whiteParticipation.getMe().score;
        blackParticipation.getMe().scoreBeforeCurrentRound = blackParticipation.getMe().score;
    }

    public void setBye(boolean bye)
    {
        this.bye = bye;

        setWhiteScore(PlayerResult.WIN);
    }

    public boolean isBye()
    {
        return bye;
    }
}
