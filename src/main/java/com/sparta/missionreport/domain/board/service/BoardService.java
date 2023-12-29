package com.sparta.missionreport.domain.board.service;

import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.dto.BoardDto.Response;
import com.sparta.missionreport.domain.board.dto.BoardDto.UpdateRequest;
import com.sparta.missionreport.domain.board.dto.BoardWorkerRequestDto;
import com.sparta.missionreport.domain.board.entity.Board;
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


    public Response createBoard(User user, BoardDto.CreateRequest createRequest) {
        User createBy = userService.findUserById(user.getId());
        Board saveBoard = boardRepository.save(createRequest.toEntity(createBy));
        boardWorkerService.saveBoardWorker(saveBoard, createBy);
        return BoardDto.Response.of(saveBoard);
    }

    @Transactional
    public BoardDto.Response updateBoardName(User user, Long boardId, UpdateRequest updateRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateRequest);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public BoardDto.Response updateBoardColor(User user, Long boardId,
            BoardDto.UpdateRequest updateRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateRequest);
        return BoardDto.Response.of(board);
    }

    @Transactional
    public BoardDto.Response updateBoardDescription(User user, Long boardId,
            BoardDto.UpdateRequest updateRequest) {
        Board board = getBoardAndCheckAuth(user, boardId);
        if (updateRequest.getName() == null) {
            throw new BoardCustomException(BoardExceptionCode.INVALID_REQUEST);
        }
        board.update(updateRequest);
        return BoardDto.Response.of(board);
    }

    public void deleteBoard(User user, Long boardId) {
        Board board = getBoardAndCheckAuth(user, boardId);
        boardRepository.delete(board);
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
    public void inviteNewUser(User user, Long boardId, BoardWorkerRequestDto requestDto) {
        Board board = getBoardAndCheckAuth(user, boardId);
        User requestUser = userService.findUserByEmail(requestDto.getEmail());
        if (boardWorkerService.isExistedWorker(requestUser, board)) {
            throw new BoardCustomException(BoardExceptionCode.ALREADY_INVITED_USER);
        }
        boardWorkerService.saveBoardWorker(board, requestUser);
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

