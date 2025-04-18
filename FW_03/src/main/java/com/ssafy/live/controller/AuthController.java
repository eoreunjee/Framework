package com.ssafy.live.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.ssafy.live.model.dto.Address;
import com.ssafy.live.model.dto.Member;
import com.ssafy.live.model.dto.Page;
import com.ssafy.live.model.dto.SearchCondition;
import com.ssafy.live.model.service.AddressService;
import com.ssafy.live.model.service.BasicAddressService;
import com.ssafy.live.model.service.BasicMemberService;
import com.ssafy.live.model.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@WebServlet(urlPatterns = "/auth")
@SuppressWarnings("serial")

//추가
@RequiredArgsConstructor
public class AuthController extends HttpServlet implements ControllerHelper {
	
	private final MemberService mService;
    private final AddressService aService;

//    private final MemberService mService = BasicMemberService.getService();
//    private final AddressService aService = BasicAddressService.getService();

    // TODO: 02-01. 개별적으로 발급받은 키를 등록하세요.
     private final String keyVworld = "v_world의 인증키";
     private final String keySgisServiceId = "서비스 id"; // 서비스 id
     private final String keySgisSecurity = "보안 key"; // 보안 key
     private final String keyData = "data.go.kr key"; // data.go.kr 인증키

    // END

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = preProcessing(request, response);
        System.out.println(action);
        switch (action) {
        case "member-detail" -> memberDetail(request, response);
        case "member-list" -> memberList(request, response);
        case "member-delete" -> memberDelete(request, response);
        case "member-modify-form" -> memberModifyForm(request, response);
        case "member-modify" -> memberModify(request, response);
        case "address-delete" -> addressDelete(request, response);
        case "address-insert" -> addressInsert(request, response);
        // default -> response.sendError(HttpServletResponse.SC_NOT_FOUND); // WAS에 404
        // 전달
        default -> forward(request, response, "/error/404.jsp"); // 지정된 페이지로 forward
        }
    }

    private void memberDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        try {

            Member member = mService.selectDetail(email);
            request.setAttribute("member", member);
            request.setAttribute("key_vworld", keyVworld);
            request.setAttribute("key_sgis_service_id", keySgisServiceId);
            request.setAttribute("key_sgis_security", keySgisSecurity);
            request.setAttribute("key_data", keyData);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("alertMsg", e.getMessage());
        }
        forward(request, response, "/member/member-detail.jsp");
    }

    private void memberList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> keyMap = Map.of("1", "name", "2", "email");
        String key = request.getParameter("key");
        if (key != null) {
            key = keyMap.getOrDefault(key, "");
        }
        String word = request.getParameter("word");
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        try {
            Page<Member> page = mService.search(new SearchCondition(key, word, currentPage));
            request.setAttribute("page", page);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("alertMsg", e.getMessage());
        }
        forward(request, response, "/member/member-list.jsp");
    }

    private void memberModifyForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        try {
            Member member = mService.selectDetail(email);
            request.setAttribute("member", member);
            forward(request, response, "/member/member-modify-form.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // 다시 member detail 상황으로 이동
            HttpSession session = request.getSession();
            session.setAttribute("alertMsg", e.getMessage());
            redirect(request, response, "/auth?action=member-detail&email=" + email);
        }
    }

    private void memberModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        int mno = Integer.parseInt(request.getParameter("mno"));

        try {
            //Member member = new Member(mno, name, email, password, role);
        	Member member = Member.builder().mno(mno).name(name).email(email).password(password).role(role).build();
            mService.update(member);
            // 세션 사용자의 경우 이름 변경해주기
            HttpSession session = request.getSession();
            if (((Member) session.getAttribute("loginUser")).getEmail().equals(member.getEmail())) {
                session.setAttribute("loginUser", member);
            }
            // post redirect get
            redirect(request, response, "/auth?action=member-detail&email=" + email);
        } catch (SQLException e) {
            e.printStackTrace();
            // 다시 member detail 상황으로 이동
            HttpSession session = request.getSession();
            session.setAttribute("alertMsg", e.getMessage());
            redirect(request, response, "/auth?action=member-detail&email=" + email);
        }
    }

    private void addressInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int mno = Integer.parseInt(request.getParameter("mno"));
            String title = request.getParameter("addr_title");
            String main = request.getParameter("addr_main");
            String detail = request.getParameter("addr_detail");
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String email = request.getParameter("email");
            Address addr = new Address(0, mno, title, main, detail, x, y);
            aService.registAddress(addr);
            Member member = mService.selectDetail(email);
            toJSON(member.getAddresses(), response);
        } catch (SQLException e) {
            e.printStackTrace();
            toJSON(Map.of("error", e.getMessage()), response);
        }
    }

    private void addressDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int ano = Integer.parseInt(request.getParameter("ano"));
            String email = request.getParameter("email");
            aService.deleteAddress(ano);
            Member member = mService.selectDetail(email);
            toJSON(member.getAddresses(), response);
        } catch (SQLException e) {
            e.printStackTrace();
            toJSON(Map.of("error", e.getMessage()), response);
        }
    }

    private void memberDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int mno = Integer.parseInt(request.getParameter("mno"));
        String email = request.getParameter("email");
        try {
            mService.delete(mno);
            HttpSession session = request.getSession();
            if (session.getAttribute("loginUser") instanceof Member m && m.getMno() == mno) {
                session.invalidate();
                redirect(request, response, "/");
            } else {
                session.setAttribute("alertMsg", "삭제되었습니다.");
                redirect(request, response, "/auth?action=member-list&currentPage=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("alertMsg", "삭제 실패");
            redirect(request, response, "/auth?action=member-detail&email=" + email);
        }
    }
}
