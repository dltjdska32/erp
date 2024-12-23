package test.erp_project.dto.answer_dto;


import test.erp_project.domain.board.Board;
import test.erp_project.domain.board.BoardAnswer;
import test.erp_project.domain.user.User;

public class AnswerData {
    Board board;
    BoardAnswer boardanswer;
    User WriteUser;

    public AnswerData(Board board, BoardAnswer boardanswer, User user){
        this.board = board;
        this.boardanswer=boardanswer;
        this.WriteUser = user;
    }

    public BoardAnswer getBoardAnswer() {
        return boardanswer;
    }

    public void setBoardAnswer(BoardAnswer boardanswer) {
        this.boardanswer = boardanswer;
    }


    public User getWriteUser() {
        return WriteUser;
    }

    public void setWriteUser(User writeUser) {
        WriteUser = writeUser;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
