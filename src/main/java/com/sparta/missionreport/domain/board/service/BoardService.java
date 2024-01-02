package com.sparta.missionreport.domain.board.service;

import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.dto.BoardDto.Response;
import com.sparta.missionreport.domain.board.dto.BoardDto.UpdateBoardRequest;
import com.sparta.missionreport.domain.board.dto.BoardWorkerDto;
import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.board.exception.BoardCustomException;
import com.sparta.missionreport.domain.board.exception.BoardExceptionCode;
import com.sparta.missionreport.domain.board.repository.BoardRepository;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserService userService;
    private final BoardRepository boardRepository;
    private final BoardWorkerService boardWorkerService;


    public Response createBoard(User user, BoardDto.CreateBoardRequest createBoardRequest) {
        User createBy = userService.findUserById(user.getId());
        Board saveBoard = boardRepository.save(createBoardRequest.toEntity(createBy));
        boardWorkerService.saveBoardWorker(saveBoard, createBy);
        return BoardDto.Response.of(saveBoard);
    }

    @Transactional
    public BoardDto.Response updateBoardName(User user, Long boardId, UpdateBoardRequest updateBoardRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateBoardRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateBoardRequest);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public BoardDto.Response updateBoardColor(User user, Long boardId,
            BoardDto.UpdateBoardRequest updateBoardRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateBoardRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateBoardRequest);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public BoardDto.Response updateBoardDescription(User user, Long boardId,
            BoardDto.UpdateBoardRequest updateBoardRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateBoardRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateBoardRequest);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public void deleteBoard(User user, Long boardId) {
        Board board = getBoardAndCheckAuth(user, boardId);
        board.deleteBoard();
        boardWorkerService.deleteBoardWorkers(board);
    }

    public List<Response> getBoards(User user) {
        List<Board> boards = boardRepository.findAllById(user.getId());
        return boards.stream().map(BoardDto.Response::of).toList();
    }

    public BoardDto.Response getBoard(User user, Long boardId) {
        Board board = getBoardAndCheckAuth(user, boardId);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public void inviteNewUser(User user, Long boardId, BoardWorkerDto.BoardWorkerInviteRequest requestDto) {
        Board board = getBoardAndCheckAuth(user, boardId);
        User requestUser = userService.findUserByEmail(requestDto.getEmail());
        if (boardWorkerService.isExistedWorker(requestUser, board)) {
            throw new BoardCustomException(BoardExceptionCode.ALREADY_INVITED_USER);
        }
        boardWorkerService.saveBoardWorker(board, requestUser);
    }

    public List<BoardWorkerDto.BoardWorkerResponse> getWorkersInBoard(User user, Long boardId) {
        Board board = getBoardAndCheckAuth(user, boardId);

        List<BoardWorker> list = boardWorkerService.findAllByWorkersInBoard(board);
        return list.stream().map(BoardWorkerDto.BoardWorkerResponse::of).toList();
    }

    public  BoardWorkerDto.BoardWorkerResponse searchWorkerInBoard(User user, Long boardId, String email) {
        Board board = getBoardAndCheckAuth(user, boardId);
        User searcBoardhUser = userService.findUserByEmail(email);

        BoardWorker boardWorker = boardWorkerService.serchBoardUser(board, searcBoardhUser);
        return BoardWorkerDto.BoardWorkerResponse.of(boardWorker);
    }

    @Transactional
    public void deleteBoardWorker(User user, Long boardId) {
        Board board = getBoardAndCheckAuth(user, boardId);
        User boardWorker = userService.findUserById(user.getId());

        if (!boardWorkerService.isExistedWorker(boardWorker, board)) {
            throw new BoardCustomException(BoardExceptionCode.NOT_FOUND_WORKER_IN_BOARD);
        }

        boardWorkerService.deleteBoardWorker(board, boardWorker);
    }

    private Board getBoardAndCheckAuth(User user, Long boardId) {
        Board board = findBoardByID(boardId);
        User loginUser = userService.findUserById(user.getId());
        if (!boardWorkerService.isExistedWorker(loginUser, board)) {
            throw new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD);
        }
        return board;
    }

    public Board findBoardByID(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));
    }
}

