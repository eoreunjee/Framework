package com.ssafy.live.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.live.model.dto.Address;
import com.ssafy.live.model.dto.Member;
import com.ssafy.live.model.dto.SearchCondition;

@Repository
public class BasicMemberDao implements MemberDao {
	
	//싱글톤 관련 코드 지우기 (1)
//    private static BasicMemberDao dao = new BasicMemberDao();
//
////    private BasicMemberDao() {
////    }
////
////    public static BasicMemberDao getDao() {
////        return dao;
////    }

    @Override
    public int insert(Connection con, Member member) throws SQLException {
        String sql = "insert into member (name, email, password) values(?,?,?)";
        int result = -1;
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            result = pstmt.executeUpdate();
        }
        return result;
    }

    @Override
    public Member select(Connection con, String email) throws SQLException {
        Member member = null;
        String sql = "select * from member where email=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                member = new Member();
                member.setMno(rset.getInt("mno"));
                member.setName(rset.getString("name"));
                member.setEmail(rset.getString("email"));
                member.setPassword(rset.getString("password"));
                member.setRole(rset.getString("role"));
            }
        }
        return member;
    }

    @Override
    public List<Member> search(Connection con) throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "select * from member order by mno desc";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Member member = new Member();
                member.setMno(rset.getInt("mno"));
                member.setName(rset.getString("name"));
                member.setEmail(rset.getString("email"));
                list.add(member);
            }
        }
        return list;
    }

    @Override
    public Member selectDetail(Connection con, String email) throws SQLException {
        Member member = null;
        String sql = """
                select m.*, a.*
                from member m left join address a using(mno)
                where m.email=?""";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int mno = rset.getInt("mno");
                String name = rset.getString("name");
                String pass = rset.getString("password");
                String role = rset.getString("role");
                Integer ano = rset.getInt("ano");
                String title = rset.getString("title");
                String address = rset.getString("address");
                String detailAddress = rset.getString("detail_address");
                String x = rset.getString("x");
                String y = rset.getString("y");
                if (member == null) {
                    //생성자 직접 호출했던 부분 바꾸기(2)
                	//member = new Member(mno, name, email, pass, role);
                	member = Member.builder().mno(mno).name(name).email(email).password(pass).role(role).build();
                }
                if (title != null) {
                    Address addr = new Address(ano, mno, title, address, detailAddress, x, y);
                    member.getAddresses().add(addr);
                }
            }
        }
        return member;
    }

    public int getTotalCount(Connection con, SearchCondition condition) throws SQLException {
        int result = 0;
        String sql = null;
        boolean hasKeyWord = condition.hasKeyword();

        sql = hasKeyWord ? "select count(*) from member where %s like ?".formatted(condition.getKey())
                : "select count(*) from member";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            if (hasKeyWord) {
                pstmt.setString(1, "%" + condition.getWord() + "%");
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }
        return result;
    }

    public List<Member> search(Connection con, SearchCondition condition) throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = null;
        boolean hasKeyWord = condition.hasKeyword();
        sql = hasKeyWord
                ? "select * from member where %s like ?  order by mno desc limit ?,?".formatted(condition.getKey())
                : "select * from member order by mno desc limit ?,?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int idx = 1;
            if (hasKeyWord) {
                pstmt.setString(idx++, "%" + condition.getWord() + "%");
            }
            pstmt.setInt(idx++, condition.getOffset());
            pstmt.setInt(idx++, condition.getItemsPerPage());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setMno(rs.getInt("mno"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));
                members.add(member);
            }
        }
        return members;
    }

    @Override
    public int update(Connection con, Member member) throws SQLException {
        int result = 0;
        String sql = "update member set name=?, password=?, role = ? where email=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getRole());
            pstmt.setString(4, member.getEmail());
            result = pstmt.executeUpdate();
        }
        return result;
    }

    @Override
    public int delete(Connection con, int mno) throws SQLException {
        int result = -1;
        String sql = "delete from member where mno=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, mno);
            result = pstmt.executeUpdate();
        }
        return result;
    }
}
