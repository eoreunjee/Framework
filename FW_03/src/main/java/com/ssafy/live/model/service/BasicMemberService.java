package com.ssafy.live.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.live.exception.RecordNotFoundException;
import com.ssafy.live.model.dao.AddressDao;
import com.ssafy.live.model.dao.BasicAddressDao;
import com.ssafy.live.model.dao.BasicMemberDao;
import com.ssafy.live.model.dao.MemberDao;
import com.ssafy.live.model.dto.Member;
import com.ssafy.live.model.dto.Page;
import com.ssafy.live.model.dto.SearchCondition;
import com.ssafy.live.util.DBUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BasicMemberService implements MemberService {
	
    private MemberDao mDao;
    private AddressDao aDao;

    private DBUtil util;
    
//    private MemberDao mDao = BasicMemberDao.getDao();
//    private AddressDao aDao = BasicAddressDao.getDao();
//
//    private DBUtil util = DBUtil.getUtil();
//    
//    private static BasicMemberService service = new BasicMemberService();
//
//    private BasicMemberService() {
//    }

//    public static BasicMemberService getService() {
//        return service;
//    }

    @Override
    public int registMember(Member member) throws SQLException {
        Connection con = util.getConnection();
        try {
            con.setAutoCommit(false);
            int result = mDao.insert(con, member);
            con.commit();
            return result;
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            util.close(con);
        }
    }

    @Override
    public Member login(String email, String password) throws SQLException {
        try (Connection con = util.getConnection()) {
            Member member = mDao.select(con, email);
            if (member != null && member.getPassword().equals(password)) {
                return member;
            } else {
                throw new RecordNotFoundException("id/pass 확인");
            }
        }
    }

    @Override
    public Member selectDetail(String email) throws SQLException {
        try (Connection con = util.getConnection()) {
            return mDao.selectDetail(con, null);
        }
    }

    public List<Member> search() throws SQLException {
        try (Connection con = util.getConnection()) {
            return mDao.search(con);
        }
    }

    @Override
    public Page<Member> search(SearchCondition condition) throws SQLException {
        try (Connection con = util.getConnection()) {
            // 전체 회원 수 조회
            int totalItems = mDao.getTotalCount(con, condition);
            // 현재 페이지의 회원 목록 조회
            List<Member> members = mDao.search(con, condition);
            // 페이지네이션 정보 생성
            Page<Member> page = new Page<>(condition, totalItems, members);
            return page;
        }
    }

    @Override
    public void delete(int mno) throws SQLException {
        Connection con = util.getConnection();
        try {
            con.setAutoCommit(false);
            aDao.deleteByMno(con, mno);
            mDao.delete(con, mno);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            util.close(con);
        }
    }

    @Override
    public void update(Member member) throws SQLException {
        Connection con = util.getConnection();
        try {
            con.setAutoCommit(false);
            mDao.update(con, member);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            util.close(con);
        }
    }
}
